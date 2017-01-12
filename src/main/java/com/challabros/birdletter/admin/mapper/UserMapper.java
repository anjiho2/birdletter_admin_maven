package com.challabros.birdletter.admin.mapper;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;

import com.challabros.birdletter.admin.dto.BirdUserDto;
import com.challabros.birdletter.admin.dto.CoinBuyLogDto;
import com.challabros.birdletter.admin.dto.LetterBirdDto;
import com.challabros.birdletter.admin.dto.UserProductBuyLogDto;
import com.challabros.birdletter.admin.dto.UserProfileDto;

public interface UserMapper {
	
	/** SELECT **/
	List<UserProfileDto> userProfileList(UserProfileDto userProfileDto);
	
	List<UserProfileDto> userProfileListAll(@Param("listType")String listTYpe);
	
	int userProfileCnt(@Param("userName")String userName, @Param("phoneNumber")String phoneNumber);
	
	UserProfileDto userProfileDetail(@Param("userId")int userId, @Param("userName")String userName, @Param("phoneNumber")String phoneNumber);
	
	List<CoinBuyLogDto> userCornBuyLogList(HashMap<String, String>paramMap);
	
	int userCornBuyLogListCnt(HashMap<String, String>paramMap);
	
	List<CoinBuyLogDto> popcornBuyLogList(HashMap<String, String>paramMap);
	
	int popcornBuyLogListCnt(HashMap<String, Object>paramMap);
	
	int findUserCornBuySum(@Param("userId")Long userId);
	
	int findUserPopcornBuySum(@Param("userId")Long userId);
	
	List<Integer> selectUserIdx();
	
	List<Integer> selectUserIdxByWhere(@Param("gender")String gender, @Param("age")int age);
	
	List<HashMap<String, Object>> findUserByBirthDay(@Param("birthDay")String birthDay);
	
	List<Long> findUserFriendShip(@Param("userId")Long userId);
	
	List<CoinBuyLogDto> popcornBuyLogListAll(@Param("userId")Long userId);
	
	String findUserPhoneNumber(@Param("userId")Long userId);
	
	List<HashMap<String, Object>> countAppVersion();
	
	int getCountTotalUser();
	
	int getCountCornBuyUser();
	
	int userLetterBirdListCnt(@Param("userId")Long userId, @Param("productName")String productName);
	
	List<LetterBirdDto> userLetterBirdList(LetterBirdDto letterBirdDto);
	
	List<UserProductBuyLogDto> userItemBuyListAll(@Param("userId") Long userId);
	
	List<Integer> selectUserIdByAuthLog(@Param("searchDate")String searchDate, @Param("authInverval")int authInverval);
	
	/** DELETE **/
	void deleteUserLetterBird(@Param("birdId")int birdId);
	
	/** UPDATE **/
	int updateUserPoint(HashMap<String, String>paramMap);
	
	int addUserPoint(@Param("userId") Long userId, @Param("cornPoint") int cornPoint, @Param("popcornPoint") int popcornPoint);
}
