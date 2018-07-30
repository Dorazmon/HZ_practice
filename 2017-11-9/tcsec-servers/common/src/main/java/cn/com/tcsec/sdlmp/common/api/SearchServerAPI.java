package cn.com.tcsec.sdlmp.common.api;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;

import cn.com.tcsec.sdlmp.common.annotation.AccessControl;
import cn.com.tcsec.sdlmp.common.annotation.ParamCtrl;
import cn.com.tcsec.sdlmp.common.param.REGEX;

/**
 * 搜索服务的接口列表
 * 
 * @author xiongk
 *
 */
@JsonRpcService("/search")
public interface SearchServerAPI {

	/**
	 * 添加一个开源项目。
	 * 
	 * @param access_token
	 *            验证身份的秘钥，测试阶段先固定使用：ForTest:75ad2d9a0e91cbc68f69b970c2623018007ae0beec51eb757b08edebb41487b1
	 * @param project_name
	 *            项目名称
	 * @param path
	 *            项目的路径，目前只支持192.168.1.199服务器的全路径，更新一个项目时，路径要和上一次保持一致
	 * @param language
	 *            项目使用的语言（java;php）
	 * @return 返回标准成功！
	 */
	@AccessControl(enable = true, method = true, cycle = 60 * 60 * 24, count = 1)
	Object addOpenProject(
			@ParamCtrl(regex = REGEX.accessToken) @JsonRpcParam(value = "access_token") String access_token,
			@ParamCtrl(regex = REGEX.resultData) @JsonRpcParam(value = "project_name") String project_name,
			@ParamCtrl(regex = REGEX.resultData) @JsonRpcParam(value = "path") String path,
			@ParamCtrl(regex = REGEX.projectLanguage) @JsonRpcParam(value = "language") String language);

	/**
	 * 搜索引擎接口，功能尚未开发
	 * 
	 * @param access_token
	 *            验证身份的秘钥
	 * @param value
	 *            搜索的内容
	 * @return 返回相应内容
	 */
	@AccessControl(enable = true, method = true, paramIndex = { 1 }, cycle = 60 * 60 * 24, count = 1)
	Object search(@ParamCtrl(regex = REGEX.accessToken) @JsonRpcParam(value = "access_token") String access_token,
			@ParamCtrl(regex = REGEX.resultData) @JsonRpcParam(value = "value") String value);

	/**
	 * 获取 代码审计行数
	 * 
	 * @param access_token
	 *            验证身份的秘钥，测试阶段先使用：ForTest:75ad2d9a0e91cbc68f69b970c2623018007ae0beec51eb757b08edebb41487b1，正式使用时必须输入用户登录后得到的:access_token.
	 * @return 返回相应内容
	 */
	@AccessControl(enable = true, method = true, paramIndex = { 1 }, cycle = 1, count = 1)
	Object getTotalCount(
			@ParamCtrl(regex = REGEX.accessToken) @JsonRpcParam(value = "access_token") String access_token);

	/**
	 * 获取 世界漏洞列表
	 * 
	 * @param access_token
	 *            验证身份的秘钥，测试阶段先使用：ForTest:75ad2d9a0e91cbc68f69b970c2623018007ae0beec51eb757b08edebb41487b1，正式使用时必须输入用户登录后得到的:access_token.
	 * @return 返回相应内容
	 */
	@AccessControl(enable = true, method = true, paramIndex = { 1 }, cycle = 1, count = 1)
	Object getCountryCount(
			@ParamCtrl(regex = REGEX.accessToken) @JsonRpcParam(value = "access_token") String access_token);

	/**
	 * 获取 中国漏洞列表
	 * 
	 * @param access_token
	 *            验证身份的秘钥，测试阶段先使用：ForTest:75ad2d9a0e91cbc68f69b970c2623018007ae0beec51eb757b08edebb41487b1，正式使用时必须输入用户登录后得到的:access_token.
	 * @return 返回相应内容
	 */
	@AccessControl(enable = true, method = true, paramIndex = { 1 }, cycle = 1, count = 1)
	Object getChinaCount(
			@ParamCtrl(regex = REGEX.accessToken) @JsonRpcParam(value = "access_token") String access_token);

