/**
 * 문자열 디코딩 처리 <br>
 * 모든 method는 static <br>
 * History <br>
 * Create	2001/02/22
 * @version	1.00, 2001/02/22
 **/
package com.challabros.birdletter.admin.util;

public class Decoder {

	/**
	 * 문자열 디코딩 처리
	 * 
	 * @param s
	 *            디코딩할 문자열
	 * @return 디코딩된 문자열
	 **/
	public static String decode(String s) {
		byte abyte0[] = null;
		try {
			StringBuffer stringbuffer = new StringBuffer();

			for (int i = 0; i < s.length(); i += 2) {
				stringbuffer.append((char) Integer.parseInt(s.substring(i, i + 2), 16));
			}

			String s1 = stringbuffer.toString();
			abyte0 = s1.getBytes("8859_1");
		} catch (Exception exception) {
			// LogFile.out("Decoder.decode() : Exception");
			// LogFile.out("MESSAGE : " + exception.getMessage());
		}
		return new String(abyte0);
	}
}