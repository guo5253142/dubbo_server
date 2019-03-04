package com.my.server.system.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.my.common.system.domain.SysJobLog;
import com.my.server.common.dao.GenericMybatisDao;
import com.my.server.system.manager.SysJobLogManager;

@Component
public class SysJobLogManagerImpl implements SysJobLogManager {

	@Autowired
	@Qualifier("adminDbDao")
	private GenericMybatisDao adminDbDao;
	
	@Autowired
	@Qualifier("transactionTemplate")
	private TransactionTemplate transactionTemplate;
	
	@Override
	public void insertSysJobLog(final SysJobLog jobLog) {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				adminDbDao.insertAndSetupId("SysJobLogMapper.insert", jobLog);
			}
		});
	}

}
