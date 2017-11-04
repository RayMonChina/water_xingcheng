package com.ideal.zsyy.response;

import com.ideal.zsyy.entity.PhUser;
import com.ideal2.base.gson.CommonRes;

public class UserRes extends CommonRes {

	PhUser phUsers;

	public PhUser getPhUsers() {
		return phUsers;
	}

	public void setPhUsers(PhUser phUsers) {
		this.phUsers = phUsers;
	}

}
