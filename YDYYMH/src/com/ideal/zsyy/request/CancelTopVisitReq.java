package com.ideal.zsyy.request;

import com.ideal2.base.gson.CommonReq;

public class CancelTopVisitReq extends CommonReq{

	private String seqn;
	private String vstCardNo;
	public String getSeqn() {
		return seqn;
	}
	public void setSeqn(String seqn) {
		this.seqn = seqn;
	}
	public String getVstCardNo() {
		return vstCardNo;
	}
	public void setVstCardNo(String vstCardNo) {
		this.vstCardNo = vstCardNo;
	}
	
	
}
