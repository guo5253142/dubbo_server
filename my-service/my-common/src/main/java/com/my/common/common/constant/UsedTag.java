package com.my.common.common.constant;

/**
 * 记录启用标志
 * 
 */
public class UsedTag extends BaseType {

	private static final long serialVersionUID = 508928308966257111L;
	protected UsedTag(Integer index, String description) {
		super(index, description);
	}
	/**0, "禁用"**/
	public static UsedTag forbidden = new UsedTag(0, "禁用");
	/**1, "启用"**/
	public static UsedTag  enabled = new UsedTag(1, "启用");

}
