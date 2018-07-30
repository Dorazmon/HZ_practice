
package cn.com.tcsec.sdlmp.auth.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;

import cn.com.tcsec.sdlmp.auth.export.entity.BugInfo;
import cn.com.tcsec.sdlmp.auth.service.AuthService;
import cn.com.tcsec.sdlmp.common.api.AuthServerAPI;
import cn.com.tcsec.sdlmp.common.exception.ErrorContent;
import redis.clients.jedis.JedisPool;

@Service
@AutoJsonRpcServiceImpl
public class AuthServerAPIImpl implements AuthServerAPI {
	@Autowired
	AuthService authServer;

	@Autowired
	JedisPool jedisPool;

	@Override
	public Object addProject(String access_token, String user_id, String type, String name, String number, String url,
			String desc, String language, String state, String[] project_user) {
		String userId = access_token.split(":")[0];
		if (!user_id.equals(userId) || !userId.equals(project_user[0])) {
			return ErrorContent.SYSTEM_REJECT;
		}
		return authServer.addProject(user_id, type, name, number, url, desc, language, state, project_user);
	}

	// project_id write
	@Override
	public Object deleteProject(String access_token, String[] project_id) {
		String userId = access_token.split(":")[0];
		for (String projectId : project_id) {
			if (!authServer.getAuthProjectAndUserWrite(userId, projectId)) {
				return ErrorContent.SYSTEM_REJECT;
			}
		}

		return authServer.deleteProject(project_id);
	}

	// project_id write
	@Override
	public Object setProject(String access_token, String project_id, String url, String name, String number,
			String desc, String language, String state, String[] project_user) {
		String userId = access_token.split(":")[0];
		if (!userId.equals(project_user[0]) || !authServer.getAuthProjectAndUserWrite(userId, project_id)) {
			return ErrorContent.SYSTEM_REJECT;
		}

		return authServer.setProject(project_id, name, url, number, desc, language, state, project_user);
	}

	@Override
	public Object getProjectList(String access_token, String user_id, String type, String current, String page_size,
			String sort, String name) {
		if (!user_id.equals(access_token.split(":")[0])) {
			return ErrorContent.SYSTEM_REJECT;
		}
		return authServer.getProjectList(user_id, type, current, page_size, sort, name);
	}

	// project_user write
	@Override
	public Object setProjectUser(String access_token, String project_id, String[] user_list) {
		String userId = access_token.split(":")[0];
		if (!userId.equals(user_list[0]) || !authServer.getAuthProjectAndUserWrite(userId, project_id)) {
			return ErrorContent.SYSTEM_REJECT;
		}
		return authServer.setProjectUser(project_id, user_list);
	}

	// project_id write
	@Override
	public Object addPlan(String access_token, String project_id, String user_id, String type, String time_flag,
			String time, String immediatelyFlag, String desc) {
		String userId = access_token.split(":")[0];
		if (!user_id.equals(userId) || !authServer.getAuthProjectAndUserWrite(userId, project_id)) {
			return ErrorContent.SYSTEM_REJECT;
		}
		return authServer.addPlan(project_id, user_id, type, time_flag, time, immediatelyFlag, desc);
	}

	// plan_id write
	@Override
	public Object setPlan(String access_token, String plan_id, String time_flag, String time, String immediatelyFlag,
			String state, String desc) {
		String userId = access_token.split(":")[0];
		if (!authServer.getAuthPlanIdWrite(userId, plan_id)) {
			return ErrorContent.SYSTEM_REJECT;
		}
		return authServer.setPlan(plan_id, time_flag, time, immediatelyFlag, state, desc);
	}

	@Override
	public Object getPlanList(String access_token, String user_id, String current, String page_size, String sort,
			String type) {
		if (!user_id.equals(access_token.split(":")[0])) {
			return ErrorContent.SYSTEM_REJECT;
		}
		return authServer.getPlanList(user_id, current, sort, type);
	}

	// plan_id write
	@Override
	public Object deletePlan(String access_token, String plan_id) {
		String userId = access_token.split(":")[0];
		if (!authServer.getAuthPlanIdWrite(userId, plan_id)) {
			return ErrorContent.SYSTEM_REJECT;
		}
		return authServer.deletePlan(plan_id);
	}

