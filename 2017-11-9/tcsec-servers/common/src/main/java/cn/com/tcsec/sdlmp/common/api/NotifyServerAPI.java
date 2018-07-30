package cn.com.tcsec.sdlmp.common.api;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;

import cn.com.tcsec.sdlmp.common.annotation.AccessControl;
import cn.com.tcsec.sdlmp.common.annotation.ParamCtrl;
import cn.com.tcsec.sdlmp.common.param.REGEX;

@JsonRpcService("/notify")
public interface NotifyServerAPI {
	/**
	 * 给输入的手机号发送一个6位数的验证码
	 * 
	 * @param phone
	 *            手机号码
	 * @param user_id
	 *            用户名
	 * @return 发送成功则返回‘success’ ，失败则返回失败说明
	 */

	@AccessControl(enable = true, method = true, paramIndex = { 2 }, cycle = 60, count = 4)
	Object sendSmsCode(@ParamCtrl(regex = REGEX.emailToken, must = false) @JsonRpcParam(value = "token") String token,
			@ParamCtrl(regex = REGEX.phone) @JsonRpcParam(value = "phone") String phone,
			@ParamCtrl(regex = REGEX.userID) @JsonRpcParam(value = "user_id") String user_id,
			@ParamCtrl(regex = REGEX.imageCode, must = false) @JsonRpcParam(value = "code") String code);

	/**
	 * 验证短信密码是否正确
	 * 
	 * @param phone
	 *            手机号
	 * @param user_id
	 *            用户名
	 * @param token
	 *            6位数的验证码
	 * @return 成功则返回‘success’ ，失败则返回失败说明
	 */
	@AccessControl(enable = true, method = true, paramIndex = { 2 }, cycle = 60, count = 5)
	Object checkSmsCode(
			@ParamCtrl(regex = REGEX.accessToken, must = false) @JsonRpcParam(value = "access_token") String access_token,
			@ParamCtrl(regex = REGEX.phone) @JsonRpcParam(value = "phone") String phone,
			@ParamCtrl(regex = REGEX.userID) @JsonRpcParam(value = "user_id") String user_id,
			@ParamCtrl(regex = REGEX.smscode) @JsonRpcParam(value = "token") String token);

	/**
	 * 验证短信密码是否正确
	 * 
	 * @param phone
	 *            手机号
	 * @param user_id
	 *            用户名
	 * @param token
	 *            6位数的验证码
	 * @return 成功则返回‘success’ ，失败则返回失败说明
	 */
	@AccessControl(enable = true, method = true, paramIndex = { 2 }, cycle = 60, count = 3)
	Object checkSmsCodeForResetPasswd(
			@ParamCtrl(regex = REGEX.accessToken, must = false) @JsonRpcParam(value = "access_token") String access_token,
			@ParamCtrl(regex = REGEX.phone) @JsonRpcParam(value = "phone") String phone,
			@ParamCtrl(regex = REGEX.smscode) @JsonRpcParam(value = "token") String token);

	@AccessControl(enable = true, method = true, paramIndex = { 1 }, cycle = 60 * 60, count = 5)
	Object sendSmsCodeForOauth(@ParamCtrl(regex = REGEX.oauthId) @JsonRpcParam(value = "oauth_id") String oauth_id,
			@ParamCtrl(regex = REGEX.phone) @JsonRpcParam(value = "phone") String phone,
			@ParamCtrl(regex = REGEX.imageCode, must = false) @JsonRpcParam(value = "image_code") String image_code,
			@ParamCtrl(regex = REGEX.emailToken) @JsonRpcParam(value = "flow_token") String flow_token);

	@AccessControl(enable = true, method = true, paramIndex = { 1 }, cycle = 60 * 60, count = 10)
	Object checkSmsCodeForOauth(@ParamCtrl(regex = REGEX.oauthId) @JsonRpcParam(value = "oauth_id") String oauth_id,
			@ParamCtrl(regex = REGEX.phone) @JsonRpcParam(value = "phone") String phone,
			@ParamCtrl(regex = REGEX.smscode) @JsonRpcParam(value = "sms_code") String sms_code,
			@ParamCtrl(regex = REGEX.emailToken) @JsonRpcParam(value = "flow_token") String flow_token);

	// #########################################################

	Object sendSmsCodeForRegister(@ParamCtrl(regex = REGEX.phone) @JsonRpcParam(value = "phone") String phone,
			@ParamCtrl(regex = REGEX.imageCode, must = false) @JsonRpcParam(value = "image_code") String image_code,
			@ParamCtrl(regex = REGEX.emailToken, must = false) @JsonRpcParam(value = "flow_token") String flow_token);

