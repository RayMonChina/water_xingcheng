package com.ideal2.components;

import android.app.Dialog;
import android.content.Context;

public class MyFullDialog extends Dialog{

	public MyFullDialog(Context context) {
		super(context);
	}

	public MyFullDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		// TODO Auto-generated constructor stub
	}

	public MyFullDialog(Context context, int theme) {
		super(context, theme);
	}
	
	
}
