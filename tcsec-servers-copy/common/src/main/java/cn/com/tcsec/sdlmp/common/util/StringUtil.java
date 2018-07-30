package cn.com.tcsec.sdlmp.common.util;

public final class StringUtil {

	private StringUtil() {
		super();
	}

	public static String extractEffectiveCode(String code) {
		return code.replaceAll(" ", "").replaceAll("\t", "").replaceAll("\n\n", "\n");
	}
}
