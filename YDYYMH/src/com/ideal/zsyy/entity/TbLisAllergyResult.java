package com.ideal.zsyy.entity;

/**
 * 药敏结果
 * @author Administrator
 *
 */
public class TbLisAllergyResult {

	private String ymmc; //药敏名称
	private String jcjg;  //检测结果描述
	private String bgrq;	//报告日期
	public String getYmmc() {
		return ymmc==null?"":ymmc;
	}
	public void setYmmc(String ymmc) {
		this.ymmc = ymmc;
	}
	public String getJcjg() {
		return jcjg==null?"":jcjg;
	}
	public void setJcjg(String jcjg) {
		this.jcjg = jcjg;
	}
	public String getBgrq() {
		return bgrq==null?"":bgrq;
	}
	public void setBgrq(String bgrq) {
		this.bgrq = bgrq;
	}
	
}
