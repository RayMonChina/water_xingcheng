package com.ideal2.http;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class XmlDriver {

	public static void driverXml(InputSource mSource, MyXmlHandler mHandelr,
			String encoding) throws IOException, SAXException, ParserConfigurationException  {

		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp;
		
			
				sp = spf.newSAXParser();
				XMLReader xr;
				xr = sp.getXMLReader();
				mSource.setEncoding(encoding);
				xr.setContentHandler(mHandelr);
				xr.parse(mSource);
		
		
		

	}

}
