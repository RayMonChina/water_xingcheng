package com.ideal.zsyy.response;

import java.util.List;

import com.ideal.zsyy.entity.TopVisitResInfo;
import com.ideal2.base.gson.CommonRes;

public class SelectTopVisistRes extends CommonRes {

	private List<TopVisitResInfo> visitInfo;

	public List<TopVisitResInfo> getVisitInfo() {
		return visitInfo;
	}

	public void setVisitInfo(List<TopVisitResInfo> visitInfo) {
		this.visitInfo = visitInfo;
	}
	
	
}
