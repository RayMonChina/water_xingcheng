package com.ideal.zsyy.response;

import java.util.List;

import com.ideal.zsyy.entity.HosChargeInfo;
import com.ideal2.base.gson.CommonRes;

public class HosChargeRes extends CommonRes {

	private List<HosChargeInfo>hosChargeInfos;

	public List<HosChargeInfo> getHosChargeInfos() {
		return hosChargeInfos;
	}

	public void setHosChargeInfos(List<HosChargeInfo> hosChargeInfos) {
		this.hosChargeInfos = hosChargeInfos;
	}
	
}
