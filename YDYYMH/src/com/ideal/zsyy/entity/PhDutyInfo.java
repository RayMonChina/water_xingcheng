package com.ideal.zsyy.entity;

import java.sql.Timestamp;

public class PhDutyInfo {

	private String dept_id;		// 科室id
	private String dept_name_cn;//科室名称
	private String vst_date;	//开诊日期
	private String noon_type;	//上下午
	private String week_day;	//星期几
	private String limit_no;	//限号数
	private String flag;		//1有效标志 0表示有效
	private String locate;		//n-北院	s-南院
	
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
	public String getVst_date() {
		return vst_date;
	}
	public void setVst_date(String vstDate) {
		vst_date = vstDate;
	}
	public String getNoon_type() {
		return noon_type;
	}
	public void setNoon_type(String noonType) {
		noon_type = noonType;
	}
	public String getWeek_day() {
		return week_day;
	}
	public void setWeek_day(String weekDay) {
		week_day = weekDay;
	}
	public String getLimit_no() {
		return limit_no;
	}
	public void setLimit_no(String limitNo) {
		limit_no = limitNo;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getLocate() {
		return locate;
	}
	public void setLocate(String locate) {
		this.locate = locate;
	}
	
}
