package com.ideal.zsyy.response;

import java.util.List;

import com.ideal.zsyy.entity.TbLisAllergyResult;
import com.ideal.zsyy.entity.TbLisBacteriaResult;
import com.ideal.zsyy.entity.TbLisIndicators;
import com.ideal2.base.gson.CommonRes;

/**
 * 具体报告单信息
 * @author Administrator
 *
 */
public class ReportDetailRes extends CommonRes {

	private List<TbLisAllergyResult> tblars; //药敏结果集
	private List<TbLisBacteriaResult> tbbrs; //细菌结果集
	private List<TbLisIndicators> tblins;	 //检验结果指标
	public List<TbLisAllergyResult> getTblars() {
		return tblars;
	}
	public void setTblars(List<TbLisAllergyResult> tblars) {
		this.tblars = tblars;
	}
	public List<TbLisBacteriaResult> getTbbrs() {
		return tbbrs;
	}
	public void setTbbrs(List<TbLisBacteriaResult> tbbrs) {
		this.tbbrs = tbbrs;
	}
	public List<TbLisIndicators> getTblins() {
		return tblins;
	}
	public void setTblins(List<TbLisIndicators> tblins) {
		this.tblins = tblins;
	}
	
	
}
