package com.ideal.zsyy.response;

import java.util.List;

import com.ideal.zsyy.entity.JKXJLBInfo;
import com.ideal2.base.gson.CommonRes;


public class JKXJLBRes extends CommonRes {

	private List<JKXJLBInfo> jkxjlbinfos;

	public List<JKXJLBInfo> getJkxjlbinfos() {
		return jkxjlbinfos;
	}

	public void setJkxjlbinfos(List<JKXJLBInfo> jkxjlbinfos) {
		this.jkxjlbinfos = jkxjlbinfos;
	}
}
