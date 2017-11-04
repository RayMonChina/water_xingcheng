package com.ideal.zsyy.request;

import com.ideal2.base.gson.CommonReq;

public class BackVisitChatReq extends CommonReq {

	public BackVisitChatReq()
	{
		setOperType("118");
	}
	private String id_card;//身份证号
	private String sendContent;//发送内容
	private String pat_No;//住院号
	private String chatType;//1、投诉 2、咨询，3、建议
	public String getId_card() {
		return id_card;
	}
	public void setId_card(String id_card) {
		this.id_card = id_card;
	}
	public String getSendContent() {
		return sendContent;
	}
	public void setSendContent(String sendContent) {
		this.sendContent = sendContent;
	}
	public String getChatType() {
		return chatType;
	}
	public void setChatType(String chatType) {
		this.chatType = chatType;
	}
	public String getPat_No() {
		return pat_No;
	}
	public void setPat_No(String pat_No) {
		this.pat_No = pat_No;
	}
	
}
