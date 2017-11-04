package com.ideal2.base;

import java.io.InputStream;
import java.io.StringReader;

import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.xml.sax.InputSource;

import android.content.Context;

import com.ideal2.http.HttpRequestPost;
import com.ideal2.http.IHttpRequestPost;
import com.ideal2.http.MyXmlHandler.OnXmlEndDocumentListening;
import com.ideal2.http.WsdlParam;
import com.ideal2.http.XmlDriver;
import com.ideal2.http.XmlNode;
import com.ideal2.http.XmlParser;
import com.ideal2.http.XmlReader;
import com.ideal2.log.ILog;

/**
 * 
 * @author xufeng
 * @version 1.0 数据传输基类
 * 
 */
public class BaseDao implements IBaseDao {

	public final static String TAG = "BaseDao";
	public final static int CONNSTATE_CREATEREQUEST = 1;
	public final static int CONNSTATE_RESPONSEING = 2;
	public final static int CONNSTATE_RESPONSEEND = 3;

	private Context context;
	private String url;
	private String xmlname;
	private String classname;
	private ConfigBase configBase;
	private IHttpRequestPost hPost;
	private Thread thread;
	private XmlNode xNode;
	private OnResponseEndListening onResponseEndListening;
	private ConnStateListening connStateListening;
	private int httpRequestPostType;
	private String HttpRequestPostPackageName;

	private BaseDao() {
	}

	public BaseDao(Context context, String url, String xmlname) {
		this.context = context;
		// this.classname = getClass().getSimpleName();
		// this.configBase = ConfigBase.getConfig();
		this.url = url;
		this.xmlname = xmlname;

		try {
			ConfigBase configBase = ConfigBase.create(context);
			httpRequestPostType = Integer.parseInt(configBase
					.getBaseDao(ConfigBase.HTTPREQUESTPOST_TYPE));
			HttpRequestPostPackageName = configBase.getBaseDao(ConfigBase.HTTPREQUESTPOST_PACKAGENAME);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

	@Override
	public boolean conn(final String requestXml) {
		String str = requestXml.trim();
		if (null != connStateListening) {
			connStateListening.connState(CONNSTATE_CREATEREQUEST);
		}
		try {
			
			hPost = (IHttpRequestPost)Class.forName(HttpRequestPostPackageName).newInstance();
			ILog.d(TAG, "请求地址:" + this.url + ",xmlname:" + this.xmlname);
			hPost.setRequest_URL(this.url);

			switch (httpRequestPostType) {
				case ConfigBase.HTTPREQUESTPOST_TYPE1:
					hPost.addNameValuePair(new BasicNameValuePair(this.xmlname, requestXml));
					break;
				case ConfigBase.HTTPREQUESTPOST_TYPE2:
					hPost.addNameValuePair(requestXml);
					break;
				case ConfigBase.HTTPREQUESTPOST_TYPE3:
					hPost.addNameValuePair(new WsdlParam("HealthPADBus", this.xmlname, requestXml));
					break;
				case ConfigBase.HTTPREQUESTPOST_TYPE4:
					hPost.addNameValuePair(new WsdlParam("Expsosewebservice", this.xmlname, str));
					break;
			}
			ILog.d("TAG", "序号：" + httpRequestPostType);
			ILog.d(TAG, "请求：" + str);
			if (null != connStateListening) {
				connStateListening.connState(CONNSTATE_RESPONSEING);
			}

			hPost.setOnHttpResponseListener(new IHttpRequestPost.OnHttpResponseListener() {

				@Override
				public void onHttpResponse(XmlNode xmlNode, InputStream mStream,
						int responseCode) {
					ILog.d(TAG, "responseCode:" + responseCode);
					if (responseCode == 200) {
						xNode = xmlNode;
						ILog.d(TAG, "响应:" + (new XmlReader(xNode)).toXml());
						if (null != onResponseEndListening) {
							onResponseEndListening.onResponseEnd(xNode, true, 200,
									"");
						}
					} else {
						if (null != onResponseEndListening) {
							ILog.d(TAG, "响应:" + (new XmlReader(xNode)).toXml());
							onResponseEndListening.onResponseEnd(xNode, false,
									responseCode, requestXml);
						}
					}
					if (null != connStateListening) {
						connStateListening.connState(CONNSTATE_RESPONSEEND);
					}

				}
			});
			thread = new Thread(hPost);
			ThreadPoolFactory.tpf().exe(thread);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return true;
	}

	@Override
	public boolean hasConn() {
		if ("".equals(this.url)) {
			return false;
		}
		return true;
	}

	@Override
	public boolean createConn(String url, String xmlname) {
		this.url = url;
		this.xmlname = xmlname;
		return false;
	}

	@Override
	public void destroy() {
		thread = null;
		if (null != hPost) {
//			hPost.setResponse(null);
		}
		System.gc();
	}


	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	protected String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	protected String getXmlname() {
		return xmlname;
	}

	protected void setXmlname(String xmlname) {
		this.xmlname = xmlname;
	}


	public interface OnResponseEndListening {
		public void onResponseEnd(XmlNode xNode, boolean result,
				int responseCode, String requestXml);
	}
	public void setOnResponseEndListening(
			OnResponseEndListening onResponseEndListening) {
		this.onResponseEndListening = onResponseEndListening;
	}

	public interface ConnStateListening {
		public boolean connState(int connstate);
	}

	public void setConnStateListening(ConnStateListening connStateListening) {
		this.connStateListening = connStateListening;
	}
}
