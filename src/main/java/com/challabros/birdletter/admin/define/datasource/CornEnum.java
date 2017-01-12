package com.challabros.birdletter.admin.define.datasource;

public enum CornEnum {
	CORN_1(10, 1100),
	CORN_2(22, 2200),
	CORN_3(55, 5500),
	CORN_4(115, 11000),
	CORN_5(240, 22000),
	CORN_6(600, 55000);
	
	private int cornCount;
	private int cornPrice;
	
	private CornEnum(int cornCount, int cornPrice) {
		// TODO Auto-generated constructor stub
		this.cornCount = cornCount;
		this.cornPrice = cornPrice;
	}
	
	public int getCornCount() {
		return this.cornCount;
	}
	
	public int getCornPrice() {
		return this.cornPrice;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	public static int getCornPrice(int cornCount) {
		int cornPrice = 0;
		if (cornCount > 0) {
			switch (cornCount) {
			case 10:
				cornPrice = CORN_1.getCornPrice();
				break;
			case 22:
				cornPrice = CORN_2.getCornPrice();
				break;
			case 55:
				cornPrice = CORN_3.getCornPrice();
				break;
			case 115:
				cornPrice = CORN_4.getCornPrice();
				break;
			case 240:
				cornPrice = CORN_5.getCornPrice();
				break;
			case 600:
				cornPrice = CORN_6.getCornPrice();
				break;
			default:
				break;
			}
		}
		return cornPrice;
	}

}
