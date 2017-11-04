package com.ideal.zsyy.service;

import com.ideal.zsyy.receiver.TickReceiver;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class TickService extends Service {

	TickReceiver receiver = new TickReceiver(); 
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		IntentFilter filter = new IntentFilter(Intent.ACTION_TIME_TICK); 
		registerReceiver(receiver, filter); 
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(receiver!=null)
		{
			unregisterReceiver(receiver);
		}
	}

}
