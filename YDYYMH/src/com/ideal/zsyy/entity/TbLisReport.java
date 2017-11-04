package com.ideal.zsyy.entity;

import java.util.List;

/**
 * 实验室检验报告表头
 * @author Administrator
 *
 */
public class TbLisReport {

	private String brxm;	//病人姓名
	private String brxb;	//病人性别
	private String brnl;	//病人年龄
	private String bgdh;	//报告单号
	private String bgrq;	//报告日期
	private String bgdlb;	//报告但类别名称
//	private List<TbLisAllergyResult> tblars; //药敏结果集
//	private List<TbLisBacteriaResult> tbbrs; //细菌结果集
//	private List<TbLisIndicators> tblins;	 //检验结果指标
	
	
	public String getBrxm() {
		return brxm;
	}
	public void setBrxm(String brxm) {
		this.brxm = brxm;
	}
	public String getBrxb() {
		return brxb;
	}
	public void setBrxb(String brxb) {
		this.brxb = brxb;
	}
	public String getBrnl() {
		return brnl;
	}
	public void setBrnl(String brnl) {
		this.brnl = brnl;
	}
	public String getBgdh() {
		return bgdh;
	}
	public void setBgdh(String bgdh) {
		this.bgdh = bgdh;
	}
	public String getBgrq() {
		return bgrq;
	}
	public void setBgrq(String bgrq) {
		this.bgrq = bgrq;
	}
	public String getBgdlb() {
		return bgdlb;
	}
	public void setBgdlb(String bgdlb) {
		this.bgdlb = bgdlb;
	}
	
	
//	public List<TbLisAllergyResult> getTblars() {
//		return tblars;
//	}
//	public void setTblars(List<TbLisAllergyResult> tblars) {
//		this.tblars = tblars;
//	}
//	public List<TbLisBacteriaResult> getTbbrs() {
//		return tbbrs;
//	}
//	public void setTbbrs(List<TbLisBacteriaResult> tbbrs) {
//		this.tbbrs = tbbrs;
//	}
//	public List<TbLisIndicators> getTblins() {
//		return tblins;
//	}
//	public void setTblins(List<TbLisIndicators> tblins) {
//		this.tblins = tblins;
//	}
//	
	
}
