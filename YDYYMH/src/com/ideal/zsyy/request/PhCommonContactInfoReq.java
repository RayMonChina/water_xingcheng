package com.ideal.zsyy.request;

import com.ideal.zsyy.entity.PhCommonContactInfo;
import com.ideal2.base.gson.CommonReq;


public class PhCommonContactInfoReq extends CommonReq {

	private PhCommonContactInfo contactInfo;

	public PhCommonContactInfo getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(PhCommonContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}
}
