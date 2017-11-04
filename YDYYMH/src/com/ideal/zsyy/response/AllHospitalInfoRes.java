package com.ideal.zsyy.response;

import java.util.List;

import com.ideal.zsyy.entity.BasiHosInfo;
import com.ideal2.base.gson.CommonRes;

/**
 * 医院列表
 * 
 * @author KOBE
 * 
 */
public class AllHospitalInfoRes extends CommonRes {

	private List<BasiHosInfo> allInfos;// 所有医院信息

	public List<BasiHosInfo> getAllInfos() {
		return allInfos;
	}

	public void setAllInfos(List<BasiHosInfo> allInfos) {
		this.allInfos = allInfos;
	}
}
