package com.ideal2.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

//import com.ideal2.http.HttpRequestPost2.IDataResponse;

public interface IHttpRequestPost extends Runnable{
	
	public void setOnHttpResponseListener(OnHttpResponseListener onHttpResponseListener);
	/**
	 * 添加必须的参数
	 * 
	 * @param param
	 */
	public void addNameValuePair(Object param);
	
	public void setRequest_URL(String mRequest_URL);

	public void setEncoding(String mEncoding);
	

	@Override
	public void run();
	
	public interface IDataResponse {
		void dataResponse(InputStream mStream, int responseCode);
	}
	
	public interface OnHttpResponseListener{
		public void onHttpResponse(XmlNode xmlNode,InputStream mStream, int responseCode);
	}

}
