package com.ideal.zsyy.entity;

import java.sql.Timestamp;

public class PhDoctorInfo {
	private String id;
	private String hosp_Id;
	private String doctor_Name;
	private String doctor_Id;
	private String job_title;
	private String is_Specialist;
	private String introduce;
	private String expertise;
	private String photo;
	private String reg_Cost;
	private String doctor_Deptid;
	private String doctor_Office;
	private String depName;
	private String locate;
	
	private String dept_id;		// 科室id
	private String dept_name_cn;//科室名称
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHosp_Id() {
		return hosp_Id;
	}
	public void setHosp_Id(String hosp_Id) {
		this.hosp_Id = hosp_Id;
	}
	public String getDoctor_Name() {
		return doctor_Name;
	}
	public void setDoctor_Name(String doctor_Name) {
		this.doctor_Name = doctor_Name;
	}
	public String getDoctor_Id() {
		return doctor_Id;
	}
	public void setDoctor_Id(String doctor_Id) {
		this.doctor_Id = doctor_Id;
	}
	public String getIs_Specialist() {
		return is_Specialist;
	}
	public void setIs_Specialist(String is_Specialist) {
		this.is_Specialist = is_Specialist;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public String getExpertise() {
		return expertise;
	}
	public void setExpertise(String expertise) {
		this.expertise = expertise;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getReg_Cost() {
		return reg_Cost;
	}
	public void setReg_Cost(String reg_Cost) {
		this.reg_Cost = reg_Cost;
	}
	public String getDoctor_Deptid() {
		return doctor_Deptid;
	}
	public void setDoctor_Deptid(String doctor_Deptid) {
		this.doctor_Deptid = doctor_Deptid;
	}
	public String getDoctor_Office() {
		return doctor_Office;
	}
	public void setDoctor_Office(String doctor_Office) {
		this.doctor_Office = doctor_Office;
	}
	public String getDepName() {
		return depName;
	}
	public void setDepName(String depName) {
		this.depName = depName;
	}
	public String getJob_title() {
		return job_title;
	}
	public void setJob_title(String jobTitle) {
		job_title = jobTitle;
	}
	
	
	public String getLocate() {
		return locate==null?"":this.locate;
	}
	public void setLocate(String locate) {
		this.locate = locate;
	}
	
	public String getDept_id() {
		return dept_id;
	}
	public void setDept_id(String deptId) {
		dept_id = deptId;
	}
	public String getDept_name_cn() {
		return dept_name_cn;
	}
	public void setDept_name_cn(String deptNameCn) {
		dept_name_cn = deptNameCn;
	}
	
	
}
