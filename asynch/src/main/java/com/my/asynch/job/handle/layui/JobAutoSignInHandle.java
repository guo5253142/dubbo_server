package com.my.asynch.job.handle.layui;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.my.asynch.job.handle.JobBaseHandle;
import com.my.asynch.job.handle.layui.util.LayuiUtil;
import com.my.asynch.tools.DateUtil;
import com.my.asynch.tools.SpringApplicationContextHolder;
import com.my.common.common.ModelResult;
import com.my.common.system.domain.SysJobConfig;
import com.my.common.system.service.SysJobConfigService;
/**
 * layui自动签到程序
 * @author guopeng
 * 2019年2月18日17:06:50
 */
@Component
public class JobAutoSignInHandle extends JobBaseHandle {
	private static Logger logger = LoggerFactory.getLogger(JobAutoSignInHandle.class);
	
	public static volatile int flag = 0;

	/**
	 * 任务标识
	 */
	@Override
	public String getJobExcuteClassName() {
		return "JobAutoSignInHandle";
	}

	/**
	 * 执行任务
	 */
	@Override
	public void jobexcute() {
		if (JobAutoSignInHandle.flag != 0) {
			logger.info("上一个layui自动签到程序定时任务没有执行完成，退出......", DateUtil.toTimeStampFm());
			return;
		}
		try {
			JobAutoSignInHandle.flag = 1;
			logger.info("启动layui自动签到程序......");
			LayuiUtil util=new LayuiUtil();
			util.autoSignIn();
		} catch (Exception e) {
			logger.error("layui自动签到程序异常报错......",e);
		}finally{
			JobAutoSignInHandle.flag = 0;
		}
		
		//更新下一次自动签到执行时间
		SysJobConfigService service = SpringApplicationContextHolder.webApplicationContext.getBean(SysJobConfigService.class);
		SysJobConfig jobConfig=new SysJobConfig();
		jobConfig.setExcuteClassName("JobAutoSignInHandle");
		Random rand=new Random();
		int hour=rand.nextInt(22)+1;
		int min=rand.nextInt(59);
		int second=rand.nextInt(59);
		String cron=second+" "+min+" "+hour+" * * ? *";
		jobConfig.setCron(cron);
		ModelResult<String> result=service.updateSysJobConfig(jobConfig);
		if(result.isSuccess()){
			logger.info("更新下一次自动签到执行时间["+cron+"]");
		}
		
		
	}
}