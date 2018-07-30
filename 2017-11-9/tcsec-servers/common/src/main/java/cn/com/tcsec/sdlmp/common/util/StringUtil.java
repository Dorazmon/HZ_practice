package cn.com.tcsec.sdlmp.common.util;

public final class StringUtil {

	private StringUtil() {
		super();
	}

	public static String extractEffectiveCode(String code) {
		return code.replaceAll(" ", "").replaceAll("\t", "").replaceAll("\n\n", "\n");
	}

	// String phone = user.get("phone");
	// if (phone != null) {
	// user.put("phone", phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
	// }
	//
	// String email = user.get("email");
	// if (email != null) {
	// user.put("email",
	// email.replaceAll("(\\w)\\w+(\\w)@(\\w+\\.){1,3}\\w+", "$1*****$2") + "@" +
	// email.split("@")[1]);
	// }
}
