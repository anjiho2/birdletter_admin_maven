package com.challabros.birdletter.admin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.challabros.birdletter.admin.dto.TodayPresentPopcornInfoDto;
import com.challabros.birdletter.admin.mapper.HeartMapper;

@Service
public class HeartService {
	
	private final static Logger logger = LoggerFactory.getLogger(HeartService.class);
	
	@Autowired
	private HeartMapper heartMapper;
	
	@Transactional(readOnly=true, propagation=Propagation.REQUIRED)
	public TodayPresentPopcornInfoDto todayPresentPopcornInfo() {
		TodayPresentPopcornInfoDto dto = heartMapper.todayPresentPopcornInfo();
		return dto;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 오늘의 선물 팝콘 정보 입력
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 06 .03 
	 * </pre> 
	 * @param minPopcorn
	 * @param maxPopcorn
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public int insertTodayPresentPopcornInfo(int minPopcorn, int maxPopcorn) {
		return heartMapper.insertTodayPresentPopcornInfo(minPopcorn, maxPopcorn);
	}
	
	/**
	 * <pre>
	 * 1. Comment : 오늘의 선물 팝콘 정보 수정
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 06 .03 
	 * </pre> 
	 * @param idx
	 * @param minPopcorn
	 * @param maxPopcorn
	 * @param hour
	 * @param minute
	 * @param heartRewardLimit
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public int updateTodayPresentPopcornInfo(int idx, int minPopcorn, 
			int maxPopcorn, int hour, int minute, int heartRewardLimit) {
		TodayPresentPopcornInfoDto dto = new TodayPresentPopcornInfoDto(
			idx, minPopcorn, maxPopcorn, hour, minute, heartRewardLimit
		);
		return heartMapper.updateTodayPresentPopcornInfo(dto);
	}

}
