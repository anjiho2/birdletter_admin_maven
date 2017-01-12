package com.challabros.birdletter.admin.define.datasource;

public enum UserStatusEnum {
	NORAML(1),
	LEAVE(2);
	
	private int userStatusCode;
	
	private UserStatusEnum(int userStatusCode) {
		this.userStatusCode = userStatusCode;
	}
	
	public int getUserStatusCode() {
		return this.userStatusCode;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
}
