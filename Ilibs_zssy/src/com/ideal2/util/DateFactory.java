package com.ideal2.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFactory {
	
	
	public static String getNowDate(String pattern){
		String str = "";
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		str = sdf.format(date);
		return str;
	}
}
