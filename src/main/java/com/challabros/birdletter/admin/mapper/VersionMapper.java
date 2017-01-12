package com.challabros.birdletter.admin.mapper;

import java.util.HashMap;

import org.apache.ibatis.annotations.Param;

import com.challabros.birdletter.admin.dto.VersionInfoDto;

public interface VersionMapper {
	
	/** SELECT **/
	VersionInfoDto birdLetterVersionInfo(@Param("device")String device);
	
	VersionInfoDto birdLetterVersionInfo_test(@Param("device")String device);
	
	/** UPDATE **/
	int updateBirdLetterVersionInfo(HashMap<String, Object>paramMap);
	
	int updateBirdLetterVersionInfo_test(HashMap<String, Object>paramMap);

}
