package com.ideal.zsyy.entity;

public class WaterPriceInfo {

	private int Unid;
	private int SIndex;
	private String priceType;
	private String priceTypeName;
	private double WFrom;
	private double WTo;
	private double WPrice;
	public int getUnid() {
		return Unid;
	}
	public void setUnid(int unid) {
		Unid = unid;
	}
	public int getSIndex() {
		return SIndex;
	}
	public void setSIndex(int sIndex) {
		SIndex = sIndex;
	}
	public double getWFrom() {
		return WFrom;
	}
	public void setWFrom(double wFrom) {
		WFrom = wFrom;
	}
	public double getWTo() {
		return WTo;
	}
	public void setWTo(double wTo) {
		WTo = wTo;
	}
	public double getWPrice() {
		return WPrice;
	}
	public void setWPrice(double wPrice) {
		WPrice = wPrice;
	}
	public String getPriceType() {
		return priceType;
	}
	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}
	public String getPriceTypeName() {
		return priceTypeName;
	}
	public void setPriceTypeName(String priceTypeName) {
		this.priceTypeName = priceTypeName;
	}
	
}
