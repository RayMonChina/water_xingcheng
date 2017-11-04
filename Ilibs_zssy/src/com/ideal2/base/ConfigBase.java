package com.ideal2.base;

import java.util.HashMap;
import java.util.Map;

import com.ideal2.log.ILog;

import android.content.Context;

public class ConfigBase {
	public final static String FILENAME_IDEAL_CONFIG = "ideal2_config";
	public final static String FILENAME_CLIENT_CONFIG = "client_config";
	public final static String CLIENT_URL = "client_url";
	public final static String CLIENT_XMLNAME = "client_xmlname";
	public final static String CLIENT_ERRORNODE = "error_node_name";
	public final static String IDEAL_URL = "ideal_url";
	public final static String IDEAL_XMLNAME = "ideal_xmlname";
	public final static String FILENAME_BASEDAO_CONFIG = "basedao_config";
	public final static String HTTPREQUESTPOST_TYPE = "httpRequestPostType";
	public final static String HTTPREQUESTPOST_PACKAGENAME = "HttpRequestPost_PackageName";
	/**
	 * 取得响应的错误信息
	 */
	public final static String FILENAME_CONFIG = "config";

	public final static String ERRMSG = "errMsg";

	private static ConfigBase configBase;
	private Context context;
	private final static String TAG = "ConfigBase";

	private ConfigBase() {
	};

	public static ConfigBase create(Context context) {
		if (null == configBase) {
			configBase = new ConfigBase();
			configBase.context = context;
		}
		return configBase;
	}

	private Map<String, String> configMap = new HashMap<String, String>();

	public String getConfigTest(String name) {
		if (null == configMap.get(name) || "".equals(configMap.get(name))) {
			IOConfig ioConfig = new IOConfig(context, FILENAME_CONFIG);
			if ("".equals(ioConfig.get(name))) {
				initConfig();
			}
			configMap.put(name, ioConfig.get(name));
		}
		return configMap.get(name);
	}

	public void initConfig() {
		IOConfig ioConfig = new IOConfig(context, FILENAME_CONFIG);
		ioConfig.put(ERRMSG, "Response_ErrMsg");
	}

	private Map<String, String> clientBaseMap = new HashMap<String, String>();

