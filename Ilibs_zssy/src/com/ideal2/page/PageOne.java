package com.ideal2.page;

import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;

import com.ideal2.demo.R;

public class PageOne extends LinearLayout{
	LinearLayout linearLayout;
	public PageOne(Context context) {
		super(context, null);
		linearLayout = (LinearLayout)LinearLayout.inflate(context, R.layout.main, null);
		linearLayout.findViewById(R.id.btn);
		linearLayout.setBackgroundResource(R.color.white);
		Log.d("asdasd",linearLayout.getBackground().toString()+"");
		this.addView(linearLayout,LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT);
	}
}
