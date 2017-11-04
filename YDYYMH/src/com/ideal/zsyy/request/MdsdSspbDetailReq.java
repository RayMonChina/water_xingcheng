package com.ideal.zsyy.request;

import com.ideal2.base.gson.CommonReq;

public class MdsdSspbDetailReq extends CommonReq {

	public MdsdSspbDetailReq()
	{
		setOperType("104");
	}
	private String pbid;
	public String getPbid() {
		return pbid;
	}
	public void setPbid(String pbid) {
		this.pbid = pbid;
	}
	
}
