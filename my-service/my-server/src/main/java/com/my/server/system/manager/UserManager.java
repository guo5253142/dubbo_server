package com.my.server.system.manager;

import java.util.List;

import com.my.common.common.DataPage;
import com.my.common.system.domain.Permission;
import com.my.common.system.domain.PermissionItem;
import com.my.common.system.domain.User;
import com.my.common.system.domain.vo.UserVo;



public interface UserManager {
	
	public List<User> queryAllUserList();
	
	public User getUserByAccount(String account);
	
	public List<Permission> getPermissionList(User user);
	
	public List<PermissionItem> getPermissionItemList(User user);
	
	public void updateUser(User user);
	
	public DataPage<User> queryUser(DataPage<User>  page, UserVo user);
	
	public void saveUser(User user);
	
	public void deleteUser(User user);

	public User getUser(User user);
	
	public void saveEditUser(User user);
}
