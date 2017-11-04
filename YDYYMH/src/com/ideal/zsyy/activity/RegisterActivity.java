package com.ideal.zsyy.activity;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ideal.wdm.tools.DataCache;
import com.ideal.zsyy.Config;
import com.jijiang.wtapp.R;
import com.ideal.zsyy.request.SmsReq;
import com.ideal.zsyy.request.UserRegisterReq;
import com.ideal.zsyy.request.UserReq;
import com.ideal.zsyy.response.SmsRes;
import com.ideal.zsyy.response.UserRegisterRes;
import com.ideal.zsyy.response.UserRes;
import com.ideal.zsyy.service.PreferencesService;
import com.ideal.zsyy.utils.IDCardUtil;
import com.ideal2.base.gson.GsonServlet;
import com.ideal2.base.gson.GsonServlet.OnResponseEndListening;

public class RegisterActivity extends Activity {

	private EditText login_user_name;
	private EditText login_pwd;
	private PreferencesService preferencesService;
	private EditText et_username;
	private EditText login_repwd;
	private EditText identifying_code;
	private int time = 60;
	private Timer timer;
	private String codeStr = "";
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				time--;
				if (time > 0) {
					btnCode.setEnabled(false);
					btnCode.setText("    " + time + "    ");
				} else {
					btnCode.setEnabled(true); 
					btnCode.setText("获取验证码");
					codeStr = "";
				}
				break;

			default:
				break;
			}
		};
	};
	private Button btnCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		preferencesService = new PreferencesService(getApplicationContext());

		initView();

	}

	private void initView() {

		login_user_name = (EditText) findViewById(R.id.login_user_name);
		et_username = (EditText) findViewById(R.id.et_username);
		login_pwd = (EditText) findViewById(R.id.login_pwd);
		login_repwd = (EditText) findViewById(R.id.login_repwd);
		identifying_code = (EditText) findViewById(R.id.identifying_code);

		btnCode = (Button) findViewById(R.id.btn_yzm_code);
		btnCode.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (login_user_name.getText().toString().trim().equals("")) {
					login_user_name.setBackgroundResource(R.drawable.settext_bg); 
					Toast.makeText(RegisterActivity.this, "手机号码不能为空", 1).show();
					return;
				} else {
//					if (login_user_name.length() < 11) {
//						Toast.makeText(RegisterActivity.this, "手机号码位数少了...", 1).show();
//						return;
//					} else if (login_user_name.length() > 11) {
//						Toast.makeText(RegisterActivity.this, "手机号码位数多了...", 1).show();
//						return;
//					}
					if (!IDCardUtil.isMobileNO(login_user_name.getText().toString().trim())) { 
						Toast.makeText(RegisterActivity.this, "您输入的手机号码无效。", 1).show(); 
						login_user_name.setBackgroundResource(R.drawable.settext_bg); 
						return;
					} else {
						login_user_name.setBackgroundDrawable(null);  
					}
				}
				Random random = new Random();
				int[] code = new int[4];
				for (int i = 0; i < code.length; i++) {
					code[i] = random.nextInt(10);
				}
				codeStr = "";
				for (int j = 0; j < code.length; j++) {
					codeStr = codeStr + code[j] + "";
				}
				String content = "您的验证码为：" + codeStr + "(掌上市一)";
				String phonenumber = login_user_name.getText().toString().trim();
				if (!"".equals(phonenumber)) {
					SmsReq smsreq = new SmsReq();
					smsreq.setContent(content);
					smsreq.setDest_number(phonenumber);
					smsreq.setOperType("102");
					sendSmsMessge(smsreq);
					TimerTask task = new TimerTask() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							Message msg = new Message();
							msg.what = 1;
							mHandler.sendMessage(msg);
						}
					};
					timer = new Timer();
					
					timer.schedule(task, 1000, 1000);
					if (time <= 0) {
						timer.cancel();
						codeStr = "";
					}
				}
			}
		});

		Button pc_bt_submit = (Button) findViewById(R.id.pc_bt_submit);
		pc_bt_submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				btnCode.setEnabled(true); 
				btnCode.setText("获取验证码");
				if(timer!=null){
					timer.cancel();
				}
