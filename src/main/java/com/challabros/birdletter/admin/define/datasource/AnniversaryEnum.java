package com.challabros.birdletter.admin.define.datasource;

/**
 * 기념일 알림 타입 정의(타입코드, 라벨, 서브내용, 날짜)
 * @author anjiho
 *
 */
public enum AnniversaryEnum  {
	THANKS_GIVING_DAY_EVENT(0, "추석맞이 팝콘선물 지급완료!!", "지금확인하세요.", "09-15"),
	FRIEND_BIRTHDAY(201, "", "생일 축하 편지를 보내주세요.", ""),
	NEW_YAER(203, "", "소중한 사람들에게 따뜻한 덕담 한마디 어떠세요?", "01-01"),
	CHINA_NEW_YEAR(204, "새해 첫 날\n음력 1월 1일", "님 새해 복 많이 받으세요.", "01-01"),
	VALENTINE_DAY(205, "발렌타인데이\n2월 14일", "사랑하는 사람에게 달콤한 속삭임을 전하세요.", "02-14"),
	PORK_BELLY_DAY(206, "삼겹살데이\n3월 3일", "가까운 사람과 오늘 저녁 삼겹살 어떠세요?", "03-03"),
	WHITE_DAY(207, "화이트데이\n3월 14일", "사랑하는 사람에게 달달한 편지 한 통 어떠세요?", "03-14"),
	MOTHERS_DAY(208, "어버이 날\n5월 8일", "오늘은 부모님께 사랑한다고 말해요.", "05-08"),
	TEACHER_DAY(209, "스승의 날\n5월 15일", "우리 선생님께 따뜻한 감사를 전하세요.", "05-15"),
	COMING_DAY(211, "", "성년이 된 지인에게 축하와 격려의 메세지를 보내보세요.", ""),
	THANKS_GIVING_DAY(212, "한가위\n음력 8월 15일", "가족과 함께 풍성하고 행복한 한가위 되세요.", "08-15"),
	KOREAN_DAY(213, "한글날\n10월 9일", "아름다운 우리말, 편지에 마음껏 담아 보내요.", "10-09"),
	PAEPAERO_DAY(214, "빼빼로데이\n11월 11일", "빼뺴로만 전하실껀가요? 진심을 담은 편지는 어떨까요?", "11-11"),
	CHRISTMAS(215, "크리스마스\n12월 25일", "가족과 친구들에게 크리스마스 인사를 전하세요.", "12-25");
	
	private int code;
	private String label;
	private String subContent;
	private String date;
	
	AnniversaryEnum(int code, String label, String subContent, String date) {
		this.code = code;
		this.label = label;
		this.subContent = subContent;
		this.date = date;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public String getSubContent() {
		return this.subContent;
	}
	
	public String getDate() {
		return this.date;
	}
}
