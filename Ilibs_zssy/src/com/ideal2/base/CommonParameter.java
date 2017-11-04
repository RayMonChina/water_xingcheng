package com.ideal2.base;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

public class CommonParameter {
	public final static String FILENAME_IDEAL_COMMONPARAMETER = "ideal_commonParameter";
	public final static String ORGCODE = "orgcode";
	public final static String USER = "user";
	public final static String USSER_ID ="user_id";
	
	
	private static CommonParameter commonParameter;
	private Context context;
	
	private CommonParameter(){};
	
	public static CommonParameter create(Context context){
		if(null==commonParameter){
			commonParameter= new CommonParameter();
			commonParameter.context = context;
		}
		return commonParameter;
	}
	
	private Map<String,String> commonParamMap = new HashMap<String,String>();
	public String getParam(String name){
		if(null==commonParamMap.get(name)||"".equals(commonParamMap.get(name))){
			IOConfig ioConfig = new IOConfig(context, FILENAME_IDEAL_COMMONPARAMETER);
			if("".equals(ioConfig.get(name))){
				initParam();
			}
			commonParamMap.put(name, ioConfig.get(name));
		}
		return commonParamMap.get(name);
	}
	
	public void initParam(){
		IOConfig ioConfig = new IOConfig(context, FILENAME_IDEAL_COMMONPARAMETER);
		ioConfig.put(ORGCODE, "");
	}
	
	public void setParam(String name,String value){
		IOConfig ioConfig = new IOConfig(context, FILENAME_IDEAL_COMMONPARAMETER);
		ioConfig.put(name, value);
	}
	
	public void clear(String name){
		IOConfig ioConfig = new IOConfig(context, FILENAME_IDEAL_COMMONPARAMETER);
		ioConfig.put(name, "");
	}
	
	public void clearAll(){
		IOConfig ioConfig = new IOConfig(context, FILENAME_IDEAL_COMMONPARAMETER);
		ioConfig.put(ORGCODE, "");
		ioConfig.put(USER, "");
		ioConfig.put(USSER_ID, "");
	}
	
}