	/**
	 * 获取 项目排名
	 * 
	 * @param access_token
	 *            验证身份的秘钥，测试阶段先使用：ForTest:75ad2d9a0e91cbc68f69b970c2623018007ae0beec51eb757b08edebb41487b1，正式使用时必须输入用户登录后得到的:access_token.
	 * @return 返回相应内容
	 */
	@AccessControl(enable = true, method = true, paramIndex = { 1 }, cycle = 1, count = 1)
	Object getProjectCount(
			@ParamCtrl(regex = REGEX.accessToken) @JsonRpcParam(value = "access_token") String access_token,
			@ParamCtrl(regex = REGEX.riskType) @JsonRpcParam(value = "risk_type") String risk_type);

	/**
	 * 获取 漏洞排名
	 * 
	 * @param access_token
	 *            验证身份的秘钥，测试阶段先使用：ForTest:75ad2d9a0e91cbc68f69b970c2623018007ae0beec51eb757b08edebb41487b1，正式使用时必须输入用户登录后得到的:access_token.
	 * @return 返回相应内容
	 */
	@AccessControl(enable = true, method = true, paramIndex = { 1 }, cycle = 1, count = 1)
	Object getIssueCount(
			@ParamCtrl(regex = REGEX.accessToken) @JsonRpcParam(value = "access_token") String access_token,
			@ParamCtrl(regex = REGEX.riskType) @JsonRpcParam(value = "risk_type") String risk_type);

	/**
	 * 获取 重大预警
	 * 
	 * @param access_token
	 *            验证身份的秘钥，测试阶段先使用：ForTest:75ad2d9a0e91cbc68f69b970c2623018007ae0beec51eb757b08edebb41487b1，正式使用时必须输入用户登录后得到的:access_token.
	 * @return 返回相应内容
	 */
	@AccessControl(enable = true, method = true, paramIndex = { 1 }, cycle = 1, count = 1)
	Object getWarnCount(
			@ParamCtrl(regex = REGEX.accessToken) @JsonRpcParam(value = "access_token") String access_token);

	/**
	 * 获取 语言漏洞排名
	 * 
	 * @param access_token
	 *            验证身份的秘钥，测试阶段先使用：ForTest:75ad2d9a0e91cbc68f69b970c2623018007ae0beec51eb757b08edebb41487b1，正式使用时必须输入用户登录后得到的:access_token.
	 * @return 返回相应内容
	 */
	@AccessControl(enable = true, method = true, paramIndex = { 1 }, cycle = 1, count = 1)
	Object getLanguageCount(
			@ParamCtrl(regex = REGEX.accessToken) @JsonRpcParam(value = "access_token") String access_token,
			@ParamCtrl(regex = REGEX.riskType) @JsonRpcParam(value = "risk_type") String risk_type);

	/**
	 * 获取 漏洞分类（圆饼图）
	 * 
	 * @param access_token
	 *            验证身份的秘钥，测试阶段先使用：ForTest:75ad2d9a0e91cbc68f69b970c2623018007ae0beec51eb757b08edebb41487b1，正式使用时必须输入用户登录后得到的:access_token.
	 * @return 返回相应内容
	 */
	@AccessControl(enable = true, method = true, paramIndex = { 1 }, cycle = 1, count = 1)
	Object getIssuePercentage(
			@ParamCtrl(regex = REGEX.accessToken) @JsonRpcParam(value = "access_token") String access_token,
			@ParamCtrl(regex = REGEX.riskType) @JsonRpcParam(value = "risk_type") String risk_type);

	@Deprecated
	@AccessControl(enable = true, method = true, paramIndex = { 1 }, cycle = 1, count = 1)
	Object getDangerCoefficient(
			@ParamCtrl(regex = REGEX.accessToken) @JsonRpcParam(value = "access_token") String access_token);
}
