package com.my.server.system.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.common.common.DataPage;
import com.my.common.common.PageResult;
import com.my.common.system.domain.Permission;
import com.my.common.system.service.PermissionService;
import com.my.server.system.manager.PermissionManager;
@Service("permissionServiceImpl")
public class PermissionServiceImpl implements PermissionService {
	@Autowired
	public PermissionManager permissionManager;

	@Override
	public List<Permission> queryPermissionList(Permission permission) {
		return permissionManager.queryPermissionList(permission);
	}

	@Override
	public PageResult<Permission> queryPermissionList(DataPage<Permission> page, Permission permission) {
		page = permissionManager.queryPermissionList(page, permission);
		PageResult<Permission> result = new PageResult<Permission>();
		result.setPage(page);
		return result;
	}

	@Override
	public Permission queryPermissionById(Long id) {
		if (id == null) {
			return null;
		}
		Permission permission = new Permission();
		permission.setId(id);
		List<Permission> permissionList = queryPermissionList(permission);
		if (permissionList != null) {
			return permissionList.get(0);
		}
		return null;
	}
	
	@Override
	public List<Permission> queryPermissionByIdList(List<Long> ids) {
		if (ids == null) {
			return null;
		}
//		List<Permission> result = new ArrayList<Permission>();
//		List<Permission> permissions = null;
//		Permission permission = new Permission();
//		for (Long id : ids) {
//			permission.setId(id);
//			permissions = queryPermissionList(permission);
//			if (permission != null) {
//				result.add(permissions.get(0));
//			}
//		}
		return permissionManager.queryPermissionListByIdSet(ids);
	}

	@Override
	public int savePermission(Permission permission) {
		return permissionManager.savePermission(permission);
	}
	
	@Override
	public int updatePermission(Permission permission) {
		if (permission.getId() == null) {
			return 0;
		}
		return permissionManager.updatePermission(permission);
	}
	
	@Override
	public int updatePermissionSortIndex(Permission permission) {
		return permissionManager.updatePermissionSortIndex(permission);
	}

	@Override
	public int deletePermission(Long id) {
		return permissionManager.deletePermission(id);
	}

}
