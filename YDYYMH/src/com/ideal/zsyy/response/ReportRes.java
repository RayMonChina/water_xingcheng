package com.ideal.zsyy.response;

import java.util.List;

import com.ideal.zsyy.entity.TbLisReport;
import com.ideal2.base.gson.CommonRes;

/**
 * 取报告单
 * @author Administrator
 *
 */
public class ReportRes extends CommonRes {
	
	
	private List<TbLisReport> reportlist;

	public List<TbLisReport> getReportlist() {
		return reportlist;
	}

	public void setReportlist(List<TbLisReport> reportlist) {
		this.reportlist = reportlist;
	}
	
	

//	private TbLisReport tbLisReport;
//
//	public TbLisReport getTbLisReport() {
//		return tbLisReport;
//	}
//
//	public void setTbLisReport(TbLisReport tbLisReport) {
//		this.tbLisReport = tbLisReport;
//	}
	
}
