package com.challabros.birdletter.admin.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.StatefulJob;
import org.quartz.impl.StdSchedulerFactory;
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
public class BirthdayQuartz extends QuartzJobBean implements StatefulJob {
	
	final static Logger logger = LoggerFactory.getLogger(BirthdayQuartz.class);
	
	private ApplicationContext ctx;

	@Override
	protected void executeInternal(JobExecutionContext ex)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		ctx = (ApplicationContext)ex.getJobDetail().getJobDataMap().get("applicationContext");
		try {
			executeJob(ex);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("static-access")
	private void executeJob(JobExecutionContext ex) throws SchedulerException {
		QuartzConfigHolder config = (QuartzConfigHolder) ctx.getBean("QuartzConfigHolder");
		if (config.isQuartzUseCheck() == true) {
			logger.info("=================== BirthdayCron start =====================");
			CronManager cronManager = (CronManager)ctx.getBean("cronManager");
			String mmdd = DateUtils.plusDay(Util.returnToDate("yyyy-MM-dd"), "MMDD", 1);
			cronManager.birthdaySchedule(mmdd);
			logger.info("=================== BirthdayCron end =====================");
		} else {
			logger.info("=================== BirthdayCron isQuartzUseCheck False =====================");
		}
	}
	
	

}
