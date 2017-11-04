package com.ideal.zsyy.response;

import java.util.List;

import com.ideal.zsyy.entity.PhJkxjInfo;
import com.ideal2.base.gson.CommonRes;

public class PhJkxjRes extends CommonRes {

	private List<PhJkxjInfo> phJkxjInfos;

	public List<PhJkxjInfo> getPhJkxjInfos() {
		return phJkxjInfos;
	}

	public void setPhJkxjInfos(List<PhJkxjInfo> phJkxjInfos) {
		this.phJkxjInfos = phJkxjInfos;
	}
	
}
