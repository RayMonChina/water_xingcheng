package com.ideal2.dao;

import java.util.LinkedList;
import java.util.List;

import android.location.Address;

public class EditTestPromptDto {
	private String id;
	private String mark;
	private String item;
	
	
	public EditTestPromptDto(String id,String mark, String item) {
		this.id = id;
		this.mark = mark;
		this.item = item;
	}
	
	public EditTestPromptDto(String mark, String item) {
		this(null,mark,item);
	}
	
	public EditTestPromptDto(String item) {
		this(null,null,item);
	}
	
	public void addItem(String item){
//		if(null==item){
//			item = new LinkedList<String>();
//			items.add(item);
//		}else{
//			items.add(item);
//		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	
	
}
