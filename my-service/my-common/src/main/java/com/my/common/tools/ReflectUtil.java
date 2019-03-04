package com.my.common.tools;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ReflectUtil {

	public static <T> T formatBean(Map<String, Object> map, Class<T> clazz) {

		if (map == null || map.isEmpty()) {
			return null;
		}
		Set<String> keySet = map.keySet();
		Iterator<String> iterator = keySet.iterator();
		Map<String, Object> newMap = new HashMap<String, Object>();
		while (iterator.hasNext()) {
			String key = iterator.next();
			key = key.replaceAll("_", "");
			newMap.put(key.toLowerCase(), map.get(key));
		}
		try {
			T t = clazz.newInstance();
			Field[] fields = clazz.getFields();
			for (Field field : fields) {
				field.setAccessible(true);
				field.set(t, newMap.get(field.getName().toLowerCase()));
			}
			return t;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
