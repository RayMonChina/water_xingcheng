package com.ideal.zsyy.response;

import java.util.List;

import com.ideal.zsyy.entity.HospitalChargeDayInfo;
import com.ideal2.base.gson.CommonRes;

public class HospitalChargeDayRes extends CommonRes {

	private List<HospitalChargeDayInfo>chargeDayInfos;

	public List<HospitalChargeDayInfo> getChargeDayInfos() {
		return chargeDayInfos;
	}

	public void setChargeDayInfos(List<HospitalChargeDayInfo> chargeDayInfos) {
		this.chargeDayInfos = chargeDayInfos;
	}
	
}
