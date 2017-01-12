package com.challabros.birdletter.admin.dto;

import lombok.Data;

@Data
public class CornInfoDto {
	private int rowNum;
	
	private int idx;
	
	private int cornIdx;
	
	private int cornCount;
	
	private String cornDesc;
	
	private int discountPercent;
	
	private int price;
}
