package com.ideal.zsyy.request;

import com.ideal2.base.gson.CommonReq;

public class PhDutyInfoReq extends CommonReq {

	private String hospId;  //医疗机构id
	private String docId;   //医生id
	public String getHospId() {
		return hospId;
	}
	public void setHospId(String hospId) {
		this.hospId = hospId;
	}
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	
}
