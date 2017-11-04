package com.ideal.zsyy.response;

import java.util.List;

import com.ideal.zsyy.entity.MZChargeGroupInfo;
import com.ideal2.base.gson.CommonRes;

public class MzChargeRes extends CommonRes {

	private List<MZChargeGroupInfo>mzChargeInfos;

	public List<MZChargeGroupInfo> getMzChargeInfos() {
		return mzChargeInfos;
	}

	public void setMzChargeInfos(List<MZChargeGroupInfo> mzChargeInfos) {
		this.mzChargeInfos = mzChargeInfos;
	}
	
}
