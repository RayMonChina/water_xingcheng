package com.ideal.zsyy.request;

import com.ideal2.base.gson.CommonReq;

public class OperationRecureTipReq extends CommonReq {

	public OperationRecureTipReq()
	{
		setOperType("112");
	}
	private String tipId;//提醒ID
	public String getTipId() {
		return tipId;
	}
	public void setTipId(String tipId) {
		this.tipId = tipId;
	}
	
}
