package cn.com.tcsec.sdlmp.notify.service;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.com.tcsec.sdlmp.common.client.RestClient;
import cn.com.tcsec.sdlmp.common.exception.ErrorContent;
import cn.com.tcsec.sdlmp.common.util.VerifyCode;
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

	public Object sendSmsCode(String token, String phone, String user_id, String code) {
		String result = null;
		try {
			result = imageCodeGet(phone, token, code);
		} catch (Exception e1) {
			logger.error("Exception	", e1);
			return ErrorContent.SERVER_ERROR;
		}

		if (result != null) {
			Map<String, String> map = new HashMap<>();
			map.put("token", result.split(":")[0]);
			map.put("code", result.split(":")[1]);
			return map;
		}

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

	private String imageCodeGet(String phone, String token, String code) throws Exception {
		Jedis jedis = jedisPool.getResource();
		jedis.select(6);
		String localCode = jedis.get(phone);
		if (localCode != null && localCode.split(":")[0].equalsIgnoreCase(code)
				&& localCode.split(":")[1].equals(token)) {
			jedis.del(phone);
			jedis.close();
			return null;
		} else {
			String str = VerifyCode.get(6);
			String emailToken = TokenManager.generateEmailToken();
			jedis.set(phone, str.split(":")[0] + ":" + emailToken);
			if (jedis.ttl(phone) == -1) {
				jedis.expire(phone, 10 * 60);
			}
			jedis.close();
			return emailToken + ":" + str.split(":")[1];
		}
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
			// Object obj = null;
			// try {
			// obj = restClient.send("updateUser", map);
			// } catch (Throwable e) {
			// logger.error("Throwable ", e);
			// return ErrorContent.SERVER_ERROR;
			// }
			// if (obj == null) {
			// return ErrorContent.SERVER_ERROR;
			// }
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

	public Object sendSmsCodeForRegister(String phone, String image_code, String flow_token) {

		return null;
	}

	public Object sendSmsCodeForOauth(String oauth_id, String phone, String image_code, String flow_token) {
		Jedis jedis = jedisPool.getResource();
		jedis.select(7);
		String key = "O0" + oauth_id;
		String local_flow_token = jedis.get(key);
		if (local_flow_token != null && local_flow_token.equals(flow_token)) {
			Map<String, Object> map = new HashMap<>();
			map.put("contact", phone);

			Object resp = null;
			try {
				resp = restClient.send("getUser", map, Object.class);
			} catch (Throwable e) {
				logger.error("Exception	restClient.send getUser:{}", map.toString(), e);
				return ErrorContent.SERVER_ERROR;
			}

			if (resp != null && resp instanceof Collection) {
				String smsCode = TokenManager.generateSmsToken();
				try {
					smsSender.sendForOauthRegister(oauth_id, phone, smsCode);
				} catch (Exception e) {
					jedis.close();
					logger.error("Exception	", e);
					return ErrorContent.SERVER_ERROR;
				}
				String new_key = "O1" + oauth_id + phone + flow_token;
				jedis.set(new_key, smsCode);
				jedis.expire(new_key, 60 * 10);
				jedis.close();
				return ErrorContent.OK;
			} else if (resp != null && resp.equals(phone)) {
				jedis.close();
				return ErrorContent.DUPLICATE_TARGET;
			} else {
				logger.error("restClient.send getUser:{},resp:{}", map.toString(), resp);
				jedis.close();
				return ErrorContent.SERVER_ERROR;
			}
		} else {
			jedis.close();
			return ErrorContent.SYSTEM_REJECT;
		}
	}

	public Object checkSmsCodeForOauth(String oauth_id, String phone, String sms_code, String flow_token) {
		Jedis jedis = jedisPool.getResource();
		jedis.select(7);
		String key = "O1" + oauth_id + phone + flow_token;
		String local_sms_code = jedis.get(key);
		if (local_sms_code != null && local_sms_code.equals(sms_code)) {
			jedis.del(key);
			jedis.close();
			Map<String, Object> map = new HashMap<>();
			map.put("user_id", oauth_id);
			map.put("phone", phone);

			Object err = null;
			try {
				err = restClient.send("setUserPhone", map, Object.class);
			} catch (Throwable e) {
				logger.error("Exception	restClient.send setUserPhone:{}", map.toString(), e);
				return ErrorContent.SERVER_ERROR;
			}
			@SuppressWarnings("unchecked")
			LinkedHashMap<String, Object> linkedHashMap = (LinkedHashMap<String, Object>) err;
			if (err == null || !(err instanceof LinkedHashMap<?, ?>) || ((int) linkedHashMap.get("code")) != 0) {
				logger.error("restClient.send setUserPhone:{},code:{}", map.toString(), err.toString());
				return ErrorContent.SERVER_ERROR;
			}

			map.clear();
			map.put("contact", oauth_id);

			try {
				return restClient.send("logon", map, Object.class);
			} catch (Throwable e) {
				logger.error("Exception	restClient.send logon:{}", map.toString(), e);
				return ErrorContent.SERVER_ERROR;
			}
		} else {
			jedis.close();
			return ErrorContent.SYSTEM_REJECT;
		}
	}

	public Object sendSmsCodeForVerify(String user_id, String phone, String image_code, String flow_token) {
		if (image_code == null || flow_token == null) {
			try {
				return getImageCodeandFlowToken(null, phone);
			} catch (IOException e) {
				logger.error("getImageCodeandFlowToken:", e);
				return ErrorContent.SERVER_ERROR;
			}
		}

		Map<String, String> map = null;
		try {
			map = verifyImageCodeandFlowToken(phone, image_code, flow_token);
		} catch (IOException e1) {
			logger.error("getImageCodeandFlowToken", e1);
			return ErrorContent.SERVER_ERROR;
		}
		if (map != null) {
			return map;
		}

		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("phone", phone);

		Object resp = null;
		try {
			resp = restClient.send("getUserIdByPhone", paramMap, Object.class);
		} catch (Throwable e) {
			logger.error("Exception	restClient.send getUser:{}", paramMap.toString(), e);
			return ErrorContent.SERVER_ERROR;
		}
		if (resp != null && resp instanceof Collection) {
			return ErrorContent.SYSTEM_REJECT;
		} else if (resp != null && resp instanceof String && user_id.equals(resp)) {
			String smsCode = TokenManager.generateSmsToken();
			try {
				smsSender.sendForVerify(resp.toString(), phone, smsCode);
			} catch (Exception e) {
				logger.error("Exception	", e);
				return ErrorContent.SERVER_ERROR;
			}
			String new_key = "V1" + phone + flow_token;
			Jedis jedis = jedisPool.getResource();
			jedis.set(new_key, smsCode);
			jedis.expire(new_key, 60 * 10);

			jedis.close();
			return ErrorContent.OK;
		} else {
			logger.error("restClient.send getUser:{},resp:{}", paramMap.toString(), resp);
			return ErrorContent.SERVER_ERROR;
		}
	}

	private Map<String, String> getImageCodeandFlowToken(Jedis jedis, String phone) throws IOException {
		String imageCode = VerifyCode.get(6);
		String flowToken = TokenManager.generateEmailToken();

		if (jedis == null) {
			jedis = jedisPool.getResource();
		}
		jedis.select(7);
		jedis.set("V0" + phone, imageCode.split(":")[0] + ":" + flowToken);
		jedis.expire("V0" + phone, 60 * 10);
		jedis.close();

		Map<String, String> map = new HashMap<>();
		map.put("image_code", imageCode.split(":")[1]);
		map.put("flow_token", flowToken);

		return map;
	}

	private Map<String, String> verifyImageCodeandFlowToken(String phone, String image_code, String flow_token)
			throws IOException {
		Jedis jedis = jedisPool.getResource();
		jedis.select(7);
		String str = jedis.get("V0" + phone);
		if (str != null && str.split(":")[0].equals(image_code) && str.split(":")[1].equals(flow_token)) {
			jedis.close();
			return null;
		}

		return getImageCodeandFlowToken(jedis, phone);
	}

	public Object checkSmsCodeForResetPhone(String phone, String sms_code, String flow_token) {
		Jedis jedis = jedisPool.getResource();
		jedis.select(7);
		String key = "V1" + phone + flow_token;
		String local_sms_code = jedis.get(key);
		if (local_sms_code != null && local_sms_code.equals(sms_code)) {
			jedis.del(key);

			jedis.incr("P0" + phone + flow_token);
			jedis.expire("P0" + phone, 60 * 10);

			jedis.close();
			return ErrorContent.OK;
		} else {
			jedis.close();
			return ErrorContent.SYSTEM_REJECT;
		}
	}

	public Object sendSmsCodeForResetPhone(String user_id, String phone, String old_phone, String flow_token) {
		Jedis jedis = jedisPool.getResource();
		jedis.select(7);
		String key = "P0" + old_phone + flow_token;
		Long count = jedis.incr(key);
		if (count.intValue() >= 2 && count.intValue() <= 5) {
			Map<String, Object> map = new HashMap<>();
			map.put("contact", phone);

			Object resp = null;
			try {
				resp = restClient.send("getUser", map, Object.class);
			} catch (Throwable e) {
				logger.error("Exception	restClient.send getUser:{}", map.toString(), e);
				return ErrorContent.SERVER_ERROR;
			}

			if (resp != null && resp instanceof Collection) {
				String smsCode = TokenManager.generateSmsToken();
				try {
					smsSender.sendForRegister(user_id, phone, smsCode);
				} catch (Exception e) {
					jedis.close();
					logger.error("Exception	", e);
					return ErrorContent.SERVER_ERROR;
				}
				String new_key = "P1" + phone + flow_token;
				jedis.set(new_key, smsCode);
				jedis.expire(new_key, 60 * 10);
				jedis.close();
				return ErrorContent.OK;
			} else if (resp != null && resp.equals(phone)) {
				jedis.close();
				return ErrorContent.DUPLICATE_TARGET;
			} else {
				logger.error("restClient.send getUser:{},resp:{}", map.toString(), resp);
				jedis.close();
				return ErrorContent.SERVER_ERROR;
			}
		} else {
			if (count.intValue() == 1) {
				jedis.del(key);
			}
			jedis.close();
			return ErrorContent.SYSTEM_REJECT;
		}
	}

	public Object checkSmsCodeForSetPhone(String user_id, String phone, String sms_code, String flow_token) {
		Jedis jedis = jedisPool.getResource();
		jedis.select(7);
		String key = "P1" + phone + flow_token;
		String local_sms_code = jedis.get(key);
		if (local_sms_code != null && local_sms_code.equals(sms_code)) {
			jedis.del(key);
			jedis.close();
			Map<String, Object> map = new HashMap<>();
			map.put("user_id", user_id);
			map.put("phone", phone);

			Object err = null;
			try {
				err = restClient.send("setUserPhone", map, Object.class);
			} catch (Throwable e) {
				logger.error("Exception	restClient.send setUserPhone:{}", map.toString(), e);
				return ErrorContent.SERVER_ERROR;
			}
			@SuppressWarnings("unchecked")
			LinkedHashMap<String, Object> linkedHashMap = (LinkedHashMap<String, Object>) err;
			if (err == null || !(err instanceof LinkedHashMap<?, ?>) || ((int) linkedHashMap.get("code")) != 0) {
				logger.error("restClient.send setUserPhone:{},code:{}", map.toString(), err.toString());
				return ErrorContent.SERVER_ERROR;
			}

			return ErrorContent.OK;
		} else {
			jedis.close();
			return ErrorContent.SYSTEM_REJECT;
		}
	}
}
