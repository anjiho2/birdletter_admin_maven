package com.challabros.birdletter.admin.define.datasource;

public enum PopcornEnum {
	POPCORN_1(10, 1),
	POPCORN_2(50, 5),
	POPCORN_3(110, 10),
	POPCORN_4(600, 50),
	POPCORN_5(1300, 100),
	POPCORN_6(7000, 500);
	
	private int popcornPoint;
	private int cornPrice;
	
	private PopcornEnum(int popcornPoint, int cornPrice) {
		// TODO Auto-generated constructor stub
		this.popcornPoint = popcornPoint;
		this.cornPrice = cornPrice;
	}
	
	public int getPopcornPoint() {
		return this.popcornPoint;
	}
	
	public int getCornPrice() {
		return this.cornPrice;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
}
