package com.challabros.birdletter.admin.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.challabros.birdletter.admin.config.QuartzConfigHolder;
import com.challabros.birdletter.admin.manager.CronManager;
import com.challabros.birdletter.admin.util.DateUtils;
import com.challabros.birdletter.admin.util.Util;

@Component
public class AnniversaryQuartz extends QuartzJobBean {
	
	final static Logger logger = LoggerFactory.getLogger(AnniversaryQuartz.class);
	
	private ApplicationContext ctx;

	@Override
	protected void executeInternal(JobExecutionContext ex)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		ctx = (ApplicationContext)ex.getJobDetail().getJobDataMap().get("applicationContext");
		try {
			executeJob(ex);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("static-access")
	private void executeJob(JobExecutionContext ex) throws Exception {
		QuartzConfigHolder config = (QuartzConfigHolder) ctx.getBean("QuartzConfigHolder");
		if (config.isQuartzUseCheck() == true) {
			CronManager cronManager = (CronManager)ctx.getBean("cronManager");
			logger.info("================== 기념일 알림 시작 ==================");
			String mmdd = DateUtils.plusDay(Util.returnToDate("yyyy-MM-dd"), "MMDD", 1);
			String yyyymmdd = DateUtils.plusDay(Util.returnToDate("yyyy-MM-dd"), "YYYYMMDD", 1);
			cronManager.anniversarySchedule(yyyymmdd, mmdd);
			logger.info("================== 기념일 알림 끝 ==================");	
		}
		
	}

}
