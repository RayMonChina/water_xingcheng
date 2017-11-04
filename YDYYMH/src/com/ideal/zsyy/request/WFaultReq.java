package com.ideal.zsyy.request;

public class WFaultReq extends ComplainReq {

	private String ImgData;
	private String WaterUserId;
	private String Describe;
	private String CreateDateTime;
	private String LoginId;
	public String getImgData() {
		return ImgData;
	}
	public void setImgData(String imgData) {
		ImgData = imgData;
	}
	public String getWaterUserId() {
		return WaterUserId;
	}
	public void setWaterUserId(String waterUserId) {
		WaterUserId = waterUserId;
	}
	public String getDescribe() {
		return Describe;
	}
	public void setDescribe(String describe) {
		Describe = describe;
	}
	public String getCreateDateTime() {
		return CreateDateTime;
	}
	public void setCreateDateTime(String createDateTime) {
		CreateDateTime = createDateTime;
	}
	public String getLoginId() {
		return LoginId;
	}
	public void setLoginId(String loginId) {
		LoginId = loginId;
	}
	
}
