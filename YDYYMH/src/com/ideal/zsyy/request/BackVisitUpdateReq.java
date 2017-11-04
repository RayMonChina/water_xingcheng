package com.ideal.zsyy.request;

import com.ideal2.base.gson.CommonReq;

public class BackVisitUpdateReq extends CommonReq {

	public BackVisitUpdateReq()
	{
		setOperType("117");
	}
	private String id;
	private String recureCon;//恢复情况
	private String medicalQuaility;//医疗质量
	private String serveCon;//服务情况
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRecureCon() {
		return recureCon;
	}
	public void setRecureCon(String recureCon) {
		this.recureCon = recureCon;
	}
	public String getMedicalQuaility() {
		return medicalQuaility;
	}
	public void setMedicalQuaility(String medicalQuaility) {
		this.medicalQuaility = medicalQuaility;
	}
	public String getServeCon() {
		return serveCon;
	}
	public void setServeCon(String serveCon) {
		this.serveCon = serveCon;
	}
	
}
