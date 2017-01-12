package com.challabros.birdletter.admin.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class AdminDto implements Serializable{
	private String adminId;
	private String adminPass;
}
