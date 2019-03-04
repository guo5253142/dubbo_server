package com.my.server.system.manager;

import java.util.List;

import com.my.common.common.DataPage;
import com.my.common.system.domain.Role;

public interface RoleManager {
	public List<Role> queryRoleList(Role role);
	
	public DataPage<Role> queryRoleList(DataPage<Role> page, Role role);
	public long saveRole(Role role);
	
	public int updateRole(Role role);
	
	public int deleteRole(Long id);
	

}
