package com.ideal.zsyy.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Gallery;

/*
 * 循环展示图片
 * */
public class MyGallery extends Gallery {

	public MyGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		//return super.onFling(e1, e2, 0, velocityY);
		// 07-25 改动 用于滑动灵敏
        int keyCode;  
        if (isScrollingLeft(e1, e2)) {  
            keyCode = KeyEvent.KEYCODE_DPAD_LEFT;  
        } else {  
            keyCode = KeyEvent.KEYCODE_DPAD_RIGHT;  
        }  
        onKeyDown(keyCode, null);  
        return true;  
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if(getAdapter() != null)
			super.onLayout(changed, l, t, r, b);
	}
	
	private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {  
        return e2.getX() > e1.getX();  
    }  
}
