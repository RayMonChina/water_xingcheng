package com.ideal.zsyy.response;


import java.util.List;

import com.ideal.zsyy.entity.PhCommonContactInfo;
import com.ideal2.base.gson.CommonRes;

public class CommonContactListRes extends CommonRes {

	private List<PhCommonContactInfo> contactList;

	public List<PhCommonContactInfo> getContactList() {
		return contactList;
	}
	public void setContactList(List<PhCommonContactInfo> contactList) {
		this.contactList = contactList;
	}
}
