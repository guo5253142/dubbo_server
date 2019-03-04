package com.my.common.system.domain;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class BasePO implements Serializable {
	private static final long serialVersionUID = -7831572507902017074L;

	 public String toString() {
	        return ReflectionToStringBuilder.toString(this);
	    }
}
