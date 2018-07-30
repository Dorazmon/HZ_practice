package cn.com.tcsec.sdlmp.common.api;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;

import cn.com.tcsec.sdlmp.common.annotation.AccessControl;
import cn.com.tcsec.sdlmp.common.annotation.ParamCtrl;
import cn.com.tcsec.sdlmp.common.param.REGEX;

@JsonRpcService("/auth")
public interface AuthServerAPI {

	/**
	 * 添加一个项目
	 * 
	 * @param caller
	 *            调用者的ID，
	 * @param auth_token
	 *            调用者的验证信息，
	 * @param user_id
	 *            用户名
	 * @param type
	 *            0-个人项目，1-公司项目（取决于用户在页面的选择）
	 * @param name
	 *            项目名称（1-90字节的字母数字下划线短线组成的字符串）
	 * @param number
	 *            项目编号
	 * @param url
	 *            项目的存放地址
	 * @param desc
	 *            项目描述
	 * @param language
	 *            项目的语言（java，php。。。。。。）
	 * @param state
	 *            状态
	 * @return 成功返回'success'，失败返回错误说明
	 */
	@AccessControl(enable = true, method = true, paramIndex = { 2 }, cycle = 60, count = 3)
	Object addProject(@ParamCtrl(regex = REGEX.accessToken) @JsonRpcParam(value = "access_token") String access_token,
			@ParamCtrl(regex = REGEX.userID) @JsonRpcParam(value = "user_id") String user_id,
			@ParamCtrl(regex = REGEX.projectType, must = false) @JsonRpcParam(value = "type") String type,
			@ParamCtrl(regex = REGEX.projectName) @JsonRpcParam(value = "name") String name,
			@ParamCtrl(regex = REGEX.projectNumber) @JsonRpcParam(value = "number") String number,
			@ParamCtrl(regex = REGEX.projectUrl) @JsonRpcParam(value = "url") String url,
			@ParamCtrl(regex = REGEX.projectDesc, ckeckSqlInject = true) @JsonRpcParam(value = "desc") String desc,
			@ParamCtrl(regex = REGEX.projectLanguage) @JsonRpcParam(value = "language") String language,
			@ParamCtrl(regex = REGEX.projectState, must = false) @JsonRpcParam(value = "state") String state,
			@ParamCtrl(regex = REGEX.userID) @JsonRpcParam(value = "project_user") String[] project_user);

	/**
	 * 删除一个项目
	 * 
	 * @param caller
	 *            调用者的ID，
	 * @param auth_token
	 *            调用者的验证信息，
	 * @param project_id
	 *            tb_project 表的id字段的值
	 * @return 删除成功返回success，失败返回错误原因
	 */
	@AccessControl(enable = true, method = true, paramIndex = { 1 }, cycle = 10, count = 3)
	Object deleteProject(
			@ParamCtrl(regex = REGEX.accessToken) @JsonRpcParam(value = "access_token") String access_token,
			@ParamCtrl(regex = REGEX.tableID) @JsonRpcParam(value = "project_id") String[] project_id);

	/**
	 * 设置一个project， 可以用来设置一个项目的url，desc，state.
	 * 
	 * @param caller
	 *            调用者的ID，
	 * @param auth_token
	 *            调用者的验证信息，
	 * @param project_id
	 *            项目id
	 * @param url
	 *            项目库地址
	 * @param name
	 *            项目名称
	 * @param number
	 *            项目编号
	 * @param desc
	 *            项目描述
	 * @param language
	 *            项目语言
	 * @param state
	 *            项目状态 ，非必选
	 * @param project_user
	 *            项目参与者
	 * @param flag
	 *            1-增加协助者，0-删除协助者
	 * @return 删除成功返回success，失败返回错误原因
	 */
	@AccessControl(enable = true, method = true, paramIndex = { 1 }, cycle = 10, count = 2)
	Object setProject(@ParamCtrl(regex = REGEX.accessToken) @JsonRpcParam(value = "access_token") String access_token,
			@ParamCtrl(regex = REGEX.tableID) @JsonRpcParam(value = "project_id") String project_id,
			@ParamCtrl(regex = REGEX.projectUrl) @JsonRpcParam(value = "url") String url,
			@ParamCtrl(regex = REGEX.projectName) @JsonRpcParam(value = "name") String name,
			@ParamCtrl(regex = REGEX.projectNumber) @JsonRpcParam(value = "number") String number,
			@ParamCtrl(regex = REGEX.projectDesc, ckeckSqlInject = true) @JsonRpcParam(value = "desc") String desc,
			@ParamCtrl(regex = REGEX.projectLanguage) @JsonRpcParam(value = "language") String language,
			@ParamCtrl(regex = REGEX.projectState) @JsonRpcParam(value = "state") String state,
			@ParamCtrl(regex = REGEX.userID) @JsonRpcParam(value = "project_user") String[] project_user);

