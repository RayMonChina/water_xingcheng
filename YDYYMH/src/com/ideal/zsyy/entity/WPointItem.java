package com.ideal.zsyy.entity;

public class WPointItem {

	private double Latitude;
	private double Longitude;
	private String UserId ;
	private String UserName;
	private String AddDate;
	private int MeterDateTimeBegin;
	private int MeterDateTimeEnd;
	private int alreadyUpload;
	public double getLatitude() {
		return Latitude;
	}
	public void setLatitude(double latitude) {
		Latitude = latitude;
	}
	public double getLongitude() {
		return Longitude;
	}
	public void setLongitude(double longitude) {
		Longitude = longitude;
	}
	public String getUserId() {
		return UserId;
	}
	public void setUserId(String userId) {
		UserId = userId;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
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
	public int getMeterDateTimeBegin() {
		return MeterDateTimeBegin;
	}
	public void setMeterDateTimeBegin(int meterDateTimeBegin) {
		MeterDateTimeBegin = meterDateTimeBegin;
	}
	public int getMeterDateTimeEnd() {
		return MeterDateTimeEnd;
	}
	public void setMeterDateTimeEnd(int meterDateTimeEnd) {
		MeterDateTimeEnd = meterDateTimeEnd;
	}
	
}
