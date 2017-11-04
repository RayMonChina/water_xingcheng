package com.ideal.zsyy.entity;

public class CityEntity {

	private String city_name;
	private String city_code;
	private String pinYinName;
	
	
	public CityEntity(String city_name) {

		this.city_name = city_name;
	}

	public CityEntity(String city_name, String city_code) {

		this(city_name);
		this.city_code = city_code;
	}

	public CityEntity(String city_name, String city_code,String pinYinName) {

		this(city_name, city_code);
		this.pinYinName = pinYinName;
	}
	public String getCity_name() {
		return city_name;
	}
	public void setCity_name(String cityName) {
		city_name = cityName;
	}
	public String getCity_code() {
		return city_code;
	}
	public void setCity_code(String cityCode) {
		city_code = cityCode;
	}
	public String getPinYinName() {
		return pinYinName;
	}
	public void setPinYinName(String pinYinName) {
		this.pinYinName = pinYinName;
	}
	
}