	/**
	 * 获取项目列表，返回的内容由输入的参数决定
	 * 
	 * @param caller
	 *            调用者的ID，
	 * @param auth_token
	 *            调用者的验证信息，
	 * @param user_id
	 *            登录的用户的用户名
	 * @param type
	 *            可选择的项目类型 0-个人，1-公司
	 * @param current
	 *            如果项目很多，可以选择显示哪一页
	 * @param page_size
	 *            每一页要显示的数量
	 * @param sort
	 *            排序方式，可以按类型、时间等属性排序，以帮助用户快速找到目标
	 * @param name
	 *            项目的名字，如果输入了该参数，可以直接返回那个项目
	 * @return 成功返回项目列表，失败返回错误原因
	 */
	@AccessControl(enable = false, method = true, paramIndex = { 2 }, cycle = 1, count = 1)
	Object getProjectList(
			@ParamCtrl(regex = REGEX.accessToken) @JsonRpcParam(value = "access_token") String access_token,
			@ParamCtrl(regex = REGEX.userID) @JsonRpcParam(value = "user_id") String user_id,
			@ParamCtrl(regex = REGEX.projectType, must = false) @JsonRpcParam(value = "type") String type,
			@ParamCtrl(regex = REGEX.currentPage, must = false) @JsonRpcParam(value = "current") String current,
			@ParamCtrl(regex = REGEX.pageSize, must = false) @JsonRpcParam(value = "page_size") String page_size,
			@ParamCtrl(regex = REGEX.sortContent, must = false) @JsonRpcParam(value = "sort") String sort,
			@ParamCtrl(regex = REGEX.projectName, must = false) @JsonRpcParam(value = "name") String name);

	/**
	 * 给一个项目增加协助者
	 * 
	 * @param caller
	 *            调用者的ID，
	 * @param auth_token
	 *            调用者的验证信息，
	 * @param project_id
	 *            项目表里的id字段的值
	 * @param user_id
	 *            用户名列表
	 * @return 成功返回success，失败返回原因
	 */
	@AccessControl(enable = true, method = true, paramIndex = { 1 }, cycle = 20, count = 3)
	Object setProjectUser(
			@ParamCtrl(regex = REGEX.accessToken) @JsonRpcParam(value = "access_token") String access_token,
			@ParamCtrl(regex = REGEX.tableID) @JsonRpcParam(value = "project_id") String project_id,
			@ParamCtrl(regex = REGEX.userID) @JsonRpcParam(value = "user_list") String[] user_list);

	/**
	 * 增加一个计划
	 * 
	 * @param caller
	 *            调用者的ID，
	 * @param auth_token
	 *            调用者的验证信息，
	 * @param project_id
	 *            项目表ID
	 * @param user_id
	 *            用户名
	 * @param type
	 *            0-个人，1-公司
	 * @param time_flag
	 *            1-每天，2-每周，3-每月
	 * @param time
	 *            (yyyy-MM-dd HH:mm:ss)
	 * @param immediatelyFlag
	 *            是否立即执行一次
	 * @return 成功返回plan_id，失败返回错误原因
	 */
	@AccessControl(enable = true, method = true, paramIndex = { 1 }, cycle = 20, count = 3)
	Object addPlan(@ParamCtrl(regex = REGEX.accessToken) @JsonRpcParam(value = "access_token") String access_token,
			@ParamCtrl(regex = REGEX.tableID) @JsonRpcParam(value = "project_id") String project_id,
			@ParamCtrl(regex = REGEX.userID) @JsonRpcParam(value = "user_id") String user_id,
			@ParamCtrl(regex = REGEX.planType) @JsonRpcParam(value = "type") String type,
			@ParamCtrl(regex = REGEX.timeFlag) @JsonRpcParam(value = "time_flag") String time_flag,
			@ParamCtrl(regex = REGEX.planTime) @JsonRpcParam(value = "time") String time,
			@ParamCtrl(regex = REGEX.immediatelyFlag) @JsonRpcParam(value = "immediatelyFlag") String immediatelyFlag,
			@ParamCtrl(regex = REGEX.planDesc, ckeckSqlInject = true) @JsonRpcParam(value = "desc") String desc);

