package com.ideal.zsyy.response;

import java.util.List;

import com.ideal.zsyy.entity.ComplainInfo;
import com.ideal2.base.gson.CommonRes;

public class ComplainRes extends CommonRes {

	private List<ComplainInfo>complainInfos;//投诉 咨询 建议信息

	public List<ComplainInfo> getComplainInfos() {
		return complainInfos;
	}

	public void setComplainInfos(List<ComplainInfo> complainInfos) {
		this.complainInfos = complainInfos;
	}
	
}
