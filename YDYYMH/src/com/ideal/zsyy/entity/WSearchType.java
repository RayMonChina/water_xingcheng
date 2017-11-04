package com.ideal.zsyy.entity;

public class WSearchType {

	private String searchName;
	private int number;
	public WSearchType()
	{}
	public WSearchType(int num,String tyName)
	{
		this.searchName=tyName;
		this.number=num;
	}
	public String getSearchName() {
		return searchName;
	}
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return searchName;
	}
	
}
