package com.ideal.zsyy.request;

import com.ideal2.base.gson.CommonReq;

public class WDownUserReq extends CommonReq {

	private String loginid;
	private String cbYear;
	private String cbMonth;
	private String meterReadingNO;

	public String getLoginid() {
		return loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	public String getCbYear() {
		return cbYear;
	}

	public void setCbYear(String cbYear) {
		this.cbYear = cbYear;
	}

	public String getCbMonth() {
		return cbMonth;
	}

	public void setCbMonth(String cbMonth) {
		this.cbMonth = cbMonth;
	}

	public String getMeterReadingNO() {
		return meterReadingNO;
	}

	public void setMeterReadingNO(String meterReadingNO) {
		this.meterReadingNO = meterReadingNO;
	}

	
	
}
