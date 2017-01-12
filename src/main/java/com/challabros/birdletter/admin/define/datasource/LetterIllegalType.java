package com.challabros.birdletter.admin.define.datasource;

public enum LetterIllegalType {
	COMMERCIAL(1, "상업/홍보성"),
	SEXUAL(2, "음란/선정성"),
	ILLEGAL_INFO(3, "불법정보"),
	INSULT(4, "욕설/인신공격"),
	PRIVACY(5, "개인정보노출"),
	RIGHT(6, "권리침해"),
	ETC(7, "기타");
	
	int code;
	String kor;
	
	LetterIllegalType(int code, String kor) {
		this.code = code;
		this.kor = kor;
	}
	
	public int code() {
		return this.code;
	}
	
	public String kor() {
		return this.kor;
	}

}
