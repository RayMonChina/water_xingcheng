package com.ideal.zsyy.view;

import com.ideal.zsyy.service.PreferencesService;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LayoutFontSize {
	private static PreferencesService preferencesService;
	public final static float MIN_TEXT_SIZE = 16f;// 最小字体
	public final static float MAX_TEXT_SIZE = 28f;// 最大字体
	static float scale = 4f;// 缩放比例
	static float textSize = 16f;// 字体大小

	public static void SetDefaultFontSize(LinearLayout loginLayout, Context context) {
		SetFontseize(loginLayout, context);
	}

	private static void SetFontseize(ViewGroup viewGroup, Context context) {
		preferencesService = new PreferencesService(context);
		float defaultsize = preferencesService.GetZoomSize();
		int count = viewGroup.getChildCount();
		for (int i = 0; i < count; i++) {
			View view = viewGroup.getChildAt(i);
			if (view instanceof TextView) { // 若是Button记录下
				TextView newDtv = (TextView) view;
				newDtv.setTextSize(defaultsize);
			} else if (view instanceof ViewGroup) {
				// 若是布局控件（LinearLayout或RelativeLayout）,继续查询子View
				SetFontseize((ViewGroup) view, context);
			}
		}
	}
	
	public static void SetFontSizes(ViewGroup viewGroup,float defaultsize, Context context) {
		int count = viewGroup.getChildCount();
		for (int i = 0; i < count; i++) {
			View view = viewGroup.getChildAt(i);
			if (view instanceof TextView) { 
				TextView newDtv = (TextView) view;
				newDtv.setTextSize(defaultsize);
			} else if (view instanceof ViewGroup) {
				SetFontSizes((ViewGroup) view,defaultsize ,context);
			}
		}
	}

	// 放大
	public static void zoomOut(float scales, LinearLayout loginLayout, Context context) {
		textSize += scale;
		if (textSize > MAX_TEXT_SIZE) {
			textSize = MIN_TEXT_SIZE;
		}
		SetFontseizeOut(loginLayout, textSize, context);
	}

	private static void SetFontseizeOut(ViewGroup viewGroup, float textSize, Context context) {
		preferencesService = new PreferencesService(context);
		int count = viewGroup.getChildCount();
		for (int i = 0; i < count; i++) {
			View view = viewGroup.getChildAt(i);
			if (view instanceof TextView) {
				TextView newDtv = (TextView) view;
				newDtv.setTextSize(textSize);
			} else if (view instanceof ViewGroup) {
				SetFontseizeOut((ViewGroup) view, textSize, context);
			}
		}
		preferencesService.SaveZoomSize(textSize);
	}

}
