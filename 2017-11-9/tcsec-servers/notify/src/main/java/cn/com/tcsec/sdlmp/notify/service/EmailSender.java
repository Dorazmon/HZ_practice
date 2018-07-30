package cn.com.tcsec.sdlmp.notify.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Component
public class EmailSender implements Sender {
	private final static Logger logger = LoggerFactory.getLogger(EmailSender.class);

	@Autowired
	private JavaMailSender mailSender;

	private TemplateEngine templateEngine = getTemplateEngine();

	@Value("${mail.fromMail.addr}")
	private String from;

	@Value("${mail.activate.addr}")
	private String activateAddr;

	@Override
	public void send(String name, String contact, String message) {
		MimeMessage msg = mailSender.createMimeMessage();
		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(msg, true);
			helper.setFrom(from);
			helper.setTo(contact);
			helper.setSubject("激活Tcsec账户");

			Context context = new Context();
			context.setVariable("name", name);
			context.setVariable("message",
					activateAddr + "?contact=" + contact + "&token=" + message + "&name=" + name);
			String emailContent = templateEngine.process("emailTemplate", context);
			logger.info(emailContent);
			helper.setText(emailContent, true);

			mailSender.send(msg);
		} catch (MessagingException e1) {
			e1.printStackTrace();
		}
	}

	private TemplateEngine getTemplateEngine() {
		TemplateEngine templateEngine = new TemplateEngine();
		ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
		resolver.setPrefix("templates/");
		resolver.setSuffix(".html");
		resolver.setCacheable(false);
		resolver.setCharacterEncoding("UTF-8");
		templateEngine.setTemplateResolver(resolver);
		return templateEngine;
	}
}
