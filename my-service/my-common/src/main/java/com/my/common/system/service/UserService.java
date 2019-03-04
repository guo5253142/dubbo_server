package com.my.common.system.service;

import java.util.List;
import java.util.Map;

import com.my.common.common.DataPage;
import com.my.common.common.PageResult;
import com.my.common.system.domain.Permission;
import com.my.common.system.domain.User;
import com.my.common.system.domain.vo.AdminMenuVo;
import com.my.common.system.domain.vo.UserSessionVo;
import com.my.common.system.domain.vo.UserVo;

public interface UserService {
	
	/**
	 * 查询所有用户
	 * @return
	 */
	public List<User> queryAllUserList();
	
	/**
	 * 查询用户信息
	 * @param account 账号
	 * @return
	 */
	public User getUserByAccount(String account);
	
	/**
	 * 查询权限
	 * @param user
	 * @return
	 */
	public UserSessionVo getPermission(User user);
	
	/**
	 * 修改user数据
	 * @param user
	 */
	public void updateUser(User user);
	
	/**
	 * 修改页面更新user数据
	 * @param user
	 */
	public void saveEditUser(User user);
	
	/**
	 * 根据权限获取菜单
	 * @param permissionMap
	 * @return
	 */
	public Map<String,List<AdminMenuVo>> getMenu(Map<String, Permission> permissionMap);
	
	/**
	 * 查询用户列表
	 * @param page
	 * @param condition
	 * @return
	 */
	public PageResult<User> queryUser(DataPage<User> page, UserVo condition);
	
	/**
	 * 新增用户
	 * @param user
	 */
	public void saveUser(User user);
	
	/**
	 * 删除用户
	 * @param user
	 */
	public void deleteUser(User user);
	
	/**
	 * 根据条件查询user
	 * @param user
	 * @return
	 */
	public User getUser(User user);
	
}
