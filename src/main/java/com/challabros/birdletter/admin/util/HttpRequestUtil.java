/**
 * 0. Project  : AnySign Notice & Faq
 *
 * 1. FileName : HttpRequestUtil.java
 * 2. Package  : com.softforum.anysign.util
 * 3. Comment  : 문자열 디코딩 처리 <br>
 *               모든 method는 static <br>
 * 4. AUTHOR   : SOFTFORUM
 * 5. @Version : v1.0
 * Copyright(c) 2014 SOFTFORUM All Rights Reserved
 *------------------------------------------------------------------------------
 *                  변         경         사         항
 *------------------------------------------------------------------------------
 *    DATE      DEV                      DESCRIPTION
 * ----------  ------  ---------------------------------------------------------
 * 2014. 1. 3.  ANJIUN  신규생성
 *------------------------------------------------------------------------------
 */
package com.challabros.birdletter.admin.util;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * <PRE>
 * 1. ClassName : HttpRequestUtil
 * 2. FileName  : HttpRequestUtil.java
 * 3. Package   : com.softforum.anysign.util
 * 4. 작성자    : ANJIUN
 * 5. 작성일    : 2014. 1. 23.
 * </PRE>
 */
public class HttpRequestUtil{
	
	/**
	* 문자열 디코딩 처리
	* @param	디코딩할 문자열
	* @return	디코딩된 문자열
	**/
	   
    private URL targetURL; 			// HTTP 프로토콜을 사용하여 연결할 URL
    private DataOutputStream out;  	// POST 방식으로 데이터를 전송할 때 사용되는 출력 스트림
    
    /**
     * 생성자
     * @param targetURL	목적URL
     */
    public HttpRequestUtil(URL targetURL) {
       this.targetURL = targetURL;
    }
    
    
    //전송메시지 입력
    public InputStream sendGetMessage() throws IOException {
       return sendGetMessage(null);
    }
    
    public InputStream sendGetMessage(Properties params)     throws IOException {
       String paramString = "";
       
       if (params != null) {
          paramString = "?"+encodeString(params);
       }
       URL url = new URL(targetURL.toExternalForm() + paramString);
       
       URLConnection conn = url.openConnection();
       conn.setUseCaches(false);
       return conn.getInputStream();
    }
    
    public InputStream sendPostMessage() throws IOException {
       return sendPostMessage("");
    }
    
    
    /**
     * POST 전송용 파라메트 설정
     * @param params 파라메터
     * @return 입력 스트링
     * @throws IOException
     */
    public InputStream sendPostMessage(Properties params)     throws IOException {
       String paramString = "";
       
       if (params != null) {
          paramString = encodeString(params);
       }
       return sendPostMessage(paramString);
    }
 
    private InputStream sendPostMessage(String encodedParamString)     throws IOException {
    	URLConnection conn = targetURL.openConnection();
       
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		out = null;
       
		try {
			out = new DataOutputStream(conn.getOutputStream());
			out.writeBytes(encodedParamString);
			out.flush();
		} finally {
			if (out != null) out.close();
		}
       
		return conn.getInputStream();
    }
    
    @SuppressWarnings({ "deprecation", "rawtypes" })
	public static String encodeString(Properties params) {
       StringBuffer sb = new StringBuffer(256);
       Enumeration names = params.propertyNames();
       
       while (names.hasMoreElements()) {
          String name = (String) names.nextElement();
          String value = params.getProperty(name);
          sb.append(URLEncoder.encode(name) + "=" + URLEncoder.encode(value) );
          
          if (names.hasMoreElements()) sb.append("&");
       }
       return sb.toString();
    }
}

