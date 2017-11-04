package com.ideal.zsyy.request;

import com.ideal2.base.gson.CommonReq;

/**
 * 医院详细信息
 * 
 * @author KOBE
 * 
 */
public class HosInfoReq extends CommonReq {

	private String hosId;

	public String getHosId() {
		return hosId;
	}

	public void setHospId(String hospId) {
		this.hosId = hospId;
	}

}
