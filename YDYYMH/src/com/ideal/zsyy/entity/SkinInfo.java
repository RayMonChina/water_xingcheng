package com.ideal.zsyy.entity;

//皮肤管理类
public class SkinInfo {

	private String skinName;
	private int skinPath;
	private String isChoose;
	private int main_bj;
	
	public SkinInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SkinInfo(String skinName, int skinPath, String isChoose, int main_bj) {
		super();
		this.skinName = skinName;
		this.skinPath = skinPath;
		this.isChoose = isChoose;
		this.main_bj = main_bj;
	}
	public int getMain_bj() {
		return main_bj;
	}
	public void setMain_bj(int main_bj) {
		this.main_bj = main_bj;
	}
	public String getSkinName() {
		return skinName;
	}
	public void setSkinName(String skinName) {
		this.skinName = skinName;
	}
	public int getSkinPath() {
		return skinPath;
	}
	public void setSkinPath(int skinPath) {
		this.skinPath = skinPath;
	}
	public String getIsChoose() {
		return isChoose;
	}
	public void setIsChoose(String isChoose) {
		this.isChoose = isChoose;
	}
	
}
