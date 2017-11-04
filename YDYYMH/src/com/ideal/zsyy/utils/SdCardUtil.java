package com.ideal.zsyy.utils;

import android.os.Environment;
import android.util.Log;

public class SdCardUtil {

	public static boolean isHaveSdCard() {

		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {

			Log.v("SdCardUtil", "存在sd卡");
			return true;
		} else {

			Log.v("SdCardUtil", "不存在sd卡");
			return false;
		}
	}

}
