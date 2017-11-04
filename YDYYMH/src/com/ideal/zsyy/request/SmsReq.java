package com.ideal.zsyy.request;

import com.ideal2.base.gson.CommonReq;

public class SmsReq extends CommonReq {

	private String dest_number;
	private String content;
	public String getDest_number() {
		return dest_number;
	}
	public void setDest_number(String destNumber) {
		dest_number = destNumber;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
