package com.my.asynch.job;

import org.quartz.Job;
import org.quartz.SchedulerException;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringSingletonJobFactory implements JobFactory, ApplicationContextAware {
	private ApplicationContext applicationContext;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	@Override
	public Job newJob(TriggerFiredBundle bundle) throws SchedulerException {
		return (Job) this.applicationContext.getBean(bundle.getJobDetail().getJobClass());
	}
}
