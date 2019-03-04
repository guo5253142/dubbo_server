package com.my.asynch.job;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PreDestroy;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.spi.JobFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.my.asynch.job.handle.JobBaseHandle;
import com.my.asynch.job.handle.JobHandleFactory;
import com.my.common.common.ModelResult;
import com.my.common.common.constant.UsedTag;
import com.my.common.system.domain.SysJobConfig;
import com.my.common.system.service.SysJobConfigService;
import com.my.common.tools.DateUtil;

/**
 * job启动初始化
 * 
 * @project asynch
 * @author guopeng
 * @date 2019年2月18日17
 */
@Component
public class JobManager implements ApplicationListener<ContextRefreshedEvent> {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	public static boolean isStart = false;
	private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();
	private static String JOB_GROUP_NAME = "sinafenqi_job_group";
	private static String TRIGGER_GROUP_NAME = "sinafenqi_trigger_group";
	@Autowired
	private JobHandleFactory jobHandleFactory;
	@Autowired
	private SysJobConfigService sysJobConfigService;
	
	@Autowired
	public void setJobFactory(JobFactory jobFactory) {
		try {
			schedulerFactory.getScheduler().setJobFactory(jobFactory);
		} catch (SchedulerException e) {
			//logger.error("", e);
		}
	}
	
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		ModelResult<List<SysJobConfig>> modelResult = sysJobConfigService.querySysJobConfigList();
		List<SysJobConfig> sysJobConfigList = modelResult.getResult();
		logger.info("job初始装配开始...........");
		if (!isStart) {
			isStart = true;
			if (sysJobConfigList != null) {
				for (SysJobConfig SysJobConfig : sysJobConfigList) {
					logger.info("任务[{}]config：{}",SysJobConfig.getName(),JSON.toJSONString(SysJobConfig));
					initOrRefreshJob(SysJobConfig);
				}
			}
		}
		logger.info("job初始装配结束...........");
		//启动一个定时任务，加载维护Job列表
		Thread thread = new Thread(){
			@Override
			public void run() {
				watchSysJobConfig();
			}
			
		};
		thread.start();
	}
	public void watchSysJobConfig(){
		while (true) {
			logger.info("job维护装配开始...........");
			try {
				//20秒加载维护一次
				Thread.sleep(1800*1000);
				ModelResult<List<SysJobConfig>> modelResult = sysJobConfigService.querySysJobConfigList();
				List<SysJobConfig> sysJobConfigList = modelResult.getResult();
				if (sysJobConfigList == null) {
					continue;
				}
				for (SysJobConfig SysJobConfig : sysJobConfigList) {
					initOrRefreshJob(SysJobConfig);
				}
			} catch (Throwable e) {
				logger.error("", e);
			}
			logger.info("job维护装配结束...........");
		}
	}
	/**
	 * 初始化job或者刷新job
	 * @param SysJobConfig
	 * @create_time 2016年7月30日
	 */
	public  void initOrRefreshJob(SysJobConfig sysJobConfig) {
		try {
			Scheduler sched = schedulerFactory.getScheduler();
			CronTrigger trigger = (CronTrigger) sched.getTrigger(sysJobConfig.getExcuteClassName(),TRIGGER_GROUP_NAME);
			if (sysJobConfig.getUsedTag() == UsedTag.forbidden.getIndex() && trigger == null ) {
				//不存在定时器的无效任务直接返回
				return;
			}
			JobBaseHandle jobBaseHandle = jobHandleFactory.getJobHandle(sysJobConfig.getExcuteClassName());
			if (jobBaseHandle == null) {
				return;
			}
			if (sysJobConfig.getUsedTag() == UsedTag.forbidden.getIndex() && trigger != null) {
				logger.info("任务[{}]移除,Config：{}",sysJobConfig.getName(),JSON.toJSONString(sysJobConfig));
				//存在定时器的无效任务直接移除
				removeJob(sysJobConfig.getExcuteClassName());
				return;
			}
			if (trigger == null) {
				addJob(sysJobConfig.getExcuteClassName(), jobBaseHandle.getClass(), sysJobConfig.getCron());
				return;
			}
			String oldCron = trigger.getCronExpression();
			if (!oldCron.equalsIgnoreCase(sysJobConfig.getCron())) {
				logger.info("任务[{}]更新,config[{}],oldCron[{}]",sysJobConfig.getName(),JSON.toJSONString(sysJobConfig),oldCron);
				JobDetail jobDetail = sched.getJobDetail(sysJobConfig.getExcuteClassName(),JOB_GROUP_NAME);
				Class objJobClass = jobDetail.getJobClass();
				removeJob(sysJobConfig.getExcuteClassName());
				addJob(sysJobConfig.getExcuteClassName(), objJobClass, sysJobConfig.getCron());
			}
		} catch (Throwable e) {
			String errMsg = String.format("job初始化或者刷新异常config:[%s]", JSON.toJSON(sysJobConfig));
			logger.error(errMsg,e);
		}
	}

	/**
	 * 添加一个job
	 * 
	 * @param jobName
	 * @param jobclass
	 * @param cron
	 * @create_time 2016年7月30日
	 */
	public static void addJob(String jobName, Class jobclass, String cron) {
		try {
			Scheduler sched = schedulerFactory.getScheduler();
			// jobdetail
			JobDetail jobDetail = new JobDetail(jobName, JOB_GROUP_NAME,
					jobclass);
			// 触发器
			CronTrigger trigger = new CronTrigger(jobName, TRIGGER_GROUP_NAME);
			// 触发器时间设定
			trigger.setCronExpression(cron);
			sched.scheduleJob(jobDetail, trigger);
			// 启动
			if (!sched.isShutdown()) {
				sched.start();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	public static void removeJob(String jobExcuteClassName) {
		try {
			Scheduler sched = schedulerFactory.getScheduler();
			// 停止触发器
			sched.pauseTrigger(jobExcuteClassName, TRIGGER_GROUP_NAME);
			// 移除触发器
			sched.unscheduleJob(jobExcuteClassName, TRIGGER_GROUP_NAME);
			// 删除任务
			sched.deleteJob(jobExcuteClassName, JOB_GROUP_NAME);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 关闭所有任务
	 * 
	 * @create_time 2016年7月30日 
	 */
	@PreDestroy
	public  void shutdownJobs() {
		try {
			Scheduler sched = schedulerFactory.getScheduler();
			if (!sched.isShutdown()) {
				sched.shutdown();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<List<String>> getJobDetail(){
		List<List<String>> jobList = new ArrayList<List<String>>(); 
		try {
			Scheduler sched = schedulerFactory.getScheduler();
			ModelResult<List<SysJobConfig>> modelResult = sysJobConfigService.querySysJobConfigList();
			List<SysJobConfig> sysJobConfigList = modelResult.getResult();
			List<String> jobctList = new ArrayList<String>();
			jobctList.add("名称");
			jobctList.add("上次执行时间");
			jobctList.add("下次执行时间");
			jobctList.add("描述");
			jobctList.add("表达式");
			jobctList.add("联系人");
			jobctList.add("联系人电话");
			jobList.add(jobctList);
			for (SysJobConfig sysJobConfig : sysJobConfigList) {
				CronTrigger trigger = (CronTrigger) sched.getTrigger(sysJobConfig.getExcuteClassName(),TRIGGER_GROUP_NAME);
				jobctList = new ArrayList<String>();
				jobctList.add(sysJobConfig.getName());//名称
				Calendar previousTime = Calendar.getInstance();
				if (trigger != null && trigger.getPreviousFireTime() != null) {//上次执行时间
					previousTime.setTime(trigger.getPreviousFireTime());
					jobctList.add(DateUtil.toTimeStampFm(previousTime));
				}else {
					jobctList.add("00-00-00");
				}
				Calendar nextTime = Calendar.getInstance();
				if (trigger != null) {//下次执行时间
					nextTime.setTime(trigger.getNextFireTime());
					jobctList.add(DateUtil.toTimeStampFm(nextTime));
				}else {
					jobctList.add("00-00-00");
				}
				jobctList.add(sysJobConfig.getRemark());//描述
				jobctList.add(sysJobConfig.getCron());//表达式
				jobctList.add(sysJobConfig.getOwner());//联系人
				jobctList.add(sysJobConfig.getOwnerPhone());//联系人电话
				jobList.add(jobctList);
			}
			
		} catch (Exception e) {
			logger.error("获取job详情异常",e);
			return new ArrayList<List<String>>();
		}
		return jobList;
	}
	
	
	public void removeAll(){
		try {
			ModelResult<List<SysJobConfig>> modelResult = sysJobConfigService.querySysJobConfigList();
			List<SysJobConfig> sysJobConfigList = modelResult.getResult();
			for (SysJobConfig sysJobConfig : sysJobConfigList) {
				removeJob(sysJobConfig.getExcuteClassName());
			}
		} catch (Exception e) {
			logger.error("获取job详情异常",e);
		}
	}
}
