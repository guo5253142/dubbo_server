package com.my.common.common.constant;

/**
 * 是否字典类型
 * 
 */
public class YesOrNoType extends BaseType {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8414351607896156035L;
	protected YesOrNoType(Integer index, String description) {
		super(index, description);
	}
	
	/**1, "是"**/
	public static YesOrNoType yes = new YesOrNoType(1, "是");
	/**0, "否"**/
	public static YesOrNoType no = new YesOrNoType(0, "否");
}
