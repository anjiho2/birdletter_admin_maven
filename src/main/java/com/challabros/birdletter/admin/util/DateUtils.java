package com.challabros.birdletter.admin.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateUtils {
	
	public final static DateTimeZone TIME_ZONE = DateTimeZone.UTC;
	public final static String DF_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	public static Date now() {
		DateTime dt = new DateTime(TIME_ZONE);
		return dt.toDate();
	}
	
	/**
	 * <pre>
	 * 1. Comment : 년, 월, 주, 요일을 입력하면 해당하는 날짜 리턴
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 05. 03
	 * 4. 요일(dayOfWeek) 값 : 1:일요일, 2:월요일, 3:화요일, 4:수요일, 5:목요일, 6:금요일, 7:토요일
	 * </pre>
	 * @param year
	 * @param month
	 * @param week
	 * @param dayOfWeek
	 * @param type
	 * @return
	 */
	public static String getDate(int year, int month, int week, int dayOfWeek, String type) {
		DecimalFormat df = new DecimalFormat("0");
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month-1);
		calendar.set(Calendar.WEEK_OF_MONTH, week);
		calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
		  
		String strMonth = df.format(calendar.get(Calendar.MONTH) + 1);
		String strDay = df.format(calendar.get(Calendar.DAY_OF_MONTH));
		String date = "";
		if ("KOREAN".equals(type)) {
			date = strMonth + "월" + strDay + "일";
		} else if ("INTEGER".equals(type)){
			if (Integer.valueOf(strMonth) < 10) {
				strMonth = "0" + strMonth; 
			}
			date = strMonth + "-" + strDay;	
		}
		return date;
	}
	
	/**
	 * <pre>
	 * 1. Comment : yyyy-mm-dd, yyyymmdd 형식의 값으로 yyyy-mm-dd 00:00:00 정각으로 변환
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 06. 14
	 * </pre> 
	 * @param yyyy_mm_dd
	 * @return string	yyyy-mm-dd 00:00:00
	 */
	public static String getOclock(String yyyy_mm_dd) {
		String dateTime = "";
		if (!yyyy_mm_dd.equals("")) {
			if (yyyy_mm_dd.contains("-")) {
				dateTime = yyyy_mm_dd + " " + "00:00:00";
			} else {
				String yyyy = yyyy_mm_dd.substring(0,4);
				String mm = yyyy_mm_dd.substring(4,6);
				String dd = yyyy_mm_dd.substring(6,8);
				dateTime = yyyy + "-" + mm + "-" + dd + " " + "00:00:00";
			}
		}
		return dateTime;
	}
	
	/**
	 * <pre>
	 * 1. Comment : yyyy-mm-dd, yyyymmdd 형식의 값으로 yyyy-mm-dd 23:59:59 정각으로 변환
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 06. 14
	 * </pre> 
	 * @param yyyy_mm_dd
	 * @return string	yyyy-mm-dd 00:00:00
	 */
	public static String getLastDayOclock(String yyyy_mm_dd) {
		if (yyyy_mm_dd == null) yyyy_mm_dd = "";
		String dateTime = "";
		if (!yyyy_mm_dd.equals("")) {
			if (yyyy_mm_dd.contains("-")) {
				dateTime = yyyy_mm_dd + " " + "23:59:59";
			} else {
				String yyyy = yyyy_mm_dd.substring(0,4);
				String mm = yyyy_mm_dd.substring(4,6);
				String dd = yyyy_mm_dd.substring(6,8);
				dateTime = yyyy + "-" + mm + "-" + dd + " " + "23:59:59";
			}
		}
		return dateTime;
	}
	
	/**
	 * <pre>
	 * 1. Comment : yyyy-mm-dd, yyyymmdd 형식의 값으로 yyyy-mm-dd 00:00:00 정각으로 변환
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 06. 14
	 * </pre> 
	 * @param yyyy_mm_dd
	 * @return string	yyyy-mm-dd 00:00:00
	 */
	public static String getPushTime(String yyyy_mm_dd) {
		if (yyyy_mm_dd == null) yyyy_mm_dd = "";
		String dateTime = "";
		if (!yyyy_mm_dd.equals("")) {
			if (yyyy_mm_dd.contains("-")) {
				dateTime = yyyy_mm_dd + " " + "15:00:00";
			} else {
				String yyyy = yyyy_mm_dd.substring(0,4);
				String mm = yyyy_mm_dd.substring(4,6);
				String dd = yyyy_mm_dd.substring(6,8);
				dateTime = yyyy + "-" + mm + "-" + dd + " " + "15:00:00";
			}
		}
		return dateTime;
	}
	
	/**
	 * <pre>
	 * 1. Comment : yyyy-mm-dd 형식의 값으로 더한 날짜를 구하기
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 07. 26
	 * </pre>
	 * @param yyyy_mm_dd
	 * @param type
	 * @param plusDay
	 * @return type:YYYY => yyyy, type:MMDD => MMdd, type:YYYYMMDD => yyyy-MM-dd)
	 */
	public static String plusDay(String yyyy_mm_dd, String type, int plusDay) {
		String MMdd = "";
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = dateFormat.parse(yyyy_mm_dd);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, plusDay);
		String strDate = dateFormat.format(cal.getTime());
		String[] str = strDate.split("-");
		if ("MMDD".equals(type)) {
			MMdd = str[1]+"-"+str[2];
		} else if ("YYYY".equals(type)) {
			MMdd = str[0];
		} else if ("YYYYMMDD".equals(type)) {
			MMdd = strDate;
		}
		return MMdd;
	}
	
	/**
	 * 입력받은 년월일로 minusDay만큼 일자 빼기
	 * @param yyyy_mm_dd
	 * @param minusDay
	 * @return
	 */
	public static String getMinusDay(String yyyy_mm_dd, int minusDay) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		
		try {
			date = dateFormat.parse(yyyy_mm_dd);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(calendar.DATE, -minusDay);
		String strDate = dateFormat.format(calendar.getTime());
		return strDate;
	}
	
	/**
	 * 입력한 년월일로 몇주인지 받아오기
	 * @param yyyymmdd
	 * @return
	 */
	public static int getWeek(String yyyymmdd) {
		int week = 0;
		String tmp = yyyymmdd;
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(tmp.substring(0, 4)));
		cal.set(Calendar.MONTH, Integer.parseInt(tmp.substring(4, 6)) - 1);
		cal.set(Calendar.DATE, Integer.parseInt(tmp.substring(6)));
		
		if( (cal.get(Calendar.MONTH) == 11 )
		&&(cal.getActualMaximum(Calendar.WEEK_OF_MONTH) == cal.get(Calendar.WEEK_OF_MONTH) ) 
		&&(cal.get(Calendar.WEEK_OF_YEAR) == 1)) {
			week = (cal.get(Calendar.WEEK_OF_YEAR)-1);//(cal.get(Calendar.YEAR)+1) +"년"	
		} else {
			week = (cal.get(Calendar.WEEK_OF_YEAR)-1);//((cal.get(Calendar.YEAR))
		}
		return week;
	}
	
	/**
	 * 기준 주차로 원하는 구간의 주차 리스트 가져오기 
	 * @param week
	 * @param minusWeek
	 * @return
	 */
	public static String[] getDiffWeek(int week, int minusWeek) {
		int startWeek = 0;
		
		if ((0 <= week) && (week <= 12)) {
			startWeek = week + 40; 
		} else {
			startWeek = week - minusWeek;	
		}
		List<String> list = new ArrayList<String>();
		//새해 0쨰주부터 12주까지
		if ((0 <= week) && (week <= 12)) {
			int j = 1;
			for (int i = startWeek; i <= week+53; i++) {
				if (i >= 53) {
					list.add(String.valueOf(j));
					j++;
				} else {
					list.add(String.valueOf(i));
				}
			}
		} else if (week >= 13 && week <= 52) {
			for (int i = startWeek; i <= week-1; i++) {
				list.add(String.valueOf(i));
			}
		}
		String[] result = list.toArray(new String[list.size()]);
		return result;
	}
	
	/**
	 * 입력받은 년도로 부터 상하위 년도 리스트 리턴
	 * @param year(기준년도)
	 * @param count(몇년도를 보여줄 값 : 1, 2, 3,.....)
	 * @param type ("P":기준년도로 부터 상위년도, "M":기준년도로 부터 하위년도)
	 * @return
	 */
	public static String[] getYearList(int year, int count, String type) {
		List<String>list = new ArrayList<String>();
		
		if (!"".equals(type)) {
			//year기준년도로 부터 plusYear까지의 년도 리스트를 가져온다
			if ("P".equals(type)) {
				int plusYear = year + count;
				for (int i = year; i <= plusYear; i++) {
					list.add(String.valueOf(i));
				}
			//minusYear기준년도로 부터 year까지의 년도 리스트를 가져온다
			} else if ("M".equals(type)) {
				int minusYear = year - count;
				for (int i = minusYear; i <= year; i++) {
					list.add(String.valueOf(i));
				}
			}
		}
		String yearList[] = list.toArray(new String[list.size()]);
		return yearList;
	}
	
	/**
	 * 입력받은 주차 수로 해당 주차에 해당하는 시작 년월일 리턴
	 * @param week
	 * @return
	 */
	public static String getDateByStartWeek(int week) {
		final long calendarWeek = week;
		LocalDate desiredDate = LocalDate.now()
		            .with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, calendarWeek)
		            .with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
		String date = desiredDate.toString(); 
		return date;
	}
	
	/**
	 * 입력받은 주차 수로 해당 주차에 해당하는 마지막 년월일 리턴
	 * @param week
	 * @return
	 */
	public static String getDateByEndWeek(int week) {
		final long calendarWeek = week;
		LocalDate desiredDate = LocalDate.now()
		            .with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, calendarWeek)
		            .with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));
		String date = desiredDate.toString();
		return date;
	}
	
	/**
	 * 현재 년도를 리턴
	 * @return
	 */
	public static String getNowYear() {
		Date today = new Date();
		SimpleDateFormat sdf;
		sdf = new SimpleDateFormat("yyyy");
		return sdf.format(today);
	}
	
	/**
	 * 나이계산 
	 * @param yyyyMMdd(yyyy-mm-dd)
	 * @param ageType (KOR:한국나이, ENG:만나이)
	 * @return
	 */
	public static int getAge(String yyyyMMdd, String ageType) {
		int age = 0;
		int year = 0;
		int month = 0;
		int day = 0;
		if (!"".equals(yyyyMMdd)) {
			
			String[] blank_ymd_split = yyyyMMdd.split(" ");
			String[] ymd_split = blank_ymd_split[0].split("-");
			
			year = Integer.parseInt(ymd_split[0]);
			month = Integer.parseInt(ymd_split[1]);
			day = Integer.parseInt(ymd_split[2]);
		}
		Calendar calendar = Calendar.getInstance();
		if (ageType.equals("KOR")) {
			calendar.set(Calendar.YEAR, year-1);	
		} else if (ageType.equals("ENG")) {
			calendar.set(Calendar.YEAR, year);
		}
		calendar.set(Calendar.MONTH, month-1);
		calendar.set(Calendar.DATE, day);
		
		Calendar now = Calendar.getInstance();
		age = now.get(Calendar.YEAR) - calendar.get(Calendar.YEAR);
		if ( (calendar.get(Calendar.MONTH) > now.get(Calendar.MONTH)) 
				|| ( calendar.get(Calendar.MONTH) == now.get(Calendar.MONTH)
						&& calendar.get(Calendar.DAY_OF_MONTH) > now.get(Calendar.DAY_OF_MONTH)) ) {
			age--;
		}
		return age;
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
		return DateTimeFormat.forPattern(pattern).withZone(TIME_ZONE).parseDateTime(dateStr).toDate();
	}
	
	public static Date stringToDate(String dateStr) {
		return stringToDate(dateStr, DateUtils.DF_TIME_PATTERN);
	}
	
	/**
	 * 데이트 객체를 패턴에 따라 문자로 변환한다.
	 *
	 * @param date 데이트 객체.
	 * @param pattern 문자열 패턴
	 * @return 패턴화 문자.
	 */
	public static String dateToStr(Date date, String pattern){
		if(date == null){
			return null;
		}
		DateTime dt = new DateTime(date, TIME_ZONE);
		return dt.toString(pattern);
	}
	
	/**
	 * <pre>
	 *		1. Comment : 데이트 객체로부터 일자 문자열을 리턴한다. 기본 포맷 >> yyyy-MM-dd HH:mm:ss
	 *		2. 작성자 : 안지호
	 *		3. 작성일 : 2016. 09. 30
	 * </pre>
	 * @param date
	 * @return
	 */
	public static String dateToStr(Date date) {
		return dateToStr(date, DateUtils.DF_TIME_PATTERN);
	}
	
	/**
	 * 현재 날짜와 입력 날짜 크기 비교 
	 * @param dateStr(yyyy-mm-dd hh:mm:ss)
	 * @param splitYn(yyyy-mm-dd hh:mm:ss.0) 콤마가 있을때 1 값을 입력하여 자른다
	 * @return 현재보다 입력한 날짜가 후면 1, 전이면 -1
	 */
	public static int compareToDateNow(String dateStr, int splitYn) {
		int compare = 0;
		String[] expireDate = null;
		Date now = DateUtils.stringToDate(DateUtils.dateToStr(DateUtils.now()));
		if (splitYn == 1) {
			expireDate = StringUtil.splitComma(dateStr);
			compare = DateUtils.stringToDate(expireDate[0]).compareTo(now);
		} else {
			compare = DateUtils.stringToDate(dateStr).compareTo(now);
		}
		return compare;
	}
	
	public static List<String> getDayOfWeek() {
		List<String> Arr = new ArrayList<>();
		Arr.add(0, "일");
		Arr.add(1, "월");
		Arr.add(2, "화");
		Arr.add(3, "수");
		Arr.add(4, "목");
		Arr.add(5, "금");
		Arr.add(6, "토");
		return Arr;
	}
	
	/**
	 * timeMillis to Date
	 * @param timeMill
	 * @return
	 */
	public static String timeMillToStr(long timeMill) {
		return DateUtils.dateToStr(new Date(timeMill));
	}
	
	/**
	 * Date to timeMillis
	 * @param date
	 * @param timeZone
	 * @return
	 * @throws ParseException
	 */
	public static long DateToTimeMill(String date, TimeZone timeZone) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.setTimeZone(timeZone);
		long timeMill =  sdf.parse(date).getTime();
		return timeMill;
	}
	
	/**
	 * timeMilliSecond로 시분초의 차이를 구한다 
	 * @param beginTime
	 * @param endTime
	 * @param dateFormat
	 * @param type("HOUR", "MINUTE", "SECOND")
	 * @return
	 * @throws Exception
	 */
	public static String getElapsedTime(String beginTime, String endTime, 
			String dateFormat, String type) throws Exception {
		String diff = "";
		if (dateFormat == null) dateFormat = "yyyy-MM-dd HH:mm:ss";
		DateFormat df = new SimpleDateFormat(dateFormat);
		
		Date beginDate = df.parse(beginTime);
		Date endDate = df.parse(endTime);
		
		Long gap = (endDate.getTime() - beginDate.getTime()) /1000;
		
		Long hourGap = gap / 60 / 60;
		Long reminder = ((Long)(gap / 60)) % 60;
		Long minuteGap = reminder;
		Long secondGap = gap % 60;
		
		if (hourGap > 99) hourGap = (long)99;
		
		if ("HOUR".equals(type)) {
			diff = String.valueOf(hourGap);
		} else if ("MINUTE".equals(type)) {
			diff = String.valueOf(minuteGap);
		} else if ("SECOND".equals(type)) {
			diff = String.valueOf(secondGap);
		}
		return diff;
	}
	
	/**
	 * 입력받은 년월로 1일~ 30,31일까지 리스트 받아오기
	 * @param yyyymmdd
	 * @return
	 */
	public static List<String> getMonthOfDays(String yyyymmdd) {
		String[] ymd = StringUtil.stringSplit(yyyymmdd, "-");
		int year = Integer.parseInt(ymd[0]);
		int month = Integer.parseInt(ymd[1]);
		int dayOfMonth = 0;
		
		if (year >= 1000 && month >= 1 && month <= 12) {
			switch (month) {
			case 2:
				if ((year%4 == 0 && year%100 != 0) || (year%400 == 0)) {
					dayOfMonth = 29;
				} else {
					dayOfMonth = 28;
				}
				break;
			case 1: case 3: case 5: case 7: case 8: case 10: case 12:
				dayOfMonth = 31;
			break;
			default:
				dayOfMonth = 30;
			break;
			}
		}
		List<String> Arr = new ArrayList<String>();
		for (int i=0; i<dayOfMonth; i++) {
			String yyyy_mm_dd = plusDay(yyyymmdd, "YYYYMMDD", i);
			Arr.add(yyyy_mm_dd);
		}
		return Arr;
	}
	
	/**
	 * 로커시간을 UTC시간으로 변환
	 * @param inputdatetime
	 * @return
	 * @throws Exception
	 */
	public static String localtimeToUTC(String inputdatetime) throws Exception {
	    String utcTime = null;
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    TimeZone tz = TimeZone.getDefault();
	
	    try {
	        Date parseDate = sdf.parse(inputdatetime);
	        long milliseconds = parseDate.getTime();
	        int offset = tz.getOffset(milliseconds);
	        utcTime = sdf.format(milliseconds - offset);
	        utcTime = utcTime.replace("+0000", "");
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new Exception(e);
	    }
	    return utcTime;
	}

	public static String makeDateTimeSecond(String yyyymmdd, String hhmm) {
		String dateTimeSecond = null;
		dateTimeSecond = yyyymmdd + " " + hhmm + ":00";
		return dateTimeSecond;
	}
	
	
	public static void main(String[] args) throws Exception {
		
	}
	
}
