package com.ideal.zsyy.request;

import com.ideal2.base.gson.CommonReq;

public class BackVisitReq extends CommonReq {

	public BackVisitReq()
	{
		setOperType("114");
	}
	private String idCard;//身份证号
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	
	
}
