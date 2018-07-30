package cn.com.tcsec.sdlmp.dockerwrapper.api;

import java.io.IOException;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;

import org.apache.activemq.command.ActiveMQObjectMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.com.tcsec.sdlmp.common.entity.ScheduledTask;
import cn.com.tcsec.sdlmp.dockerwrapper.service.DockerHandlerService;

@Service
public class DockerWrapperMsqService {
	private static final Logger logger = LoggerFactory.getLogger(DockerHandlerService.class);

	@Autowired
	DockerHandlerService dockerHandlerService;

	@Autowired
	JmsMessagingTemplate jmsMessagingTemplate;

	public static void main(String[] args) throws JsonProcessingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String str = "{\"type\":\"issue\",\"check_name\":\"Unserialize\",\"description\":\"CWE-502 反序列化漏洞(Deserialization of Untrusted Data): 用户在进行反序列化操作并且变量可控的地方注入序列化对象，从而实现代码注入、SQL注入等攻击。修复建议：对参数进行过滤，尽量避免使用unserialize()函数。\",\"remediation_points\":50000,\"location\":{\"path\":\"\\/system\\/database\\/DB_cache.php\",\"lines\":{\"begin\":151,\"end\":152}},\"categories\":[\"Style\"]}";
		mapper.readTree(str);
	}

	@JmsListener(destination = "docker.addProject", concurrency = "3")
	public void onMessage(Message msg) {
		if (!(msg instanceof ActiveMQObjectMessage)) {
			logger.error("Message 不是Object类型 ");
			return;
		}
		ActiveMQObjectMessage activeMQObjectMessage = (ActiveMQObjectMessage) msg;

		ScheduledTask scheduledTask = null;
		try {
			scheduledTask = (ScheduledTask) activeMQObjectMessage.getObject();
		} catch (JMSException e1) {
			logger.error("获取 scheduledTask 出错 ！", e1);
			return;
		}
		if (scheduledTask == null) {
			logger.error("获取 scheduledTask 为空 ！");
			return;
		}

		List<String> resultList = null;
		logger.info("开始扫描任务   projectName：{}，url：{}，languag：{}", scheduledTask.getProject_name(),
				scheduledTask.getProject_url(), scheduledTask.getProject_language());

		try {
			resultList = dockerHandlerService.execCodeScan(scheduledTask);
		} catch (JsonParseException e) {
			for (String str : scheduledTask.getList()) {
				logger.error("scaner输出结果:[{}]", str);
			}
			logger.error("json String 解析出错！", e);
			return;
		} catch (Exception e) {
			logger.error("执行扫描出错", e);
			return;
		}

		if (resultList == null) {
			logger.error("结果为空 ！ ");
			return;
		}

		logger.info("扫描结束   projectName：{}，url：{}，languag：{}, resultCount:{}", scheduledTask.getProject_name(),
				scheduledTask.getProject_url(), scheduledTask.getProject_language(), resultList.size());

		scheduledTask.setList(resultList);

		try {
			jmsMessagingTemplate.convertAndSend(scheduledTask.getReturnMsqDestName(), scheduledTask);
		} catch (Exception e) {
			logger.error("返回扫描结果出错 ", e);
			return;
		}
	}
}
