package com.ideal.zsyy.response;

import java.util.List;

import com.ideal.zsyy.entity.PhJHQueue;
import com.ideal2.base.gson.CommonRes;
/**
 * 叫号响应实体
 * @author PYM
 *
 */
public class PhJHQueueRes extends CommonRes {

	private List<PhJHQueue> jhqueue;

	public List<PhJHQueue> getJhqueue() {
		return jhqueue;
	}

	public void setJhqueue(List<PhJHQueue> jhqueue) {
		this.jhqueue = jhqueue;
	}
}
