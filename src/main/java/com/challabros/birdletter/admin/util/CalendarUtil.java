/**
 * 0. Project  : AnySign Notice & Faq
 *
 * 1. FileName : CalendarUtil.java
 * 2. Package  : com.softforum.anysign.util
 * 3. Comment  : 
 * 4. AUTHOR   : SOFTFORUM
 * 5. @Version : v1.0
 * Copyright(c) 2014 SOFTFORUM All Rights Reserved
 *------------------------------------------------------------------------------
 *                  변         경         사         항                       
 *------------------------------------------------------------------------------
 *    DATE      DEV                      DESCRIPTION                        
 * ----------  ------  --------------------------------------------------------- 
 * 2014. 1. 9. ANJIUN  신규생성                                     
 *------------------------------------------------------------------------------
 */
package com.challabros.birdletter.admin.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * <PRE>
 * 1. ClassName : CalendarUtil
 * 2. FileName  : CalendarUtil.java
 * 3. Package   : com.softforum.anysign.util
 * 4. 작성자    : ANJIUN
 * 5. 작성일    : 2014. 3. 28.
 * </PRE>
 */
public class CalendarUtil { 

	public static void main(String[] args) {
		GregorianCalendar cal = new GregorianCalendar();
		cal = gCalPlusSevenDay(cal);

	}

	/**
	 * <PRE>
	 * 1. MethodName : replaceDate
	 * 2. ClassName  : CalendarUtil
	 * 3. Comment    : 	날짜를 문자로 <br/> 
	 *                  Util.replaceStr(20040708,-);  -> 2004-07-08 <br/>
	 *                  문자를 날짜로 <br/>
	 *                  replaceDate(2004-07-08, ""); -> 20040708
	 * 4. 작성자     : ANJIUN
	 * 5. 작성일     : 2014. 3. 28.
	 * </PRE>
	 *   @return String
	 *   @param src
	 *   @param sReplace
	 */
	
	public static String replaceDate(String src, String sReplace) {
		String sdate = "";
		if ((src == null) || (src.equals(""))) {
			return "";
		} else {
			int i = src.length();
			if (i == 8) {
				String nyear = src.substring(0, 4);
				String nmonth = src.substring(4, 6);
				String nday = src.substring(6, 8);
				sdate = nyear + sReplace + nmonth + sReplace + nday;
			} else if (i == 10) {
				String nyear = src.substring(0, 4);
				String nmonth = src.substring(5, 7);
				String nday = src.substring(8, 10);
				sdate = nyear + nmonth + nday;
			} else if (i == 14) {
				String nyear = src.substring(0, 4);
				String nmonth = src.substring(4, 6);
				String nday = src.substring(6, 8);
				sdate = nyear + sReplace + nmonth + sReplace + nday;
			} else {
				sdate = src;
			}
			return sdate;
		}
	}
	   
   	/**
   	 * <PRE>
   	 * 1. MethodName : getMove
   	 * 2. ClassName  : CalendarUtil
   	 * 3. Comment    : 이전 이후달과 연도값 리턴
   	 * 4. 작성자     : ANJIUN
   	 * 5. 작성일     : 2014. 3. 28.
   	 * </PRE>
   	 *   @return int
   	 *   @param ny
   	 *   @param nm
   	 *   @param gu
   	 */
   	
   	public static int getMove(int ny, int nm, String gu) {
		int vals = 0;
		if (nm == 12){
			
			if (gu.equals("bfy")){
	 			vals = ny;
   			} else if (gu.equals("bfm")){
	 			vals = 11;
   			} else if (gu.equals("afy")){
	 			vals = ny + 1;
   			} else if (gu.equals("afm")){
	 			vals = 1;
   			}
   			
		} else if (nm == 1){
	 		
	 		if (gu.equals("bfy")){
   				vals = ny - 1;
 			} else if (gu.equals("bfm")){
   				vals = 12;
 			} else if (gu.equals("afy")){
   				vals = ny;
 			} else if (gu.equals("afm")){
	   			vals = 2;
	 		}  		
	 		
 		} else {
	 		
	 		if (gu.equals("bfy")){
   				vals = ny;
 			} else if (gu.equals("bfm")){
   				vals = nm-1;
 			} else if (gu.equals("afy")){
   				vals = ny;
 			} else if (gu.equals("afm")){
			   	vals = nm+1;
			} 
		}
		return vals;  	
	}    
     
	/**
	 * <PRE>
	 * 1. MethodName : getDateInterval
	 * 2. ClassName  : CalendarUtil
	 * 3. Comment    : 두 날짜 사이의 간격처리
	 * 4. 작성자     : ANJIUN
	 * 5. 작성일     : 2014. 3. 28.
	 * </PRE>
	 *   @return int
	 *   @param sdate
	 *   @param edate
	 */
	
	public static int getDateInterval(String sdate, String edate){
		int ii = 0;
		if (sdate.equals("") || edate.equals("0")) {
			ii = 0;
		} else {
		
			int year1 = 0;
			int mon1 = 0; 
			int day1 = 0;
			
			int year2 = 0;
			int mon2 = 0; 
			int day2 = 0;
			try{
				year1 = Integer.parseInt(sdate.substring(0,4));
				mon1 = Integer.parseInt(sdate.substring(4,6)); 
				day1 = Integer.parseInt(sdate.substring(6,8));
						
				year2 = Integer.parseInt(edate.substring(0,4));
				mon2 = Integer.parseInt(edate.substring(4,6)); 
				day2 = Integer.parseInt(edate.substring(6,8)); 
	
				Calendar cal1 = Calendar.getInstance(); 
				Calendar cal2 = Calendar.getInstance();  
			
				cal1.set(Calendar.YEAR,year1); 
				cal1.set(Calendar.MONTH,mon1); 
				cal1.set(Calendar.DATE,day1); 
			
				cal2.set(Calendar.YEAR,year2); 
				cal2.set(Calendar.MONTH,mon2); 
				cal2.set(Calendar.DATE,day2); 
	
				long val = (cal2.getTimeInMillis() - cal1.getTimeInMillis()) / 1000 / 60 / 60 / 24 ; 
				ii =  Integer.parseInt(Long.toString(val))+1;
			
			} catch (Exception e) {
			} finally {
			}
		}
		return ii;
	} 
		     
