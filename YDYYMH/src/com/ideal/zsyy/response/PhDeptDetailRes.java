package com.ideal.zsyy.response;

import com.ideal.zsyy.entity.PhDeptInfo;
import com.ideal2.base.gson.CommonRes;

public class PhDeptDetailRes extends CommonRes {

	PhDeptInfo phDeptInfo;

	public PhDeptInfo getPhDeptInfo() {
		return phDeptInfo;
	}

	public void setPhDeptInfo(PhDeptInfo phDeptInfo) {
		this.phDeptInfo = phDeptInfo;
	}
}
