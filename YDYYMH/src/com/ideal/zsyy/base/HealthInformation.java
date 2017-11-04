package com.ideal.zsyy.base;

public class HealthInformation {

	private int image_id;
	private String information_title;
	private String information_brief_introduction;
	private String information_detail_introduction;
	private String time;

	public HealthInformation() {

	}

	public HealthInformation(String information_title,
			String information_brief_introduction,
			String information_detail_introduction, String time, int image_id) {

		this.image_id = image_id;
		this.information_title = information_title;
		this.information_brief_introduction = information_brief_introduction;
		this.information_detail_introduction = information_detail_introduction;
		this.time = time;

	}

	public int getImage_id() {
		return image_id;
	}

	public void setImage_id(int image_id) {
		this.image_id = image_id;
	}

	public String getInformation_title() {
		return information_title;
	}

	public void setInformation_title(String information_title) {
		this.information_title = information_title;
	}

	public String getInformation_brief_introduction() {
		return information_brief_introduction;
	}

	public void setInformation_brief_introduction(
			String information_brief_introduction) {
		this.information_brief_introduction = information_brief_introduction;
	}

	public String getInformation_detail_introduction() {
		return information_detail_introduction;
	}

	public void setInformation_detail_introduction(
			String information_detail_introduction) {
		this.information_detail_introduction = information_detail_introduction;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
