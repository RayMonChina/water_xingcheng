package com.ideal.zsyy.request;

import com.ideal2.base.gson.CommonReq;

public class GetChatRecordReq extends CommonReq{

	private String patient_id_card;  
    private String doctor_id;
	public GetChatRecordReq() {
		setOperType("12002");
	}
	public String getPatient_id_card() {
		return patient_id_card;
	}
	public void setPatient_id_card(String patient_id_card) {
		this.patient_id_card = patient_id_card;
	}
	public String getDoctor_id() {
		return doctor_id;
	}
	public void setDoctor_id(String doctor_id) {
		this.doctor_id = doctor_id;
	}
}
