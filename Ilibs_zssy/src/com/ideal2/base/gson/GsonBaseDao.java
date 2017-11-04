package com.ideal2.base.gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ideal2.log.ILog;

public class GsonBaseDao implements Runnable{
	public final static String TAG = "GsonBaseDao";
	public final static int CONNSTATE_CREATEREQUEST = 1;
	public final static int CONNSTATE_RESPONSEING = 2;
	public final static int CONNSTATE_RESPONSEEND = 3;
	
	private String url = "";
	private int responseCode = -1;
	private InputStream mStream;
	private String mEncoding = HTTP.UTF_8;
	private List<BasicNameValuePair> mParams;
	private OnResponseEndListening onResponseEndListening;
	private ConnStateListening connStateListening;
	private HttpResponse httpResponse = null;
	private String gsonReqStr;
	
	
	public String getGsonReqStr() {
		return gsonReqStr;
	}

	public void setGsonReqStr(String gsonReqStr) {
		this.gsonReqStr = gsonReqStr;
	}

	public GsonBaseDao() {
		mParams = new ArrayList<BasicNameValuePair>();
	}

	public void doService(String gsonReqStr) {
		setGsonReqStr(gsonReqStr);
		Thread th = new Thread(this);
		th.start();
	}
	
	@Override
	public void run() {
		ILog.d(TAG, "url："+url);
		ILog.d(TAG, "请求："+gsonReqStr);
		if (null != connStateListening) {
			connStateListening.connState(CONNSTATE_CREATEREQUEST);
		}
		HttpPost request = new HttpPost(url); // 根据内容来源地址创建一个Http请求
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		try {
			mParams.add(new BasicNameValuePair("gson", gsonReqStr));
			request.setEntity(new UrlEncodedFormEntity(mParams, mEncoding));
			// 设置参数的编码
			
			BasicHttpParams httpParams = new BasicHttpParams();
		    HttpConnectionParams.setConnectionTimeout(httpParams, 30000);  
		    HttpConnectionParams.setSoTimeout(httpParams, 30000);
			DefaultHttpClient client = new DefaultHttpClient(httpParams);
			
			httpResponse = client.execute(request);
			
			
			if (null != connStateListening) {
				connStateListening.connState(CONNSTATE_RESPONSEING);
			}
			// 发送请求并获取反馈
			// 解析返回的内容
			responseCode = httpResponse.getStatusLine().getStatusCode();
			ILog.d(TAG, "responseCode:"+responseCode);
			if (responseCode == 200) {
				// result = EntityUtils.toString(httpResponse.getEntity());
				mStream = httpResponse.getEntity().getContent();
//				Log.d("Encoding", "Encoding:"
//						+ httpResponse.getEntity().getContentEncoding());
//				Log.d("ContentType", "ContentType:"
//						+ httpResponse.getEntity().getContentType());

				InputStreamReader isr = new InputStreamReader(mStream,
						mEncoding);
			
				
				if (null != onResponseEndListening) {
					onResponseEndListening.onResponseEnd(isr, true, 200,
							gsonReqStr);
				}
			}else{
				if (null != onResponseEndListening) {
					onResponseEndListening.onResponseEnd(null, false, responseCode,
							gsonReqStr);
				}
			}

		}catch (HttpHostConnectException e) {
			if (null != onResponseEndListening) {
				onResponseEndListening.onResponseEnd(null, false, 802,gsonReqStr);
			}
			e.printStackTrace();
		}catch(SocketException e){
			if (null != onResponseEndListening) {
				onResponseEndListening.onResponseEnd(null, false, 803,gsonReqStr);
			}
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			if (null != onResponseEndListening) {
				onResponseEndListening.onResponseEnd(null, false, 805,
						gsonReqStr);
			}
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			if (null != onResponseEndListening) {
				onResponseEndListening.onResponseEnd(null, false, 806,
						gsonReqStr);
			}
			e.printStackTrace();
		} catch (IllegalStateException e) {
			if (null != onResponseEndListening) {
				onResponseEndListening.onResponseEnd(null, false, 807,
						gsonReqStr);
			}
			e.printStackTrace();
		} catch (IOException e) {
			if (null != onResponseEndListening) {
				onResponseEndListening.onResponseEnd(null, false, 808,
						gsonReqStr);
			}
			e.printStackTrace();
		}catch(Exception e){
			if (null != onResponseEndListening) {
				onResponseEndListening.onResponseEnd(null, false, 809,
						gsonReqStr);
			}
		}//finally{
			if (null != connStateListening) {
				connStateListening.connState(CONNSTATE_RESPONSEEND);
			}
		//}
	}
	public void destroy() {
		httpResponse = null;
		System.gc();
	}
	public interface OnResponseEndListening {
		public void onResponseEnd(InputStreamReader isr, boolean result,
				int responseCode, String requestGson);
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
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public InputStream getmStream() {
		return mStream;
	}

	public void setmStream(InputStream mStream) {
		this.mStream = mStream;
	}

	public String getmEncoding() {
		return mEncoding;
	}

	public void setmEncoding(String mEncoding) {
		this.mEncoding = mEncoding;
	}

	public List<BasicNameValuePair> getmParams() {
		return mParams;
	}

	public void setmParams(List<BasicNameValuePair> mParams) {
		this.mParams = mParams;
	}

	

}
