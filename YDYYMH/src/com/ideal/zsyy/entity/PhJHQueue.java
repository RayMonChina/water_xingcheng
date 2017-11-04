package com.ideal.zsyy.entity;

/**
 * 叫号查询
 * @author PYM
 *
 */
public class PhJHQueue {

	private String dept_name;	//科室名称
	private String room_no;		//诊室；
	private String reg_no;		//号
	private String pat_name;	//病人姓名
	private String doc_name;	//医生姓名
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public String getRoom_no() {
		return room_no;
	}
	public void setRoom_no(String room_no) {
		this.room_no = room_no;
	}
	public String getReg_no() {
		return reg_no;
	}
	public void setReg_no(String reg_no) {
		this.reg_no = reg_no;
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
	
}
