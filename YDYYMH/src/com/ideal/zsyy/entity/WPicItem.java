package com.ideal.zsyy.entity;

public class WPicItem {
	private int PicId;
	private String UserNo;
	private String NoteNo;
	private String PicName;
	private String PicPath;
	private String AddDate;
	private String AddUserId;
	private String AddUser;
	private double Longitude;
	private double Latitude;
	private int alreadyUpload;
	private int PicType;
	private int BId;
	private String StealNo;
	public int getPicId() {
		return PicId;
	}
	public void setPicId(int picId) {
		PicId = picId;
	}
	public String getUserNo() {
		return UserNo;
	}
	public void setUserNo(String userNo) {
		UserNo = userNo;
	}
	public String getPicName() {
		return PicName;
	}
	public void setPicName(String picName) {
		PicName = picName;
	}
	public String getPicPath() {
		return PicPath;
	}
	public void setPicPath(String picPath) {
		PicPath = picPath;
	}
	public String getAddDate() {
		return AddDate;
	}
	public void setAddDate(String addDate) {
		AddDate = addDate;
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
	public int getAlreadyUpload() {
		return alreadyUpload;
	}
	public void setAlreadyUpload(int alreadyUpload) {
		this.alreadyUpload = alreadyUpload;
	}
	public int getPicType() {
		return PicType;
	}
	public void setPicType(int picType) {
		PicType = picType;
	}
	public String getNoteNo() {
		return NoteNo;
	}
	public void setNoteNo(String noteNo) {
		NoteNo = noteNo;
	}
	public double getLongitude() {
		return Longitude;
	}
	public void setLongitude(double longitude) {
		Longitude = longitude;
	}
	public double getLatitude() {
		return Latitude;
	}
	public void setLatitude(double latitude) {
		Latitude = latitude;
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
