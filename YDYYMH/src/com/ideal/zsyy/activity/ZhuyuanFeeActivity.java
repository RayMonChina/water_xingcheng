package com.ideal.zsyy.activity;

import com.jijiang.wtapp.R;
import com.ideal.zsyy.adapter.ZhuyChargeAdapter;
import com.ideal.zsyy.adapter.ZlChargeAdapter;
import com.ideal.zsyy.entity.HospitalChargeInfo;
import com.ideal.zsyy.request.HosFeeTipReq;
import com.ideal.zsyy.request.HospitalChargeReq;
import com.ideal.zsyy.response.HosFeeTipRes;
import com.ideal.zsyy.response.HospitalChargeRes;
import com.ideal.zsyy.service.PreferencesService;
import com.ideal2.base.gson.GsonServlet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ZhuyuanFeeActivity extends Activity {

	private ListView lv_fee_item;
	private TextView tv_totle,tv_time;
	Button btn_back;

	private String pat_no, inDate;
	
	PreferencesService preService;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zhuyuan_fee_tip);
		init();
		setListener();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(!preService.getIsLogin())
		{
			Intent intent=new Intent(ZhuyuanFeeActivity.this,LoginActivity.class);
			intent.putExtra("logintype", "zhuyuanfee");
			startActivity(intent);
		}
		else {
			getData(inDate,pat_no);
		}
	}

	private void init() {
		lv_fee_item = (ListView) findViewById(R.id.lv_zy_fee);
		tv_totle = (TextView) findViewById(R.id.tv_zy_totle);
		tv_time=(TextView)findViewById(R.id.tv_zy_time);
		btn_back = (Button) findViewById(R.id.btn_back);
		preService=new PreferencesService(ZhuyuanFeeActivity.this);
		Intent intent=getIntent();
		pat_no=intent.getStringExtra("pat_no");
		inDate=intent.getStringExtra("indate");
	}

	private void setListener() {
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	private void getData(String dateVal,String strpat_no) {
		HospitalChargeReq yyreReq = new HospitalChargeReq();
		yyreReq.setOperType("120");
		yyreReq.setPatNo(strpat_no);
		GsonServlet<HospitalChargeReq, HospitalChargeRes> gsonServlet = new GsonServlet<HospitalChargeReq, HospitalChargeRes>(
				ZhuyuanFeeActivity.this);
		gsonServlet.request(yyreReq, HospitalChargeRes.class);
		gsonServlet
				.setOnResponseEndListening(new GsonServlet.OnResponseEndListening<HospitalChargeReq, HospitalChargeRes>() {

					@Override
					public void onResponseEnd(HospitalChargeReq commonReq,
							HospitalChargeRes commonRes, boolean result,
							String errmsg, int responseCode) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onResponseEndSuccess(HospitalChargeReq commonReq,
							HospitalChargeRes commonRes, String errmsg,
							int responseCode) {
						// TODO Auto-generated method stub
						if (commonRes != null) {
							ZhuyChargeAdapter zAdapter=new ZhuyChargeAdapter(ZhuyuanFeeActivity.this,commonRes.getChargeInfos());
							lv_fee_item.setAdapter(zAdapter);
							tv_totle.setText("总费用："+commonRes.getTotleFee()+"（元）");
							tv_time.setText(commonRes.getTimes());
						}
					}

					@Override
					public void onResponseEndErr(HospitalChargeReq commonReq,
							HospitalChargeRes commonRes, String errmsg,
							int responseCode) {
						// TODO Auto-generated method stub
					}
				});
	}
}
