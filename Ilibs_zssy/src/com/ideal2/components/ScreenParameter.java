package com.ideal2.components;

import android.content.Context;
import android.util.DisplayMetrics;

public class ScreenParameter
{
	private static ScreenParameter screenParameter;
	private int width;
	private int height;
	private float density;

	private ScreenParameter(Context context)
	{
		DisplayMetrics dm = new DisplayMetrics();
		dm = context.getApplicationContext().getResources().getDisplayMetrics();
		this.width = dm.widthPixels;
		this.height = dm.heightPixels;
		this.density = dm.densityDpi;
		
		
	}
	
	public static ScreenParameter getInstance(Context context)
	{
		screenParameter = screenParameter == null ?  new ScreenParameter(context) : screenParameter;
		return screenParameter;
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}
	
	public float getDensity()
	{
		return density;
	}
	
}
