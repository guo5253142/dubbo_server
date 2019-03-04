package com.my.server.system.manager;

import java.util.List;

import com.my.common.common.ModelResult;
import com.my.common.system.domain.SysJobConfig;

/**
 * job配置manager接口
 * 
 * @project my-server
 * @author guopeng
 * @date 2019年2月18日
 */
public interface SysJobConfigManager {
	/**
	 * 查询所有job配置
	 * @return
	 * @create_time 2016年8月3日
	 */
	public List<SysJobConfig> querySysJobConfigList();
	
	public void updateSysJobConfig(SysJobConfig jobConfig);
}
