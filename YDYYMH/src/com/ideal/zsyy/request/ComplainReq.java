package com.ideal.zsyy.request;

import com.ideal2.base.gson.CommonReq;

public class ComplainReq extends CommonReq {

	public ComplainReq()
	{
		setOperType("116");
	}
	private String pat_no;//住院号
	private String type;//1、投诉 2、咨询 、3 建议
	public String getPat_no() {
		return pat_no;
	}
	public void setPat_no(String pat_no) {
		this.pat_no = pat_no;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
