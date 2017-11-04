package com.ideal2.http;

public class WsdlParam {
	private String methodName;
	private String attrName;
	private String requestValue;
	
	public WsdlParam(String methodName, String attrName, String requestValue) {
		this.methodName = methodName;
		this.attrName = attrName;
		this.requestValue = requestValue;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getAttrName() {
		return attrName;
	}
	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}
	public String getRequestValue() {
		return requestValue;
	}
	public void setRequestValue(String requestValue) {
		this.requestValue = requestValue;
	}
	
}
