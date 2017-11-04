package com.ideal.zsyy.response;

import com.ideal.zsyy.entity.BackVisitInfo;
import com.ideal2.base.gson.CommonRes;

public class BackVisitDetailRes extends CommonRes {

	private BackVisitInfo bVisitInfo;//回访信息

	public BackVisitInfo getbVisitInfo() {
		return bVisitInfo;
	}

	public void setbVisitInfo(BackVisitInfo bVisitInfo) {
		this.bVisitInfo = bVisitInfo;
	}
	
}
