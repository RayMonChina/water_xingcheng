package com.ideal.zsyy.utils;

import com.hp.hpl.sparta.xpath.ThisNodeTest;
import com.jijiang.wtapp.R;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class DialogCirleProgress {
	private static Dialog dialog=null;
	private Context _context=null;

	public DialogCirleProgress(Context context)
	{
		this._context=context;
	}
	private void showDialog(Dialog d) {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
		dialog = d;

		if (dialog != null) {
			dialog.show();
		}

	}
	
	public void showProcessDialog()
	{
		showDialogHandler.sendEmptyMessage(0);
	}
	
	public void hideProcessDialog()
	{
		showDialogHandler.sendEmptyMessage(1);
	}
	
	public void TestDialog()
	{
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				showProcessDialog();
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				hideProcessDialog();
			}
		}).start();
	}

	private Handler showDialogHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:

				Log.d("showDialogHandler", "showDialogHandler");
				Dialog dg = new Dialog(_context, R.style.pro2Dialog);
				dg.setContentView(R.layout.progressbar_layout);
				dg.setCanceledOnTouchOutside(false);
				dg.setOnDismissListener(new DialogInterface.OnDismissListener() {
					@Override
					public void onDismiss(DialogInterface dialog) {
					}
				});
				showDialog(dg);

				break;
			case 1:
				if (dialog != null && dialog.isShowing()) {
					dialog.dismiss();
				}
				break;
			default:
				break;
			}

		}
	};
}
