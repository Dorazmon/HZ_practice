package cn.com.tcsec.sdlmp.auth.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.com.tcsec.sdlmp.auth.api.AuthServerAPIImpl;
import cn.com.tcsec.sdlmp.auth.db.entity.Plan;
import cn.com.tcsec.sdlmp.auth.db.entity.Project;
import cn.com.tcsec.sdlmp.auth.db.entity.ProjectUser;
import cn.com.tcsec.sdlmp.auth.db.entity.Task;
import cn.com.tcsec.sdlmp.auth.export.entity.BugInfo;
import cn.com.tcsec.sdlmp.auth.export.entity.Notify;
import cn.com.tcsec.sdlmp.auth.mapper.AuthMapper;
import cn.com.tcsec.sdlmp.auth.mapper.IssueLevelMapper;
import cn.com.tcsec.sdlmp.common.client.RestClient;
import cn.com.tcsec.sdlmp.common.entity.IssueLevel;
import cn.com.tcsec.sdlmp.common.exception.ErrorContent;
import cn.com.tcsec.sdlmp.common.util.SerializableUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
@MapperScan("cn.com.tcsec.sdlmp.auth.mapper")
public class AuthService {
	private final static Logger logger = LoggerFactory.getLogger(AuthServerAPIImpl.class);
	ObjectMapper mapper = new ObjectMapper();
	@Autowired
	AuthMapper authMapper;
	@Autowired
	IssueLevelMapper issueLevelMapper;

	@Autowired
	RestClient restClient;
	@Autowired
	JedisPool jedisPool;
	@Autowired
	private JmsMessagingTemplate jmsTemplate;

	public Object getProjectList(String user_id, String type, String current, String page_size, String sort,
			String name) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

		List<ProjectUser> projectUserList = authMapper.selectAllProjectUserByUserId(user_id);
		List<Map<String, List<String>>> userList = new ArrayList<Map<String, List<String>>>();

		String project_id = null;
		String project_id_cur = null;
		Map<String, List<String>> tmpMap = null;
		List<String> tmpList = null;
		for (ProjectUser list : projectUserList) {
			project_id_cur = list.getProject_id();
			if (project_id_cur == null) {
				logger.error("Map key project_id is empty", new Object());
				return ErrorContent.SERVER_ERROR;
			}

			if (project_id == null || !project_id.equals(project_id_cur)) {
				tmpMap = new HashMap<String, List<String>>(1, 2);
				tmpList = new ArrayList<String>();
				tmpList.add(list.getUser_id());
				tmpMap.put(project_id_cur, tmpList);
				userList.add(tmpMap);
				project_id = project_id_cur;
			} else {
				tmpList.add(list.getUser_id());
			}
		}

		Map<String, Object> project = null;
		for (Map<String, List<String>> map : userList) {
			for (String str : map.keySet()) {
				project = authMapper.selectProjectReturnMap(str);
				if (project == null) {
					logger.error("Project {} is not ixist", str);
					return ErrorContent.SERVER_ERROR;
				}

				project.put("user_list", map.get(str));
				result.add(project);
			}
		}

