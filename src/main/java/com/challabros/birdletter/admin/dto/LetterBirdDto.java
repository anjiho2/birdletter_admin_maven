package com.challabros.birdletter.admin.dto;

import lombok.Data;

@Data
public class LetterBirdDto {
	private int rowNum;
	private String start;
	private String end;
	private Long birdId;
	private Long ownerId;
	private String energyCon;
	private String birdName;
	private String birdEmotion;
	private String birdMood;
	private String birdBirthDay;
	private int birdSlot;
	private int totalHeartPoint;
	private String bodyType;
	
	private String productName;
}
