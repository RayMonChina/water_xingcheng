package com.ideal.zsyy.request;

import com.ideal2.base.gson.CommonReq;

/**
 * 大楼科室信息2
 * 
 * @author KOBE
 * 
 */
public class FloorDeptInfoReq extends CommonReq {

	private String buildNo;
	private String hosId;

	public String getBuildNo() {
		return buildNo;
	}

	public void setBuildNo(String buildNo) {
		this.buildNo = buildNo;
	}

	public String getHosId() {
		return hosId;
	}

	public void setHosId(String hosId) {
		this.hosId = hosId;
	}
}
