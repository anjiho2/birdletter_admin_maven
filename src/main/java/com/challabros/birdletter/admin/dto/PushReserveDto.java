package com.challabros.birdletter.admin.dto;

import lombok.Data;

@Data
public class PushReserveDto {
	private int idx;
	
	private String name;
	
	private String content;
	
	private String subTitle;
	
	private String sendDateTime;
	
	private int pushInfoId;
	
	private String createDate;
	
	private int retension;
	
	private String sendType;
	
	private int status;
	
	public PushReserveDto() {}
	
	public PushReserveDto(String name, String content, String sendDateTime, int retension, String sendType) {
		this.name = name;
		this.content = content;
		this.sendDateTime = sendDateTime;
		this.retension = retension;
		this.sendType = sendType;
	}
}
