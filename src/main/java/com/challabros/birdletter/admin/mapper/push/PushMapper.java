package com.challabros.birdletter.admin.mapper.push;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;

import com.challabros.birdletter.admin.dto.PushInfoDto;
import com.challabros.birdletter.admin.dto.PushReserveDto;
import com.challabros.birdletter.admin.dto.PushSendListDto;


@Resource(name = "sqlSessionFactoryBean2")
public interface PushMapper {
	
	/** SELECT **/
	List<PushInfoDto> pushInfoList(HashMap<String, Object>paramMap);
	
	int pushInfoListCnt(@Param("status")String status, @Param("name")String name, @Param("pushType")String pushType);
	
	int findPushInfoIdxByAnniversary(@Param("sendDateTime")String sendDateTime);
	
	PushInfoDto findPushInfoByAdmin(@Param("idx")int pushIdx);
	
	PushInfoDto findPushInfo(@Param("idx")int pushIdx);
	
	List<PushReserveDto> selectPushReserve(@Param("date")String date);
	
	/** INSERT **/
	void insertPushSendList(@Param("list")List<PushSendListDto>paramDto);
	
	void insertPushSendListAtBirthday(@Param("list")List<PushSendListDto>paramDto);
	
	void insertPushSendListAtBirthdayLog(@Param("list")List<PushSendListDto>paramDto);
	
	int pushInfoInsertbyBirthDay(PushInfoDto pushInfoDto);
	
	void insertPushSendListByGift(PushSendListDto pushSendListDto);
	
	int pushInfoInsert(PushInfoDto pushInfoDto);
	
	int insertReservePush(PushReserveDto pushReserveDto);
	
	/** DELETE **/
	void deletePushSendList();
	
}
