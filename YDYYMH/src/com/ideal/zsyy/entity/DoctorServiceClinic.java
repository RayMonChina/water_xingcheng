package com.ideal.zsyy.entity;

import java.util.List;

public class DoctorServiceClinic {
	private String clinic_id;
	private String clinic_name;
	private String hdept_Id;
	
	private List<DoctorServiceHdeptDate> doctorServices;

	
	public String getHdept_Id() {
		return hdept_Id;
	}

	public void setHdept_Id(String hdept_Id) {
		this.hdept_Id = hdept_Id;
	}

	public List<DoctorServiceHdeptDate> getDoctorServices() {
		return doctorServices;
	}

	public void setDoctorServices(List<DoctorServiceHdeptDate> doctorServices) {
		this.doctorServices = doctorServices;
	}

	public String getClinic_id() {
		return clinic_id;
	}

	public void setClinic_id(String clinic_id) {
		this.clinic_id = clinic_id;
	}

	public String getClinic_name() {
		return clinic_name;
	}

	public void setClinic_name(String clinic_name) {
		this.clinic_name = clinic_name;
	}
	
}
