package com.ideal.zsyy.activity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.ideal.zsyy.Config;
import com.jijiang.wtapp.R;
import com.ideal.zsyy.adapter.PersonalCenterListAdapter;
import com.ideal.zsyy.entity.PhUser;
import com.ideal.zsyy.entity.TopVisitResInfo;
import com.ideal.zsyy.request.SelectTopVisitReq;
import com.ideal.zsyy.response.SelectTopVisistRes;
import com.ideal.zsyy.utils.DataUtils;
import com.ideal2.base.gson.GsonServlet;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

/**
 * 查看常用联系人预约
 * 
 * @author KOBE
 * 
 */
public class CyContactYYListActivity extends Activity {

	private String contactInfo_id = "";
	private String contactInfo_name = "";
	private String contactInfo_zjtype = "";
	private String contactInfo_zjnumber = "";

	private String startShiftDate = "";
	private String endShiftDate = "";

	private int current = -30;

	private PersonalCenterListAdapter listAdapter_yuyue;
	private PersonalCenterListAdapter listAdapter_qxyuyue;

	private List<TopVisitResInfo> list_yuyue = new ArrayList<TopVisitResInfo>();
	private List<TopVisitResInfo> list_cancel = new ArrayList<TopVisitResInfo>();

	private ListView lv_pc_yuyue;
	private ListView lv_pc_cancel;
	private TextView top_title;

	private ProgressDialog progressDialog;

	private LinearLayout health_ll_title_img;
	private LinearLayout health_ll_title_vedio;

	private Handler mHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case 0:

				setYuyueList();

				break;

			case 1:

				setCancelList();

				break;
			case 2:

				// queryGetYYData("1", 0);
				// queryGetYYData("2", 1);

				String message = (String) msg.obj;

				Toast.makeText(getApplicationContext(), message,
						Toast.LENGTH_LONG).show();

				// queryYYData("1", 0);
				// queryYYData("2", 1);

				break;
			case 3:

//				queryYYData("1", 0);
//				queryYYData("2", 1);
				queryData();
				break;

			default:
				break;
			}
		};
	};
	private String medical_cardnum;
	private PhUser phUsers;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cycontactyy_list);
 
//		DataUtils.ispre = false;
//		DataUtils.month1 = 0;
//		startShiftDate = DataUtils.getCurrentDate2();
//		endShiftDate = DataUtils.getDateStr_pro(current, false)
//				.replace("-", "") + "000000";
//		// endShiftDate = "20131023000000";
//		Log.v("tags", "endShiftDate=" + endShiftDate);
		phUsers = Config.phUsers;
		Bundle bundle = getIntent().getExtras();

		if (bundle != null) {

//			contactInfo_id = bundle.getString("contactInfo_id");
//			contactInfo_zjtype = bundle.getString("contactInfo_zjtype");
			contactInfo_zjnumber = bundle.getString("contactInfo_zjnumber"); 
			contactInfo_name = bundle.getString("contactInfo_name");
			medical_cardnum = bundle.getString("medical_cardnum");
			// ToastUtil.show(getApplicationContext(), "contactInfo_id="
			// + contactInfo_id);

		}

		initView();

