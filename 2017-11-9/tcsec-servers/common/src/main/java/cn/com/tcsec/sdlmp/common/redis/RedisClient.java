package cn.com.tcsec.sdlmp.common.redis;

import org.springframework.beans.factory.annotation.Autowired;

import cn.com.tcsec.sdlmp.common.util.TokenManager;
import cn.com.tcsec.sdlmp.common.util.VerifyCode;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisClient {

	@Autowired
	JedisPool jedisPool;

	public String imageCodeGet(String flowFlag, String phone, String token, String code) throws Exception {
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

	public String setForSendSms(String flowFlag, String phone, String flow_token, String image_code) throws Exception {
		Jedis jedis = jedisPool.getResource();
		jedis.select(7);
		String key = flowFlag + phone + flow_token;
		String str = VerifyCode.get(6);
		String emailToken = TokenManager.generateEmailToken();
		jedis.set(phone, str.split(":")[0] + ":" + emailToken);
		if (jedis.ttl(phone) == -1) {
			jedis.expire(phone, 10 * 60);
		}
		jedis.close();
		return null;
	}

	public boolean verifyForSendSms(String flowFlag, String phone, String flow_token, String image_code) {
		Jedis jedis = jedisPool.getResource();
		jedis.select(7);
		String key = flowFlag + phone + flow_token;
		String localValue = jedis.get(key);
		if (localValue != null && localValue.equalsIgnoreCase(image_code)) {
			jedis.del(key);
			jedis.close();
			return true;
		} else {
			jedis.close();
			return false;
		}
	}
}
