package com.ideal.zsyy.request;

import com.ideal2.base.gson.CommonReq;

public class MzChargeReq extends CommonReq{

	public MzChargeReq()
	{
		setOperType("119");
	}
	private String pid;
	private String idCard;
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	
}
