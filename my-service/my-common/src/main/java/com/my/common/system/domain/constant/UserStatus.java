package com.my.common.system.domain.constant;

import com.my.common.common.constant.BaseType;

/**
 * 用户状态
 * 
 */
public class UserStatus extends BaseType {

	private static final long serialVersionUID = 508928308966257111L;
	protected UserStatus(Integer index, String description) {
		super(index, description);
	}
	/**0, "禁用"**/
	public static UserStatus forbidden = new UserStatus(0, "禁用");
	/**1, "启用"**/
	public static UserStatus  enabled = new UserStatus(1, "启用");

}
