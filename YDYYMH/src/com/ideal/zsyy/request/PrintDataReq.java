package com.ideal.zsyy.request;

import com.ideal2.base.gson.CommonReq;

/**
 * 医院列表
 * 
 * @author KOBE
 * 
 */
public class PrintDataReq extends CommonReq {
	private String recordId;	//行政区域代码

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	
}
