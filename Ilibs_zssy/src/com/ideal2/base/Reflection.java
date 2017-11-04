package com.ideal2.base;

import java.lang.reflect.Method;

public class Reflection {
	
	  public static Object getter(Object obj, String att) {
		  	att = att.substring(0,1).toUpperCase()+att.substring(1);
			try {
				Method method = obj.getClass().getMethod("get" + att);
				return method.invoke(obj);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
}
