package com.my.common.system.service;

import java.util.List;

import com.my.common.system.domain.RolePermission;

public interface RolePermissionService {

	public List<RolePermission> queryRolePermissionListByRoleId(Long roleId);
	
	public List<Long> queryDistinctPermissionIdByRoleId(Long roleId);
	
	public List<RolePermission> queryRolePermissionList(RolePermission rolePermission);
	
	public void saveRolePermission(RolePermission rolePermission);
	
	public int deleteRolePermission(RolePermission rolePermission);
}
