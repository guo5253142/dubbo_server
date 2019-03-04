package com.my.common.system.service;

import java.util.List;

import com.my.common.common.DataPage;
import com.my.common.common.PageResult;
import com.my.common.system.domain.Permission;

public interface PermissionService {

	public List<Permission> queryPermissionList(Permission permission);
	
	public Permission queryPermissionById(Long id);
	
	public PageResult<Permission> queryPermissionList(DataPage<Permission>  page, Permission permission);
	
	public List<Permission> queryPermissionByIdList(List<Long> ids);
	
	public int savePermission(Permission permission);
	
	public int updatePermission(Permission permission);
	
	public int updatePermissionSortIndex(Permission permission);
	
	public int deletePermission(Long id);
	
	
}
