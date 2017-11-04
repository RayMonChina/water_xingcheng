package com.ideal.zsyy.response;

import java.util.List;

import com.ideal.zsyy.entity.TbListReportX;
import com.ideal2.base.gson.CommonRes;

public class ReportXRes extends CommonRes {

	private List<TbListReportX> tblistreportx;

	public List<TbListReportX> getTblistreportx() {
		return tblistreportx;
	}

	public void setTblistreportx(List<TbListReportX> tblistreportx) {
		this.tblistreportx = tblistreportx;
	}
	
}
