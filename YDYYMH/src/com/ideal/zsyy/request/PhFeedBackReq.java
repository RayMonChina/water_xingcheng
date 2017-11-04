package com.ideal.zsyy.request;

import com.ideal.zsyy.entity.PhFeedBack;
import com.ideal2.base.gson.CommonReq;

/**
 * 意见反馈请求类
 * @author Administrator
 *
 */
public class PhFeedBackReq extends CommonReq {

	private PhFeedBack feedback;

	public PhFeedBack getFeedback() {
		return feedback;
	}

	public void setFeedback(PhFeedBack feedback) {
		this.feedback = feedback;
	}
	
	
}
