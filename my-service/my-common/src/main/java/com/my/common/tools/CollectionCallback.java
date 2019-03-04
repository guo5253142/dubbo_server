package com.my.common.tools;

import java.util.Collection;

public interface CollectionCallback<E extends Object> {
	public void call(Collection<E> collection);
}
