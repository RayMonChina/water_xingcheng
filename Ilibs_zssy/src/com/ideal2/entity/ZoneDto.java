package com.ideal2.entity;

import java.util.HashMap;
import java.util.Map;

import com.ideal2.base.IDomainObject;
import com.ideal2.http.XmlNode;

public class ZoneDto implements IDomainObject{
	private String zone;
	private String zone_ID;
	private String dutyDoctor;
	private String dutyDoctor_ID;
	private Map<String, Object> map;
	public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	public ZoneDto(){
		map = new HashMap<String, Object>();
		map.put("Zone","Zone");
		map.put("Zone_ID","Zone_ID");
		map.put("DutyDoctor","DutyDoctor");
		map.put("DutyDoctor_ID","DutyDoctor_ID");
	}
	public XmlNode requestXml(){return null;}
	public String getZone() {
		return this.zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public String getZone_ID() {
		return this.zone_ID;
	}
	public void setZone_ID(String zone_ID) {
		this.zone_ID = zone_ID;
	}
	public String getDutyDoctor() {
		return this.dutyDoctor;
	}
	public void setDutyDoctor(String dutyDoctor) {
		this.dutyDoctor = dutyDoctor;
	}
	public String getDutyDoctor_ID() {
		return this.dutyDoctor_ID;
	}
	public void setDutyDoctor_ID(String dutyDoctor_ID) {
		this.dutyDoctor_ID = dutyDoctor_ID;
	}
}
