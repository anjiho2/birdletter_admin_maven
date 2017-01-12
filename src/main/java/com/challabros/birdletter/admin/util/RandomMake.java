package com.challabros.birdletter.admin.util;

import java.util.List;
import java.util.Random;

public class RandomMake {

	public static String getPhoneNumber() {
		String[] no = {"1","2","3","4","5","6","7","8","9"};
		StringBuffer sb = new StringBuffer();
		Random rm = new Random();
		sb.append("010");
		for (int i = 0; i < 8; i++) {
			sb.append(no[rm.nextInt(8)]);
		}
		return sb.toString();
	}
	
	public static String getUuId() {
		String[] str = {"A","B","C","D","E","F","G","H","I","J","K",
						"L","M","N","O","P","Q","R","S","T","U","V",
						"W","X","Y","Z","0","1","2","3","4","5","6","7","8","9"};
		StringBuffer sb = new StringBuffer();
		Random rn = new Random();
		for (int i = 0; i < 8; i++) {
			sb.append(str[rn.nextInt(36)]);
		}
		sb.append("-");
		for (int j = 0; j < 4; j++) {
			sb.append(str[rn.nextInt(36)]);
		}
		sb.append("-");
		for (int j = 0; j < 4; j++) {
			sb.append(str[rn.nextInt(36)]);
		}
		sb.append("-");
		for (int j = 0; j < 4; j++) {
			sb.append(str[rn.nextInt(36)]);
		}
		sb.append("-");
		for (int j = 0; j < 12; j++) {
			sb.append(str[rn.nextInt(36)]);
		}
		return sb.toString();
	}
	
	public static String getGender() {
		String[] gender = {"MALE","FEMALE"};
		StringBuffer sb = new StringBuffer();
		Random rn = new Random();
		for (int i = 0; i < 1; i++) {
			sb.append(gender[rn.nextInt(2)]);
		}
		return sb.toString();
	}
	
	 public static String getBirthDay(int year1, int year2) {
		 StringBuffer sb = new StringBuffer();
		 String m_month = "";
		 String m_day = "";
		 int year = (int) (Math.random() * (year2 - year1 + 1)) + year1;
		 int month = (int) (Math.random() * (12 - 1 + 1)) + 1;
		 int day = (int) (Math.random() * (31 - 1 + 1)) + 1;
		 if (month <= 9) {
			 m_month = "0"+month;
		 } else {
			 m_month = String.valueOf(month);
		 }
		 if (day <= 9) {
			 m_day = "0"+day;
		 } else {
			 m_day = String.valueOf(day);
		 }
		 sb.append(year);
		 sb.append("-");
		 sb.append(m_month);
		 sb.append("-");
		 sb.append(m_day);
		 return sb.toString();
	 }
	 
	 public static String getOsType() {
		 String[] os = {"IOS","ANDROID"};
		 StringBuffer sb = new StringBuffer();
		 Random rn = new Random();
		 for (int i = 0; i < 1; i++) {
			sb.append(os[rn.nextInt(2)]);
		}
		return sb.toString();
	 }
	 
	 public static long getUserId(int count) {
		 long userId = (long)(Math.random() * count)+1;
		 return userId;
	 }
	 
	 public static int getCornIdx(int count) {
		 int cornIdx = (int) (Math.random() * count)+1;
		 return cornIdx;
	 }
	 
	 public static String getProductCode(List<String> Arr) {
		 String productCodes[] = Arr.toArray(new String[Arr.size()]); 
		 StringBuffer sb = new StringBuffer();
		 Random rn = new Random();
		 for (int i = 0; i < 1; i++) {
			sb.append(productCodes[rn.nextInt(productCodes.length)]);
		}
		return sb.toString();
	 }
	
	public static void main(String[] args) {
		for (int i = 1; i < 300; i++) {
			System.out.println(getCornIdx(300));	
		} 
		
	}
}
