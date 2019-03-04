package com.my.common.system.service;

import java.util.List;

import com.my.common.common.DataPage;
import com.my.common.common.PageResult;
import com.my.common.system.domain.Role;

public interface RoleService {

	public List<Role> queryRoleList(Role role);
	
	public Role getRoleById(Long id);
	
	public PageResult<Role> queryRoleList(DataPage<Role>  page, Role role);
	
	public long saveRole(Role role);
	
	public int updateRole(Role role);
	
	public int deleteRole(Long id);
	
}
