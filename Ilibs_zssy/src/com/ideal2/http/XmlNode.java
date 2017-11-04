package com.ideal2.http;

import java.util.ArrayList;
import java.util.HashMap;

import android.text.TextUtils;
import android.util.Log;

public class XmlNode {

	private static final String TAG = "XmlNode";
	private final boolean notForeach = false;
	private String mElementName;
	private String mElementValue="";
	public boolean isNotForeach() {
		return notForeach;
	}

	private int mLevel;
	private HashMap<String, String> attrMap;
	private ArrayList<XmlNode> mChildNodes;
	private XmlNode father;
	private int mChildNum=0;

	
	public void setLevel(int mLevel) {
		this.mLevel = mLevel;
	}

	public String getElementName() {
		return mElementName;
	}
	
	public void setElement(String tag,String val){
		this.mElementName=tag;
		this.mElementValue=val;
	}
	
	public void  addAttr(String attrName,String val) {
		attrMap.put(attrName, val);
	}
	
	public void setElementName(String mElementName) {
		this.mElementName = mElementName;
	}

	public String getElementValue(){
		if(null==mElementValue){
			return "";
		}else{
			return mElementValue;
		}
		
	}

	public void setElementValue(String mElementValue) {
		this.mElementValue = mElementValue;
	}

	
	public XmlNode getFather() {
		return father;
	}

	public void setFather(XmlNode father) {
		this.father = father;
	}

	public HashMap<String, String> getMap() {
		return attrMap;
	}

	public void setAttrMap(HashMap<String, String> map) {
		this.attrMap = map;
	}

	public XmlNode() {
		this.mChildNodes = new ArrayList<XmlNode>();
		attrMap=new HashMap<String, String>();
	}

	public void addChild(XmlNode child) {
		mChildNodes.add(child);
		mChildNum = mChildNum+1;
	}

	public boolean hasChild() {
		return mChildNum>0;
	}

	public XmlNode getChildNode(int index) {
		if (index < mChildNodes.size()) {
			return mChildNodes.get(index);
		} else {
			return new XmlNode();
		}
	}

	public ArrayList<XmlNode> getChildNodes() {
		return mChildNodes;
	}

	public String getString(String key) {
		if (attrMap == null) {
			return null;
		}
		return attrMap.get(key);
	}

	public int getLevel() {
		return mLevel;
	}

	public int getChildren() {
		if (mChildNodes != null) {
			return mChildNodes.size();
		} else {
			return 0;
		}
	}

	public int getInt(String key) {
		if (attrMap == null) {
			return Integer.MIN_VALUE;
		}
		if (TextUtils.isEmpty(attrMap.get(key))) {
			return Integer.MIN_VALUE;
		}
		return Integer.parseInt(attrMap.get(key));
	}

	public boolean getBoolean(String key) {
		if (attrMap == null) {
			return false;
		}
		if (TextUtils.isEmpty(attrMap.get(key))) {
			return false;
		}
		return Boolean.parseBoolean(attrMap.get(key));
	}

	public int size() {
		if (mChildNum>0) {
			int size = 0;
			for (XmlNode xNode : mChildNodes) {
				size = size + xNode.size();
			}
			return size;
		} else {
			return 1;
		}
	}

	

	public void collectMaps(ArrayList<XmlNode> xNodes) {
		if (notForeach) {
			return;
		} else {
			xNodes.add(this);
			if (mChildNum>0) {
				for (XmlNode xNode : mChildNodes) {
					xNode.collectMaps(xNodes);
				}
			}
		}
	}

	public void getNodes(int level, ArrayList<XmlNode> fNodes) {
		if (this.mLevel == level) {
			fNodes.add(this);
		} else if (mChildNum>0) {
			for (XmlNode item : mChildNodes) {
				item.getNodes(level, fNodes);
			}
		}

	}
	
	
}
