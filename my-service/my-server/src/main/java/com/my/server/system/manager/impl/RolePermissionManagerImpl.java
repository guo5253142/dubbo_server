package com.my.server.system.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.my.common.system.domain.RolePermission;
import com.my.common.tools.MapUtil;
import com.my.server.common.dao.GenericMybatisDao;
import com.my.server.system.manager.RolePermissionManager;
@Component
public class RolePermissionManagerImpl implements  RolePermissionManager{
	@Autowired
	@Qualifier("adminDbDao")
	private GenericMybatisDao adminDbDao;

	public List<RolePermission> queryRolePermissionListByRoleId(Long id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("roleId", id);
		return adminDbDao.queryList("RolePermissionMapper.queryRolePermission", paramMap);
	}
	
	public List<RolePermission> queryRolePermissionList(RolePermission rolePermission) {
		Map<String, Object> paramMap = MapUtil.object2MapSpecail(rolePermission);
		return adminDbDao.queryList("RolePermissionMapper.queryRolePermission", paramMap);
	}
	
	public List<Long> queryDistinctPermissionIdByRoleId(Long roleId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("roleId", roleId);
		return adminDbDao.queryList("RolePermissionMapper.queryDistinctPermissionId", paramMap);
	}
	
	public List<Long> queryDistinctPermissionItemIdByRoleId(Long id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("roleId", id);
		return adminDbDao.queryList("RolePermissionMapper.queryDistinctPermissionItemId", paramMap);
	}
	
	public void saveRolePermission(RolePermission rolePermission) {
		adminDbDao.insertAndSetupId("RolePermissionMapper.saveRolePermission", rolePermission);
	}
	
	public int deleteRolePermission(RolePermission rolePermission) {
		Map<String, Object> paramMap = MapUtil.object2MapSpecail(rolePermission);
		return adminDbDao.update("RolePermissionMapper.deleteRolePermission", paramMap);
	}

}
