package com.ideal.zsyy.response;

import java.util.List;

import com.ideal.zsyy.entity.QianFeiHistoryItem;
import com.ideal2.base.gson.CommonRes;

public class QianFeiHistoryRes extends CommonRes {

	private List<QianFeiHistoryItem>QianFeiHistoryItem;
	
	public List<QianFeiHistoryItem> getQianFeiHistoryItem() {
		return QianFeiHistoryItem;
	}
	public void setQianFeiHistoryItem(List<QianFeiHistoryItem> QianFeiHistoryItems) {
		this.QianFeiHistoryItem = QianFeiHistoryItems;
	}
}
