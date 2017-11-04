package com.ideal2.components;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class CheckFactory {
	
	public static boolean check(ViewGroup viewGroup){
		
		for(int i =0;i<viewGroup.getChildCount();i++){
			View view = viewGroup.getChildAt(i);
			if(view instanceof ICheck){
				ICheck check = (ICheck)view;
				if(null!=check){
					boolean resultEmpty = check.checkEmpty();
					if(resultEmpty){
						return true;
					}
					boolean resultLength = check.checklength();
					if(resultLength){
						return true;
					}
					boolean resultCheck = check.check();
					if(resultCheck){
						return true;
					}
				}
			}else if(view instanceof ViewGroup){
				ViewGroup vg = (ViewGroup)view;
				boolean resultcf = CheckFactory.check(vg);
				if(resultcf){
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean checkOne(ICheck check){
		if(null!=check){
			boolean resultEmpty = check.checkEmpty();
			if(resultEmpty){
				return true;
			}
			boolean resultLength = check.checklength();
			if(resultLength){
				return true;
			}
			boolean resultCheck = check.check();
			if(resultCheck){
				return true;
			}
		}
		return false;
	}
}
