package com.ideal.zsyy.entity;

import java.util.List;

public class UserEntity{

	private String userId;
	private String EMail;
	private String sex;
	private String name;
//	private String register_phone;
	private String medical_cardnum;
	private String user_add;
	private String id_Card;
	private String birthday;
	private String contact_person_name;
	private String contact_person_phone;
	private List<UserCard> phUserCard;
	
	public String getUserId() {
		return userId==null?"":this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getEMail() {
		return EMail==null?"":this.EMail;
	}
	public void setEMail(String eMail) {
		EMail = eMail;
	}
	
//	public String getRegister_phone() {
//		return register_phone;
//	}
//	public void setRegister_phone(String registerPhone) {
//		register_phone = registerPhone;
//	}
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
	public void setId_Card(String idCard) {
		id_Card = idCard;
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
	public List<UserCard> getPhUserCard() {
		return phUserCard;
	}
	public void setPhUserCard(List<UserCard> phUserCard) {
		this.phUserCard = phUserCard;
	}
	public String getMedical_cardnum() {
		return medical_cardnum==null?"":this.medical_cardnum;
	}
	public void setMedical_cardnum(String medicalCardnum) {
		medical_cardnum = medicalCardnum;
	}
	public String getUser_add() {
		return user_add;
	}
	public void setUser_add(String userAdd) {
		user_add = userAdd;
	}
}
