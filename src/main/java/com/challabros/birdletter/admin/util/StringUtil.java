package com.challabros.birdletter.admin.util;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringUtil {
	
	/**
	 * arrayList -> string[] 변환
	 * @param arrayList
	 * @return
	 */
	public static String[] listToStringArray(List<String> arrayList) {
		String[] stringArray = arrayList.toArray(new String[arrayList.size()]);
		return stringArray;
	}
	
	/**
	 * stringArray -> arrayList 변환
	 * @param stringArray
	 * @return
	 */
	public static List<String> stringArrayToList(String[] stringArray) {
		ArrayList<String> Arr = new ArrayList<>();
		Arr = new ArrayList<String>(Arrays.asList(stringArray));
		return Arr;
	}
	
	/**
	 * 구분자로 문자 합치기
	 * @param array
	 * @param regx
	 * @return
	 */
	public static String stringJoin(String array[], String regx) {
		String str = "";
		
		for (int i=0; i<array.length; i++) {
			str += array[i];
			if (i < array.length-1) str += regx;
		}
		return str;
	}
	
	/**
	 * 구분자로 문자열 자르기
	 * @param str
	 * @param regx
	 * @return
	 */
	public static String[] stringSplit(String str, String regx) {
		String[] array = null;
		if (!"".equals(str)) array = str.split(regx);
		return array;
	}
	
	/**
	 * 콤마 split
	 * @param srcStr
	 * @return
	 */
	public static String[] splitComma(String srcStr) {
		String splitStr[] = null;
		if (!"".equals(srcStr)) {
			splitStr = srcStr.split("\\.");
		}
		return splitStr;
	}
	
	/**
	 * 문자열에 한글이 포함되어있는지 확인
	 * @param korStr
	 * @return
	 */
	public static boolean isKorean(String korStr) {
		boolean bl = false;
		if (!"".equals(korStr)) {
			try {
				korStr = new String(korStr.getBytes("utf-8"),"euc-kr");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(korStr.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")) bl = true; 
		}
		return bl;
	}
	
	/**
	 * 천단위 콤마찍기
	 * @param num
	 * @return
	 */
	public static String addThousandSeparatorCommas(String num) {
		String resultInt = "";
		if (!num.equals("")) {
			 int intValue = Integer.parseInt(num);
			 DecimalFormat commas = new DecimalFormat("#,###");
			 resultInt = (String)commas.format(intValue);
		}
		return resultInt;
	}
	
	/**
	 * 좌측 "0" 제거
	 * @param str
	 * @return
	 */
	public static String leaveLeftZero(String str) {
		String leavedStr = "";
		if (!"".equals(str)) {
			leavedStr = str.replace("0", "");
		}
		return leavedStr;
	}
	
	/**
	 * 문자배열 만들기
	 * @param strings
	 * @return
	 */
	public static String[] getStringArray(String... strings) {
		String[] stringArray = null;
		List<String>Array = new ArrayList<String>();
		
		if (strings.length > 0) {
			for (String string : strings) {
				Array.add(string);
			}
		}
		stringArray = Array.toArray(new String[Array.size()]);
		return stringArray;
	}
	
	public static void main(String[] args) {
		String[] array = stringSplit("1,2,3,4", ",");
		for (String str : array) {
			System.out.println(str);	
		}
	}

}
