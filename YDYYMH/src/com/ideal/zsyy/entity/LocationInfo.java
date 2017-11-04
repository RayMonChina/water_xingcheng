package com.ideal.zsyy.entity;

import java.util.Date;

public class LocationInfo {
	private Date getDate;
	private double latitude;
	private double lontitude;
	private double radius;

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLontitude() {
		return lontitude;
	}

	public void setLontitude(double lontitude) {
		this.lontitude = lontitude;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}
	public Date getGetDate() {
		return getDate;
	}

	public void setGetDate(Date getDate) {
		this.getDate = getDate;
	}
}
