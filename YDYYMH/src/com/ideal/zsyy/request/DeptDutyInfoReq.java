package com.ideal.zsyy.request;

import com.ideal2.base.gson.CommonReq;

public class DeptDutyInfoReq extends CommonReq {

	private String dept_no;
	private String dutytime;	//按周
	private String noontype;	//上午 01      下午02
	public String getDept_no() {
		return dept_no;
	}
	public void setDept_no(String deptNo) {
		dept_no = deptNo;
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
