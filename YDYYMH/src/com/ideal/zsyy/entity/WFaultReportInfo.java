package com.ideal.zsyy.entity;

import java.util.List;

public class WFaultReportInfo {

	private int FId;
	private String UserNo;
	private String NoteNo;
	private String FaultDescript;
	private String AddDate;
	private String AddUserID;
	private String AddUser;
	private int alreadyUpload;
	private int BId;
	private String StealNo;
	private List<WPicItem>picItems;
	public int getFId() {
		return FId;
	}
	public void setFId(int fId) {
		FId = fId;
	}
	public String getUserNo() {
		return UserNo;
	}
	public void setUserNo(String userNo) {
		UserNo = userNo;
	}
	public String getFaultDescript() {
		return FaultDescript;
	}
	public void setFaultDescript(String faultDescript) {
		FaultDescript = faultDescript;
	}
	public String getAddDate() {
		return AddDate;
	}
	public void setAddDate(String addDate) {
		AddDate = addDate;
	}
	public String getAddUserID() {
		return AddUserID;
	}
	public void setAddUserID(String addUserID) {
		AddUserID = addUserID;
	}
	public String getAddUser() {
		return AddUser;
	}
	public void setAddUser(String addUser) {
		AddUser = addUser;
	}
	public int getAlreadyUpload() {
		return alreadyUpload;
	}
	public void setAlreadyUpload(int alreadyUpload) {
		this.alreadyUpload = alreadyUpload;
	}
	public List<WPicItem> getPicItems() {
		return picItems;
	}
	public void setPicItems(List<WPicItem> picItems) {
		this.picItems = picItems;
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
	public String getStealNo() {
		return StealNo;
	}
	public void setStealNo(String stealNo) {
		StealNo = stealNo;
	}
	
}
