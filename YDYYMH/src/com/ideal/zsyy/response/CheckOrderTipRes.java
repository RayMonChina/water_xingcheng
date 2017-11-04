package com.ideal.zsyy.response;

import com.ideal.zsyy.entity.CheckOrderTip;
import com.ideal2.base.gson.CommonRes;

public class CheckOrderTipRes extends CommonRes{

	private CheckOrderTip checkTip;

	public CheckOrderTip getCheckTip() {
		return checkTip;
	}

	public void setCheckTip(CheckOrderTip checkTip) {
		this.checkTip = checkTip;
	}
	
}
