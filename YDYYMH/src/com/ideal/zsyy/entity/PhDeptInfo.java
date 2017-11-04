package com.ideal.zsyy.entity;

import java.sql.Timestamp;

public class PhDeptInfo {
	private String id;
	private String hosp_Id;
	private String dept_Name;
	private String dept_Id;
	private String dept_Phone;
	private String dept_Introduce;
	private String dept_BuildNo;
	private String dept_Floor;
	private String ward_BuildNo;
	private String ward_Floor;
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
	public String getDept_Name() {
		return dept_Name;
	}
	public void setDept_Name(String dept_Name) {
		this.dept_Name = dept_Name;
	}
	public String getDept_Id() {
		return dept_Id;
	}
	public void setDept_Id(String dept_Id) {
		this.dept_Id = dept_Id;
	}
	public String getDept_Phone() {
		return dept_Phone;
	}
	public void setDept_Phone(String dept_Phone) {
		this.dept_Phone = dept_Phone;
	}
	public String getDept_Introduce() {
		return dept_Introduce;
	}
	public void setDept_Introduce(String dept_Introduce) {
		this.dept_Introduce = dept_Introduce;
	}
	public String getDept_BuildNo() {
		return dept_BuildNo;
	}
	public void setDept_BuildNo(String dept_BuildNo) {
		this.dept_BuildNo = dept_BuildNo;
	}
	public String getDept_Floor() {
		return dept_Floor;
	}
	public void setDept_Floor(String dept_Floor) {
		this.dept_Floor = dept_Floor;
	}
	public String getWard_BuildNo() {
		return ward_BuildNo;
	}
	public void setWard_BuildNo(String wardBuildNo) {
		ward_BuildNo = wardBuildNo;
	}
	public String getWard_Floor() {
		return ward_Floor;
	}
	public void setWard_Floor(String wardFloor) {
		ward_Floor = wardFloor;
	}
	
}
