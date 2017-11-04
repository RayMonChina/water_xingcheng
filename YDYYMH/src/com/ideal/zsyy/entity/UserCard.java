package com.ideal.zsyy.entity;

public class UserCard {

	private String userId;			//用户id
	private String cardNum;			//卡号
	private String cardType;		//卡类型 -- 1：医保卡   2：自费卡
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCardNum() {
		return cardNum;
	}
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	
}
