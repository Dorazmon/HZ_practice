package cn.com.tcsec.sdlmp.user.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.googlecode.jsonrpc4j.ErrorResolver.JsonError;

import cn.com.tcsec.sdlmp.common.client.RestClient;
import cn.com.tcsec.sdlmp.common.exception.ErrorContent;
import cn.com.tcsec.sdlmp.common.util.HttpUtil;
import cn.com.tcsec.sdlmp.common.util.Sha256;
import cn.com.tcsec.sdlmp.common.util.VerifyCode;
import cn.com.tcsec.sdlmp.user.entity.Company;
import cn.com.tcsec.sdlmp.user.entity.User;
import cn.com.tcsec.sdlmp.user.mapper.UserMapper;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
@MapperScan("cn.com.tcsec.sdlmp.user.mapper")
public class UserService implements InitializingBean {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	UserMapper userMapper;

	@Autowired
	JedisPool jedisPool;

	@Autowired
	RestClient restClient;

	public Object getUser(char contactFlag, String contact) {
		User user = null;
		switch (contactFlag) {
		case '0':
			try {
				user = userMapper.selectUserByUserId(contact);
			} catch (Exception e) {
				logger.error("Exception	", e);
				return ErrorContent.SERVER_ERROR;
			}
			break;
		case '1':
			try {
				user = userMapper.selectUserByEmail(contact);
			} catch (Exception e) {
				logger.error("Exception	", e);
				return ErrorContent.SERVER_ERROR;
			}
			break;
		case '2':
			try {
				user = userMapper.selectUserByPhone(contact);
			} catch (Exception e) {
				logger.error("Exception	", e);
				return ErrorContent.SERVER_ERROR;
			}
			break;
		default:
			break;
		}

		if (user == null) {
			return new ArrayList<>();
		} else {
			return contact;
		}
	}

	public Object getUserInfo(String contact) {
		Map<String, String> user = null;
		try {
			user = userMapper.selectUser(contact);
		} catch (Exception e) {
			logger.error("Exception	", e);
			return ErrorContent.SERVER_ERROR;
		}

		String phone = user.get("phone");
		user.put("phone", phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));

		String email = user.get("email");
		user.put("email",
				email.replaceAll("(\\w)\\w+(\\w)@(\\w+\\.){1,3}\\w+", "$1*****$2") + "@" + email.split("@")[1]);

