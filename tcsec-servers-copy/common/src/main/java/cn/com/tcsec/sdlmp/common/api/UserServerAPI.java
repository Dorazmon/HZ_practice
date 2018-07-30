package cn.com.tcsec.sdlmp.common.api;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;

import cn.com.tcsec.sdlmp.common.annotation.AccessControl;
import cn.com.tcsec.sdlmp.common.annotation.ParamCtrl;
import cn.com.tcsec.sdlmp.common.param.REGEX;

@JsonRpcService("/user")
public interface UserServerAPI {

	/**
	 * 查找一个用户
	 * 
	 * @param caller
	 *            调用者的ID，（登录后为user_id,登录前输入空）
	 * @param auth_token
	 *            调用者的验证信息，（登录后为auth_token,登录前输入空）
	 * @param flag
	 *            (0=用户名登录，1=邮箱登录，2=手机登录，3=第三方账号登录)
	 * @param contact
	 *            需要查找的用户
	 * @return 如果存在该用户则返回原contact，如果不存在则返回null;
	 */
	@AccessControl()
	Object getUser(@ParamCtrl(regex = REGEX.contact) @JsonRpcParam(value = "contact") String contact);

	/**
	 * 查找一个用户
	 * 
	 * @param caller
	 *            调用者的ID，（登录后为user_id,登录前输入空）
	 * @param auth_token
	 *            调用者的验证信息，（登录后为auth_token,登录前输入空）
	 * @param flag
	 *            (0=用户名登录，1=邮箱登录，2=手机登录，3=第三方账号登录)
	 * @param contact
	 *            需要查找的用户
	 * @return 如果存在该用户则返回该用户，如果不存在则返回null;
	 */
	@AccessControl()
	Object getUserInfo(@ParamCtrl(regex = REGEX.accessToken) @JsonRpcParam(value = "access_token") String access_token,
			@ParamCtrl(regex = REGEX.userID) @JsonRpcParam(value = "user_id") String user_id);

	/**
	 * 添加一个用户
	 * 
	 * @param flag
	 *            (1=邮箱登录，2=手机登录)
	 * @param user_id
	 *            该用户的用户名
	 * @param contact
	 *            该用户的联系方式
	 * @param passwd
	 *            该用户的密码
	 * @param smscode
	 *            手机注册时输入6位数字的token
	 * @return 添加成功 返回‘success’，添加失败返回错误说明
	 */
	@AccessControl()
	Object addUser(@ParamCtrl(regex = REGEX.userID) @JsonRpcParam(value = "user_id") String user_id,
			@ParamCtrl(regex = REGEX.phone) @JsonRpcParam(value = "phone") String phone,
			@ParamCtrl(regex = REGEX.passwd) @JsonRpcParam(value = "passwd") String passwd,
			@ParamCtrl(regex = REGEX.smscode) @JsonRpcParam(value = "smscode") String smscode);

	/**
	 * 用户登录 根据第三方账号判断是否是第一次登陆，如果是则生成tcsec账号，如果不是则在用户表里找到记录并返回
	 * 
	 * @param flag
	 *            （0=用户名登录，1=邮箱登录，2=手机登录，3=第三方账号登录）
	 * @param contact
	 *            （用户名，邮箱，手机，第三方账号）
	 * @param passwd
	 *            （密码，密码，密码，第三方账号平台代号：csdn、osch）
	 * @return 登录成功返回用户权限，登录失败返回空
	 */
	@AccessControl()
	Object logon(@ParamCtrl(regex = REGEX.contact) @JsonRpcParam(value = "contact") String contact,
			@ParamCtrl(regex = REGEX.passwd) @JsonRpcParam(value = "passwd") String passwd,
			@ParamCtrl(regex = REGEX.imageCode, must = false) @JsonRpcParam(value = "code") String code);

	@AccessControl()
	Object resetPasswd(@ParamCtrl(regex = REGEX.phone) @JsonRpcParam(value = "phone") String phone,
			@ParamCtrl(regex = REGEX.passwd) @JsonRpcParam(value = "passwd") String passwd,
			@ParamCtrl(regex = REGEX.emailToken) @JsonRpcParam(value = "token") String token);

	/**
	 * 用户登录 根据第三方账号判断是否是第一次登陆，如果是则生成tcsec账号，如果不是则在用户表里找到记录并返回
	 * 
	 * @param flag
	 *            （0=用户名登录，1=邮箱登录，2=手机登录，3=第三方账号登录）
	 * @param contact
	 *            （用户名，邮箱，手机，第三方账号）
	 * @param passwd
	 *            （密码，密码，密码，第三方账号平台代号：csdn、osch）
	 * @return 登录成功返回用户权限，登录失败返回空
	 */
	@AccessControl()
	Object logonOauth(@ParamCtrl(regex = REGEX.oauthChannel) @JsonRpcParam(value = "channel") String channel,
			@ParamCtrl(regex = REGEX.oauthToken) @JsonRpcParam(value = "oauth_token") String oauth_token);

