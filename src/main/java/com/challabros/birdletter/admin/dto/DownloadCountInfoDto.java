package com.challabros.birdletter.admin.dto;


import lombok.Data;

@Data
public class DownloadCountInfoDto {
	private String device;
	private int downloadCount;
	private String createDate;
	
	private int ios;
	private int android;
	private int total;
	
	public DownloadCountInfoDto() {
		// TODO Auto-generated constructor stub
	}
	
	public DownloadCountInfoDto(String device, int downloadCount, String createDate, int ios, int android,	int total) {
		this.device = device;
		this.downloadCount = downloadCount;
		this.createDate = createDate;
		this.ios = ios;
		this.android = android;
		this.total = total;
	}
	
	public static DownloadCountInfoDto consume(DownloadCountInfoDto dto) {
		return new DownloadCountInfoDto("", 0, dto.getCreateDate(), dto.getIos(), dto.getAndroid(), dto.getTotal());
	}
	
	public static DownloadCountInfoDto defaultConsume(String createDate) {
		return new DownloadCountInfoDto("", 0, createDate, 0, 0, 0);
	}
}