//		queryYYData("1", 0);
//		queryYYData("2", 1);
//		queryData(); 
	}

	private ViewAnimator viewAnimator;
	private Button btnYY;
	private Button btn_qxyy;

	private void initView() {

		viewAnimator = (ViewAnimator) findViewById(R.id.contanct_ll_changeId);
		viewAnimator.setDisplayedChild(0);

		top_title = (TextView) findViewById(R.id.top_title);
		top_title.setText(contactInfo_name + "-预约详情");
		
		lv_pc_yuyue = (ListView) findViewById(R.id.lv_pc_yuyue);
		lv_pc_cancel = (ListView) findViewById(R.id.lv_pc_cancel);
		
		btnYY = (Button) findViewById(R.id.btn_yy);
		btn_qxyy = (Button) findViewById(R.id.btn_qxyy); 
		
		btnYY.setOnClickListener(new View.OnClickListener() {

			

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				btnYY.setBackgroundResource(R.drawable.navigation_tab_left_down);
				btnYY.setTextColor(getResources().getColor(android.R.color.white));
				btn_qxyy.setBackgroundResource(R.drawable.navigation_tab_right_up);
				btn_qxyy.setTextColor(getResources().getColor(R.color.textcolor1));
				viewAnimator.setDisplayedChild(0);
			}
		});
		btn_qxyy.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				btnYY.setBackgroundResource(R.drawable.navigation_tab_left_up);
				btnYY.setTextColor(getResources().getColor(R.color.textcolor1));
				btn_qxyy.setBackgroundResource(R.drawable.navigation_tab_right_down);
				btn_qxyy.setTextColor(getResources().getColor(android.R.color.white));
				viewAnimator.setDisplayedChild(1);
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

	private void setYuyueList() {

		listAdapter_yuyue = new PersonalCenterListAdapter(this, list_yuyue,
				true, mHandler, phUsers.getUser_Account());
		lv_pc_yuyue.setAdapter(listAdapter_yuyue);

	}

	private void setCancelList() {
		listAdapter_yuyue = new PersonalCenterListAdapter(this, list_cancel,
				false, mHandler, phUsers.getUser_Account());
		lv_pc_cancel.setAdapter(listAdapter_yuyue);

	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		queryData();
	}

	public void queryData() {
		list_yuyue.clear();
		list_cancel.clear();
		SelectTopVisitReq selectreq = new SelectTopVisitReq();
		selectreq.setPat_name(contactInfo_name);
		selectreq.setVst_card_no(medical_cardnum); 
		selectreq.setId_card(contactInfo_zjnumber);
		selectreq.setOperType("33");
		GsonServlet<SelectTopVisitReq, SelectTopVisistRes> gsonServlet = new GsonServlet<SelectTopVisitReq, SelectTopVisistRes>(
				this);
		gsonServlet.request(selectreq, SelectTopVisistRes.class);
		gsonServlet
				.setOnResponseEndListening(new GsonServlet.OnResponseEndListening<SelectTopVisitReq, SelectTopVisistRes>() {
					@Override
					public void onResponseEnd(SelectTopVisitReq commonReq,
							SelectTopVisistRes commonRes, boolean result,
							String errmsg, int responseCode) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onResponseEndSuccess(
							SelectTopVisitReq commonReq,
							SelectTopVisistRes commonRes, String errmsg,
							int responseCode) {
						// TODO Auto-generated method stub
						if (commonRes != null) {
							List<TopVisitResInfo> visitInfo = commonRes
									.getVisitInfo();
							for (TopVisitResInfo topVisitResInfo : visitInfo) {
//								if (topVisitResInfo.getStatus().equals("0")) {
//									list_yuyue.add(topVisitResInfo);
//								} else if (topVisitResInfo.getStatus().equals(
//										"1")) {
//									list_cancel.add(topVisitResInfo);
//								}
								String convertString = DataUtils.convertString(topVisitResInfo.getReg_time(),topVisitResInfo.getReg_locate());
								convertString = convertString.substring(6);
								String compare_date = topVisitResInfo.getReg_date() + " " + convertString;
								boolean flag = DataUtils.compare_date(compare_date);
								if (topVisitResInfo.getStatus().equals("1") || flag) {
									list_cancel.add(topVisitResInfo);
								} else if(topVisitResInfo.getStatus().equals("0") && flag){
									list_cancel.add(topVisitResInfo);
								}else {
									list_yuyue.add(topVisitResInfo);
								}
							}
						}
						setYuyueList();
						setCancelList();
					}

					@Override
					public void onResponseEndErr(SelectTopVisitReq commonReq,
							SelectTopVisistRes commonRes, String errmsg,
							int responseCode) {
						// TODO Auto-generated method stub
					}
				});
	}

}
