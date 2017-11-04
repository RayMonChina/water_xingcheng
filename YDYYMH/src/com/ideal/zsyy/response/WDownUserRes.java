package com.ideal.zsyy.response;

import java.util.List;

import com.ideal.zsyy.entity.WCBUserEntity;
import com.ideal2.base.gson.CommonRes;

public class WDownUserRes extends CommonRes {

	private List<WCBUserEntity>userItems;

	public List<WCBUserEntity> getUserItems() {
		return userItems;
	}

	public void setUserItems(List<WCBUserEntity> userItems) {
		this.userItems = userItems;
	}
	
}
