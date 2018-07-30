package cn.com.tcsec.sdlmp.notify.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import cn.com.tcsec.sdlmp.notify.Application;
import cn.com.tcsec.sdlmp.notify.entity.Message;
import cn.com.tcsec.sdlmp.notify.mapper.NotifyMapper;
import postSMS.ApiException;
import postSMS.DefaultUninetsClient;
import postSMS.UninetsClient;
import postSMS.request.UninetsFcSmsNumSendRequest;
import postSMS.response.UninetsFcSmsNumSendResponse;

@Component
public class SmsSender {
	private final static Logger logger = LoggerFactory.getLogger(Application.class);

	@Autowired
	NotifyMapper notifyMapper;

	@Value("${sms.api.url}")
	private String url;

	@Value("${sms.api.appkey}")
	private String appkey;

	@Value("${sms.api.secret}")
	private String secret;

	public void send(String subject, String contact, String message) {
		UninetsClient client = new DefaultUninetsClient(url, appkey, secret);
		UninetsFcSmsNumSendRequest req = new UninetsFcSmsNumSendRequest();
		req.setExtend("8888");
		req.setSmsType("normal");
		req.setSmsFreeSignName("孝道科技");// 签名
		req.setSmsParamString("{\"pin\":\"" + message + "\"}");// 参数,
		req.setRecNum(contact);
		req.setSmsTemplateCode("\tSMS_105140");// 模板id
		try {
			client.execute(req);
		} catch (ApiException e) {
			logger.error("ApiException", e);
		}
	}

	public void sendForOauthRegister(String oauth_id, String phone, String sms_code) throws Exception {
		UninetsClient client = new DefaultUninetsClient(url, appkey, secret);
		UninetsFcSmsNumSendRequest req = new UninetsFcSmsNumSendRequest();
		req.setExtend("8888");
		req.setSmsType("normal");
		req.setSmsFreeSignName("孝道科技");// 签名
		req.setSmsParamString("{\"pin\":\"" + sms_code + "\"}");// 参数,
		req.setRecNum(phone);
		req.setSmsTemplateCode("\tSMS_105140");// 模板id
		try {
			UninetsFcSmsNumSendResponse response = client.execute(req);
			// TODO
			logger.info(response.getBody()); // {"alibaba_aliqin_fc_sms_num_send_response":{"result":{"err_code":"0","model":"1^1","success":true,"msg":"成功"},"request_id":"BAFA486DB53882495577A4BD479CECDE"}}
		} catch (ApiException e) {
			logger.error("ApiException,发送验证码错误：{}", phone + ":" + oauth_id, e);
		}

		Message msg = new Message();
		msg.setContact(phone);
		msg.setMessage(sms_code);
		msg.setSubject("oauth注册");
		msg.setType("phone");
		msg.setUser_id(oauth_id);

		if (notifyMapper.insertMessage(msg) != 1) {
			throw new Exception("notifyMapper.insertMessage 插入失败:" + msg.toString());
		}

	}

	public void sendForVerify(String user_id, String phone, String sms_code) throws Exception {
		UninetsClient client = new DefaultUninetsClient(url, appkey, secret);
		UninetsFcSmsNumSendRequest req = new UninetsFcSmsNumSendRequest();
		req.setExtend("8888");
		req.setSmsType("normal");
		req.setSmsFreeSignName("孝道科技");// 签名
		req.setSmsParamString("{\"pin\":\"" + sms_code + "\"}");// 参数,
		req.setRecNum(phone);
		req.setSmsTemplateCode("\tSMS_105140");// 模板id
		try {
			UninetsFcSmsNumSendResponse response = client.execute(req);
			// TODO
			logger.info(response.getBody()); // {"alibaba_aliqin_fc_sms_num_send_response":{"result":{"err_code":"0","model":"1^1","success":true,"msg":"成功"},"request_id":"BAFA486DB53882495577A4BD479CECDE"}}
		} catch (ApiException e) {
			logger.error("ApiException,发送验证码错误：{}", phone + ":" + user_id, e);
		}

		Message msg = new Message();
		msg.setContact(phone);
		msg.setMessage(sms_code);
		msg.setSubject("验证信息");
		msg.setType("phone");
		msg.setUser_id(user_id);

		if (notifyMapper.insertMessage(msg) != 1) {
			throw new Exception("notifyMapper.insertMessage 插入失败:" + msg.toString());
		}
	}

	public void sendForRegister(String user_id, String phone, String sms_code) throws Exception {
		UninetsClient client = new DefaultUninetsClient(url, appkey, secret);
		UninetsFcSmsNumSendRequest req = new UninetsFcSmsNumSendRequest();
		req.setExtend("8888");
		req.setSmsType("normal");
		req.setSmsFreeSignName("孝道科技");// 签名
		req.setSmsParamString("{\"pin\":\"" + sms_code + "\"}");// 参数,
		req.setRecNum(phone);
		req.setSmsTemplateCode("\tSMS_105140");// 模板id
		try {
			UninetsFcSmsNumSendResponse response = client.execute(req);
			// TODO
			logger.info(response.getBody()); // {"alibaba_aliqin_fc_sms_num_send_response":{"result":{"err_code":"0","model":"1^1","success":true,"msg":"成功"},"request_id":"BAFA486DB53882495577A4BD479CECDE"}}
		} catch (ApiException e) {
			logger.error("ApiException,发送验证码错误：{}", phone + ":" + user_id, e);
		}

		Message msg = new Message();
		msg.setContact(phone);
		msg.setMessage(sms_code);
		msg.setSubject("新增手机");
		msg.setType("phone");
		msg.setUser_id(user_id);

		if (notifyMapper.insertMessage(msg) != 1) {
			throw new Exception("notifyMapper.insertMessage 插入失败:" + msg.toString());
		}

	}
}
