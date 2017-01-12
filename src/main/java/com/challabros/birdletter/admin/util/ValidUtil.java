package com.challabros.birdletter.admin.util;

public class ValidUtil {

	/**
	 * 패스워드 유효성 체크
	 * @param password
	 * @return
	 */
	public static boolean isPasswordCharCheck(String password){
 
		if(password == null || "".equals(password)){
 
			return false;
 
		}
 
		boolean isNumber = false;
		boolean isSpecialChar = false;
		boolean isLowerChar = false;
		boolean isUpperChar = false;
		int chkCnt = 0;

		String validNumber = "0123456789";
		String validSpecialChar = "{}[]|\\~`!@#$^:?'";

		char tempChar;
 
		for(int i=0;i<password.length();i++){
			tempChar = password.charAt(i);

			for(int j=0; j<validNumber.length();j++){
				if(validNumber.charAt(j) == tempChar){
					isNumber = true;
					chkCnt++;
				}
			}

			for(int j=0; j<validSpecialChar.length();j++){
				if(validSpecialChar.charAt(j) == tempChar){
					isSpecialChar = true;
					chkCnt++;
				}
			}

			if((tempChar >= 'a') && (tempChar <= 'z')){
				isLowerChar = true;
				chkCnt++;
			}

			if((tempChar >= 'A') && (tempChar <= 'Z')){
				isUpperChar = true;
				chkCnt++;
			}
		}
 
		System.out.println(isNumber);
		System.out.println(isSpecialChar);
		System.out.println(isLowerChar);
		System.out.println(isUpperChar);

		if(chkCnt < 2){
			return false;
		}
		
		return true;
	}
}
