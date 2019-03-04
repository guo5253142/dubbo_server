package com.my.asynch.job.handle;

import java.util.Calendar;
import java.util.Date;

import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.my.asynch.tools.SpringApplicationContextHolder;
import com.my.common.system.domain.SysJobConfig;
import com.my.common.system.domain.SysJobLog;
import com.my.common.system.service.SysJobLogService;
import com.my.common.tools.DateUtil;

/**
 * 定时任务基础handle
 * 
 * @project asynch
 * @author guopeng
 * @date 2016年7月30日
 */
public abstract class JobBaseHandle implements Job{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 
	 */
	private SysJobConfig jobConfig;
	/**
	 * 任务标识
	 * @return
	 * @create_time 2016年7月30日
	 */
	public abstract String getJobExcuteClassName();
	/**
	 * job业务执行方法
	 * 
	 * @create_time 2016年7月30日
	 */
	public abstract void jobexcute();

	
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		logger.info("任务[{}],cronExpression[{}]执行开始....",context.getJobDetail().getName(),((CronTrigger)context.getTrigger()).getCronExpression());
		try {
			jobexcute();
		} catch (Throwable e) {
			String errMsg = String.format("任务[%s]异常", context.getJobDetail().getName());
			logger.error(errMsg,e);
		}
		Calendar nextTime = Calendar.getInstance();
		nextTime.setTime(context.getNextFireTime());
		logger.info("任务[{}]执行结束....下次执行时间[{}]",context.getJobDetail().getName(),DateUtil.toTimeStampFm(nextTime));
		//记录执行日记
		Date startTime = context.getFireTime();
		Date endTime = new Date();
		SpringApplicationContextHolder.webApplicationContext.getBean(SysJobLogService.class).insertSysJobLog(
				new SysJobLog(getJobExcuteClassName(), startTime, endTime, endTime.getTime() - startTime.getTime()));
	}
	public SysJobConfig getJobConfig() {
		return jobConfig;
	}
	public void setJobConfig(SysJobConfig jobConfig) {
		this.jobConfig = jobConfig;
	}
	
	

	
}
