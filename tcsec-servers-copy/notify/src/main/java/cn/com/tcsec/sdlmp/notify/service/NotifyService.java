package cn.com.tcsec.sdlmp.notify.service;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.com.tcsec.sdlmp.common.client.RestClient;
import cn.com.tcsec.sdlmp.common.exception.ErrorContent;
import cn.com.tcsec.sdlmp.notify.entity.Message;
import cn.com.tcsec.sdlmp.notify.mapper.NotifyMapper;
import cn.com.tcsec.sdlmp.util.TokenManager;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
@MapperScan("cn.com.tcsec.sdlmp.notify.mapper")
public class NotifyService {
	private static final Logger logger = LoggerFactory.getLogger(NotifyService.class);

	@Autowired
	NotifyMapper notifyMapper;

	@Autowired
	EmailSender emailSender;

	@Autowired
	SmsSender smsSender;

	@Autowired
	JedisPool jedisPool;

	@Autowired
	RestClient restClient;

	public Object sendSmsCode(String phone, String user_id) {
		String smsCode = TokenManager.generateSmsToken();
		smsSender.send(null, phone, smsCode);

		Message msg = new Message("phone", user_id, phone, "验证", smsCode);
		try {
			notifyMapper.insertMessage(msg);
		} catch (Exception e) {
			logger.error("Exception	", e);
			return ErrorContent.SERVER_ERROR;
		}
		return ErrorContent.OK;
	}

	public Object checkSmsCode(String phone, String user_id, String token) {
		Message msg;
		try {
			msg = notifyMapper.selectMessageByContact(phone);
		} catch (Exception e) {
			logger.error("Exception	", e);
			return ErrorContent.SERVER_ERROR;
		}
		if (TokenManager.checkSmsToken(phone, token, msg)) {
			return ErrorContent.OK;
		}
		return ErrorContent.SYSTEM_REJECT;
	}

	public Object sendActiveEmail(String email, String user_id) {
		String emailToken = TokenManager.generateEmailToken();
		emailSender.send(user_id, email, emailToken);
		Message msg = new Message("email", user_id, email, "验证", emailToken);
		try {
			notifyMapper.insertMessage(msg);
		} catch (Exception e) {
			logger.error("Exception	", e);
			return ErrorContent.SERVER_ERROR;
		}
		return ErrorContent.OK;
	}

	public Object checkActiveEmail(String email, String user_id, String token) {
		Message msg;
		try {
			msg = notifyMapper.selectMessageByContact(email);
		} catch (Exception e) {
			logger.error("Exception	", e);
			return ErrorContent.SERVER_ERROR;
		}
		if (TokenManager.checkEmailToken(email, token, msg)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("user_id", user_id);
			map.put("state", "1");
			Object obj = null;
			try {
				obj = restClient.send("updateUser", map);
			} catch (Throwable e) {
				logger.error("Throwable  ", e);
				return ErrorContent.SERVER_ERROR;
			}
			if (obj == null) {
				return ErrorContent.SERVER_ERROR;
			}
			return ErrorContent.OK;
		}
		return ErrorContent.OK;
	}

	public Object checkSmsCodeForResetPasswd(String phone, String token) {
		Message msg;
		try {
			msg = notifyMapper.selectMessageByContact(phone);
		} catch (Exception e) {
			logger.error("Exception	", e);
			return ErrorContent.SERVER_ERROR;
		}
		if (TokenManager.checkSmsToken(phone, token, msg)) {
			String emailToken = TokenManager.generateEmailToken();
			Jedis jedis = jedisPool.getResource();
			jedis.select(5);
			jedis.set(phone, emailToken);
			jedis.expire(phone, 60 * 10);
			jedis.close();
			return new HashMap<String, String>() {
				private static final long serialVersionUID = -4557256829733781574L;
				{
					put("token_passwd", emailToken);
					put("phone", phone);
				}
			};
		}
		return ErrorContent.SYSTEM_REJECT;
	}
}
