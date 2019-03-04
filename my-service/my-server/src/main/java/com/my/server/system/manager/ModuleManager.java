package com.my.server.system.manager;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.my.common.common.DataPage;
import com.my.common.system.domain.Module;

public interface ModuleManager {
	public List<Module> queryModuleList(Module module) ;
	public Map<Long,Module> queryModuleMapByIdSet(final Collection<Long> ids);
	public DataPage<Module> queryModuleList(DataPage<Module> page, Module module) ;
	
	public int deleteModuleById(Long id);
	
	public int saveModule(Module module);
	
	public int updateModule(Module module);
	
	public int updateModuleSortIndex(Module module);
}
