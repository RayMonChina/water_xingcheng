package com.ideal.zsyy.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.DefaultedHttpParams;
import org.apache.http.protocol.HTTP;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ideal.wdm.tools.DataCache;
import com.ideal2.base.gson.CommonReq;
import com.ideal2.base.gson.CommonRes;
import com.ideal2.log.ILog;

public class HttpTask<REQ extends CommonReq,RES extends CommonRes> {

	private Timer timer;
	private REQ req;
	private RES res;
	private Context mContext;
	private OnResponseListening<REQ,RES> onResponseListening;


	public HttpTask(Context context) {
		timer=new Timer();
		mContext=context;
		
	}
	
	
	
	public void start(final REQ req,final Class resClass)
	{
	 GsonBuilder builder=new GsonBuilder();
     final Gson gson=builder.create(); 
     DataCache dataCache=DataCache.getCache(mContext);
     final String url=dataCache.getUrl() + "";
     timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				
				try {
					String gsonStr=gson.toJson(req);
					ILog.d("HttpTask", "url："+url);
					ILog.d("HttpTask", "请求："+gsonStr);
					
					
					HttpPost post=new HttpPost(url);
					List<BasicNameValuePair> values=new ArrayList<BasicNameValuePair>();
					values.add(new BasicNameValuePair("gson", gsonStr));
					values.add(new BasicNameValuePair("type",req.getOperType()));
					post.setEntity(new UrlEncodedFormEntity(values,HTTP.UTF_8));
					
					
					HttpResponse httpResponse=new DefaultHttpClient().execute(post);
					int responsecode=httpResponse.getStatusLine().getStatusCode();
					if(responsecode==200)
					{
						InputStream mStream=httpResponse.getEntity().getContent();
						InputStreamReader isr=new InputStreamReader(mStream);
						RES res=(RES)gson.fromJson(isr, resClass);
						ILog.d("HttpTask", "gson->" + gson.toJson(res));
						Message msg=new Message();
						msg.obj=res;
						msg.what=1;
						handler.sendMessage(msg);
						mStream.close();
						isr.close();
					}
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
	             
				
				
			}
		}, 1000, 8000);
	}
	
	
	private Handler handler=new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			
			switch(msg.what)
			{
			   case 1:
				   res=(RES) msg.obj;
				   onResponseListening.onResponseSuccess(res);
				   break;
			}
		};
		
	};
	public void stop()
	{
		
	 if(timer!=null)
	 {
		 timer.cancel();
	 }
		
	}
	public interface OnResponseListening<REQ extends CommonReq, RES extends CommonRes>
	{

		public void onResponseSuccess(RES commonRes);
	}
	public OnResponseListening<REQ, RES> getOnResponseListening() {
		return onResponseListening;
	}



	public void setOnResponseListening(
			OnResponseListening<REQ, RES> onResponseListening) {
		this.onResponseListening = onResponseListening;
	}

}
