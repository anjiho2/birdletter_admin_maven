package com.challabros.birdletter.admin.util;

import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.CharBuffer;
import java.security.SecureRandom;
import java.security.Signature;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Random;
import java.util.SimpleTimeZone;
import java.util.StringTokenizer;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.challabros.birdletter.admin.dto.PagingDto;
import com.google.api.client.googleapis.auth.clientlogin.ClientLogin;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

/**
 * <PRE>
 * 1. ClassName : Util
 * 2. FileName  : Util.java
 * 3. Package   : com.softforum.anysign.util
 * 4. 작성자    : ANJIUN
 * 5. 작성일    : 2014. 3. 3.
 * </PRE>
 */
public class Util {
	
	public final static Locale LOCALE = Locale.KOREA; 
	
	private static SecureRandom random = new SecureRandom();
	
	public static String kscToasc(String str) throws UnsupportedEncodingException{
  		return new String(str.getBytes("KSC5601"),"8859_1");
  	} 

	 public static String getToday(){
		java.util.Date date = new java.util.Date();
		java.text.SimpleDateFormat formatter =new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.KOREA);
		return formatter.format(date);
	}
	
	/**
	 * null이인지 체크함.
	 * @param str 체크할 대상 
	 * @return 체크결과
	 */
	public static boolean isNull(String str){
		return ( (str == null||str.equals("")) ? true : false );
	}
	
	/**
	 * null이 아닌지 체크함.
	 * @param str 체크할 대상 
	 * @return 체크결과
	 */
	public static boolean isNotNull(String str){
		return ( (str == null||str.equals("")) ? false : true );
	}
	
	/**
	 * @설명 : 빈값인경우 체크,널대체값이 있는경우
	 */
	public static String isNullValue(String str2,String NulltoStr) {
		String str = str2;
		if (str == null){
			str = NulltoStr;
		} else if (str.equals("")){ 
			str = NulltoStr;
		}
		return str;
	}
	
	/**
	 * 입력한 문가가 null이면 space로 리턴, 아니면 입력문자열을 돌려줌
	 * @param str 체크할 대상 
	 * @return 체크결과
	 */
	public static String isNullreplace(String str){
		return ( (str == null) ? "" : str );
	}

	/**
	 * String 전화번호를 받아 정규전화번호 형태로 만든다
	 * @param str
	 * @return 
	 */
	public static String checkPhone(String pno) {
		String phone_no = "";
		if (pno.length() == 10){
			phone_no = "0"+pno.substring(0,3) + "0"+pno.substring(3,7)+pno.substring(7,10);
		} else if (pno.length() == 11){
			phone_no = "0"+pno.substring(0,3) + pno.substring(3,8)+pno.substring(8,11);
		}
		return phone_no;
	}	
	
	/**
	 * String 전화번호를 받아 3자리로 나눈다
	 * @param str
	 * @return 
	 */
	public static String[] splitPhone(String pno) {
		String[] phone_no = new String[3];
		if (pno.length() == 10){
			phone_no[0] = pno.substring(0,3);
			phone_no[1] = Integer.toString(Integer.parseInt(pno.substring(3,6)));
			phone_no[2] = pno.substring(6,10);			
		} else if (pno.length() == 11){
			phone_no[0] = pno.substring(0,3);
			phone_no[1] = Integer.toString(Integer.parseInt(pno.substring(3,7)));
			phone_no[2] = pno.substring(7,11);			
		} else if (pno.length() == 12){
			phone_no[0] = pno.substring(1,4);
			phone_no[1] = Integer.toString(Integer.parseInt(pno.substring(4,8)));
			phone_no[2] = pno.substring(8,12);
		} else {
			phone_no[0] = "";
			phone_no[1] = "";
			phone_no[2] = "";	
		}
		return phone_no;
	}
	
	/**
	 * @설명 : 문자열에 대한 %% like 검색어 처리
	 */
	public static String getLike(String str) {
		return "%"+str+"%";
	}

	/** 
	 * @설명 : 문자열에 대한 %% like 검색어 처리
	 */
	public static String getPlus(String str, int k) {
		String rst = "";
		int i = Integer.parseInt(str);
		i = i + k;
		rst = Integer.toString(i);
		return rst;
	}

		
	/**
	 * @설명 : CHECKBOX의 값을 String으로 반환
	 */
	public static String getCkboxVaue(String str){
		String val = "N";
		if (!str.equals("[]")){
			val= "Y";
		}
		return val;
	}

  	/**
  	*  src 문자열을 delim을 기준으로 자른다.
  	*/    
  	public static String[] split(String src, String delim) {
		StringTokenizer st = new StringTokenizer(src, delim);
		int i = 0;
		String arr[] = new String[st.countTokens()];
		while(st.hasMoreTokens()) {
			arr[i++] =st.nextToken();
		}
		return arr;
  	}    

	/**
	*  URL정보에서 페이지명 뽑아가기 
	*/    
	public static String getPagenm(String src, String delim){
		String pgnm = "";
		if (src == null){
			return pgnm;
		} else {
			pgnm = getFileName(src.substring(src.lastIndexOf(delim) + 1, src.length())); 
		   return pgnm;
	 	}
	}    
	
	/**
	* 확장자 뽑아가기
	*/ 
	public static String getFile(String src) {
		int comma = src.lastIndexOf(".");
		src = src.substring(comma,src.length());
		return src;
	}    

	/**
	* 파일명 뽑아가기
	*/ 
	public static String getFileName(String src) {
		int comma = src.lastIndexOf(".");
		src = src.substring(0,comma);
		return src;
	}    
	
	/**
	*  src 문자열의 좌우 공백을 제거후 리턴한다.
	*/  
  	public static String bothTrim(String src) { 
		String str = "";
		str = src.trim();
		return str;
  	}

	/**
	 * 주어진 String의 오른쪽 Space를 모두 없앤다.<BR>
	 *      예) szSource: "  123 " -> Return: "   123"
	 *          szSource: "  " -> Return: ""
	 */
	public static String rightTrim( String szSource )	{
		if ( szSource == null ) return "";
		if ( szSource.length() == 0 ) return szSource;
	
		String szResult = szSource;
		int nLastIndex = szSource.length() - 1;
		while ( nLastIndex >= 0 && szResult.charAt(nLastIndex) == ' ' ) {
			szResult = ( szResult.length() == 1 ? "" : szResult.substring(0, nLastIndex) );
			nLastIndex --;
		}
		return szResult;
	}
	
	
	/**
	 * 주어진 String의 왼쪽 Space를 모두 없앤다.<BR>
	 *      예) szSource: "  123 " -> Return: "123  "
	 *          szSource: "  " -> Return: ""
	 */
	public static String leftTrim( String szSource )	{
		if ( szSource == null ) return "";
		if ( szSource.length() == 0 ) return szSource;
		
		String szResult = szSource;
		int nFirstindex = 0;
		int nLastIndex = szSource.length()-1;
		while ( nLastIndex >= 0 && szResult.charAt(nFirstindex) == ' ' ) {
			szResult = ( szResult.length() == 1 ? "" : szResult.substring(nFirstindex, nLastIndex) );
			nFirstindex++;
		}
		return szResult;
	}

	/**
	 *  문자열의 길이만큼  문자열에 공백 더하기
	 */
	public static String addNull(String str, int i){
		int len = str.length();
		for (int k = len; k <= i;k++){
			str = str + " ";
		}
		return str;
	}

	/**
	 * <PRE>
	 * 1. MethodName : isChkStrLenTerm
	 * 2. ClassName  : Util
	 * 3. Comment    : 
	 * 4. 작성자     : ANJIUN
	 * 5. 작성일     : 2014. 1. 24.
	 * </PRE>
	 *   @return boolean	문자열의 길이 체크(minLen <= length(chkstr) <= maxLen)
	 *   @param chkStr	체크값
	 *   @param minLen	최소길이
	 *   @param maxLen	최대길이(0이면 최소길이만 체크)
	 */
	
	public static boolean isChkStrLenTerm(String chkStr, int minLen, int maxLen) {
		if (minLen == 0) {
			return ((chkStr.length() <= maxLen) ? true : false);
		} else if (maxLen == 0) {
			return ((chkStr.length() >= minLen) ? true : false);
		} else {
			return ((chkStr.length() >= minLen) && (chkStr.length() <= maxLen) ? true : false);
		}
	}

    /**
     * <PRE>
     * 1. MethodName : isChkStrLen( length(chkstr) = strLen)
     * 2. ClassName  : Util
     * 3. Comment    : 문자열의 길이 체크
     * 4. 작성자     : ANJIUN
     * 5. 작성일     : 2014. 1. 24.
     * </PRE>
     *   @return boolean	true/false
     *   @param chkStr	체크값
     *   @param strLen
     */
    
    public static boolean isChkStrLen(String chkStr, int strLen) {
    	return ((chkStr.length()==strLen)? true : false );
    }
    
	/**
	 * <PRE>
	 * 1. MethodName : ranAlphabet
	 * 2. ClassName  : Util
	 * 3. Comment    : 요청한 길이만큼의 랜덤한 알파벳을 리턴한다.
	 * 4. 작성자     : ANJIUN
	 * 5. 작성일     : 2014. 1. 24.
	 * </PRE>
	 *   @return String
	 *   @param flg		B:대문자, S:소문자
	 *   @param len
	 */
	
	public static String ranAlphabet(String flg, int len) {
		String rtnStr = "";

		try {
			if (flg.equals("B")) {
				for (int i = 0; i < len; i++) {
					char al = (char) ((Math.random() * 26) + 65);
					rtnStr += al;
				}
			} else {
				for (int i = 0; i < len; i++) {
					char al = (char) ((Math.random() * 26) + 97);
					rtnStr += al;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rtnStr;
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : traceToString
	 * 2. ClassName  : Util
	 * 3. Comment    : Tracelog를 String으로 변환하기 위한 공통 부분 
	 * 4. 작성자     : ANJIUN
	 * 5. 작성일     : 2014. 1. 23.
	 * </PRE>
	 *   @return String
	 *   @param str
	 *   @param thru
	 */
	
	public static String traceToString(String str, StackTraceElement[] thru){
		StringBuffer sb =  new StringBuffer();
		
		sb.append(str);
		
		for (int i = 0; i < thru.length; i++) {
			sb.append("\n");
			sb.append(thru[i]);
		}
		return sb.toString();
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : traceToString
	 * 2. ClassName  : Util
	 * 3. Comment    : SQLException 에러 Tracelog를 String으로 변환
	 * 4. 작성자     : ANJIUN
	 * 5. 작성일     : 2014. 1. 23.
	 * </PRE>
	 *   @return String
	 *   @param e
	 */
	
	public static String traceToString(SQLException e) {

		StackTraceElement[] thru = e.getStackTrace();
		
		return traceToString(e.toString(), thru);

	}
	
	/**
	 * <PRE>
	 * 1. MethodName : traceToString
	 * 2. ClassName  : Util
	 * 3. Comment    : NullPointerException 에러 Tracelog를 String으로 변환
	 * 4. 작성자     : ANJIUN
	 * 5. 작성일     : 2014. 1. 23.
	 * </PRE>
	 *   @return String
	 *   @param e
	 */
	
	public static String traceToString(NullPointerException e) {
		
		StackTraceElement[] thru = e.getStackTrace();
				
		return traceToString(e.toString(), thru);
		
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : traceToString
	 * 2. ClassName  : Util
	 * 3. Comment    : NumberFormatException 에러 Tracelog를 String으로 변환
	 * 4. 작성자     : ANJIUN
	 * 5. 작성일     : 2014. 1. 23.
	 * </PRE>
	 *   @return String
	 *   @param e
	 */
	
	public static String traceToString(NumberFormatException e) {
		
		StackTraceElement[] thru = e.getStackTrace();
		
		return traceToString(e.toString(), thru);
		
	}

	/**
	 * <PRE>
	 * 1. MethodName : traceToString
	 * 2. ClassName  : Util
	 * 3. Comment    : IndexOutOfBoundsException 에러 Tracelog를 String으로 변환
	 * 4. 작성자     : ANJIUN
	 * 5. 작성일     : 2014. 1. 23.
	 * </PRE>
	 *   @return String
	 *   @param e
	 */
	
	public static String traceToString(IndexOutOfBoundsException e) {
		
		StackTraceElement[] thru = e.getStackTrace();
		
		return traceToString(e.toString(), thru);
		
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : traceToString
	 * 2. ClassName  : Util
	 * 3. Comment    : 기타 Exception 에러 Tracelog를 String으로 변환
	 * 4. 작성자     : ANJIUN
	 * 5. 작성일     : 2014. 1. 23.
	 * </PRE>
	 *   @return String
	 *   @param e
	 */
	
	public static String traceToString(Exception e) {
		
		StackTraceElement[] thru = e.getStackTrace();
		
		return traceToString(e.toString(), thru);
		
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : removeSpecialHtml
	 * 2. ClassName  : Util
	 * 3. Comment    : HTML 특수문자 제거 
	 * 4. 작성자     : ANJIUN
	 * 5. 작성일     : 2014. 3. 3.
	 * </PRE>
	 *   @return String
	 *   @param str
	 */
	
	public static String removeSpecialHtml(String str){
		str = str.replaceAll("&", "&amp;");		// &
		str = str.replaceAll("<", "&lt;");		// <
		str = str.replaceAll(">", "&gt;");		// >
		str = str.replaceAll("\"", "&#34;");	// "
		str = str.replaceAll("\'", "&#39;");	// '
		str = str.replaceAll("%00", null);		// null 문자
		str = str.replaceAll("%", "&#37;");		// %
		
		return str;
	}
	
	/**
	 * 스크립트 필터링
	 * @param value
	 * @return
	 */
	public static String scriptFilterEnc(String value) {
        if (value == null) {
            return null;
        }        
        
        StringBuffer result = new StringBuffer(value.length());
        
        for(int i = 0; i < value.length(); i++) {
            switch (value.charAt(i)) {
            case '<':
                result.append("&lt;");
                break;
            case '>': 
                result.append("&gt;");
                break;
            case '"': 
                result.append("&quot;");
                break;
            case '\'': 
                result.append("&#39;");
                break;
            case '%': 
                result.append("&#37;");
                break;
            case ';': 
                result.append("&#59;");
                break;
            case '(': 
                result.append("&#40;");
                break;
            case ')': 
                result.append("&#41;");
                break;
            case '&': 
                result.append("&amp;");
                break;
            case '+':
                result.append("&#43;");
                break;
            case '#':
                result.append("&#35;");
                break;
            case '/':
                result.append("&#47;");
                break;
            case '.':
                result.append("&#46;");
                break;       
            default:
                result.append(value.charAt(i));
                break;
            }        
        }
        return result.toString();
	}
	
	/**
	 * 필터링된 데이터 복원
	 * @param value
	 * @return
	 */
	public static String scriptFilterDec(String value) {
        if (value == null) {
            return null;
        }   
        
        String rtnVal = value.replaceAll("&amp;", "\\&").replaceAll("&#35;", "\\#").replaceAll("&lt;", "\\<").replaceAll("&gt;", "\\>").replaceAll("&quot;", "\"").replaceAll("&#39;", "\\").replaceAll("&#37;", "\\%").replaceAll("&#40;", "\\(").replaceAll("&#41;", "\\)").replaceAll("&#43;", "\\+").replaceAll("&#47;", "\\/").replaceAll("&#46;", "\\.");
        System.out.println(rtnVal);
        return rtnVal;
	}
	
	/**
	 * 문자열의 최소, 최대 길이 체크
	 * @param value
	 * @param min : 최소 길이
	 * @param max : 최대 길이
	 * @return
	 */
	public static boolean sizeChecker(String value, int min, int max){
		return (value.length() >= min && value.length() <= max);
	}
	
	/**
	 * 문자의 시작과 끝을 지정해서 자르기
	 * @param value
	 * @param start : 문자 시작
	 * @param end : 문자 끝
	 * @return
	 */
	public static String subStrStartEnd(String value, int start, int end) {
		if (value == null || value == "") {
			return null;
		}
		String subValue = value.substring(start, end);
		
		return subValue;
	}
	
	/**
	 * 현재 시각 (yyyy-mm-dd 오전(오후) hh:mm:ss 형식)
	 * @return 
	 */
	public static String returnNowDate() {
		Date today = new Date();
		SimpleDateFormat sdf;
		sdf = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss");
		return sdf.format(today);
	}
	
	public static String returnNow() {
		Date today = new Date();
		SimpleDateFormat sdf;
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(today);
	}
	
	public static String returnNowDateByYyyymmddhhmmss() {
		Date today = new Date();
		SimpleDateFormat sdf;
		sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		return sdf.format(today);
	}
	
	public static String returnNowDateByYYMMDD() {
		Date today = new Date();
		SimpleDateFormat sdf;
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(today);
	}
	
	 public static String returnHourMinuteTime() {
	        Date today = new Date();
	        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
	        return sdf.format(today);
	 }
	 
	 public static String yesterDay(String date) {
		 long chStart = 0;
		 DateFormat df = new SimpleDateFormat("yyyyMMdd");
		 
		 if (!"".equals(date)) {
			 date = date.replaceAll("-", "");
			 try {
				chStart = df.parse(date).getTime();
				chStart -= 86400000;
				
				Date aa = new Date(chStart);
				date = df.format(aa);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		 }
		 return date;
	 }
	 
	 /**
	  * 
	  * @return
	  */
	 public static String returnToDayYYMMDD() {
		 Date today = new Date();
		 SimpleDateFormat sdf;
		 sdf = new SimpleDateFormat("yyyyMMdd");
		 return sdf.format(today);
	}
	 
	 /**
	  * 현재 날짜를 리턴 
	  * @param dateType 날짜형식(ex : yyyy-MM-dd, yyyyMMdd ....)
	  * @return
	  */
	 public static String returnToDate(String dateType) {
		 Date today = new Date();
		 SimpleDateFormat sdf;
		 sdf = new SimpleDateFormat(dateType);
		 return sdf.format(today);
	}
	 
	 public static String returnToDateFromLocale(String dateType, Locale locale) {
		 Date today = new Date();
		 SimpleDateFormat sdf;
		 sdf = new SimpleDateFormat(dateType, locale);
		 return sdf.format(today);
	}
	 
	 /**
	   * 시작일부터 종료일까지 사이의 날짜를 배열에 담아 리턴
	   * ( 시작일과 종료일을 모두 포함한다 )
	   * @param fromDate yyyyMMdd 형식의 시작일
	   * @param toDate yyyyMMdd 형식의 종료일
	   * @return yyyyMMdd 형식의 날짜가 담긴 배열
	   */
	  public static String[] getDiffDays(String fromDate, String toDate) {
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		  Calendar cal = Calendar.getInstance();
		  
		  try {
			  cal.setTime(sdf.parse(fromDate));
		  } catch (Exception e) {
			  e.printStackTrace();
		  }
		  int count = getDiffDayCount(fromDate, toDate);
		  // 시작일부터
		  cal.add(Calendar.DATE, -1);
		  // 데이터 저장
		  List list = new ArrayList();
		  for (int i = 0; i <= count; i++) {
			  cal.add(Calendar.DATE, 1);
			  list.add(sdf.format(cal.getTime()));
		  }
		   String[] result = new String[list.size()];
		   list.toArray(result);
		   return result;
	  }
	  
	  /* 두날짜 사이의 일수를 리턴
	   * @param fromDate yyyyMMdd 형식의 시작일
	   * @param toDate yyyyMMdd 형식의 종료일
	   * @return 두날짜 사이의 일수
	   */
	  public static int getDiffDayCount(String fromDate, String toDate) {
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		  try {
			  return (int) ((sdf.parse(toDate).getTime() - sdf.parse(fromDate).getTime()) / 1000 / 60 / 60 / 24);
		  } catch (Exception e) {
			  e.printStackTrace();
			  return 0;
		  } 
	  }
	
	  /**
	   * 일주일전 날짜 구하기
	   * @return
	   */
	  public static String day7Ago() {  
		  Calendar cal = Calendar.getInstance(new SimpleTimeZone(0x1ee6280, "KST"));
		  cal.add(Calendar.DATE, -6);
		  Date weekago = cal.getTime();
		  SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
		  return formatter.format(weekago);
	  }

	 public static String underBarSplitAndPlus(String value) {
	        String str = "";
	        if(!"".equals(value)) {
	            String splitStr[] = value.split("_");
	            String code = splitStr[0];
	            int codeValue = Integer.parseInt(splitStr[1]);
	            int num = codeValue;
	            int su = 0;
	            int jarisu = 0;
	            int sum = 0;
	            while(num > 0) {
	                su = num & 0xa;
	                sum += su;
	                num /= 10;
	                jarisu++;
	            }
	            if(jarisu == 1)
	                str = (new StringBuilder(String.valueOf(code))).append("_").append("000").append(codeValue + 1).toString();
	            else
	            if(jarisu == 2)
	                str = (new StringBuilder(String.valueOf(code))).append("_").append("00").append(codeValue + 1).toString();
	        }
	        return str;
	    }
	
	 public static String convertToYYYY_MM_DD(String yyyyMMdd) {
		 String yyyy_mm_dd = yyyyMMdd.substring(0,4)+'-'+yyyyMMdd.substring(4,6)+"-"+yyyyMMdd.substring(6);
		 return yyyy_mm_dd;
	 }
	 
	 public static String convertToYYYY_MM(String yyyyMM) {
		 String yyyy_mm = yyyyMM.substring(0,4)+'-'+yyyyMM.substring(4,6);
		 return yyyy_mm;
	 }
	 
	 public static String convertToYYYYMMDD(String yyyy_MM_dd) {
		 String date = "";
		 if (!"".equals(yyyy_MM_dd)) {
			 String[] yyyymmdd = yyyy_MM_dd.split("-");
			 date = yyyymmdd[0]+yyyymmdd[1]+yyyymmdd[2];
		 }
		 return date;
	 }
	 
	/**
	  * 현재 시각의 데이트 객체를 반환한다.
	  *
	  * @return 현재 시각의 데이트 객체.
	  */
	public static Date now() {
		DateTime dt = new DateTime();
		Calendar jdkCal = dt.toCalendar(LOCALE);
		dt = new DateTime(jdkCal);
			return dt.toDate();
	}
	
	
	
	/**
	 * 입력받은 년도의 01월~12월까지 리스트 반환 (2016-01, 2016-02, .....)
	 * @param year
	 * @return
	 */
	public static String[] returnYYYY_MM(String year) {
		List<String> list = new ArrayList<>();
		if (!"".equals(year)) {
			int j = 1;
			for (int i = 0; i < 12; i++) {
				if (j > 9) {
					list.add(year+"-"+j);
				} else {
					list.add(year+"-"+"0"+j);
				}
				j++;
			}
		}
		String[] result = new String[list.size()];
		list.toArray(result);
		return result;
	}
	
	/**
	 * 입력받은 yyyymm으로 1년전 yyyymm 구하기
	 * @param yyyymm
	 * @return
	 */
	public static String getOneYearAgo(String yyyymm) {
		int year = Integer.parseInt(yyyymm.substring(0,4));
		int month = Integer.parseInt(yyyymm.substring(4,6));
		//int date = Integer.parseInt(yyyymmdd.substring(6,8));
		
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, 0);
		
		cal.add(Calendar.YEAR, -1);
		cal.add(Calendar.MONTH, +1);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
		String r = dateFormat.format(cal.getTime());
		return r;
	}
	
	/**
	 * 입력받은 년월 기준으로 전년도 1년 만들기
	 * @param year
	 * @param month
	 * @return
	 */
	public static String[] makeYearByMonth(int year, String month) {
		List<String> list = new ArrayList<>();
		if (!"".equals(year)) {
			year = year - 1;
			int j = 0;
			if (Integer.parseInt(month) <= 12) {
				j = (Integer.parseInt(month)+1);	
			}
			for (int i = 0; i <= 12; i++) {
				if (j > 9) {
					if (j < 13) {
						list.add(year+"-" + j);	
					}
				} else {
					list.add(year+"-"+"0"+ j);
				}
				j++;
				if (j > 13) {
					year = year + 1;
					j = 1;
				}
			}
		}
		String[] result = new String[list.size()];
		list.toArray(result);
		return result;
	}
	
	/**
	 * 임의의 문자열을 생성한다.
	 *
	 * @return String
	 */
	public static String genRndStr() {
        return new BigInteger(130, random).toString(32);
    }
	
	/**
	 * 'yyyy-MM-dd' 와 같은 패턴의 문자열을 데이트 객체로 변환한다.
	 *
	 * @param dateStr 변환 대상 문자.
	 * @param pattern 변환 패턴.
	 * @return 결과값 데이트 객체.
	 */
	public static Date stringToDate(String dateStr, String pattern) {
		if(dateStr == null) return null;
		DateTimeFormatter df = DateTimeFormat.forPattern(pattern);
		long mills = df.parseMillis(dateStr);
		return new Date(mills);
	}
	
	/**
	 * 랜덤 값 생성
	 * @param min
	 * @param max
	 * @return
	 */
	public static int random(int min, int max) {
		int result = (int) (Math.random() * max) + 1;
		if(result < min){
			result += min;
		}
		return result;
	}
	
	public static int random2(int min, int max) {
		int result = (int) (Math.random() * (max - min + 1)) + min;
		return result;
		
	}
	
	/**
	 * 페이징 값 가져오기
	 * @param sPage
	 * @param pageInList
	 * @return
	 */
	public static PagingDto getPaging(int sPage, int pageInList) {
		PagingDto pagingDto = new PagingDto();
		
		int page_cnt = pageInList;
		int srow = page_cnt * (sPage -1) + 1;
		String start = Integer.toString(srow);
		String end = Integer.toString(page_cnt * sPage);
		
		pagingDto.setStart(start);
		pagingDto.setEnd(end);
		return pagingDto;
	}
	
	public static HttpResponse http(String url, String body) {

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
        	JSONObject jsonParam = new JSONObject();
        	jsonParam.put("receipt-data", body);
        	System.out.println("json : " + jsonParam);
            HttpPost request = new HttpPost(url);
            StringEntity params = new StringEntity(jsonParam.toString());
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse result = httpClient.execute(request);

            String json = EntityUtils.toString(result.getEntity(), "UTF-8");
            try {
                JSONParser parser = new JSONParser();
                Object resultObject = parser.parse(json);

                if (resultObject instanceof JSONArray) {
                    JSONArray array=(JSONArray)resultObject;
                    for (Object object : array) {
                        JSONObject obj =(JSONObject)object;
                        System.out.println("status ::::" + obj.get("status"));
                    }

                } else if (resultObject instanceof JSONObject) {
                    JSONObject obj =(JSONObject)resultObject;
                    System.out.println("status :: " + obj.get("status"));
                    System.out.println(obj.get("environment"));
                    System.out.println( obj.get("receipt"));
                    JSONObject obj2 =(JSONObject)obj.get("receipt");
                    System.out.println(obj2.get("application_version"));
                    System.out.println(obj2.get("bundle_id"));
                    /*
                    JSONArray array = (JSONArray)obj.get("receipt");
                    for (Object object : array) {
                        JSONObject obj2 =(JSONObject)object;
                        System.out.println(obj2.get("transaction_id"));
                    }
                    */
                }

            } catch (Exception e) {
                // TODO: handle exception
            	e.printStackTrace();
            }

        } catch (IOException ex) {
        	ex.printStackTrace();
        }
        return null;
    }
	
	public static int getCornRewardByPercent(int corn, int percent) {
		float calcPercent = percent * (float) 0.01;
		int rewardPopcorn = (int)((corn * 10 ) * Float.valueOf(String.format("%.2f", calcPercent)));
		return rewardPopcorn;
	}
	
	public static void main(String[] args) throws Exception {
		String[] searchDate = Util.split("2017-01-13 09:35:00", " ");
		System.out.println("searchDate : " + searchDate[0]);
	 }
}

