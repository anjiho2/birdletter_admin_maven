package com.challabros.birdletter.admin.dto;

import lombok.Data;

@Data
public class GiftBoxListDto {
	private int idx;
	private long userId;
	private String giftTitle;
	private String giftType;
	private String productCode;
	private int coinCount;
	private int giftState;
	private String createDate;
	private String expireDate;
	private String receiveDate;
	private int expireDayCount;
	
	public GiftBoxListDto() {}
	
	public GiftBoxListDto(String giftType, String giftTitle, Long userId, 
			String productCode, int coinCount, int expireDayCount) {
		this.giftType = giftType;
		this.giftTitle = giftTitle;
		this.userId = userId;
		this.productCode = productCode;
		this.coinCount = coinCount;
		this.expireDayCount = expireDayCount;
	}
	
	public static GiftBoxListDto consume(String giftType, String giftTitle, Long userId, 
			String productCode, int coinCount, int expireDayCount) {
		return new GiftBoxListDto(giftType, giftTitle, userId, productCode, coinCount, expireDayCount);
	}
}
