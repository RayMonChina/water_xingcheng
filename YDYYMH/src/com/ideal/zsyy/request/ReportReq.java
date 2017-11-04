package com.ideal.zsyy.request;

import com.ideal2.base.gson.CommonReq;
/**
 * 取报告单
 * @author Administrator
 *
 */

public class ReportReq extends CommonReq {

	private String brxm;	//病人姓名
	private String kh;		//卡号
	private String month;		//月份
	private String erq;		//结束日期
	private String status;
	public String getBrxm() {
		return brxm;
	}
	public void setBrxm(String brxm) {
		this.brxm = brxm;
	}
	public String getKh() {
		return kh;
	}
	public void setKh(String kh) {
		this.kh = kh;
	}
	
	public String getErq() {
		return erq;
	}
	public void setErq(String erq) {
		this.erq = erq;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
