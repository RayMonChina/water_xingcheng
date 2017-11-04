package com.ideal.zsyy.activity;

import java.io.IOException;
import com.ideal.zsyy.db.WdbManager;
import com.ideal.zsyy.request.WPriceReq;
import com.ideal.zsyy.response.WPriceRes;
import com.ideal.zsyy.utils.AutoUpdateUtil;
import com.ideal.zsyy.utils.DeviceHelper;
import com.ideal.zsyy.utils.DialogCirleProgress;
import com.ideal.zsyy.utils.FileUtils;
import com.ideal2.base.gson.GsonServlet;
import com.ideal2.base.gson.GsonServlet.OnResponseEndListening;
import com.jijiang.wtapp.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class WSystemSettingActivity extends Activity {

	private TextView tv_version;
	private RelativeLayout ly_changeaccount, ly_clearCache,ly_autoupdate;
	private DialogCirleProgress progress = null;
	private WdbManager dbManager = null;
	private Button btn_back=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.w_system_setting);
		progress = new DialogCirleProgress(WSystemSettingActivity.this);
		dbManager = new WdbManager(WSystemSettingActivity.this);
		this.initView();
		this.InitData();
	}

	private void initView() {
		tv_version = (TextView) findViewById(R.id.tv_version);
		ly_changeaccount = (RelativeLayout) findViewById(R.id.ly_changeaccount);
		ly_clearCache = (RelativeLayout) findViewById(R.id.ly_clearcache);
		ly_autoupdate=(RelativeLayout)findViewById(R.id.ly_autoupdate);
		
		btn_back=(Button)findViewById(R.id.btn_back);
		if (ly_changeaccount != null) {
			ly_changeaccount.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(dbManager.CheckUnUpload())
					{
						Toast.makeText(WSystemSettingActivity.this,"有未上传的数据，请先上传数据！",Toast.LENGTH_SHORT).show();
						Intent intent=new Intent(WSystemSettingActivity.this,WUploadDataActivity.class);
						startActivity(intent);
						return;
					}
					Intent nextIntent = new Intent(WSystemSettingActivity.this,
							LoginActivity.class);
					startActivity(nextIntent);
					finish();
				}
			});
		}
		if (ly_clearCache != null) {
			ly_clearCache.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					AlertDialog.Builder builder = new AlertDialog.Builder(
							WSystemSettingActivity.this);
					builder.setTitle("是否确定清空所有缓存数据？");
					builder.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									new Thread( new Runnable() {

										@Override
										public void run() {
											// TODO Auto-generated method stub
											if (progress != null) {
												progress.showProcessDialog();
											}
											if (dbManager != null) {
												dbManager.ResetTable();
											}
											FileUtils.deleteDir();
											try {
												FileUtils.createSDDir("");
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											if (progress != null) {
												progress.hideProcessDialog();
											}
											handler.sendEmptyMessage(1);
										}
									}).start();
								}
							});
					builder.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub

								}
							});
					builder.show();
				}
			});
		}
		if(ly_autoupdate!=null)
		{
			ly_autoupdate.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					AutoUpdateUtil aUpdate=new AutoUpdateUtil(WSystemSettingActivity.this);
					aUpdate.setShowToast(true);
					aUpdate.checkVersionSys();
				}
			});
		}
		if(btn_back!=null)
		{
			btn_back.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
				}
			});
		}
	}
	

	private void InitData() {
		if (tv_version != null) {
			tv_version.setText(DeviceHelper
					.getVersionName(WSystemSettingActivity.this));
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(dbManager!=null)
		{
			dbManager.closeDB();
		}
	}
	
	private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch(msg.what)
			{
			case 1:
				Toast.makeText(WSystemSettingActivity.this,"清除成功",Toast.LENGTH_SHORT).show();
				break;
			}
		}
		
	};
	
}
