package com.challabros.birdletter.admin.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * <PRE>
 * 1. ClassName : LawAesCryto
 * 2. FileName  : LawAesCryto.java
 * 3. Package   : com.softforum.anysign.util
 * 4. 작성자    : ANJIUN
 * 5. 작성일    : 2014. 1. 21.
 * </PRE>
 */
public class LawAesCryto {
	   
	// Key String (hex String) Key Size 는 16byte 유지
	public static String aesKey = "abab4329b232c132";
	
	/**
	* Hex String >> ByteArray 변환
	* @param String hex
	* @return byte[]
	*/
	public static byte[] hexToByte(String hex) {
		if (hex == null || hex.length() == 0) {
			return null;
		}

		byte[] byteArray = new byte[hex.length() / 2];
		
		for (int i = 0; i < byteArray.length; i++) {
			byteArray[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}
		
		return byteArray;
	}

	/**
	* ByteArray >> Hex String 변환
	* @param byte[] byteArray
	* @return String
	*/
	public static String byteToHex(byte[] byteArray) {
		
		if (byteArray == null || byteArray.length == 0) {
			return null;
		}

		StringBuffer strbuf = new StringBuffer(byteArray.length * 2);
		String hexNum = "";
			
		for (int x = 0; x < byteArray.length; x++) {
			hexNum = "0" + Integer.toHexString(0xff & byteArray[x]);
			strbuf.append(hexNum.substring(hexNum.length() - 2));
		}
		
	    return strbuf.toString();
	} 

	/**
	* AES 암호화
	* @param String msg (문자열)
	* @return String
	*/
	public static String encrypt(String msg) throws Exception {

		SecretKeySpec skeySpec = new SecretKeySpec(aesKey.getBytes(), "AES");

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

		byte[] encrypmsg = cipher.doFinal(msg.getBytes());
		
		return byteToHex(encrypmsg);
	}

	/**
	* AES 복호화
	* @param String encrypmsg (암호화된 문자열)
	* @return String
	*/
	public static String decrypt(String encrypmsg) throws Exception {

		SecretKeySpec skeySpec = new SecretKeySpec(aesKey.getBytes(), "AES");
	
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        
        byte[] original = cipher.doFinal(hexToByte(encrypmsg));
        String originalmsg = new String(original);
        
        return originalmsg;
	}
}
