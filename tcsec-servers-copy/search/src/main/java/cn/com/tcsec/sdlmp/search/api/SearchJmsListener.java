package cn.com.tcsec.sdlmp.search.api;

import javax.jms.JMSException;
import javax.jms.Message;

import org.apache.activemq.command.ActiveMQObjectMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import cn.com.tcsec.sdlmp.common.entity.ScheduledTask;
import cn.com.tcsec.sdlmp.search.service.SearchService;

@Component
public class SearchJmsListener{
	private static final Logger logger = LoggerFactory.getLogger(SearchService.class);
	@Autowired
	SearchService searchService;

	@JmsListener(destination = "search.docker.addReport", concurrency = "3")
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

		searchService.addScanResult(scheduledTask);
	}
}
