package com.ideal.zsyy.request;

import com.ideal2.base.gson.CommonReq; 

public class PhDeptInfoReq extends CommonReq {

	private String hosID;
	private String depName;
	private String pageSize="";
	private String pageIndex="";//从0开始
	private String isFeatureDept;  //1是特色科室
	public String getIsFeatureDept() {
		return isFeatureDept;
	}

	public void setIsFeatureDept(String isFeatureDept) {
		this.isFeatureDept = isFeatureDept;
	}
	public String getHosID() {
		return hosID;
	}

	public void setHosID(String hosID) {
		this.hosID = hosID;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
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
}
