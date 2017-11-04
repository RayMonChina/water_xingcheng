package com.ideal2.http;

import java.util.HashMap;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import android.util.Log;

public class XmlParser extends MyXmlHandler {

	private static final String TAG = "XmlParser";
	private XmlNode maps;
	private XmlNode pointer;
	private XmlNode parent;
	private Stack<XmlNode> mStack;
	private int level = 0;
	private IXmlCallback iCallBack;
	private OnXmlEndDocumentListening onXmlEndDocumentListening;
	private boolean isElement;
	private StringBuilder mBuilder;
	private int requestCode;
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
	    if(isElement){
	//	Log.d(TAG, "characters :"+new String(ch,start,length));
		mBuilder.append(new String(ch,start,length));
		
	    }
		super.characters(ch, start, length);
	}

	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
		if (iCallBack != null) {
			iCallBack.xmlCallback(maps,requestCode);
		}
		if(null!=onXmlEndDocumentListening){
			onXmlEndDocumentListening.onXmlEndDocument(maps, requestCode);
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		super.endElement(uri, localName, qName);
	//	Log.d(TAG, "endElement");
		pointer.setElementValue(mBuilder.toString());
		isElement=false;
		if (!mStack.isEmpty()) {
			mStack.pop();
		}
		level--;
	}

	@Override
	public void startDocument() throws SAXException {
		mStack = new Stack<XmlNode>();
	//	Log.d(TAG, "startDocument");
		mBuilder=new StringBuilder();
		super.startDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		pointer = new XmlNode();
	//	Log.d(TAG, "startElement");
		mBuilder.setLength(0);
		isElement=true;
		if (maps == null) {
			maps = pointer;
		}
		HashMap<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < attributes.getLength(); i++) {
			//Log.d(TAG, "attributes.getLocalName(i):"+attributes.getLocalName(i)+" attributes.getValue(i): "+attributes.getValue(i));
			map.put(attributes.getLocalName(i),
					attributes.getValue(i));
		}
		//Log.d(TAG, "localName: "+localName+" qName:¡¡"+qName);
		pointer.setElementName(localName);
//		if (!map.containsKey("base")) {
//			map.put("base", localName.toLowerCase());
//		} else {
//			map.put("base", map.get("base").toString().toLowerCase());
//		}
		pointer.setLevel(level);
		if (!mStack.isEmpty()) {
			parent = mStack.peek();
			pointer.setFather(parent);
			
		}
		level++;
		pointer.setAttrMap(map);
		if (parent != null) {
			parent.addChild(pointer);
		}
		mStack.push(pointer);
		super.startElement(uri, localName, qName, attributes);
	}

	@Override
	public void setCallBack(IXmlCallback l,int requestCode) {
		// TODO Auto-generated method stub
	    	this.requestCode=requestCode;
		this.iCallBack=l;
	}

	public void setOnXmlEndDocumentListening(
			OnXmlEndDocumentListening onXmlEndDocumentListening,int requestCode) {
		this.requestCode=requestCode;
		this.onXmlEndDocumentListening = onXmlEndDocumentListening;
	}
	

}
