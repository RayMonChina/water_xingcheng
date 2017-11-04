package com.ideal.zsyy.request;

import java.util.List;

import com.ideal.zsyy.entity.WPointItem;
import com.ideal2.base.gson.CommonReq;

public class WPointReq extends CommonReq {

	private List<WPointItem>pointItems;

	public List<WPointItem> getPointItems() {
		return pointItems;
	}

	public void setPointItems(List<WPointItem> pointItems) {
		this.pointItems = pointItems;
	}
	
}
