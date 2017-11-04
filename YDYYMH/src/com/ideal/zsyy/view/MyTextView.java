package com.ideal.zsyy.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class MyTextView extends TextView {

	public MyTextView(Context context, AttributeSet attrs) {

		super(context, attrs);

		Typeface face = Typeface.createFromAsset(context.getAssets(),
				"fonts/FZLTXHK.TTF");
		this.setTypeface(face);

	}

}
