package cn.com.tcsec.sdlmp.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class Sha256 {
	private Sha256() {
		super();
	}

	public static String encrypt(String strSrc) {
		MessageDigest md = null;
		String strDes = null;

		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(strSrc.getBytes());
			strDes = bytes2Hex(md.digest());
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
		return strDes;
	}

	private static String bytes2Hex(byte[] bts) {
		String des = "";
		String tmp = null;
		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des;
	}

}
