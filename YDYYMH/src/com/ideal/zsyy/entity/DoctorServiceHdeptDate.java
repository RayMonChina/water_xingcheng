package com.ideal.zsyy.entity;



public class DoctorServiceHdeptDate extends DoctorService {

	private String id;
	private String SCID;//排班ID
	private String hdept_Id;//大科室ID
	private String clinic_Id;//门诊科室ID
	private String clinic_Name;//门诊科室名字
	private String clinic_Address;//门诊地址
	private String shift_Date_Str;//排班日期;
	private String day_Section;//上下午
	private String reg_Total;//总号源
	private String get_Reg_Time;//取号时间
	private String get_Reg_Address;//取号地点
	private String sex_Limit;//性别限制
	private String age_Upper_Limit;//年龄上限
	private String age_Lower_Limit;//年龄下线
	private String reg_Fee;//挂号费
	private String is_time_division;//是否分时段
	private String is_clinic;//是否为门诊班次
	private String dept_name;//大科室名称
	
	
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public String getSCID() {
		return SCID;
	}
	public void setSCID(String sCID) {
		SCID = sCID;
	}
	public String getHdept_Id() {
		return hdept_Id;
	}
	public void setHdept_Id(String hdept_Id) {
		this.hdept_Id = hdept_Id;
	}
	public String getClinic_Id() {
		return clinic_Id;
	}
	public void setClinic_Id(String clinic_Id) {
		this.clinic_Id = clinic_Id;
	}
	public String getClinic_Name() {
		return clinic_Name;
	}
	public void setClinic_Name(String clinic_Name) {
		this.clinic_Name = clinic_Name;
	}
	public String getClinic_Address() {
		return clinic_Address;
	}
	public void setClinic_Address(String clinic_Address) {
		this.clinic_Address = clinic_Address;
	}
	public String getShift_Date_Str() {
		return shift_Date_Str;
	}
	public void setShift_Date_Str(String shift_Date_Str) {
		this.shift_Date_Str = shift_Date_Str;
	}
	public String getDay_Section() {
		return day_Section;
	}
	public void setDay_Section(String day_Section) {
		this.day_Section = day_Section;
	}
	public String getReg_Total() {
		return reg_Total;
	}
	public void setReg_Total(String reg_Total) {
		this.reg_Total = reg_Total;
	}
	public String getGet_Reg_Time() {
		return get_Reg_Time;
	}
	public void setGet_Reg_Time(String get_Reg_Time) {
		this.get_Reg_Time = get_Reg_Time;
	}
	public String getGet_Reg_Address() {
		return get_Reg_Address;
	}
	public void setGet_Reg_Address(String get_Reg_Address) {
		this.get_Reg_Address = get_Reg_Address;
	}
	public String getSex_Limit() {
		return sex_Limit;
	}
	public void setSex_Limit(String sex_Limit) {
		this.sex_Limit = sex_Limit;
	}
	public String getAge_Upper_Limit() {
		return age_Upper_Limit;
	}
	public void setAge_Upper_Limit(String age_Upper_Limit) {
		this.age_Upper_Limit = age_Upper_Limit;
	}
	public String getAge_Lower_Limit() {
		return age_Lower_Limit;
	}
	public void setAge_Lower_Limit(String age_Lower_Limit) {
		this.age_Lower_Limit = age_Lower_Limit;
	}
	public String getReg_Fee() {
		return reg_Fee;
	}
	public void setReg_Fee(String reg_Fee) {
		this.reg_Fee = reg_Fee;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIs_time_division() {
		return is_time_division;
	}
	public void setIs_time_division(String is_time_division) {
		this.is_time_division = is_time_division;
	}
	public String getIs_clinic() {
		return is_clinic;
	}
	public void setIs_clinic(String is_clinic) {
		this.is_clinic = is_clinic;
	}
	
}
