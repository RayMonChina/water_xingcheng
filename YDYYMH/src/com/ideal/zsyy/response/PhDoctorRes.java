package com.ideal.zsyy.response;

import java.util.List;

import com.ideal.zsyy.entity.PhDoctorInfo;
import com.ideal2.base.gson.CommonRes;

public class PhDoctorRes extends CommonRes {

	private List<PhDoctorInfo> phDoctorInfos;

	public List<PhDoctorInfo> getPhDoctorInfos() {
		return phDoctorInfos;
	}

	public void setPhDoctorInfos(List<PhDoctorInfo> phDoctorInfos) {
		this.phDoctorInfos = phDoctorInfos;
	}

}
