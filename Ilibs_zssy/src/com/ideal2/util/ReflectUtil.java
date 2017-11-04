package com.ideal2.util;

import java.lang.reflect.Method;
/**
 * 
 * @author xufeng
 *反射工厂类
 */
public class ReflectUtil {
	
	/** * @param obj * 操作的对象 * @param att * 操作的属性 * */
	public static Object getter(Object obj, String att) {
		try {
			att = att.substring(0,1).toUpperCase()+att.substring(1);
			Method method = obj.getClass().getMethod("get" + att);
			return method.invoke(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void setter(Object obj, String att, Object value,
			Class<?> type) {
		try {
			att = att.substring(0,1).toUpperCase()+att.substring(1);
			Method method = obj.getClass().getMethod("set" + att, type);
			method.invoke(obj, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
