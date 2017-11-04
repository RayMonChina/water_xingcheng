package com.ideal.zsyy.entity;

import java.util.List;

public class DoctorService {

	private List<DoctorServiceTime> times;//分时段

	public List<DoctorServiceTime> getTimes() {
		return times;
	}

	public void setTimes(List<DoctorServiceTime> times) {
		this.times = times;
	}
}