	/**
	 * <PRE>
	 * 1. MethodName : gCalToDBDayString
	 * 2. ClassName  : CalendarUtil
	 * 3. Comment    : Calendar Date를 DB형태 날짜(20000415) 문자열로
	 * 4. 작성자     : ANJIUN
	 * 5. 작성일     : 2014. 3. 28.
	 * </PRE>
	 *   @return String
	 *   @param cal
	 */
	
	public static String gCalToDBDayString(GregorianCalendar cal) {

		int year = cal.get(GregorianCalendar.YEAR);
		int month = cal.get(GregorianCalendar.MONTH) + 1;
		int date = cal.get(GregorianCalendar.DATE);

		return Integer.toString(year)
				+ ((month < 10) ? "0" + month : Integer.toString(month))
				+ ((date < 10) ? "0" + date : Integer.toString(date));
	}
  
	     	     
	/**
	 * <PRE>
	 * 1. MethodName : gCalToDBTimeString
	 * 2. ClassName  : CalendarUtil
	 * 3. Comment    : Calendar Time을 DB형태 시간(231103) 문자열로
	 * 4. 작성자     : ANJIUN
	 * 5. 작성일     : 2014. 3. 28.
	 * </PRE>
	 *   @return String
	 *   @param cal
	 */
	
	public static String gCalToDBTimeString(GregorianCalendar cal) {
		
		int hour = cal.get(GregorianCalendar.HOUR_OF_DAY);
		int minute = cal.get(GregorianCalendar.MINUTE);
		int second = cal.get(GregorianCalendar.SECOND);
		
		return 	((hour < 10) ? "0" + hour : Integer.toString(hour))
			  			+ ((minute < 10) ? "0" + minute : Integer.toString(minute))
			  			+ ((second < 10) ? "0" + second : Integer.toString(second));
	}

	/**
	 * <PRE>
	 * 1. MethodName : gCalToDBTime
	 * 2. ClassName  : CalendarUtil
	 * 3. Comment    : Calendar Time을 DB형태 시간(23:11) 문자열로
	 * 4. 작성자     : ANJIUN
	 * 5. 작성일     : 2014. 3. 28.
	 * </PRE>
	 *   @return String
	 */
	
	public static String gCalToDBTime() {

		GregorianCalendar cal = new GregorianCalendar();
		int hour = cal.get(GregorianCalendar.HOUR_OF_DAY);
		int minute = cal.get(GregorianCalendar.MINUTE); 
				
		return 	((hour < 10) ? "0" + hour : Integer.toString(hour))
						+":"+ ((minute < 10) ? "0" + minute : Integer.toString(minute));
	}

	/**
	 * <PRE>
	 * 1. MethodName : gCalToDBTime2
	 * 2. ClassName  : CalendarUtil
	 * 3. Comment    : Calendar Time을 DB형태 시간(231100) 문자열로
	 * 4. 작성자     : ANJIUN
	 * 5. 작성일     : 2014. 3. 28.
	 * </PRE>
	 *   @return String
	 */
	
	public static String gCalToDBTime2() {

		GregorianCalendar cal = new GregorianCalendar();
		int hour = cal.get(GregorianCalendar.HOUR_OF_DAY);
		int minute = cal.get(GregorianCalendar.MINUTE); 
		int second = cal.get(GregorianCalendar.SECOND); 
				
		return 	((hour < 10) ? "0" + hour : Integer.toString(hour))
						+((minute < 10) ? "0" + minute : Integer.toString(minute))
						+((second < 10) ? "0" + second : Integer.toString(second));
	}

	/**
	 * <PRE>
	 * 1. MethodName : gCalToDBTimeString2
	 * 2. ClassName  : CalendarUtil
	 * 3. Comment    : Calendar Time을 DB형태 시간(231103) 문자열로
	 * 4. 작성자     : ANJIUN
	 * 5. 작성일     : 2014. 3. 28.
	 * </PRE>
	 *   @return String
	 *   @param cal
	 */
	
	public static String gCalToDBTimeString2(GregorianCalendar cal) {
		
		int hour = cal.get(GregorianCalendar.HOUR_OF_DAY);
		int minute = cal.get(GregorianCalendar.MINUTE);
		int second = cal.get(GregorianCalendar.SECOND);
		int millisecond = cal.get(GregorianCalendar.MILLISECOND);
		
		return 	((hour < 10) ? "0" + hour : Integer.toString(hour))
						+ ((minute < 10) ? "0" + minute : Integer.toString(minute))
						+ ((second < 10) ? "0" + second : Integer.toString(second))
						+ ((millisecond < 10) ? "0" + millisecond : Integer.toString(millisecond));
	}

	/**
	 * <PRE>
	 * 1. MethodName : gCurrentToStr
	 * 2. ClassName  : CalendarUtil
	 * 3. Comment    : 현재 날짜(문자)를 포맷된 문자열로 (2004-04-15) 
	 * 4. 작성자     : ANJIUN
	 * 5. 작성일     : 2014. 3. 28.
	 * </PRE>
	 *   @return String
	 */
	
	public static String gCurrentToStr(){
		return dbDayPrintDayFormat(gCalToDBDayString());
	}	

	/**
	 * <PRE>
	 * 1. MethodName : gCalToDBDayString
	 * 2. ClassName  : CalendarUtil
	 * 3. Comment    : 현재 날짜를 문자열로 (20040415) 
	 * 4. 작성자     : ANJIUN
	 * 5. 작성일     : 2014. 3. 28.
	 * </PRE>
	 *   @return String
	 */
	
