package com.ideal2.base;

import java.util.Map;

import com.ideal2.http.XmlNode;

public interface IDomainObject{
	
	public abstract Map<String, Object> getMap();
	
	public abstract void setMap(Map<String, Object> map);
	
	public abstract XmlNode requestXml();
	
}
