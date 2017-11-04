package com.ideal2.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.ideal2.http.MyXmlHandler.OnXmlEndDocumentListening;
import com.ideal2.log.ILog;

public class HttpRequestPost implements IHttpRequestPost {

	/**
	 * 定义需要获取的内容来源地址
	 */
	private String mRequest_URL;

	private List<BasicNameValuePair> mParams;

	private String mEncoding = HTTP.UTF_8;
	
	private OnHttpResponseListener onHttpResponseListener;

	public HttpRequestPost(){
		super();
		mParams = new ArrayList<BasicNameValuePair>();
	}
	
	public HttpRequestPost(String url) {
		super();
		this.mRequest_URL = url;
		mParams = new ArrayList<BasicNameValuePair>();
	}
	
	/**
	 * 添加必须的参数
	 * 
	 * @param param
	 */

	public void setRequest_URL(String mRequest_URL) {
		this.mRequest_URL = mRequest_URL;
	}

	public void setEncoding(String mEncoding) {
		this.mEncoding = mEncoding;
	}
	InputStream mStream = null;
	int responseCode = -1;
	@Override
	public final void run() {
		HttpPost request = new HttpPost(mRequest_URL); // 根据内容来源地址创建一个Http请求
		try {
			request.setEntity(new UrlEncodedFormEntity(mParams, mEncoding));
			// 设置参数的编码
			HttpResponse httpResponse = null;
			httpResponse = new DefaultHttpClient().execute(request);
			// 发送请求并获取反馈
			// 解析返回的内容
			responseCode = httpResponse.getStatusLine().getStatusCode();
			if (responseCode == 200) {
				// result = EntityUtils.toString(httpResponse.getEntity());
				mStream = httpResponse.getEntity().getContent();
				
				InputSource iSource = new InputSource(mStream);
				XmlParser xParser = new XmlParser();
				xParser.setOnXmlEndDocumentListening(new OnXmlEndDocumentListening() {
					
					public void onXmlEndDocument(XmlNode xmlNode, int requestCode) {
						InputSource iSource = new InputSource(new StringReader(
								xmlNode.getElementValue()));
						XmlParser xParser = new XmlParser();
						xParser.setOnXmlEndDocumentListening(new OnXmlEndDocumentListening() {
							
							@Override
							public void onXmlEndDocument(XmlNode xmlNode, int requestCode) {
								onHttpResponseListener.onHttpResponse(xmlNode, mStream, responseCode);
							}
						},2);
						try {
							XmlDriver.driverXml(iSource, xParser, HTTP.UTF_8);
						} catch (IOException e) {
							onHttpResponseListener.onHttpResponse(null, null, 500);
							e.printStackTrace();
						} catch (SAXException e) {
							onHttpResponseListener.onHttpResponse(null, null, 500);
							e.printStackTrace();
						} catch (ParserConfigurationException e) {
							onHttpResponseListener.onHttpResponse(null, null, 500);
							e.printStackTrace();
						}
					}
				},1);
				
				XmlDriver.driverXml(iSource, xParser, HTTP.UTF_8);
				
			}else{
				onHttpResponseListener.onHttpResponse(null, mStream, responseCode);
			}

		} catch (UnsupportedEncodingException e) {
			onHttpResponseListener.onHttpResponse(null, null, 500);
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			onHttpResponseListener.onHttpResponse(null, null, 500);
			e.printStackTrace();
		} catch (IllegalStateException e) {
			onHttpResponseListener.onHttpResponse(null, null, 500);
			e.printStackTrace();
		} catch (IOException e) {
			onHttpResponseListener.onHttpResponse(null, null, 500);
			e.printStackTrace();
		} catch (SAXException e) {
			onHttpResponseListener.onHttpResponse(null, null, 500);
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			onHttpResponseListener.onHttpResponse(null, null, 500);
			e.printStackTrace();
		}
		if (responseCode != 200) {
//			try {
//				Thread.sleep(500);
//			} catch (InterruptedException e1) {
//				e1.printStackTrace();
//			}
			try {
				request.setEntity(new UrlEncodedFormEntity(mParams, mEncoding));
				// 设置参数的编码
				HttpResponse httpResponse = null;
				httpResponse = new DefaultHttpClient().execute(request);
				// 发送请求并获取反馈
				// 解析返回的内容
				responseCode = httpResponse.getStatusLine().getStatusCode();
				if (responseCode == 200) {
					// result = EntityUtils.toString(httpResponse.getEntity());
					mStream = httpResponse.getEntity().getContent();
				}

			} catch (UnsupportedEncodingException e) {
				onHttpResponseListener.onHttpResponse(null, null, 500);
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				onHttpResponseListener.onHttpResponse(null, null, 500);
				e.printStackTrace();
			} catch (IllegalStateException e) {
				onHttpResponseListener.onHttpResponse(null, null, 500);
				e.printStackTrace();
			} catch (IOException e) {
				onHttpResponseListener.onHttpResponse(null, null, 500);
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void addNameValuePair(Object param) {
		if(param instanceof BasicNameValuePair ){
			mParams.add((BasicNameValuePair)param);
		}
	}

	@Override
	public void setOnHttpResponseListener(
			OnHttpResponseListener onHttpResponseListener) {
		this.onHttpResponseListener = onHttpResponseListener;
		
	}

}
