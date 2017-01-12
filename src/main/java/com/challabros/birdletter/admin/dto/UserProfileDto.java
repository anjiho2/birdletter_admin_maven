package com.challabros.birdletter.admin.dto;

import lombok.Data;

@Data
public class UserProfileDto {
	/* 페이징 처리 관련 추가 */
	private String start = "";
	private String end = "";
	private int rowNum = 0; 
	
	/* BIRD_USER */
	private String phoneNumber = "";
	
	private int userId;
	private String uuId;
	private String facebookId;
	private String authToken;
	private String authDate;
	private String message = "";
	private String gender = "";
	private String userName = "";
	private String birthDay = "";
	private String college = "";
	private String company = "";
	private String createDate = "";
	private String updateDate = "";
	private String deleteYn = "";
	
	private int cornPoint;
	private int popcornPoint;
	
	private int cnt;
	private int age;
	private Integer age2;
	
	private String osType;
	
	private String appVersion;
	
	private int totalHeartPoint;
}
