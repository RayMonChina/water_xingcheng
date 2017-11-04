package com.ideal.zsyy.activity;

import com.jijiang.wtapp.R;
import com.ideal.zsyy.entity.CheckOrderTip;
import com.ideal.zsyy.request.CheckOrderTipReq;
import com.ideal.zsyy.response.CheckOrderTipRes;
import com.ideal.zsyy.service.PreferencesService;
import com.ideal2.base.gson.GsonServlet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CheckOrderActivity extends Activity {

	private String checkId;
	private Button btn_back;
	private PreferencesService preService;
	private TextView tv_pname, tv_psex, tv_dept, tv_doctor, tv_checkname,
			tv_checktime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.check_yuyue_tip);
		init();
	}

	private void init() {
		Intent intent = getIntent();
		checkId = intent.getStringExtra("id");

		btn_back = (Button) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		preService = new PreferencesService(CheckOrderActivity.this);

		tv_pname = (TextView) findViewById(R.id.tv_pname);
		tv_psex = (TextView) findViewById(R.id.tv_psex);
		tv_dept = (TextView) findViewById(R.id.tv_dept);
		tv_doctor = (TextView) findViewById(R.id.tv_doctor);
		tv_checkname = (TextView) findViewById(R.id.ptv_checkname);
		tv_checktime = (TextView) findViewById(R.id.tv_checktime);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (!preService.getIsLogin()) {
			Intent intent = new Intent(CheckOrderActivity.this,
					LoginActivity.class);
			intent.putExtra("logintype", "checkorder");
			startActivity(intent);
		} else {
			getData(checkId);
		}
	}

	private void getData(String strId) {
		CheckOrderTipReq yyreReq = new CheckOrderTipReq();
		yyreReq.setOperType("111");
		yyreReq.setOrderId(strId);
		GsonServlet<CheckOrderTipReq, CheckOrderTipRes> gsonServlet = new GsonServlet<CheckOrderTipReq, CheckOrderTipRes>(
				CheckOrderActivity.this);
		gsonServlet.request(yyreReq, CheckOrderTipRes.class);
		gsonServlet
				.setOnResponseEndListening(new GsonServlet.OnResponseEndListening<CheckOrderTipReq, CheckOrderTipRes>() {

					@Override
					public void onResponseEnd(CheckOrderTipReq commonReq,
							CheckOrderTipRes commonRes, boolean result,
							String errmsg, int responseCode) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onResponseEndSuccess(
							CheckOrderTipReq commonReq,
							CheckOrderTipRes commonRes, String errmsg,
							int responseCode) {
						// TODO Auto-generated method stub
						if (commonRes != null) {
							CheckOrderTip orderTip = commonRes.getCheckTip();
							if (orderTip != null) {
								tv_checkname.setText(orderTip.getCheckName());
								tv_checktime.setText(orderTip.getCheckDate()
										+ "    " + orderTip.getCheckTime());
								tv_dept.setText(orderTip.getDeptName());
								tv_doctor.setText(orderTip.getDoctorName());
								tv_pname.setText(orderTip.getpName());
								tv_psex.setText(orderTip.getpSex());
							}

						}
					}

					@Override
					public void onResponseEndErr(CheckOrderTipReq commonReq,
							CheckOrderTipRes commonRes, String errmsg,
							int responseCode) {
						// TODO Auto-generated method stub
					}
				});
	}

}
