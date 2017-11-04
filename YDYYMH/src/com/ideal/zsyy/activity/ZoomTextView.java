package com.ideal.zsyy.activity;

import com.ideal.zsyy.service.PreferencesService;

import android.widget.TextView;

public class ZoomTextView extends ZoomView<TextView>
{
	private PreferencesService preferencesService;
	/**最小字体*/
	public static final float MIN_TEXT_SIZE = 12f;
	
	/**最大子图*/
	public static final float MAX_TEXT_SIZE = 26f;

	/** 缩放比例 */
	float scale;

	/** 设置字体大小 */
	float textSize;

	public ZoomTextView(TextView view, float scale)
	{
		super(view);
		this.scale = scale;
		textSize = 18;
	}

	/**
	 * 放大
	 */
	protected void zoomOut(float scales)
	{
		textSize +=scale;
		if (textSize > MAX_TEXT_SIZE)
		{
			textSize = MAX_TEXT_SIZE;
		}
		view.setTextSize(textSize);
		preferencesService.SaveZoomSize(textSize);
	}

	/**
	 * 缩小
	 */
	protected void zoomIn(float scales)
	{
		textSize -=scale;
		if (textSize < MIN_TEXT_SIZE)
		{
			textSize = MIN_TEXT_SIZE;
		}
		view.setTextSize(textSize);
		preferencesService.SaveZoomSize(textSize);
	}

}
