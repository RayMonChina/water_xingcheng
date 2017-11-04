package com.ideal.zsyy.request;

import com.ideal2.base.gson.CommonReq;

public class DocDutyInfoReq extends CommonReq {

	private String docId;   //医生id
	private String dutytime;	//日期
	private String noontype;	//上午 01      下午02 
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public String getDutytime() {
		return dutytime==null?"":this.dutytime;
	}
	public void setDutytime(String dutytime) {
		this.dutytime = dutytime;
	}
	public String getNoontype() {
		return noontype==null?"":this.noontype;
	}
	public void setNoontype(String noontype) {
		this.noontype = noontype;
	}
	
}
