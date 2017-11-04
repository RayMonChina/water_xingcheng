package com.ideal2.base;

import java.lang.reflect.Method;



public class AnnotationFactory {
	
	public void buildDomainObject(Class form){
		Method[] methods = form.getDeclaredMethods();
		for (Method method : methods) {
			/*
			 * 判断方法中是否有指定注解类型的注解
			 */
			boolean hasAnnotation = method.isAnnotationPresent(DomainObject.class);
			if (hasAnnotation) {
				/*
				 * 根据注解类型返回方法的指定类型注解
				 */
				DomainObject annotation = method.getAnnotation(DomainObject.class);
				
			}
		}
	}
	
	
}