	Object checkSmsCodeForRegister(@ParamCtrl(regex = REGEX.phone) @JsonRpcParam(value = "phone") String phone,
			@ParamCtrl(regex = REGEX.smscode) @JsonRpcParam(value = "sms_code") String sms_code,
			@ParamCtrl(regex = REGEX.emailToken) @JsonRpcParam(value = "flow_token") String flow_token);

	@AccessControl(enable = true, method = true, paramIndex = { 2 }, cycle = 60 * 60, count = 5)
	Object sendSmsCodeForVerify(
			@ParamCtrl(regex = REGEX.accessToken) @JsonRpcParam(value = "access_token") String access_token,
			@ParamCtrl(regex = REGEX.phone) @JsonRpcParam(value = "phone") String phone,
			@ParamCtrl(regex = REGEX.imageCode, must = false) @JsonRpcParam(value = "image_code") String image_code,
			@ParamCtrl(regex = REGEX.emailToken, must = false) @JsonRpcParam(value = "flow_token") String flow_token);

	@AccessControl(enable = true, method = true, paramIndex = { 2 }, cycle = 60 * 60, count = 10)
	Object checkSmsCodeForResetPhone(
			@ParamCtrl(regex = REGEX.accessToken) @JsonRpcParam(value = "access_token") String access_token,
			@ParamCtrl(regex = REGEX.phone) @JsonRpcParam(value = "phone") String phone,
			@ParamCtrl(regex = REGEX.smscode) @JsonRpcParam(value = "sms_code") String sms_code,
			@ParamCtrl(regex = REGEX.emailToken) @JsonRpcParam(value = "flow_token") String flow_token);

	@AccessControl(enable = true, method = true, paramIndex = { 2 }, cycle = 60 * 60, count = 5)
	Object sendSmsCodeForResetPhone(
			@ParamCtrl(regex = REGEX.accessToken) @JsonRpcParam(value = "access_token") String access_token,
			@ParamCtrl(regex = REGEX.phone) @JsonRpcParam(value = "phone") String phone,
			@ParamCtrl(regex = REGEX.phone, must = false) @JsonRpcParam(value = "old_phone") String old_phone,
			@ParamCtrl(regex = REGEX.emailToken, must = false) @JsonRpcParam(value = "flow_token") String flow_token);

	@AccessControl(enable = true, method = true, paramIndex = { 2 }, cycle = 60 * 60, count = 10)
	Object checkSmsCodeForSetPhone(
			@ParamCtrl(regex = REGEX.accessToken) @JsonRpcParam(value = "access_token") String access_token,
			@ParamCtrl(regex = REGEX.phone) @JsonRpcParam(value = "phone") String phone,
			@ParamCtrl(regex = REGEX.smscode) @JsonRpcParam(value = "sms_code") String sms_code,
			@ParamCtrl(regex = REGEX.emailToken) @JsonRpcParam(value = "flow_token") String flow_token);

	// #########################################################

	/**
	 * 发送激活邮件，用户用邮箱注册后，user的借口会调用该接口发送一个带有激活url的激活邮件。
	 * 
	 * @param caller
	 *            调用者的ID，
	 * @param auth_token
	 *            调用者的验证信息，
	 * @param email
	 *            邮箱地址
	 * @param user_id
	 *            用户名
	 * @return 成功则返回‘success’ ，失败则返回失败说明
	 */
	@AccessControl(enable = true, method = true, cycle = 60 * 60 * 24, count = 1)
	Object sendActiveEmail(
			@ParamCtrl(regex = REGEX.accessToken, must = true) @JsonRpcParam(value = "access_token") String access_token,
			@ParamCtrl(regex = REGEX.email) @JsonRpcParam(value = "email") String email,
			@ParamCtrl(regex = REGEX.userID) @JsonRpcParam(value = "user_id") String user_id);

	/**
	 * 校验激活邮件URL
	 * 
	 * @param caller
	 *            调用者的ID，
	 * @param auth_token
	 *            调用者的验证信息，
	 * @param email
	 *            邮箱
	 * @param user_id
	 *            用户名
	 * @param token
	 *            URL里携带的一个token属性的值（暂定为20位的随机字符串）
	 * @return 成功则返回‘success’ ，失败则返回失败说明
	 */
	@AccessControl(enable = true, method = true, cycle = 60 * 60 * 24, count = 1)
	Object checkActiveEmail(
			@ParamCtrl(regex = REGEX.accessToken, must = true) @JsonRpcParam(value = "access_token") String access_token,
			@ParamCtrl(regex = REGEX.email) @JsonRpcParam(value = "email") String email,
			@ParamCtrl(regex = REGEX.userID) @JsonRpcParam(value = "user_id") String user_id,
			@ParamCtrl(regex = REGEX.emailToken) @JsonRpcParam(value = "token") String token);
}
