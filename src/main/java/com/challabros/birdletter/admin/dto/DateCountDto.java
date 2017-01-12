package com.challabros.birdletter.admin.dto;

import lombok.Data;

@Data
public class DateCountDto {
	private String date;
	private float cnt;
	private int userCnt;
	
	public DateCountDto() {}
	
	public DateCountDto(String date, float cnt) {
		this.date = date;
		this.cnt = cnt;
		
	}
	
	public static DateCountDto consume(DateCountDto dto) {
		return new DateCountDto(dto.getDate(), dto.getCnt());
	}
	
	public static DateCountDto defaultConsume(String date) {
		return new DateCountDto(date, 0);
	}
}
