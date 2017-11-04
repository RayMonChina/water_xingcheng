package com.ideal.zsyy.response;

import com.ideal.zsyy.entity.OperationRecureTip;
import com.ideal2.base.gson.CommonRes;

public class OperationRecureTipRes extends CommonRes {

	private OperationRecureTip recureTip;

	public OperationRecureTip getRecureTip() {
		return recureTip;
	}

	public void setRecureTip(OperationRecureTip recureTip) {
		this.recureTip = recureTip;
	}
	
}
