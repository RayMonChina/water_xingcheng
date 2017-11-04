package com.ideal.wdm.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class DataCache {
	private final static String SPF = "DATA_CACHE";
	private final static String URL = "URL";
	private final static String PWD = "PWD";
	private final static String USERID = "USERID";
	private final static String SERVERIP = "SERVERIP";
	private final static String ISLOGIN = "ISLOGIN";
	private final static String LOGINNAME = "LOGINNAME";
	private final static String FEATURESLIST = "FEATURESLIST";
	private final static String PUSH_STATE = "PUSH_STATE";
	private final static String WATER_PRICE="UNITPRICE";
	private String url;
	private String userID;
	private String pwd;
	private String serverIP;
	private boolean isLogin;
	private String loginName;
	private String featureslist;
	private int pushState = 1;
	private float waterUnitPrice;
	public int getPushState() {
		return pushState;
	}

	public void setPushState(int pushState) {
		this.pushState = pushState;
	}

	private static DataCache dCache;

	public DataCache(Context mContext) {
		SharedPreferences preferences = mContext.getSharedPreferences(SPF,
				Context.MODE_PRIVATE);

		url = preferences.getString(URL, "");
		userID = preferences.getString(USERID, "");
		pwd = preferences.getString(PWD, "");
		serverIP = preferences.getString(SERVERIP, "");
		isLogin = preferences.getBoolean(ISLOGIN, false);
		loginName = preferences.getString(LOGINNAME, "");
		featureslist = preferences.getString(FEATURESLIST, "");
		pushState=preferences.getInt(PUSH_STATE, 1);
		waterUnitPrice=preferences.getFloat(WATER_PRICE, 0);
	}

	public void commit(Context mContext) {
		SharedPreferences preferences = mContext.getSharedPreferences(SPF,
				Context.MODE_PRIVATE);
		Editor editor;
		editor = preferences.edit();
		editor.putString(URL, url);
		editor.putString(USERID, userID);
		editor.putString(PWD, pwd);
		editor.putString(SERVERIP, serverIP);
		editor.putString(LOGINNAME, loginName);
		editor.putBoolean(ISLOGIN, isLogin);
		editor.putString(FEATURESLIST, featureslist);
		editor.putInt(PUSH_STATE, pushState);
		editor.putFloat(WATER_PRICE, waterUnitPrice);
		editor.commit();
	}

	public String getFeatureslist() {
		return featureslist;
	}

	public void setFeatureslist(String featureslist) {
		this.featureslist = featureslist;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;

	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getServerIP() {
		return serverIP;
	}

	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public float getWaterUnitPrice() {
		return waterUnitPrice;
	}

	public void setWaterUnitPrice(float waterUnitPrice) {
		this.waterUnitPrice = waterUnitPrice;
	}

	public static DataCache getCache(Context mContext) {
		if (dCache == null) {
			dCache = new DataCache(mContext);
		}
		return dCache;
	}

}
