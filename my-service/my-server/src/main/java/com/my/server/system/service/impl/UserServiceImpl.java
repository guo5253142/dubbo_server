package com.my.server.system.service.impl;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.my.common.common.DataPage;
import com.my.common.common.PageResult;
import com.my.common.common.constant.YesOrNoType;
import com.my.common.system.domain.Module;
import com.my.common.system.domain.Permission;
import com.my.common.system.domain.PermissionItem;
import com.my.common.system.domain.User;
import com.my.common.system.domain.vo.AdminMenuVo;
import com.my.common.system.domain.vo.UserSessionVo;
import com.my.common.system.domain.vo.UserVo;
import com.my.common.system.service.UserService;
import com.my.common.tools.StringUtil;
import com.my.server.system.manager.ModuleManager;
import com.my.server.system.manager.UserManager;
@Service("userServiceImpl")
public class UserServiceImpl implements UserService {
	
    private transient final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public UserManager userManager;
	
	@Autowired
	private ModuleManager moduleManager;
	

	@Override
	public List<User> queryAllUserList() {
		return userManager.queryAllUserList();
	}

	@Override
	public User getUserByAccount(String account) {
		return userManager.getUserByAccount(account);
	}

	@Override
	public UserSessionVo getPermission(User user) {
		UserSessionVo vo = new UserSessionVo();
		Map<String, Permission> permissionMap = new HashMap<String, Permission>();
	    Map<String, PermissionItem> itemMap = new HashMap<String, PermissionItem>();
	    Map<Long,Permission> pMap = new  HashMap<Long,Permission>();
	    vo.setPermissionMap(permissionMap);
	    vo.setItemMap(itemMap);
	    List<Permission> permissionList=userManager.getPermissionList(user);
	    for(Permission permission:permissionList){
	    	if(StringUtils.isNotEmpty(permission.getParamName())){
	    		String url = StringUtils.trimToEmpty(permission.getActionName())+StringUtils.trimToEmpty(permission.getMethodName()) + StringUtils.trimToEmpty(permission.getParamName())
                + StringUtils.trimToEmpty(permission.getParamValue());
	    		permissionMap.put(url.toLowerCase(), permission);
	    	}else{
	    		String url = StringUtils.trimToEmpty(permission.getActionName()).toLowerCase()+StringUtils.trimToEmpty(permission.getMethodName()).toLowerCase();
                permissionMap.put(url, permission);
	    	}
	    	//由于页面加载和数据加载是不同的方法，所以将加载数据方法自动配入子权限
	    	if(!StringUtil.isBlank(permission.getLoadDataMethodName())){
	    		String url = StringUtils.trimToEmpty(permission.getActionName()).toLowerCase()+StringUtils.trimToEmpty(permission.getLoadDataMethodName()).toLowerCase();
	    		PermissionItem item=new PermissionItem();
	    		item.setPermission(permission);
	    		itemMap.put(url, item);
	    	}
	    	pMap.put(permission.getId(), permission);
	    }
	    
	    List<PermissionItem> itemList=userManager.getPermissionItemList(user);
	    if (itemList != null && !itemList.isEmpty()) {
	           for(PermissionItem item :itemList){
	        	   item.setPermission(pMap.get(item.getPermissionId()));
	        	   if (StringUtils.isNotEmpty(item.getMethodValue1())) {
	                   itemMap.put(item.getPermission().getActionName().toLowerCase()
	                           + item.getMethodValue1().toLowerCase(), item);
	               }
	               if (StringUtils.isNotEmpty(item.getMethodValue2())) {
	                   itemMap.put(item.getPermission().getActionName().toLowerCase()
	                           + item.getMethodValue2().toLowerCase(), item);
	               }
	           }            
	        }		
		return vo;
	}

	@Override
	@Transactional
	public void updateUser(User user) {
		userManager.updateUser(user);
	}

