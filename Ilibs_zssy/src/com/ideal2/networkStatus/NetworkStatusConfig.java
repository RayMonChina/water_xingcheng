package com.ideal2.networkStatus;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.ideal2.base.ConfigBase;
import com.ideal2.base.IOConfig;
import com.ideal2.log.ILog;

public class NetworkStatusConfig {
	
	public final static String TAG = "NetworkStatusConfig";
	public final static String FILENAME_NETWORKSTATUSCONFIG = "networkStatusConfig";
	public final static String WIFI_CLIENT_URL = "wifi_client_url";
	public final static String WIFI_CLIENT_XMLNAME = "wifi_client_xmlname";
	public final static String GPRS_CLIENT_URL = "gprs_client_url";
	public final static String GPRS_CLIENT_XMLNAME = "gprs_client_xmlname";
	public final static String WIFI_IDEAL_URL = "wifi_ideal_url";
	public final static String WIFI_IDEAL_XMLNAME = "wifi_ideal_xmlname";
	public final static String GPRS_IDEAL_URL = "gprs_ideal_url";
	public final static String GPRS_IDEAL_XMLNAME = "gprs_ideal_xmlname";
	public final static String NETWORKSTATUS = "network_status";
	
	
	private static NetworkStatusConfig networkStatusConfig;
	private Context context;
	
	private NetworkStatusConfig(){};
	
	public static NetworkStatusConfig create(Context context){
		if(null==networkStatusConfig){
			networkStatusConfig= new NetworkStatusConfig();
		}
		networkStatusConfig.setContext(context);
		return networkStatusConfig;
	}
	
	private Map<String,String> configMap = new HashMap<String,String>();
	public String getNetworkConfig(String name){
//		ILog.d(TAG, "configMap.get(name):"+configMap.get(name));
		if(null==configMap.get(name)||"".equals(configMap.get(name))){
			IOConfig ioConfig = new IOConfig(context, FILENAME_NETWORKSTATUSCONFIG);
//			ILog.d(TAG, "ioConfig.get(name):"+ioConfig.get(name));
//			if("".equals(ioConfig.get(name))){
//				initNetworkConfig();
//			}
			configMap.put(name, ioConfig.get(name));
		}
		return configMap.get(name);
	}
	public void initNetworkConfig(){
		IOConfig ioConfig = new IOConfig(context, FILENAME_NETWORKSTATUSCONFIG);
		
	}
	
	public void setNetworkConfig(String name,String value){
		IOConfig ioConfig = new IOConfig(context, FILENAME_NETWORKSTATUSCONFIG);
		ioConfig.put(name, value);
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}
	
	
}
