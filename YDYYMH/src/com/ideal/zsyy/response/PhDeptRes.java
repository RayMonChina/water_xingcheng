package com.ideal.zsyy.response;

import java.util.List;

import com.ideal.zsyy.entity.DeptInfo;
import com.ideal2.base.gson.CommonRes;

public class PhDeptRes extends CommonRes {
	List<DeptInfo> phDeptInfos;

	public List<DeptInfo> getPhDeptInfos() {
		return phDeptInfos;
	}

	public void setPhDeptInfos(List<DeptInfo> phDeptInfos) {
		this.phDeptInfos = phDeptInfos;
	}
}
