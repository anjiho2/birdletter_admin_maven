package com.challabros.birdletter.admin.dto;

import lombok.Data;

@Data
public class PushSendListDto {
	private int userId;
	private int pushIdx;
	private long targetUserId;
	private String pushLabel;
	private String pushSubTitle;
	private String pushSendDateTime;
	private int sendListStatus;	// 0:발송됨, 1:발송전
	private String sendType; // 9:기념일, 8:생일
	
	public PushSendListDto () {}
	
	public PushSendListDto(int userId, int pushIdx, String pushLabel, 
			String pushSubTitle, String pushSendDateTime, String sendType) {
		this.userId = userId;
		this.pushIdx = pushIdx;
		this.pushLabel = pushLabel;
		this.pushSubTitle = pushSubTitle;
		this.pushSendDateTime = pushSendDateTime;
		this.sendType = sendType;
	}
	
	public PushSendListDto(int userId, int pushIdx, Long targetUserId, String pushLabel, 
			String pushSubTitle, String pushSendDateTime, String sendType) {
		this.userId = userId;
		this.pushIdx = pushIdx;
		this.targetUserId = targetUserId;
		this.pushLabel = pushLabel;
		this.pushSubTitle = pushSubTitle;
		this.pushSendDateTime = pushSendDateTime;
		this.sendType = sendType;
	}
	
	public PushSendListDto(Long targetUserId, int pushIdx, String pushLabel, 
			String pushSubTitle, String pushSendDateTime, String sendType) {
		this.targetUserId = targetUserId;
		this.pushIdx = pushIdx;
		this.pushLabel = pushLabel;
		this.pushSubTitle = pushSubTitle;
		this.pushSendDateTime = pushSendDateTime;
		this.sendType = sendType;
	}
	
	
	public static PushSendListDto consume(int userId, int pushIdx, 
			String pushLabel, String pushSubTitle, String pushSendDateTime, String sendType) {
		return new PushSendListDto(userId, pushIdx, pushLabel, pushSubTitle, pushSendDateTime, sendType);
	}
	
	public static PushSendListDto consume(int userId, int pushIdx, Long targetUserId,
			String pushLabel, String pushSubTitle, String pushSendDateTime, String sendType) {
		return new PushSendListDto(userId, pushIdx, targetUserId, pushLabel, pushSubTitle, pushSendDateTime, sendType);
	}
	
	public static PushSendListDto giftConsume(Long targetUserId, int pushIdx, String pushLabel,
			String pushSubTitle, String pushSendDateTime, String sendType) {
		return new PushSendListDto(targetUserId, pushIdx, pushLabel, pushSubTitle, pushSendDateTime, sendType);
	}
}
