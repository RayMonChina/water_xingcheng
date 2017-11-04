package com.ideal.zsyy.entity;

public class EquipmentItem {
	private String MEID;
	private String MECode;
	private String LoginID;
	private int States;

	public String getMEID() {
		return MEID;
	}

	public void setMEID(String mEID) {
		MEID = mEID;
	}

	public String getMECode() {
		return MECode;
	}

	public void setMECode(String mECode) {
		MECode = mECode;
	}

	public String getLoginID() {
		return LoginID;
	}

	public void setLoginID(String loginID) {
		LoginID = loginID;
	}

	public int getStates() {
		return States;
	}

	public void setStates(int states) {
		States = states;
	}

}
