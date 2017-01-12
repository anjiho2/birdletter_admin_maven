package com.challabros.birdletter.admin.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.amazonaws.services.identitymanagement.model.User;
import com.challabros.birdletter.admin.define.datasource.DataSource;
import com.challabros.birdletter.admin.define.datasource.DataSourceType;
import com.challabros.birdletter.admin.dto.CoinBuyLogDto;
import com.challabros.birdletter.admin.dto.EmailSendDto;
import com.challabros.birdletter.admin.dto.PagingDto;
import com.challabros.birdletter.admin.dto.UserProductBuyLogDto;
import com.challabros.birdletter.admin.dto.UserProfileDto;
import com.challabros.birdletter.admin.error.BirdAdminErrorCode;
import com.challabros.birdletter.admin.error.BirdAdminException;
import com.challabros.birdletter.admin.mapper.UserMapper;
import com.challabros.birdletter.admin.util.DateUtils;
import com.challabros.birdletter.admin.util.Util;
import com.challabros.birdletter.admin.util.Value;

public class UserService {
	
	final static Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private EmailService emailService;
	
	/**
	 * <pre>
	 * 1. Comment : 사용자정보 리스트
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 01 .12
	 * </pre>
	 * @param sPage
	 * @param userName
	 * @param phoneNumber
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly=true)
	public List<UserProfileDto> userProfileList(int sPage, int pagingCnt, String userName, String phoneNumber) throws Exception {
		List<UserProfileDto> Arr = new ArrayList<UserProfileDto>();
		
		try {
			PagingDto pagingDto = new PagingDto();
			pagingDto = Util.getPaging(sPage, pagingCnt);
			
			String start = pagingDto.getStart();
            String end = pagingDto.getEnd();
            
            UserProfileDto dto = new UserProfileDto();
            dto.setStart(start);
            dto.setEnd(end);
            dto.setPhoneNumber(Util.isNullValue(phoneNumber, ""));
            dto.setUserName(Util.isNullValue(userName, ""));
            
            Arr = userMapper.userProfileList(dto);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return Arr;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 사용자정보 전체 리스트
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 03 .17
	 * </pre>
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<UserProfileDto> userProfileListAll() {
		List<UserProfileDto> Arr = new ArrayList<>();
		
		try {
			Arr = userMapper.userProfileListAll("DEFAULT");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return Arr;
	}
	
	@Transactional(readOnly=true)
	public List<UserProfileDto> userListByExcel() {
		List<UserProfileDto> Arr = new ArrayList<>();
		
		try {
			List<UserProfileDto> userList = new ArrayList<>();
			userList = userMapper.userProfileListAll("EXCEL");
			if (userList.size() > 0) {
				for (UserProfileDto userProfileDto : userList) {
					UserProfileDto dto = new UserProfileDto();
					dto.setUserName(userProfileDto.getUserName());
					dto.setPhoneNumber(userProfileDto.getPhoneNumber());
					dto.setAge(DateUtils.getAge(userProfileDto.getBirthDay(), "KOR"));
					dto.setGender(userProfileDto.getGender());
					Arr.add(dto);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return Arr;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 사용자정보 리스트 갯수
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 01 .12
	 * </pre> 
	 * @param userName
	 * @param phoneNumber
	 * @return
	 */
	@Transactional(readOnly=true)
	public int userProfileCnt (String userName, String phoneNumber) {
		int cnt = 0;
		
		try {
			cnt = userMapper.userProfileCnt(userName, phoneNumber);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return cnt;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 사용자정보 상세
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 01 .12 
	 * </pre>
	 * @param userId
	 * @param userName
	 * @param phoneNumber
	 * @return
	 */
	@Transactional(readOnly=true)
	public UserProfileDto userProfileDetail(int userId, String userName, String phoneNumber) {
		UserProfileDto userProfileDto = new UserProfileDto();
		
		try {
			userProfileDto = userMapper.userProfileDetail(userId, userName, phoneNumber);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return userProfileDto;
	}
	
	/**
     * <pre>
     * 1. Comment : 사용자 콘구매 내역 리스트
     * 2. 작성자 : 안지호
     * 3. 작성일 : 2016. 01. 12
     * </pre>
	 * @param sPage
	 * @param userId
	 * @param cornName
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<CoinBuyLogDto> userCornBuyLogList(int sPage, Long userId, String cornName, String startDate, String endDate) {
		List<CoinBuyLogDto> Arr = new ArrayList<>();
		
		try {
			if (userId == null) userId = Long.valueOf(0);
			PagingDto pagingDto = Util.getPaging(sPage, Value.pageIntLis15);
			
			HashMap<String, String> paramMap = new HashMap<>();
			paramMap.put("start", pagingDto.getStart());
			paramMap.put("end", pagingDto.getEnd());
			paramMap.put("userId", String.valueOf(userId));
			paramMap.put("cornName", cornName);
			paramMap.put("startDate", startDate);
			paramMap.put("endDate", endDate);
			 
			Arr = userMapper.userCornBuyLogList(paramMap);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return Arr;
	}
	
	/**
     * <pre>
     * 1. Comment : 사용자 콘구매 내역 리스트 갯수
     * 2. 작성자 : 안지호
     * 3. 작성일 : 2016. 01. 12
     * </pre>
	 * @param userId
	 * @param cornName
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@Transactional(readOnly=true)
	public int userCornBuyLogListCnt(Long userId, String cornName, String startDate, String endDate) {
		int cnt = 0;
		
		try {
			if (userId == null) userId = Long.valueOf(0);
			
			HashMap<String, String> paramMap = new HashMap<>();
			paramMap.put("userId", String.valueOf(userId));
			paramMap.put("cornName", cornName);
			paramMap.put("startDate", startDate);
			paramMap.put("endDate", endDate);
			
			cnt = userMapper.userCornBuyLogListCnt(paramMap);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return cnt;
	}
	
	/**
	 * <PRE>
     * 1. Comment : 팝콘구매 내역 리스트
     * 2. 작성자 : 안지호
     * 3. 작성일 : 2016. 01. 14
     * </PRE>
	 * @param sPage
	 * @param userId
	 * @param popcornName
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<CoinBuyLogDto> popcornBuyLogList(int sPage, Long userId, String popcornName, String startDate, String endDate) {
		List<CoinBuyLogDto> Arr = new ArrayList<>();
		
		try {
			if (userId == null) userId = Long.valueOf(0);
			PagingDto pagingDto = Util.getPaging(sPage, Value.pageIntLis15);
			
			HashMap<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("start", pagingDto.getStart());
			paramMap.put("end", pagingDto.getEnd());
			paramMap.put("userId", String.valueOf(userId));
			paramMap.put("popcornName", Util.isNullValue(popcornName, ""));
			paramMap.put("startDate", Util.isNullValue(startDate, ""));
			paramMap.put("endDate", Util.isNullValue(endDate, ""));
			
			Arr = userMapper.popcornBuyLogList(paramMap);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return Arr;
	}
	
	/**
	 * <PRE>
     * 1. Comment : 팝콘구매 내역 리스트 갯수
     * 2. 작성자 : 안지호
     * 3. 작성일 : 2016. 01. 14
     * </PRE>
	 * @param userId
	 * @param popcornName
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@Transactional(readOnly=true)
	public int popcornBuyLogListCnt(Long userId, String popcornName, String startDate, String endDate) {
		int cnt=0;
		
		try {
			if (userId == null) userId = Long.valueOf(0);
			
			HashMap<String, Object> paramMap = new HashMap<String, Object>(); 
			paramMap.put("userId", userId);
    		paramMap.put("popcornName", popcornName);
    		paramMap.put("startDate", startDate);
    		paramMap.put("endDate", endDate);
    		cnt = userMapper.popcornBuyLogListCnt(paramMap);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return cnt;
	}
	
	/**
	 * <PRE>
     * 1. Comment : 팝콘,콘 총 내역
     * 2. 작성자 : 안지호
     * 3. 작성일 : 2016. 01. 14
     * </PRE>
	 * @param userId
	 * @param buyType
	 * @return
	 */
	@Transactional(readOnly=true)
	public int findUserCornPopcornBuySum(Long userId, String buyType) {
		int sum=0;
		
		try {
			if (userId > 0L) {
				if (Value.CORN.equals(buyType)) sum = userMapper.findUserCornBuySum(userId);
				else if (Value.POPCORN.equals(buyType)) sum = userMapper.findUserPopcornBuySum(userId);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return sum;
	}
	
	/**
	 * <PRE>
     * 1. Comment : 유저 id값 체크
     * 2. 작성자 : 안지호
     * 3. 작성일 : 2016. 01. 14
     * </PRE>
	 * @return
	 */
	@DataSource(DataSourceType.ADMIN)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<Integer> selectUserIdx() {
		List<Integer> userIds = new ArrayList<Integer>();
		
		try {
			userIds = userMapper.selectUserIdx();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return userIds;
	}
	
	/**
	 * <PRE>
     * 1. Comment : 조건에 대한 유저 id값 체크
     * 2. 작성자 : 안지호
     * 3. 작성일 : 2016. 04. 29
     * </PRE>
	 * @param gender
	 * @param minAge
	 * @return
	 */
	@DataSource(DataSourceType.ADMIN)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<Integer> selectUserIdxByWhere(String gender, int minAge) {
		List<Integer> userIds = new ArrayList<>();
		
		try {
			userIds = userMapper.selectUserIdxByWhere(gender, minAge);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return userIds;
	}
	
	/**
	 * <PRE>
	 * 1. Comment : 생일자(MM-dd)검색
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 02. 26
	 * </PRE> 
	 * @param birthDay
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<HashMap<String, Object>> findUserByBirthDay(String birthDay) {
		List<HashMap<String, Object>> resultMap = new ArrayList<>();
		
		try {
			if (!"".equals(birthDay)) 
				resultMap = userMapper.findUserByBirthDay(birthDay);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return resultMap;
	}
	
	/**
	 * <PRE>
     * 1. Comment : 유저의 친구목록 조회
     * 2. 작성자 : 안지호
     * 3. 작성일 : 2016. 02. 26
     * </PRE>
	 * @param userId
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<Long> findUserFriendShip(Long userId) {
		List<Long> friendShipIdList = new ArrayList<>();
		
		try {
			if (userId > 0)
				friendShipIdList = userMapper.findUserFriendShip(userId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return friendShipIdList;
	}
	
	/**
	 * <PRE>
     * 1. Comment : 유저의 팝콘 구매 전체 목록 가져오기
     * 2. 작성자 : 안지호
     * 3. 작성일 : 2016. 03. 15
     * </PRE>
	 * @param userId
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<CoinBuyLogDto> popcornBuyLogListAll(Long userId) {
		List<CoinBuyLogDto> Arr = new ArrayList<>();
		
		try {
			if (userId > 0)
				Arr = userMapper.popcornBuyLogListAll(userId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return Arr;
	}
	
	/**
	 * <PRE>
     * 1. Comment : 사용자의 핸드폰 번호 가져오기
     * 2. 작성자 : 안지호
     * 3. 작성일 : 2016. 07. 06
     * </PRE>
	 * @param userId
	 * @return
	 */
	@Transactional(readOnly=true)
	public String findUserPhoneNumber(Long userId) {
		String phoneNumber = "";
		
		try {
			if (userId > 0)
				phoneNumber = userMapper.findUserPhoneNumber(userId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return phoneNumber;
	}
	
	@Transactional(readOnly=true)
	public List<HashMap<String, Object>> countAppVersion() {
		List<HashMap<String, Object>> Arr = new ArrayList<>();
		
		try {
			Arr = userMapper.countAppVersion();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return Arr;
	}
	
	@Transactional(readOnly=true)
	public int getCountTotalUser() {
		int result=0;
		
		try {
			result = userMapper.getCountTotalUser();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
	
	@Transactional(readOnly=true)
	public int getCountCornBuyUser() {
		int result=0;
		
		try {
			result = userMapper.getCountCornBuyUser();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
	
	@Transactional(readOnly = true)
	public List<UserProductBuyLogDto> userItemBuyListAll(Long userId) {
		if (userId < 1L) throw new BirdAdminException(BirdAdminErrorCode.BAD_REQUEST);
		List<UserProductBuyLogDto>Arr = userMapper.userItemBuyListAll(userId);
		return Arr;
	}
	
	public int userConnectionCnt(String targetUrl1, String targetUrl2) throws IOException {
		int cnt=0;
		int cnt2=0;
		
		try {
			URL url = new URL(targetUrl1);
			URLConnection connection;
			InputStream is;
			InputStreamReader isr;
			BufferedReader br;
			
			connection = url.openConnection();
			is = connection.getInputStream();
			isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
			
			String buf = null;
			while (true) {
				buf = br.readLine();
				if (buf == null) break;
				cnt = Integer.valueOf(buf);
			}
			
			URL url2 = new URL(targetUrl1);
			URLConnection connection2;
			InputStream is2;
			InputStreamReader isr2;
			BufferedReader br2;
			
			connection2 = url2.openConnection();
			is2 = connection2.getInputStream();
			isr2 = new InputStreamReader(is2);
			br2 = new BufferedReader(isr2);
			
			String buf2 = null;
			while (true) {
				buf2 = br2.readLine();
				if (buf2 == null) break;
				cnt2 = Integer.valueOf(buf2);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return cnt + cnt2;
	}
	
	public void sendEmail() throws Exception {
		EmailSendDto dto = new EmailSendDto();
		dto.setSubject("제목");
		dto.setContent("내용");
		dto.setReceiver("anjo070@challabros.com");
		emailService.SendEMail(dto);
	}
	
	@Transactional(readOnly=true)
	public List<Integer> selectUserIdByAuthLog(String searchDate, int authInverval) {
		List<Integer> Arr = userMapper.selectUserIdByAuthLog(searchDate, -authInverval);
		return Arr;
	}
	
}
