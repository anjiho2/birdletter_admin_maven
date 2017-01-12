package com.challabros.birdletter.admin.util;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class Seed {

	public static String encVal(String text, String key) throws Exception{
		SeedCipher seed = new SeedCipher();
		String encryptText = Base64.encode(seed.encrypt(text, key.getBytes(), "UTF-8"));
		
		return encryptText;
	}
	
	public static String decVal(String text, String key) throws Exception{
		SeedCipher seed = new SeedCipher();
		byte[] encryptbytes = Base64.decode(text);
		String decryptText = seed.decryptAsString(encryptbytes, key.getBytes(), "UTF-8");
		
		return decryptText;
	}
}
