package com.ideal.zsyy.request;

import java.util.List;

import com.ideal.zsyy.entity.WCustomerAdviceInfo;
import com.ideal2.base.gson.CommonReq;

public class WAdviceReq extends CommonReq {

	private List<WCustomerAdviceInfo>adviceItems;
	public List<WCustomerAdviceInfo> getAdviceItems() {
		return adviceItems;
	}
	public void setAdviceItems(List<WCustomerAdviceInfo> adviceItems) {
		this.adviceItems = adviceItems;
	}
	
}
