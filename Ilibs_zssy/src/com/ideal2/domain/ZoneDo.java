package com.ideal2.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ideal2.base.IDomainObject;
import com.ideal2.base.SetConfig;
import com.ideal2.entity.ZoneDto;
import com.ideal2.http.XmlNode;

public class ZoneDo implements IDomainObject{
	private String request_OrgCode;
	private String request_OperType;
	private String user;
	private String user_ID;
	private String group;
	private String group_ID;
	private String response_ErrMsg;
	private SetConfig listconf;
	private List<ZoneDto> listZones;
	public List<ZoneDto> getListZones() {
		return this.listZones;
	}
	public void setListZones(List<ZoneDto> listZones) {
		this.listZones = listZones;
	}
	private Map<String, Object> map;
	public Map<String, Object> getMap() {
		return this.map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	public ZoneDo(){
		map = new HashMap<String, Object>();
		listconf = new SetConfig(1, ZoneDto.class, "listZones");
		listZones = new ArrayList<ZoneDto>();
		map.put("Zones", listconf);
		map.put("Request_OrgCode","Request_OrgCode");
		map.put("Request_OperType","Request_OperType");
		map.put("User","User");
		map.put("User_ID","User_ID");
		map.put("Group","Group");
		map.put("Group_ID","Group_ID");
		map.put("Response_ErrMsg","Response_ErrMsg");
	}
	public XmlNode requestXml(){
		XmlNode xnBody = new XmlNode();
		XmlNode xnRequest = new XmlNode();
		xnRequest.setElementName("Request");
		xnBody.setElementName("Body");
		xnRequest.addAttr("OrgCode", this.request_OrgCode);
		xnRequest.addAttr("OperType", this.request_OperType);
		xnBody.addChild(xnRequest);
		XmlNode xnUser1 = new XmlNode();
		xnUser1.setElementName("User");
		xnUser1.setElementValue(this.user);
		xnUser1.addAttr("ID", this.user_ID);
		xnRequest.addChild(xnUser1);
		XmlNode xnGroup2 = new XmlNode();
		xnGroup2.setElementName("Group");
		xnGroup2.setElementValue(this.group);
		xnGroup2.addAttr("ID", this.group_ID);
		xnRequest.addChild(xnGroup2);
		return xnBody;
	}
	public String getResponse_ErrMsg() {
		return this.response_ErrMsg;
	}
	public void setResponse_ErrMsg(String response_ErrMsg) {
		this.response_ErrMsg = response_ErrMsg;
	}
	public String getRequest_OrgCode() {
		return this.request_OrgCode;
	}
	public void setRequest_OrgCode(String request_OrgCode) {
		this.request_OrgCode = request_OrgCode;
	}
	public String getRequest_OperType() {
		return this.request_OperType;
	}
	public void setRequest_OperType(String request_OperType) {
		this.request_OperType = request_OperType;
	}
	public String getUser() {
		return this.user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getUser_ID() {
		return this.user_ID;
	}
	public void setUser_ID(String user_ID) {
		this.user_ID = user_ID;
	}
	public String getGroup() {
		return this.group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getGroup_ID() {
		return this.group_ID;
	}
	public void setGroup_ID(String group_ID) {
		this.group_ID = group_ID;
	}
}
