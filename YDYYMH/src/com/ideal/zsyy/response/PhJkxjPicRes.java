package com.ideal.zsyy.response;

import java.util.List;

import com.ideal.zsyy.entity.PhJkxjPicInfo;
import com.ideal2.base.gson.CommonRes;

public class PhJkxjPicRes extends CommonRes {

	private List<PhJkxjPicInfo> jkxjpicinfo;

	public List<PhJkxjPicInfo> getJkxjpicinfo() {
		return jkxjpicinfo;
	}

	public void setJkxjpicinfo(List<PhJkxjPicInfo> jkxjpicinfo) {
		this.jkxjpicinfo = jkxjpicinfo;
	}
}