	public static String gCalToDBDayString() {
		
		GregorianCalendar cal = new GregorianCalendar();
		
		int year = cal.get(GregorianCalendar.YEAR);
		int month = cal.get(GregorianCalendar.MONTH) + 1;
		int date = cal.get(GregorianCalendar.DATE);
	
		return Integer.toString(year)
					+ ((month < 10) ? "0" + month : Integer.toString(month))
					+ ((date < 10) ? "0" + date : Integer.toString(date));
	}
	
	
	@SuppressWarnings("unused")
	public static String getCalcMonth2(String flg) throws ParseException {
		GregorianCalendar gCal = new GregorianCalendar(); 
		Calendar tCal = GregorianCalendar.getInstance();
		if(flg.equals("plus")){
			gCal.add(Calendar.MONTH, 2);
		}
		
		int year = gCal.get(Calendar.YEAR);
		int month = gCal.get(Calendar.MONTH);
		if(month == 0){
			month = 12;
			year = year - 1;
		}
		int date =  gCal.get(Calendar.DAY_OF_MONTH);
		
		return year + "-" + ((month < 10) ? "0" + month : Integer.toString(month)) + "-" + ((date < 10) ? "0" + date : Integer.toString(date));
		
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : gCalToDBDayInt
	 * 2. ClassName  : CalendarUtil
	 * 3. Comment    : 현재 날짜를 문자열로 (20040415) 
	 * 4. 작성자     : ANJIUN
	 * 5. 작성일     : 2014. 3. 28.
	 * </PRE>
	 *   @return int
	 */
	
	public static int gCalToDBDayInt() {

		GregorianCalendar cal = new GregorianCalendar();

		int year = cal.get(GregorianCalendar.YEAR);
		int month = cal.get(GregorianCalendar.MONTH) + 1;
		int date = cal.get(GregorianCalendar.DATE);

		String ndate = Integer.toString(year)
				+ ((month < 10) ? "0" + month : Integer.toString(month))
				+ ((date < 10) ? "0" + date : Integer.toString(date));
		return Integer.parseInt(ndate);
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : gCalToDBDayString2
	 * 2. ClassName  : CalendarUtil
	 * 3. Comment    : 현재 날짜를 문자열로 (20040415122312) 
	 * 4. 작성자     : ANJIUN
	 * 5. 작성일     : 2014. 3. 28.
	 * </PRE>
	 *   @return String
	 */
	
	public static String gCalToDBDayString2() {

		GregorianCalendar cal = new GregorianCalendar();

		int year = cal.get(GregorianCalendar.YEAR);
		int month = cal.get(GregorianCalendar.MONTH) + 1;
		int date = cal.get(GregorianCalendar.DATE);
		int hour = cal.get(GregorianCalendar.HOUR_OF_DAY);
		int min = cal.get(GregorianCalendar.MINUTE);
		int sec = cal.get(GregorianCalendar.SECOND);

		return Integer.toString(year)
				+ ((month < 10) ? "0" + month : Integer.toString(month))
				+ ((date < 10) ? "0" + date : Integer.toString(date))
				+ ((hour < 10) ? "0" + hour : Integer.toString(hour))
				+ ((min < 10) ? "0" + min : Integer.toString(min))
				+ ((sec < 10) ? "0" + sec : Integer.toString(sec));
	}
	
	

	/**
	 * <PRE>
	 * 1. MethodName : gCalToDBDayString3
	 * 2. ClassName  : CalendarUtil
	 * 3. Comment    : 현재 날짜를 문자열로 (200404) 
	 * 4. 작성자     : ANJIUN
	 * 5. 작성일     : 2014. 3. 28.
	 * </PRE>
	 *   @return String
	 */
	
	public static String gCalToDBDayString3() {
		GregorianCalendar cal = new GregorianCalendar();	
		int year = cal.get(GregorianCalendar.YEAR);
		int month = cal.get(GregorianCalendar.MONTH);
		return Integer.toString(year) + ((month < 10) ? "0" + month : Integer.toString(month));
	}

	/**
	 * <PRE>
	 * 1. MethodName : gCalToDBDayString4
	 * 2. ClassName  : CalendarUtil
	 * 3. Comment    : 현재 날짜 시간을 문자열로 (00) 
	 * 4. 작성자     : ANJIUN
	 * 5. 작성일     : 2014. 3. 28.
	 * </PRE>
	 *   @return String
	 */
	
	public static String gCalToDBDayString4() {

		GregorianCalendar cal = new GregorianCalendar();
		int hour = cal.get(GregorianCalendar.HOUR_OF_DAY);
		return ((hour < 10) ? "0" + hour : Integer.toString(hour));

	}
	
	/**
	 * <PRE>
	 * 1. MethodName : gCalToYear
	 * 2. ClassName  : CalendarUtil
	 * 3. Comment    : Calendar Date를 DB형태 연도(2000) Int로
	 * 4. 작성자     : ANJIUN
	 * 5. 작성일     : 2014. 3. 28.
	 * </PRE>
	 *   @return int
	 */
	
	public static int gCalToYear() {
		GregorianCalendar cal = new GregorianCalendar();
		int year = cal.get(GregorianCalendar.YEAR);
		return year;
	}
	  
	/**
	 * <PRE>
	 * 1. MethodName : gCalToMonth
	 * 2. ClassName  : CalendarUtil
	 * 3. Comment    : Calendar Date를 DB형태 월(8) Int로
	 * 4. 작성자     : ANJIUN
	 * 5. 작성일     : 2014. 3. 28.
	 * </PRE>
	 *   @return int
	 */
	
	public static int gCalToMonth() {
		GregorianCalendar cal = new GregorianCalendar();
		int month = cal.get(GregorianCalendar.MONTH) + 1;
		return month;
	}
		
	/**
	 * <PRE>
	 * 1. MethodName : gCalToDay
	 * 2. ClassName  : CalendarUtil
	 * 3. Comment    : Calendar Date를 DB형태 월(8) Int로
	 * 4. 작성자     : ANJIUN
	 * 5. 작성일     : 2014. 3. 28.
	 * </PRE>
	 * 
	 * @return int
	 */

	public static int gCalToDay() {
		GregorianCalendar cal = new GregorianCalendar();
		int day = cal.get(GregorianCalendar.DATE);
		return day;
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : gCalNowString
	 * 2. ClassName  : CalendarUtil
	 * 3. Comment    : Calendar Date를 DB형태 날짜(20011123124560) 문자열로 
	 * 4. 작성자     : ANJIUN
	 * 5. 작성일     : 2014. 3. 28.
	 * </PRE>
	 *   @return String
	 */
	
	public static String gCalNowString() {
		GregorianCalendar cal = new GregorianCalendar();
		return gCalToDBDayString(cal) + gCalToDBTimeString(cal);
	}

	/**
	 * <PRE>
	 * 1. MethodName : gCalNowString2
	 * 2. ClassName  : CalendarUtil
	 * 3. Comment    : Calendar Date를 DB형태 날짜(2001112312456000) 문자열로 (밀리세컨드까지)
	 * 4. 작성자     : ANJIUN
	 * 5. 작성일     : 2014. 3. 28.
	 * </PRE>
	 *   @return String
	 */
	
	public static String gCalNowString2() {
		GregorianCalendar cal = new GregorianCalendar();
		return gCalToDBDayString(cal) + gCalToDBTimeString2(cal);
	}
	
  	/**
  	 * <PRE>
  	 * 1. MethodName : gCalToDBDayTimeString
  	 * 2. ClassName  : CalendarUtil
  	 * 3. Comment    : Calendar Date를 DB형태 날짜(20011123124560) 문자열로
  	 * 4. 작성자     : ANJIUN
  	 * 5. 작성일     : 2014. 3. 28.
  	 * </PRE>
  	 *   @return String
  	 *   @param cal
  	 */
  	
  	public static String gCalToDBDayTimeString(GregorianCalendar cal) {
		return gCalToDBDayString(cal) + gCalToDBTimeString(cal);
  	}
  
	/**
	 * <PRE>
	 * 1. MethodName : dbDayStringToGCal
	 * 2. ClassName  : CalendarUtil
	 * 3. Comment    : DB형태 날짜(20000415) 문자열을 Calendar Date로
	 * 4. 작성자     : ANJIUN
	 * 5. 작성일     : 2014. 3. 28.
	 * </PRE>
	 *   @return GregorianCalendar
	 *   @param s
	 */
	
	public static GregorianCalendar dbDayStringToGCal(String s) {
		return dbDayTimeStringToGCal(s, "000000");  
	}

  	/**
  	 * <PRE>
  	 * 1. MethodName : dbTimeStringToGCal
  	 * 2. ClassName  : CalendarUtil
  	 * 3. Comment    : DB형태 시간(231103) 문자열을 Calendar Time으로
  	 * 4. 작성자     : ANJIUN
  	 * 5. 작성일     : 2014. 3. 28.
  	 * </PRE>
  	 *   @return GregorianCalendar
  	 *   @param s
  	 */
  	
  	public static GregorianCalendar dbTimeStringToGCal(String s) {
		return dbDayTimeStringToGCal("19700101", s);
  	}
 
	/**
	 * <PRE>
	 * 1. MethodName : dbDayTimeStringToGCal
	 * 2. ClassName  : CalendarUtil
	 * 3. Comment    : DB형태 날짜시간(20010415231103) 문자열을 Calendar DateTime으로
	 * 4. 작성자     : ANJIUN
	 * 5. 작성일     : 2014. 3. 28.
	 * </PRE>
	 *   @return GregorianCalendar
	 *   @param sDay
	 *   @param sTime
	 */
	
	public static GregorianCalendar dbDayTimeStringToGCal(String sDay, String sTime) {

		int year = Integer.parseInt(sDay.substring(0, 4));
		int month = Integer.parseInt(sDay.substring(4, 6));
		int date = Integer.parseInt(sDay.substring(6, 8));
		
		int hour = Integer.parseInt(sTime.substring(0, 2));
		int minute = Integer.parseInt(sTime.substring(2, 4));
		int second = Integer.parseInt(sTime.substring(4, 6));
		
		GregorianCalendar gCal = new GregorianCalendar();
		gCal.set(year, month - 1, date, hour, minute, second);
		return gCal;
	}

	/**
	 * <PRE>
	 * 1. MethodName : getTimeString
	 * 2. ClassName  : CalendarUtil
	 * 3. Comment    : String (080000)문자열을 String(08:00:00)으로
	 * 4. 작성자     : ANJIUN
	 * 5. 작성일     : 2014. 3. 28.
	 * </PRE>
	 *   @return String
	 *   @param sTime
	 */
	
	public static String getTimeString(String sTime) {

		String timetype = "";
		String hour = sTime.substring(0, 2);
		String minute = sTime.substring(2, 4);
		String second = sTime.substring(4, 6);
		timetype = hour + ":" + minute + ":" + second;
		return timetype;
	}
	
  	/**
  	 * <PRE>
  	 * 1. MethodName : dbDayPrintDayFormat
  	 * 2. ClassName  : CalendarUtil
  	 * 3. Comment    : 문자열(20040725)을 문자열(2001-11-23)으로 날짜만 표현
  	 * 4. 작성자     : ANJIUN
  	 * 5. 작성일     : 2014. 3. 28.
  	 * </PRE>
  	 *   @return String
  	 *   @param dbDay
  	 */
  	
  	public static String dbDayPrintDayFormat(String dbDay) {
		if (dbDay.length() != 8) {
			//String dbDate = dbDay.substring(0,8);
			StringBuffer sb = new StringBuffer();
			sb.append(dbDay.substring(0, 4));
			sb.append(".");
			sb.append(dbDay.substring(4, 6));
			sb.append(".");
			sb.append(dbDay.substring(6, 8));
			sb.append(" ");
			sb.append(dbDay.substring(8, 10));
			sb.append(":");
			sb.append(dbDay.substring(10, 12));
			sb.append(":");
			sb.append(dbDay.substring(12, 14));
			return sb.toString();
		} else { 
			StringBuffer sb = new StringBuffer();
			sb.append(dbDay.substring(0, 4));
			sb.append("-");
			sb.append(dbDay.substring(4, 6));
			sb.append("-");
			sb.append(dbDay.substring(6, 8));
			return sb.toString();
		}
  	}

  	/**
  	 * <PRE>
  	 * 1. MethodName : dbDayHanFormat
  	 * 2. ClassName  : CalendarUtil
  	 * 3. Comment    : 문자열(20040725)을 문자열(2001년 11월 23)으로 날짜만 표현
  	 * 4. 작성자     : ANJIUN
  	 * 5. 작성일     : 2014. 3. 28.
  	 * </PRE>
  	 *   @return String
  	 *   @param dbDay
  	 */
  	
  	public static String dbDayHanFormat(String dbDay) {
		StringBuffer sb = new StringBuffer();

		sb.append(dbDay.substring(0, 4));
		sb.append("년");
		sb.append(dbDay.substring(4, 6));
		sb.append("월");
		sb.append(dbDay.substring(6, 8));
		sb.append("일");
		return sb.toString();
  	}
	  
    /**
     * <PRE>
     * 1. MethodName : gCalPlusOneDay
     * 2. ClassName  : CalendarUtil
     * 3. Comment    : 문자열(20011120000001)을 1일 더한 문자열(20011130124560)으로 반환
     * 4. 작성자     : ANJIUN
     * 5. 작성일     : 2014. 3. 28.
     * </PRE>
     *   @return String
     *   @param s
     */
    
    public static String gCalPlusOneDay(String s) { 
      	int year = Integer.parseInt(s.substring(0, 4)); 
      	int month = Integer.parseInt(s.substring(4, 6)) - 1; 
      	int date = Integer.parseInt(s.substring(6, 8)) + 1; 

      	GregorianCalendar gCal = new GregorianCalendar(); 
      	gCal.set(year, month, date); 

      	if (s.length() == 14) {
	    	return gCalToDBDayString(gCal) + s.substring(8, 14);   
      	} else {
	    	return gCalToDBDayString(gCal); 
      	}
	} 

	/**
	 * <PRE>
	 * 1. MethodName : gCalPlusSevenDay
	 * 2. ClassName  : CalendarUtil
	 * 3. Comment    : 문자열(20011123124560)을 7일 더한 문자열(20011130124560)으로 반환
	 * 4. 작성자     : ANJIUN
	 * 5. 작성일     : 2014. 3. 28.
	 * </PRE>
	 *   @return String
	 *   @param s
	 */
	
	public static String gCalPlusSevenDay(String s) { 

	  	int year = Integer.parseInt(s.substring(0, 4)); 
	  	int month = Integer.parseInt(s.substring(4, 6)) - 1; 
	  	int date = Integer.parseInt(s.substring(6, 8)) + 7; 
	
	  	GregorianCalendar gCal = new GregorianCalendar(); 
	  	gCal.set(year, month, date); 
	
	  	if (s.length() == 14) {
			return gCalToDBDayString(gCal) + s.substring(8, 14);   
	  	} else {
			return gCalToDBDayString(gCal); 
	  	}
	}

	/**
	 * <PRE>
	 * 1. MethodName : gCalPlusYear
	 * 2. ClassName  : CalendarUtil
	 * 3. Comment    : 문자열(20011123124560)을 7일 더한 문자열(20011130124560)으로 반환
	 * 4. 작성자     : ANJIUN
	 * 5. 작성일     : 2014. 3. 28.
	 * </PRE>
	 *   @return String
	 *   @param s
	 *   @param years
	 */
	
	public static String gCalPlusYear(String s, String years) { 

		int year = Integer.parseInt(s.substring(0, 4)); 
		int month = Integer.parseInt(s.substring(4, 6)) - 1; 
		int k = Integer.parseInt(years);
		int	date = Integer.parseInt(s.substring(6, 8)) + (365*k); 
	
		GregorianCalendar gCal = new GregorianCalendar(); 
		gCal.set(year, month, date); 
	
		if (s.length() == 14) {
			return gCalToDBDayString(gCal) + s.substring(8, 14);   
		} else {
			return gCalToDBDayString(gCal); 
		}
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : gCalPlusSevenDay
	 * 2. ClassName  : CalendarUtil
	 * 3. Comment    : GregorianCalendarn형을 받아서 7일 더함
	 * 4. 작성자     : ANJIUN
	 * 5. 작성일     : 2014. 3. 28.
	 * </PRE>
	 *   @return GregorianCalendar
	 *   @param gCal
	 */
	
	public static GregorianCalendar gCalPlusSevenDay(GregorianCalendar gCal) { 
	  	String s = gCalToDBDayTimeString(gCal);

	  	int year = Integer.parseInt(s.substring(0, 4)); 
	  	int month = Integer.parseInt(s.substring(4, 6)) - 1; 
	  	int date = Integer.parseInt(s.substring(6, 8)) + 7; 

	  	gCal.set(year, month, date);

	  	return gCal; 
	} 
	
	/**
	 * <PRE>
	 * 1. MethodName : gCalMinusoneDay
	 * 2. ClassName  : CalendarUtil
	 * 3. Comment    : 입력일의 하루전일 구하기 (20040415) 
	 * 4. 작성자     : ANJIUN
	 * 5. 작성일     : 2014. 3. 28.
	 * </PRE>
	 *   @return String
	 *   @param s
	 */
	
	public static String gCalMinusoneDay(String s) { 

		int year = Integer.parseInt(s.substring(0, 4)); 
		int month = Integer.parseInt(s.substring(4, 6)) - 1; 
		int date = Integer.parseInt(s.substring(6, 8)) -1; 
	
		GregorianCalendar gCal = new GregorianCalendar(); 
		gCal.set(year, month, date); 
	
		return gCalToDBDayString(gCal); 
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : gCalMinustwoDay
	 * 2. ClassName  : CalendarUtil
	 * 3. Comment    : 입력일의 하루전일 구하기 (20040415) 
	 * 4. 작성자     : ANJIUN
	 * 5. 작성일     : 2014. 3. 28.
	 * </PRE>
	 *   @return String
	 *   @param s
	 */
	
	public static String gCalMinustwoDay(String s) { 

		int year = Integer.parseInt(s.substring(0, 4)); 
		int month = Integer.parseInt(s.substring(4, 6)) - 1; 
		int date = Integer.parseInt(s.substring(6, 8)) -2; 
	
		GregorianCalendar gCal = new GregorianCalendar(); 
		gCal.set(year, month, date); 
	
		return gCalToDBDayString(gCal); 
	}
		
	/**
	 * <PRE>
	 * 1. MethodName : gCalPlusoneDay
	 * 2. ClassName  : CalendarUtil
	 * 3. Comment    : 입력일의 하루 다음날일 구하기 (20040415) 
	 * 4. 작성자     : ANJIUN
	 * 5. 작성일     : 2014. 3. 28.
	 * </PRE>
	 *   @return String
	 *   @param s
	 */
	
	public static String gCalPlusoneDay(String s) { 
		if (s.equalsIgnoreCase("")) return s;

		int year = Integer.parseInt(s.substring(0, 4)); 
		int month = Integer.parseInt(s.substring(4, 6)) - 1; 
		int date = Integer.parseInt(s.substring(6, 8)) +1; 
	
		GregorianCalendar gCal = new GregorianCalendar(); 
		gCal.set(year, month, date); 
	
		return gCalToDBDayString(gCal); 
	}

	/**
	 * <PRE>
	 * 1. MethodName : gCalMinusDay
	 * 2. ClassName  : CalendarUtil
	 * 3. Comment    : 입력일의 하루전일 구하기 (20040415) 
	 * 4. 작성자     : ANJIUN
	 * 5. 작성일     : 2014. 3. 28.
	 * </PRE>
	 *   @return String
	 *   @param s
	 *   @param id
	 */
	
	public static String gCalMinusDay(String s,int id) { 

		int year = Integer.parseInt(s.substring(0, 4)); 
		int month = Integer.parseInt(s.substring(4, 6)) - 1; 
		int date = Integer.parseInt(s.substring(6, 8)) - id; 
	
		GregorianCalendar gCal = new GregorianCalendar(); 
		gCal.set(year, month, date); 
	
		return gCalToDBDayString(gCal); 
	}

	/**
	 * <PRE>
	 * 1. MethodName : gCalPlusDay
	 * 2. ClassName  : CalendarUtil
	 * 3. Comment    : 입력일의 하루 다음날일 구하기 (20040415) 
	 * 4. 작성자     : ANJIUN
	 * 5. 작성일     : 2014. 3. 28.
	 * </PRE>
	 *   @return String
	 *   @param s
	 *   @param id
	 */
	
	public static String gCalPlusDay(String s,int id) { 
		if (s.equalsIgnoreCase("")) return s;

		int year = Integer.parseInt(s.substring(0, 4)); 
		int month = Integer.parseInt(s.substring(4, 6)) - 1; 
		int date = Integer.parseInt(s.substring(6, 8)) + id; 
	
		GregorianCalendar gCal = new GregorianCalendar(); 
		gCal.set(year, month, date); 
	
		return gCalToDBDayString(gCal); 
	}

	/**
   *  입력받은 년도와 월을 가지고 DB형태 날짜(20000415) 문자열로
   */
   public static String getYMdate(String str) {
	    int len = str.length();
		int year = Integer.parseInt(str.substring(0, 4)); 
		int month = Integer.parseInt(str.substring(4,len));
		int date = 1;
	
		return Integer.toString(year)
					+ ((month < 10) ? "0" + month : Integer.toString(month))
					+ ((date < 10) ? "0" + date : Integer.toString(date));
	}	
	
	/**
	 * <PRE>
	 * 1. MethodName : getThisWeek
	 * 2. ClassName  : CalendarUtil
	 * 3. Comment    : sdate의 해당 주의 시작일 끝일
	 * 4. 작성자     : ANJIUN
	 * 5. 작성일     : 2014. 3. 28.
	 * </PRE>
	 *   @return String
	 *   @param sdate
	 *   @param gu
	 *   @throws ParseException
	 */
	
	public static String getThisWeek(String sdate, String gu) throws ParseException {

		// 1 일, 2 월, 3 화, 4 수, 5 목, 6 금, 7 토
		FormatCalendar tocal2 = new FormatCalendar("yyyy-MM-dd");
		tocal2.parse("yyyyMMdd", sdate);
		int idif = tocal2.get(FormatCalendar.DAY_OF_WEEK); // 1일의 요일
		String bDate = gCalMinusDay(sdate, idif - 1);
		String fDate = gCalPlusDay(sdate, 7 - (idif));
		if (gu.equals("S")) {
			return bDate;
		} else {
			return fDate;
		}
	}

	/**
	 * <PRE>
	 * 1. MethodName : getNextWeek
	 * 2. ClassName  : CalendarUtil
	 * 3. Comment    : sdate의 다음 주의 시작일 끝일
	 * 4. 작성자     : ANJIUN
	 * 5. 작성일     : 2014. 3. 28.
	 * </PRE>
	 *   @return String
	 *   @param sdate
	 *   @param gu
	 *   @throws ParseException
	 */
	
	public static String getNextWeek(String sdate, String gu) throws ParseException{

		int vwd = 0;
		int vint = 0;
		String vdate = "";
		FormatCalendar tocal = new FormatCalendar("yyyy-MM-dd");		
		FormatCalendar nextcal = new FormatCalendar("yyyy-MM-dd");		
		
		//다음주
		nextcal.parse("yyyyMMdd", sdate);
		nextcal.add(FormatCalendar.DATE, 7);
		String nextweek = nextcal.format("yyyyMMdd");
		  
		FormatCalendar nextWeekSdate = new FormatCalendar("yyyy-MM-dd");
		FormatCalendar nextWeekEdate = new FormatCalendar("yyyy-MM-dd");   
		  
		// 몇주째인지 검사

		tocal.parse("yyyyMMdd", nextweek);
		tocal.set(tocal.get(FormatCalendar.YEAR), tocal.get(FormatCalendar.MONTH), 1);
		nextWeekSdate.parse("yyyyMMdd", nextweek);
		nextWeekEdate.parse("yyyyMMdd", nextweek);
		
		vwd = tocal.get(FormatCalendar.DAY_OF_WEEK); //1일의 요일 
		vint = getDifference(FormatCalendar.DATE, nextWeekSdate, tocal);
		 
		// 그 주의 처음일자와 마지막 일자 구하기

		nextWeekSdate.add(FormatCalendar.DATE, - ((vint + vwd -1) % 7));//처음일자
		nextWeekSdate.add(FormatCalendar.DATE, 0);//처음일자 -1
		  
		nextWeekEdate.add(FormatCalendar.DATE, -((vint + vwd -1) % 7));//처음일자
		nextWeekEdate.add(FormatCalendar.DATE, 6);//처음일자 +6  
	    
		String firstday = nextWeekSdate.format("yyyyMMdd");
		String endday = nextWeekEdate.format("yyyyMMdd");
		
		if (gu.equals("S")) {
			vdate = firstday;
		} else {
			vdate = endday;
		}
		return vdate;
	}	
	
	/**
	 * <PRE>
	 * 1. MethodName : getPrevWeek
	 * 2. ClassName  : CalendarUtil
	 * 3. Comment    : sdate의 지난 주의 시작일 끝일
	 * 4. 작성자     : ANJIUN
	 * 5. 작성일     : 2014. 3. 28.
	 * </PRE>
	 *   @return String
	 *   @param sdate
	 *   @param gu
	 *   @throws ParseException
	 */
	
	public static String getPrevWeek(String sdate, String gu) throws ParseException{

		int vwd = 0;
		int vint = 0;
		String vdate = ""; 
		FormatCalendar tocal = new FormatCalendar("yyyy-MM-dd");		
		FormatCalendar prevcal = new FormatCalendar("yyyy-MM-dd");		

		//지난주
		prevcal.parse("yyyyMMdd", sdate);
		prevcal.add(FormatCalendar.DATE, -7);
		String prevweek = prevcal.format("yyyyMMdd");

		FormatCalendar pervWeekSdate = new FormatCalendar("yyyy-MM-dd");
		FormatCalendar pervWeekEdate = new FormatCalendar("yyyy-MM-dd");   

		// 몇주째인지 검사

		tocal.parse("yyyyMMdd", prevweek);
		tocal.set(tocal.get(FormatCalendar.YEAR), tocal.get(FormatCalendar.MONTH), 1);
		pervWeekSdate.parse("yyyyMMdd", prevweek);
		pervWeekEdate.parse("yyyyMMdd", prevweek);

		vwd = tocal.get(FormatCalendar.DAY_OF_WEEK); //1일의 요일 
		vint = getDifference(FormatCalendar.DATE, pervWeekSdate, tocal);
	
		// 그 주의 처음일자와 마지막 일자 구하기
		pervWeekSdate.add(FormatCalendar.DATE, - ((vint + vwd -1) % 7));//처음일자
		pervWeekSdate.add(FormatCalendar.DATE, 0);//처음일자 -1
		  
		pervWeekEdate.add(FormatCalendar.DATE, -((vint + vwd -1) % 7));//처음일자
		pervWeekEdate.add(FormatCalendar.DATE, 6);//처음일자 +6  
	    
		String firstday = pervWeekSdate.format("yyyyMMdd");
		String endday = pervWeekEdate.format("yyyyMMdd");
		
		if (gu.equals("S")) {
			vdate = firstday;
		} else {
			vdate = endday;
		}
		return vdate;
	}	
	
	 /**
	 * <PRE>
	 * 1. MethodName : getThisMonth
	 * 2. ClassName  : CalendarUtil
	 * 3. Comment    : sdate의 해당 월의 시작일 끝일 
	 * 4. 작성자     : ANJIUN
	 * 5. 작성일     : 2014. 3. 28.
	 * </PRE>
	 *   @return String
	 *   @param sdate
	 *   @param gu
	 *   @throws ParseException
	 */
	
	public static String getThisMonth(String sdate, String gu) throws ParseException{
			
		String vdate = "";
		FormatCalendar tocal = new FormatCalendar("yyyy-MM-dd");
		FormatCalendar gcal = new FormatCalendar("yyyy-MM-dd");
	   
		//1일 날짜 가지고 있기
		tocal.parse("yyyyMMdd", sdate);
		tocal.set(tocal.get(FormatCalendar.YEAR), tocal.get(FormatCalendar.MONTH), 1);

	
		//1일의 요일
		gcal.parse("yyyyMMdd", sdate);
		gcal.set(gcal.get(FormatCalendar.YEAR), gcal.get(FormatCalendar.MONTH), 1);
		
		//마지막 날짜
		gcal.add(FormatCalendar.MONTH, 1);
		gcal.add(FormatCalendar.DATE, -1); 
		
		String firstday = tocal.format("yyyyMMdd");
		String endday = gcal.format("yyyyMMdd");
		
		if (gu.equals("S")) {
		 	vdate = firstday;
		} else {
			vdate = endday;
		}
		return vdate;
	 }
	
	/**
	 * <PRE>
	 * 1. MethodName : getNextMonth
	 * 2. ClassName  : CalendarUtil
	 * 3. Comment    : sdate의 다음 달의 시작일 끝일
	 * 4. 작성자     : ANJIUN
	 * 5. 작성일     : 2014. 3. 28.
	 * </PRE>
	 *   @return String
	 *   @param sdate
	 *   @param gu
	 *   @throws ParseException
	 */
	
	public static String getNextMonth(String sdate, String gu) throws ParseException {

		String vdate = "";
		FormatCalendar tocal = new FormatCalendar("yyyy-MM-dd");
		FormatCalendar nextcal = new FormatCalendar("yyyy-MM-dd");

		// 1일 날짜 가지고 있기
		nextcal.parse("yyyyMMdd", sdate);
		nextcal.set(nextcal.get(FormatCalendar.YEAR),
				nextcal.get(FormatCalendar.MONTH) + 1, 1);
		String nextmont = nextcal.format("yyyyMMdd");

		// 1일의 요일
		tocal.parse("yyyyMMdd", nextmont);
		tocal.set(tocal.get(FormatCalendar.YEAR),
				tocal.get(FormatCalendar.MONTH), 1);

		// 마지막 날짜
		tocal.add(FormatCalendar.MONTH, 1);
		tocal.add(FormatCalendar.DATE, -1);

		String firstday = nextcal.format("yyyyMMdd");
		String endday = tocal.format("yyyyMMdd");

		if (gu.equals("S")) {
			vdate = firstday;
		} else {
			vdate = endday;
		}
		return vdate;
	}
	  
	public static String getFiscalYear2(String opt) {
		String ndate = CalendarUtil.gCalToDBDayString();
		int years = Integer.parseInt(ndate.substring(0, 4));
		int months = Integer.parseInt(ndate.substring(4, 6));
		String vc_start_date = "";
		if (months > 11) {
			vc_start_date = Integer.toString(years) + "1201";
		} else {
			vc_start_date = Integer.toString(years - 1) + "1201";
		}
		return (opt.equalsIgnoreCase("S") ? vc_start_date : ndate);
	}

	/**
	 * <PRE>
	 * 1. MethodName : getIntevalM
	 * 2. ClassName  : CalendarUtil
	 * 3. Comment    : 입력받은 두 날짜 사이의 차리를 분으로 리턴
	 * 4. 작성자     : ANJIUN
	 * 5. 작성일     : 2014. 3. 28.
	 * </PRE>
	 *   @return int
	 *   @param fdate
	 *   @param edate
	 *   @throws ParseException
	 */
	
	public static int getIntevalM(String fdate, String edate) throws ParseException {
		int min = 0;

		Date stDate = new SimpleDateFormat("yyyyMMddHHmmss").parse(fdate);
		Date etDate = new SimpleDateFormat("yyyyMMddHHmmss").parse(edate);

		long timemillis = etDate.getTime() - stDate.getTime();
		min = (int) (timemillis / (60 * 1000));

		return min;
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : getSrchDateFormat
	 * 2. ClassName  : CalendarUtil
	 * 3. Comment    : 입력받은 두 날짜 사이의 차리를 분으로 리턴
	 * 4. 작성자     : ANJIUN
	 * 5. 작성일     : 2014. 3. 28.
	 * </PRE>
	 *   @return String
	 *   @param fdate
	 *   @param gu
	 *   @throws ParseException
	 */
	
	public static String getSrchDateFormat(String fdate, String gu) throws ParseException {
		String min = "";

		if (gu.equals("S")) {
			min = fdate + "000000";
		} else {
			min = fdate + "235959";
		}

		return min;
	}
	
	public static final int getDifference(int field, Calendar aCalX, Calendar aCalY) {
		int gap = 0;
		int step = 0;
		Calendar calX = new GregorianCalendar();
		Calendar calY = new GregorianCalendar();

		calX.set(aCalX.get(Calendar.YEAR), aCalX.get(Calendar.MONTH),
				aCalX.get(Calendar.DATE), 0, 0, 0);
		calY.set(aCalY.get(Calendar.YEAR), aCalY.get(Calendar.MONTH),
				aCalY.get(Calendar.DATE), 0, 0, 0);

		step = (calX.before(calY) ? -1 : 1);

		if (field == Calendar.DATE) {
			while (!calX.equals(calY)) {
				calY.add(Calendar.DATE, step);
				gap = gap + step;
			}
		} else if (field == Calendar.MONTH) {
			calX.set(calX.get(Calendar.YEAR), calX.get(Calendar.MONTH), 1);
			calY.set(calY.get(Calendar.YEAR), calY.get(Calendar.MONTH), 1);
			while (!calX.equals(calY)) {
				calY.add(Calendar.MONTH, step);
				gap = gap + step;
			}
		} else if (field == Calendar.YEAR) {
			calX.set(calX.get(Calendar.YEAR), 1, 1);
			calY.set(calY.get(Calendar.YEAR), 1, 1);
			while (!calX.equals(calY)) {
				calY.add(Calendar.YEAR, step);
				gap = gap + step;
			}
		}

		return gap;
	}

	public static final String getKoreanWeek(int week) {
		return koreanWeeks[week];
	}
	
	private static String koreanWeeks[] = {"", "일", "월", "화", "수", "목", "금", "토"};

	public static String getCalcMonth(String flg) throws ParseException {
		GregorianCalendar gCal = new GregorianCalendar(); 
		Calendar tCal = GregorianCalendar.getInstance();
		if(flg.equals("plus")){
			gCal.add(Calendar.MONTH, 2);
		}
		
		tCal.add(Calendar.MONTH, -1) ;
		int year = tCal.get(Calendar.YEAR);
		int month = tCal.get(Calendar.MONTH) + 1;
		int date = tCal.get(Calendar.DATE);
		
		/*
		int year = gCal.get(Calendar.YEAR);
		int month = gCal.get(Calendar.MONTH);
		if(month == 0){
			month = 12;
			year = year - 1;
		}
		int date =  gCal.get(Calendar.DAY_OF_MONTH);
		*/
		
		return year + "-" + ((month < 10) ? "0" + month : Integer.toString(month)) + "-" + ((date < 10) ? "0" + date : Integer.toString(date));
		
	}
	
	public static String getCalcDate(String flg) throws ParseException {
		GregorianCalendar gCal = new GregorianCalendar(); 
		Calendar tCal = GregorianCalendar.getInstance();
	
		if(flg.equals("plus")){
			gCal.add(Calendar.MONTH, 2);
		}
		
		tCal.add(Calendar.DATE, -7) ;
		int year = tCal.get(Calendar.YEAR);
		int month = tCal.get(Calendar.MONTH)+1 ;
		int date = tCal.get(Calendar.DATE);
		
		return year + "-" + ((month < 10) ? "0" + month : Integer.toString(month)) + "-" + ((date < 10) ? "0" + date : Integer.toString(date));
		
	}

	/**
	 * <PRE>
	 * 1. MethodName : day2Day
	 * 2. ClassName  : CalendarUtil
	 * 3. Comment    : 두 날짜 사이의 차이
	 * 4. 작성자     : ANJIUN
	 * 5. 작성일     : 2014. 3. 28.
	 * </PRE>
	 *   @return long		날짜 차이
	 *   @param startDate	시작 날짜
	 *   @param endDate		종료 날짜
	 *   @param format		날짜 형식
	 *   @throws Exception
	 */
	
	public static long day2Day(String startDate, String endDate, String format) throws Exception {
		if (format == null)
			format = "yyyy/MM/dd HH:mm:ss.SSS";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date sDate;
		Date eDate;
		long day2day = 0;
		try {
			sDate = sdf.parse(startDate);
			eDate = sdf.parse(endDate);
			day2day = (eDate.getTime() - sDate.getTime())
					/ (1000 * 60 * 60 * 24);
		} catch (Exception e) {
			throw new Exception("wrong format string");
		}

		return day2day;
	}

}
