package com.ideal.zsyy.response;

import java.util.List;

import com.ideal.zsyy.entity.DayFeeList;
import com.ideal2.base.gson.CommonRes;

public class DayFeeRes extends CommonRes{

	private List<DayFeeList>feeLists;

	public List<DayFeeList> getFeeLists() {
		return feeLists;
	}

	public void setFeeLists(List<DayFeeList> feeLists) {
		this.feeLists = feeLists;
	}
	
}
