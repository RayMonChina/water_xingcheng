package com.ideal.zsyy.request;

import com.ideal2.base.gson.CommonReq;

/**
 * 医院列表
 * 
 * @author KOBE
 * 
 */
public class AllHospitalInfoReq extends CommonReq {
	private String hosp_code;	//行政区域代码

	public String getHosp_code() {
		return hosp_code;
	}

	public void setHosp_code(String hospCode) {
		hosp_code = hospCode;
	}
}
