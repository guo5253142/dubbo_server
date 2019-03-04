package com.my.common.system.domain.vo;

import java.io.Serializable;
import java.util.Map;

import com.my.common.system.domain.Permission;
import com.my.common.system.domain.PermissionItem;
import com.my.common.system.domain.User;


/**
 * 登录用户VO，包括登录用户权限值，登录后用户信息
 * @author guopeng
 *
 */
@SuppressWarnings("serial")
public class UserSessionVo implements Serializable {
    /* 上线登录时间 */
    private String lastLoginTime;
    
    /* 用户信息 */
    private User user;

    /* 一级权限列表 */
    private Map<String, Permission> permissionMap;
    
    /* 二级权限列表 */
    private Map<String, PermissionItem> itemMap;

 
    public Map<String, Permission> getPermissionMap() {
        return permissionMap;
    }

    public void setPermissionMap(Map<String, Permission> permissionMap) {
        this.permissionMap = permissionMap;
    }

    public Map<String, PermissionItem> getItemMap() {
        return itemMap;
    }

    public void setItemMap(Map<String, PermissionItem> itemMap) {
        this.itemMap = itemMap;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}
