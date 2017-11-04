package com.ideal.zsyy.response;

import java.util.List;

import com.ideal.zsyy.entity.PhHotPic;
import com.ideal2.base.gson.CommonRes;

public class PhHotPicRes extends CommonRes {
	
	List<PhHotPic> phHotPics;

	public List<PhHotPic> getPhHotPics() {
		return phHotPics;
	}

	public void setPhHotPics(List<PhHotPic> phHotPics) {
		this.phHotPics = phHotPics;
	}
	
}
