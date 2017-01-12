package com.challabros.birdletter.admin.dto;

import lombok.Data;

@Data
public class ItemTypeDto {
	
	private String itemCode;
	
	private String itemName;
	
	public ItemTypeDto(String itemCode, String itemName) {
		this.itemCode = itemCode;
		this.itemName = itemName;
	}

}
