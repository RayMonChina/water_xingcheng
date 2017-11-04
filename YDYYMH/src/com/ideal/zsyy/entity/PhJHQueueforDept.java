package com.ideal.zsyy.entity;

import java.util.List;

/**
 * 
 * @author PYM
 *
 */
public class PhJHQueueforDept {
	private String dept_name;	//科室名称
	private String pat_name;	//病人姓名
	private String doc_name;	//医生姓名
	private String reg_no;		//排队号
	private List<PhJHQueueDoctor> doctorlist;
	private boolean isClike = false;
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public String getPat_name() {
		return pat_name;
	}
	public void setPat_name(String pat_name) {
		this.pat_name = pat_name;
	}
	public String getDoc_name() {
		return doc_name;
	}
	public void setDoc_name(String doc_name) {
		this.doc_name = doc_name;
	}
	public List<PhJHQueueDoctor> getDoctorlist() {
		return doctorlist;
	}
	public void setDoctorlist(List<PhJHQueueDoctor> doctorlist) {
		this.doctorlist = doctorlist;
	}
	public boolean isClike() {
		return isClike;
	}
	public void setClike(boolean isClike) {
		this.isClike = isClike;
	}
	public String getReg_no() {
		return reg_no;
	}
	public void setReg_no(String reg_no) {
		this.reg_no = reg_no;
	}
}
