package com.my.common.system.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PermissionItem extends BasePO {

    private static final long serialVersionUID = 4767307740910429540L;

    private Long id;
    
    private Long permissionId;
    @JsonIgnore
    private Permission permission;

    private String name;

    private String methodValue1;

    private String methodValue2;
    
    // 用于修改权限时候，不是数据库字段
    private boolean isChecked;

    
    public PermissionItem(Long id) {
		this.id = id;
	}
    
    public PermissionItem() {
	}
    
	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMethodValue1() {
        return methodValue1;
    }

    public void setMethodValue1(String methodValue1) {
        this.methodValue1 = methodValue1;
    }

    public String getMethodValue2() {
        return methodValue2;
    }

    public void setMethodValue2(String methodValue2) {
        this.methodValue2 = methodValue2;
    }

	public Long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}

	public boolean getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

}
