package com.ideal.zsyy.base;

public class PersonalCenter {

	private String yuyue_no;
	private String yuyue_room_or_person;
	private String yuyue_time;
	private String yuyue_result;

	public PersonalCenter() {

	}

	public PersonalCenter(String yuyue_no, String yuyue_room_or_person,
			String yuyue_time, String yuyue_result) {

		this.yuyue_no = yuyue_no;
		this.yuyue_room_or_person = yuyue_room_or_person;
		this.yuyue_time = yuyue_time;
		this.yuyue_result = yuyue_result;
	}

	public String getYuyue_no() {
		return yuyue_no;
	}

	public void setYuyue_no(String yuyue_no) {
		this.yuyue_no = yuyue_no;
	}

	public String getYuyue_room_or_person() {
		return yuyue_room_or_person;
	}

	public void setYuyue_room_or_person(String yuyue_room_or_person) {
		this.yuyue_room_or_person = yuyue_room_or_person;
	}

	public String getYuyue_time() {
		return yuyue_time;
	}

	public void setYuyue_time(String yuyue_time) {
		this.yuyue_time = yuyue_time;
	}

	public String getYuyue_result() {
		return yuyue_result;
	}

	public void setYuyue_result(String yuyue_result) {
		this.yuyue_result = yuyue_result;
	}

}
