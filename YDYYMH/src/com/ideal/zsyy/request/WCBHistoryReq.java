package com.ideal.zsyy.request;

import com.ideal2.base.gson.CommonReq;

public class WCBHistoryReq extends CommonReq {

	private String StealNo;
	private int CbMonth;
	private int CbYear;

	public String getStealNo() {
		return StealNo;
	}
	public void setStealNo(String stealNo) {
		StealNo = stealNo;
	}
	public int getCbMonth() {
		return CbMonth;
	}
	public void setCbMonth(int cbMonth) {
		CbMonth = cbMonth;
	}
	public int getCbYear() {
		return CbYear;
	}
	public void setCbYear(int cbYear) {
		CbYear = cbYear;
	}
	
}
