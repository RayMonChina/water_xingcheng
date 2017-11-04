package com.ideal.zsyy.response;

import com.ideal2.base.gson.CommonRes;

public class WUploadUserRes extends CommonRes {
	private Boolean hasPrint;
	private String chargeId;
	private int chargeType;
	public Boolean getHasPrint() {
		return hasPrint;
	}
	public void setHasPrint(Boolean hasPrint) {
		this.hasPrint = hasPrint;
	}
	public String getChargeId() {
		return chargeId;
	}
	public void setChargeId(String chargeId) {
		this.chargeId = chargeId;
	}
	public int getChargeType() {
		return chargeType;
	}
	public void setChargeType(int chargeType) {
		this.chargeType = chargeType;
	}
	
}
