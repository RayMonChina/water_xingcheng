package com.ideal.zsyy.response;

import java.util.HashMap;
import java.util.List;

import com.ideal.zsyy.entity.ChatContent;
import com.ideal.zsyy.entity.PatientChatContent;
import com.ideal2.base.gson.CommonRes;

public class GetChatRecordRes extends CommonRes {

	private List<PatientChatContent> patient_content;
	
	
	public List<PatientChatContent> getPatient_content() {
		return patient_content;
	}
	public void setPatient_content(List<PatientChatContent> patient_content) {
		this.patient_content = patient_content;
	}
	public HashMap<String, ChatContent> getDoctor_content() {
		return doctor_content;
	}
	public void setDoctor_content(HashMap<String, ChatContent> doctor_content) {
		this.doctor_content = doctor_content;
	}
	private HashMap<String, ChatContent> doctor_content;
}
