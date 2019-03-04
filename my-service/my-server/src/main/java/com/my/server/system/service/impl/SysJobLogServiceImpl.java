package com.my.server.system.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.common.system.domain.SysJobLog;
import com.my.common.system.service.SysJobLogService;
import com.my.server.system.manager.SysJobLogManager;

@Service
public class SysJobLogServiceImpl implements SysJobLogService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private SysJobLogManager sysJobLogManager;
	@Override
	public void insertSysJobLog(SysJobLog jobLog) {
		try {
			sysJobLogManager.insertSysJobLog(jobLog);
		} catch (Exception e) {
			logger.error(String.format("记录job执行日记错误。参数：%s",jobLog.toString()), e);
		}
	}

}
