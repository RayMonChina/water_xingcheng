package com.ideal.wdm.tools;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferencesService {

	private Context context;

	public PreferencesService(Context context) {
		this.context = context;
	}

	public void saveLoginPre(String loginName, String pwd, boolean isLogin,
			String userId, String ip) {
		SharedPreferences preferences = context.getSharedPreferences("sm",
				Context.MODE_PRIVATE);
		Editor editor = preferences.edit();

		editor.putString("loginName", loginName);
		editor.putString("pwd", pwd);
		editor.putBoolean("isLogin", isLogin);
		editor.putString("userId", userId);
		editor.putString("ip", ip);
		editor.commit();
	}
	public void saveLoginPre(String loginName, String pwd, boolean isLogin,
			String userId, String ip,String url) {
		SharedPreferences preferences = context.getSharedPreferences("sm",
				Context.MODE_PRIVATE);
		Editor editor = preferences.edit();

		editor.putString("loginName", loginName);
		editor.putString("pwd", pwd);
		editor.putBoolean("isLogin", isLogin);
		editor.putString("userId", userId);
		editor.putString("ip", ip);
		editor.putString("url", url);
		editor.commit();
	}
	public void saveLoginPre(String loginName, String pwd, boolean isLogin,
			String userId) {
		SharedPreferences preferences = context.getSharedPreferences("sm",
				Context.MODE_PRIVATE);
		Editor editor = preferences.edit();

		editor.putString("loginName", loginName);
		editor.putString("pwd", pwd);
		editor.putBoolean("isLogin", isLogin);
		editor.putString("userId", userId);
		editor.commit();
	}

	public Map<String, Object> getLoginPre() {
		Map<String, Object> params = new HashMap<String, Object>();
		SharedPreferences preferences = context.getSharedPreferences("sm",
				Context.MODE_PRIVATE);
		params.put("loginName", preferences.getString("loginName", ""));
		params.put("isLogin", preferences.getBoolean("isLogin", true));
		params.put("pwd", preferences.getString("pwd", ""));
		params.put("userId", preferences.getString("userId", ""));
		params.put("ip", preferences.getString("ip", ""));
		params.put("url", preferences.getString("url", ""));
		return params;
	}

	public void clearLogin() {
		SharedPreferences preferences = context.getSharedPreferences("sm",
				Context.MODE_PRIVATE);
		Editor editor = preferences.edit();

//		editor.putString("loginName", loginName);
//		editor.putString("pwd", pwd);
		editor.putBoolean("isLogin", false);
//		editor.putString("userId", userId);
		editor.commit();
	}
	
	
}
