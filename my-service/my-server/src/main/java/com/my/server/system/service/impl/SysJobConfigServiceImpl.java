package com.my.server.system.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.common.common.ModelResult;
import com.my.common.system.domain.SysJobConfig;
import com.my.common.system.service.SysJobConfigService;
import com.my.server.system.manager.SysJobConfigManager;
/**
 * job配置管理接口实现
 * 
 * @project sinafenqi-server
 * @author duannp
 * @date 2016年8月3日
 * www.sinafenqi.com
 */
@Service("sysJobConfigServiceImpl")
public class SysJobConfigServiceImpl implements SysJobConfigService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private SysJobConfigManager sysJobConfigManager;
	@Override
	public  ModelResult<List<SysJobConfig>> querySysJobConfigList() {
		ModelResult<List<SysJobConfig>> modelResult = new ModelResult<List<SysJobConfig>>();
		try {
			List<SysJobConfig> jobConfigList = sysJobConfigManager.querySysJobConfigList();
			modelResult.setResult(jobConfigList);
		} catch (Exception e) {
			logger.error("接口SysJobConfigServiceImpl.querySysJobConfigList运行时异常", e);
		}
		return modelResult;
	}
	@Override
	public ModelResult<String> updateSysJobConfig(SysJobConfig jobConfig) {
		ModelResult<String> modelResult = new ModelResult<>();
		sysJobConfigManager.updateSysJobConfig(jobConfig);
		return modelResult;
	}

}
