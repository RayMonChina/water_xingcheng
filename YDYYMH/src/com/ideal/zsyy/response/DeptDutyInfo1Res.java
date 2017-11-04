package com.ideal.zsyy.response;

import java.util.List;

import com.ideal.zsyy.entity.DeptDutysInfo;
import com.ideal2.base.gson.CommonRes;

public class DeptDutyInfo1Res extends CommonRes {

	private List<DeptDutysInfo> deptinfos;

	public List<DeptDutysInfo> getDeptinfos() {
		return deptinfos;
	}

	public void setDeptinfos(List<DeptDutysInfo> deptinfos) {
		this.deptinfos = deptinfos;
	}
	 
}
