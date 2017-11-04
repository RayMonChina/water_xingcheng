package com.ideal2.http;

import org.xml.sax.helpers.DefaultHandler;

import android.R.integer;

public abstract class MyXmlHandler extends DefaultHandler  {
	
	public abstract void setCallBack(IXmlCallback l,int requestCode);
	
	@Deprecated
	public interface IXmlCallback{
		public void xmlCallback(Object object,int requestCode);
	}
	
	public interface OnXmlEndDocumentListening{
		public void onXmlEndDocument(XmlNode xmlNode,int requestCode);
	}

}
