package com.ideal.zsyy.utils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class WaterUtils {

	public static Double CalcWaterFee(String stepPrice,String extPrice,double waterVal)
	{
		return CalcStepFee(stepPrice,waterVal)+CalcExtFee(extPrice,waterVal);
	}
	
	public static Double GetAvgPrice(String stepPrice)
	{
		double retValue=0d;
		if(stepPrice==null||stepPrice=="")
		{
			return retValue;
		}
		String []arrStep=stepPrice.split("\\|");
		for(String sItem:arrStep)
		{
			String[] arrOne=sItem.split(":");
			if(arrOne.length>1)
			{
				try {
					retValue=Double.parseDouble(arrOne[1]);
					break;
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
		return retValue;
	}
	
	public static Map<String, Double>GetExtPrice(String extPrice)
	{
		Map<String, Double>retDic=new HashMap<String, Double>();
		double f1=0d;
		double f2=0d;
		double price=0;
		if(extPrice!=null&&extPrice!="")
		{
			String[]prices=extPrice.split("\\|");
			for(int i=0;i<prices.length;i++)
			{
				String itm=prices[i];
				String[]arrOne=itm.split(":");
				try {
					price=Double.parseDouble(arrOne[1]);
					if(i==0)
					{
						f1=price;
					}
					else if(i==1){
						f2=price;
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
		retDic.put("F1", f1);
		retDic.put("F2",f2);
		return retDic;
	}
	//获取阶梯水费
	public static double CalcStepFee(String stepPrice,double waterValue)
	{
		double retValue=0d;
		double begin=0d;
		double end=0d;
		double price=0;
		if(stepPrice==null||stepPrice=="")
		{
			return retValue;
		}
		String []arrStep=stepPrice.split("\\|");
		for(String sItem:arrStep)
		{
			String[] arrOne=sItem.split(":");
			if(arrOne.length>1)
			{
				String[] steps=arrOne[0].split("-");
				try {
					begin=Double.parseDouble(steps[0]);
					end=Double.parseDouble(steps[1]);
					price=Double.parseDouble(arrOne[1]);
					if(waterValue<begin)
					{
						break;
					}
					if(waterValue>end)
					{
						retValue+=(end-begin)*price;
					}
					else if(waterValue>=begin&&waterValue<=end){
						retValue+=(waterValue-begin)*price;
						break;
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
		return retValue;
	}
	
	//获取其它费用
	public static double CalcExtFee(String extPrice,double waterValue)
	{
		double retVal=0;
		double price=0;
		if(extPrice==null||extPrice=="")
		{
			return retVal;
		}
		String[]prices=extPrice.split("\\|");
		for(String itm :prices)
		{
			String[]arrOne=itm.split(":");
			try {
				price=Double.parseDouble(arrOne[1]);
				retVal+=waterValue*price;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return retVal;
	}

	 public static Double round(Double value, Integer scale) {  
	        if (scale < 0) {  
	            throw new IllegalArgumentException(  
	                    "The scale must be a positive integer or zero");  
	        }  
	        BigDecimal b = new BigDecimal(Double.toString(value));  
	        BigDecimal one = new BigDecimal("1");  
	        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();  
	    }  
}
