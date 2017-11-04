package com.ideal2.components;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class MyListView extends ListView{

	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setScrollingCacheEnabled(false);
		setVerticalFadingEdgeEnabled(false);
	}

}
