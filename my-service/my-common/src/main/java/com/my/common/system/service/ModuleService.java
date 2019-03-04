package com.my.common.system.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.my.common.common.DataPage;
import com.my.common.common.PageResult;
import com.my.common.system.domain.Module;

public interface ModuleService {

	public List<Module> queryModuleList(Module module);
	
	public PageResult<Module> queryModuleList(DataPage<Module>  page, Module module);
	
	public List<Module> queryModuleListByIdList(List<Long> ids);
	
	public Module queryModuleById(Long id);
	
	public int saveModule(Module module);
	
	public int deleteModuleById(Long id);
	
	public int updateModule(Module module);
	
	public int updateModuleSortIndex(Module module);
	
	public Map<Long,Module> queryModuleMapByIdSet(final Collection<Long> ids);
}
