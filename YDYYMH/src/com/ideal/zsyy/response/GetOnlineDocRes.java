package com.ideal.zsyy.response;

import java.util.List;

import com.ideal.zsyy.entity.OnlineDoctor;
import com.ideal2.base.gson.CommonRes;

//����ֵ��ҽ��
public class GetOnlineDocRes extends CommonRes{

	private List<OnlineDoctor> doctorList;

	public List<OnlineDoctor> getDoctorList() {
		return doctorList;
	}

	public void setDoctorList(List<OnlineDoctor> doctorList) {
		this.doctorList = doctorList;
	}
}
