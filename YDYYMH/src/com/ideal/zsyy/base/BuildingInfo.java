package com.ideal.zsyy.base;

/**
 * 医院大楼分部
 * 
 * @author KOBE
 * 
 */

public class BuildingInfo {

	private String id;

	private String build_no;

	private String build_name;

	public BuildingInfo(String id, String build_no, String build_name) {

		this.id = id;
		this.build_no = build_no;
		this.build_name = build_name;
	}

	public String getBuild_no() {
		return build_no;
	}

	public void setBuild_no(String build_no) {
		this.build_no = build_no;
	}

	public String getBuild_name() {
		return build_name;
	}

	public void setBuild_name(String build_name) {
		this.build_name = build_name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
