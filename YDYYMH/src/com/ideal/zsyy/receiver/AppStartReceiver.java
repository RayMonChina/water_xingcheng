package com.ideal.zsyy.receiver;

import com.ideal.zsyy.service.LocationService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AppStartReceiver extends BroadcastReceiver {
	/*要接收的intent源*/  
    static final String ACTION = "android.intent.action.BOOT_COMPLETED";   
           
    public void onReceive(Context context, Intent intent)    
    {   
        if (intent.getAction().equals(ACTION))    
        {   
        	Intent locationService=new Intent(context,LocationService.class);
        	context.startService(locationService);
            //这边可以添加开机自动启动的应用程序代码   
        }   
    }   
}
