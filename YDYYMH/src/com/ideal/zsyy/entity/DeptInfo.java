package com.ideal.zsyy.entity;

public class DeptInfo {

	private String id;
	private String dept_Name;
	private String dept_Id;
	private String dept_Build_no;
	private String dept_Floor;
	private String ward_BuildNo;
	private String ward_Floor;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDept_Name() {
		return dept_Name==null?"":dept_Name;
	}
	public void setDept_Name(String dept_Name) {
		this.dept_Name = dept_Name;
	}
	public String getDept_Id() {
		return dept_Id==null?"":dept_Id;
	}
	public void setDept_Id(String dept_Id) {
		this.dept_Id = dept_Id;
	}
	public String getDept_Build_no() {
		return dept_Build_no==null?"":dept_Build_no;
	}
	public void setDept_Build_no(String dept_Build_no) {
		this.dept_Build_no = dept_Build_no;
	}
	public String getDept_Floor() {
		return dept_Floor==null?"":dept_Floor;
	}
	public void setDept_Floor(String dept_Floor) {
		this.dept_Floor = dept_Floor;
	}
	public String getWard_BuildNo() {
		return ward_BuildNo==null?"":ward_BuildNo;
	}
	public void setWard_BuildNo(String wardBuildNo) {
		this.ward_BuildNo = wardBuildNo;
	}
	public String getWard_Floor() {
		return ward_Floor==null?"":ward_Floor;
	}
	public void setWard_Floor(String wardFloor) {
		this.ward_Floor = wardFloor;
	}
	
}