	/**
	 * 修改一个计划的属性
	 * 
	 * @param caller
	 *            调用者的ID，
	 * @param auth_token
	 *            调用者的验证信息，
	 * @param plan_id
	 *            计划编号
	 * @param time_flag
	 *            ( 1-每天，2-每周，3-每月)
	 * @param time
	 *            (yyyy-MM-dd HH:mm:ss)
	 * @param immediatelyFlag
	 *            是否立即执行一次
	 * @return 成功返回success，失败返回错误原因
	 */
	@AccessControl(enable = true, method = true, paramIndex = { 2 }, cycle = 20, count = 1)
	Object setPlan(@ParamCtrl(regex = REGEX.accessToken) @JsonRpcParam(value = "access_token") String access_token,
			@ParamCtrl(regex = REGEX.tableID) @JsonRpcParam(value = "plan_id") String plan_id,
			@ParamCtrl(regex = REGEX.timeFlag) @JsonRpcParam(value = "time_flag") String time_flag,
			@ParamCtrl(regex = REGEX.planTime) @JsonRpcParam(value = "time") String time,
			@ParamCtrl(regex = REGEX.immediatelyFlag) @JsonRpcParam(value = "immediatelyFlag") String immediatelyFlag,
			@ParamCtrl(regex = REGEX.planState) @JsonRpcParam(value = "state") String state,
			@ParamCtrl(regex = REGEX.planDesc, ckeckSqlInject = true) @JsonRpcParam(value = "desc") String desc);

	/**
	 * 获取计划列表
	 * 
	 * @param caller
	 *            调用者的ID，
	 * @param auth_token
	 *            调用者的验证信息，
	 * @param user_id
	 *            用户名称
	 * @param current
	 *            如果项目很多，可以选择显示哪一页
	 * @param page_size
	 *            每一页要显示的数量
	 * @param sort
	 *            排序规则
	 * @param type
	 *            计划类型 0-个人，1-公司
	 * @return 成功返回计划列表，失败返回错误原因
	 */
	@AccessControl(enable = false, method = true, paramIndex = { 2 }, cycle = 1, count = 1)
	Object getPlanList(@ParamCtrl(regex = REGEX.accessToken) @JsonRpcParam(value = "access_token") String access_token,
			@ParamCtrl(regex = REGEX.userID) @JsonRpcParam(value = "user_id") String user_id,
			@ParamCtrl(regex = REGEX.currentPage, must = false) @JsonRpcParam(value = "current") String current,
			@ParamCtrl(regex = REGEX.pageSize, must = false) @JsonRpcParam(value = "page_size") String page_size,
			@ParamCtrl(regex = REGEX.sortContent, must = false) @JsonRpcParam(value = "sort") String sort,
			@ParamCtrl(regex = REGEX.planType, must = false) @JsonRpcParam(value = "type") String type);

	/**
	 * 删除一个计划
	 * 
	 * @param caller
	 *            调用者的ID，
	 * @param auth_token
	 *            调用者的验证信息，
	 * @param plan_id
	 *            计划编号
	 * @return 成功返回success，失败返回错误原因
	 */
	@AccessControl(enable = true, method = true, paramIndex = { 1 }, cycle = 10, count = 1)
	Object deletePlan(@ParamCtrl(regex = REGEX.accessToken) @JsonRpcParam(value = "access_token") String access_token,
			@ParamCtrl(regex = REGEX.tableID) @JsonRpcParam(value = "plan_id") String plan_id);

	/**
	 * 获取报告列表
	 * 
	 * @param caller
	 *            调用者的ID，
	 * @param auth_token
	 *            调用者的验证信息，
	 * @param project_id
	 *            项目编号
	 * @param plan_id
	 *            计划编号
	 * @param user_id
	 *            用户账号
	 * @param current
	 *            如果项目很多，可以选择显示哪一页
	 * @param page_size
	 *            每一页要显示的数量
	 * @param sort
	 *            排序规则
	 * @return 成功返回计划列表，失败返回错误原因
	 */
	@AccessControl(enable = false, method = true, paramIndex = { 1 }, cycle = 1, count = 1)
	Object getReportList(
			@ParamCtrl(regex = REGEX.accessToken) @JsonRpcParam(value = "access_token") String access_token,
			@ParamCtrl(regex = REGEX.tableID, must = false) @JsonRpcParam(value = "project_id") String project_id,
			@ParamCtrl(regex = REGEX.tableID, must = false) @JsonRpcParam(value = "plan_id") String plan_id,
			@ParamCtrl(regex = REGEX.userID) @JsonRpcParam(value = "user_id") String user_id,
			@ParamCtrl(regex = REGEX.currentPage, must = false) @JsonRpcParam(value = "current") String current,
			@ParamCtrl(regex = REGEX.pageSize, must = false) @JsonRpcParam(value = "page_size") String page_size,
			@ParamCtrl(regex = REGEX.sortContent, must = false) @JsonRpcParam(value = "sort") String sort);

