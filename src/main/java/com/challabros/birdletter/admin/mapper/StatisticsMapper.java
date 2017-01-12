package com.challabros.birdletter.admin.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.challabros.birdletter.admin.dto.AuthStatisticsDto;
import com.challabros.birdletter.admin.dto.DateCountDto;
import com.challabros.birdletter.admin.dto.DormantUserDto;
import com.challabros.birdletter.admin.dto.DownloadCountInfoDto;
import com.challabros.birdletter.admin.dto.ItemBuyRankDto;
import com.challabros.birdletter.admin.dto.LetterStatisticsDto;
import com.challabros.birdletter.admin.dto.MemberStatisticsDto;
import com.challabros.birdletter.admin.dto.UserProductBuyStatsicsDto;
import com.challabros.birdletter.admin.dto.UserProfileDto;

public interface StatisticsMapper {
	
	/** SELECT **/
	List<ItemBuyRankDto> cornSaleStatistics(@Param("year")String year);
	
	List<ItemBuyRankDto> cornSaleStatisticsAmount(@Param("year")String year);
	
	List<ItemBuyRankDto> popcornSaleStatistics(@Param("year")String year);
	
	List<ItemBuyRankDto> popcornSaleStatisticsAmount(@Param("year")String year);
	
	List<ItemBuyRankDto> cornSaleYearStatistics();
	
	List<ItemBuyRankDto> cornSaleYearStatisticsAmount();
	
	List<ItemBuyRankDto> popcornSaleYearStatistics();
	
	List<ItemBuyRankDto> popcornSaleYearStatisticsAmount();
	
	List<ItemBuyRankDto> userLetterSendCurrentStatistics(@Param("userId")Long userId);
	
	List<ItemBuyRankDto> userOpenLetterSendCurrentStatistics(@Param("userId")Long userId);
	
	List<UserProductBuyStatsicsDto> userProductBuyStatsics(@Param("phoneNumber")String phoneNumber);
	
	List<UserProductBuyStatsicsDto> userProductBuyStatsicsByPopcorn(@Param("phoneNumber")String phoneNumber);
	
	List<UserProductBuyStatsicsDto> userProductBuyStatsicsByCorn(@Param("phoneNumber")String phoneNumber);
	
	List<UserProfileDto> userGenderStatsics(HashMap<String, Object>paramMap);
	
	List<UserProfileDto> messageSendStaticsByGender(HashMap<String, Object>paramMap);
	
	List<UserProfileDto> userAgeStatics(HashMap<String, Object>paramMap);
	
	List<UserProfileDto> messageSendStaticsGroupByAges(HashMap<String, Object>paramMap);
	
	List<UserProfileDto> openLetterStaticsGroupByAge(HashMap<String, Object>paramMap);
	
	List<HashMap<String, Object>> userMessageSendStatistics(Map<String, String>paramMap);
	
	int userMessageSendStatisticsCnt(Map<String, String>paramMap);
	
	List<HashMap<String, Object>> userOpenLetterStatistics(Map<String, String>paramMap);
	
	int userOpenLetterStatisticsCnt(Map<String, String>paramMap);
	
	List<HashMap<String, Object>> userMessageSendByDailyStats(Map<String, String>paramMap);
	
	List<HashMap<String, Object>> userOpenLetterDailyStatistics(Map<String, String>paramMap);
	
	List<HashMap<String, Object>> userMessageSendByWeekStats(Map<String, String>paramMap);
	
	List<HashMap<String, Object>> userOpenLetterWeekStatistics(Map<String, String>paramMap);
	
	int checkDownloadCount(Map<String, String>paramMap);
	
	HashMap<String, Integer> getAccumulate();
	
	List<DownloadCountInfoDto> dailyDownloadStatistics(@Param("searchDate")String searchDate);
	
	List<DownloadCountInfoDto> weeklyDownloadStatistics(@Param("searchDate")String searchDate);
	
	List<DownloadCountInfoDto> monthlyDownloadStatistics(@Param("startMonth")String startMonth, @Param("endMonth")String endMonth);
	
	List<DownloadCountInfoDto> yearDownloadStatistics(@Param("thisYear")String thisYear);
	
	List<MemberStatisticsDto> dailyMemberRegStatistics(@Param("startDate")String startDate, @Param("today")String today);
	
	List<MemberStatisticsDto> weeklyMemberRegStatistics(@Param("searchDate")String searchDate);
	
	List<MemberStatisticsDto> monthlyMemberRegStatistics(@Param("startMonth")String startMonth, @Param("endMonth")String endMonth);
	
	List<MemberStatisticsDto> yearMemberRegStatistics(@Param("thisYear")String thisYear);
	
	List<DateCountDto> arpuStatistics(@Param("startMonth")String startMonth, @Param("endMonth")String endMonth);
	
	List<DateCountDto> arpuStatisticsByYear(@Param("year")String year);
	
	List<DateCountDto> arppuStatistics(@Param("startMonth")String startMonth, @Param("endMonth")String endMonth);
	
	List<DateCountDto> arppuStatisticsByYear(@Param("year")String year);
	
	List<LetterStatisticsDto> dailyLetterStatistics(@Param("date")String date);
	
	List<LetterStatisticsDto> weeklyLetterStatistics(@Param("date")String date);
	
	List<LetterStatisticsDto> monthlyLetterStatistics(@Param("startMonth")String startMonth, @Param("endMonth")String endMonth);
	
	List<LetterStatisticsDto> yearLetterStatistics(@Param("thisYear")String thisYear);
	
	List<MemberStatisticsDto> dailyDauStatistics(@Param("startDate")String startDate, @Param("today")String today);
	
	List<MemberStatisticsDto> weeklyDauStatistics(@Param("searchDate")String searchDate);
	
	List<MemberStatisticsDto> monthlyDauStatistics(@Param("startMonth")String startMonth, @Param("endMonth")String endMonth);
	
	List<MemberStatisticsDto> yearDauStatistics(@Param("thisYear")String thisYear);
	
	List<MemberStatisticsDto> mauStatisticsByYear(@Param("year")String year);
	
	List<MemberStatisticsDto> mauStatistics(@Param("startMonth")String startMonth, @Param("endMonth")String endMonth);
	
	List<AuthStatisticsDto> authStatisticsByAge(HashMap<String, Object>paramMap);
	
	List<DormantUserDto> dormantUserStatistics(HashMap<String, Object>paramMap);
	
	int dormantUserStatisticsCnt(@Param("searchDate")String searchDate, @Param("dayCount")int dayCount);
	
	List<HashMap<String, Object>> dauRetension(@Param("interval")int interval, @Param("day")String day);
	
	List<HashMap<String, Object>> regRetension(@Param("interval")int interval, @Param("day")String day);
	
	/** INSERT **/
	int insertDownloadCount(DownloadCountInfoDto downloadCountInfoDto); 
	
	/** UPDATE **/
	int updateDownloadCount(DownloadCountInfoDto downloadCountInfoDto);
	
	
	
}
