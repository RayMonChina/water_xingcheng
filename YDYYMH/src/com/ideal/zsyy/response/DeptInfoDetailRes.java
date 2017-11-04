package com.ideal.zsyy.response;

import com.ideal.zsyy.entity.DeptInfoDetail;
import com.ideal2.base.gson.CommonRes;

public class DeptInfoDetailRes extends CommonRes {
	DeptInfoDetail deptInfos;

	public DeptInfoDetail getDeptInfos() {
		return deptInfos;
	}

	public void setDeptInfos(DeptInfoDetail deptInfos) {
		this.deptInfos = deptInfos;
	}
}
