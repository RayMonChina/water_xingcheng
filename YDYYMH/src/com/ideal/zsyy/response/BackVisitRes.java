package com.ideal.zsyy.response;

import java.util.List;

import com.ideal.zsyy.entity.BackVisitInfo;
import com.ideal2.base.gson.CommonRes;

public class BackVisitRes extends CommonRes {

	private List<BackVisitInfo> backVisitInfos;

	public List<BackVisitInfo> getBackVisitInfos() {
		return backVisitInfos;
	}

	public void setBackVisitInfos(List<BackVisitInfo> backVisitInfos) {
		this.backVisitInfos = backVisitInfos;
	}
	
}
