package com.challabros.birdletter.admin.dto;

import lombok.Data;

@Data
public class UserItemBuyListDto {
	private int rowNum;
	private String start;
	private String end;
	
	private String phoneNumber;
	
	private int idx;
	private Long userId;
	private String itemCode;
	private int itemCategory;
	private int itemType;
	private String useYn;
	private String createDate;
	
	private String productCode;
	private String productName;
	
	private int popcornPrice;
	private int cornPrice;
	
	private String storeType;
}
