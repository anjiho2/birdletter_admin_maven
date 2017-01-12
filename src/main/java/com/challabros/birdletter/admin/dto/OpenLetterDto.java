package com.challabros.birdletter.admin.dto;

import lombok.Data;

@Data
public class OpenLetterDto {
	private long openLetterId;
	private long letterId;
	private long ownerId;
	private int openLevel;
	private long bsmFileId;
	private int deleteYn;
	private String createDate;
	private String updateDate;
	private String title;
	private String openLetterType;
	private int sharedCount;
	private int heartCount;
	private int lastCommentSeq;
	private int letterSequence;
	private int letterCommentCount;
	private int letterReportCount;
	private int weeklyRanking;
	private int bestRanking;
	private int blockYn;
	
	private int cnt;
	private String gender;
	private String phoneNumber;
	private String userName;
	private int rowNum;
}
