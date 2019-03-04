package com.my.server.system.service.impl;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.common.common.DataPage;
import com.my.common.common.PageResult;
import com.my.common.system.domain.Module;
import com.my.common.system.service.ModuleService;
import com.my.server.system.manager.ModuleManager;
@Service("moduleServiceImpl")
public class ModuleServiceImpl implements ModuleService {
	@Autowired
	public ModuleManager ModuleManager;

	@Override
	public List<Module> queryModuleList(Module module) {
		return ModuleManager.queryModuleList(module);
	}

	@Override
	public PageResult<Module> queryModuleList(DataPage<Module> page, Module module) {
		page = ModuleManager.queryModuleList(page, module);
		PageResult<Module> result = new PageResult<Module>();
		result.setPage(page);
		return result;
	}

	@Override
	public List<Module> queryModuleListByIdList(List<Long> ids) {
		if (ids == null) {
			return null;
		}
		List<Module> result = new ArrayList<Module>();
		List<Module> modules = null;
		Module module = new Module();
		for (Long id : ids) {
			module.setId(id);
			modules = queryModuleList(module);
			if (modules != null) {
				result.add(modules.get(0));
			}
		}
		return result;
	}
	
	
	
	@Override
	public Module queryModuleById(Long id) {
		if (id == null) {
			return null;
		}
		List<Module> modules = null;
		Module module = new Module();
		module.setId(id);
		modules = queryModuleList(module);
		if (modules != null) {
			return modules.get(0);
		}
		return null;
	}
	
	@Override
	public int deleteModuleById(Long id) {
		if (id == null) {
			return 0;
		}
		return ModuleManager.deleteModuleById(id);
	}

	@Override
	public int saveModule(Module module) {
		return ModuleManager.saveModule(module);
	}

	@Override
	public int updateModule(Module module) {
		return ModuleManager.updateModule(module);
	}
	
	@Override
	public int updateModuleSortIndex(Module module) {
		return ModuleManager.updateModuleSortIndex(module);
	}

	@Override
	public Map<Long, Module> queryModuleMapByIdSet(Collection<Long> ids) {
		 
		return ModuleManager.queryModuleMapByIdSet(ids);
	}
	
}
