package com.ideal2.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.util.Log;

import com.ideal2.http.MyXmlHandler.OnXmlEndDocumentListening;
import com.ideal2.log.ILog;

public class HttpRequestPost2 implements IHttpRequestPost {

	public static final String DRUG_LIST = "http://180.168.123.137:8404/AccountService/xml/PutXY";

	private String mRequest_URL;

	private IDataResponse iResponse;

	private String strXML;

	private List<BasicNameValuePair> mParams;

	private String mEncoding = HTTP.UTF_8;

	private OnHttpResponseListener onHttpResponseListener;
	public HttpRequestPost2(){
		super();
	}
	public HttpRequestPost2(String url) {
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
		// TODO Auto-generated method stub

//		Log.d("HttpRequestPost", mRequest_URL + " " + "kkkkkkk");
		
		HttpPost request = new HttpPost(mRequest_URL); // 根据内容来源地址创建一个Http请求
		try {
			request.setEntity(new StringEntity(strXML, mEncoding));
			request.setHeader("Content-Type", "application/xml;charset=UTF-8");
			// 设置参数的编码
			HttpResponse httpResponse = null;
			httpResponse = new DefaultHttpClient().execute(request);

			// 发送请求并获取反馈
			// 解析返回的内容
			responseCode = httpResponse.getStatusLine().getStatusCode();
			System.out.println(responseCode);
			if (responseCode == 200) {
				// result = EntityUtils.toString(httpResponse.getEntity());
				mStream = httpResponse.getEntity().getContent();

				InputSource iSource = new InputSource(mStream);
				XmlParser xParser = new XmlParser();
				xParser.setOnXmlEndDocumentListening(
						new OnXmlEndDocumentListening() {
							public void onXmlEndDocument(XmlNode xmlNode,
									int requestCode) {
								onHttpResponseListener.onHttpResponse(xmlNode,
										mStream, responseCode);
							}
						}, 1);

				XmlDriver.driverXml(iSource, xParser, HTTP.UTF_8);

			} else {
				onHttpResponseListener.onHttpResponse(null, mStream,
						responseCode);
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
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
//			ILog.d("sadasfdasd", "param:"+param.toString());
			this.strXML = param.toString().trim();
	}

}
