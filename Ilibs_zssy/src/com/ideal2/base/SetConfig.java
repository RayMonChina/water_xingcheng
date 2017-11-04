package com.ideal2.base;

public class SetConfig {
	
	private final static int ARRAYLIST = 1;
	
	private int type;
	
	private transient Class className;
	
	private String parentObjName;
	
	private int num;
	
	public SetConfig(int type, Class className, String parentObjName) {
		this.type = type;
		this.className = className;
		this.parentObjName = parentObjName;
	}

	public SetConfig(int type, Class className, String parentObjName, int num) {
		this.type = type;
		this.className = className;
		this.parentObjName = parentObjName;
		this.num = num;
	}
	
	public SetConfig(int type, int num) {
		this.type = type;
		this.num = num;
	}

	public String getParentObjName() {
		return parentObjName;
	}

	public void setParentObjName(String parentObjName) {
		this.parentObjName = parentObjName;
	}
	
	public Class getClassName() {
		return className;
	}

	public void setClassName(Class className) {
		this.className = className;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
	
}
