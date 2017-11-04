package com.ideal.zsyy.request;

import com.ideal.zsyy.entity.TopVisitResInfo;
import com.ideal2.base.gson.CommonReq;

public class TopVisitReq extends CommonReq {

	private TopVisitResInfo topvisitinfo;

	public TopVisitResInfo getTopvisitinfo() {
		return topvisitinfo;
	}

	public void setTopvisitinfo(TopVisitResInfo topvisitinfo) {
		this.topvisitinfo = topvisitinfo;
	}
	
}