	/**
	 * 更新用户信息
	 * 
	 * @param caller
	 *            调用者的ID，
	 * @param auth_token
	 *            调用者的验证信息，
	 * @param user_id
	 *            需要更新的用户的用户名
	 * @param phone
	 *            手机号
	 * @param email
	 *            邮箱
	 * @param passwd
	 *            密码
	 * @param name
	 *            昵称
	 * @param company_id
	 *            公司id
	 * @param role
	 *            角色 0-ROOT,1-公司管理员,2-公司普通员工，3-开发者个人
	 * @param state
	 *            状态 1-正常，2-邮箱注册未激活
	 * @return 更新成功返回‘success’，添加失败返回错误说明
	 */
	@AccessControl()
	Object setUser(@ParamCtrl(regex = REGEX.accessToken) @JsonRpcParam(value = "access_token") String access_token,
			@ParamCtrl(regex = REGEX.userID) @JsonRpcParam(value = "user_id") String user_id,
			@ParamCtrl(regex = REGEX.phone, must = false) @JsonRpcParam(value = "phone") String phone,
			@ParamCtrl(regex = REGEX.email, must = false) @JsonRpcParam(value = "email") String email,
			@ParamCtrl(regex = REGEX.passwd, must = false) @JsonRpcParam(value = "passwd") String passwd,
			@ParamCtrl(regex = REGEX.userName, must = false) @JsonRpcParam(value = "name") String name,
			@ParamCtrl(regex = REGEX.tableID, must = false) @JsonRpcParam(value = "company_id") String company_id,
			@ParamCtrl(regex = REGEX.role, must = false) @JsonRpcParam(value = "role") String role,
			@ParamCtrl(regex = REGEX.userState, must = false) @JsonRpcParam(value = "state") String state);

	/**
	 * 根据公司id或user_id来获取该公司的成员列表（目前只能根据公司ID来取，如需按用户名查找，随时可添加）
	 * 
	 * @param caller
	 *            调用者的ID，
	 * @param auth_token
	 *            调用者的验证信息，
	 * @param user_id
	 *            用户id
	 * @param company_id
	 *            公司id
	 * @return 成功返回list，添加失败返回错误说明
	 */
	@AccessControl()
	Object getCompanyUserList(
			@ParamCtrl(regex = REGEX.accessToken) @JsonRpcParam(value = "access_token") String access_token,
			@ParamCtrl(regex = REGEX.userID) @JsonRpcParam(value = "user_id") String user_id,
			@ParamCtrl(regex = REGEX.tableID, must = false) @JsonRpcParam(value = "company_id") String company_id);

	/**
	 * 获取权限
	 * 
	 * @param caller
	 *            调用者的ID，
	 * @param auth_token
	 *            调用者的验证信息，
	 * @param auth
	 *            该用户的权限值 （0-root，1-管理员用户，2-普通用户， 3-个人开发者）
	 * @return 成功返回权限列表，失败返回错误说明
	 */
	@AccessControl()
	Object getAuth(@ParamCtrl(regex = REGEX.accessToken) @JsonRpcParam(value = "access_token") String access_token,
			@ParamCtrl(regex = REGEX.role) @JsonRpcParam(value = "role") String role);

	/**
	 * 用户设置自己所属的公司，如果系统公司表已经存在该公司则直接修改用户表的公司字段，如果不存在则添加该公司。
	 * 
	 * @param caller
	 *            调用者的ID，
	 * @param auth_token
	 *            调用者的验证信息，
	 * @param user_id
	 *            用户名
	 * @param name
	 *            公司名(2-180个字节的中英数字组成)
	 * @param url
	 *            公司主页，非必选
	 * @param desc
	 *            公司描述，非必选
	 * @return 成功返回'success'，失败返回错误说明
	 */
	@AccessControl()
	Object setCompany(@ParamCtrl(regex = REGEX.accessToken) @JsonRpcParam(value = "access_token") String access_token,
			@ParamCtrl(regex = REGEX.userID) @JsonRpcParam(value = "user_id") String user_id,
			@ParamCtrl(regex = REGEX.companyName, ckeckSqlInject = true) @JsonRpcParam(value = "name") String name,
			@ParamCtrl(regex = REGEX.companyUrl) @JsonRpcParam(value = "url") String url,
			@ParamCtrl(regex = REGEX.companyDesc, ckeckSqlInject = true) @JsonRpcParam(value = "desc") String desc);
}
