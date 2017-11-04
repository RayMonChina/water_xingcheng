package com.ideal.zsyy.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.jijiang.wtapp.R;

public class VideoTotalView extends LinearLayout {

	public VideoTotalView(Context context) {
		super(context);
		layout(context);
	}

	public VideoTotalView(Context context, AttributeSet attrs) {
		super(context, attrs);
		layout(context);
	}

	private void layout(Context context) {
		
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		addView(inflater.inflate(R.layout.video_total_view, null),
				new LayoutParams(LayoutParams.FILL_PARENT,
						LayoutParams.FILL_PARENT));
	}

}
