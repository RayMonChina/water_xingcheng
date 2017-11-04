package com.ideal.zsyy.request;

import com.ideal2.base.gson.CommonReq;

public class DayFeeReq extends CommonReq {

	public DayFeeReq()
	{
		setOperType("108");
	}
	private String pat_no;
	public String getPat_no() {
		return pat_no;
	}
	public void setPat_no(String pat_no) {
		this.pat_no = pat_no;
	}
	
}
