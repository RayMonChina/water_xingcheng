package com.ideal2.base;

import java.util.HashMap;
import java.util.Map;

public class Session {
	private static Session s;
	private Map session;

	private Session(){
		session = new HashMap();
	}
	
	public static Session getSession(){
		if(null==s){
			s = new Session();
		}
		return s;
	}
	
	public void put(Object key,Object value){
		session.put(key, value);
	}
	
	public Object get(Object key){
		return session.get(key);
	}
	
	public void destroy(){
		s = null;
		System.gc();
	}
}