		return result;
	}

	@Transactional
	public Object addProject(String user_id, String type, String name, String number, String url, String desc,
			String language, String state, String[] project_user) {
		Project project = new Project(user_id, type, name, number, url, desc, language, state);
		try {
			authMapper.insertProject(project);
		} catch (DuplicateKeyException e) {
			return ErrorContent.DUPLICATE_TARGET;
		} catch (Exception e) {
			logger.error("Exception	", e);
			return ErrorContent.SERVER_ERROR;
		}

		if (setProjectUser(project.getId(), project_user).equals(ErrorContent.SERVER_ERROR)) {
			return ErrorContent.SERVER_ERROR;
		}

		return ErrorContent.OK;
	}

	@Transactional
	public Object deleteProject(String[] project_id) {
		for (String srt : project_id) {
			int iResult = authMapper.deleteProject(srt);
			if (iResult == 0) {
				return ErrorContent.SERVER_ERROR;
			}

			iResult = authMapper.deleteProjectUserByProjectId(srt);
			if (iResult == 0) {
				return ErrorContent.SERVER_ERROR;
			}
		}

		return ErrorContent.OK;
	}

	@Transactional
	public Object setProject(String project_id, String name, String url, String number, String desc, String language,
			String state, String[] project_user) {

		if (setProjectUser(project_id, project_user).equals(ErrorContent.SERVER_ERROR)) {
			return ErrorContent.SERVER_ERROR;
		}

		int iResult = authMapper.updateProject(new Project(project_id, name, url, number, desc, language, state));
		System.out.println(iResult);
		if (iResult == 0) {
			return ErrorContent.SERVER_ERROR;
		}

		return ErrorContent.OK;
	}

	public Object setProjectUser(String project_id, String[] project_user) {
		List<String> projectUsers = null;
		try {
			projectUsers = authMapper.selectAllProjectUserByProjectId(project_id);
		} catch (Exception e) {
			logger.error("Exception	", e);
			return ErrorContent.SERVER_ERROR;
		}

		List<String> needToDelUser = new ArrayList<String>();
		List<String> needToAddUser = new ArrayList<String>();
		String projectAdmin = project_user[0];

		for (String user_id : project_user) {
			if (!projectUsers.contains(user_id)) {
				needToAddUser.add(user_id);
			}
		}

		for (String user_id : projectUsers) {
			boolean flag = false;
			for (String user : project_user) {
				if (user_id.equals(user)) {
					flag = true;
				}
			}
			if (flag == false && !projectAdmin.equals(user_id)) {
				needToDelUser.add(user_id);
			}
		}

		deleteProjectUser(project_id, needToDelUser.toArray(new String[needToDelUser.size()]));
		addProjectUser(project_id, needToAddUser.toArray(new String[needToAddUser.size()]), projectAdmin);
		return ErrorContent.OK;
	}

	private Object addProjectUser(String project_id, String[] user_id, String projectAdmin) {
		for (String userId : user_id) {
			if (userId.equals(projectAdmin)) {
				authMapper.insertProjectUser(new ProjectUser(project_id, userId, "1", "1"));
			} else {
				authMapper.insertProjectUser(new ProjectUser(project_id, userId, "1", "0"));
			}
		}
		return ErrorContent.OK;
	}

	private Object deleteProjectUser(String project_id, String[] user_id) {
		for (String userId : user_id) {
			authMapper.deleteProjectUser(project_id, userId);
		}
		return ErrorContent.OK;
	}

	public Object addPlan(String project_id, String user_id, String type, String period_flag, String timeParam,
			String immediatelyFlag, String desc) {

		String latestNumber = authMapper.selectLatestPlanNum(project_id);
		if (latestNumber == null) {
			latestNumber = "A";
		} else {
			latestNumber = String.valueOf((char) (latestNumber.charAt(0) + 1));
		}

		Plan plan = null;
		if ("0".equals(period_flag)) {
			plan = new Plan(project_id, user_id, type, latestNumber, period_flag, immediatelyFlag, "00", "00:00", "2",
					desc);
		} else {
			plan = new Plan(project_id, user_id, type, latestNumber, period_flag, immediatelyFlag,
					timeParam.substring(0, 2), timeParam.substring(2), "1", desc);
		}

		try {
			authMapper.insertPlan(plan);
			;
		} catch (DuplicateKeyException e) {
			return ErrorContent.DUPLICATE_TARGET;
		} catch (Exception e) {
			e.printStackTrace();
			return ErrorContent.SERVER_ERROR;
		}

		return ErrorContent.OK;
	}

	public Object setPlan(String plan_id, String period_flag, String timeParam, String immediatelyFlag, String state,
			String desc) {
		Plan plan = null;
		if ("0".equals(period_flag)) {
			plan = new Plan(plan_id, period_flag, immediatelyFlag, "00", "00:00", "2", null, desc);
		} else {
			plan = new Plan(plan_id, period_flag, immediatelyFlag, timeParam.substring(0, 2), timeParam.substring(2),
					state, null, desc);
		}

		int iResult = authMapper.updatePlan(plan);
		if (iResult != 1) {
			return ErrorContent.SERVER_ERROR;
		}
		return ErrorContent.OK;
	}

	public Object getPlanList(String user_id, String page, String sort, String type) {
		List<Map<String, Object>> list = authMapper.selectPlanList(user_id);

		for (Map<String, Object> map : list) {
			Object obj = map.get("scan_count");
			String scan_count = null;
			if (obj == null) {
				scan_count = "0";
			} else {
				scan_count = obj.toString();
			}
			String plan_time = map.get("plan_time").toString();
			Object next_time = map.get("next_time");
			if (next_time == null) {
				next_time = plan_time;
			}
			String period_flag = map.get("period_flag").toString();
			String period = map.get("period").toString();
			String plan_state = map.get("plan_state").toString();
			String state_desc = null;
			String scan_desc = null;
			if (plan_state.equals("1")) {
				state_desc = "共执行了" + scan_count + "次，系统将于" + next_time.toString() + "执行第"
						+ (1 + Integer.valueOf(scan_count)) + "次扫描";
			} else {
				state_desc = "共执行了" + scan_count + "次，计划周期已结束";
			}

			if (period_flag.equals("0")) {
				scan_desc = "立即执行";
			} else if (period_flag.equals("1")) {
				scan_desc = "每天" + plan_time + "执行";
			} else if (period_flag.equals("2")) {
				scan_desc = "每周" + period.substring(1) + " " + plan_time + "执行";
			} else if (period_flag.equals("3")) {
				scan_desc = "每月" + period + "号" + plan_time + "执行";
			}

			map.put("state_desc", state_desc);
			map.put("scan_desc", scan_desc);

			map.put("task_list", authMapper.selectTaskByPlanId(map.get("plan_id").toString()));
		}

		return list;
	}

	public Object deletePlan(String plan_id) {
		authMapper.deletePlan(plan_id);
		return ErrorContent.OK;
	}

	public Object getReportList(String project_id, String plan_id, String user_id, String current, String pageSize,
			String sort) {
		List<Map<String, Object>> list = authMapper.selectReportList(user_id);

		for (Map<String, Object> map : list) {
			Object issue_count = map.get("issue_count");
			Object file_count = map.get("file_count");
			if (issue_count == null) {
				map.put("issue_count", "没有错误");
			} else {
				map.put("issue_count", "存在" + issue_count.toString() + "个错误，其中" + file_count.toString() + "个文件受影响");
			}
		}

		return list;
	}

	public Object getReport(String task_id, String current, String pageSize, String sort) {
		int total = authMapper.selectReportTotal(task_id);
		int iCurrent = Integer.valueOf(current);
		int iPageSize = Integer.valueOf(pageSize);
		if (total == 0) {
			return null;
		}

		int start;
		if (iCurrent == 1) {
			start = 0;
		} else {
			int totalPage = total / iPageSize;
			totalPage = totalPage + (total % iPageSize > 0 ? 1 : 0);
			if (iCurrent > totalPage) {
				iCurrent = totalPage;
			}
			start = (iCurrent - 1) * iPageSize - 1;
		}

		List<Map<String, Object>> list = authMapper.selectReport(task_id, start, iPageSize, sort);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> pageMap = new HashMap<String, Object>();
		pageMap.put("pageSize", iPageSize);
		pageMap.put("total", total);
		pageMap.put("current", iCurrent);
		resultMap.put("pagination", pageMap);
		resultMap.put("results", list);

		return resultMap;
	}

	public Object addTask(Task task) {
		int iResult = 0;
		try {
			iResult = authMapper.insertTask(task);
		} catch (DuplicateKeyException e) {
			return ErrorContent.DUPLICATE_TARGET;
		} catch (Exception e) {
			logger.error("Exception	", e);
			return ErrorContent.SERVER_ERROR;
		}

		if (iResult == 0) {
			return ErrorContent.SERVER_ERROR;
		}

		List<String> list = null;
		try {
			list = authMapper.selectAllProjectUserByProjectId(task.getProject_id());
		} catch (Exception e) {
			logger.error("Exception	", e);
			return ErrorContent.SERVER_ERROR;
		}

		if (list == null || list.isEmpty()) {
			return ErrorContent.SERVER_ERROR;
		}

		byte[] notifyByte = null;
		try {
			notifyByte = SerializableUtil.serialize(new Notify(Notify.TYPE_TASK_START, Notify.DESC_TASK_START,
					Notify.URL_REPORT.replace("#{id}", task.getId())));
		} catch (Exception e) {
			logger.error("序列化对象出错	", e);
			return ErrorContent.SERVER_ERROR;
		}

		Jedis jedis = getNotifyRedis();
		for (String user_id : list) {
			jedis.lpush(user_id.getBytes(), notifyByte);
		}
		jedis.close();
		return ErrorContent.OK;
	}

	public List<Plan> scheduled_scanPlan() {
		List<Plan> list = null;
		try {
			list = authMapper.selectScanPlan();
		} catch (Exception e) {
			logger.error("Exception	", e);
			return null;
		}
		return list;
	}

	@Transactional
	public String scheduled_setPlan(String id, String immediately_flag, String next_time, String state) {
		int iResult = 0;
		try {
			iResult = authMapper.updatePlan(new Plan(id, immediately_flag, next_time, state));
		} catch (Exception e) {
			logger.error("Exception	", e);
			return null;
		}

		if (iResult == 0) {
			return null;
		}

		return "success";
	}

	public Project scheduled_getProject(String project_id) {
		Project project = null;
		try {
			project = authMapper.selectProject(project_id);
		} catch (Exception e) {
			logger.error("Exception	", e);
			return null;
		}

		return project;
	}

	public void scheduled_sendToDocker(Object object) {
		try {
			jmsTemplate.convertAndSend("docker.addProject", object);
		} catch (Exception e) {
			logger.error("Exception	", e);
		}
	}

	public Object getOutline(String project_id) {
		Map<String, Object> outLine = null;
		try {
			outLine = authMapper.callOutline(project_id);
		} catch (Exception e) {
			logger.error("Exception	", e);
			return null;
		}

		List<Object> resultList = new ArrayList<Object>();

		Map<String, Object> map1 = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		Map<String, Object> map3 = new HashMap<String, Object>();
		Map<String, Object> map4 = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<String> list = null;

		String desclUp = "比上一次高";
		String descEquality = "和上一次持平";
		String descDown = "耗时比上次减少";
		String levelUp = "up";
		String levelEquality = "equality";
		String levelDown = "down";

		map1.put("title", "问题数");
		map1.put("count", outLine.get("issue"));
		if (outLine.get("issue_rate").toString().startsWith("-")) {
			map1.put("desc", descDown + " " + outLine.get("issue_rate").toString().substring(1));
			map1.put("level", levelDown);
		} else if (outLine.get("issue_rate").toString().startsWith("0.00")) {
			map1.put("desc", descEquality + " " + outLine.get("issue_rate").toString());
			map1.put("level", levelEquality);
		} else {
			map1.put("desc", desclUp + " " + outLine.get("issue_rate").toString());
			map1.put("level", levelUp);
		}

		map2.put("title", "文件数");
		map2.put("count", outLine.get("file"));
		if (outLine.get("file_rate").toString().startsWith("-")) {
			map2.put("desc", descDown + " " + outLine.get("file_rate").toString().substring(1));
			map2.put("level", levelDown);
		} else if (outLine.get("file_rate").toString().startsWith("0.00")) {
			map2.put("desc", descEquality + " " + outLine.get("file_rate").toString());
			map2.put("level", levelEquality);
		} else {
			map2.put("desc", desclUp + " " + outLine.get("file_rate").toString());
			map2.put("level", levelUp);
		}

		map3.put("title", "审计次数");
		map3.put("count", outLine.get("scan"));
		if (outLine.get("scan_rate").toString().startsWith("-")) {
			map3.put("desc", "扫描时间" + descDown + " " + outLine.get("scan_rate").toString().substring(1));
			map3.put("level", levelDown);
		} else if (outLine.get("scan_rate").toString().startsWith("0.00")) {
			map3.put("desc", "扫描时间" + descEquality + " " + outLine.get("scan_rate").toString());
			map3.put("level", levelEquality);
		} else {
			map3.put("desc", "扫描时间" + desclUp + " " + outLine.get("scan_rate").toString());
			map3.put("level", levelUp);
		}

		map4.put("title", "解决率");
		map4.put("count", outLine.get("solve"));
		if (outLine.get("solve_rate").toString().startsWith("-")) {
			map4.put("desc", descDown + " " + outLine.get("solve_rate").toString().substring(1));
			map4.put("level", levelDown);
		} else if (outLine.get("solve_rate").toString().startsWith("0.00")) {
			map4.put("desc", descEquality + " " + outLine.get("solve_rate").toString());
			map4.put("level", levelEquality);
		} else {
			map4.put("desc", desclUp + " " + outLine.get("solve_rate").toString());
			map4.put("level", levelUp);
		}

		list = authMapper.selectTaskList(project_id);

		resultList.add(map1);
		resultList.add(map2);
		resultList.add(map3);
		resultList.add(map4);

		resultMap.put("countInfo", resultList);
		resultMap.put("statisInfo", list);

		return resultMap;
	}

	@SuppressWarnings("unchecked")
	public Object getChartBugInfo(String project_id, String risk_type) {

		List<IssueLevel> issueList = null;
		try {
			issueList = issueLevelMapper.selectIssueLevelList();
		} catch (Exception e) {
			logger.error("Select tb_issue_level 出错", e);
			return ErrorContent.SERVER_ERROR;
		}

		if (issueList.isEmpty()) {
			logger.error("Select tb_issue_level 为空");
			return ErrorContent.SERVER_ERROR;
		}

		Task task = null;
		try {
			task = authMapper.selectLastTaskByProjectId(project_id);
		} catch (Exception e) {
			logger.error("Select LastTask By ProjectId 出错", e);
			return ErrorContent.SERVER_ERROR;
		}

		BugInfo bugInfo = null;
		if (task == null || task.getIssue_count() == null) {
			return new BugInfo(10);
		} else {
			bugInfo = new BugInfo(Integer.valueOf(task.getIssue_count()));
		}
		Map<String, Integer> map;
		try {
			map = mapper.readValue(task.getInfo(), Map.class);
		} catch (IOException e1) {
			logger.error("jackson 解析percentage 出错 {}", task.getInfo(), e1);
			return ErrorContent.SERVER_ERROR;
		}

		if (map.isEmpty()) {
			return bugInfo;
		}

		for (Entry<String, Integer> entry : map.entrySet()) {
			IssueLevel issueLevel = issueList.get(Integer.valueOf(entry.getKey().substring(5)) - 1);
			if (("high".equals(risk_type)
					&& ("mid".equals(issueLevel.getLevel()) || "low".equals(issueLevel.getLevel())))
					|| ("middle".equals(risk_type)
							&& ("high".equals(issueLevel.getLevel()) || "low".equals(issueLevel.getLevel())))
					|| ("low".equals(risk_type)
							&& ("mid".equals(issueLevel.getLevel()) || "high".equals(issueLevel.getLevel())))) {
				continue;
			}
			bugInfo.add(issueLevel.getDesc_chinese(), entry.getValue(), issueLevel.getLevel());
		}

		return bugInfo;
	}

	private Jedis getNotifyRedis() {
		Jedis jedis = jedisPool.getResource();
		jedis.select(1);
		return jedis;
	}

	public Object getNotify(String user_id) {
		List<Notify> resultList = new ArrayList<>();

		Jedis jedis = getNotifyRedis();
		byte[] obj = null;
		Notify notify = null;
		while ((obj = jedis.rpop(user_id.getBytes())) != null) {
			try {
				notify = (Notify) SerializableUtil.deSerialize(obj);
			} catch (Exception e) {
				logger.error("SerializableUtil 反序列化出错 出错 {}", new String(obj), e);
				return ErrorContent.SERVER_ERROR;
			}
			if (notify == null) {
				logger.error("SerializableUtil 反序列化结果为空 {}", new String(obj));
				return ErrorContent.SERVER_ERROR;
			}
			resultList.add(notify);
		}
		jedis.close();

		Collections.reverse(resultList);

		return resultList;
	}

	public Object getChartResolutionRate(String project_id) {
		return authMapper.selectTaskIndexByProjectId(project_id);
	}

	public Object getChartProjectRrade(String project_id) {
		return authMapper.selectTaskGradeByProjectId(project_id);
	}

	public boolean getAuthProjectIdRead(String user_id, String project_id) {
		String id = null;
		try {
			id = authMapper.selectAuthProjectIdRead(user_id, project_id);
		} catch (Exception e) {
			logger.error("authMapper.selectAuthProjectIdRead 出错！user_id:{},project_id:{}", user_id, project_id);
			logger.error("Exception: ", e);
			return false;
		}

		return id != null && !id.isEmpty();
	}

	public boolean getAuthProjectAndUserWrite(String user_id, String project_id) {
		String id = null;
		try {
			id = authMapper.selectAuthProjectAndUserWrite(user_id, project_id);
		} catch (Exception e) {
			logger.error("authMapper.selectAuthProjectAndUserWrite 出错！user_id:{},project_id:{}", user_id, project_id);
			logger.error("Exception: ", e);
			return false;
		}

		return id != null && !id.isEmpty();
	}

	public boolean getAuthPlanIdWrite(String user_id, String plan_id) {
		String id = null;
		try {
			id = authMapper.selectAuthPlanIdWrite(user_id, plan_id);
		} catch (Exception e) {
			logger.error("authMapper.selectAuthPlanIdWrite 出错！user_id:{},plan_id:{}", user_id, plan_id);
			logger.error("Exception: ", e);
			return false;
		}

		return id != null && !id.isEmpty();
	}

	public boolean getAuthPlanIdRead(String user_id, String plan_id) {
		String id = null;
		try {
			id = authMapper.selectAuthPlanIdRead(user_id, plan_id);
		} catch (Exception e) {
			logger.error("authMapper.selectAuthPlanIdRead 出错！user_id:{},plan_id:{}", user_id, plan_id);
			logger.error("Exception: ", e);
			return false;
		}

		return id != null && !id.isEmpty();
	}

	public boolean getAuthTaskIdRead(String user_id, String task_id) {
		String id = null;
		try {
			id = authMapper.selectAuthTaskIdRead(user_id, task_id);
		} catch (Exception e) {
			logger.error("authMapper.selectAuthTaskIdRead 出错！user_id:{},task_id:{}", user_id, task_id);
			logger.error("Exception: ", e);
			return false;
		}
		
		return id != null && !id.isEmpty();
	}
}
