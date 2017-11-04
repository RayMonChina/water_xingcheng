package com.ideal.zsyy.request;

import com.ideal2.base.gson.CommonReq;

public class HosChargeReq extends CommonReq {

	public HosChargeReq()
	{
		setOperType("106");
	}
	private String pat_no;//住院号？
	private String status;
	public String getPat_no() {
		return pat_no;
	}
	public void setPat_no(String pat_no) {
		this.pat_no = pat_no;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
