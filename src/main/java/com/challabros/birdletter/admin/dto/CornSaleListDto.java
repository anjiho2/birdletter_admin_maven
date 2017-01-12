package com.challabros.birdletter.admin.dto;

import lombok.Data;

@Data
public class CornSaleListDto {
	private int rowNum;
	private String createDate;
	private Long userId;
	private String phoneNumber;
	private int cornPoint;
	private int popcornPoint;
	private String userName;
}
