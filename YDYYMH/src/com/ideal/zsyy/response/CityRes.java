package com.ideal.zsyy.response;

import java.util.List;

import com.ideal.zsyy.entity.CityInfo;
import com.ideal2.base.gson.CommonRes;

public class CityRes extends CommonRes {

	private List<CityInfo> cityinfos;

	public List<CityInfo> getCityinfos() {
		return cityinfos;
	}

	public void setCityinfos(List<CityInfo> cityinfos) {
		this.cityinfos = cityinfos;
	}
	
}
