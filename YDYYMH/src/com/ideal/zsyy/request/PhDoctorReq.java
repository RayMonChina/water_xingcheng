package com.ideal.zsyy.request;

import com.ideal2.base.gson.CommonReq;

public class PhDoctorReq extends CommonReq {

	private String hosID;//医院ID
	private String docName;//医生姓名
	private String docId;
	private String dept_Id;
	private String pageSize="";//如果为空则不分页
	private String pageIndex="";//从0开始
	private String isPro;//为空查询所有的，0、非专家 1、专家
	
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public String getHosID() {
		return hosID;
	}
	public void setHosID(String hosID) {
		this.hosID = hosID;
	}
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	public String getIsPro() {
		return isPro;
	}
	public void setIsPro(String isPro) {
		this.isPro = isPro;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}
	public String getDept_Id() {
		return dept_Id;
	}
	public void setDept_Id(String dept_Id) {
		this.dept_Id = dept_Id;
	}
}
