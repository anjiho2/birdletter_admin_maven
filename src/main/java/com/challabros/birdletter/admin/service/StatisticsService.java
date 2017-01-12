package com.challabros.birdletter.admin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.challabros.birdletter.admin.dto.AuthStatisticsDto;
import com.challabros.birdletter.admin.dto.DateCountDto;
import com.challabros.birdletter.admin.dto.DormantUserDto;
import com.challabros.birdletter.admin.dto.DownloadCountInfoDto;
import com.challabros.birdletter.admin.dto.ItemBuyRankDto;
import com.challabros.birdletter.admin.dto.LetterStatisticsDto;
import com.challabros.birdletter.admin.dto.MemberStatisticsDto;
import com.challabros.birdletter.admin.dto.PagingDto;
import com.challabros.birdletter.admin.dto.UserProductBuyStatsicsDto;
import com.challabros.birdletter.admin.dto.UserProfileDto;
import com.challabros.birdletter.admin.mapper.StatisticsMapper;
import com.challabros.birdletter.admin.util.BirdAdminUtil;
import com.challabros.birdletter.admin.util.DateUtils;
import com.challabros.birdletter.admin.util.StringUtil;
import com.challabros.birdletter.admin.util.Util;
import com.challabros.birdletter.admin.util.Value;;

@Service
public class StatisticsService {
	
