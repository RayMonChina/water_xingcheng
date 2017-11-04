package com.ideal.zsyy.request;

import com.ideal.zsyy.entity.WCBWaterChargeItem;
import com.ideal2.base.gson.CommonReq;

public class WaterChargeReq extends CommonReq {
	WCBWaterChargeItem chageItem;

	public WCBWaterChargeItem getChageItem() {
		return chageItem;
	}

	public void setChageItem(WCBWaterChargeItem chageItem) {
		this.chageItem = chageItem;
	}

}
