package com.ideal.zsyy.request;

import com.ideal2.base.gson.CommonReq;

public class UserRegisterReq extends CommonReq{

	private String hospId;
	private String userAccount;
	private String userName;
	private String pwd;
	public String getUserAccount() {
		return userAccount;
	} 
	
	public String getHospId() {
		return hospId;
	}
	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setHospId(String hospId) {
		this.hospId = hospId;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	} 
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	} 
	
}