	final static Logger logger = LoggerFactory.getLogger(StatisticsService.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private StatisticsMapper statisticsMapper;
	
	/**
	 * <pre>
	 * 1. Comment : 콘판매 월간 통계
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 01 .11
	 * </pre>
	 * @param year
	 * @param searchType
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<HashMap<String, Object>> cornSaleStatistics(String year, String searchType) throws DataAccessException, Exception {
		List<HashMap<String, Object>> returnList = new ArrayList<HashMap<String, Object>>();
		
		try {
			List<ItemBuyRankDto> Arr = new ArrayList<ItemBuyRankDto>();
			if (Value.COUNT.equals(searchType)) Arr = statisticsMapper.cornSaleStatistics(year);
			else if (Value.AMOUNT.equals(searchType)) Arr = statisticsMapper.cornSaleStatisticsAmount(year);
			
			//1년 1월~12월까지 리스트 가져오기
			String monthList[] = Util.returnYYYY_MM(year);
			if (Arr.size() > 0) {
				for (int i = 0; i < monthList.length; i++) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					for (int j = 0; j < Arr.size(); j++) {
						if (Arr.get(j).getDate().equals(monthList[i])) {
							map.put("create_date", Arr.get(j).getDate());
							map.put("cnt", Arr.get(j).getCnt());
							break;
						} else {
							map.put("create_date", monthList[i]);
							map.put("cnt", 0);
						}
					}
					returnList.add(map);
				}
			} else {
				for (int i = 0; i < monthList.length; i++) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("create_date", monthList[i]);
					map.put("cnt", 0);
					returnList.add(map);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 팝콘판매 월간 통계
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 01 .11
	 * </pre>
	 * @param year
	 * @param searchType
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<HashMap<String, Object>> popcornSaleStatistics(String year, String searchType) {
		List<HashMap<String, Object>> returnList = new ArrayList<HashMap<String, Object>>();
		
		try {
			List<ItemBuyRankDto> Arr = new ArrayList<ItemBuyRankDto>();
			if (Value.COUNT.equals(searchType)) Arr = statisticsMapper.popcornSaleStatistics(year);
			else if (Value.AMOUNT.equals(searchType)) Arr = statisticsMapper.popcornSaleStatisticsAmount(year);
			//1년 리스트
			String monthList[] = Util.returnYYYY_MM(year);
			if(Arr.size() > 0) {
				for (int i = 0; i < 12; i++) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					for (int k = 0; k < Arr.size(); k++) {
						if (Arr.get(k).getDate().equals(monthList[i])) {
							map.put("create_date", Arr.get(k).getDate());
							map.put("cnt", Arr.get(k).getCnt());
							break;
						} else if (!Arr.get(k).getDate().equals(monthList[i])) {
							map.put("create_date", monthList[i]);
							map.put("cnt", 0);
						}
					}
					returnList.add(map);
				}
			} else {
				for (int i = 0; i < 12; i++) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("create_date", monthList[i]);
					map.put("cnt", 0);
					returnList.add(map);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 콘판매 년간 통계
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 01 .11
	 * </pre>
	 * @param searchType
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<ItemBuyRankDto> cornSaleYearStatistics(String searchType) {
		List<ItemBuyRankDto> Arr = new ArrayList<ItemBuyRankDto>();
		
		try {
			if (Value.COUNT.equals(searchType)) Arr = statisticsMapper.cornSaleYearStatistics();
			else if (Value.AMOUNT.equals(searchType)) Arr = statisticsMapper.cornSaleYearStatisticsAmount();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return Arr;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 팝콘판매 년간 통계
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 01 .11
	 * </pre> 
	 * @param searchType
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<ItemBuyRankDto> popcornSaleYearStatistics(String searchType) {
		List<ItemBuyRankDto> Arr = new ArrayList<ItemBuyRankDto>();
		
		try {
			if (Value.COUNT.equals(searchType)) Arr = statisticsMapper.popcornSaleYearStatistics();
			else if (Value.AMOUNT.equals(searchType)) Arr = statisticsMapper.popcornSaleYearStatisticsAmount();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return Arr;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 사용자 레터 발송건수 통계(최근7일)
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 01 .11
	 * </pre>
	 * @param userId
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<HashMap<String, Object>> userLetterSendCurrentStatistics(Long userId, String letterType, String day7Ago, String today) {
		List<HashMap<String, Object>> returnList = new ArrayList<HashMap<String, Object>>();
		
		try {
			List<ItemBuyRankDto> Arr = new ArrayList<ItemBuyRankDto>();
			if (userId > 0) {
				HashMap<String, Long> paramMap = new HashMap<String, Long>();
				paramMap.put("userId", userId);
				
				if ("private".equals(letterType)) {
					Arr = statisticsMapper.userLetterSendCurrentStatistics(userId);
				} else if ("open".equals(letterType)) {
					Arr = statisticsMapper.userOpenLetterSendCurrentStatistics(userId);
				}
				
				//최근 일주일 리스트
				String weekList[] = Util.getDiffDays(day7Ago, today);
				
				if (Arr.size() > 0) {
					for (int i = 0; i < weekList.length; i++) {
						HashMap<String, Object> map = new HashMap<String, Object>();
						for (int k=0; k < Arr.size(); k++) {
							if (Arr.get(k).getDate().equals(Util.convertToYYYY_MM_DD(weekList[i]))) {
								logger.info("equals");
								map.put("create_date", Arr.get(k).getDate());
								map.put("cnt", Arr.get(k).getCnt());
								break;
							} else {
								map.put("create_date", Util.convertToYYYY_MM_DD(weekList[i]));
								map.put("cnt", 0);
							}
						}
						returnList.add(map);
					}
				} else {
					for (int i = 0; i < weekList.length; i++) {
						HashMap<String, Object> map = new HashMap<String, Object>();
								map.put("create_date", Util.convertToYYYY_MM_DD(weekList[i]));
								map.put("cnt", 0);
								returnList.add(map);
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return returnList;
	}
	
	@Transactional(readOnly=true)
	public List<UserProductBuyStatsicsDto> userProductBuyStatsics(String phoneNumber) {
		List<UserProductBuyStatsicsDto> Arr = new ArrayList<UserProductBuyStatsicsDto>();
		
		try {
			Arr = statisticsMapper.userProductBuyStatsics(phoneNumber);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return Arr;
	}
	
	@Transactional(readOnly=true)
	public List<UserProductBuyStatsicsDto> userProductBuyStatsicsByPopcorn(String phoneNumber) {
		List<UserProductBuyStatsicsDto> Arr = new ArrayList<>();
		
		try {
			Arr = statisticsMapper.userProductBuyStatsicsByPopcorn(phoneNumber);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return Arr;
	}
	
	@Transactional(readOnly=true)
	public List<UserProductBuyStatsicsDto> userProductBuyStatsicsByCorn(String phoneNumber) {
		List<UserProductBuyStatsicsDto> Arr = new ArrayList<>();
		
		try {
			Arr = statisticsMapper.userProductBuyStatsicsByCorn(phoneNumber);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return Arr;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 가입자 성별 통계
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 03 .03
	 * </pre>
	 * @param termType
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<HashMap<String, Object>> userGenderStatsics(String termType) {
		List<HashMap<String, Object>> resultMap = new ArrayList<>();
		List<UserProfileDto> Arr = new ArrayList<>();
		
		try {
			HashMap<String, Object> paramMap = BirdAdminUtil.staticsDateParam(termType);
			Arr = statisticsMapper.userGenderStatsics(paramMap);
			
			if (Arr.size() > 0) {
				String[] genders = StringUtil.getStringArray("MALE", "FEMALE");
				for (String gender : genders) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					for (UserProfileDto userProfileDto : Arr) {
						if (userProfileDto.getGender().equals(gender)) {
							map.put("gender", userProfileDto.getGender());
							map.put("cnt", userProfileDto.getCnt());
							break;
						} else {
							map.put("gender", gender);
							map.put("cnt", 0);
						}
					}
					resultMap.add(map);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return resultMap;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 성별 메세지 발송 통계
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 03 .03
	 * </pre>
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<HashMap<String, Object>> messageSendStaticsGroupByGender(String termType) {
		List<HashMap<String, Object>> resultMap = new ArrayList<HashMap<String,Object>>();
		List<UserProfileDto> Arr = new ArrayList<>();
		
		try {
			HashMap<String, Object> paramMap = BirdAdminUtil.staticsDateParam(termType);
			Arr = statisticsMapper.messageSendStaticsByGender(paramMap);
			
			if (Arr.size() > 0) {
				String[] genders = StringUtil.getStringArray(Value.MALE, Value.FEMALE);
				for (String gender : genders) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					for (UserProfileDto userProfileDto : Arr) {
						if (userProfileDto.getGender().equals(gender)) {
							map.put("gender", userProfileDto.getGender());
							map.put("cnt", userProfileDto.getCnt());
							break;
						} else {
							map.put("gender", gender);
							map.put("cnt", 0);
						}
					}
					resultMap.add(map);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return resultMap;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 연령별 가입자 통계
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 03 .03
	 * </pre>
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<HashMap<String, Object>> userAgeStatics(String termType) {
		List<HashMap<String, Object>> resultMap = new ArrayList<>();
		
		try {
			List<UserProfileDto> Arr = new ArrayList<>();
			HashMap<String, Object> paramMap = BirdAdminUtil.staticsDateParam(termType);
			Arr = statisticsMapper.userAgeStatics(paramMap);
			
			if (Arr.size() > 0) {
				for (int i=1; i < 10; i++) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					for (UserProfileDto userProfileDto : Arr) {
						if (userProfileDto.getAge2() == i) {
							map.put("ages", userProfileDto.getAge2());
							map.put("cnt", userProfileDto.getCnt());
							break;
						} else {
							map.put("ages", i);
							map.put("cnt", 0);
						}
					}
					resultMap.add(map);
				}
			} else {
				for (int i = 1; i < 10; i++) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("ages", i);
					map.put("cnt", 0);
					resultMap.add(map);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return resultMap;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 연령별 메세지 발송 통계
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 03 .03
	 * </pre>
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<HashMap<String, Object>> messageSendStaticsGroupByAges(String termType) {
		List<HashMap<String, Object>> resultMap = new ArrayList<>();
		
		try {
			List<UserProfileDto> Arr = new ArrayList<>();
			HashMap<String, Object> paramMap = BirdAdminUtil.staticsDateParam(termType);
			Arr = statisticsMapper.messageSendStaticsGroupByAges(paramMap);
			
			if (Arr.size() > 0) {
				for (int i=0; i<10; i++) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					for (UserProfileDto userProfileDto : Arr) {
						if (userProfileDto.getAge() == i) {
							map.put("ages", userProfileDto.getAge());
							map.put("cnt", userProfileDto.getCnt());
							break;
						} else {
							map.put("ages", i);
							map.put("cnt", 0);
						}
					}
					resultMap.add(map);
				}
			} else{
				for (int i = 1; i < 10; i++) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("ages", i);
					map.put("cnt", 0);
					resultMap.add(map);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return resultMap;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 연령별 공개편지 등록 통계
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 04 .28
	 * </pre>
	 * @param termType
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<HashMap<String, Object>> openLetterStaticsGroupByAge(String termType) {
		List<HashMap<String, Object>>resultMap = new ArrayList<>();
		
		try {
			List<UserProfileDto> Arr = new ArrayList<>();
			HashMap<String, Object> paramMap = BirdAdminUtil.staticsDateParam(termType);
			Arr = statisticsMapper.openLetterStaticsGroupByAge(paramMap);
			
			if (Arr.size() > 0) {
				for (int i=0; i<10; i++) {
					HashMap<String, Object>map = new HashMap<>();
					for (UserProfileDto userProfileDto : Arr) {
						if (userProfileDto.getAge() == i) {
							map.put("ages", userProfileDto.getAge());
							map.put("cnt", userProfileDto.getCnt());
							break;
						} else {
							map.put("ages", i);
							map.put("cnt", 0);
						}
					}
					resultMap.add(map);
				}
			} else {
				for (int i = 0; i < 10; i++) {
					HashMap<String, Object>map = new HashMap<>();
					map.put("ages", i);
					map.put("cnt", 0);
					resultMap.add(map);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return resultMap;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 사용자의 개인편지 발송 회수(시작,종료일 조건 검색)
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 03 .18
	 * </pre>
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<HashMap<String, Object>> userMessageSendStatistics(int sPage, String startDate, 
			String endDate, String userName, String gender) {
		List<HashMap<String, Object>> resultMap = new ArrayList<HashMap<String,Object>>();
		
		try {
			PagingDto pagingDto = null;
			pagingDto = Util.getPaging(sPage, Value.pageInList20);
			
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("start", sPage > 0 ? pagingDto.getStart() : "");
			paramMap.put("end", sPage > 0 ? pagingDto.getEnd() : "");
			paramMap.put("startDate", startDate);
			paramMap.put("endDate", endDate);
			paramMap.put("userName", userName);
			paramMap.put("gender", gender);
			
			resultMap = statisticsMapper.userMessageSendStatistics(paramMap);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return resultMap;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 사용자의 개인편지 발송 회수 개수
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 03 .18
	 * </pre> 
	 * @param startDate
	 * @param endDate
	 * @param userName
	 * @param gender
	 * @return
	 */
	@Transactional(readOnly = true)
	public int userMessageSendStatisticsCnt(String startDate, 
			String endDate, String userName, String gender) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startDate", startDate);
		paramMap.put("endDate", endDate);
		paramMap.put("userName", userName);
		paramMap.put("gender", gender);
		
		return statisticsMapper.userMessageSendStatisticsCnt(paramMap);
	}
	
