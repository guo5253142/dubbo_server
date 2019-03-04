package com.my.common.system.domain;


public class RolePermission extends BasePO {

	private static final long serialVersionUID = 7097891803903170247L;

	private Long id;

	private Long roleId;

	private Long permissionId;

	private Long permissionItemId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}

	public Long getPermissionItemId() {
		return permissionItemId;
	}

	public void setPermissionItemId(Long permissionItemId) {
		this.permissionItemId = permissionItemId;
	}


}
