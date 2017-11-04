package com.ideal.zsyy.response;

import java.util.List;

import com.ideal.zsyy.entity.PhDoctorInfo;
import com.ideal2.base.gson.CommonRes;

public class DocDutyInfoRes extends CommonRes {

	private List<PhDoctorInfo> docdutyinfos;

	public List<PhDoctorInfo> getDocdutyinfos() {
		return docdutyinfos;
	}

	public void setDocdutyinfos(List<PhDoctorInfo> docdutyinfos) {
		this.docdutyinfos = docdutyinfos;
	}
	
}
