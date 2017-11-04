package com.ideal.zsyy.receiver;

import com.ideal.zsyy.service.LocationService;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class TickReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		boolean isServiceRunning = false;
		if (intent.getAction().equalsIgnoreCase(Intent.ACTION_TIME_TICK)) {
			ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
			for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
				if ("com.ideal.zsyy.service.LocationService".equals(service.service.getClassName())) {
					isServiceRunning = true;
				}
			}
			if (!isServiceRunning) {
				Intent i = new Intent(context, LocationService.class);
				context.startService(i);
			}
		}

	}
	
	

}
