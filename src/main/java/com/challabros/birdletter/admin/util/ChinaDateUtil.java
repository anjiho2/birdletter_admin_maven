package com.challabros.birdletter.admin.util;

import com.challabros.birdletter.admin.define.datasource.AnniversaryEnum;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.ChineseCalendar;

public class ChinaDateUtil {
	
	/**
	 * <pre>
	 * 1. Comment : 양력 날짜 입력하면 음력 날짜를 리턴
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 04. 29
	 * 4. 필요한 JAR : icu4j-4_8_1_1.jar 
	 * </pre>
	 * @param today(YYYY-MM-dd)
	 * @return String
	 */
	public static String ChinaDate(String today) {
		String chinaDate = "";
		
		ChineseCalendar chinaCal = new ChineseCalendar();
		Calendar cal = Calendar.getInstance();
		
		cal.set(Calendar.YEAR, Integer.parseInt(today.substring(0,4)));
		cal.set(Calendar.MONTH, Integer.parseInt(today.substring(5,7)) - 1);
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(today.substring(8,10)));
		chinaCal.setTimeInMillis(cal.getTimeInMillis());
		
		int chinaYY = chinaCal.get(ChineseCalendar.EXTENDED_YEAR) - 2637;
		int chinaMM = chinaCal.get(ChineseCalendar.MONTH) + 1;
		int chinaDD = chinaCal.get(ChineseCalendar.DAY_OF_MONTH);
		
		chinaDate += chinaYY;
		chinaDate += "-";
		
		if (chinaMM < 10) {
			chinaDate += "0" + Integer.toString(chinaMM);
		} else {
			chinaDate += Integer.toString(chinaMM);
		}
		
		chinaDate += "-";
		
		if (chinaDD < 10) {
			chinaDate += "0" + Integer.toString(chinaDD);
		} else {
			chinaDate += Integer.toString(chinaDD);
		}
		return chinaDate;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 음력 공휴일을 체크한다.
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 04. 29
	 * 4. 필요한 JAR : icu4j-4_8_1_1.jar
	 * </pre>
	 * @param date(YYYY-MM-dd)
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean isLunar(String date) throws Exception {
		boolean result = false;
		
		String[] arrLunar = {
				AnniversaryEnum.CHINA_NEW_YEAR.getDate(),     // 설날 
                AnniversaryEnum.THANKS_GIVING_DAY.getDate()   // 추석
		};
		
		ChineseCalendar chinaCal = new ChineseCalendar();
		Calendar cal = Calendar.getInstance();
		
		cal.set(Calendar.YEAR, Integer.parseInt(date.substring(0,4)));
		cal.set(Calendar.MONTH, Integer.parseInt(date.substring(5,7)) - 1);
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date.substring(8,10)));
		chinaCal.setTimeInMillis(cal.getTimeInMillis());
		
		int chinaMM = chinaCal.get(ChineseCalendar.MONTH) + 1;
		int chinaDD = chinaCal.get(ChineseCalendar.DAY_OF_MONTH);
		
		String chinaDate = "" ;     // 음력 날짜
         
		if(chinaMM < 10) {         // 월
			chinaDate += "0" + Integer.toString(chinaMM) ;
		} else {
			chinaDate += Integer.toString(chinaMM) ;
		}
		chinaDate += "-" ;          // 날짜 구분자
	         
		if(chinaDD < 10) {         // 일
			chinaDate += "0" + Integer.toString(chinaDD) ;
		} else {
			chinaDate += Integer.toString(chinaDD) ;
		}
		// 음력 공휴일 목록과 변환한 음력날짜가 일치하는지 비교
		for(int i=0; i < arrLunar.length; i++){
			String tmpLunar = arrLunar[i] ;
			if(tmpLunar.equals(chinaDate)) {
				result = true ;
			}
		}
		return result ;
	}
	
	public static void main(String[] args) throws Exception {
		boolean bl = ChinaDateUtil.isLunar(DateUtils.plusDay(Util.returnToDate("2017-01-27"), "YYYYMMDD", 1));
		System.out.println(bl);
	}

}
