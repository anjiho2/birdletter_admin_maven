package com.challabros.birdletter.admin.dto;

import java.util.Date;

import lombok.Data;

@Data
public class DormantUserDto {
	private int rowNum;
	private long userId;
	private String userName;
	private String createDate;
	private String gender;
	private int birthDay;
	private Date createDate2;
}