	/**
	 * <pre>
	 * 1. Comment : 모든 사용자의 공개편지 등록 회수(시작,종료일 조건 검색)
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 04 .12
	 * </pre>
	 * @param startDate
	 * @param endDate
	 * @param userName
	 * @param gender
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<HashMap<String, Object>> userOpenLetterStatistics(int sPage, String startDate, 
			String endDate, String userName, String gender) {
		List<HashMap<String, Object>> resultMap = new ArrayList<HashMap<String,Object>>();
		
		try {
			PagingDto pagingDto = null;
			pagingDto = Util.getPaging(sPage, Value.pageInList20);
			
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("start", sPage > 0 ? pagingDto.getStart() : "");
			paramMap.put("end", sPage > 0 ? pagingDto.getEnd() : "");
			paramMap.put("startDate", startDate);
			paramMap.put("endDate", endDate);
			paramMap.put("userName", userName);
			paramMap.put("gender", gender);
			
			resultMap = statisticsMapper.userOpenLetterStatistics(paramMap);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return resultMap;
	}
	
	@Transactional(readOnly = true)
	public int userOpenLetterStatisticsCnt(String startDate, 
			String endDate, String userName, String gender) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startDate", startDate);
		paramMap.put("endDate", endDate);
		paramMap.put("userName", userName);
		paramMap.put("gender", gender);
		
		return statisticsMapper.userOpenLetterStatisticsCnt(paramMap);
	}
	
	/**
	 * <pre>
	 * 1. Comment : 해당 년월일자의 24시간 공개,개인 편지 발송 통계
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 04 .19
	 * </pre>
	 * @param startDate
	 * @param letterType(PRIVATE:개인, OPEN:공개)
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<HashMap<String, Object>> userMessageSendByDailyStats(String startDate, String letterType) {
		List<HashMap<String, Object>> resultList = new ArrayList<>();
		
		try {
			Map<String, String>paramMap = new HashMap<String, String>();
			String endDate = "";
			if (!"".equals(startDate)) endDate = DateUtils.getLastDayOclock(startDate);
			
			paramMap.put("startDate", startDate);
			paramMap.put("endDate", endDate);
			
			List<HashMap<String, Object>> Arr = new ArrayList<>();
			if ("PRIVATE".equals(letterType)) Arr = statisticsMapper.userMessageSendByDailyStats(paramMap);
			else if ("OPEN".equals(letterType)) Arr = statisticsMapper.userOpenLetterDailyStatistics(paramMap);
			
			if (Arr.size() > 0) {
				for (int i = 0; i < 24; i++) {
					HashMap<String, Object> map = new HashMap<>();
					for (int j = 0; j < Arr.size(); j++) {
						if (Arr.get(j).get("hour").equals(i)) {
							map.put("hour", Arr.get(j).get("hour"));
							map.put("cnt", Arr.get(j).get("cnt"));
							break;
						} else {
							map.put("hour", i);
							map.put("cnt", 0);
						}	
					}
					resultList.add(map);	
				}
			} else {
				for (int i = 0; i < 24; i++) {
					HashMap<String, Object> map = new HashMap<>();
					map.put("hour", i);
					map.put("cnt", 0);
					resultList.add(map);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return resultList;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 주차별 개인, 공개 편지 발송 통계
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 04 .19
	 * </pre>
	 * @param startDate
	 * @param endDate
	 * @param letterType
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<HashMap<String, Object>> userMessageSendByWeekStats(String startDate, String endDate, String letterType) {
		List<HashMap<String, Object>> resultList = new ArrayList<>();
		
		try {
			Map<String, String>paramMap = new HashMap<String, String>();
			if (!"".equals(startDate)) endDate = DateUtils.getLastDayOclock(endDate);
			paramMap.put("startDate", startDate);
			paramMap.put("endDate", endDate);
			
			List<HashMap<String, Object>> Arr = new ArrayList<>();
			if ("PRIVATE".equals(letterType)) Arr = statisticsMapper.userMessageSendByWeekStats(paramMap);
			else if ("OPEN".equals(letterType)) Arr = statisticsMapper.userOpenLetterWeekStatistics(paramMap);
			
			List<String> dayOfWeek = DateUtils.getDayOfWeek();
			if (Arr.size() > 0) {
				for (String weekName : dayOfWeek) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					for (HashMap<String, Object> ArrList : Arr) {
						if (ArrList.get("week_name").equals(weekName)) {
							map.put("week", ArrList.get("week_name"));
							map.put("cnt", ArrList.get("cnt"));
							break;
						} else {
							map.put("week", weekName);
							map.put("cnt", 0);
						}
					}
					resultList.add(map);
				}
			} else {
				for (String weekName : dayOfWeek) {
					HashMap<String, Object> map = new HashMap<>();
					map.put("week", weekName);
					map.put("cnt", 0);
					resultList.add(map);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return resultList;
	}
	
	@Transactional(readOnly=true)
	public boolean isDownloadCount(String device, String date) {
		boolean bl = false;
		
		try {
			Map<String, String>paramMap = new HashMap<>();
			paramMap.put("device", device);
			paramMap.put("createDate", DateUtils.getOclock(date));
			
			int cnt = statisticsMapper.checkDownloadCount(paramMap);
			if (cnt == 0) bl = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return bl;
	}
	
	/**
	 * <pre>
	 * 	1. Comment : 다운로드 수 등록
	 * 	2. 작성자 : 안지호
	 * 	3. 작성일 : 2016. 08 .17
	 * </pre>
	 * @param device
	 * @param date
	 * @param downloadCount
	 * @param type
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public int upsultDownloadCount(String device, String date, int downloadCount, String type) {
		int result = 0;
		
		try {
			DownloadCountInfoDto downloadCountInfoDto = new DownloadCountInfoDto();
			downloadCountInfoDto.setDevice(Util.isNullValue(device, ""));
			downloadCountInfoDto.setDownloadCount(downloadCount);
			downloadCountInfoDto.setCreateDate(Util.isNullValue(DateUtils.getOclock(date), ""));
			
			if("INSERT".equals(type)) { 
				result = statisticsMapper.insertDownloadCount(downloadCountInfoDto);
			} else if ("UPDATE".equals(type)) {
				result = statisticsMapper.updateDownloadCount(downloadCountInfoDto);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * <pre>
	 * 	1. Comment : 전제 누적수 가져오기(userCnt : 누적 가입자수, downloadCnt : 누적 다운로드수)
	 * 	2. 작성자 : 안지호
	 * 	3. 작성일 : 2016. 08 .17
	 * </pre> 
	 * @return
	 */
	@Transactional(readOnly=true)
	public HashMap<String, Integer> getAccumulate() {
		HashMap<String, Integer>resultMap = new HashMap<String, Integer>();
		
		try {
			resultMap = statisticsMapper.getAccumulate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return resultMap;
	}
	
	/**
	 * <pre>
	 * 	1. Comment : 일별, 주간별 누적 다운로드 수 통계
	 * 	2. 작성자 : 안지호
	 * 	3. 작성일 : 2016. 08 .17
	 * </pre>
	 * @param searchDate
	 * @param statisticsType (DAY:일별, WEEK:주간별)
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<DownloadCountInfoDto> downloadStatistics(String searchDate, String dayType) {
		List<DownloadCountInfoDto>resultList = new ArrayList<>();
		String startDate;
		String weekList[] = null;
		int thisWeek;
		
		try {
			List<DownloadCountInfoDto> Arr = new ArrayList<>();
			if ("DAY".equals(dayType)) {
				startDate = DateUtils.getMinusDay(searchDate, 13);
				weekList = Util.getDiffDays(Util.convertToYYYYMMDD(startDate), Util.convertToYYYYMMDD(searchDate));
				Arr = statisticsMapper.dailyDownloadStatistics(searchDate);
			} else if ("WEEK".equals(dayType)) {
				thisWeek = DateUtils.getWeek(Util.convertToYYYYMMDD(searchDate));
				logger.info("thisWeek :: " + thisWeek);
				weekList = DateUtils.getDiffWeek(thisWeek, 14);
				Arr = statisticsMapper.weeklyDownloadStatistics(searchDate);
			}
			
			if (Arr.size() > 0) {
				for (String week : weekList) {
					logger.info("week :: " + week);
					DownloadCountInfoDto dto = new DownloadCountInfoDto();
					for (DownloadCountInfoDto downloadCountInfoDto : Arr) {
						if ("DAY".equals(dayType)) {
							if (downloadCountInfoDto.getCreateDate().equals(Util.convertToYYYY_MM_DD(week))) {
								dto = DownloadCountInfoDto.consume(downloadCountInfoDto);
								break;
							} else {
								dto = DownloadCountInfoDto.defaultConsume(Util.convertToYYYY_MM_DD(week));
							}
						} else if ("WEEK".equals(dayType)) {
							if (downloadCountInfoDto.getCreateDate().equals(week)) {
								dto = DownloadCountInfoDto.consume(downloadCountInfoDto);
								break;
							} else {
								dto = DownloadCountInfoDto.defaultConsume(week);
							}
						}
					}
					resultList.add(dto);
				}
			} else {
				for (String week : weekList) {
					DownloadCountInfoDto dto = new DownloadCountInfoDto();
					dto = DownloadCountInfoDto.defaultConsume(Util.convertToYYYY_MM_DD(week));
					resultList.add(dto);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return resultList;
	}
	
	/**
	 * <pre>
	 * 	1. Comment : 월별 누적 다운로드 수 통계
	 * 	2. 작성자 : 안지호
	 * 	3. 작성일 : 2016. 08 .17
	 * </pre>
	 * @param year
	 * @param month
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<DownloadCountInfoDto> monthlyDownloadStatistics(int year, String month) {
		List<DownloadCountInfoDto> returnList = new ArrayList<>();
		
		try {
			if (Integer.valueOf(month) <= 9) month = "0"+month;
			String yyyymm = String.valueOf(year) + month;
			String monthList[] = Util.makeYearByMonth(year, month);
			
			List<DownloadCountInfoDto>Arr = new ArrayList<DownloadCountInfoDto>();
			Arr = statisticsMapper.monthlyDownloadStatistics(Util.getOneYearAgo(yyyymm), Util.convertToYYYY_MM(yyyymm));
			
			if (Arr.size() > 0) {
				for (String months : monthList) {
					DownloadCountInfoDto dto = new DownloadCountInfoDto();
					for (DownloadCountInfoDto downloadCountInfoDto : Arr) {
						if (downloadCountInfoDto.getCreateDate().equals(months)) {
							dto = DownloadCountInfoDto.consume(downloadCountInfoDto);
							break;
						} else {
							dto = DownloadCountInfoDto.defaultConsume(months);
						}
					}
					returnList.add(dto);
				}
			} else {
				for (String months : monthList) {
					DownloadCountInfoDto dto = new DownloadCountInfoDto();
					dto = DownloadCountInfoDto.defaultConsume(months);
					returnList.add(dto);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return returnList; 
	}
	
	/**
	 * <pre>
	 * 	1. Comment : 연별 누적 다운로드 수 통계
	 * 	2. 작성자 : 안지호
	 * 	3. 작성일 : 2016. 08 .19
	 * </pre>
	 * @param year
	 * @param thisYear
	 * @return
	 */
	@Transactional(readOnly=true) 
	public List<DownloadCountInfoDto> yearDownloadStatistics(String year, String thisYear) {
		List<DownloadCountInfoDto> returnList = new ArrayList<>();
		
		try {
			String yearList[] = DateUtils.getYearList(Integer.parseInt(year), 4, "M");
			List<DownloadCountInfoDto> Arr = new ArrayList<>();
			Arr = statisticsMapper.yearDownloadStatistics(thisYear);
			
			if (Arr.size() > 0) {
				for (String years : yearList) {
					DownloadCountInfoDto dto = new DownloadCountInfoDto();
					for (DownloadCountInfoDto downloadCountInfoDto : Arr) {
						if (downloadCountInfoDto.getCreateDate().equals(years)) {
							dto = DownloadCountInfoDto.consume(downloadCountInfoDto);
							break;
						} else {
							dto = DownloadCountInfoDto.defaultConsume(years);
						}
					}
					returnList.add(dto);
				}
			} else {
				for (String years : yearList) {
					DownloadCountInfoDto dto = new DownloadCountInfoDto();
					dto = DownloadCountInfoDto.defaultConsume(years);
					returnList.add(dto);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * <pre>
	 * 	1. Comment : 일별, 주별 가입자 수 통계
	 * 	2. 작성자 : 안지호
	 * 	3. 작성일 : 2016. 08 .19
	 * </pre>
	 * @param searchDate
	 * @param dayType(DAY:일별, WEEK:주별)
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<MemberStatisticsDto>memberRegStatistics(String searchDate, String dayType) {
		List<MemberStatisticsDto>resultList = new ArrayList<>();
		String startDate;
		String dayList[] = null;
		int thisWeek;
		
		try {
			List<MemberStatisticsDto> Arr = new ArrayList<>();
			if ("DAY".equals(dayType)) {
				startDate = DateUtils.getMinusDay(searchDate, 13);
				dayList = Util.getDiffDays(Util.convertToYYYYMMDD(startDate), Util.convertToYYYYMMDD(searchDate));
				Arr = statisticsMapper.dailyMemberRegStatistics(startDate, DateUtils.getLastDayOclock(searchDate));
			} else if ("WEEK".equals(dayType)) {
				thisWeek = DateUtils.getWeek(Util.convertToYYYYMMDD(searchDate));
				dayList = DateUtils.getDiffWeek(thisWeek, 14);
				Arr = statisticsMapper.weeklyMemberRegStatistics(DateUtils.getLastDayOclock(searchDate));
			}
			
			if (Arr.size() > 0) {
				for (String days : dayList) {
					MemberStatisticsDto dto = new MemberStatisticsDto();
					for (MemberStatisticsDto memberStatisticsDto : Arr) {
						if ("DAY".equals(dayType)) {
							if (memberStatisticsDto.getDate().equals(Util.convertToYYYY_MM_DD(days))) {
								dto = MemberStatisticsDto.consume(memberStatisticsDto);
								break;
							} else {
								dto = MemberStatisticsDto.defaultConsume(Util.convertToYYYY_MM_DD(days));
							}
						} else if ("WEEK".equals(dayType)) {
							if (memberStatisticsDto.getDate().equals(days)) {
								dto = MemberStatisticsDto.consume(memberStatisticsDto);
								break;
							} else {
								dto = MemberStatisticsDto.defaultConsume(days);
							}
						}
					}
					resultList.add(dto);
				}
			} else {
				for (String days : dayList) {
					MemberStatisticsDto dto = new MemberStatisticsDto();
					dto = MemberStatisticsDto.defaultConsume(Util.convertToYYYY_MM_DD(days));
					resultList.add(dto);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return resultList;
	}
	
	/**
	 * <pre>
	 * 	1. Comment : 월별 가입자 수 통계
	 * 	2. 작성자 : 안지호
	 * 	3. 작성일 : 2016. 08 .19
	 * </pre> 
	 * @param year
	 * @param month
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<MemberStatisticsDto> monthlyMemberRegStatistics(int year, String month) {
		List<MemberStatisticsDto>resultList = new ArrayList<>();
		
		try {
			if (Integer.valueOf(month) <= 9) month = "0"+month;
			String yyyymm = String.valueOf(year)+month;
			String monthList[] = Util.makeYearByMonth(year, month);
			
			List<MemberStatisticsDto>Arr = new ArrayList<>();
			Arr = statisticsMapper.monthlyMemberRegStatistics(Util.getOneYearAgo(yyyymm), Util.convertToYYYY_MM(yyyymm));
			
			if (Arr.size() > 0) {
				for (String months : monthList) {
					MemberStatisticsDto dto = new MemberStatisticsDto();
					for (MemberStatisticsDto memberStatisticsDto : Arr) {
						if (memberStatisticsDto.getDate().equals(months)) {
							dto = MemberStatisticsDto.consume(memberStatisticsDto);
							break;
						} else {
							dto = MemberStatisticsDto.defaultConsume(months);
						}
					}
					resultList.add(dto);
				}
			} else {
				for (String months : monthList) {
					MemberStatisticsDto dto = new MemberStatisticsDto();
					dto = MemberStatisticsDto.defaultConsume(months);
					resultList.add(dto);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return resultList;
	}
	
	/**
	 * <pre>
	 * 	1. Comment : 연별 가입자 수 통계
	 * 	2. 작성자 : 안지호
	 * 	3. 작성일 : 2016. 08 .19
	 * </pre>
	 * @param year
	 * @param thisYear
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<MemberStatisticsDto> yearMemberRegStatistics(String year, String thisYear) {
		List<MemberStatisticsDto>resultList = new ArrayList<>();
		
		try {
			String yearList[] = DateUtils.getYearList(Integer.parseInt(year), 4, "M");
			
			List<MemberStatisticsDto> Arr = new ArrayList<MemberStatisticsDto>();
			Arr = statisticsMapper.yearMemberRegStatistics(thisYear);
			
			if (Arr.size() > 0) {
				for (String years : yearList) {
					MemberStatisticsDto memberStatistics = new MemberStatisticsDto();
					for (MemberStatisticsDto ArrList : Arr) {
						if (ArrList.getDate().equals(years)) {
							memberStatistics = MemberStatisticsDto.consume(ArrList);
							break;
						} else {
							memberStatistics = MemberStatisticsDto.defaultConsume(years);
						}
					}
					resultList.add(memberStatistics);
				}
			} else {
				for (String years : yearList) {
					MemberStatisticsDto memberStatistics = new MemberStatisticsDto();
					memberStatistics = MemberStatisticsDto.defaultConsume(years);
					resultList.add(memberStatistics);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return resultList;
	}
	
	/**
	 * <pre>
	 * 	1. Comment : ARPU 통계
	 * 	2. 작성자 : 안지호
	 * 	3. 작성일 : 2016. 08 .22
	 * </pre>
	 * @param year
	 * @param month
	 * @param type
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<DateCountDto> arpuStatistics(int year, String month, String type) {
		List<DateCountDto>resultList = new ArrayList<>();
		String monthList[];
		
		try {
			if (month != null) {
				if (Integer.valueOf(month) <= 9) {
					month = "0" + month;
				}
			}
			String yyyymm = String.valueOf(year) + month;
			
			List<DateCountDto> Arr = new ArrayList<>();
			if (type.equals("YEAR")) {
				monthList = Util.returnYYYY_MM(String.valueOf(year));
				Arr = statisticsMapper.arpuStatisticsByYear(String.valueOf(year));
			} else {
				monthList = Util.makeYearByMonth(year, month);
				Arr = statisticsMapper.arpuStatistics(Util.getOneYearAgo(yyyymm), Util.convertToYYYY_MM(yyyymm));
			}
			
			if (Arr.size() > 0) {
				for (String months : monthList) {
					DateCountDto dto = new DateCountDto();
					for (DateCountDto dateCountDto : Arr) {
						if (dateCountDto.getDate().equals(months)) {
							dto.setDate(months);
							dto.setCnt(dateCountDto.getCnt());
							break;
						} else {
							dto = DateCountDto.defaultConsume(months);
						}
					}
					resultList.add(dto);
				}
			} else {
				for (String months : monthList) {
					DateCountDto dto = new DateCountDto();
					dto = DateCountDto.defaultConsume(months);
					resultList.add(dto);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return resultList;
	}
	
	/**
	 * <pre>
	 * 	1. Comment : ARPPU 통계
	 * 	2. 작성자 : 안지호
	 * 	3. 작성일 : 2016. 08 .22
	 * </pre>
	 * @param year
	 * @param month
	 * @param type
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<DateCountDto> arppuStatistics(int year, String month, String type) {
		List<DateCountDto> resultList = new ArrayList<>();
		String monthList[];
		
		try {
			if (month != null) {
				if (Integer.valueOf(month) <= 9) {
					month = "0" + month;
				}
			}
			String yyyymm = String.valueOf(year) + month;
			
			List<DateCountDto> Arr = new ArrayList<>();
			if (type.equals("YEAR")) {
				monthList = Util.returnYYYY_MM(String.valueOf(year));
				Arr = statisticsMapper.arppuStatisticsByYear(String.valueOf(year));
			} else {
				monthList = Util.makeYearByMonth(year, month);
				Arr = statisticsMapper.arppuStatistics(Util.getOneYearAgo(yyyymm), Util.convertToYYYY_MM(yyyymm));
			}
			
			if (Arr.size() > 0) {
				for (String months : monthList) {
					DateCountDto dto = new DateCountDto();
					for (DateCountDto dateCountDto : Arr) {
						if (dateCountDto.getDate().equals(months)) {
							int cnt = (int)(dateCountDto.getCnt() / dateCountDto.getUserCnt());
							dto.setDate(months);
							dto.setCnt(cnt);
							break;
						} else {
							dto = DateCountDto.defaultConsume(months);
						}
					}
					resultList.add(dto);
				}
			} else {
				for (String months : monthList) {
					DateCountDto dto = new DateCountDto();
					dto = DateCountDto.defaultConsume(months);
					resultList.add(dto);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return resultList;
	}
	
	/**
	 * <pre>
	 * 	1. Comment : 일별, 주별 편지생성 수 통계 
	 * 	2. 작성자 : 안지호
	 * 	3. 작성일 : 2016. 08 .24
	 * </pre> 
	 * @param date
	 * @param dayType
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<LetterStatisticsDto> letterStatistics(String date, String dayType) {
		List<LetterStatisticsDto> resultList = new ArrayList<>();
		String startDate;
		String weekList[] = null;
		int thisWeek;
		
		try {
			List<LetterStatisticsDto> Arr = new ArrayList<>();
			
			if ("DAY".equals(dayType)) {
				startDate = DateUtils.getMinusDay(date, 13);
				weekList = Util.getDiffDays(Util.convertToYYYYMMDD(startDate), Util.convertToYYYYMMDD(date));
				Arr = statisticsMapper.dailyLetterStatistics(DateUtils.getLastDayOclock(date));
			} else if ("WEEK".equals(dayType)) {
				thisWeek = DateUtils.getWeek(Util.convertToYYYYMMDD(date));
				weekList = DateUtils.getDiffWeek(thisWeek, 14);
				Arr = statisticsMapper.weeklyLetterStatistics(DateUtils.getLastDayOclock(date));
			}
			
			if (Arr.size() > 0) {
				for (String week : weekList) {
					LetterStatisticsDto dto = new LetterStatisticsDto();
					for (LetterStatisticsDto letterStatisticsDto : Arr) {
						if ("DAY".equals(dayType)) {
							if (letterStatisticsDto.getDate().equals(Util.convertToYYYY_MM_DD(week))) {
								dto = LetterStatisticsDto.consume(letterStatisticsDto);
								break;
							} else {
								dto = LetterStatisticsDto.defaultConsume(Util.convertToYYYY_MM_DD(week));
							}
						} else if ("WEEK".equals(dayType)) {
							if (letterStatisticsDto.getDate().equals(week)) {
								dto = LetterStatisticsDto.consume(letterStatisticsDto);
								break;
							} else {
								dto = LetterStatisticsDto.defaultConsume(week);	
							}
						}
					}
					resultList.add(dto);
				}
			} else {
				for (String week : weekList) {
					LetterStatisticsDto dto = new LetterStatisticsDto();
					dto = LetterStatisticsDto.defaultConsume(Util.convertToYYYY_MM_DD(week));
					resultList.add(dto);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return resultList;
	}
	
	/**
	 * <pre>
	 * 	1. Comment : 월별 편지생성 수 통계 
	 * 	2. 작성자 : 안지호
	 * 	3. 작성일 : 2016. 08 .24
	 * </pre>
	 * @param year
	 * @param month
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<LetterStatisticsDto>monthlyLetterStatistics(int year, String month) {
		List<LetterStatisticsDto>returnList = new ArrayList<>();
		
		try {
			if (Integer.valueOf(month) <= 9) {
				month = "0" + month;
			}
			String yyyymm = String.valueOf(year)+month;
			
			List<LetterStatisticsDto>Arr = new ArrayList<>();
			Arr = statisticsMapper.monthlyLetterStatistics(Util.getOneYearAgo(yyyymm), Util.convertToYYYY_MM(yyyymm));
			
			String monthList[] = Util.makeYearByMonth(year, month);
			if (Arr.size() > 0) {
				for (String months : monthList) {
					LetterStatisticsDto dto = new LetterStatisticsDto();
					for (LetterStatisticsDto ArrList : Arr) {
						if (ArrList.getDate().equals(months)) {
							dto = LetterStatisticsDto.consume(ArrList);
							break;	
						} else {
							dto = LetterStatisticsDto.defaultConsume(months);
						}
					}
					returnList.add(dto);
				}
			} else {
				for (String months : monthList) {
					LetterStatisticsDto dto = new LetterStatisticsDto();
					dto = LetterStatisticsDto.defaultConsume(months);
					returnList.add(dto);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * <pre>
	 * 	1. Comment : 연별 편지생성 수 통계 
	 * 	2. 작성자 : 안지호
	 * 	3. 작성일 : 2016. 08 .24
	 * </pre>
	 * @param year
	 * @param thisYear
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<LetterStatisticsDto>yearLetterStatistics(String year, String thisYear) {
		List<LetterStatisticsDto>returnList = new ArrayList<>();
		
		try {
			List<LetterStatisticsDto>Arr = new ArrayList<>();
			Arr = statisticsMapper.yearLetterStatistics(thisYear);
			
			String yearList[] = DateUtils.getYearList(Integer.parseInt(year), 4, "M");
			if (Arr.size() > 0) {
				for (String years : yearList) {
					LetterStatisticsDto dto = new LetterStatisticsDto();
					for (LetterStatisticsDto ArrList : Arr) {
						if (ArrList.getDate().equals(years)) {
							dto = LetterStatisticsDto.consume(ArrList);
							break;
						} else {
							dto = LetterStatisticsDto.defaultConsume(years);
						}
					}
					returnList.add(dto);
				}
			} else {
				for (String years : yearList) {
					LetterStatisticsDto dto = new LetterStatisticsDto();
					dto = LetterStatisticsDto.defaultConsume(years);
					returnList.add(dto);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * <pre>
	 * 	1. Comment : 일별, 주별 DAU
	 * 	2. 작성자 : 안지호
	 * 	3. 작성일 : 2016. 08 .24
	 * </pre> 
	 * @param searchDate
	 * @param dayType
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<MemberStatisticsDto>dauStatistics(String searchDate, String dayType) {
		List<MemberStatisticsDto>returnList = new ArrayList<>();
		String startDate;
		String weekList[] = null;
		int thisWeek;
		
		try {
			List<MemberStatisticsDto>Arr = new ArrayList<>();
			
			if ("DAY".equals(dayType)) {
				startDate = DateUtils.getMinusDay(searchDate, 13);
				weekList = Util.getDiffDays(Util.convertToYYYYMMDD(startDate), Util.convertToYYYYMMDD(searchDate));
				Arr = statisticsMapper.dailyDauStatistics(startDate, DateUtils.getLastDayOclock(searchDate));
			} else if ("WEEK".equals(dayType)) {
				thisWeek = DateUtils.getWeek(Util.convertToYYYYMMDD(searchDate));
				weekList = DateUtils.getDiffWeek(thisWeek, 14);
				Arr = statisticsMapper.weeklyDauStatistics(searchDate);
			}
			
			if (Arr.size() > 0) {
				for (String week : weekList) {
					MemberStatisticsDto dto = new MemberStatisticsDto();
					for (MemberStatisticsDto ArrList : Arr) {
						if ("DAY".equals(dayType)) {
							if (ArrList.getDate().equals(Util.convertToYYYY_MM_DD(week))) {
								dto = MemberStatisticsDto.consume(ArrList);
								break;
							} else {
								dto = MemberStatisticsDto.defaultConsume(Util.convertToYYYY_MM_DD(week));
							}	
						} else if ("WEEK".equals(dayType)) {
							if (ArrList.getDate().equals(week)) {
								dto = MemberStatisticsDto.consume(ArrList);
								break;
							} else {
								dto = MemberStatisticsDto.defaultConsume(week);
							}
						}
					}
					returnList.add(dto);
				}
			} else {
				for (String week : weekList) {
					MemberStatisticsDto dto = new MemberStatisticsDto();
					dto = MemberStatisticsDto.defaultConsume(Util.convertToYYYY_MM_DD(week));
					returnList.add(dto);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * <pre>
	 * 	1. Comment : 월별 DAU
	 * 	2. 작성자 : 안지호
	 * 	3. 작성일 : 2016. 08 .24
	 * </pre> 
	 * @param year
	 * @param month
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<MemberStatisticsDto>monthlyDauStatistics(int year, String month) {
		List<MemberStatisticsDto>resultList = new ArrayList<>();

		try {
			if (Integer.valueOf(month) <= 9) {
				month = "0"+month;
			}
			String yyyymm = String.valueOf(year)+month;
			Map<String, String>paramMap = new HashMap<>();
			paramMap.put("startMonth", Util.getOneYearAgo(yyyymm));
			paramMap.put("endMonth",   Util.convertToYYYY_MM(yyyymm));
			
			List<MemberStatisticsDto>Arr = new ArrayList<>();
			Arr = statisticsMapper.monthlyDauStatistics(Util.getOneYearAgo(yyyymm), Util.convertToYYYY_MM(yyyymm));

			String monthList[] = Util.makeYearByMonth(year, month);
			if (Arr.size() > 0) {
				for (String months : monthList) {
					MemberStatisticsDto memberStatistics = new MemberStatisticsDto();
					for (MemberStatisticsDto ArrList : Arr) {
						if (ArrList.getDate().equals(months)) {
							memberStatistics = MemberStatisticsDto.consume(ArrList);
							break;
						} else {
							memberStatistics = MemberStatisticsDto.defaultConsume(months);
						}
					}
					resultList.add(memberStatistics);
				}
			} else {
				for (String months : monthList) {
					MemberStatisticsDto memberStatistics = new MemberStatisticsDto();
					memberStatistics = MemberStatisticsDto.defaultConsume(months);
					resultList.add(memberStatistics);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return resultList;
	}
	
	/**
	 * <pre>
	 * 	1. Comment : 연별 DAU
	 * 	2. 작성자 : 안지호
	 * 	3. 작성일 : 2016. 08 .24
	 * </pre>
	 * @param year
	 * @param thisYear
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<MemberStatisticsDto>yearDauStatistics(String year, String thisYear) {
		List<MemberStatisticsDto>resultList = new ArrayList<>();
		
		try {
			List<MemberStatisticsDto>Arr = new ArrayList<>();
			Arr = statisticsMapper.yearDauStatistics(thisYear);
			
			String yearList[] = DateUtils.getYearList(Integer.parseInt(year), 4, "M");
			if (Arr.size() > 0) {
				for (String years : yearList) {
					MemberStatisticsDto memberStatistics = new MemberStatisticsDto();
					for (MemberStatisticsDto ArrList : Arr) {
						if (ArrList.getDate().equals(years)) {
							memberStatistics = MemberStatisticsDto.consume(ArrList);
							break;
						} else {
							memberStatistics = MemberStatisticsDto.defaultConsume(years);
						}
					}
					resultList.add(memberStatistics);
				}
			} else {
				for (String years : yearList) {
					MemberStatisticsDto memberStatistics = new MemberStatisticsDto();
					memberStatistics = MemberStatisticsDto.defaultConsume(years);
					resultList.add(memberStatistics);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return resultList;
	}
	
	/**
	 * <pre>
	 * 	1. Comment : MAU
	 * 	2. 작성자 : 안지호
	 * 	3. 작성일 : 2016. 08 .24
	 * </pre>
	 * @param year
	 * @param month
	 * @param type
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<MemberStatisticsDto>mauStatistics(int year, String month, String type) {
		List<MemberStatisticsDto>resultList = new ArrayList<>();
		
		try {
			Map<String, String>paramMap = new HashMap<>();
			if (month != null) {
				if (Integer.valueOf(month) <= 9) {
					month = "0" + month;
				}
			}
			String yyyymm = String.valueOf(year) + month;
			
			List<MemberStatisticsDto>Arr = new ArrayList<>();
			String monthList[];
			if (type.equals("YEAR")) {
				monthList = Util.returnYYYY_MM(String.valueOf(year));
				Arr = statisticsMapper.mauStatisticsByYear(String.valueOf(year));
			} else {
				monthList = Util.makeYearByMonth(year, month);
				Arr = statisticsMapper.mauStatistics(Util.getOneYearAgo(yyyymm), Util.convertToYYYY_MM(yyyymm));
			}
			if (Arr.get(0).getDate() != null) {
				for (String months : monthList) {
					MemberStatisticsDto memberStatistics = new MemberStatisticsDto();
					for (MemberStatisticsDto ArrList : Arr) {
						if (ArrList.getDate().equals(months)) {
							memberStatistics = MemberStatisticsDto.consume(ArrList);
							break;
						} else {
							memberStatistics = MemberStatisticsDto.defaultConsume(months);
						}
					}
					resultList.add(memberStatistics);
				}
			} else {
				for (String months : monthList) {
					MemberStatisticsDto memberStatistics = new MemberStatisticsDto();
					memberStatistics = MemberStatisticsDto.defaultConsume(months);
					resultList.add(memberStatistics);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return resultList;
	}
	
	@Transactional(readOnly = true)
	public List<AuthStatisticsDto>authStatisticsByAge(String searchDate, String type)
		throws DataAccessException, Exception {
		List<AuthStatisticsDto>returnList = new ArrayList<>();
		
		try {
			List<AuthStatisticsDto>Arr = new ArrayList<>();
			HashMap<String, Object>paramMap = new HashMap<String, Object>();
			if ("daily".equals(type)) {
				paramMap.put("type", Util.isNullValue(type, ""));
				paramMap.put("start", DateUtils.getOclock(searchDate));
				paramMap.put("end", DateUtils.getLastDayOclock(searchDate));
			} else if ("weekly".equals(type)) {
				int week = DateUtils.getWeek(Util.convertToYYYYMMDD(searchDate));
				if (week >= 0 && week <= 12)  {
					week = week + 1;
				}
				paramMap.put("type", Util.isNullValue(type, ""));
				paramMap.put("week", week);
			} else if ("monthly".equals(type)) {
				paramMap.put("type", Util.isNullValue(type, ""));
				paramMap.put("month", searchDate);
			} else if ("year".equals(type)) {
				paramMap.put("type", Util.isNullValue(type, ""));
				paramMap.put("year", searchDate);
			}
			Arr = statisticsMapper.authStatisticsByAge(paramMap);
			
			if (Arr.size() > 0) {
				for (int i=0; i<=6; i++) {
					AuthStatisticsDto domain = new AuthStatisticsDto();
					for (AuthStatisticsDto ArrList : Arr) {
						if (ArrList.getAge() == i) {
							domain = AuthStatisticsDto.consume(ArrList);
							break;
						} else {
							domain = AuthStatisticsDto.defaultConsume(i);
						}
					}
					returnList.add(domain);
				}
			} else {
				for (int i=0; i<=6; i++) {
					AuthStatisticsDto domain = new AuthStatisticsDto();
					domain = AuthStatisticsDto.defaultConsume(i);
					returnList.add(domain);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return returnList;
	}
	
	@Transactional(readOnly=true)
	public List<DormantUserDto> dormantUserStatistics(int sPage, String searchDate, int dayCount) {
		List<DormantUserDto> Arr = new ArrayList<>();
		
		try {
			PagingDto pagingDto = Util.getPaging(sPage, Value.pageInList20);
			String start = "";
			String end = "";
			if (sPage > 0) {
				start = pagingDto.getStart();
				end = pagingDto.getEnd();	
			}
			HashMap<String, Object>paramMap = new HashMap<>();
			paramMap.put("searchDate", searchDate);
			paramMap.put("dayCount", dayCount+1);
			paramMap.put("start", start);
			paramMap.put("end", end);
			
			Arr = statisticsMapper.dormantUserStatistics(paramMap);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return Arr;
	}
	
	@Transactional(readOnly = true)
	public int dormantUserStatisticsCnt(String searchDate, int dayCount) {
		int cnt = 0;
		
		try {
			cnt = statisticsMapper.dormantUserStatisticsCnt(searchDate, dayCount);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return cnt;
	}
	
	@Transactional(readOnly = true)
	public List<List<HashMap<String, Object>>> retensionExcelDownload(String retensionType, int cnt, String yyyymmdd) {
		int interval = cnt + 41;
		List<String>days = DateUtils.getMonthOfDays(yyyymmdd);
		List<List<HashMap<String, Object>>> list = new ArrayList<>();
		for (String s : days) {
			List<HashMap<String, Object>> list2 = new ArrayList<>();
			if ("dau".equals(retensionType)) {
				list2 = statisticsMapper.dauRetension(interval, s);
			} else if ("reg".equals(retensionType)) {
				list2 = statisticsMapper.regRetension(interval, s);
			}
			list.add(list2);
		}
		return list;
	}
}
