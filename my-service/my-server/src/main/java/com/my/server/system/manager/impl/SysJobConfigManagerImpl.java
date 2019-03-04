package com.my.server.system.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.my.common.system.domain.SysJobConfig;
import com.my.server.common.dao.GenericMybatisDao;
import com.my.server.system.manager.SysJobConfigManager;
/**
 * job配置manager实现
 * 
 * @project server
 * @author guopeng
 */
@Component
public class SysJobConfigManagerImpl implements SysJobConfigManager {
	@Autowired
	@Qualifier("adminDbDao")
	private GenericMybatisDao adminDbDao;
	@Override
	public List<SysJobConfig> querySysJobConfigList() {
		return adminDbDao.queryList("SysJobConfigMapper.querySysJobConfigList",null);
	}
	@Override
	public void updateSysJobConfig(SysJobConfig jobConfig) {
		adminDbDao.updateByObj("SysJobConfigMapper.updateSysJobConfig", jobConfig);
	}

}
