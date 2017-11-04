package com.ideal2.log;

import android.util.Log;

public class ILog {
	private static final boolean iLog = true;
	private static final boolean iLogd1 = true;
	private static final boolean iLogd2 = true;
	private static final boolean iLoge1 = true;
	private static final boolean iLoge2 = true;
	private static final boolean iLogi1 = true;
	private static final boolean iLogi2 = true;
	private static final boolean iLogv1 = true;
	private static final boolean iLogv2 = true;
	private static final boolean iLogw1 = true;
	private static final boolean iLogw2 = true;
	private static final boolean iLogw3 = true;
	
	public static void d(String TAG, String msg) {
		if (iLog){
			if(iLogd1){
				Log.d(TAG, msg);
			}
		}
			
	}
	public static void d(String TAG, String msg, Throwable tr) {
		if (iLog){
			if(iLogd2){
				Log.d(TAG, msg, tr);
			}
		}
			
	}

	public static void e(String TAG, String msg) {
		if (iLog){
			if(iLoge1){
				Log.e(TAG, msg);
			}
		}
			
	}

	public static void e(String TAG, String msg, Throwable tr) {
		if (iLog){
			if(iLoge2){
				Log.e(TAG, msg, tr);
			}
		}
			
	}

	public static void i(String TAG, String msg) {
		if (iLog){
			if(iLogi1){
				Log.i(TAG, msg);
			}
		}
	}

	public static void i(String TAG, String msg, Throwable tr) {
		if (iLog){
			if(iLogi2){
				Log.i(TAG, msg, tr);
			}
		}
	}

	public static void v(String TAG, String msg) {
		if (iLog){
			if(iLogv1){
				Log.v(TAG, msg);
			}
		}
	}

	public static void v(String TAG, String msg, Throwable tr) {
		if (iLog){
			if(iLogv2){
				Log.v(TAG, msg, tr);
			}
		}
	}

	public static void w(String TAG, String msg) {
		if (iLog){
			if(iLogw1){
				Log.w(TAG, msg);
			}
		}
			
	}

	public static void w(String TAG, Throwable tr) {
		if (iLog){
			if(iLogw2){
				Log.w(TAG, tr);
			}
		}
	}

	public static void w(String TAG, String msg, Throwable tr) {
		if (iLog){
			if(iLogw3){
				Log.w(TAG, msg, tr);
			}
		}
			
	}
}
