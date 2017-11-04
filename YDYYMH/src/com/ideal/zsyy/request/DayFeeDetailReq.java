package com.ideal.zsyy.request;

import com.ideal2.base.gson.CommonReq;

public class DayFeeDetailReq extends CommonReq {
	public DayFeeDetailReq()
	{
		setOperType("109");
	}
	private String pat_no;//住院号
	private String fee_day;//费用发生时间
	public String getPat_no() {
		return pat_no;
	}
	public void setPat_no(String pat_no) {
		this.pat_no = pat_no;
	}
	public String getFee_day() {
		return fee_day;
	}
	public void setFee_day(String fee_day) {
		this.fee_day = fee_day;
	}
	
}
