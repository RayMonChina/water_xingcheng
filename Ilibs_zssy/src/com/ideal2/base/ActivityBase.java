package com.ideal2.base;


import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class ActivityBase extends Activity{
	
	
	public final static int DEFAULT =0;
	public final static int MAIN = 1;
	public final static int WELCOME =2;
	public final static int LOGIN = 3;
	public final static int PROGRESS = 4;
	
	
	
	private int activityType = 0;//activity¿‡–Õ
	
	 protected void onCreate(Bundle savedInstanceState) {
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
			super.onCreate(savedInstanceState);
			
	}

	public int getActivityType() {
		return activityType;
	}

	public void setActivityType(int activityType) {
		this.activityType = activityType;
	}
	 
	 


	 
}
