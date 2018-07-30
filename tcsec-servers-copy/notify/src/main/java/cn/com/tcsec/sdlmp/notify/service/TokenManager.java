package cn.com.tcsec.sdlmp.notify.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import cn.com.tcsec.sdlmp.notify.entity.Message;

public class TokenManager {
	private static String baseString = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static Long smsLimitTime = 10L * 60L * 1000L;
	private static Long emailLimitTime = 24L * 60L * 60L * 1000L;

	public static String generateSmsToken() {
		Random random = new Random();
		int number = 0;
		for (int i = 0; i < 6; i++) {
			number = number * 10 + random.nextInt(11);
		}
		return String.valueOf(number);
	}

	public static String generateEmailToken() {
		Random random = new Random();
		int number = 0;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 20; i++) {
			number = random.nextInt(baseString.length());
			sb.append(baseString.charAt(number));
		}
		return sb.toString();
	}

	private static boolean compareTime(Long time, String createTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime expTime = LocalDateTime.parse(createTime, formatter).plusSeconds(time);
		return expTime.isAfter(LocalDateTime.now());
	}

	public static boolean checkSmsToken(String contact, String token, Message msg) {
		return (compareTime(smsLimitTime, msg.getGmt_create()) && token.equals(msg.getMessage())
				&& contact.equals(msg.getContact())) ? true : false;
	}

	public static boolean checkEmailToken(String contact, String token, Message msg) {
		return (compareTime(emailLimitTime, msg.getGmt_create()) && token.equals(msg.getMessage())
				&& contact.equals(msg.getContact())) ? true : false;
	}
}
