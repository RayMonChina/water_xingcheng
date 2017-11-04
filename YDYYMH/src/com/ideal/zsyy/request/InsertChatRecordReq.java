package com.ideal.zsyy.request;

import com.ideal.zsyy.entity.PatientChatContent;
import com.ideal2.base.gson.CommonReq;

public class InsertChatRecordReq extends CommonReq {

	private String message;
	private String patient_id_card;
	private String doctor_id;
	private String doctor_name;
	public InsertChatRecordReq() {
       setOperType("12003");
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
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
	public String getDoctor_name() {
		return doctor_name;
	}
	public void setDoctor_name(String doctor_name) {
		this.doctor_name = doctor_name;
	}
	
}
