package com.ideal.zsyy.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WBBList {

	public List<String> GetBBList() {
		List<String> bbList = new ArrayList<String>();
		bbList.add("10005");
		bbList.add("10007");
		bbList.add("10013");
		bbList.add("10028");
		bbList.add("10118");
		return bbList;
	}
	public List<String> GetBBList3() {
		List<String> bbList = new ArrayList<String>();
		bbList.add("10005");
		bbList.add("10007");
		bbList.add("10013");
		return bbList;
	}

	public Map<String,WDownLoadData> getDownLoadDatas() {
		Map<String,WDownLoadData> wDatas = new HashMap<String, WDownLoadData>();
		WDownLoadData data = new WDownLoadData();
		data.setBbh("10005");
		data.setCber("王强");
		data.setCbmonth("11月");
		data.setCbTotle("536");
		wDatas.put("10005",data);
		
		data = new WDownLoadData();
		data.setBbh("10007");
		data.setCber("李可");
		data.setCbmonth("11月");
		data.setCbTotle("546");
		wDatas.put("10007",data);
		
		data = new WDownLoadData();
		data.setBbh("10013");
		data.setCber("孙新");
		data.setCbmonth("11月");
		data.setCbTotle("578");
		wDatas.put("10013",data);
		
		data = new WDownLoadData();
		data.setBbh("10028");
		data.setCber("赵哲");
		data.setCbmonth("11月");
		data.setCbTotle("496");
		wDatas.put("10028",data);
		
		data = new WDownLoadData();
		data.setBbh("10118");
		data.setCber("沉珂");
		data.setCbmonth("11月");
		data.setCbTotle("525");
		wDatas.put("10118",data);
		return wDatas;
	}

	public Map<String,WAnalysisItem> getAnalysis()
	{
		Map<String,WAnalysisItem> wDatas = new HashMap<String, WAnalysisItem>();
		WAnalysisItem data = new WAnalysisItem();
		data.setTotle("536");
		data.setYcb("1");
		data.setWcb("535");
		data.setSbgz("0");
		data.setZsl("420");
		data.setZfy("2100");
		wDatas.put("10005",data);
		
		data = new WAnalysisItem();
		data.setTotle("524");
		data.setYcb("2");
		data.setWcb("522");
		data.setSbgz("1");
		data.setZsl("210");
		data.setZfy("1050");
		wDatas.put("10007",data);
		
		data = new WAnalysisItem();
		data.setTotle("400");
		data.setYcb("10");
		data.setWcb("390");
		data.setSbgz("10");
		data.setZsl("500");
		data.setZfy("2500");
		wDatas.put("10013",data);
		
		data = new WAnalysisItem();
		data.setTotle("320");
		data.setYcb("20");
		data.setWcb("300");
		data.setSbgz("10");
		data.setZsl("300");
		data.setZfy("1500");
		wDatas.put("10028",data);
		
		data = new WAnalysisItem();
		data.setTotle("410");
		data.setYcb("30");
		data.setWcb("380");
		data.setSbgz("20");
		data.setZsl("350");
		data.setZfy("1750");
		wDatas.put("10118",data);
		
		return wDatas;
	}

	public List<WUserItem>getUserItems()
	{
		List<WUserItem>uList=new ArrayList<WUserItem>();
		WUserItem uItem=new WUserItem();
		uItem.setYhbm("101007");
		uItem.setXm("地税局");
		uItem.setDh("32783278");
		uItem.setDz("民生东路");
		uItem.setSyzd(10565);
		uItem.setByzd(0);
		uItem.setSl(0);
		uItem.setBbh("10005");
		uList.add(uItem);
		
		uItem=new WUserItem();
		uItem.setYhbm("101008");
		uItem.setXm("国税局");
		uItem.setDh("3728327");
		uItem.setDz("人民东路");
		uItem.setSyzd(11783);
		uItem.setByzd(0);
		uItem.setSl(110);
		uItem.setBbh("10007");
		uList.add(uItem);
		
		uItem=new WUserItem();
		uItem.setYhbm("101260");
		uItem.setXm("文化广场门面");
		uItem.setDh("7599111");
		uItem.setDz("原宜章电影院");
		uItem.setSyzd(10679);
		uItem.setByzd(0);
		uItem.setSl(0);
		uItem.setBbh("10013");
		uList.add(uItem);
		
		uItem=new WUserItem();
		uItem.setYhbm("101261");
		uItem.setXm("文化广场家属楼");
		uItem.setDh("7599111");
		uItem.setDz("民生东路");
		uItem.setSyzd(2603);
		uItem.setByzd(0);
		uItem.setSl(0);
		uItem.setBbh("10028");
		uList.add(uItem);
		return uList;
	}
}
