package com.ideal.zsyy.response;

import java.util.List;

import com.ideal.zsyy.entity.FloorDeptInfo;
import com.ideal2.base.gson.CommonRes;

/**
 * 大楼科室信息2
 * @author KOBE
 *
 */
public class FloorDeptInfoRes extends CommonRes {

	private List<FloorDeptInfo> floorDeptInfos;

	public List<FloorDeptInfo> getFloorDeptInfos() {
		return floorDeptInfos;
	}

	public void setFloorDeptInfos(List<FloorDeptInfo> floorDeptInfos) {
		this.floorDeptInfos = floorDeptInfos;
	}

}
