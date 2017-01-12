package com.challabros.birdletter.admin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.challabros.birdletter.admin.define.datasource.AnniversaryEnum;
import com.challabros.birdletter.admin.define.datasource.DataSource;
import com.challabros.birdletter.admin.define.datasource.DataSourceType;
import com.challabros.birdletter.admin.dto.EmailSendDto;
import com.challabros.birdletter.admin.dto.PagingDto;
import com.challabros.birdletter.admin.dto.PushInfoDto;
import com.challabros.birdletter.admin.dto.PushReserveDto;
import com.challabros.birdletter.admin.dto.PushSendListDto;
import com.challabros.birdletter.admin.dto.UserProfileDto;
import com.challabros.birdletter.admin.error.BirdAdminErrorCode;
import com.challabros.birdletter.admin.error.BirdAdminException;
import com.challabros.birdletter.admin.mapper.push.PushMapper;
import com.challabros.birdletter.admin.util.DateUtils;
import com.challabros.birdletter.admin.util.Util;
import com.challabros.birdletter.admin.util.Value;


@Service
public class PushService {
	
	final static Logger logger = LoggerFactory.getLogger(PushService.class);
	
	@Autowired
	private PushMapper pushMapper;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailService emailService;

	/**
	 * <pre>
	 * 1. Comment : 푸시정보 입력
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 02. 11
	 * </pre> 
	 * @param content
	 * @param subTitle
	 * @param sendType
	 * @param sendDateTime
	 * @return
	 */
	@DataSource(DataSourceType.PUSH)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean pushInfoInsert(String content, String subTitle, int sendType, String sendDateTime) {
		PushInfoDto pushInfoDto = new PushInfoDto();
		pushInfoDto.setName("기념일 알림");
		pushInfoDto.setContent(content);
		pushInfoDto.setSubTitle(subTitle);
		pushInfoDto.setSendDateTime(sendDateTime);
		
		int result = pushMapper.pushInfoInsert(pushInfoDto);
		if (result < 1) return false;
		return true;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 푸시정보 리스트
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 02. 11
	 * </pre> 
	 * @param sPage
	 * @param status
	 * @param pushName
	 * @return
	 */
	@DataSource(DataSourceType.PUSH)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<PushInfoDto> pushInfoList(int sPage, String status, String pushName) {
		String sortValue = "";
		if ("".equals(status)) sortValue = "all";
		else if ("pre".equals(status)) sortValue = "pre";
		else if ("after".equals(status)) sortValue = "after";
		
		PagingDto pagingDto = Util.getPaging(sPage, Value.pageInList10);
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("start", pagingDto.getStart());
		paramMap.put("end", pagingDto.getEnd());
		paramMap.put("status", Util.isNullValue(sortValue, ""));
		paramMap.put("name", Util.isNullValue(pushName, ""));
		paramMap.put("pushType", Value.ANNIVERSARY);
		
		List<PushInfoDto> Arr = pushMapper.pushInfoList(paramMap);
		return Arr;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 푸시정보 리스트 갯수
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 02. 11
	 * </pre> 
	 * @param status
	 * @param pushName
	 * @return
	 */
	@DataSource(DataSourceType.PUSH)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public int pushInfoListCnt(String status, String pushName) {
		String sortValue = "";
		if ("".equals(status)) sortValue = "all";
		else if ("pre".equals(status)) sortValue = "pre";
		else if ("after".equals(status)) sortValue = "after";
		return pushMapper.pushInfoListCnt(Util.isNullValue(sortValue, ""), Util.isNullValue(pushName, ""), Value.ANNIVERSARY);
	}
	
	/**
	 * <pre>
	 * 1. Comment : 생일 푸시정보 리스트
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 03. 15
	 * </pre> 
	 * @param sPage
	 * @param status
	 * @param pushName
	 * @return
	 */
	@DataSource(DataSourceType.PUSH)
	@Transactional(propagation = Propagation.REQUIRED)
	public List<PushInfoDto> birthdayPushInfoList(int sPage, String status, String pushName) {
		String sortValue = "";
		if ("".equals(status)) sortValue = "all";
		else if ("pre".equals(status)) sortValue = "pre";
		else if ("after".equals(status)) sortValue = "after";
		
		PagingDto pagingDto = Util.getPaging(sPage, Value.pageInList10);
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("start", pagingDto.getStart());
		paramMap.put("end", pagingDto.getEnd());
		paramMap.put("status", Util.isNullValue(sortValue, ""));
		paramMap.put("name", Util.isNullValue(pushName, ""));
		paramMap.put("pushType", Util.isNullValue(Value.BIRTHDAY, ""));
		
		List<PushInfoDto> Arr = pushMapper.pushInfoList(paramMap);
		return Arr;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 생일 푸시정보 리스트 갯수
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 03. 15
	 * </pre> 
	 * @param status
	 * @param pushName
	 * @return
	 */
	@DataSource(DataSourceType.PUSH)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public int birthdayPushInfoListCnt(String status, String pushName) {
		String sortValue = "";
		if ("".equals(status)) sortValue = "all";
		else if ("pre".equals(status)) sortValue = "pre";
		else if ("after".equals(status)) sortValue = "after";
		return pushMapper.pushInfoListCnt(Util.isNullValue(sortValue, ""), Util.isNullValue(pushName, ""), Value.BIRTHDAY);
	}
	
	/**
	 * <pre>
	 * 1. Comment : 날짜로 기념일 pushInfo 정보의 idx값 찾기
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 02. 11
	 * </pre> 
	 * @param sendDateTime
	 * @return
	 */
	@DataSource(DataSourceType.PUSH)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public int findPushInfoIdx(String sendDateTime) {
		if ("".equals(sendDateTime)) throw new BirdAdminException(BirdAdminErrorCode.BAD_REQUEST);
		return pushMapper.findPushInfoIdxByAnniversary(sendDateTime);
	}
	
	/**
	 * <pre>
	 * 1. Comment : 모든 유저에게 보낼 푸시정보 입력
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 02. 11
	 * </pre> 
	 * @param pushIdx
	 * @param age
	 * @param gender
	 * @param sendType
	 */
	@DataSource(DataSourceType.PUSH)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void insertPushSendList(int pushIdx, int age, String gender, int sendType) {
		if (pushIdx < 1) throw new BirdAdminException(BirdAdminErrorCode.BAD_REQUEST);
		
		//푸시정보 검색
		PushInfoDto pushInfoDto = null;
		if (sendType == 0) pushInfoDto = pushMapper.findPushInfoByAdmin(pushIdx);	//관리자 알림 발송일때
		else pushInfoDto = pushMapper.findPushInfo(pushIdx);
		
		//발송할 유저 검색
		List<Integer> Arr = new ArrayList<Integer>();
		
		if (age > 0 || !"".equals(gender)) Arr = userService.selectUserIdxByWhere(gender, age);
		else Arr = userService.selectUserIdx();	
		
		//푸시내용 입력
		if (pushInfoDto != null) {
			List<PushSendListDto>paramDto = new ArrayList<>();
			for (Integer userId : Arr) {
				PushSendListDto pushSendListDto = PushSendListDto.consume(
					userId, pushInfoDto.getIdx(), pushInfoDto.getContent(), 
					pushInfoDto.getSubTitle(), pushInfoDto.getSendDateTime(), 
					String.valueOf(sendType)
				);
				paramDto.add(pushSendListDto);
			}
			pushMapper.insertPushSendList(paramDto);
		}
	}
	
	@DataSource(DataSourceType.PUSH)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void insertPushSendListByNewYear(List<UserProfileDto>userList, 
			int pushIdx, String pushContent, String subTitle, String sendDateTime) {
		if (pushIdx < 1) throw new BirdAdminException(BirdAdminErrorCode.BAD_REQUEST);
		
		List<PushSendListDto>paramList = new ArrayList<>();
		for (UserProfileDto userProfileDto : userList) {
			String userName = userProfileDto.getUserName();
			String pushSubTitle = userName + subTitle;
			
			PushSendListDto sendListDto = PushSendListDto.consume(
				userProfileDto.getUserId(), pushIdx, 
				pushContent, pushSubTitle, sendDateTime, 
				String.valueOf(AnniversaryEnum.CHINA_NEW_YEAR.getCode()) 
			);
			paramList.add(sendListDto);
		}
		pushMapper.insertPushSendList(paramList);
	}
	
	@DataSource(DataSourceType.PUSH)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void insertPushSendListAtBirthDay(int pushIdx, List<Long>friendUserIds, 
			Long userId, String label, String subTitle, String sendDateTime) {
		if (pushIdx < 1) throw new BirdAdminException(BirdAdminErrorCode.BAD_REQUEST);
		
		if (friendUserIds.size() > 0) {
			List<PushSendListDto>paramList = new ArrayList<>();
			for (Long friendId : friendUserIds) {
				PushSendListDto pushSendListDto = PushSendListDto.consume(
					userId.intValue(), pushIdx, friendId, 
					label, subTitle, DateUtils.getPushTime(sendDateTime), 
					String.valueOf(AnniversaryEnum.FRIEND_BIRTHDAY.getCode())
				);
				paramList.add(pushSendListDto);
			}
			pushMapper.insertPushSendListAtBirthday(paramList);	
			pushMapper.insertPushSendListAtBirthdayLog(paramList);
		}
	}
	
	/**
	 * <pre>
	 * 1. Comment : 생일푸시알림 정보 입력
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 03. 14
	 * </pre> 
	 * @param name
	 * @param content
	 * @param subtitle
	 * @param sendDateTime
	 * @return
	 */
	@DataSource(DataSourceType.PUSH)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public int insertPushInfoByBirthDay(String name, String content, String subtitle, String sendDateTime) {
		PushInfoDto pushInfoDto = PushInfoDto.consume(name, content, subtitle, sendDateTime, Value.BIRTHDAY);
		pushMapper.pushInfoInsertbyBirthDay(pushInfoDto);
		return pushInfoDto.getIdx();
	}
	
	@DataSource(DataSourceType.PUSH)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public int insertPushInfo(String name, String content, String subTitle, String sendDateTime, String sendType) {
		PushInfoDto pushInfoDto = PushInfoDto.consume(name, content, subTitle, sendDateTime, sendType);
		pushMapper.pushInfoInsertbyBirthDay(pushInfoDto);
		return pushInfoDto.getIdx();
	}
	
	/**
	 * <pre>
	 * 1. Comment : 선물 알림 pushInfo 입력
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 11. 22
	 * </pre> 
	 * @param content
	 * @param subTitle
	 * @return
	 */
	@DataSource(DataSourceType.PUSH)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public int insertPushInfoByGift(String content, String subTitle) {
		PushInfoDto pushInfoDto = PushInfoDto.consume("선물 알림", content, subTitle, Util.returnNow(), Value.GIFT);
		pushMapper.pushInfoInsertbyBirthDay(pushInfoDto);
		return pushInfoDto.getIdx();
	}
	
	/**
	 * <pre>
	 * 1. Comment : 선물 알림 pushSendList 입력
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 11. 22
	 * </pre> 
	 * @param userId
	 * @param pushIdx
	 * @param pushContent
	 * @param pushSubTitle
	 * @param sendType
	 * @param sendDateTime
	 */
	@DataSource(DataSourceType.PUSH)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void insertPushSendListByGift(long userId, int pushIdx, 
			String pushContent, String pushSubTitle, int sendType, String sendDateTime) {
		PushSendListDto pushSendListDto = PushSendListDto.giftConsume(
			userId, pushIdx, pushContent, pushSubTitle, sendDateTime, String.valueOf(sendType)
		);
		pushMapper.insertPushSendListByGift(pushSendListDto);
	}
	
	/**
	 * <pre>
	 * 1. Comment : 푸시 발송된 목록 삭제 
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 12. 26
	 * </pre> 
	 */
	@DataSource(DataSourceType.PUSH)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void deletePushSendList() {
		pushMapper.deletePushSendList();
	}
	
	/**
	 * <pre>
	 * 1. Comment : 푸시 예약 발송 입력 
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2017. 01. 11
	 * </pre>  
	 * @param content
	 * @param subTitle
	 * @param sendDate
	 * @param time
	 * @param retenseion
	 * @return
	 * @throws Exception
	 */
	@DataSource(DataSourceType.PUSH)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public int reservePush(String content, String subTitle, String sendDate, String time, int retenseion) 
			throws Exception {
		String sendDateTime = DateUtils.localtimeToUTC(DateUtils.makeDateTimeSecond(sendDate, time));
		PushReserveDto reserveDto = new PushReserveDto(content, subTitle, sendDateTime, retenseion, Value.ADMIN);
		return pushMapper.insertReservePush(reserveDto);
	}
	
	/**
	 * <pre>
	 * 1. Comment : 예약된 푸시 목록 가져오기 
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2017. 01. 12
	 * </pre> 
	 * @param date
	 * @return
	 */
	@DataSource(DataSourceType.PUSH)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<PushReserveDto> selectPushReserve(String date) {
		List<PushReserveDto> Arr = new ArrayList<>();
		Arr = pushMapper.selectPushReserve(date);
		return Arr;
	}
	
	@DataSource(DataSourceType.PUSH)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void insertPushSendListByReserve(List<PushSendListDto>pushSendListDtos) {
		if (pushSendListDtos != null) {
			pushMapper.insertPushSendList(pushSendListDtos);
		}
	}

}
