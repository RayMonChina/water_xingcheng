package com.ideal.zsyy.service;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.ideal.zsyy.utils.AsynImageLoader;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

public class ImageUpdateService extends Service {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		timer();
	}
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	private void timer() {
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				// 每天凌晨自动更新数据
//				SharedPreferences sp = getSharedPreferences("actm",
//						Context.MODE_PRIVATE);
//				SharedPreferences.Editor editor = sp.edit();
//				editor.putLong("sum", 0L);
//				editor.commit();
				AsynImageLoader.deleteImage();
			}
		};
		Timer timer = new Timer();
		int hour = new Date().getHours();
		// timer.schedule(task,(24-hour)*3600*1000, 24*3600*1000);
		timer.schedule(task, 60 * 1000, 60 * 1000);// 测试用
		
	}

}
