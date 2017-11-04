package com.ideal.zsyy.entity;

import java.util.List;

public class DeptInfoDetail {
	private String id;
	private String hosp_Id;
	private String dept_Name;
	private String dept_Id;
	private String dept_head;
	private String dept_Phone;
	private String dept_Introduce;
	private List<FloorInfo> buildlist_mz;
	private List<FloorInfo> buildlist_bf;
	
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
	public String getHosp_Id() {
		return hosp_Id;
	}
	public void setHosp_Id(String hosp_Id) {
		this.hosp_Id = hosp_Id;
	}
	public String getDept_head() {
		return dept_head;
	}
	public void setDept_head(String dept_head) {
		this.dept_head = dept_head;
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
	public List<FloorInfo> getBuildlist_mz() {
		return buildlist_mz;
	}
	public void setBuildlist_mz(List<FloorInfo> buildlist_mz) {
		this.buildlist_mz = buildlist_mz;
	}
	public List<FloorInfo> getBuildlist_bf() {
		return buildlist_bf;
	}
	public void setBuildlist_bf(List<FloorInfo> buildlist_bf) {
		this.buildlist_bf = buildlist_bf;
	}
}
