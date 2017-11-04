package com.ideal2.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

import com.ideal2.http.MyXmlHandler.OnXmlEndDocumentListening;
import com.ideal2.log.ILog;

public class HttpRequestPostWsdl3 implements IHttpRequestPost {

	private String mRequest_URL;

	private String strXML;

	private String mEncoding = HTTP.UTF_8;

	private WsdlParam wsdlParam;

	private OnHttpResponseListener onHttpResponseListener;

	public HttpRequestPostWsdl3() {
		super();
	}

	public HttpRequestPostWsdl3(String url) {
		super();
		this.mRequest_URL = url;
	}

	public void setRequest_URL(String mRequest_URL) {
		this.mRequest_URL = mRequest_URL;
	}

	public void setEncoding(String mEncoding) {
		this.mEncoding = mEncoding;
	}

	InputStream mStream = null;
	int responseCode = -1;

	@Override
	public void run() {
		SoapObject request = new SoapObject("http://shlx.com/", wsdlParam.getMethodName());
		request.addProperty("arg0",wsdlParam.getRequestValue());
		SoapSerializationEnvelope se = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		se.setOutputSoapObject(request);
		(new MarshalBase64()).register(se);
		HttpTransportSE ht = new HttpTransportSE(mRequest_URL);
		ht.debug = true;
		try {
			ht.call(null, se);
			if (se.implicitTypes) {
				SoapPrimitive resultsRequestSOAP = (SoapPrimitive) se
						.getResponse();
				if(null==resultsRequestSOAP){
					onHttpResponseListener.onHttpResponse(null,
							mStream, 204);
				}else{
					InputSource iSource = new InputSource(new StringReader(
							resultsRequestSOAP.toString()));
					XmlParser xParser = new XmlParser();
					xParser.setOnXmlEndDocumentListening(
							new OnXmlEndDocumentListening() {

								@Override
								public void onXmlEndDocument(XmlNode xmlNode,
										int requestCode) {
									
									onHttpResponseListener.onHttpResponse(xmlNode,
											mStream, 200);
								}
							}, 1);
					XmlDriver.driverXml(iSource, xParser, HTTP.UTF_8);
				}
				
			} else {
				onHttpResponseListener.onHttpResponse(null,
						mStream, 500);
			}

		}catch (ConnectException e) {
			onHttpResponseListener.onHttpResponse(null,
					mStream, -1);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setOnHttpResponseListener(
			OnHttpResponseListener onHttpResponseListener) {
		this.onHttpResponseListener = onHttpResponseListener;

	}

	@Override
	public void addNameValuePair(Object param) {
		if (param instanceof WsdlParam) {
			this.wsdlParam = (WsdlParam) param;
		}

	}

}