		return user;
	}

	public Object addUser(char flag, String user_id, String contact, String passwd, String smscode) {
		Map<String, Object> map = new HashMap<String, Object>();
		Object response = null;

		switch (flag) {
		case '1':
			try {
				userMapper.insertUser(new User(user_id, null, contact, passwd, "2"));
			} catch (Exception e) {
				logger.error("Exception	", e);
				return ErrorContent.SERVER_ERROR;
			}
			map.put("user_id", user_id);
			map.put("email", contact);
			try {
				response = restClient.send("sendActiveEmail", map);
			} catch (Exception e1) {
				logger.error("Exception	", e1);
				return ErrorContent.SERVER_ERROR;
			} catch (Throwable e1) {
				logger.error("Exception	", e1);
				return ErrorContent.SERVER_ERROR;
			}
			break;
		case '2':
			map.put("user_id", user_id);
			map.put("phone", contact);
			map.put("token", smscode);
			try {
				response = restClient.send("checkSmsCode", map);
			} catch (Exception e1) {
				logger.error("Exception	", e1);
				return ErrorContent.SERVER_ERROR;
			} catch (Throwable e1) {
				logger.error("Exception	", e1);
				return ErrorContent.SERVER_ERROR;
			}
			if ("success".equals(response.toString())) {
				try {
					userMapper.insertUser(new User(user_id, contact, null, passwd, "1"));
				} catch (Exception e) {
					logger.error("Exception	", e);
					return ErrorContent.SERVER_ERROR;
				}
			} else
				return ErrorContent.SERVER_ERROR;
		default:
			break;
		}

		return ErrorContent.OK;

	}

	public Object logon(char flag, String contact, String passwd, String code) {
		User user = null;
		switch (flag) {
		case '0':
			try {
				user = userMapper.selectUserByUserId(contact);
			} catch (Exception e) {
				logger.error("Exception	", e);
				return ErrorContent.SERVER_ERROR;
			}
			break;
		case '1':
			try {
				user = userMapper.selectUserByPhone(contact);
			} catch (Exception e) {
				logger.error("Exception	", e);
				return ErrorContent.SERVER_ERROR;
			}
			break;
		default:
			break;
		}

		if (user != null) {
			Jedis jedis = jedisPool.getResource();
			jedis.select(4);
			String localCode = jedis.get(user.getUser_id());
			if (localCode != null && !localCode.equals(code)) {
				String str = null;
				try {
					str = VerifyCode.get(6);
				} catch (Exception e) {
					logger.error("Exception	", e);
					jedis.close();
					return ErrorContent.SERVER_ERROR;
				}
				jedis.set(user.getUser_id(), str.split(":")[0]);
				jedis.close();
				return new JsonError(-32046, "auth code reject", str.split(":")[1]);
			}

			if (passwd == null || passwd.equals(user.getPasswd())) {
				Object obj = getAuth(user.getRole());

				if (obj != null && obj instanceof List) {
					String authToken = Sha256.encrypt(flag + contact + LocalDateTime.now().toString());
					Map<String, String> userInfoMap = new HashMap<String, String>();
					userInfoMap.put("user_id", user.getUser_id());
					userInfoMap.put("user_name", user.getName());
					userInfoMap.put("access_token", user.getUser_id() + ":" + authToken);
					userInfoMap.put("company", user.getCompany_id());
					userInfoMap.put("user_type", user.getRole());

					Map<String, String> jedisMap = new HashMap<String, String>();
					jedisMap.put(user.getUser_id(), authToken);
					jedis.select(0);
					jedis.hmset("user-accessToken", jedisMap);

					jedis.select(3);
					jedis.del(user.getUser_id());
					jedis.select(4);
					jedis.del(user.getUser_id());

					Map<String, Object> resultMap = new HashMap<String, Object>();
					resultMap.put("results", obj);
					resultMap.put("user", userInfoMap);
					jedis.close();
					return resultMap;
				} else {
					jedis.close();
					return ErrorContent.SERVER_ERROR;
				}
			} else {
				jedis.select(3);
				Long count = jedis.incr(user.getUser_id());
				if (jedis.ttl(user.getUser_id()) == -1) {
					jedis.expire(user.getUser_id(), 60 * 60);
				}

				if (count >= 1) {
					String str = null;
					try {
						str = VerifyCode.get(6);
					} catch (Exception e) {
						logger.error("Exception:", e);
						jedis.close();
						return ErrorContent.SERVER_ERROR;
					}
					jedis.select(4);
					jedis.set(user.getUser_id(), str.split(":")[0]);
					if (jedis.ttl(user.getUser_id()) == -1) {
						jedis.expire(user.getUser_id(), 60 * 60);
					}

					jedis.close();
					return new JsonError(-32049, "failed access", str.split(":")[1]);
				} else {
					jedis.close();
				}
			}
		}
		return ErrorContent.FAILED_ACCESS;
	}

	public Object logonOauth(String channel, String oauth_token) {
		String url = null;
		if ("osch".equals(channel)) {
			url = "https://www.oschina.net/action/openapi/user?access_token=" + oauth_token + "&dataType=json";
		}
		if ("gith".equals(channel)) {
			// TODO
		}

		if (url != null) {
			JsonNode node = null;
			try {
				node = HttpUtil.doGetForOauth(url);
				if (node == null || node.findValue("id") == null || node.findValue("id").isNull()) {
					throw new Exception("返回的 JsonNode 为空, 或者为ERROR字段");
				}
			} catch (Exception e) {
				logger.error("Exception	", e);
				return ErrorContent.SERVER_ERROR;
			}

			String userId = String.valueOf(node.findValue("id").asInt());
			String name = node.findValue("name").asText();
			String email = node.findValue("email").asText();
			userId = channel.substring(0, 4) + userId;
			User user = null;
			try {
				user = userMapper.selectUserByUserId(userId);
			} catch (Exception e) {
				logger.error("Exception	", e);
				return ErrorContent.SERVER_ERROR;
			}

			if (user == null) {
				user = new User();
				user.setUser_id(userId);
				user.setEmail(email);
				user.setName(name);
				user.setRole("1");
				user.setState("1");
				try {
					userMapper.insertUser(user);
				} catch (Exception e) {
					logger.error("Exception	", e);
					return ErrorContent.SERVER_ERROR;
				}
			}

			return logon('0', user.getUser_id(), null, null);
		}

		return ErrorContent.SERVER_ERROR;
	}

	public Object setUser(String user_id, String phone, String email, String passwd, String name, String company_id,
			String role, String state) {
		User user = new User(user_id, phone, email, passwd, name, company_id, role, state);
		int iResult = 0;
		try {
			iResult = userMapper.updateUserByUserId(user);
		} catch (Exception e) {
			logger.error("Exception	", e);
			return ErrorContent.SERVER_ERROR;
		}

		if (iResult == 1) {
			return ErrorContent.OK;
		}

		return ErrorContent.SERVER_ERROR;
	}

	public Object getCompanyUserList(String company_id, String user_id) {
		try {
			List<User> list = userMapper.selectUserByCompany(company_id);
			for (User user : list) {
				if (user_id.equals(user.getUser_id())) {
					return list;
				}
			}
		} catch (Exception e) {
			logger.error("Exception	", e);
		}
		return ErrorContent.SERVER_ERROR;
	}

	@SuppressWarnings("unchecked")
	public Object getAuth(String auth) {
		List<Map<String, Object>> auths;
		try {
			auths = userMapper.selectAuthList(auth);
		} catch (Exception e) {
			logger.error("Exception	", e);
			return ErrorContent.SERVER_ERROR;
		}
		Map<String, Map<String, Object>> authsMap = new HashMap<String, Map<String, Object>>();

		for (Map<String, Object> map : auths) {
			authsMap.put(map.get("menu_id").toString(), map);
		}

		for (int i = auths.size() - 1; i >= 0; i--) {
			String parent = auths.get(i).get("parent").toString();

			if (!parent.equals("0")) {
				Map<String, Object> parentmap = authsMap.get(parent);
				if (parentmap.get("children") == null) {
					parentmap.put("children", new ArrayList<Map<String, Object>>());
				}
				((List<Map<String, Object>>) parentmap.get("children")).add(auths.get(i));
				authsMap.remove(auths.get(i).get("menu_id").toString());
			}
		}

		Iterator<Map<String, Object>> it = auths.iterator();
		while (it.hasNext()) {
			if (!it.next().get("parent").toString().equals("0")) {
				it.remove();
			}
		}

		auths.sort(new Comparator<Map<String, Object>>() {
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				return o1.get("sort").toString().compareTo(o2.get("sort").toString());
			}
		});

		return auths;
	}

	public Object setCompany(String user_id, String name, String url, String desc) {
		// 查找已经存在的公司
		Company company;
		try {
			company = userMapper.selectCompany(name);
		} catch (Exception e) {
			logger.error("Exception	", e);
			return ErrorContent.SERVER_ERROR;
		}

		if (company == null) {
			company = new Company(name, url, desc);
			try {
				userMapper.insertCompany(company);
			} catch (Exception e) {
				logger.error("Exception	", e);
				return ErrorContent.SERVER_ERROR;
			}
		}

		User user = new User();
		user.setCompany_id(company.getId());
		user.setUser_id(user_id);
		try {
			userMapper.updateUserByUserId(user);
		} catch (Exception e) {
			logger.error("Exception	", e);
			return ErrorContent.SERVER_ERROR;
		}

		return ErrorContent.OK;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// Jedis jedis = jedisPool.getResource();
		// Map<String, String> jedisMap = new HashMap<String, String>();
		// jedisMap.put("ForTest",
		// "75ad2d9a0e91cbc68f69b970c2623018007ae0beec51eb757b08edebb41487b1");
		// jedis.hmset("user-accessToken", jedisMap);
		// jedis.close();
	}

	public Object resetPasswd(String phone, String passwd, String token) {
		Jedis jedis = jedisPool.getResource();
		jedis.select(5);
		String redisToken = jedis.get(phone);
		if (redisToken != null) {
			jedis.del(phone);
		}
		jedis.close();
		if (token.equals(redisToken)) {
			try {
				int iRet = userMapper.updateUserPasswdByPhone(phone, passwd);
				if (iRet != 1) {
					logger.error("userMapper.updateUserPasswdByPhone	");
					return ErrorContent.SERVER_ERROR;
				}
			} catch (Exception e) {
				logger.error("userMapper.updateUserPasswdByPhone	", e);
				return ErrorContent.SERVER_ERROR;
			}
			return ErrorContent.OK;
		}
		return ErrorContent.SYSTEM_REJECT;
	}
}
