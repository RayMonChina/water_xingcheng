package com.ideal.zsyy.request;

import com.ideal2.base.gson.CommonReq;

public class WUserReq extends CommonReq {

	private String LOGINNAME;
	private String LOGINPASSWORD;
	public String getLOGINNAME() {
		return LOGINNAME;
	}
	public void setLOGINNAME(String lOGINNAME) {
		LOGINNAME = lOGINNAME;
	}
	public String getLOGINPASSWORD() {
		return LOGINPASSWORD;
	}
	public void setLOGINPASSWORD(String lOGINPASSWORD) {
		LOGINPASSWORD = lOGINPASSWORD;
	}

	
}