	/**
	 * 获取一个报告
	 * 
	 * @param caller
	 *            调用者的ID，
	 * @param auth_token
	 *            调用者的验证信息，
	 * @param task_id
	 *            任务编号
	 * @param current
	 *            如果项目很多，可以选择显示哪一页
	 * @param page_size
	 *            每一页要显示的数量
	 * @param sort
	 *            排序规则
	 * @return 成功返回计划列表，失败返回错误原因
	 */
	@AccessControl(enable = false, method = true, paramIndex = { 1 }, cycle = 1, count = 1)
	Object getReport(@ParamCtrl(regex = REGEX.accessToken) @JsonRpcParam(value = "access_token") String access_token,
			@ParamCtrl(regex = REGEX.tableID) @JsonRpcParam(value = "task_id") String task_id,
			@ParamCtrl(regex = REGEX.currentPage) @JsonRpcParam(value = "current") String current,
			@ParamCtrl(regex = REGEX.pageSize) @JsonRpcParam(value = "page_size") String page_size,
			@ParamCtrl(regex = REGEX.sortContent) @JsonRpcParam(value = "sort") String sort);

	/**
	 * 生成一个报告
	 * 
	 * @param caller
	 *            调用者的ID，
	 * @param auth_token
	 *            调用者的验证信息，
	 * @param task_id
	 *            任务编号
	 * @param resultDate
	 *            报告内容
	 * @return 成功返回计划列表，失败返回错误原因
	 */
	@AccessControl(enable = true, method = true, paramIndex = { 1 }, cycle = 1, count = 3)
	Object getOutline(@ParamCtrl(regex = REGEX.accessToken) @JsonRpcParam(value = "access_token") String access_token,
			@ParamCtrl(regex = REGEX.tableID) @JsonRpcParam(value = "project_id") String project_id);

	/**
	 * 生成一个报告
	 * 
	 * @param caller
	 *            调用者的ID，
	 * @param auth_token
	 *            调用者的验证信息，
	 * @param task_id
	 *            任务编号
	 * @param resultDate
	 *            报告内容
	 * @return 成功返回计划列表，失败返回错误原因
	 */
	@AccessControl(enable = true, method = true, paramIndex = { 1 }, cycle = 1, count = 3)
	Object getChartProject(
			@ParamCtrl(regex = REGEX.accessToken) @JsonRpcParam(value = "access_token") String access_token,
			@ParamCtrl(regex = REGEX.tableID) @JsonRpcParam(value = "project_id") String project_id);

	/**
	 * 生成一个报告
	 * 
	 * @param caller
	 *            调用者的ID，
	 * @param auth_token
	 *            调用者的验证信息，
	 * @param task_id
	 *            任务编号
	 * @param resultDate
	 *            报告内容
	 * @return 成功返回计划列表，失败返回错误原因
	 */
	@AccessControl(enable = true, method = true, paramIndex = { 1 }, cycle = 1, count = 3)
	Object getChartBugInfo(
			@ParamCtrl(regex = REGEX.accessToken) @JsonRpcParam(value = "access_token") String access_token,
			@ParamCtrl(regex = REGEX.tableID) @JsonRpcParam(value = "project_id") String project_id);

	/**
	 * 生成一个报告
	 * 
	 * @param caller
	 *            调用者的ID，
	 * @param auth_token
	 *            调用者的验证信息，
	 * @param task_id
	 *            任务编号
	 * @param resultDate
	 *            报告内容
	 * @return 成功返回计划列表，失败返回错误原因
	 */
	@AccessControl(enable = true, method = true, paramIndex = { 1 }, cycle = 1, count = 3)
	Object getChartIssueRanking(
			@ParamCtrl(regex = REGEX.accessToken) @JsonRpcParam(value = "access_token") String access_token,
			@ParamCtrl(regex = REGEX.tableID) @JsonRpcParam(value = "project_id") String project_id,
			@ParamCtrl(regex = REGEX.riskType) @JsonRpcParam(value = "risk_type") String risk_type);

