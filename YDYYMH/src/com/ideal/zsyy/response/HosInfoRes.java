package com.ideal.zsyy.response;

import java.util.List;

import com.ideal.zsyy.entity.PhHospitalInfo;
import com.ideal2.base.gson.CommonRes;

/**
 * 医院详细信息
 * @author KOBE
 *
 */
public class HosInfoRes extends CommonRes {
	List<PhHospitalInfo> phHospitalInfo;

	public List<PhHospitalInfo> getPhHospitalInfo() {
		return phHospitalInfo;
	}

	public void setPhHospitalInfo(List<PhHospitalInfo> phHospitalInfo) {
		this.phHospitalInfo = phHospitalInfo;
	}
}
