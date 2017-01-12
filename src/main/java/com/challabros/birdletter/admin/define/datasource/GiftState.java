package com.challabros.birdletter.admin.define.datasource;

public enum GiftState {
	PRE_RECEIVE(0),	//수령전 
	RECEIVE(1), //수령완료
	EXPIRE(2), //만료
	DELETE(3), //삭제
	CANCEL(4);	//지급취소
	
	private int giftStateCode;
	
	private GiftState(int giftStateCode) {
		this.giftStateCode = giftStateCode;
	}
	
	public int getGiftStateCode() {
		return this.giftStateCode;
	}
	

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	
}
