package com.my.server.system.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.common.system.domain.RolePermission;
import com.my.common.system.service.RolePermissionService;
import com.my.server.system.manager.RolePermissionManager;
@Service("rolePermissionServiceImpl")
public class RolePermissionServiceImpl implements RolePermissionService {
	@Autowired
	public RolePermissionManager rolePermissionManager;

	@Override
	public List<RolePermission> queryRolePermissionListByRoleId(Long roleId) {
		if (roleId == null) {
			return null;
		}
		return rolePermissionManager.queryRolePermissionListByRoleId(roleId);
	}

	@Override
	public List<Long> queryDistinctPermissionIdByRoleId(Long roleId) {
		if (roleId == null) {
			return null;
		}
		return rolePermissionManager.queryDistinctPermissionIdByRoleId(roleId);
	}

	@Override
	public List<RolePermission> queryRolePermissionList(RolePermission rolePermission) {
		return rolePermissionManager.queryRolePermissionList(rolePermission);
	}

	@Override
	public void saveRolePermission(RolePermission rolePermission) {
		 rolePermissionManager.saveRolePermission(rolePermission);
	}

	@Override
	public int deleteRolePermission(RolePermission rolePermission) {
		return rolePermissionManager.deleteRolePermission(rolePermission);
	}
}
