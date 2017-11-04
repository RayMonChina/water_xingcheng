package com.ideal.zsyy.request;

import com.ideal2.base.gson.CommonReq;

public class HosFeeTipReq extends CommonReq {

	public HosFeeTipReq()
	{
		setOperType("110");
	}
	private String pat_no;//住院号
	private String in_date;//住院日期
	public String getPat_no() {
		return pat_no;
	}
	public void setPat_no(String pat_no) {
		this.pat_no = pat_no;
	}
	public String getIn_date() {
		return in_date;
	}
	public void setIn_date(String in_date) {
		this.in_date = in_date;
	}
	
}
