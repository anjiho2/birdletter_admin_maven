package com.challabros.birdletter.admin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.challabros.birdletter.admin.dto.GiftBoxListDomainDto;
import com.challabros.birdletter.admin.dto.GiftBoxListDto;
import com.challabros.birdletter.admin.dto.PagingDto;
import com.challabros.birdletter.admin.dto.UserPresentDomainDto;
import com.challabros.birdletter.admin.error.BirdAdminErrorCode;
import com.challabros.birdletter.admin.error.BirdAdminException;
import com.challabros.birdletter.admin.manager.PushManager;
import com.challabros.birdletter.admin.mapper.GiftMapper;
import com.challabros.birdletter.admin.util.DateUtils;
import com.challabros.birdletter.admin.util.Util;
import com.challabros.birdletter.admin.util.Value;

@Service
public class GiftService {
	
	private final static Logger logger = LoggerFactory.getLogger(GiftService.class);
	
	@Autowired
	private PushManager pushManager;
	
	@Autowired
	private GiftMapper giftMapper;
	
	/**
	 * <pre>
	 * 1. Comment : 선물함 입력하기
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 08 .12
	 * </pre> 
	 * @param giftType
	 * @param giftTitle
	 * @param userIds
	 * @param productCode
	 * @param coinCount
	 * @param expireDayCount
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean insertGiftBoxList(String giftType, String giftTitle, 
			String[] userIds, String productCode, int coinCount, int expireDayCount) {
		if (userIds == null) throw new BirdAdminException(BirdAdminErrorCode.BAD_REQUEST);
		
		List<GiftBoxListDto>dtoList = new ArrayList<>();
		for (String userId : userIds) {
			GiftBoxListDto giftBoxListDto = new GiftBoxListDto(
					giftType, giftTitle, Long.parseLong(userId), 
					productCode, coinCount, expireDayCount
			);
			dtoList.add(giftBoxListDto);
			pushManager.giftPush(Long.parseLong(userId), "선물이 도착했어요", "선물이 도착했어요");
		}
		int result = giftMapper.insertGiftBoxList(dtoList);
		if (result < 1) return false;
		return true;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 선물함 총개수, 개봉 선물 개수, 미개봉 선물 개수
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 08 .12
	 * </pre> 
	 * @return
	 */
	@Transactional(readOnly=true, propagation=Propagation.REQUIRED)
	public HashMap<String, Object> presentCountList() {
		HashMap<String, Object>result = giftMapper.presentCountList();
		return result;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 선물함 리스트
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 08 .12
	 * </pre> 
	 * @param sPage
	 * @param startDate
	 * @param endDate
	 * @param searchType
	 * @param searchValue
	 * @return
	 */
	@Transactional(readOnly=true, propagation=Propagation.REQUIRED)
	public List<GiftBoxListDomainDto> presentList(int sPage, String startDate, 
			String endDate, String searchType, String searchValue) {
		PagingDto pagingDto = Util.getPaging(sPage, Value.pageIntLis15);
		
		HashMap<String, Object>paramMap = new HashMap<>();
		paramMap.put("start", pagingDto.getStart());
		paramMap.put("end", pagingDto.getEnd());
		paramMap.put("startDate", Util.isNullValue(DateUtils.getOclock(startDate), ""));
		paramMap.put("endDate", Util.isNullValue(DateUtils.getLastDayOclock(endDate), ""));
		paramMap.put("searchType", Util.isNullValue(searchType, ""));
		paramMap.put("searchValue", Util.isNullValue(searchValue, ""));
		
		List<GiftBoxListDomainDto>Arr = giftMapper.presentList(paramMap);
		return Arr;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 선물함 리스트 개수
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 08 .29
	 * </pre> 
	 * @param startDate
	 * @param endDate
	 * @param searchType
	 * @param searchValue
	 * @return
	 */
	@Transactional(readOnly=true, propagation=Propagation.REQUIRED)
	public int presentListCnt(String startDate, String endDate, String searchType, String searchValue) {
		HashMap<String, Object> paramMap = new HashMap<>();
		paramMap.put("startDate", Util.isNullValue(DateUtils.getOclock(startDate), ""));
		paramMap.put("endDate", Util.isNullValue(DateUtils.getLastDayOclock(endDate), ""));
		paramMap.put("searchType", Util.isNullValue(searchType, ""));
		paramMap.put("searchValue", Util.isNullValue(searchValue, ""));

		return giftMapper.presentListCnt(paramMap);
	}
	
	/**
	 * <pre>
	 * 1. Comment : 선물상태 변경
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 08 .29
	 * </pre> 
	 * @param giftIdx
	 * @param giftState
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean updateGiftState(int giftIdx, int giftState) {
		if (giftIdx < 1) throw new BirdAdminException(BirdAdminErrorCode.BAD_REQUEST);
		int result = giftMapper.updateGiftState(giftIdx, giftState);
		if (result < 1) return false;
		return true;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 선물상태 확인
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 09. 30
	 * </pre> 
	 * @param giftIdxs
	 * @return
	 */
	@Transactional(readOnly=true, propagation=Propagation.REQUIRED)
	public boolean validateUpdateGiftState(List<Integer>giftIdxs) {
		if (giftIdxs == null) throw new BirdAdminException(BirdAdminErrorCode.BAD_REQUEST);
		
		for (Integer giftIdx : giftIdxs) {
			int giftState = giftMapper.validateUpdateGiftState(giftIdx);
			if (giftState == 1) {
				return false;
			}
		}
		return true;
	}
	
	@Transactional(readOnly=true, propagation=Propagation.REQUIRED)
	public List<UserPresentDomainDto> userPresentList(String type, int sPage, String startDate, 
			String endDate, String senderPhoneNumber, String receiverPhoneNumber) {
		PagingDto pagingDto = Util.getPaging(sPage, Value.pageIntLis15);
		
		Map<String, Object>paramMap = new HashMap<>();
		paramMap.put("start", pagingDto.getStart());
		paramMap.put("end", pagingDto.getEnd());
		paramMap.put("startDate", Util.isNullValue(DateUtils.getOclock(startDate), ""));
		paramMap.put("endDate", Util.isNullValue(DateUtils.getLastDayOclock(endDate), ""));
		paramMap.put("senderPhoneNumber", Util.isNullValue(senderPhoneNumber, ""));
		paramMap.put("receiverPhoneNumber", Util.isNullValue(receiverPhoneNumber, ""));
		
		List<UserPresentDomainDto>Arr = new ArrayList<>();
		
		if (Value.ITEM.equals(type)) Arr = giftMapper.userPresentList(paramMap);
		else if (Value.COIN.equals(type)) Arr = giftMapper.userCoinPresentList(paramMap);
		else if (Value.BODY.equals(type)) Arr = giftMapper.userBirdPresentList(paramMap);
		
		return Arr;
	}
	
	@Transactional(readOnly=true, propagation=Propagation.REQUIRED)
	public int userPresentListCnt(String type, String startDate, 
			String endDate, String senderPhoneNumber, String receiverPhoneNumber) {
		int cnt = 0;
		
		Map<String, Object>paramMap = new HashMap<>();
		paramMap.put("startDate", Util.isNullValue(DateUtils.getOclock(startDate), ""));
		paramMap.put("endDate", Util.isNullValue(DateUtils.getLastDayOclock(endDate), ""));
		paramMap.put("senderPhoneNumber", Util.isNullValue(senderPhoneNumber, ""));
		paramMap.put("receiverPhoneNumber", Util.isNullValue(receiverPhoneNumber, ""));
		
		if (Value.ITEM.equals(type)) cnt = giftMapper.userPresentListCnt(paramMap);
		else if (Value.COIN.equals(type)) cnt = giftMapper.userCoinPresentListCnt(paramMap);
		else if (Value.BODY.equals(type)) cnt = giftMapper.userBirdPresentListCnt(paramMap);
		
		return cnt;
	}
	
	public List<GiftBoxListDomainDto> presentListAtExcel(String startDate, String endDate,
			String searchType, String searchValue) {
		HashMap<String, Object>paramMap = new HashMap<>();
		paramMap.put("startDate", Util.isNullValue(DateUtils.getOclock(startDate), ""));
		paramMap.put("endDate", Util.isNullValue(DateUtils.getLastDayOclock(endDate), ""));
		paramMap.put("searchType", Util.isNullValue(searchType, ""));
		paramMap.put("searchValue", Util.isNullValue(searchValue, ""));
		
		List<GiftBoxListDomainDto>Arr = giftMapper.presentListAtExcel(paramMap);
		return Arr;
	}

}
