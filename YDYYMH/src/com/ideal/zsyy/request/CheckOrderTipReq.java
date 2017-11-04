package com.ideal.zsyy.request;

import com.ideal2.base.gson.CommonReq;

public class CheckOrderTipReq extends CommonReq {

	public CheckOrderTipReq()
	{
		setOperType("111");
	}
	private String orderId;//预约ID
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
}
