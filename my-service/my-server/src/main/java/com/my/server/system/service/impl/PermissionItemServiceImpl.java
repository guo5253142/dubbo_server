package com.my.server.system.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.my.common.system.domain.PermissionItem;
import com.my.common.system.domain.RolePermission;
import com.my.common.system.service.PermissionItemService;
import com.my.server.system.manager.PermissionItemManager;
import com.my.server.system.manager.RolePermissionManager;
@Service("permissionItemServiceImpl")
public class PermissionItemServiceImpl implements PermissionItemService {
	@Autowired
	public PermissionItemManager permissionItemManager;
	@Autowired
	public RolePermissionManager rolePermissionManager;

	@Override
	public List<PermissionItem> queryPermissionItemList(
			PermissionItem permissionItem) {
		return permissionItemManager.queryPermissionItemList(permissionItem);
	}

	@Override
	public int savePermissionItem(PermissionItem permissionItem) {
		return permissionItemManager.savePermissionItem(permissionItem);
	}

	@Override
	public int updatePermissionItem(PermissionItem permissionItem) {
		return permissionItemManager.updatePermissionItem(permissionItem);
	}

	@Override
	public PermissionItem getPermissionItemById(Long id) {
		if (id == null) {
			return null;
		}
		PermissionItem item = new PermissionItem();
		item.setId(id);
		List<PermissionItem> resultList = queryPermissionItemList(item);
		if (resultList != null && resultList.size() > 0) {
			return resultList.get(0);
		}
		return null;
	}

	@Override
	@Transactional
	public int delPermissionItemById(Long id) {
		RolePermission rolePermission=new RolePermission();
		rolePermission.setPermissionItemId(id);
		rolePermissionManager.deleteRolePermission(rolePermission);
		return permissionItemManager.delPermissionItem(id);
	}

	@Override
	public int delPermissionItemByIds(List<String> ids) {
		// TODO Auto-generated method stub
		return permissionItemManager.delPermissionItemByIds(ids);
	}



	
}
