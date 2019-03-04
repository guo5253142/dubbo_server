package com.my.server.system.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.common.common.DataPage;
import com.my.common.common.PageResult;
import com.my.common.system.domain.Role;
import com.my.common.system.service.RoleService;
import com.my.server.system.manager.RoleManager;
@Service("roleServiceImpl")
public class RoleServiceImpl implements RoleService {
	@Autowired
	public RoleManager roleManager;

	@Override
	public List<Role> queryRoleList(Role role) {
		return roleManager.queryRoleList(role);
	}

	@Override
	public PageResult<Role> queryRoleList(DataPage<Role> page, Role role) {
		page = roleManager.queryRoleList(page, role);
		PageResult<Role> result = new PageResult<Role>();
		result.setPage(page);
		return result;
	}

	@Override
	public long saveRole(Role role) {
		return roleManager.saveRole(role);
	}
	
	@Override
	public int updateRole(Role role) {
		return roleManager.updateRole(role);
	}
	
	@Override
	public int deleteRole(Long id) {
		return roleManager.deleteRole(id);
	}

	@Override
	public Role getRoleById(Long id) {
		if (id == null) {
			return null;
		}
		Role role = new Role();
		role.setId(id);
		List<Role> list = queryRoleList(role);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	
}
