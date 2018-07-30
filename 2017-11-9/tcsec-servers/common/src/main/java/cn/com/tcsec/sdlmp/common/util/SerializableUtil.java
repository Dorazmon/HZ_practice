package cn.com.tcsec.sdlmp.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializableUtil {

	private SerializableUtil() {
		super();
	}

	public static byte[] serialize(Object obj) throws Exception {
		byte[] bytes = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(obj);
		bytes = baos.toByteArray();
		baos.close();
		oos.close();
		return bytes;
	}

	public static Object deSerialize(byte[] bytes) throws Exception {
		Object obj = null;
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		ObjectInputStream ois = new ObjectInputStream(bais);
		obj = ois.readObject();
		return obj;
	}
}
