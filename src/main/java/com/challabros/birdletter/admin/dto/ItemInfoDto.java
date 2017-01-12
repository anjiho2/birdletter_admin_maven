package com.challabros.birdletter.admin.dto;

import lombok.Data;

@Data
public class ItemInfoDto {
	private int rowNum;
	private int idx;
	private String itemCode;
	private String itemName;
	private String itemDesc;
	private String itemType;
	private String createDate;
}
