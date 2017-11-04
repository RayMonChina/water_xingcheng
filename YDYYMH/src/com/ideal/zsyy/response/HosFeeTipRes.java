package com.ideal.zsyy.response;

import java.util.List;

import com.ideal.zsyy.entity.HosChargeInfo;
import com.ideal2.base.gson.CommonRes;

public class HosFeeTipRes extends CommonRes {
	private List<HosChargeInfo>hosFees;
	private String feeTotle;
	public List<HosChargeInfo> getHosFees() {
		return hosFees;
	}
	public void setHosFees(List<HosChargeInfo> hosFees) {
		this.hosFees = hosFees;
	}
	public String getFeeTotle() {
		return feeTotle;
	}
	public void setFeeTotle(String feeTotle) {
		this.feeTotle = feeTotle;
	}
	
}
