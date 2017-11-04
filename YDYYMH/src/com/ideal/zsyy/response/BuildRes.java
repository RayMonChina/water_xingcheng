package com.ideal.zsyy.response;

import java.util.List;

import com.ideal.zsyy.entity.HosBuildInfo;
import com.ideal2.base.gson.CommonRes;

/**
 * 楼层
 * 
 * @author KOBE
 * 
 */

public class BuildRes extends CommonRes {

	// 所有的大楼信息
	List<HosBuildInfo> hosBuildInfos;

	public List<HosBuildInfo> getHosBuildInfos() {
		return hosBuildInfos;
	}

	public void setHosBuildInfos(List<HosBuildInfo> hosBuildInfos) {
		this.hosBuildInfos = hosBuildInfos;
	}

}