	@Override
	public Map<String,List<AdminMenuVo>> getMenu(Map<String, Permission> permissionMap){
		Map<String, List<AdminMenuVo>> result = new LinkedHashMap<String, List<AdminMenuVo>>();
		Map<Long, List<AdminMenuVo>> map = new TreeMap<Long, List<AdminMenuVo>>();
		Module module = null;
		AdminMenuVo menuVo = null;
		Collection<Long> ids = new LinkedList<Long>();
        for(Entry<String, Permission> entry :permissionMap.entrySet()){
        	if (ids.contains(entry.getValue().getModuleId())) {
        		continue;
        	}
        	ids.add(entry.getValue().getModuleId());
        }
	    Map<Long,Module> moduleMap = moduleManager.queryModuleMapByIdSet(ids);
	    if(moduleMap!=null && !moduleMap.isEmpty()){
	    	 for(Entry<String, Permission> entry :permissionMap.entrySet()){
	    		 if(YesOrNoType.no.getIndex()==entry.getValue().getIsMenu()){
	            	  continue;
	              }
	    		  if(StringUtils.isBlank(entry.getValue().getMenuUrl())){
	            	  continue;
	              }
		    	  module = moduleMap.get(entry.getValue().getModuleId());
	              if(!map.containsKey(entry.getValue().getModuleId())){
  					map.put(module.getId(), new ArrayList<AdminMenuVo>());
  					moduleMap.put(module.getId(), module);
	              } 
	             
	              menuVo = new AdminMenuVo();
	  			  menuVo.setHref(entry.getValue().getMenuUrl());
	  			  menuVo.setName(entry.getValue().getName());
	  			  String rel = entry.getValue().getActionName().toLowerCase();
	  			  if (StringUtils.isNotBlank(entry.getValue().getMethodName())) {
	  				  rel += entry.getValue().getMethodName().toLowerCase();
	  			  }
	  			  menuVo.setRel(rel);
	  			  menuVo.setOrderIndex(entry.getValue().getOrderIndex());
	  			  map.get(entry.getValue().getModuleId()).add(menuVo);
		    }
	    	 
	    	// 排序的module
	    	Map<Integer, Module> treeModule = new TreeMap<Integer, Module>();
	    	Set<Long> keySet = map.keySet();
	    	for (Long key : keySet) {
	    		module = moduleMap.get(key);
	    		treeModule.put(module.getOrderIndex(), module);
	    		// 排序模块中的菜单
	    		sortAdminMenuVo(map.get(key));
	    	}
	    	Set<Integer> treeSet = treeModule.keySet();
	    	for (Integer tKey : treeSet) {
	    		module = treeModule.get(tKey);
	    		result.put(module.getName(), map.get(module.getId()));
	    	}
	    	
	    }else{
	    	logger.info("权限模块为空！");
	    }
	   
		return result;
	}
	
	// 排序
		private void sortAdminMenuVo(List<AdminMenuVo> resultList) {
			if (resultList != null && resultList.size() > 1) {
				for (int index1 = resultList.size() - 1; index1 > 0; index1--) {
					for (int index2 = 0; index2 < index1; index2++) {
						if (resultList.get(index2).getOrderIndex() > resultList.get(index2 + 1).getOrderIndex()) {
							resultList.add(index2, resultList.get(index2 + 1));
							resultList.remove(index2 + 2);
						}
					}
				}
			}
		}

		@Override
		public PageResult<User> queryUser(DataPage<User> page, UserVo condition) {
			page = userManager.queryUser(page, condition);
			PageResult<User> result = new PageResult<User>();
			result.setPage(page);
			return result;
		}

		@Override
		public void saveUser(User user) {
			userManager.saveUser(user);
		}

		@Override
		@Transactional
		public void deleteUser(User user) {
			userManager.deleteUser(user);
		}

		@Override
		public User getUser(User user) {
			return userManager.getUser(user);
		}

		@Override
		@Transactional
		public void saveEditUser(User user) {
			userManager.saveEditUser(user);
		}


}
