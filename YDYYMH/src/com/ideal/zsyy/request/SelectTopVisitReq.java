package com.ideal.zsyy.request;

import com.ideal2.base.gson.CommonReq;

public class SelectTopVisitReq extends CommonReq{
	
	private String vst_card_no;
	private String pat_name;
	private String id_card;
	
	public String getId_card() {
		return id_card;
	}
	public void setId_card(String id_card) {
		this.id_card = id_card;
	}
	public String getVst_card_no() {
		return vst_card_no;
	}
	public void setVst_card_no(String vstCardNo) {
		vst_card_no = vstCardNo;
	}
	public String getPat_name() {
		return pat_name;
	}
	public void setPat_name(String patName) {
		pat_name = patName;
	}
	
}
