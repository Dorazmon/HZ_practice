package cn.com.tcsec.sdlmp.auth.analyzer;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.com.tcsec.sdlmp.auth.export.entity.Notify;
import cn.com.tcsec.sdlmp.auth.mapper.AuthMapper;
import cn.com.tcsec.sdlmp.auth.mapper.IssueLevelMapper;
import cn.com.tcsec.sdlmp.common.entity.IssueLevel;
import cn.com.tcsec.sdlmp.common.entity.ScheduledTask;
import cn.com.tcsec.sdlmp.common.entity.ScheduledTask.Report;
import cn.com.tcsec.sdlmp.common.util.SerializableUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
@MapperScan("cn.com.tcsec.sdlmp.auth.mapper")
public class ReportAnalyzer {
	private static final Logger logger = LoggerFactory.getLogger(ReportAnalyzer.class);
	@Autowired
	JedisPool jedisPool;

	@Autowired
	private AuthMapper authMapper;
	@Autowired
	IssueLevelMapper issueLevelMapper;

	ObjectMapper mapper = new ObjectMapper();

	@SuppressWarnings("unchecked")
	private void doAnalyze(ScheduledTask scheduledTask) throws Exception {
		List<String> dataList = scheduledTask.getList();
		if (dataList.isEmpty()) {
			scheduledTask.setExtend_file_count(0);
			scheduledTask.setGrade("100");
			String lastIssue = authMapper.selectLastIssueCount(scheduledTask.getProject_id());
			if (lastIssue == null || lastIssue.isEmpty()) {
				scheduledTask.setResolution_rate("0.00");
			} else {
				BigDecimal last_issue_count = new BigDecimal(lastIssue);
				if (last_issue_count.intValue() == 0) {
					scheduledTask.setResolution_rate("0.00");
				} else {
					scheduledTask.setResolution_rate("100.00");
				}
			}
			return;
		}

		Map<String, Integer> pathMap = new HashMap<String, Integer>();
		for (String jsonStr : dataList) {
			JsonNode rootNode = mapper.readTree(jsonStr);

			String path = findPath(rootNode);

			if (pathMap.containsKey(path)) {
				pathMap.put(path, pathMap.get(path) + 1);
			} else {
				pathMap.put(path, 1);
			}
		}

		int high = 0;
		int mid = 0;
		int low = 0;
		Map<String, Integer> issueMap = mapper.readValue(scheduledTask.getType_percentage(), Map.class);

		List<IssueLevel> list = issueLevelMapper.selectIssueLevelList();

		for (Entry<String, Integer> entry : issueMap.entrySet()) {
			for (IssueLevel issueLevel : list) {
				if (issueLevel.getIssue().equals(entry.getKey())) {
					if ("high".equals(issueLevel.getLevel())) {
						high = high + entry.getValue();
					}
					if ("mid".equals(issueLevel.getLevel())) {
						mid = mid + entry.getValue();
					}
					if ("low".equals(issueLevel.getLevel())) {
						low = low + entry.getValue();
					}
				}
			}
		}
		if ((high + mid + low) == 0) {
			scheduledTask.setGrade("100");
		} else {
			scheduledTask.setGrade(String.valueOf((high * 10 + mid * 35 + low * 55) / (high + mid + low)));
		}

		List<String> lastIssueKeyList = authMapper.selectReportIssueKeyList(scheduledTask.getProject_id());

		if (lastIssueKeyList == null || lastIssueKeyList.isEmpty()) {
			scheduledTask.setExtend_file_count(0);
		} else {
			int extents_file_count = 0;
			for (String issue_key : lastIssueKeyList) {
				if (scheduledTask.containsKey(issue_key)) {
					extents_file_count++;
				}
			}
			scheduledTask.setExtend_file_count(lastIssueKeyList.size() - extents_file_count);
		}

		String lastIssue = authMapper.selectLastIssueCount(scheduledTask.getProject_id());

		if (lastIssue == null || lastIssue.isEmpty()) {
			scheduledTask.setResolution_rate("0.00");
		} else if (scheduledTask.getExtend_file_count() == lastIssueKeyList.size()) {
			scheduledTask.setResolution_rate("100.00");
		} else {
			BigDecimal extend_file_count = new BigDecimal(scheduledTask.getExtend_file_count() * 100);
			BigDecimal last_issue_count = new BigDecimal(lastIssue);
			scheduledTask.setResolution_rate(
					extend_file_count.divide(last_issue_count, 2, BigDecimal.ROUND_HALF_UP).toString());
		}

	}

	public ScheduledTask analyze(ScheduledTask scheduledTask) {
		try {
			doAnalyze(scheduledTask);
			inserReport(scheduledTask);
		} catch (Exception e) {
			exception(scheduledTask, e);
			return scheduledTask;
		}

		finish(scheduledTask);

		return scheduledTask;
	}

	private void inserReport(ScheduledTask scheduledTask) throws Exception {
		if (scheduledTask.getReportList() == null) {
			return;
		}
		int result = 0;
		for (Report report : scheduledTask.getReportList()) {
			result = authMapper.insertReport(scheduledTask.getTask_id(), report.getInfo(), report.getKey());
			if (result != 1) {
				logger.error("insertReport 未找到目标数据，需要检查原因！");
			}
		}
	}

	private void finish(ScheduledTask scheduledTask) {
		int resultCount = 0;
		try {
			resultCount = authMapper.updateTaskWhenFinsh(scheduledTask.getTask_id(), "2",
					scheduledTask.getIssue_count(), scheduledTask.getAffected_file_count(),
					scheduledTask.getExtend_file_count(), scheduledTask.getResolution_rate(),
					scheduledTask.getType_percentage(), scheduledTask.getGrade());
		} catch (Exception e1) {
			logger.error("Exception ", e1);
		}
		if (resultCount == 0) {
			logger.error("updateTaskWhenFinsh 未找到目标数据，需要检查原因！");
		}

		List<String> list = null;
		try {
			list = authMapper.selectAllProjectUserByProjectId(scheduledTask.getProject_id());
		} catch (Exception e) {
			logger.error("Exception	", e);
			return;
		}

		if (list == null || list.isEmpty()) {
			return;
		}

		byte[] notifyByte = null;
		try {
			notifyByte = SerializableUtil.serialize(new Notify(Notify.TYPE_TASK_FINISH, Notify.DESC_TASK_FINISH,
					Notify.URL_REPORT.replace("#{id}", scheduledTask.getTask_id())));
		} catch (Exception e) {
			logger.error("序列化对象出错	", e);
			return;
		}

		Jedis jedis = getNotifyRedis();
		for (String user_id : list) {
			jedis.lpush(user_id.getBytes(), notifyByte);
		}
		jedis.close();

		return;
	}

	private void exception(ScheduledTask scheduledTask, Exception e) {
		logger.error("Exception", e);

		int resultCount = 0;
		try {
			resultCount = authMapper.updateTaskWhenFinsh(scheduledTask.getTask_id(), "3", 0, 0, 0, "0.00", "{}", "0");
		} catch (Exception e1) {
			logger.error("Exception ", e1);
		}
		if (resultCount == 0) {
			logger.error("updateTaskWhenFinsh 未找到目标数据，需要检查原因！");
		}
	}

	private String findPath(JsonNode rootNode) throws Exception {
		String path = rootNode.findValue("path").toString().substring(1);
		path = path.substring(0, path.length() - 1);
		return path;
	}

	private Jedis getNotifyRedis() {
		Jedis jedis = jedisPool.getResource();
		jedis.select(1);
		return jedis;
	}
}
