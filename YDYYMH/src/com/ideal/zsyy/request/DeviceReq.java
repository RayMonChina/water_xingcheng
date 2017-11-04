package com.ideal.zsyy.request;

import com.ideal2.base.gson.CommonReq;

public class DeviceReq extends CommonReq {

	private String MECode;
	private int isRegButton;


	public String getMECode() {
		return MECode;
	}
	public void setMECode(String mECode) {
		MECode = mECode;
	}

	public int getIsRegButton() {
		return isRegButton;
	}

	public void setIsRegButton(int isRegButton) {
		this.isRegButton = isRegButton;
	}
	
}
