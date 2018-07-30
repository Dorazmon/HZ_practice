package cn.com.tcsec.sdlmp.common.util;

import java.util.Random;

public class TokenManager {
	private static String baseString = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	private TokenManager() {
		super();
	}

	public static String generateSmsToken() {
		Random random = new Random();
		int number = 0;
		int randomNumber;
		for (int i = 0; i < 6; i++) {
			if (i == 0) {
				while ((randomNumber = random.nextInt(10)) == 0) {
				}
			} else {
				randomNumber = random.nextInt(10);
			}
			number = number * 10 + randomNumber;
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
}
