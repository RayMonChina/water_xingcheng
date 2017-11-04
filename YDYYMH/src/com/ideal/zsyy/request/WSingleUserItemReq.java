package com.ideal.zsyy.request;

import com.ideal2.base.gson.CommonReq;

public class WSingleUserItemReq extends CommonReq {
	private String readMeterRecordId;
	public String getReadMeterRecordId() {
		return readMeterRecordId;
	}
	public void setReadMeterRecordId(String ReadMeterRecordId) {
		readMeterRecordId = ReadMeterRecordId;
	}

}
