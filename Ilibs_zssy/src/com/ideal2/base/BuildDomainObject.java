package com.ideal2.base;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ideal2.http.XmlNode;
import com.ideal2.http.XmlReader;
import com.ideal2.log.ILog;

/**
 * 
 * @author xufeng
 * 根据XML构建具体的domainObject
 *
 */
public class BuildDomainObject<DO extends IDomainObject> {
	
	private final static String TAG = "BuildDomainObject";
	private DO domainObject;
	private String errMsg;
	private String errMsgNodeName;
	
	private BuildDomainObject(){};
	
	public BuildDomainObject(DO domainObject){
		this.domainObject = domainObject;
		
	}
	
	public void foreachNode(XmlNode xmlNode, DO domainObj) {
		if(null==xmlNode){
			return;
		}
		if (xmlNode.hasChild()) {
			for (XmlNode node : xmlNode.getChildNodes()) {
				XmlReader xReader = new XmlReader(node);
				String elementName = node.getElementName().trim();
				String elementValue = "";
				if (null != node.getElementValue()) {
					elementValue = node.getElementValue().trim();
				}
				boolean isForeach = this.set(elementName, elementValue, node,
						domainObj);
				HashMap<String, String> attrMap = node.getMap();

				if (null != attrMap) {
					for (String arrname : attrMap.keySet()) {
						String key = elementName + "_" + arrname;
						String value = attrMap.get(arrname);
						this.set(key, value, node, domainObj);
					}
				}
				if (isForeach) {
					foreachNode(node, null);
				}
			}
		}
	}

	public boolean set(String xmlName, String xmlValue, XmlNode xmlNode,
			DO domainObj) {
//		ILog.d(TAG, "执行xmlName:"+xmlName+",xmlValue:"+xmlValue);
		//取得响应的错误信息
		if(xmlName.equals(errMsgNodeName)){
//			ILog.d(TAG, "执行errMsgNodeName进去："+errMsgNodeName);
			errMsg = xmlValue;
		}
		
//		if(xmlName.equals("Response_ErrMsg")){
////			ILog.d(TAG, "执行errMsgNodeName进去："+errMsgNodeName);
//			errMsg = xmlValue;
//		}
		
		xmlName = xmlName.trim();
		DO dObj = null;
		if (null == domainObj) {
			dObj = domainObject;
		} else {
			dObj = domainObj;
		}
		Map<String, Object> map = dObj.getMap();
		Object attribute = map.get(xmlName);
		if (null != map && null != attribute) {
			if (attribute instanceof String) {
				String key = (attribute + "").trim();
				key = key.substring(0, 1).toUpperCase() + key.substring(1);
				setter(dObj, key, xmlValue, String.class);
			} else if (attribute instanceof SetConfig) {
				SetConfig setConfig = (SetConfig) attribute;
				switch (setConfig.getType()) {
				case 1:
					Class classname = setConfig.getClassName();
					String parentObjName = setConfig.getParentObjName();
					parentObjName = parentObjName.substring(0, 1).toUpperCase()
							+ parentObjName.substring(1);
					try {
						DO obj = (DO) classname
								.newInstance();
						foreachNode(xmlNode, obj);
						List list = (ArrayList) getter(dObj, parentObjName);
						if (null != list) {
							list.add(obj);
						}
						return false;
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InstantiationException e) {
						e.printStackTrace();
					}
					break;
				case 2:
					Class classname2 = setConfig.getClassName();
					String parentObjName2 = setConfig.getParentObjName();
					parentObjName2 = parentObjName2.substring(0, 1)
							.toUpperCase() + parentObjName2.substring(1);
					try {
						DO obj = (DO) classname2
								.newInstance();
						foreachNode(xmlNode, obj);
						this.setter(dObj, parentObjName2, obj, classname2);
						return false;
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 3:
					int num = setConfig.getNum();
					XmlNode xnRes = new XmlNode();
					xnRes.setElementName("Response");

					if (xmlNode.hasChild()) {
						int i = 1;
						XmlNode xnObj = null;
						xnObj = new XmlNode();
						xnObj.setElementName("Obj");
						xnRes.addChild(xnObj);
						for (XmlNode node : xmlNode.getChildNodes()) {
							if (i>num&&i % num == 0) {
								xnObj = new XmlNode();
								xnObj.setElementName("Obj");
								xnRes.addChild(xnObj);
							}
							if (null != xnObj) {
								xnObj.addChild(node);
							}
							i++;
						}
					}

					foreachNode(xnRes, null);
					return false;
				default:
					break;
				}
			}
		} else {
//			xmlName = xmlName.substring(0, 1).toUpperCase()
//					+ xmlName.substring(1).toLowerCase();
//			setter(this.domainObject, xmlName, xmlValue, String.class);
		}
		return true;
	}
	
	
	/** * @param obj * 操作的对象 * @param att * 操作的属性 * */
	public static Object getter(Object obj, String att) {
		try {
			Method method = obj.getClass().getMethod("get" + att);
			return method.invoke(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void setter(Object obj, String att, Object value,
			Class<?> type) {
		try {
			Method method = obj.getClass().getMethod("set" + att, type);
			method.invoke(obj, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getErrMsgNodeName() {
		return errMsgNodeName;
	}

	public void setErrMsgNodeName(String errMsgNodeName) {
		this.errMsgNodeName = errMsgNodeName;
	}

}
