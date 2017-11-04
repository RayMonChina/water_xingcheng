package com.ideal.zsyy.entity;

import java.util.List;

public class MZChargeGroupInfo {

	private float totle;//总费用
	private String feeTime;//费用发生时间
	private List<MZChargeInfo>chargeInfos;//费用信息
	
	public float getTotle() {
		return totle;
	}
	public void setTotle(float totle) {
		this.totle = totle;
	}
	public String getFeeTime() {
		return feeTime;
	}
	public void setFeeTime(String feeTime) {
		this.feeTime = feeTime;
	}
	public List<MZChargeInfo> getChargeInfos() {
		return chargeInfos;
	}
	public void setChargeInfos(List<MZChargeInfo> chargeInfos) {
		this.chargeInfos = chargeInfos;
	}
	
}
