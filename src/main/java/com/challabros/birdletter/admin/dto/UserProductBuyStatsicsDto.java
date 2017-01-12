package com.challabros.birdletter.admin.dto;

import lombok.Data;

@Data
public class UserProductBuyStatsicsDto {
	 
	private int rowNum;
	private long userId;
	private String phoneNumber;
	private int buyCnt;
	private int popcorn;
	private int corn;
	private String userName;

}
