package com.ideal2.components;

import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class MyPopupWindowSl {
	
//	private static MyPopupWindowSl myPopupWindowSl;
	private static PopupWindow m_popupwindow;
	
	
	
	private MyPopupWindowSl(){};
	public static PopupWindow getInstance(LinearLayout v,View vv) {
		if (m_popupwindow == null) {
			m_popupwindow = new PopupWindow(v, LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT);// LayoutParams.WRAP_CONTENT);
//			m_popupwindow = new PopupWindow();
//			m_popupwindow.setContentView(v);
//			m_popupwindow.setHeight(LayoutParams.WRAP_CONTENT);
//			m_popupwindow.setWidth(LayoutParams.FILL_PARENT);
			m_popupwindow.setBackgroundDrawable(new BitmapDrawable());
			m_popupwindow.setFocusable(true);
			m_popupwindow.setOutsideTouchable(true);
			m_popupwindow.showAtLocation(vv, Gravity.BOTTOM, 0, 0);
		}
		return m_popupwindow;
	}
	
	public PopupWindow getPopupwindow() {
		return m_popupwindow;
	}
	public void init(LinearLayout v){
		m_popupwindow.setContentView(v);
		m_popupwindow.setHeight(LayoutParams.WRAP_CONTENT);
		m_popupwindow.setWidth(LayoutParams.FILL_PARENT);
		m_popupwindow.setBackgroundDrawable(new BitmapDrawable());
		m_popupwindow.setFocusable(true);
		m_popupwindow.setOutsideTouchable(true);
	}

	public static void show(View v, int type) {
		try {
			switch (type) {
			case 1:
				m_popupwindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void dismiss(){
		m_popupwindow.dismiss();
	}

}
