package com.challabros.birdletter.admin.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * <pre>@System : 온라인 채널 / 유틸리티
 * 
 * <pre>@Description : DES3을 지원하는 Utility 클래스.
 */
public class CipherTripleDES {
	static String DESede_CBC_PKCS5Padding = "DESede/CBC/PKCS5Padding";
	static String DESede_ECB_PKCS5Padding = "DESede/ECB/PKCS5Padding";

	/**
	 * Key 생성
	 * 
	 * @param keyValue
	 * @return
	 * @throws Exception
	 */
	public static SecretKey generateKey(String keyValue) throws Exception {
		// DESedeKeySpec keySpec = new DESedeKeySpec(keyValue.getBytes());
		// return
		// SecretKeyFactory.getInstance("DESede").generateSecret(keySpec);
		return new SecretKeySpec(keyValue.getBytes(), "DESede");
	}

	/**
	 * 암호화
	 * 
	 * @param transType
	 *            암호화 방식(CBC/ECB)
	 * @param srcData
	 *            데이터
	 * @param keyValue
	 *            암호화 Key값 (24byte)
	 * @param ivValue
	 *            iv값 (CBC인 경우만 사용, 8byte)
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String transType, String srcData, String keyValue, String ivValue) throws Exception {
		BASE64Encoder encoder = new BASE64Encoder();
		String result = null;

		try {
			SecretKey key = generateKey(keyValue);
			Cipher cipher = Cipher.getInstance(transType);

			if (transType.equals(DESede_ECB_PKCS5Padding)) {
				// ECB모드는 ivKey 사용없음
				cipher.init(Cipher.ENCRYPT_MODE, key);
			} else {
				// CBC모드는 ivKey 사용
				if (null != ivValue && ivValue.length() > 0) {
					cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(ivValue.getBytes()));
				} else {
					cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(new byte[8]));
				}
			}
			byte[] encData = cipher.doFinal(srcData.getBytes());
			result = encoder.encode(encData);
		} catch (Exception e) {
			result = "";
		}
		return result;
	}

	public static String encryptCbc(String srcData, String keyValue, String ivValue) throws Exception {
		return encrypt(DESede_CBC_PKCS5Padding, srcData, keyValue, ivValue);
	}

	public static String encryptEcb(String srcData, String keyValue) throws Exception {
		return encrypt(DESede_ECB_PKCS5Padding, srcData, keyValue, null);
	}

	/**
	 * 복호화
	 * 
	 * @param transType
	 *            암호화 방식(CBC/ECB)
	 * @param srcData
	 *            데이터
	 * @param keyValue
	 *            암호화 Key값 (24byte)
	 * @param ivValue
	 *            iv값 (CBC인 경우만 사용, 8byte)
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String transType, String srcData, String keyValue, String ivValue) throws Exception {
		BASE64Decoder decoder = new BASE64Decoder();
		String result = "";
		try {
			SecretKey key = generateKey(keyValue);
			Cipher cipher = Cipher.getInstance(transType);
			if (transType.equals(DESede_ECB_PKCS5Padding)) {
				// ECB모드는 ivKey 사용없음
				cipher.init(Cipher.DECRYPT_MODE, key);
			} else {
				// CBC모드는 ivKey 사용
				if (null != ivValue && ivValue.length() > 0) {
					cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(ivValue.getBytes()));
				} else {
					cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(new byte[8]));
				}
			}
			byte[] decData = cipher.doFinal(decoder.decodeBuffer(srcData));
			result = new String(decData);
		} catch (Exception e) {
			result = "";
		}
		return result;
	}

	public static String decryptCbc(String srcData, String keyValue, String ivValue) throws Exception {
		return decrypt(DESede_CBC_PKCS5Padding, srcData, keyValue, ivValue);
	}

	public static String decryptEcb(String srcData, String keyValue) throws Exception {
		return decrypt(DESede_ECB_PKCS5Padding, srcData, keyValue, null);
	}
}