package com.ideal2.components;

import android.content.Context;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MyToast {

	private Toast toast;

	public Toast getToast() {
		return toast;
	}

	public void setToast(Toast toast) {
		this.toast = toast;
	}

	public static MyToast makeText(Context context, CharSequence text,
			int duration) {
		ScreenParameter screenParameter = ScreenParameter.getInstance(context);
		int width = screenParameter.getWidth();
		int height = screenParameter.getHeight();
		
		
		
		MyToast myToast = new MyToast();
		LinearLayout ll = (LinearLayout) LinearLayout.inflate(context,
				com.ideal2.demo.R.layout.my_toast, null);
		TextView textView = (TextView) ll.findViewById(com.ideal2.demo.R.id.message);
		textView.setText(text);
		Toast toast = new Toast(context);
		toast.setView(ll);
		toast.setDuration(duration);
		toast.setGravity(Gravity.CENTER, 0, height/3);
		myToast.setToast(toast);
		
		switch(width){
		case 1280:
			textView.setTextSize(30);
		case 480:
			textView.setTextSize(12);
		}
		
		return myToast;
	}

	public void show() {
		toast.show();
	}
	
}
