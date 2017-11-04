package com.ideal2.base;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

import com.ideal2.log.ILog;

public class CopyOfConfigBase {

	private final static String TAG = "ConfigBase";
	public final static String CFG = "cfg";

	private static CopyOfConfigBase s;
	private Map<String,IdealXml> idealXml;

	private CopyOfConfigBase() {
		
	}
	
	public static String getUrl(String idealXmlName){
		return CopyOfConfigBase.getConfig().getIdealXml(idealXmlName).getBaseDao().get("url");
	}
	public static String getXmlName(String idealXmlName){
		return CopyOfConfigBase.getConfig().getIdealXml(idealXmlName).getBaseDao().get("xmlname");
	}

	public static CopyOfConfigBase getConfig() {
		if (null == s) {
			s = new CopyOfConfigBase();
			s.init();
		}
		return s;
	}
	

	public IdealXml getIdealXml(String key) {
		return idealXml.get(key);
	}

	public void init() {
		try {
			XmlPullParser pullParser = Xml.newPullParser();
			InputStream xml = this.getClass().getClassLoader().getResourceAsStream("ideal.xml");
			pullParser.setInput(xml, "UTF-8");//为Pull解析器设置要解析的XML数据
			int event = pullParser.getEventType();
			IdealXml ix = null;
			while(event != XmlPullParser.END_DOCUMENT){
				switch (event) {
				case XmlPullParser.START_DOCUMENT:
					idealXml = new HashMap<String,IdealXml>();
					break;
					
				case XmlPullParser.START_TAG:
					if("ideal-xml".equals(pullParser.getName())){
						ix = new IdealXml();
					}else if("xml-name".equals(pullParser.getName())){
						ix.xmlName = pullParser.nextText();
					}else if("xml-type".equals(pullParser.getName())){
						ix.xmlType = pullParser.nextText();
					}else if("xml-url".equals(pullParser.getName())){
						ix.xmlUrl = pullParser.nextText();
					}
					break;
					
				case XmlPullParser.END_TAG:
					if("ideal-xml".equals(pullParser.getName())){
						idealXml.put(ix.xmlName, ix);
					}
					break;
				}
				event = pullParser.next();
			}
			
		} catch (Exception e) {
			ILog.d(TAG, e.getMessage()+""); // 若有异常，则输出异常信息
		}
	}
	
	public void idealXml(String filename,Map<String,String> map){
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance(); // 实例化一个工厂类
			DocumentBuilder builder = factory.newDocumentBuilder(); // 创建树立xml文档的对象，具体名称我也说不上，其上以上两条语句都是为处理xml文档做准备的，即进行初始化工作
			Document document = builder.parse(new File(filename)); // 导入xml文档
			Element rootElement = document.getDocumentElement(); // 得到根节点对象,即<ideal-app>
			
			Element basedao = (Element)rootElement.getElementsByTagName("basedao"); // 通过标签名"Header"得到<Header>结点对象的列表，即所有名为"Header"的结点都被保存在一个list列表对象中
			Element name = (Element)basedao.getElementsByTagName("name").item(0);
			Element url = (Element)basedao.getElementsByTagName("url").item(0);
			Element xmlname = (Element)basedao.getElementsByTagName("xmlname").item(0);
			map.put("name", name.getNodeValue());
			map.put("url", url.getNodeValue());
			map.put("xmlname", xmlname.getNodeValue());
			
			
		} catch (Exception e) {
			ILog.d(TAG,""+ e.getMessage()); // 若有异常，则输出异常信息
		}
	}

	public void destroy() {
		s = null;
		System.gc();
	}
	
	public class IdealXml{
		private String xmlName;
		private String xmlType;
		private String xmlUrl;
		private Map<String,String> baseDaoMap;
		public String getXmlName() {
			return xmlName;
		}
		public void setXmlName(String xmlName) {
			this.xmlName = xmlName;
		}
		public String getXmlType() {
			return xmlType;
		}
		public void setXmlType(String xmlType) {
			this.xmlType = xmlType;
		}
		public String getXmlUrl() {
			return xmlUrl;
		}
		public void setXmlUrl(String xmlUrl) {
			this.xmlUrl = xmlUrl;
		}
		
		public Map<String,String> getBaseDao(){
			if(null==baseDaoMap){
				try {
					XmlPullParser pullParser = Xml.newPullParser();
					InputStream xml = this.getClass().getClassLoader().getResourceAsStream(xmlUrl);
					pullParser.setInput(xml, "UTF-8");//为Pull解析器设置要解析的XML数据
					int event = pullParser.getEventType();
					while(event != XmlPullParser.END_DOCUMENT){
						switch (event) {
						case XmlPullParser.START_DOCUMENT:
							baseDaoMap = new HashMap<String,String>();
							break;
							
						case XmlPullParser.START_TAG:
							baseDaoMap.put(pullParser.getName(), pullParser.nextText());
							break;
						case XmlPullParser.END_TAG:
							break;
						}
						event = pullParser.next();
					}
					return baseDaoMap;
				} catch (Exception e) {
					ILog.d(TAG, e.getMessage()+""); // 若有异常，则输出异常信息
					return null;
				}
			}else{
				return baseDaoMap;
			}
			
		}
		
		
	}
	
	
}
