package com.challabros.birdletter.admin.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.challabros.birdletter.admin.config.QuartzConfigHolder;
import com.challabros.birdletter.admin.service.PushService;

/**
 * <pre>
 * 1. Comment : 매일 08시00분에 푸시정보가 pushSendList테이블 삭제
 * 2. 작성자 : 안지호
 * 3. 작성일 : 2016. 02. 11
 * </pre> 
 * @author anjiho
 *
 */
public class DeleteQuartz extends QuartzJobBean {
	
	private final static Logger logger = LoggerFactory.getLogger(DeleteQuartz.class);
	
	private ApplicationContext context;

	@Override
	protected void executeInternal(JobExecutionContext ex)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		context = (ApplicationContext)ex.getJobDetail().getJobDataMap().get("applicationContext");
		executeJob(ex);
	}
	
	@SuppressWarnings("static-access")
	private void executeJob(JobExecutionContext ex) {
		QuartzConfigHolder config = (QuartzConfigHolder)context.getBean("QuartzConfigHolder");
		if (config.isQuartzUseCheck() == true) {
			PushService pushService = (PushService)context.getBean("pushService");
			logger.info("================ DeleteQuartz start ===================");
			pushService.deletePushSendList();
			logger.info("================ DeleteQuartz end ===================");	
		}
		
	}
}
