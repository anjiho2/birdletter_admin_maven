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

import com.challabros.birdletter.admin.dto.LetterBirdDto;
import com.challabros.birdletter.admin.dto.PagingDto;
import com.challabros.birdletter.admin.error.BirdAdminErrorCode;
import com.challabros.birdletter.admin.error.BirdAdminException;
import com.challabros.birdletter.admin.mapper.UserMapper;
import com.challabros.birdletter.admin.util.Util;
import com.challabros.birdletter.admin.util.Value;

@Service
public class BirdService {
	
	final static Logger logger = LoggerFactory.getLogger(BirdService.class);
	
	@Autowired
	private UserMapper userMapper;
	
	/**
	 * <PRE>
	 * 1. Comment : 사용자의 새 리스트 갯수
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 01 .11
	 * </PRE> 
	 * @param userId
	 * @param productName
	 * @return
	 */
	@Transactional(readOnly=true)
	public int userLetterBirdListCnt(Long userId, String productName) {
		int cnt=0;
		
		try {
			cnt = userMapper.userLetterBirdListCnt(userId, productName);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return cnt;
	}
	
	/**
	 * <PRE>
	 * 1. Comment : 사용자의 새 리스트 
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 01 .11
	 * </PRE>
	 * @param sPage
	 * @param userId
	 * @param productName
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<LetterBirdDto> userLetterBirdList(int sPage, Long userId,  String productName) {
		List<LetterBirdDto> Arr = new ArrayList<LetterBirdDto>();
		
		try {
			PagingDto pagingDto = Util.getPaging(sPage, Value.pageIntLis15);
			LetterBirdDto letterBirdDto = new LetterBirdDto();
			letterBirdDto.setOwnerId(userId);
			letterBirdDto.setStart(pagingDto.getStart());
			letterBirdDto.setEnd(pagingDto.getEnd());
			letterBirdDto.setProductName(productName);
			
			Arr = userMapper.userLetterBirdList(letterBirdDto);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return Arr;
	}
	
	/**
	 * <PRE>
	 * 1. Comment : 사용자의 새 삭제
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 01 .11
	 * </PRE>
	 * @param birdId
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean deleteUserLetterBird(int birdId) {
		boolean bl = true;
		
		try {
			userMapper.deleteUserLetterBird(birdId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			bl = false;
		}
		return bl;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 사용자 포인트 변경 
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 12 .19 	
	 * </pre> 
	 * @param userId
	 * @param point
	 * @param type
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean updateUserPoint(Long userId, int point, String type) {
		if (userId < 0L && !"".equals(type)) {
			throw new BirdAdminException(BirdAdminErrorCode.BAD_REQUEST);
		}
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("userId", Long.toString(userId));
		if (Value.CORN.equals(type)) {
			paramMap.put("cornPoint", Util.isNullValue(Integer.toString(point), ""));
			paramMap.put("popcornPoint", "");
		} else if (Value.POPCORN.equals(type)) {
			paramMap.put("cornPoint", "");
			paramMap.put("popcornPoint", Util.isNullValue(Integer.toString(point), ""));
		}
		int result = userMapper.updateUserPoint(paramMap);
		if (result < 1) {
			return false;
		}
		return true;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 사용자 포인트 추가 
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 12 .19 	
	 * </pre>
	 * @param userId
	 * @param addPoint
	 * @param type
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean addUserPoint(Long userId, int addPoint, String type) {
		if (userId < 0L && !"".equals(type)) {
			throw new BirdAdminException(BirdAdminErrorCode.BAD_REQUEST);
		}
		int cornPoint = 0;
		int popcornPoint = 0;
		if (Value.CORN.equals(type)) {
			cornPoint = addPoint;
		} else if (Value.POPCORN.equals(type)) {
			popcornPoint = addPoint ;
		}
		int result = userMapper.addUserPoint(userId, cornPoint, popcornPoint);
		if (result < 1) {
			return false;
		}
		return true;
	}
	
}
