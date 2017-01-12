package com.challabros.birdletter.admin.dto;

import lombok.Data;

@Data
public class UserPresentDomainDto {
	private int rowNum;
	
	private int idx;
	
	private String sender;
	
	private String receiver;
	
	private String createDate;
	
	private String itemName;
	
	private int coin;
	
	private int openYn;
	
	private String openDate;

}
