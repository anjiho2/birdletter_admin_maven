package com.challabros.birdletter.admin.dto;

import lombok.Data;

@Data
public class NestItemBuyLogDto {

	private int rowNum;
	
	private Long userId;
	
	private String phoneNumber;
	
	private String userName;
	
	private String productCode;
	
	private String itemName;
	
	private String createDate;
}
