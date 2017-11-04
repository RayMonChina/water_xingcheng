package com.ideal.zsyy.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class FileUtil {

	public static String choosePic(String url) {

		String str = "";

		if (url != null) {

			if (url.trim().endsWith("jpg")) {
				str = ".jpg";
			}

			else if (url.trim().endsWith("png")) {
				str = ".png";
			}

			else if (url.trim().endsWith("gif")) {
				str = ".gif";
			}

			else if (url.trim().endsWith("jpeg")) {
				str = ".jpeg";
			}

			else if (url.trim().endsWith("mp3")) {
				str = ".mp3";
			}
		}
		return str;
	}

	public static Bitmap findBitmap(AssetManager am, String name) {

		try {
			AssetFileDescriptor afd = am.openFd(name);
			if (afd == null) {
				return null;
			}
			return BitmapFactory.decodeStream(afd.createInputStream());
		} catch (IOException e) {
			return null;

		}
	}

	/**
	 * 读取文件
	 * @param url
	 * @return
	 */
	public static String getDataFileCache(String url) {
		if (url == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		try {
			File file = new File(url);
			if (!file.exists() || file.isDirectory())
				throw new FileNotFoundException();
			FileInputStream fis = new FileInputStream(file); 
			byte[] buf = new byte[1024];
			while ((fis.read(buf)) != -1) {
				sb.append(new String(buf));
				buf = new byte[1024];// 重新生成，避免和上次读取的数据重复
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	/**
	 * 写入文件 
	 * @param url
	 * @param data
	 */
	public static void setDataFileCache(String path,String data,String filename){
		File file=new File(path + filename);
        try {
			if(!file.exists())
			    file.mkdirs();
			FileOutputStream out=new FileOutputStream(file,true);         
			for(int i=0;i<10000;i++){
			    out.write(data.toString().getBytes("utf-8"));
			}        
			out.close(); 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
