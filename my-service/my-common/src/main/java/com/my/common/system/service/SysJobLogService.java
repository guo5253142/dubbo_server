package com.my.common.system.service;

import com.my.common.system.domain.SysJobLog;

/**
 * job执行记录接口
 * 
 * @project my-common
 * @author guopeng
 * @date 2019年2月18日
 */
public interface SysJobLogService {
	/**
	 * 查询所有job配置
	 * @param jobLog
	 * @return
	 */
	public void insertSysJobLog(SysJobLog jobLog);

}
