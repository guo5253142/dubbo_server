package com.my.common.system.service;

import java.util.List;

import com.my.common.common.ModelResult;
import com.my.common.system.domain.SysJobConfig;

/**
 * job配置管理接口
 * 
 * @project my-common
 * @author guopeng
 * @date 2019年2月18日
 */
public interface SysJobConfigService {
	/**
	 * 查询所有job配置
	 * @return
	 */
	public ModelResult<List<SysJobConfig>> querySysJobConfigList();
	
	/**
	 * 根据job名称更新job配置
	 * @param className
	 * @return
	 */
	public ModelResult<String> updateSysJobConfig(SysJobConfig jobConfig);

}
