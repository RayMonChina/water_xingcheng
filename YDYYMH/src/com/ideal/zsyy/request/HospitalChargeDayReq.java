package com.ideal.zsyy.request;

import com.ideal2.base.gson.CommonReq;

public class HospitalChargeDayReq extends CommonReq {

	public HospitalChargeDayReq()
	{
		setOperType("121");
	}
	private String patNo;
	public String getPatNo() {
		return patNo;
	}
	public void setPatNo(String patNo) {
		this.patNo = patNo;
	}
	
}
