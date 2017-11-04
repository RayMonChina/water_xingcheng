package com.ideal.zsyy.request;

import com.ideal2.base.gson.CommonReq;

/**
 * 楼层
 * 
 * @author KOBE
 * 
 */
public class BuildReq extends CommonReq {

	private String hosId;// 医院ID

	public String getHosId() {
		return hosId;
	}

	public void setHosId(String hosId) {
		this.hosId = hosId;
	}
}
