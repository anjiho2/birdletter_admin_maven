package com.challabros.birdletter.admin.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.challabros.birdletter.admin.dto.OpenLetterDto;
import com.challabros.birdletter.admin.dto.OpenLetterIllegalReportDto;

public interface LetterMapper {
	
	/** SELECT **/
	int openLetterListCnt(HashMap<String, Object>paramMap);
	
	List<OpenLetterDto>openLetterList(HashMap<String, Object>paramMap);
	
	OpenLetterDto detailOpenLetter(@Param("letterId")Long letterId);
	
	int countSquareLetterReport(@Param("letterId")Long letterId);
	
	List<OpenLetterIllegalReportDto>findSquareLetterReport(@Param("start")String start, @Param("end")String end, @Param("letterId")Long letterId);
	
	/** UPDATE **/
	int blockOpenLetter(@Param("letterId")Long letterId, @Param("blockYn")int blockYn);

}
