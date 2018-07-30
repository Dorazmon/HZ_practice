package cn.com.tcsec.sdlmp.user.api;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;

import cn.com.tcsec.sdlmp.common.api.UserServerAPI;
import cn.com.tcsec.sdlmp.common.exception.ErrorContent;
import cn.com.tcsec.sdlmp.common.param.REGEX;
import cn.com.tcsec.sdlmp.user.service.UserService;
import redis.clients.jedis.JedisPool;

@Service
@AutoJsonRpcServiceImpl
public class UserServerAPIImpl implements UserServerAPI {
	@Autowired
	UserService userService;

	@Autowired
	JedisPool jedisPool;

	@Override
	public Object addUser(String user_id, String contact, String passwd, String smscode) {
		return userService.addUser('2', user_id, contact, passwd, smscode);
	}

	@Override
	public Object getUser(String contact) {
		char flag;

		if (Pattern.compile(REGEX.phone).matcher(contact).matches()) {
			flag = '2';
		} else if (Pattern.compile(REGEX.email).matcher(contact).matches()) {
			flag = '1';
		} else if (Pattern.compile(REGEX.userID).matcher(contact).matches()) {
			flag = '0';
		} else {
			return ErrorContent.METHOD_PARAMS_INVALID;
		}
		Object obj = userService.getUser(flag, contact);

		return obj;
	}

	@Override
	public Object getUserInfo(String access_token, String user_id) {
		if (!user_id.equals(access_token.split(":")[0])) {
			return ErrorContent.SYSTEM_REJECT;
		}
		return userService.getUserInfo(user_id);
	}

	@Override
	public Object logon(String contact, String passwd, String code) {
		char flag;

		if (Pattern.compile(REGEX.phone).matcher(contact).matches()) {
			flag = '1';
		} else if (Pattern.compile(REGEX.userID).matcher(contact).matches()) {
			flag = '0';
		} else {
			return ErrorContent.METHOD_PARAMS_INVALID;
		}

		return userService.logon(flag, contact, passwd, code);
	}

	@Override
	public Object logonOauth(String channel, String oauth_token) {
		return userService.logonOauth(channel, oauth_token);
	}

	@Override
	public Object setUser(String access_token, String user_id, String phone, String email, String passwd, String name,
			String company_id, String role, String state) {
		if (!user_id.equals(access_token.split(":")[0])) {
			return ErrorContent.SYSTEM_REJECT;
		}
		return userService.setUser(user_id, phone, email, passwd, name, company_id, role, state);
	}

	@Override
	public Object getCompanyUserList(String access_token, String user_id, String company_id) {
		if (!user_id.equals(access_token.split(":")[0])) {
			return ErrorContent.SYSTEM_REJECT;
		}
		return userService.getCompanyUserList(company_id, user_id);
	}

	@Override
	public Object getAuth(String access_token, String role) {
		return userService.getAuth(role);
	}

	@Override
	public Object setCompany(String access_token, String user_id, String name, String url, String desc) {
		if (!user_id.equals(access_token.split(":")[0])) {
			return ErrorContent.SYSTEM_REJECT;
		}
		return userService.setCompany(user_id, name, url, desc);
	}

	@Override
	public Object resetPasswd(String phone, String passwd, String token) {
		return userService.resetPasswd(phone, passwd, token);
	}

	@Override
	public Object setUserPhone(String user_id, String phone) {
		return userService.setUserPhone(user_id, phone);
	}

	@Override
	public Object getUserIdByPhone(String phone) {
		return userService.getUserIdByPhone(phone);
	}
}
