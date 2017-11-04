package com.ideal.zsyy.response;

import com.ideal.zsyy.entity.DiseaseHistoryInfo;
import com.ideal2.base.gson.CommonRes;

public class DiseaseHistoryRes extends CommonRes {

	private DiseaseHistoryInfo disHisInfo;

	public DiseaseHistoryInfo getDisHisInfo() {
		return disHisInfo;
	}

	public void setDisHisInfo(DiseaseHistoryInfo disHisInfo) {
		this.disHisInfo = disHisInfo;
	}
	
}
