package com.challabros.birdletter.admin.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.challabros.birdletter.admin.define.datasource.DataSource;
import com.challabros.birdletter.admin.define.datasource.DataSourceType;
import com.challabros.birdletter.admin.service.PushService;
import com.challabros.birdletter.admin.util.Util;

@Component
public class PushManager {

	final static Logger logger = LoggerFactory.getLogger(PushManager.class);
	
	@Autowired
	private PushService pushService;
	
	@DataSource(DataSourceType.PUSH)
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean giftPush(long userId, String content, String subTitle) {
		int pushIdx = pushService.insertPushInfoByGift(content, subTitle);
		if (pushIdx > 0) {
			String now = Util.returnNow();
			pushService.insertPushSendListByGift(userId, pushIdx, content, subTitle, 301, now);
		}
		return true;
	}
}