	/**
	 * 生成一个报告
	 * 
	 * @param caller
	 *            调用者的ID，
	 * @param auth_token
	 *            调用者的验证信息，
	 * @param task_id
	 *            任务编号
	 * @param resultDate
	 *            报告内容
	 * @return 成功返回计划列表，失败返回错误原因
	 */
	@AccessControl(enable = true, method = true, paramIndex = { 1 }, cycle = 1, count = 3)
	Object getChartIssueClassify(
			@ParamCtrl(regex = REGEX.accessToken) @JsonRpcParam(value = "access_token") String access_token,
			@ParamCtrl(regex = REGEX.tableID) @JsonRpcParam(value = "project_id") String project_id,
			@ParamCtrl(regex = REGEX.riskType) @JsonRpcParam(value = "risk_type") String risk_type);

	@AccessControl(enable = true, method = true, paramIndex = { 1 }, cycle = 1, count = 3)
	Object getChartOneIssueRanking(
			@ParamCtrl(regex = REGEX.accessToken) @JsonRpcParam(value = "access_token") String access_token,
			@ParamCtrl(regex = REGEX.tableID) @JsonRpcParam(value = "task_id") String task_id,
			@ParamCtrl(regex = REGEX.riskType) @JsonRpcParam(value = "risk_type") String risk_type);

	@AccessControl(enable = true, method = true, paramIndex = { 1 }, cycle = 1, count = 3)
	Object getChartOneIssueClassify(
			@ParamCtrl(regex = REGEX.accessToken) @JsonRpcParam(value = "access_token") String access_token,
			@ParamCtrl(regex = REGEX.tableID) @JsonRpcParam(value = "task_id") String task_id,
			@ParamCtrl(regex = REGEX.riskType) @JsonRpcParam(value = "risk_type") String risk_type);

	/**
	 * 生成一个报告
	 * 
	 * @param caller
	 *            调用者的ID，
	 * @param auth_token
	 *            调用者的验证信息，
	 * @param task_id
	 *            任务编号
	 * @param resultDate
	 *            报告内容
	 * @return 成功返回计划列表，失败返回错误原因
	 */
	@AccessControl(enable = true, method = true, paramIndex = { 1 }, cycle = 1, count = 3)
	Object getChartIssueLevel(
			@ParamCtrl(regex = REGEX.accessToken) @JsonRpcParam(value = "access_token") String access_token,
			@ParamCtrl(regex = REGEX.tableID) @JsonRpcParam(value = "project_id") String project_id);

	/**
	 * 生成一个报告
	 * 
	 * @param caller
	 *            调用者的ID，
	 * @param auth_token
	 *            调用者的验证信息，
	 * @param task_id
	 *            任务编号
	 * @param resultDate
	 *            报告内容
	 * @return 成功返回计划列表，失败返回错误原因
	 */
	@AccessControl(enable = true, method = true, paramIndex = { 1 }, cycle = 1, count = 3)
	Object getChartResolutionRate(
			@ParamCtrl(regex = REGEX.accessToken) @JsonRpcParam(value = "access_token") String access_token,
			@ParamCtrl(regex = REGEX.tableID) @JsonRpcParam(value = "project_id") String project_id);

	/**
	 * 生成一个报告
	 * 
	 * @param caller
	 *            调用者的ID，
	 * @param auth_token
	 *            调用者的验证信息，
	 * @param task_id
	 *            任务编号
	 * @param resultDate
	 *            报告内容
	 * @return 成功返回计划列表，失败返回错误原因
	 */
	@AccessControl(enable = true, method = true, paramIndex = { 1 }, cycle = 1, count = 3)
	Object getChartProjectRrade(
			@ParamCtrl(regex = REGEX.accessToken) @JsonRpcParam(value = "access_token") String access_token,
			@ParamCtrl(regex = REGEX.tableID) @JsonRpcParam(value = "project_id") String project_id);

	/**
	 * 生成一个报告
	 * 
	 * @param caller
	 *            调用者的ID，
	 * @param auth_token
	 *            调用者的验证信息，
	 * @param task_id
	 *            任务编号
	 * @param resultDate
	 *            报告内容
	 * @return 成功返回计划列表，失败返回错误原因
	 */
	@AccessControl(enable = true, method = true, paramIndex = { 1 }, cycle = 1, count = 3)
	Object getNotify(@ParamCtrl(regex = REGEX.accessToken) @JsonRpcParam(value = "access_token") String access_token);

}
