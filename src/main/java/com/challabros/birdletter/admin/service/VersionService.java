package com.challabros.birdletter.admin.service;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.challabros.birdletter.admin.util.Util;
import com.challabros.birdletter.admin.define.datasource.DataSource;
import com.challabros.birdletter.admin.define.datasource.DataSourceType;
import com.challabros.birdletter.admin.dto.VersionInfoDto;
import com.challabros.birdletter.admin.error.BirdAdminErrorCode;
import com.challabros.birdletter.admin.error.BirdAdminException;
import com.challabros.birdletter.admin.mapper.VersionMapper;

@Service
public class VersionService {
	
	static final Logger logger = LoggerFactory.getLogger(VersionService.class);
	
	@Autowired
	private VersionMapper versionMapper;
	
	/**
	 * <pre>
	 * 1. Comment : 버드레터 버전 정보
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 03. 25
	 * </pre> 
	 * @param device
	 * @param serverKind
	 * @return
	 */
	@DataSource(DataSourceType.VERSION)
	@Transactional(readOnly=true, propagation=Propagation.REQUIRED)
	public VersionInfoDto birdLetterVersionInfo(String device, String serverKind) {
		if ("".equals(device)) throw new BirdAdminException(BirdAdminErrorCode.BAD_REQUEST);
		
		VersionInfoDto dto = new VersionInfoDto();
		if ("aws".equals(serverKind)) dto = versionMapper.birdLetterVersionInfo(device);
		else if ("test".equals(serverKind)) dto = versionMapper.birdLetterVersionInfo_test(device);
		return dto;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 버드레터 버전 정보 수정
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 03. 25
	 * </pre> 
	 * @param Idx
	 * @param versionNumber
	 * @param serverOnoff
	 * @param serverStatusCode
	 * @param serverStatusContent
	 * @param serverStatusTitle
	 * @param serverKind
	 * @return
	 */
	@DataSource(DataSourceType.VERSION)
	@Transactional(propagation=Propagation.REQUIRED)
	public int updateBirdLetterVersionInfo(int Idx, String versionNumber, String serverOnoff, 
			String serverStatusCode, String serverStatusContent, String serverStatusTitle, String serverKind) {
		HashMap<String, Object> paramMap = new HashMap<>();
		paramMap.put("idx", Idx);
		paramMap.put("versionNumber", Util.isNullValue(versionNumber, ""));
		paramMap.put("serverOnoff", Util.isNullValue(serverOnoff, ""));
		paramMap.put("serverStatusCode", Util.isNullValue(serverStatusCode, ""));
		paramMap.put("serverStatusContent", Util.isNullValue(serverStatusContent, ""));
		paramMap.put("serverStatusTitle", Util.isNullValue(serverStatusTitle, ""));
		paramMap.put("updateDate", Util.isNullValue(String.valueOf(Util.now()), ""));
		
		int result = 0;
		if ("aws".equals(serverKind)) result = versionMapper.updateBirdLetterVersionInfo(paramMap);
		else if ("test".equals(serverKind)) result = versionMapper.updateBirdLetterVersionInfo_test(paramMap);
		return result;
	}

}
