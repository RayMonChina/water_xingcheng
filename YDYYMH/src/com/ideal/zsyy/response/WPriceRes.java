package com.ideal.zsyy.response;

import java.util.List;

import com.ideal.zsyy.entity.WaterPriceInfo;
import com.ideal2.base.gson.CommonRes;

public class WPriceRes extends CommonRes {

	private List<WaterPriceInfo>priceItems;

	public List<WaterPriceInfo> getPriceItems() {
		return priceItems;
	}

	public void setPriceItems(List<WaterPriceInfo> priceItems) {
		this.priceItems = priceItems;
	}
	
}
