package com.challabros.birdletter.admin.dto;

import java.util.Date;

import lombok.Data;

@Data
public class UserProductBuyLogDto {
	private int rowNum;
	private String start;
	private String end;
	
	private int idx;
	private Long userId;
	private String productCode;
	private int cornPrice;
	private int popcornPrice;
	private String createDate;
	private String productName;
	
	private String phoneNumber;
	
	private Date createDate2;
}
