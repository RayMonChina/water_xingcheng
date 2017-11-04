package com.ideal.zsyy.response;

import java.util.List;

import com.ideal.zsyy.entity.PhFloorDeptInfo;
import com.ideal2.base.gson.CommonRes;

public class PhFloorDeptInfoRes extends CommonRes {

	private List<PhFloorDeptInfo> floorDeptInfos;

	public List<PhFloorDeptInfo> getFloorDeptInfos() {
		return floorDeptInfos;
	}

	public void setFloorDeptInfos(List<PhFloorDeptInfo> floorDeptInfos) {
		this.floorDeptInfos = floorDeptInfos;
	}

}