	@Override
	public Object getReportList(String access_token, String project_id, String plan_id, String user_id, String current,
			String page_size, String sort) {
		if (!user_id.equals(access_token.split(":")[0])) {
			return ErrorContent.SYSTEM_REJECT;
		}
		return authServer.getReportList(project_id, plan_id, user_id, current, page_size, sort);
	}

	// task_id read
	@Override
	public Object getReport(String access_token, String task_id, String current, String page_size, String sort) {
		String userId = access_token.split(":")[0];
		if (!authServer.getAuthTaskIdRead(userId, task_id)) {
			return ErrorContent.SYSTEM_REJECT;
		}
		return authServer.getReport(task_id, current, page_size, sort);
	}

	// project_id read
	@Override
	public Object getOutline(String access_token, String project_id) {
		String userId = access_token.split(":")[0];
		if (!authServer.getAuthProjectIdRead(userId, project_id)) {
			return ErrorContent.SYSTEM_REJECT;
		}
		return authServer.getOutline(project_id);
	}

	// project_id read
	@Override
	public Object getChartProject(String access_token, String project_id) {
		String userId = access_token.split(":")[0];
		if (!authServer.getAuthProjectIdRead(userId, project_id)) {
			return ErrorContent.SYSTEM_REJECT;
		}
		return authServer.getOutline(project_id);
	}

	@Override
	@Deprecated
	public Object getChartBugInfo(String access_token, String project_id) {
		return authServer.getChartBugInfo(project_id, null);
	}

	@Override
	public Object getNotify(String access_token) {
		return authServer.getNotify(access_token.split(":")[0]);
	}

	// project_id read
	@Override
	public Object getChartIssueRanking(String access_token, String project_id, String risk_type) {
		String userId = access_token.split(":")[0];
		if (!authServer.getAuthProjectIdRead(userId, project_id)) {
			return ErrorContent.SYSTEM_REJECT;
		}
		Object obj = authServer.getChartBugInfo(project_id, risk_type);
		if (obj instanceof BugInfo) {
			return ((BugInfo) obj).getRanking();
		}
		return obj;
	}

	// project_id read
	@Override
	public Object getChartIssueClassify(String access_token, String project_id, String risk_type) {
		String userId = access_token.split(":")[0];
		if (!authServer.getAuthProjectIdRead(userId, project_id)) {
			return ErrorContent.SYSTEM_REJECT;
		}
		Object obj = authServer.getChartBugInfo(project_id, risk_type);
		if (obj instanceof BugInfo) {
			return ((BugInfo) obj).getClassify(risk_type);
		}
		return obj;
	}

	@Override
	public Object getChartOneIssueRanking(String access_token, String task_id, String risk_type) {
		String userId = access_token.split(":")[0];
		if (!authServer.getAuthTaskIdRead(userId, task_id)) {
			return ErrorContent.SYSTEM_REJECT;
		}
		Object obj = authServer.getChartBugInfoByTaskId(task_id, risk_type);
		if (obj instanceof BugInfo) {
			return ((BugInfo) obj).getRanking();
		}
		return obj;
	}

	@Override
	public Object getChartOneIssueClassify(String access_token, String task_id, String risk_type) {
		String userId = access_token.split(":")[0];
		if (!authServer.getAuthTaskIdRead(userId, task_id)) {
			return ErrorContent.SYSTEM_REJECT;
		}
		Object obj = authServer.getChartBugInfoByTaskId(task_id, risk_type);
		if (obj instanceof BugInfo) {
			return ((BugInfo) obj).getClassify(risk_type);
		}
		return obj;
	}

	@Override
	@Deprecated
	public Object getChartIssueLevel(String access_token, String project_id) {
		Object obj = authServer.getChartBugInfo(project_id, null);
		if (obj instanceof BugInfo) {
			return ((BugInfo) obj).getLevel();
		}
		return obj;
	}

	// project_id read
	@Override
	public Object getChartResolutionRate(String access_token, String project_id) {
		String userId = access_token.split(":")[0];
		if (!authServer.getAuthProjectIdRead(userId, project_id)) {
			return ErrorContent.SYSTEM_REJECT;
		}
		return authServer.getChartResolutionRate(project_id);
	}

	// project_id read
	@Override
	public Object getChartProjectRrade(String access_token, String project_id) {
		String userId = access_token.split(":")[0];
		if (!authServer.getAuthProjectIdRead(userId, project_id)) {
			return ErrorContent.SYSTEM_REJECT;
		}
		return authServer.getChartProjectRrade(project_id);
	}
}
