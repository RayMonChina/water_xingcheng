package com.ideal.zsyy.request;

import com.ideal2.base.gson.CommonReq;

public class HospitalChargeReq extends CommonReq {

	public HospitalChargeReq()
	{
		setOperType("120");
	}
	private String reqDate;
	private String patNo;
	public String getReqDate() {
		return reqDate;
	}
	public void setReqDate(String reqDate) {
		this.reqDate = reqDate;
	}
	public String getPatNo() {
		return patNo;
	}
	public void setPatNo(String patNo) {
		this.patNo = patNo;
	}
	
}
