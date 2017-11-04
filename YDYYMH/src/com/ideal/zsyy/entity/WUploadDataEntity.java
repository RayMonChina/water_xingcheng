package com.ideal.zsyy.entity;

public class WUploadDataEntity {

	private String userName;//抄表员
	private String cbMonth;//抄表月份
	private int totleUsersNum;//用户总数
	private int alreadyNum;//已超用户数
	private int unCbNum;//未抄用户数
	private int faultNum;//故障报修
	private int adviceNum;//建议
	private int photoNum;//照片
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCbMonth() {
		return cbMonth;
	}
	public void setCbMonth(String cbMonth) {
		this.cbMonth = cbMonth;
	}
	public int getTotleUsersNum() {
		return totleUsersNum;
	}
	public void setTotleUsersNum(int totleUsersNum) {
		this.totleUsersNum = totleUsersNum;
	}
	public int getAlreadyNum() {
		return alreadyNum;
	}
	public void setAlreadyNum(int alreadyNum) {
		this.alreadyNum = alreadyNum;
	}
	public int getUnCbNum() {
		return unCbNum;
	}
	public void setUnCbNum(int unCbNum) {
		this.unCbNum = unCbNum;
	}
	public int getFaultNum() {
		return faultNum;
	}
	public void setFaultNum(int faultNum) {
		this.faultNum = faultNum;
	}
	public int getAdviceNum() {
		return adviceNum;
	}
	public void setAdviceNum(int adviceNum) {
		this.adviceNum = adviceNum;
	}
	public int getPhotoNum() {
		return photoNum;
	}
	public void setPhotoNum(int photoNum) {
		this.photoNum = photoNum;
	}
	
}
