package com.ideal.zsyy.entity;

import java.util.List;

public class WUserItem {

	private int UserID;
	private String yhbm;//用户编码
	private String bbh;//表本号
	private String xm;//姓名
	private String dh;//电话
	private String dz;//地址
	private double syzd;//上月止度，上月表数
	private double sysl;//上月水量
	private double sysf;//上月水费
	private String jglx;//价格类型
	private String jglxName;//价格类型名称
	private String bgh;//表钢号
	private int cbbz;//抄表标志
	private double ycje;//预存金额
	private double ljqf;//累计欠费
	private double byzd;//本月止度，本月表数
	private double sl;//水量
	private double bysf;//本月收费
	private String sfrq;//收费日期
	private String cbrq;//本月抄表日期
	private double Latitude;//纬度
	private double Longitude;//经度
	private double currentMonthFee;//本月费用
	private double orPremoney;//原始预存金额
	private double orOweMoney;//原始累计欠费
	private int Bid;//表本ID
	private List<WPicItem>picItems;
	public String getYhbm() {
		return yhbm;
	}
	public void setYhbm(String yhbm) {
		this.yhbm = yhbm;
	}
	public String getXm() {
		return xm;
	}
	public void setXm(String xm) {
		this.xm = xm;
	}
	public String getDh() {
		return dh;
	}
	public void setDh(String dh) {
		this.dh = dh;
	}
	public String getDz() {
		return dz;
	}
	public void setDz(String dz) {
		this.dz = dz;
	}
	public double getSyzd() {
		return syzd;
	}
	public void setSyzd(double syzd) {
		this.syzd = syzd;
	}
	public double getByzd() {
		return byzd;
	}
	public void setByzd(double byzd) {
		this.byzd = byzd;
	}
	public double getSl() {
		double dsl=this.byzd-this.syzd;
		return dsl>0?dsl:0;
	}
	public void setSl(double sl) {
		this.sl = sl;
	}
	public String getBbh() {
		return bbh;
	}
	public void setBbh(String bbh) {
		this.bbh = bbh;
	}
	public double getSysl() {
		return sysl;
	}
	public void setSysl(double sysl) {
		this.sysl = sysl;
	}
	public double getSysf() {
		return sysf;
	}
	public void setSysf(double sysf) {
		this.sysf = sysf;
	}
	public String getJglx() {
		return jglx;
	}
	public void setJglx(String jglx) {
		this.jglx = jglx;
	}
	public String getBgh() {
		return bgh;
	}
	public void setBgh(String bgh) {
		this.bgh = bgh;
	}
	public int getCbbz() {
		return cbbz;
	}
	public void setCbbz(int cbbz) {
		this.cbbz = cbbz;
	}
	public double getYcje() {
		return ycje;
	}
	public void setYcje(double ycje) {
		this.ycje = ycje;
	}
	public double getLjqf() {
		return ljqf;
	}
	public void setLjqf(double ljqf) {
		this.ljqf = ljqf;
	}
	public double getBysf() {
		return bysf;
	}
	public void setBysf(double bysf) {
		this.bysf = bysf;
	}
	public String getSfrq() {
		return sfrq;
	}
	public void setSfrq(String sfrq) {
		this.sfrq = sfrq;
	}
	public String getCbrq() {
		return cbrq;
	}
	public void setCbrq(String cbrq) {
		this.cbrq = cbrq;
	}
	public double getLatitude() {
		return Latitude;
	}
	public void setLatitude(double latitude) {
		Latitude = latitude;
	}
	public double getLongitude() {
		return Longitude;
	}
	public void setLongitude(double longitude) {
		Longitude = longitude;
	}
	public List<WPicItem> getPicItems() {
		return picItems;
	}
	public void setPicItems(List<WPicItem> picItems) {
		this.picItems = picItems;
	}
	public double getCurrentMonthFee() {
		return currentMonthFee;
	}
	public void setCurrentMonthFee(double currentMonthFee) {
		this.currentMonthFee = currentMonthFee;
	}
	public double getOrPremoney() {
		return orPremoney;
	}
	public void setOrPremoney(double orPremoney) {
		this.orPremoney = orPremoney;
	}
	public double getOrOweMoney() {
		return orOweMoney;
	}
	public void setOrOweMoney(double orOweMoney) {
		this.orOweMoney = orOweMoney;
	}
	public String getJglxName() {
		return jglxName;
	}
	public void setJglxName(String jglxName) {
		this.jglxName = jglxName;
	}
	public int getUserID() {
		return UserID;
	}
	public void setUserID(int userID) {
		UserID = userID;
	}
	public int getBid() {
		return Bid;
	}
	public void setBid(int bid) {
		Bid = bid;
	}
	
}
