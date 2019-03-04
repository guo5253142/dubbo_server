package com.my.common.system.domain.vo;

import java.io.Serializable;

public class AdminMenuVo  implements Serializable {

	/**
	 *  admin首页展示菜单栏
	 */
	private static final long serialVersionUID = 1L;

	private String href;
	
	private String rel;
	
	private String name;
	
	private Integer orderIndex;

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}

	
}
