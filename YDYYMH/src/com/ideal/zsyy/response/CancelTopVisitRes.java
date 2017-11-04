package com.ideal.zsyy.response;

import com.ideal2.base.gson.CommonRes;

public class CancelTopVisitRes extends CommonRes {

	private String result;
	private String smscontent;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getSmscontent() {
		return smscontent;
	}

	public void setSmscontent(String smscontent) {
		this.smscontent = smscontent;
	}
	
}
