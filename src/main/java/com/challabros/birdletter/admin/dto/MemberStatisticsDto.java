package com.challabros.birdletter.admin.dto;

import lombok.Data;

@Data
public class MemberStatisticsDto {
	private String date;
	private int male;
	private int female;
	private int total;
	
	public MemberStatisticsDto() {
		// TODO Auto-generated constructor stub
	}
	
	public MemberStatisticsDto(String date, int male, int female, int total) {
		this.date = date;
		this.male = male;
		this.female = female;
		this.total = total;
	}
	
	public static MemberStatisticsDto consume(MemberStatisticsDto dto) {
		return new MemberStatisticsDto(
				dto.getDate(),
				dto.getMale(),
				dto.getFemale(),
				dto.getTotal()
		);
	}
	
	public static MemberStatisticsDto defaultConsume(String date) {
		return new MemberStatisticsDto(date, 0, 0, 0);
	}

}
