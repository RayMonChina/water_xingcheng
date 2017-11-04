package com.ideal.zsyy.response;

import com.ideal2.base.gson.CommonRes;

public class ServerDateTimeRes extends CommonRes {
	
	private String ServerTime;
	
	public String getServerTime()
	{
		return ServerTime;
	}
	
	public void setServerTime(String serverTime)
	{
		this.ServerTime=serverTime;
	}

}
