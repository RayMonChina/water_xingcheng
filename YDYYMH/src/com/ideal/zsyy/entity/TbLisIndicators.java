package com.ideal.zsyy.entity;

/**
 * 检验结果指标表
 * @author Administrator
 *
 */
public class TbLisIndicators {

	private String jczbmc;	//检测指标名称
	private String jczbjg;	//检测指标结果
	private String jldw;	//计量单位
	private String ckz;		//参考值范围
	private String bgrq;	//报告日期
	public String getJczbmc() {
		return jczbmc;
	}
	public void setJczbmc(String jczbmc) {
		this.jczbmc = jczbmc;
	}
	public String getJczbjg() {
		return jczbjg;
	}
	public void setJczbjg(String jczbjg) {
		this.jczbjg = jczbjg;
	}
	public String getJldw() {
		return jldw;
	}
	public void setJldw(String jldw) {
		this.jldw = jldw;
	}
	public String getCkz() {
		return ckz;
	}
	public void setCkz(String ckz) {
		this.ckz = ckz;
	}
	public String getBgrq() {
		return bgrq;
	}
	public void setBgrq(String bgrq) {
		this.bgrq = bgrq;
	}
	
}
