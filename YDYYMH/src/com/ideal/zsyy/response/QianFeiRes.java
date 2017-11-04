package com.ideal.zsyy.response;

import java.util.List;

import com.ideal2.base.gson.CommonRes;
import com.ideal.zsyy.entity.QianFeiItem;

public class QianFeiRes extends CommonRes {
	private List<QianFeiItem>QianFeiItem;

	public List<QianFeiItem> getQianFeiItem() {
		return QianFeiItem;
	}

	public void setQianFeiItem(List<QianFeiItem> qianFeiItem) {
		QianFeiItem = qianFeiItem;
	}
}
