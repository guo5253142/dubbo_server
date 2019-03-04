package com.my.common.tools;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * map转换工具
 * 
 * @project sinafenqi-common
 * @author duannp
 * @date 2016年7月19日 www.sinafenqi.com
 */
public class MapUtil {

	private  final static  Logger logger = LoggerFactory.getLogger(MapUtil.class);
	/**
	 * 将对象转化成MAP格式 如果对象中 属性类型为String 值为""则去掉该属性
	 *
	 * @param object
	 * @return
	 */
	public static Map<String, Object> object2MapSpecail(Object object) {
		Map<String, Object> returnMap = new HashMap<String, Object>();

		if (object == null) {
			return returnMap;
		}

		BeanInfo beanInfo = null;
		try {
			beanInfo = Introspector.getBeanInfo(object.getClass());
		} catch (IntrospectionException e1) {
			return returnMap;
		}

		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			try {
				PropertyDescriptor descriptor = propertyDescriptors[i];
				String propertyName = descriptor.getName();
				if (!propertyName.equals("class")) {
					Method readMethod = descriptor.getReadMethod();
					Object result = readMethod.invoke(object, new Object[0]);
					if (result != null && !result.equals("")) {
						returnMap.put(propertyName, result);
					}
				}
			} catch (NullPointerException e) {
				logger.error("",e);
				continue;
			} catch (Exception e) {
				logger.error("",e);
				continue;
			}
		}

		return returnMap;
	}

	/**
	 * 非空判断
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Map map) {
		return (null == map) || (0 == map.size());
	}

	@SuppressWarnings("rawtypes")
	public static boolean isNotEmpty(Map map) {
		return !isEmpty(map);
	}
}
