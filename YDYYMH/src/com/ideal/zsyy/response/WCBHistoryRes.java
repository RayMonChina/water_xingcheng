package com.ideal.zsyy.response;

import com.ideal.zsyy.entity.WCBUserEntity;
import com.ideal2.base.gson.CommonRes;

public class WCBHistoryRes extends CommonRes {

	private WCBUserEntity UserItem;

	public WCBUserEntity getUserItem() {
		return UserItem;
	}

	public void setUserItem(WCBUserEntity userItem) {
		UserItem = userItem;
	}
	
}
