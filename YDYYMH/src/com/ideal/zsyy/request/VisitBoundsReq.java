package com.ideal.zsyy.request;

import com.ideal2.base.gson.CommonReq;

public class VisitBoundsReq extends CommonReq {

	private String pat_name;
	public String getPat_name() {
		return pat_name;
	}
	public void setPat_name(String patName) {
		pat_name = patName;
	}
}
