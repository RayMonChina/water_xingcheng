package com.ideal.zsyy.response;

import com.ideal2.base.gson.CommonRes;

public class WUserRes extends CommonRes {

	private String LOGINID;
	private String USERNAME;
	private int MeterDateTimeBegin;
	private int MeterDateTimeEnd;
	public String getLOGINID() {
		return LOGINID;
	}
	public void setLOGINID(String lOGINID) {
		LOGINID = lOGINID;
	}
	public String getUSERNAME() {
		return USERNAME;
	}
	public void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}
	public int getMeterDateTimeEnd() {
		return MeterDateTimeEnd;
	}
	public void setMeterDateTimeEnd(int meterDateTimeEnd) {
		MeterDateTimeEnd = meterDateTimeEnd;
	}
	public int getMeterDateTimeBegin() {
		return MeterDateTimeBegin;
	}
	public void setMeterDateTimeBegin(int meterDateTimeBegin) {
		MeterDateTimeBegin = meterDateTimeBegin;
	}
	
	
}
