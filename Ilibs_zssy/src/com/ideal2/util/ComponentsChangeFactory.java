package com.ideal2.util;

import android.view.View;
import android.view.ViewGroup;

public class ComponentsChangeFactory {
	/**
	 * Components控件前文字和后文本之间的距离设置
	 * @param pixels
	 * @param viewGroup
	 */
	public static void changeSpace(int pixels,ViewGroup viewGroup){
		for(int i =0;i<viewGroup.getChildCount();i++){
			View view = viewGroup.getChildAt(i);
			if(view instanceof com.ideal2.components.Components){
				com.ideal2.components.Components components = (com.ideal2.components.Components )view;
				if(components.isSpace()){
					components.getName().setWidth(pixels);
				}
			}else if(view instanceof ViewGroup){
				changeSpace(pixels,(ViewGroup)view);
			}
		}
	}
	
}
