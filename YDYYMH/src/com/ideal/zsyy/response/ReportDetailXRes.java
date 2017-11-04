package com.ideal.zsyy.response;

import java.util.List;

import com.ideal.zsyy.entity.TbLisIndicatorsX;
import com.ideal2.base.gson.CommonRes;


public class ReportDetailXRes extends CommonRes{

	//检验结果
	private List<TbLisIndicatorsX> indicatorsx;

	public List<TbLisIndicatorsX> getIndicatorsx() {
		return indicatorsx;
	}

	public void setIndicatorsx(List<TbLisIndicatorsX> indicatorsx) {
		this.indicatorsx = indicatorsx;
	}
	
}
