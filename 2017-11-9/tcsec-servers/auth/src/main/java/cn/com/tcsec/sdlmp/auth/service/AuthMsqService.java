package cn.com.tcsec.sdlmp.auth.service;

import javax.jms.JMSException;
import javax.jms.Message;

import org.apache.activemq.command.ActiveMQObjectMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import cn.com.tcsec.sdlmp.auth.analyzer.ReportAnalyzer;
import cn.com.tcsec.sdlmp.common.entity.ScheduledTask;

@Component
public class AuthMsqService {
	private static final Logger logger = LoggerFactory.getLogger(AuthMsqService.class);

	@Autowired
	ReportAnalyzer reportAnalyzer;

	@JmsListener(destination = "auth.docker.addReport", concurrency = "20")
	public void onMessage(Message message) {
		if (!(message instanceof ActiveMQObjectMessage)) {
			logger.error("Message 不是Object类型 ");
			return;
		}

		ActiveMQObjectMessage activeMQObjectMessage = (ActiveMQObjectMessage) message;
		ScheduledTask scheduledTask = null;
		try {
			scheduledTask = (ScheduledTask) activeMQObjectMessage.getObject();
		} catch (JMSException e) {
			logger.error("Message 内容格式错误 ", e);
			return;
		}
		if (scheduledTask == null) {
			logger.error("获取 scheduledTask 为空 ！");
			return;
		}

		reportAnalyzer.analyze(scheduledTask);
	}
}
