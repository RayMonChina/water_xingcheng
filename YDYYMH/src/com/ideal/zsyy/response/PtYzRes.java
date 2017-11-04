package com.ideal.zsyy.response;

import java.util.List;

import com.ideal.zsyy.entity.PtYzEntity;
import com.ideal2.base.gson.CommonRes;

public class PtYzRes extends CommonRes {

	private List<PtYzEntity>ptEntities;//医嘱内容

	public List<PtYzEntity> getPtEntities() {
		return ptEntities;
	}

	public void setPtEntities(List<PtYzEntity> ptEntities) {
		this.ptEntities = ptEntities;
	}
	
}
