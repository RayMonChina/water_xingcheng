package com.ideal2.base;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class IOConfig {
	
	
	
	private Context context;
	private SharedPreferences preferences;
	
	public IOConfig(Context context,String xmlUrl) {
		this.context = context;
		preferences = this.context.getSharedPreferences(xmlUrl, Context.MODE_PRIVATE);
	}
	
	public void put(String key,String value){
		Editor editor = preferences.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	public String get(String key){
		return preferences.getString(key, "");
	}

	public SharedPreferences getPreferences() {
		return preferences;
	}
	
	
}
