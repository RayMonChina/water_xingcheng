package com.ideal.zsyy.activity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ideal.wdm.tools.DataCache;
import com.ideal.zsyy.Config;
import com.jijiang.wtapp.R;
import com.ideal.zsyy.entity.PhUser;
import com.ideal.zsyy.request.WSingleUserItemReq;
import com.ideal.zsyy.request.WUserReq;
import com.ideal.zsyy.response.ServerDateTimeRes;
import com.ideal.zsyy.response.WUserRes;
import com.ideal.zsyy.service.PreferencesService;
import com.ideal.zsyy.utils.AutoUpdateUtil;
import com.ideal.zsyy.utils.DeviceHelper;
import com.ideal2.base.gson.GsonServlet;
import com.ideal2.base.gson.GsonServlet.OnResponseEndListening;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private EditText login_user_name;
	private EditText login_pwd;
	private String logintype;
	private Intent intent;
	private Dialog dialog = null;
	private EditText et_ip = null;
	private DataCache dCache;
	private TextView tv_setting,tv_version;
	private PreferencesService preferencesService;
	private String userName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		preferencesService = new PreferencesService(LoginActivity.this);
		dCache = DataCache.getCache(LoginActivity.this);
		intent = getIntent();
		logintype = intent.getStringExtra("logintype");
		userName=intent.getStringExtra("userName");
		if(userName==null||userName=="")
		{
			Intent intentSp=new Intent(LoginActivity.this,SplashActivity.class);
			startActivity(intentSp);
			finish();
		}
		initView();
		if (dCache.getUrl() == null || dCache.getUrl() == "") {
			this.inputIP();
		}

	}

	private void initView() {
		login_user_name = (EditText) findViewById(R.id.login_tv_username);
		login_pwd = (EditText) findViewById(R.id.login_tv_password);

		login_user_name.setText(userName);
		//login_pwd.setText(preferencesService.getLoginInfo().get("pwd").toString());
		tv_setting = (TextView) findViewById(R.id.tv_setting);
		tv_version=(TextView)findViewById(R.id.tv_version);
		Button pc_bt_submit = (Button) findViewById(R.id.login_bt_login);
		pc_bt_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				GetServerDate();
			}
		});

		tv_setting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				inputIP();
			}
		});

		if(tv_version!=null)
		{
			String versionName=DeviceHelper.getVersionName(LoginActivity.this);
			tv_version.setText(versionName);
		}
		//检测新版本
		AutoUpdateUtil aUpdate=new AutoUpdateUtil(LoginActivity.this);
		aUpdate.setShowToast(false);
		aUpdate.checkVersionSys();
	}
	
	private void GetServerDate() {
		WSingleUserItemReq req = new WSingleUserItemReq();
		req.setOperType("16");
		req.setReadMeterRecordId("0");

		GsonServlet<WSingleUserItemReq, ServerDateTimeRes> gServlet = new GsonServlet<WSingleUserItemReq, ServerDateTimeRes>(
				this);
		gServlet.request(req, ServerDateTimeRes.class);
		gServlet.setOnResponseEndListening(new OnResponseEndListening<WSingleUserItemReq, ServerDateTimeRes>() {
			@Override
			public void onResponseEnd(WSingleUserItemReq commonReq, ServerDateTimeRes commonRes, boolean result,
					String errmsg, int responseCode) {

			}

			@Override
			public void onResponseEndSuccess(WSingleUserItemReq commonReq, ServerDateTimeRes commonRes, String errmsg,
					int responseCode) {
				if (commonRes != null && commonRes.getServerTime() != null ) {

					// 判断网络时间，如果网络不通，不能打印
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					Date curDate = new Date(System.currentTimeMillis());
					String LocalDate = formatter.format(curDate);
					
					String serverDate=commonRes.getServerTime();
					
					if (!serverDate.equals(LocalDate)) {
						Toast.makeText(LoginActivity.this, "手机系统时间错误，请调整后重试！", 1).show();
						return;
					}
					else
					{
						sysLogin();
					}
					return;
				}

			}

			@Override
			public void onResponseEndErr(WSingleUserItemReq commonReq, ServerDateTimeRes commonRes, String errmsg,
					int responseCode) {
				Toast.makeText(getApplicationContext(), errmsg, Toast.LENGTH_SHORT).show();
			}

		});
	}
	
	private void sysLogin()
	{
		String uname = login_user_name.getText().toString();
		String pwd = login_pwd.getText().toString();
		if (uname != null && !uname.equals("") && !pwd.equals("")
				&& pwd != null) {
			queryData(uname, pwd);
			// loginSys(uname, pwd);
		} else if (uname == null || uname.equals("") || pwd.equals("")
				|| pwd == null) {
			Toast.makeText(LoginActivity.this, "用户名或密码不能为空", 1).show();
			login_pwd.setText(null);
		}
	}
	

	private void queryData(final String user_name, final String pwd) {
		WUserReq req = new WUserReq();
		req.setOperType("1");
		req.setLOGINNAME(user_name);
		req.setLOGINPASSWORD(pwd);

		GsonServlet<WUserReq, WUserRes> gServlet = new GsonServlet<WUserReq, WUserRes>(
				this);
		gServlet.request(req, WUserRes.class);
		gServlet.setOnResponseEndListening(new OnResponseEndListening<WUserReq, WUserRes>() {

			@Override
			public void onResponseEnd(WUserReq commonReq, WUserRes commonRes,
					boolean result, String errmsg, int responseCode) {
				
				
			}

			@Override
			public void onResponseEndSuccess(WUserReq commonReq,
					WUserRes commonRes, String errmsg, int responseCode) {
				
				if (commonRes != null) {
					if (preferencesService != null) {
						preferencesService.saveLoginInfo(
								commonReq.getLOGINNAME(),
								commonReq.getLOGINPASSWORD(), true,
								commonRes.getLOGINID(), 
								commonRes.getUSERNAME(),
								commonRes.getMeterDateTimeBegin(),
								commonRes.getMeterDateTimeEnd());
					}
					Intent mIntent=new Intent(LoginActivity.this,MainMenuActivity.class);
					startActivity(mIntent);
					finish();
				}

			}

			@Override
			public void onResponseEndErr(WUserReq commonReq,
					WUserRes commonRes, String errmsg, int responseCode) {
				
				Toast.makeText(getApplicationContext(), errmsg,
						Toast.LENGTH_SHORT).show();
			}

		});
	}

	private void inputIP() {

		dialog = new Dialog(LoginActivity.this, R.style.dialog);
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
					DataCache dCache = DataCache.getCache(LoginActivity.this);
					dCache.setServerIP(ip);
					dCache.setUrl(url);
					dCache.commit(LoginActivity.this);
					// mHandler.sendEmptyMessage(1);
					LoginActivity.this.dialog.dismiss();
				} else {
					Toast.makeText(LoginActivity.this, R.string.ip_wrongful,
							Toast.LENGTH_LONG).show();
				}
			}
		});

		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				LoginActivity.this.dialog.dismiss();
				// mHandler.sendEmptyMessageDelayed(0, 500);
			}
		});

		this.dialog.show();
	}
}