//				codeStr = "";
				if (login_user_name.getText().toString().trim().equals("")) {
					login_user_name.setBackgroundResource(R.drawable.settext_bg); 
					Toast.makeText(RegisterActivity.this, "手机号码不能为空", 1).show();
					return;
				} else {
//					if (login_user_name.length() < 11) {
//						Toast.makeText(RegisterActivity.this, "手机号码位数少了...", 1).show();
//						return;
//					} else if (login_user_name.length() > 11) {
//						Toast.makeText(RegisterActivity.this, "手机号码位数多了...", 1).show();
//						return;
//					}
					if (!IDCardUtil.isMobileNO(login_user_name.getText().toString().trim())) { 
						Toast.makeText(RegisterActivity.this, "您输入的手机号码无效。", 1).show(); 
						login_user_name.setBackgroundResource(R.drawable.settext_bg); 
						return;
					} else {
						login_user_name.setBackgroundDrawable(null);  
					}
				}
				
				if (et_username.getText().toString().trim().equals("")) {
					Toast.makeText(RegisterActivity.this, "用户名不能为空", 1).show();
					et_username.setBackgroundResource(R.drawable.settext_bg);
					return;
				} else {
					et_username.setBackgroundDrawable(null);  
				}

				if (login_pwd.getText().toString().trim().equals("")) {
					Toast.makeText(RegisterActivity.this, "密码不能为空", 1).show();
					login_pwd.setBackgroundResource(R.drawable.settext_bg);
					return;
				} else {
					login_pwd.setBackgroundDrawable(null);  
				}

				if (!login_pwd.getText().toString().trim()
						.equals(login_repwd.getText().toString().trim())) {
					Toast.makeText(RegisterActivity.this, "两次密码不一致!!", 1)
							.show();
					login_repwd.setBackgroundResource(R.drawable.settext_bg);
					return;
				} else {
					login_repwd.setBackgroundDrawable(null);  
				}
				
				String identityCode = identifying_code.getText().toString().trim();
				
				if (codeStr.trim().equals(identityCode)) {
					queryRegisterData(login_user_name.getText().toString(),
							login_pwd.getText().toString(), et_username.getText().toString());
				} else {
					Toast.makeText(RegisterActivity.this, "验证码错误!!", 1).show();
				}
			}
		});

		Button btn_back = (Button) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	protected void sendSmsMessge(SmsReq smsreq) {
		// TODO Auto-generated method stub
		GsonServlet<SmsReq, SmsRes> gsonServlet = new GsonServlet<SmsReq, SmsRes>(
				this);
		gsonServlet.request(smsreq, SmsRes.class);
		gsonServlet.setShowDialog(false);
		gsonServlet
				.setOnResponseEndListening(new GsonServlet.OnResponseEndListening<SmsReq, SmsRes>() {
					@Override
					public void onResponseEnd(SmsReq commonReq,
							SmsRes commonRes, boolean result, String errmsg,
							int responseCode) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onResponseEndSuccess(SmsReq commonReq,
							SmsRes commonRes, String errmsg, int responseCode) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onResponseEndErr(SmsReq commonReq,
							SmsRes commonRes, String errmsg, int responseCode) {
						// TODO Auto-generated method stub

					}
				});
	}

	private void queryRegisterData(final String userAccount, final String pwd,
			final String username) {

		DataCache datacache = DataCache.getCache(this);
		datacache.setUrl(Config.url);

		UserRegisterReq req = new UserRegisterReq();
		req.setOperType("10");
		req.setUserAccount(userAccount);
		req.setUserName(username);
		req.setPwd(pwd);
		req.setHospId(Config.hosId);

		GsonServlet<UserRegisterReq, UserRegisterRes> gServlet = new GsonServlet<UserRegisterReq, UserRegisterRes>(
				this);
		gServlet.request(req, UserRegisterRes.class);
		gServlet.setOnResponseEndListening(new OnResponseEndListening<UserRegisterReq, UserRegisterRes>() {

			@Override
			public void onResponseEnd(UserRegisterReq commonReq,
					UserRegisterRes commonRes, boolean result, String errmsg,
					int responseCode) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onResponseEndSuccess(UserRegisterReq commonReq,
					UserRegisterRes commonRes, String errmsg, int responseCode) {
				// TODO Auto-generated method stub
				if (commonRes != null) {

					String use_id = commonRes.getUserId();
					queryLoginData(userAccount, pwd, use_id);
				}
			}

			@Override
			public void onResponseEndErr(UserRegisterReq commonReq,
					UserRegisterRes commonRes, String errmsg, int responseCode) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), errmsg,
						Toast.LENGTH_SHORT).show();
			}

		});
	}

	private void queryLoginData(final String user_name, final String pwd,
			final String use_id) {

		DataCache datacache = DataCache.getCache(this);
		datacache.setUrl(Config.url);

		UserReq req = new UserReq();
		req.setOperType("6");
		req.setUserAccount(user_name);
		req.setPwd(pwd);

		GsonServlet<UserReq, UserRes> gServlet = new GsonServlet<UserReq, UserRes>(
				this);
		gServlet.request(req, UserRes.class);
		gServlet.setOnResponseEndListening(new OnResponseEndListening<UserReq, UserRes>() {

			@Override
			public void onResponseEnd(UserReq commonReq, UserRes commonRes,
					boolean result, String errmsg, int responseCode) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onResponseEndSuccess(UserReq commonReq,
					UserRes commonRes, String errmsg, int responseCode) {
				// TODO Auto-generated method stub
				if (commonRes != null) {

					Config.phUsers = commonRes.getPhUsers();
					Config.phUsers.setId(use_id);

					preferencesService.saveLoginInfo(user_name, pwd, true,
							use_id,"",1,25);

					Intent intent = new Intent(getApplicationContext(),
							PersonalCenterActivity.class);
					intent.putExtra("isFirst", true);
					startActivity(intent);
					finish();
				}

			}

			@Override
			public void onResponseEndErr(UserReq commonReq, UserRes commonRes,
					String errmsg, int responseCode) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), errmsg,
						Toast.LENGTH_SHORT).show();
			}

		});
	}

}
