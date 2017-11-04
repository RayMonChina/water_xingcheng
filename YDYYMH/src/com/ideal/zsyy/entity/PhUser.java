package com.ideal.zsyy.entity;

import java.sql.Timestamp;
import java.util.List;

//用户个人信息表
public class PhUser {
	private String id;
	private String user_Account;
	private String hosp_Id;
	private String secret;
	private String EMail;
	private String sex;
	private String name;
	private String id_Card;
	private String medical_cardnum;
	private String user_add;
	private String user_Photo;
	private String register_Time;
	private String login_Last_Time;
	private String register_Phone;
	private String birthday;
	private String contact_person_name;
	private String contact_person_phone;
	private String report_flag;
	private List<UserCard> phUserCard;
	public String getId() {
		return id==null?"":this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUser_Account() {
		return user_Account==null?"":this.user_Account;
	}
	public void setUser_Account(String user_Account) {
		this.user_Account = user_Account;
	}
	public String getHosp_Id() {
		return hosp_Id==null?"":this.hosp_Id;
	}
	public void setHosp_Id(String hosp_Id) {
		this.hosp_Id = hosp_Id;
	}
	public String getSecret() {
		return secret==null?"":this.secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public String getEMail() {
		return EMail==null?"":this.EMail;
	}
	public void setEMail(String eMail) {
		EMail = eMail;
	}
	public String getSex() {
		return sex==null?"":this.sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getName() {
		return name==null?"":this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId_Card() {
		return id_Card==null?"":this.id_Card;
	}
	public void setId_Card(String id_Card) {
		this.id_Card = id_Card;
	}
	public String getUser_Photo() {
		return user_Photo==null?"":this.user_Photo;
	}
	public void setUser_Photo(String user_Photo) {
		this.user_Photo = user_Photo;
	}
	public String getRegister_Time() {
		return register_Time==null?"":this.register_Time;
	}
	public void setRegister_Time(String register_Time) {
		this.register_Time = register_Time;
	}
	
	public String getLogin_Last_Time() {
		return login_Last_Time==null?"":this.login_Last_Time;
	}
	public void setLogin_Last_Time(String loginLastTime) {
		login_Last_Time = loginLastTime;
	}
	public String getRegister_Phone() {
		return register_Phone==null?"":this.register_Phone;
	}
	public void setRegister_Phone(String register_Phone) {
		this.register_Phone = register_Phone;
	}
	public List<UserCard> getPhUserCard() {
		return phUserCard;
	}
	public void setPhUserCard(List<UserCard> phUserCard) {
		this.phUserCard = phUserCard;
	}
	public String getBirthday() {
		return birthday==null?"":this.birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getContact_person_name() {
		return contact_person_name==null?"":this.contact_person_name;
	}
	public void setContact_person_name(String contactPersonName) {
		contact_person_name = contactPersonName;
	}
	public String getContact_person_phone() {
		return contact_person_phone==null?"":this.contact_person_phone;
	}
	public void setContact_person_phone(String contactPersonPhone) {
		contact_person_phone = contactPersonPhone;
	}
	public String getMedical_cardnum() {
		return medical_cardnum==null?"":this.medical_cardnum;
	}
	public void setMedical_cardnum(String medicalCardnum) {
		medical_cardnum = medicalCardnum;
	}
	public String getReport_flag() {
		return report_flag;
	}
	public void setReport_flag(String reportFlag) {
		report_flag = reportFlag;
	}
	public String getUser_add() {
		return user_add==null?"":this.user_add;
	}
	public void setUser_add(String userAdd) {
		user_add = userAdd;
	}
	
}
