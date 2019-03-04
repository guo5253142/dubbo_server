package com.my.common.system.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.my.common.system.domain.constant.UserStatus;

public class User extends BasePO {

	private static final long serialVersionUID = 4767307740910429540L;

	private Long id;

	private String account;// 帐号

	@JsonIgnore
	private String password;// 密码

	private String name;// 姓名
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date lastLoginTime;//上一次登录时间
	
	private Integer status;//状态
	
	private String statusDesc;//状态 描述
	
	@JsonIgnore
	private Integer usedTag;//数据是否有效标志 默认为1
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createDate;//创建时间
	
	
	private Role role;
	
	//创建人
	private Long creator;
		
	//角色ID
	private Long roleId;
	
	public String getStatusDesc(){
		if(null!=status){
			UserStatus tempType=UserStatus.getDescByIndex(UserStatus.class, status);
			if(null!=tempType){
				return tempType.getDescription();
			}
		}
		return "";
	}
	
	
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getUsedTag() {
		return usedTag;
	}

	public void setUsedTag(Integer usedTag) {
		this.usedTag = usedTag;
	}

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
