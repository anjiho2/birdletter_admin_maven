package com.challabros.birdletter.admin.dto;

import lombok.Data;

@Data
public class ItemBuyRankDto {
	private int rowNum;
	private String productCode;
	private String productName;
	private int itemCategory;
	private int itemType;
	private int cnt;
	
	private String date;
	private int price;
}
