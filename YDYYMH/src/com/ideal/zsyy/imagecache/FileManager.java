package com.ideal.zsyy.imagecache;

import com.ideal.zsyy.Config;



public class FileManager {

	public static String getSaveFilePath() {
		
		return CommonUtil.getRootFilePath() + Config.down_path+ "/imgcache/";
		
//		if (CommonUtil.hasSDCard()) {
//			return CommonUtil.getRootFilePath() + "productshow/imgcache/";
//		} else {
//			return CommonUtil.getRootFilePath() + "productshow/imgcache/";
//		}
	}
}
