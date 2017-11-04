package com.ideal.zsyy.response;

import java.util.List;

import com.ideal.zsyy.entity.DeptDutyInfo;
import com.ideal2.base.gson.CommonRes;

public class DeptDutyInfoRes extends CommonRes {

	private List<DeptDutyInfo> deptdutyinfos;

	public List<DeptDutyInfo> getDeptdutyinfos() {
		return deptdutyinfos;
	}

	public void setDeptdutyinfos(List<DeptDutyInfo> deptdutyinfos) {
		this.deptdutyinfos = deptdutyinfos;
	}
	
}
