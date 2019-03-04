package com.my.server.system.manager;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.my.common.system.domain.PermissionItem;

public interface PermissionItemManager {
public List<PermissionItem> queryPermissionItemIdList(final Collection<Long> ids);
	public Map<Long,PermissionItem> queryPermissionItemMapIdList(final Collection<Long> ids);
	
	
	public List<PermissionItem> queryPermissionItemList(PermissionItem permissionItem);
	
	public int savePermissionItem(PermissionItem permissionItem);
	
	
	public int updatePermissionItem(PermissionItem permissionItem);
	
	
	public int delPermissionItem(Long id);
	
	public int delPermissionItemByIds(List<String> ids);
}
