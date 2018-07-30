package cn.com.tcsec.sdlmp.notify.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import cn.com.tcsec.sdlmp.notify.Application;
import postSMS.ApiException;
import postSMS.DefaultUninetsClient;
import postSMS.UninetsClient;
import postSMS.request.UninetsFcSmsNumSendRequest;

@Component
public class SmsSender implements Sender {
	private final static Logger logger = LoggerFactory.getLogger(Application.class);

	@Value("${sms.api.url}")
	private String url;

	@Value("${sms.api.appkey}")
	private String appkey;

	@Value("${sms.api.secret}")
	private String secret;

	@Override
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
}
