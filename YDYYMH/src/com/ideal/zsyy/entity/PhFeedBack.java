package com.ideal.zsyy.entity;

import java.sql.Timestamp;

/**
 * 意见反馈
 * @author Administrator
 *
 */
public class PhFeedBack {

	private String hosp_id;		//医院ID
	private String user_id;		//用户ID
	private String user_name;	//用户姓名
	private String user_phone;	//用户电话
	private String e_mail;		//email
	private String user_device;	//用户手机类型
	private String feed_back;	//反馈信息
	private String feed_datetime;	 //反馈时间
	public String getHosp_id() {
		return hosp_id;
	}
	public void setHosp_id(String hospId) {
		hosp_id = hospId;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String userId) {
		user_id = userId;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String userName) {
		user_name = userName;
	}
	public String getUser_phone() {
		return user_phone;
	}
	public void setUser_phone(String userPhone) {
		user_phone = userPhone;
	}
	public String getE_mail() {
		return e_mail;
	}
	public void setE_mail(String eMail) {
		e_mail = eMail;
	}
	public String getUser_device() {
		return user_device;
	}
	public void setUser_device(String userDevice) {
		user_device = userDevice;
	}
	public String getFeed_back() {
		return feed_back;
	}
	public void setFeed_back(String feedBack) {
		feed_back = feedBack;
	}
//	public Timestamp getFeed_datetime() {
//		return feed_datetime;
//	}
//	public void setFeed_datetime(Timestamp feedDatetime) {
//		feed_datetime = feedDatetime;
//	}
	public String getFeed_datetime() {
		return feed_datetime;
	}
	public void setFeed_datetime(String feed_datetime) {
		this.feed_datetime = feed_datetime;
	}
	
	
	
}
