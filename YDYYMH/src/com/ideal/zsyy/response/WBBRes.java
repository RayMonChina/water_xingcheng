package com.ideal.zsyy.response;

import java.util.List;

import com.ideal.zsyy.entity.WBBItem;
import com.ideal2.base.gson.CommonRes;

public class WBBRes extends CommonRes {

	private List<WBBItem>bbItems;

	public List<WBBItem> getBbItems() {
		return bbItems;
	}

	public void setBbItems(List<WBBItem> bbItems) {
		this.bbItems = bbItems;
	}
}
