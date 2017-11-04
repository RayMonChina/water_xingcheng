package com.ideal.zsyy.request;

import com.ideal2.base.gson.CommonReq;

public class PhJkxjReq extends CommonReq {

//	private String hosp_id;
	private String type;
	private String pageSize="";//如果为空则不分页
	private String pageIndex="";//从0开始
//	public String getHosp_id() {
//		return hosp_id;
//	}
//	public void setHosp_id(String hospId) {
//		hosp_id = hospId;
//	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
