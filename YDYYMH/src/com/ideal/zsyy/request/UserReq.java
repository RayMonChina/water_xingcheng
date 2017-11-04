package com.ideal.zsyy.request;

import com.ideal2.base.gson.CommonReq;


public class UserReq extends CommonReq {

	private String userAccount;
	private String pwd;
	public String getUserAccount() {
		return userAccount;
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
