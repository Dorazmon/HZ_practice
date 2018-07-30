package cn.com.tcsec.sdlmp.notify.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;

import cn.com.tcsec.sdlmp.common.api.NotifyServerAPI;
import cn.com.tcsec.sdlmp.notify.service.NotifyService;
import redis.clients.jedis.JedisPool;

@Service
@AutoJsonRpcServiceImpl
public class NotifyServerAPIImpl implements NotifyServerAPI {
	@Autowired
	NotifyService notifyService;

	@Autowired
	JedisPool jedisPool;

	@Override
	public Object sendSmsCode(String token, String phone, String user_id, String code) {
		return notifyService.sendSmsCode(token, phone, user_id, code);
	}

	@Override
	public Object checkSmsCode(String access_token, String phone, String user_id, String token) {
		return notifyService.checkSmsCode(phone, user_id, token);
	}

	@Override
	public Object sendActiveEmail(String access_token, String email, String user_id) {
		return null;
	}

	@Override
	public Object checkActiveEmail(String access_token, String email, String user_id, String token) {
		return null;
	}

	@Override
	public Object checkSmsCodeForResetPasswd(String access_token, String phone, String token) {
		return notifyService.checkSmsCodeForResetPasswd(phone, token);
	}

	// #########################################################

	@Override
	public Object sendSmsCodeForRegister(String phone, String image_code, String flow_token) {
		return notifyService.sendSmsCodeForRegister(phone, image_code, flow_token);
	}

	@Override
	public Object checkSmsCodeForRegister(String phone, String sms_code, String flow_token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object sendSmsCodeForOauth(String oauth_id, String phone, String image_code, String flow_token) {
		return notifyService.sendSmsCodeForOauth(oauth_id, phone, image_code, flow_token);
	}

	@Override
	public Object checkSmsCodeForOauth(String oauth_id, String phone, String sms_code, String flow_token) {
		return notifyService.checkSmsCodeForOauth(oauth_id, phone, sms_code, flow_token);
	}

	@Override
	public Object sendSmsCodeForVerify(String access_token, String phone, String image_code, String flow_token) {
		return notifyService.sendSmsCodeForVerify(access_token.split(":")[0], phone, image_code, flow_token);
	}

	@Override
	public Object checkSmsCodeForResetPhone(String access_token, String phone, String sms_code, String flow_token) {
		return notifyService.checkSmsCodeForResetPhone(phone, sms_code, flow_token);
	}

	@Override
	public Object sendSmsCodeForResetPhone(String access_token, String phone, String old_phone, String flow_token) {
		return notifyService.sendSmsCodeForResetPhone(access_token.split(":")[0], phone, old_phone, flow_token);
	}

	@Override
	public Object checkSmsCodeForSetPhone(String access_token, String phone, String sms_code, String flow_token) {
		return notifyService.checkSmsCodeForSetPhone(access_token.split(":")[0], phone, sms_code, flow_token);
	}

	// #########################################################
}
