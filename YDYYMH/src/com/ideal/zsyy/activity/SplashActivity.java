package com.ideal.zsyy.activity;

import java.util.UUID;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jijiang.wtapp.R;
import com.ideal.wdm.tools.DataCache;
import com.ideal.zsyy.request.DeviceReq;
import com.ideal.zsyy.response.DeviceRes;
import com.ideal.zsyy.service.PreferencesService;
import com.ideal.zsyy.utils.DeviceHelper;
import com.ideal.zsyy.utils.HttpUtil;
import com.ideal2.base.gson.GsonServlet;
import com.ideal2.base.gson.GsonServlet.OnResponseEndListening;

public class SplashActivity extends Activity {

	private final int SPLASH_DISPLAY_LENGHT = 1000; // 延迟三秒
	private PreferencesService preferencesService;
	private Button btn_reg = null;
	String deviceId = "";
	private Dialog dialog;
	private EditText et_ip = null;
	private DataCache dCache;
	private String []accounts;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().requestFeature(Window.FEATURE_PROGRESS); // 去标题栏
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		preferencesService = new PreferencesService(getApplicationContext());
		dCache = DataCache.getCache(SplashActivity.this);
		deviceId=preferencesService.GetDeviceId();
		if(deviceId==null||deviceId=="")
		{
			deviceId = DeviceHelper.ObtainDeviceID(SplashActivity.this);
			if(deviceId==null||deviceId=="")
			{
				deviceId=UUID.randomUUID().toString().replace("-","");
			}
		}
		btn_reg = (Button) findViewById(R.id.btn_reg);
		if (dCache.getUrl() == null || dCache.getUrl() == ""||!HttpUtil.checkNet(SplashActivity.this)) {
			this.inputIP();
		}
		if (btn_reg != null) {
			btn_reg.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					
					queryData(deviceId,1);
				}
			});
		}
		if(preferencesService.GetDeviceId()!="")
		{
			btn_reg.setVisibility(View.GONE);
			queryData(deviceId, 0);
		}
		else {
			if(btn_reg!=null)
			{
				btn_reg.setVisibility(View.VISIBLE);
			}
		}
	}

	private void queryData(String deviceId, int isRegBtn) {
		DeviceReq req = new DeviceReq();
		req.setOperType("0");
		req.setMECode(deviceId);
		req.setIsRegButton(isRegBtn);
		GsonServlet<DeviceReq, DeviceRes> gServlet = new GsonServlet<DeviceReq, DeviceRes>(
				this);
		gServlet.request(req, DeviceRes.class);
		gServlet.setOnResponseEndListening(new OnResponseEndListening<DeviceReq, DeviceRes>() {

			@Override
			public void onResponseEnd(DeviceReq commonReq, DeviceRes commonRes,
					boolean result, String errmsg, int responseCode) {
				

			}

			@Override
			public void onResponseEndSuccess(DeviceReq commonReq,
					DeviceRes commonRes, String errmsg, int responseCode) {
				
				if (commonRes != null) {
					if(commonReq.getIsRegButton()==1)
					{
						preferencesService.SaveDeviceId(commonReq.getMECode());
					}
					if (commonRes.getEquipmentItems() != null&&commonRes.getEquipmentItems().size()>0) {
						if(commonRes.getEquipmentItems().size()==1)
						{
							PostDealy(commonRes.getEquipmentItems().get(0).getLoginID());
						}
						else {
							accounts=new String[commonRes.getEquipmentItems().size()];
							for(int i=0;i<commonRes.getEquipmentItems().size();i++)
							{
								accounts[i]=commonRes.getEquipmentItems().get(i).getLoginID();
							}
							new AlertDialog.Builder(SplashActivity.this)
							.setTitle("选择登录账户")
							.setItems(accounts,
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog,int which) {
											PostDealy(accounts[which]);
										}
									}).create().show();
						}
					}
					else {
						Toast.makeText(SplashActivity.this,"请退出后，重新启动应用",Toast.LENGTH_SHORT).show();
					}
				}
			}

			@Override
			public void onResponseEndErr(DeviceReq commonReq,
					DeviceRes commonRes, String errmsg, int responseCode) {
				
				Toast.makeText(getApplicationContext(), errmsg,
						Toast.LENGTH_SHORT).show();
				if(commonRes!=null&&commonRes.getErrMsgNo()==1)
				{
					new Handler().postDelayed(new Runnable() {
						
						@Override
						public void run() {
							
							System.exit(0);
						}
					}, SPLASH_DISPLAY_LENGHT);
				}
				if(commonRes==null)
				{
					inputIP();
				}
			}

		});
	}

	private void PostDealy(final String userName) {
		new Handler().postDelayed(new Runnable() {
			public void run() {
				
				Intent intent = new Intent(SplashActivity.this,
						LoginActivity.class);
				intent.putExtra("userName", userName);
				startActivity(intent);
				SplashActivity.this.finish();
			}
		}, SPLASH_DISPLAY_LENGHT);
	}
	
	private void inputIP() {
		dialog = new Dialog(SplashActivity.this, R.style.dialog);
		dialog.setContentView(R.layout.ip_dialog);
		Button btn_ok = (Button) dialog.findViewById(R.id.ok);
		Button btn_cancel = (Button) dialog.findViewById(R.id.cancel);
		et_ip = (EditText) dialog.findViewById(R.id.et_ip);
		et_ip.setText(dCache.getServerIP());
		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				String ip = et_ip.getText().toString().trim();
				String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
				String regexPort = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d):(\\d{1,5})$";
				boolean ipMatch = ip.matches(regex) || ip.matches(regexPort);
				if (ipMatch || ip.startsWith("http://")
						|| ip.startsWith("https://")) {
					String url = ip;
					if (ipMatch) {
						url = "http://" + ip;
					}
					url = url + "/service/AndroidService.ashx";
					DataCache dCache = DataCache.getCache(SplashActivity.this);
					dCache.setServerIP(ip);
					dCache.setUrl(url);
					dCache.commit(SplashActivity.this);
					// mHandler.sendEmptyMessage(1);
					SplashActivity.this.dialog.dismiss();
				} else {
					Toast.makeText(SplashActivity.this, R.string.ip_wrongful,
							Toast.LENGTH_LONG).show();
				}
			}
		});

		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SplashActivity.this.dialog.dismiss();
				// mHandler.sendEmptyMessageDelayed(0, 500);
			}
		});

		this.dialog.show();
	}
}
