package com.challabros.birdletter.admin.mapper;

import org.apache.ibatis.annotations.Param;

import com.challabros.birdletter.admin.dto.TodayPresentPopcornInfoDto;

public interface HeartMapper {
	
	TodayPresentPopcornInfoDto todayPresentPopcornInfo();
	
	int insertTodayPresentPopcornInfo(@Param("minimumPopcorn")int minimumPopcorn, @Param("maxPopcorn")int maxPopcorn);
	
	int updateTodayPresentPopcornInfo(TodayPresentPopcornInfoDto todayPresentPopcornInfoDto);

}
