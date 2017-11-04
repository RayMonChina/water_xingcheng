package com.ideal2.base;

public interface IBaseDao {
	
	public boolean createConn(String url, String xmlname);

	public boolean hasConn();
	
	public boolean conn(String requestXml);
	
	public void destroy();

}
