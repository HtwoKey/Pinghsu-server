package com.htwokey.blog.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author hchbo
 */
public class SerializationUtils {

	public static byte[] serialize(Object obj) throws IOException {
		ObjectOutputStream oos = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			return baos.toByteArray();
		} finally {
			if(oos != null) {
				try {
					oos.close();
				} catch (IOException ignored) {}
			}
		}
	}

	
	public static Object deserialize(byte[] bits) throws IOException {
		if(bits == null || bits.length == 0) {
			return null;
		}
		ObjectInputStream ois = null;
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(bits);
			ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (ClassNotFoundException e) {
			return null;
		} finally {
			if(ois != null) {
				try {
					ois.close();
				} catch (IOException ignored) {}
			}
		}
	}

}
