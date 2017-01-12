package com.challabros.birdletter.admin.dto;

import java.util.List;

import lombok.Data;

@Data
public class PushInfoDto {
	private int rowNum;
	private int idx;
	private String name;
	private String content;
	private String subTitle;
	private String sendDateTime;
	private String sendType; // ANNIVERSARY:기념일, BIRTHDAY:생일
	private int sendStatus;	// 0:발송됨, 1:발송전
	
	List<String>userIdArr;
	
	public PushInfoDto() {}
	
	public PushInfoDto(String name, String content, 
			String subTitle, String sendDateTime, String sendType) {
		this.name = name;
		this.content = content;
		this.subTitle = subTitle;
		this.sendDateTime = sendDateTime;
		this.sendType = sendType;
	}
	
	public static PushInfoDto consume(String name, String content, 
			String subTitle, String sendDateTime, String sendType) {
		return new PushInfoDto(name, content, subTitle, sendDateTime, sendType);
	}
}
