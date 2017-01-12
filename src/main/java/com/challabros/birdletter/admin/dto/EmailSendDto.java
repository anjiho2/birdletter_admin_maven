package com.challabros.birdletter.admin.dto;

import lombok.Data;

@Data
public class EmailSendDto {
	
	private String subject;
	
	private String content;
	
	private String receiver;
	
	public EmailSendDto() {}
	
	public EmailSendDto(String subject, String content, String receiver) {
		this.subject = subject;
		this.content = content;
		this.receiver = receiver;
	}

}
