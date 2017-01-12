package com.challabros.birdletter.admin.dto;

import lombok.Data;

@Data
public class GiftBoxListDomainDto {
	private int rowNum;
	private int idx;
	private String createDate;
	private String expireDate;
	private String giftTitle;
	private String phoneNumber;
	private String userName;
	private String birthDay;
	private String gender;
	private int giftState;
	private String receiveDate;
	private String productName;
	private int giftType;
	private int coinCount;
}
