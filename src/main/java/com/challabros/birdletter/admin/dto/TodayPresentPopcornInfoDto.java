package com.challabros.birdletter.admin.dto;

import lombok.Data;

@Data
public class TodayPresentPopcornInfoDto {

	private int idx;
	
	private int minimumPopcorn;
	
	private int maxPopcorn;
	
	private int hourLimit;
	
	private int minuteLimit;
	
	private int heartRewardLimit;
	
	public TodayPresentPopcornInfoDto() {}
	
	public TodayPresentPopcornInfoDto(int idx, int minPopcorn, 
			int maxPopcorn,	int hour, int minute, int heartRewardLimit) {
		this.idx = idx;
		this.minimumPopcorn = minPopcorn;
		this.maxPopcorn = maxPopcorn;
		this.hourLimit = hour;
		this.minuteLimit = minute;
		this.heartRewardLimit = heartRewardLimit;
	}
	
	public static TodayPresentPopcornInfoDto consume(int idx, int minPopcorn, 
			int maxPopcorn,	int hour, int minute, int heartRewardLimit) {
		return new TodayPresentPopcornInfoDto(idx, minPopcorn, maxPopcorn, hour, minute, heartRewardLimit);
	}
}
