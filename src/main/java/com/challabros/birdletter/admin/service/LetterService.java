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

import com.challabros.birdletter.admin.util.DateUtils;
import com.challabros.birdletter.admin.util.Util;
import com.challabros.birdletter.admin.dto.OpenLetterDto;
import com.challabros.birdletter.admin.dto.OpenLetterIllegalReportDto;
import com.challabros.birdletter.admin.dto.PagingDto;
import com.challabros.birdletter.admin.error.BirdAdminErrorCode;
import com.challabros.birdletter.admin.error.BirdAdminException;
import com.challabros.birdletter.admin.mapper.LetterMapper;
import com.challabros.birdletter.admin.util.Value;

@Service
public class LetterService {
	
	private final static Logger logger = LoggerFactory.getLogger(LetterService.class);
	
	@Autowired
	private LetterMapper letterMapper;
	
	/**
	 * <PRE>
	 * 1. Comment : 공개편지 리스트
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 05. 12
	 * </PRE> 
	 * @param sPage
	 * @param letterType
	 * @param startDate
	 * @param endDate
	 * @param userName
	 * @param phoneNumber
	 * @param sortType
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<OpenLetterDto> openLetterList(int sPage, String letterType, String startDate, 
			String endDate, String userName, String phoneNumber, String sortType) {
		PagingDto pagingDto = Util.getPaging(sPage, Value.pageInList10);
		
		HashMap<String, Object>paramMap = new HashMap<>();
		paramMap.put("start", pagingDto.getStart());
		paramMap.put("end", pagingDto.getEnd());
		paramMap.put("letterType", Util.isNullValue(letterType.toUpperCase(), ""));
		paramMap.put("startDate", Util.isNullValue(DateUtils.getOclock(startDate), ""));
		paramMap.put("endDate", Util.isNullValue(DateUtils.getLastDayOclock(endDate), ""));
		paramMap.put("userName", Util.isNullValue(userName, ""));
		paramMap.put("phoneNumber", Util.isNullValue(phoneNumber, ""));
		paramMap.put("sortType", Util.isNullValue(sortType.toUpperCase(), ""));
		
		List<OpenLetterDto>Arr = letterMapper.openLetterList(paramMap);
		return Arr;
	}
	
	/**
	 * <PRE>
	 * 1. Comment : 공개편지 리스트 개수
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 05. 12
	 * </PRE> 
	 * @param letterType
	 * @param startDate
	 * @param endDate
	 * @param userName
	 * @param phoneNumber
	 * @param sortType
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public int openLetterListCnt(String letterType, String startDate, 
			String endDate, String userName, String phoneNumber, String sortType) {
		HashMap<String, Object>paramMap = new HashMap<>();
		paramMap.put("letterType", Util.isNullValue(letterType.toUpperCase(), ""));
		paramMap.put("startDate", Util.isNullValue(startDate, ""));
		paramMap.put("endDate", Util.isNullValue(endDate, ""));
		paramMap.put("userName", Util.isNullValue(userName, ""));
		paramMap.put("phoneNumber", Util.isNullValue(phoneNumber, ""));
		paramMap.put("sortType", Util.isNullValue(sortType.toUpperCase(), ""));
		
		return letterMapper.openLetterListCnt(paramMap);
	}
	
	/**
	 * <PRE>
	 * 1. Comment : 공개편지 상세정보
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 07. 25
	 * </PRE> 
	 * @param letterId
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public OpenLetterDto detailOpenLetter(Long letterId) {
		if (letterId < 1) throw new BirdAdminException(BirdAdminErrorCode.BAD_REQUEST);
		
		OpenLetterDto openLetterDto = letterMapper.detailOpenLetter(letterId);
		return openLetterDto;
	}
	
	/**
	 * <PRE>
	 * 1. Comment : 광장편지 신고 된 갯수
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 07. 25
	 * </PRE> 
	 * @param letterId
	 * @return
	 */
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED)
	public int countSquareLetterReport(Long letterId) {
		if (letterId < 1) throw new BirdAdminException(BirdAdminErrorCode.BAD_REQUEST);
		return letterMapper.countSquareLetterReport(letterId);
	}
	
	/**
	 * <PRE>
	 * 1. Comment : 광장편지 신고 된 리스트 가져오기
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 07. 25
	 * </PRE> 
	 * @param sPage
	 * @param letterId
	 * @return
	 */
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED)
	public List<OpenLetterIllegalReportDto> findSquareLetterReport(int sPage, Long letterId) {
		if (letterId < 1) throw new BirdAdminException(BirdAdminErrorCode.BAD_REQUEST);
		
		PagingDto pagingDto = Util.getPaging(sPage, Value.pageInList5);
		List<OpenLetterIllegalReportDto>Arr = new ArrayList<OpenLetterIllegalReportDto>();
		Arr = letterMapper.findSquareLetterReport(pagingDto.getStart(), pagingDto.getEnd(), letterId);
		return Arr;
	}
	
	/**
	 * <PRE>
	 * 1. Comment : 광장편지 상태 변경
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 07. 25
	 * </PRE> 
	 * @param letterId
	 * @param blockYn
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean blockOpenLetter(Long letterId, int blockYn) {
		if (letterId < 1) throw new BirdAdminException(BirdAdminErrorCode.BAD_REQUEST);
		int cnt = letterMapper.blockOpenLetter(letterId, blockYn);
		if (cnt < 1) return false;
		return true;
	}
	

}
