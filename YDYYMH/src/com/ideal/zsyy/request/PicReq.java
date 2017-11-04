package com.ideal.zsyy.request;

import com.ideal2.base.gson.CommonReq;

public class PicReq extends CommonReq {
	private String UserId;
	private String UserNo;
	private String picType;
	private String picName;
	private String picData;
	private String addDate;
	private double Longitude;
	private double Latitude;
	private int PicId;
	public String getUserId() {
		return UserId;
	}
	public void setUserId(String userId) {
		UserId = userId;
	}
	public String getUserNo() {
		return UserNo;
	}
	public void setUserNo(String userNo) {
		UserNo = userNo;
	}
	public String getPicType() {
		return picType;
	}
	public void setPicType(String picType) {
		this.picType = picType;
	}
	public String getPicName() {
		return picName;
	}
	public void setPicName(String picName) {
		this.picName = picName;
	}
	public String getPicData() {
		return picData;
	}
	public void setPicData(String picData) {
		this.picData = picData;
	}
	public String getAddDate() {
		return addDate;
	}
	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}
	public int getPicId() {
		return PicId;
	}
	public void setPicId(int picId) {
		PicId = picId;
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
	
}
