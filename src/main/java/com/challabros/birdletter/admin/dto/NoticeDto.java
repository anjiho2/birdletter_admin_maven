package com.challabros.birdletter.admin.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class NoticeDto {
	private int rowNum;
	private int idx;
	private String title;
	private String content;
	private String createDate;
	private int viewYn;
	private String fileName;
	private int imgWidth;
	private int imgHeight;
	
	
	private MultipartFile uploadFile;
	private int imgType;
}
