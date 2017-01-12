package com.challabros.birdletter.admin.dto;

import lombok.Data;

@Data
public class CoinBuyLogDto {
private int rowNum;
	
	private int idx;
	private Long userId;
	private int cornIdx;
	private int popcornIdx;
	private String createDate;
	
	private String cornCount;
	private String cornDesc;
	private String popcornName;
	private int popcornPoint;
}
