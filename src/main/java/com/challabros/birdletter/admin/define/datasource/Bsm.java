package com.challabros.birdletter.admin.define.datasource;

public enum Bsm implements BsmType {
	웃음(0), 인사(1), 사랑(2), 슬픔(3), 분노(4), 충격(5);

	private int code;
	
	Bsm(int code) {
		this.code = code;
	}
	
	@Override
	public int code() {
		// TODO Auto-generated method stub
		return this.code;
	}

	@Override
	public BsmType code(int code) {
		// TODO Auto-generated method stub
		Bsm result = null;
		for (Bsm each : Bsm.values()) {
			if (each.code() == code) {
				result = each;
				break;
			}
		}
		return result;
	}
	
	/**
	 * <PRE>
	 * 1. Comment : bsm타입에 따른 타입명 가져오기
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 03. 30
	 * </PRE>
	 * @param resType
	 * @return
	 */
	public static String getBsmTypeName(int resType) {
		String bsmTypeName = "";
		if (Bsm.웃음.code == resType) {
			bsmTypeName = Bsm.웃음.name();
		} else if (Bsm.인사.code == resType) {
			bsmTypeName = Bsm.인사.name();
		} else if (Bsm.사랑.code == resType) {
			bsmTypeName = Bsm.사랑.name();
		} else if (Bsm.슬픔.code == resType) {
			bsmTypeName = Bsm.슬픔.name();
		} else if (Bsm.분노.code == resType) {
			bsmTypeName = Bsm.분노.name();
		} else if (Bsm.충격.code == resType) {
			bsmTypeName = Bsm.충격.name();
		} else {
			bsmTypeName = "전체";
		}
		return bsmTypeName;
	}
}
