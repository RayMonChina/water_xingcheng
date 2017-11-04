package com.ideal.zsyy.entity;

import java.util.List;

/**
 * 健康宣教
 * @author Administrator
 *
 */
public class PhJkxjInfo {

	private String id;
	private String hosp_id;
	private String type;
	private String title;
	private String memo;
	private String user_id;
	private List<PhJkxjPicInfo> jkxjpicinfo;
	private boolean isVedio = false;

	public boolean isVedio() {
		return isVedio;
	}

	public void setVedio(boolean isVedio) {
		this.isVedio = isVedio;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHosp_id() {
		return hosp_id;
	}
	public void setHosp_id(String hospId) {
		hosp_id = hospId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String userId) {
		user_id = userId;
	}
	public List<PhJkxjPicInfo> getJkxjpicinfo() {
		return jkxjpicinfo;
	}
	public void setJkxjpicinfo(List<PhJkxjPicInfo> jkxjpicinfo) {
		this.jkxjpicinfo = jkxjpicinfo;
	}
	
	
}
