package com.ideal.zsyy.response;

import java.util.List;

import com.ideal.zsyy.entity.PhDutyInfo;
import com.ideal2.base.gson.CommonRes;

public class PhDutyInfoRes extends CommonRes {

	private List<PhDutyInfo> phdutyInfos;

	public List<PhDutyInfo> getPhdutyInfos() {
		return phdutyInfos;
	}

	public void setPhdutyInfos(List<PhDutyInfo> phdutyInfos) {
		this.phdutyInfos = phdutyInfos;
	}
	
}
