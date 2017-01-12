package com.challabros.birdletter.admin.dto;

import lombok.Data;

@Data
public class OpenLetterIllegalReportDto {
	private int rowNum;
	private int idx;
	private long openLetterId;
	private long reportUserId;
	private String letterIllegalType;
	private int checkYn;
	private String reportDate;
	private String updateDate;
	private String reportMessage;
	
	private String userName;
	private String phoneNumber;
}
