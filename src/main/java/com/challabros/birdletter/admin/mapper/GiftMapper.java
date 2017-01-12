package com.challabros.birdletter.admin.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.challabros.birdletter.admin.dto.GiftBoxListDomainDto;
import com.challabros.birdletter.admin.dto.GiftBoxListDto;
import com.challabros.birdletter.admin.dto.UserPresentDomainDto;

public interface GiftMapper {
	
	/** SELECT **/
	HashMap<String, Object> presentCountList();
	
	List<GiftBoxListDomainDto>presentList(HashMap<String, Object>paramMap);
	
	int presentListCnt(HashMap<String, Object>paramMap);
	
	int validateUpdateGiftState(@Param("giftIdx")Integer giftIdx);
	
	List<UserPresentDomainDto>userPresentList(Map<String, Object>paramMap);
	
	List<UserPresentDomainDto>userCoinPresentList(Map<String, Object>paramMap);
	
	List<UserPresentDomainDto>userBirdPresentList(Map<String, Object>paramMap);
	
	int userPresentListCnt(Map<String, Object>paramMap);
	
	int userCoinPresentListCnt(Map<String, Object>paramMap);
	
	int userBirdPresentListCnt(Map<String, Object>paramMap);
	
	List<GiftBoxListDomainDto> presentListAtExcel(HashMap<String, Object>paramMap);

	/** INSERT **/
	int insertGiftBoxList(@Param("list")List<GiftBoxListDto>arrayList);
	
	/** UPDATE **/
	int updateGiftState(@Param("giftIdx")int giftIdx, @Param("giftState")int giftState);
	
	
}
