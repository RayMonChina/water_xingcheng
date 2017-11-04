package com.ideal.zsyy.entity;

public class HosChargeInfo {

	private String id;
	private String dept;//科室
	private String hos_no;//住院号
	private String bed_no;//床位号
	private String hosin_time;//入院时间
	private String fee_time;//费用发生时间
	private String item_name;//项目
	private String item_value;//花费
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getHos_no() {
		return hos_no;
	}
	public void setHos_no(String hos_no) {
		this.hos_no = hos_no;
	}
	public String getBed_no() {
		return bed_no;
	}
	public void setBed_no(String bed_no) {
		this.bed_no = bed_no;
	}
	public String getHosin_time() {
		return hosin_time;
	}
	public void setHosin_time(String hosin_time) {
		this.hosin_time = hosin_time;
	}
	public String getFee_time() {
		return fee_time;
	}
	public void setFee_time(String fee_time) {
		this.fee_time = fee_time;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public String getItem_value() {
		return item_value;
	}
	public void setItem_value(String item_value) {
		this.item_value = item_value;
	}
	
}
