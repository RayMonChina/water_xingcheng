package com.ideal.zsyy.response;

import java.util.List;

import com.ideal.zsyy.entity.HospitalChargeInfo;
import com.ideal2.base.gson.CommonRes;

public class HospitalChargeRes extends CommonRes{

	private String times;
	private String totleFee;
	private List<HospitalChargeInfo>chargeInfos;

	
	public String getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
	}

	public String getTotleFee() {
		return totleFee;
	}

	public void setTotleFee(String totleFee) {
		this.totleFee = totleFee;
	}

	public List<HospitalChargeInfo> getChargeInfos() {
		return chargeInfos;
	}

	public void setChargeInfos(List<HospitalChargeInfo> chargeInfos) {
		this.chargeInfos = chargeInfos;
	}
	
}
