package com.ideal.zsyy.entity;

public class ComplainInfo {

	private String id;
	private String patNo;//
	private String complaintTime;//投诉，建议时间
	private String complaintContent;//投诉建议内容
	private String replayTime;//回复 时间
	private String replayContent;//回复内容
	private String type;//类型 1、投诉 2、咨询 3、建议
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPatNo() {
		return patNo;
	}
	public void setPatNo(String patNo) {
		this.patNo = patNo;
	}
	public String getComplaintTime() {
		return complaintTime;
	}
	public void setComplaintTime(String complaintTime) {
		this.complaintTime = complaintTime;
	}
	public String getComplaintContent() {
		return complaintContent;
	}
	public void setComplaintContent(String complaintContent) {
		this.complaintContent = complaintContent;
	}
	public String getReplayTime() {
		return replayTime;
	}
	public void setReplayTime(String replayTime) {
		this.replayTime = replayTime;
	}
	public String getReplayContent() {
		return replayContent;
	}
	public void setReplayContent(String replayContent) {
		this.replayContent = replayContent;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
