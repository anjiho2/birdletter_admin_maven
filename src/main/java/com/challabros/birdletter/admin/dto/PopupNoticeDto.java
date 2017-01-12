package com.challabros.birdletter.admin.dto;

import lombok.Data;

@Data
public class PopupNoticeDto {
	private int rowNum;
	private int idx;
	private String title;
	private String content;
	private String imgFileName;
	private int useYn;
	private String startDate;
	private String endDate;
	private String createDate;
	private String targetUrl;
}
