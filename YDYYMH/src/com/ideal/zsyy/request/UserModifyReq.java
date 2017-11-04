package com.ideal.zsyy.request;

import com.ideal.zsyy.entity.UserEntity;
import com.ideal2.base.gson.CommonReq;

/**
 * 用户信息完善
 * 
 * @author KOBE
 * 
 */
public class UserModifyReq extends CommonReq {

	private UserEntity phUser;

	public UserEntity getPhUser() {
		return phUser;
	}

	public void setPhUser(UserEntity phUser) {
		this.phUser = phUser;
	}

}
