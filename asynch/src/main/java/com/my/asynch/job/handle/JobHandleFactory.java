package com.my.asynch.job.handle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 获取job对象工厂
 * 
 * @project sinafenqi-asynch
 * @author duannp
 * @date 2016年7月30日
 * www.sinafenqi.com
 */
@Component
public class JobHandleFactory {
	
	private static Map<String, JobBaseHandle> jobMap = new HashMap<String, JobBaseHandle>();
	
	@Autowired
	private void setJobHandle(List<JobBaseHandle> jobBaseHandleList){
		if (jobBaseHandleList == null) {
			return;
		}
		for (JobBaseHandle jobBaseHandle : jobBaseHandleList) {
			jobMap.put(jobBaseHandle.getJobExcuteClassName(), jobBaseHandle);
		}
	}
	
	public JobBaseHandle getJobHandle(String jobObjFlag){
		return jobMap.get(jobObjFlag);
	}

}
