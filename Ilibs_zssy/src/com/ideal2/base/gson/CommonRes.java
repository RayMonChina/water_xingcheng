package com.ideal2.base.gson;

public class CommonRes {
	
	private boolean isErrmsg;
	private String errMsg;
	private int errMsgNo;
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public int getErrMsgNo() {
		return errMsgNo;
	}
	public void setErrMsgNo(int errMsgNo) {
		this.errMsgNo = errMsgNo;
	}
	public boolean isErrMsg(){
		if(errMsg==null||"".equals(errMsg)){
			this.isErrmsg = false;
			return this.isErrmsg;
		}else{
			this.isErrmsg = true;
			return this.isErrmsg;
		}
	}
	
}
