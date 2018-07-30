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
	public Object sendSmsCode(String access_token, String phone, String user_id) {
		return notifyService.sendSmsCode(phone, user_id);
	}

	@Override
	public Object checkSmsCode(String access_token, String phone, String user_id, String token) {
		return notifyService.checkSmsCode(phone, user_id, token);
	}

	@Override
	public Object sendActiveEmail(String access_token, String email, String user_id) {
		return notifyService.sendActiveEmail(email, user_id);
	}

	@Override
	public Object checkActiveEmail(String access_token, String email, String user_id, String token) {
		return notifyService.checkActiveEmail(email, user_id, token);
	}

	@Override
	public Object checkSmsCodeForResetPasswd(String access_token, String phone, String token) {
		return notifyService.checkSmsCodeForResetPasswd(phone, token);
	}
}
