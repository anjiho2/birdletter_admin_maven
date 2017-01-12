package com.challabros.birdletter.admin.dto;

import lombok.Data;

@Data
public class VersionInfoDto {

	private int idx;
	
	private String versionNumber;
	
	private int serverOnoff;
	
	private int serverStatusCode;
	
	private String serverStatusContent;
	
	private String serverStatusTitle;
	
	private String createDate;
	
	private String updateDate;
	
	private String device;
}
