package com.ideal.zsyy.entity;

import com.hp.hpl.sparta.xpath.ThisNodeTest;

//表本信息
public class WBBItem {

	private int BId;
	private String NoteNo;
	private int CBMonth;
	private String CBUser;
	private String CBUserID;
	private String PianNo;
	private String AreaNo;
	private String DuanNo;
	private int CBYear;
	private int CustomerCount;

	public int getBId() {
		return BId;
	}

	public void setBId(int bId) {
		BId = bId;
	}

	public String getNoteNo() {
		return NoteNo;
	}

	public void setNoteNo(String noteNo) {
		NoteNo = noteNo;
	}

	public int bbiItem() {
		return CBMonth;
	}

	public void setCBMonth(int cBMonth) {
		CBMonth = cBMonth;
	}

	public String getCBUser() {
		return CBUser;
	}

	public void setCBUser(String cBUser) {
		CBUser = cBUser;
	}

	public String getCBUserID() {
		return CBUserID;
	}

	public void setCBUserID(String cBUserID) {
		CBUserID = cBUserID;
	}

	public int getCustomerCount() {
		return CustomerCount;
	}

	public void setCustomerCount(int customerCount) {
		CustomerCount = customerCount;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		if (null == this.AreaNo || "".equals(this.AreaNo)) {
			return this.NoteNo;
		} else {
			if (this.NoteNo.equals(this.AreaNo)) {
				return this.NoteNo;
			} else {
				return this.NoteNo + '-' + this.AreaNo;
			}
		}
		// PianNo+" "+AreaNo+" "+DuanNo;
	}

	public int getCBYear() {
		return CBYear;
	}

	public void setCBYear(int cBYear) {
		CBYear = cBYear;
	}

	public String getPianNo() {
		return PianNo;
	}

	public void setPianNo(String pianNo) {
		PianNo = pianNo;
	}

	public String getAreaNo() {
		return AreaNo;
	}

	public void setAreaNo(String areaNo) {
		AreaNo = areaNo;
	}

	public String getDuanNo() {
		return DuanNo;
	}

	public void setDuanNo(String duanNo) {
		DuanNo = duanNo;
	}

	public int getCBMonth() {
		return CBMonth;
	}

}
