package com.my.common.system.domain.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息
 * 
 */
public class UserVo implements Serializable {
    private static final long serialVersionUID = -2841261086490397344L;

    private Long id;
    private String account;
    private String name;
    private Date lastLoginTime;
    private Long roleId;
    
    private String status;

    /**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

}