	public String getClientText(String name) {
		try {
			if (null == clientBaseMap.get(name)
					|| "".equals(clientBaseMap.get(name))) {
				IOConfig ioConfig = new IOConfig(context,
						FILENAME_CLIENT_CONFIG);
				if ("".equals(ioConfig.get(name))) {
					// initClient();
					ILog.d(TAG, name + "类型不存在或此参数值为空。");
				}
				clientBaseMap.put(name, ioConfig.get(name));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return clientBaseMap.get(name);
	}

	public void initClient() {
		IOConfig ioConfig = new IOConfig(context, FILENAME_CLIENT_CONFIG);
		ioConfig.put(CLIENT_URL,
				"http://192.168.0.120/YHPAD/YPPadWebService.asmx/HealthPADBus");
		ioConfig.put(CLIENT_XMLNAME, "XmlString");
		ioConfig.put(CLIENT_ERRORNODE, "Response_ErrMsg");
	}

	private Map<String, String> idealBaseMap = new HashMap<String, String>();

	public String getIdealText(String name) {
		try {
			if (null == idealBaseMap.get(name)
					|| "".equals(idealBaseMap.get(name))) {
				IOConfig ioConfig = new IOConfig(context, FILENAME_IDEAL_CONFIG);
				if ("".equals(ioConfig.get(name))) {
					initIdeal();
				}
				idealBaseMap.put(name, ioConfig.get(name));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return idealBaseMap.get(name);
	}

	public void initIdeal() {
		initBaseDao();
		IOConfig ioConfig = new IOConfig(context, FILENAME_IDEAL_CONFIG);
		ioConfig.put(IDEAL_URL,
				"http://180.168.123.186/Jsdemo/JSDemoYHWebService.asmx/HealthPADBus");
		ioConfig.put(IDEAL_XMLNAME, "XmlString");
	}

	/**
	 * 
	 * @param name
	 * @return false 为空 true 不为空
	 */
	public boolean hasClientConfig(String name) {
		boolean result;
		IOConfig ioConfig = new IOConfig(context, FILENAME_CLIENT_CONFIG);
		if ("".equals(ioConfig.get(name))) {
			result = false;
		} else {
			result = true;
		}
		return result;
	}

	/**
	 * 
	 * @param name
	 * @return false 为空 true 不为空
	 */
	public boolean hasIdealConfig(String name) {
		boolean result;
		IOConfig ioConfig = new IOConfig(context, FILENAME_IDEAL_CONFIG);
		if ("".equals(ioConfig.get(name))) {
			result = false;
		} else {
			result = true;
		}
		return result;
	}

	private Map<String, String> baseDaoMap = new HashMap<String, String>();

	public String getBaseDao(String name) {
		try {
			if (null == baseDaoMap.get(name) || "".equals(baseDaoMap.get(name))) {
				IOConfig ioConfig = new IOConfig(context,
						FILENAME_BASEDAO_CONFIG);
				if ("".equals(ioConfig.get(name))) {
					initBaseDao();
				}
				idealBaseMap.put(name, ioConfig.get(name));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return idealBaseMap.get(name);
	}

	public final static int HTTPREQUESTPOST_TYPE1 = 1;
	public final static int HTTPREQUESTPOST_TYPE2 = 2;
	public final static int HTTPREQUESTPOST_TYPE3 = 3;
	public final static int HTTPREQUESTPOST_TYPE4 = 4;

	public void setHttpRequestpost(int type) {
		IOConfig ioConfig = new IOConfig(context, FILENAME_BASEDAO_CONFIG);
		switch (type) {
		case HTTPREQUESTPOST_TYPE1:
			ioConfig.put(HTTPREQUESTPOST_PACKAGENAME,
					"com.ideal2.http.HttpRequestPost");
			ioConfig.put(HTTPREQUESTPOST_TYPE, HTTPREQUESTPOST_TYPE1 + "");
			break;
		case HTTPREQUESTPOST_TYPE2:
			ioConfig.put(HTTPREQUESTPOST_PACKAGENAME,
					"com.ideal2.http.HttpRequestPost2");
			ioConfig.put(HTTPREQUESTPOST_TYPE, HTTPREQUESTPOST_TYPE2 + "");
			break;
		case HTTPREQUESTPOST_TYPE3:
			ioConfig.put(HTTPREQUESTPOST_PACKAGENAME,
					"com.ideal2.http.HttpRequestPostWsdl");
			ioConfig.put(HTTPREQUESTPOST_TYPE, HTTPREQUESTPOST_TYPE3 + "");
			break;
		case HTTPREQUESTPOST_TYPE4:
			ioConfig.put(HTTPREQUESTPOST_PACKAGENAME,
					"com.ideal2.http.HttpRequestPostWsdl3");
			ioConfig.put(HTTPREQUESTPOST_TYPE, HTTPREQUESTPOST_TYPE4 + "");
			break;
		default:
			ioConfig.put(HTTPREQUESTPOST_PACKAGENAME,
					"com.ideal2.http.HttpRequestPost");
			ioConfig.put(HTTPREQUESTPOST_TYPE, HTTPREQUESTPOST_TYPE1 + "");
			break;
		}
	}

	public void setBaseDao(String name, String value) {
		IOConfig ioConfig = new IOConfig(context, FILENAME_BASEDAO_CONFIG);
		ioConfig.put(name, value);
	}

	public void initBaseDao() {
		setHttpRequestpost(HTTPREQUESTPOST_TYPE1);
		// IOConfig ioConfig = new IOConfig(context, FILENAME_BASEDAO_CONFIG);
		// ioConfig.put(HTTPREQUESTPOST_TYPE, "false");
		// ioConfig.put(HTTPREQUESTPOST_PACKAGENAME,
		// "com.ideal2.http.HttpRequestPost");
	}

	public void setClientNode(String name, String value) {
		IOConfig ioConfig = new IOConfig(context, FILENAME_CLIENT_CONFIG);
		ioConfig.put(name, value);
	}

	/* 设置用户地址 */
	public void setClientConfig(String url, String xmlName) {
		IOConfig ioConfig = new IOConfig(context, FILENAME_CLIENT_CONFIG);
		ioConfig.put(CLIENT_URL, url);
		ioConfig.put(CLIENT_XMLNAME, xmlName);
	}

	/* 设置ideal地址 */
	public void setIdealConfig(String url, String xmlName) {
		IOConfig ioConfig = new IOConfig(context, FILENAME_IDEAL_CONFIG);
		ioConfig.put(CLIENT_URL, url);
		ioConfig.put(CLIENT_XMLNAME, xmlName);
	}

}
