package com.ideal2.components;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.ideal2.util.DensityUtil;

public class MyTextView extends TextView {

//	private int textSize;
//	private float scale;

	public MyTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
//		DensityUtil desityUtil = new DensityUtil(context);
		// setTextSize(desityUtil.px2dip(getTextSize()));
//		this.textSize = this.px2dip(18);
//		ScreenParameter screenParameter = ScreenParameter
//				.getInstance(context);
////		this.width = screenParameter.getWidth();
//		this.scale = screenParameter.getDensity() / 160;
//		setTextSize(size);
	}

//	private int px2dip(float pxValue) {
//		return (int) (pxValue / this.scale + 0.5f);
//	}
}
