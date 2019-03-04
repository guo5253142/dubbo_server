package com.my.common.system.service;

import java.util.List;

import com.my.common.system.domain.PermissionItem;

public interface PermissionItemService {

	public List<PermissionItem> queryPermissionItemList(PermissionItem PermissionItem);
	
	public PermissionItem getPermissionItemById(Long id);
	
	public int savePermissionItem(PermissionItem PermissionItem);
	
	public int updatePermissionItem(PermissionItem PermissionItem);
	
	public int delPermissionItemById(Long id);
	
	public int delPermissionItemByIds(List<String> ids);
	
}
