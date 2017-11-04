package com.ideal.zsyy.request;

import com.ideal2.base.gson.CommonReq;

public class YuYueTipReq extends CommonReq {

	public YuYueTipReq()
	{
		setOperType("103");
	}
	private String id;//预约编号
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
