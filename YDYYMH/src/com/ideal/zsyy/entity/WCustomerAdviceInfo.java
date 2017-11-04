package com.ideal.zsyy.entity;

public class WCustomerAdviceInfo {

	private int AdviceID;
	private String UserNo;
	private String NoteNo;
	private String Advice;
	private String AddUserId;
	private String AddUser;
	private String AddDate;
	private String UserName;
	private int BId;
	private int alreadyUpload;
	
	public int getAdviceID() {
		return AdviceID;
	}
	public void setAdviceID(int adviceID) {
		AdviceID = adviceID;
	}
	public String getUserNo() {
		return UserNo;
	}
	public void setUserNo(String userNo) {
		UserNo = userNo;
	}
	public String getAdvice() {
		return Advice;
	}
	public void setAdvice(String advice) {
		Advice = advice;
	}
	public String getAddUserId() {
		return AddUserId;
	}
	public void setAddUserId(String addUserId) {
		AddUserId = addUserId;
	}
	public String getAddUser() {
		return AddUser;
	}
	public void setAddUser(String addUser) {
		AddUser = addUser;
	}
	public String getAddDate() {
		return AddDate;
	}
	public void setAddDate(String addDate) {
		AddDate = addDate;
	}
	public int getAlreadyUpload() {
		return alreadyUpload;
	}
	public void setAlreadyUpload(int alreadyUpload) {
		this.alreadyUpload = alreadyUpload;
	}
	public String getNoteNo() {
		return NoteNo;
	}
	public void setNoteNo(String noteNo) {
		NoteNo = noteNo;
	}
	public int getBId() {
		return BId;
	}
	public void setBId(int bId) {
		BId = bId;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	
}
