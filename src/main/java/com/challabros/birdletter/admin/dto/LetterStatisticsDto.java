package com.challabros.birdletter.admin.dto;

import lombok.Data;

@Data
public class LetterStatisticsDto {
	private String date;
	private int privateLetterCnt;
	private int openLetterCnt;
	private int total;
	
	public LetterStatisticsDto() {}
	
	public LetterStatisticsDto(String date, int privateLetterCnt, int openLetterCnt, int total) {
		this.date = date;
		this.privateLetterCnt = privateLetterCnt;
		this.openLetterCnt = openLetterCnt;
		this.total = total;
	}
	
	public static LetterStatisticsDto consume(LetterStatisticsDto dto) {
		return new LetterStatisticsDto(
				dto.getDate(),
				dto.getPrivateLetterCnt(),
				dto.getOpenLetterCnt(),
				dto.getTotal()
				);
	}
	
	public static LetterStatisticsDto defaultConsume(String date) {
		return new LetterStatisticsDto(date, 0, 0, 0);
	}
	
}
