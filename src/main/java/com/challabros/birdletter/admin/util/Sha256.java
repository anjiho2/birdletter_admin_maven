/**
 * 0. Project  : AnySign Notice & Faq
 *
 * 1. FileName : Sha256.java
 * 2. Package  : com.softforum.anysign.util
 * 3. Comment  : SHA-256
 * 4. AUTHOR   : SOFTFORUM
 * 5. @Version : v1.0
 * Copyright(c) 2014 SOFTFORUM All Rights Reserved
 *------------------------------------------------------------------------------
 *                  변         경         사         항                       
 *------------------------------------------------------------------------------
 *    DATE      DEV                      DESCRIPTION                        
 * ----------  ------  --------------------------------------------------------- 
 * 2014. 1. 14. ANJIUN  신규생성                                     
 *------------------------------------------------------------------------------
 */
package com.challabros.birdletter.admin.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <PRE>
 * 1. ClassName : Sha256
 * 2. FileName  : Sha256.java
 * 3. Package   : com.softforum.anysign.util
 * 4. 작성자    : ANJIUN
 * 5. 작성일    : 2014. 1. 14.
 * </PRE>
 */
public class Sha256 {

    private static final String ALGORITHM = "SHA-256";

    public static byte[] getHash(byte[] input) {
        try {
			MessageDigest md = MessageDigest.getInstance(ALGORITHM);
			return md.digest(input);
		} catch (NoSuchAlgorithmException e) {
			
			e.printStackTrace();
			return null;
		}
    }
 
    public static byte[] getHash(InputStream input) throws IOException {
        try {
			MessageDigest md = MessageDigest.getInstance(ALGORITHM);
			int read = -1;
			byte[] buffer = new byte[1024];
			while ((read = input.read(buffer)) != -1) {
				md.update(buffer, 0, read);
			}
			return md.digest();
		} catch (NoSuchAlgorithmException e) {
		
			e.printStackTrace();
			return null;
		}
    }
 
    public static byte[] getHash(File file) throws IOException {
		byte[] hash = null;
    	BufferedInputStream bis = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(file));
			hash = getHash(bis);
		} finally {
			if (bis != null) try { bis.close(); } catch(IOException ie) {}
		}
		return hash;
    }

    public static String getHashHexString(byte[] input) {
		byte[] hash = getHash(input);
		StringBuffer sb = new StringBuffer(); 
		for (int i = 0; i < hash.length; i++) { 
			 sb.append(Integer.toString((hash[i] & 0xf0) >> 4, 16)); 
			 sb.append(Integer.toString(hash[i] & 0x0f, 16));
		} 
		return sb.toString();
    }
 
    public static String getHashHexString(String input) {
		return getHashHexString(input.getBytes());
    }
 
    public static String getHashHexString(String input, String charsetName) throws UnsupportedEncodingException {
		return getHashHexString(input.getBytes(charsetName));
    }
 
    public static String getHashHexString(InputStream input) throws IOException {
        byte[] hash = getHash(input);
        StringBuffer sb = new StringBuffer(hash.length * 2); 
        for (int i = 0; i < hash.length; i++) { 
			 sb.append(Integer.toString((hash[i] & 0xf0) >> 4, 16)); 
			 sb.append(Integer.toString(hash[i] & 0x0f, 16));
		} 
    	return sb.toString();
    }
 
    public static String getHashHexString(File file) throws IOException {
		byte[] hash = getHash(file);
		StringBuffer sb = new StringBuffer(hash.length * 2);
		for (int i = 0; i < hash.length; i++) { 
			 sb.append(Integer.toString((hash[i] & 0xf0) >> 4, 16)); 
			 sb.append(Integer.toString(hash[i] & 0x0f, 16));
		}
    	return sb.toString();
    }
}