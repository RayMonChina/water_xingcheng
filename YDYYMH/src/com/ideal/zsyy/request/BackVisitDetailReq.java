package com.ideal.zsyy.request;

import com.ideal2.base.gson.CommonReq;

public class BackVisitDetailReq extends CommonReq {

	public BackVisitDetailReq()
	{
		setOperType("115");
	}
	private String id;//id
	private String status;//状态 0、未回复 1、已回复
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
