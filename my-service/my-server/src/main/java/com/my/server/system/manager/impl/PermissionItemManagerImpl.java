package com.my.server.system.manager.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.my.common.system.domain.PermissionItem;
import com.my.server.common.dao.GenericMybatisDao;
import com.my.server.system.manager.PermissionItemManager;
@Component
public class PermissionItemManagerImpl implements PermissionItemManager{
	@Autowired
	@Qualifier("adminDbDao")
	private GenericMybatisDao adminDbDao;

	public List<PermissionItem> queryPermissionItemIdList(final Collection<Long> ids){
		
		final List<PermissionItem> resultList = new ArrayList<PermissionItem>();
		if (ids == null || ids.isEmpty()) {
			return resultList;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("idList", ids);
		List<PermissionItem> permissionList = adminDbDao.queryList("PermissionItemMapper.queryPermissionItemByIdSet", paramMap);
		resultList.addAll(permissionList);
		return resultList;
		
	}
	public Map<Long,PermissionItem> queryPermissionItemMapIdList(final Collection<Long> ids){
		
		final Map<Long,PermissionItem> resultMap = new HashMap<Long,PermissionItem>();
		if (ids == null || ids.isEmpty()) {
			return resultMap;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("idList", ids);
		List<PermissionItem> permissionList = adminDbDao.queryList("PermissionItemMapper.queryPermissionItemByIdSet", paramMap);
		for(PermissionItem per:permissionList){
			resultMap.put(per.getId(), per);
		}
		return resultMap;
		
	}
	
	
	public List<PermissionItem> queryPermissionItemList(PermissionItem permissionItem) {
		Map<String, Object> paramMap = conditionMap(permissionItem);
		return adminDbDao.queryList("PermissionItemMapper.queryPermissionItem", paramMap);
	}
	
	public int savePermissionItem(PermissionItem permissionItem) {
		if (permissionItem.getPermissionId() == null) {
			return 0;
		}
		return adminDbDao.insertAndSetupId("PermissionItemMapper.savePermissionItem", permissionItem);
	}
	
	
	public int updatePermissionItem(PermissionItem permissionItem) {
		return adminDbDao.updateByObj("PermissionItemMapper.updatePermissionItem", permissionItem);
	}
	
	private Map<String, Object> conditionMap(PermissionItem permissionItem) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (permissionItem != null) {
			if (StringUtils.isNotBlank(permissionItem.getName())) {
				paramMap.put("name", permissionItem.getName());
			}
			if (permissionItem.getId() != null) {
				paramMap.put("id", permissionItem.getId());
			}
			if (StringUtils.isNotBlank(permissionItem.getMethodValue1())) {
				paramMap.put("methodValue1", permissionItem.getMethodValue1());
			}
			if (StringUtils.isNotBlank(permissionItem.getMethodValue2())) {
				paramMap.put("methodValue2", permissionItem.getMethodValue2());
			}
			if (permissionItem.getPermissionId() != null) {
				paramMap.put("permissionId", permissionItem.getPermissionId());
			}
		}
		return paramMap;
	}
	
	public int delPermissionItem(Long id) {
		if (id == null) {
			return 0;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		return adminDbDao.update("PermissionItemMapper.deletePermissionItem", paramMap);
	}
	@Override
	public int delPermissionItemByIds(List<String> ids) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ids", ids);
		return adminDbDao.update("PermissionItemMapper.delPermissionItemByIds", paramMap);
	}
}
