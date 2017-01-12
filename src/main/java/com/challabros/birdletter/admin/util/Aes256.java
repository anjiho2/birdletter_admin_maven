package com.challabros.birdletter.admin.util;

import sun.misc.BASE64Encoder;
import sun.misc.BASE64Decoder;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import java.io.*;
import java.security.NoSuchAlgorithmException;

public class Aes256 {
	@SuppressWarnings("unused")
	public static SecretKeySpec getKeySpec() throws IOException, NoSuchAlgorithmException {
		String iv = "sofo123456789012";
		
		SecretKeySpec keyspec;
		Cipher cipher;

		keyspec = new SecretKeySpec(iv.getBytes(), "AES");

		try {
			cipher = Cipher.getInstance("AES");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return keyspec;
	}

	public static String encrypt(String text) throws Exception {
		SecretKeySpec spec = getKeySpec();
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, spec);
		BASE64Encoder enc = new BASE64Encoder();

		return enc.encode(cipher.doFinal(text.getBytes())).toString();
	}

	public static String decrypt(String text) throws Exception {
		SecretKeySpec spec = getKeySpec();
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, spec);
		BASE64Decoder dec = new BASE64Decoder();
		return new String(cipher.doFinal(dec.decodeBuffer(text)));
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		String mode = "encrypt";
		String text = "{result:true}";
		String encText = "{'"+"phone_number"+"':"+"'"+"01062585228,"+"'"+"uuid"+"':"+"'"+"956C3187-3580-42E1-ABAD-D21E8AF8A866"+"'}";
		String decText = "b+mP5oZmP6BYxVzF";
		encText = Aes256.encrypt(encText);
		System.out.println("enc::" + encText);
		decText = Aes256.decrypt("YeRiVTxg/jiXOLgCNKCO39SR6JREZhBkzlFjFKWlAwycycJjiNhSD8Uvm7QABBTCi2K1noHjzKKULhF37IgFYA==");
		System.out.println("dec::" + decText);
	}
}