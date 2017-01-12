package com.challabros.birdletter.admin.dto;

import lombok.Data;

@Data
public class AuthStatisticsDto {
	private int age;
	private int cnt;
	
	public AuthStatisticsDto() {}
	
	public AuthStatisticsDto(int age, int cnt) {
		this.age = age;
		this.cnt = cnt;
	}
	
	public static AuthStatisticsDto consume(AuthStatisticsDto domain) {
		return new AuthStatisticsDto(domain.getAge(), domain.getCnt());
	}
	
	public static AuthStatisticsDto defaultConsume(int age) {
		return new AuthStatisticsDto(age, 0);
	}
}
