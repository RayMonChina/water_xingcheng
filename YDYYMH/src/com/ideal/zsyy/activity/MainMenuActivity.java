package com.ideal.zsyy.activity;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.SDKInitializer;
import com.jijiang.wtapp.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.ideal.zsyy.service.PreferencesService;
import com.ideal.zsyy.view.BaseLayout;
import com.ideal.zsyy.view.MainPageView;
import com.ideal.zsyy.view.UserSearchView;
import com.ideal.zsyy.view.WCBMenuView;
import com.ideal.zsyy.view.WMapFjyhView;

public class MainMenuActivity extends Activity {
	private static final String LTAG = MainMenuActivity.class.getSimpleName();
	private Context context;
	private RadioGroup radioGroup;
	private TabHost tabHost;
	private boolean flagMeeting, flagOper, flag_fjyh, flag_cb;
	private LinearLayout lay_mainpage, lay_yhcx, lay_fjyh, lay_cb;
	private BaseLayout base_mainpage, base_yhcx, base_cb;
	private WMapFjyhView base_fjyh;
	private RadioButton rd_btn_main;
	private TextView tv_main_title;
	
    private LocationMode tempMode = LocationMode.Hight_Accuracy;
    private String tempcoor="gcj02";
    private LocationClient mLocationClient;
    private PreferencesService preferencesService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_tablayout);
		preferencesService=new PreferencesService(MainMenuActivity.this);
		initView();
		setviewEvent();
	}

	@SuppressWarnings("deprecation")
	private void initView() {
		tabHost = (TabHost) findViewById(R.id.tabHost);
		radioGroup = (RadioGroup) findViewById(R.id.main_radio);
		lay_mainpage = (LinearLayout) findViewById(R.id.lay_mainpage);
		lay_yhcx = (LinearLayout) findViewById(R.id.lay_yhcx);
		lay_fjyh = (LinearLayout) findViewById(R.id.lay_fjyh);
		lay_cb = (LinearLayout) findViewById(R.id.lay_cb);
		rd_btn_main = (RadioButton) findViewById(R.id.btn_mainpage);
		flagMeeting = flagOper = false;
		this.context = MainMenuActivity.this;
		tabHost.setup();
		tabHost.addTab(tabHost
				.newTabSpec("mainpage")
				.setIndicator(getResources().getString(R.string.main_page),
						null).setContent(R.id.lay_mainpage));
		tabHost.addTab(tabHost
				.newTabSpec("yhcx")
				.setIndicator(getResources().getString(R.string.main_yhcx),
						null).setContent(R.id.lay_yhcx));
		tabHost.addTab(tabHost
				.newTabSpec("fjyh")
				.setIndicator(getResources().getString(R.string.main_fjyh),
						null).setContent(R.id.lay_fjyh));
		tabHost.addTab(tabHost.newTabSpec("cb")
				.setIndicator(getResources().getString(R.string.main_cb), null)
				.setContent(R.id.lay_cb));
		base_mainpage = new MainPageView(context);
		// base_content = base_meeting;
		flagMeeting = true;
		lay_mainpage.addView(base_mainpage, new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		tabHost.setCurrentTab(0);
		tv_main_title=(TextView)base_mainpage.findViewById(R.id.top_title);
		if(tv_main_title!=null)
		{
			tv_main_title.setText(preferencesService.getLoginInfo().get("userName").toString()+",欢迎使用！");
		}
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.btn_mainpage:
					tabHost.setCurrentTabByTag("mainpage");
					if (!flagMeeting) {
						base_mainpage = base_mainpage == null ? new MainPageView(
								context) : base_mainpage;
						lay_mainpage.addView(base_mainpage, new LayoutParams(
								LayoutParams.FILL_PARENT,
								LayoutParams.FILL_PARENT));
					}
					flagMeeting = true;
					break;
				case R.id.btn_yhcx:
					tabHost.setCurrentTabByTag("yhcx");

					if (!flagOper) {
						base_yhcx = base_yhcx == null ? new UserSearchView(
								context) : base_yhcx;
						lay_yhcx.addView(base_yhcx, new LayoutParams(
								LayoutParams.FILL_PARENT,
								LayoutParams.FILL_PARENT));
					}
					flagOper = true;

					break;
				case R.id.btn_fjyh:
					tabHost.setCurrentTabByTag("fjyh");
					if (!flag_fjyh) {
						base_fjyh = base_fjyh == null ? new WMapFjyhView(
								context) : base_fjyh;
						lay_fjyh.addView(base_fjyh, new LayoutParams(
								LayoutParams.FILL_PARENT,
								LayoutParams.FILL_PARENT));
					}
					flag_fjyh = true;
					break;
				case R.id.btn_cb:
					tabHost.setCurrentTabByTag("cb");
					if (!flag_cb) {
						//base_cb = base_cb == null ? new WCBView(context):base_cb;
						base_cb = base_cb == null ? new WCBMenuView(context):base_cb;
						lay_cb.addView(base_cb, new LayoutParams(
								LayoutParams.FILL_PARENT,
								LayoutParams.FILL_PARENT));
						
					}
					flag_cb = true;
					/*new Handler().postDelayed(new Runnable() {
						public void run() {
							
							Intent intent = null;
							intent = new Intent(MainMenuActivity.this,
									WCBMainActivity.class);
							MainMenuActivity.this.startActivity(intent);
						}
					}, 1000);*/
					break;

				}
			}

		});
		
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onResume() {
		
		if (base_fjyh != null) {
			base_fjyh.onResume();
		}
		super.onResume();
		if (radioGroup.getCheckedRadioButtonId() == R.id.btn_cb) {
			tabHost.setCurrentTabByTag("mainpage");
			if (!flagMeeting) {
				base_mainpage = base_mainpage == null ? new MainPageView(
						context) : base_mainpage;
				lay_mainpage.addView(base_mainpage, new LayoutParams(
						LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			}
			flagMeeting = true;
			if (rd_btn_main != null) {
				rd_btn_main.setChecked(true);
			}
		}
	}

	private void setviewEvent() {
		if (base_mainpage != null) {
			View vcb = base_mainpage.findViewById(R.id.ll_chaobiao);
			vcb.setOnClickListener(new View.OnClickListener() {//
				RadioButton rd_btn_cb = (RadioButton) findViewById(R.id.btn_cb);

				@SuppressWarnings("deprecation")
				@Override
				public void onClick(View v) {
					
					tabHost.setCurrentTabByTag("cb");
					if (!flag_cb) {
						base_cb = base_cb == null ? new WCBMenuView(context)
								: base_cb;
						lay_cb.addView(base_cb, new LayoutParams(
								LayoutParams.FILL_PARENT,
								LayoutParams.FILL_PARENT));
					}
					flag_cb = true;
					if (rd_btn_cb != null) {
						rd_btn_cb.setChecked(true);
					}
				}
			});

			View cuserSearch = base_mainpage.findViewById(R.id.ll_usersearch);
			cuserSearch.setOnClickListener(new View.OnClickListener() {
				RadioButton rd_btn_user_search = (RadioButton) findViewById(R.id.btn_yhcx);

				@SuppressWarnings("deprecation")
				@Override
				public void onClick(View v) {
					
					tabHost.setCurrentTabByTag("yhcx");
					if (rd_btn_user_search != null) {
						rd_btn_user_search.setChecked(true);
					}
					if (!flagOper) {
						base_yhcx = base_yhcx == null ? new UserSearchView(
								context) : base_yhcx;
						lay_yhcx.addView(base_yhcx, new LayoutParams(
								LayoutParams.FILL_PARENT,
								LayoutParams.FILL_PARENT));
					}

					flagOper = true;
				}
			});

		}
	}
	
	@Override
	protected void onPause() {
		
		if (base_fjyh != null) {
			base_fjyh.onPause();
		}
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		
		if (base_fjyh != null) {
			base_fjyh.onDestroy();
		}
		super.onDestroy();
		if(mReceiver!=null)
		{
			unregisterReceiver(mReceiver);
		}
		
	}

	@Override
	protected void onStop() {
		
		super.onStop();
		if(mLocationClient!=null)
		{
			mLocationClient.stop();
		}
		
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			AlertDialog.Builder builder = new AlertDialog.Builder(
					MainMenuActivity.this);
			builder.setTitle("是否要退出抄表系统？");
			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							finish();
						}
					});

			builder.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
						}

					});
			builder.create().show();

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public class SDKReceiver extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {
			String s = intent.getAction();
			Log.d(LTAG, "action: " + s);
			if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
				Log.e(LTAG, "key 验证出错! 请在 AndroidManifest.xml 文件中检查 key 设置");
			} else if (s
					.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_OK)) {
				
				Log.e(LTAG, "key 验证成功! 功能可以正常使用");
			} else if (s
					.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
				// text.setText("网络出错");
				Toast.makeText(MainMenuActivity.this, "网络出错",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	private SDKReceiver mReceiver;

	private void initLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(tempMode);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setCoorType(tempcoor);// 可选，默认gcj02，设置返回的定位结果坐标系，
		int span = 1000;
		try {
			//span = Integer.valueOf(frequence.getText().toString());
		} catch (Exception e) {
			// TODO: handle exception
		}
		option.setScanSpan(span);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		//option.setIsNeedAddress(checkGeoLocation.isChecked());// 可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(true);// 可选，默认false,设置是否使用gps
		option.setLocationNotify(true);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
		option.setIgnoreKillProcess(true);// 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死

		mLocationClient.setLocOption(option);
	}
}
