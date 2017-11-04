package com.ideal.zsyy.activity;

import java.util.List;

import com.jijiang.wtapp.R;
import com.ideal.zsyy.adapter.DayFeeAdapter;
import com.ideal.zsyy.adapter.ZlChargeAdapter;
import com.ideal.zsyy.adapter.ZlDayChargeAdapter;
import com.ideal.zsyy.entity.DayFeeList;
import com.ideal.zsyy.entity.HosChargeInfo;
import com.ideal.zsyy.entity.HospitalChargeDayInfo;
import com.ideal.zsyy.entity.HospitalChargeInfo;
import com.ideal.zsyy.request.DayFeeDetailReq;
import com.ideal.zsyy.request.DayFeeReq;
import com.ideal.zsyy.request.HospitalChargeDayReq;
import com.ideal.zsyy.request.HospitalChargeReq;
import com.ideal.zsyy.response.DayFeeRes;
import com.ideal.zsyy.response.HosChargeRes;
import com.ideal.zsyy.response.HospitalChargeDayRes;
import com.ideal.zsyy.response.HospitalChargeRes;
import com.ideal.zsyy.utils.DensityUtil;
import com.ideal2.base.gson.GsonServlet;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class DayFeeActivity extends Activity {

	private PopupWindow pop;
	private ListView lv_feeItems, lv_dates;
	private TextView  tv_rq, tv_totle;
	private LayoutInflater inflater;
	private List<HospitalChargeDayInfo> tempDayFees;
	private List<HospitalChargeInfo> tempCharge;
	private String strpat_no, pat_name, searchDate;
	private Button btn_back;

	boolean isfirst=true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zl_dayfee_info);
		init();
		setListener();
	}

	private void init() {
		inflater = LayoutInflater.from(DayFeeActivity.this);
		LinearLayout layPop = (LinearLayout) inflater.inflate(
				R.layout.dayfee_pop_view, null);
		lv_feeItems = (ListView) findViewById(R.id.lv_zlinfo);
		lv_dates = (ListView) layPop.findViewById(R.id.lv_dates);
		tv_rq = (TextView) findViewById(R.id.tv_rq);
		tv_totle = (TextView) findViewById(R.id.tv_totle);
		btn_back=(Button)findViewById(R.id.btn_back);

		pop = new PopupWindow(layPop, DensityUtil.dip2px(DayFeeActivity.this, 120),
				DensityUtil.dip2px(DayFeeActivity.this, 200), false);
		// 需要设置一下此参数，点击外边可消失
		pop.setBackgroundDrawable(new BitmapDrawable());
		// 设置点击窗口外边窗口消失
		pop.setOutsideTouchable(true);
		// 设置此参数获得焦点，否则无法点击
		pop.setFocusable(true);

		Intent intent = getIntent();
		strpat_no = intent.getStringExtra("kh");
		pat_name = intent.getStringExtra("brxm");
		getPopList(strpat_no);

	}

	private void setListener() {
		tv_rq.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (pop != null) {
					if (pop.isShowing()) {
						pop.dismiss();
					} else {
						pop.showAsDropDown(tv_rq);
					}
				}
			}
		});

		lv_dates.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				HospitalChargeDayInfo dlList = tempDayFees.get(arg2);
				tv_rq.setText(dlList.getDays());
				tv_totle.setText("总费用："+dlList.getDayFee()+"（元）");
				getFeeDetail(strpat_no, dlList.getDays());
				
				if (pop.isShowing()) {
					pop.dismiss();
				}
			}
		});
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	private void getPopList(String pat_no) {
		if (tempDayFees != null) {
			DayFeeAdapter dAdapter = new DayFeeAdapter(DayFeeActivity.this,
					tempDayFees);
			lv_dates.setAdapter(dAdapter);
			return;
		}
		HospitalChargeDayReq req = new HospitalChargeDayReq();
		req.setOperType("121");
		req.setPatNo(pat_no);
		GsonServlet<HospitalChargeDayReq, HospitalChargeDayRes> gServlet = new GsonServlet<HospitalChargeDayReq, HospitalChargeDayRes>(
				this);
		gServlet.request(req, HospitalChargeDayRes.class);
		gServlet.setOnResponseEndListening(new GsonServlet.OnResponseEndListening<HospitalChargeDayReq, HospitalChargeDayRes>() {

			@Override
			public void onResponseEnd(HospitalChargeDayReq commonReq, HospitalChargeDayRes commonRes,
					boolean result, String errmsg, int responseCode) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onResponseEndSuccess(HospitalChargeDayReq commonReq,
					HospitalChargeDayRes commonRes, String errmsg, int responseCode) {
				// TODO Auto-generated method stub
				if (commonRes != null) {
					tempDayFees = commonRes.getChargeDayInfos();
					DayFeeAdapter dAdapter = new DayFeeAdapter(
							DayFeeActivity.this, tempDayFees);
					lv_dates.setAdapter(dAdapter);
					if(isfirst)
					{
						if (tempDayFees != null) {
							if (tempDayFees.size() > 0) {
								HospitalChargeDayInfo feeList = tempDayFees.get(0);
								tv_rq.setText(feeList.getDays());
								tv_totle.setText("总费用:" + feeList.getDayFee()
										+ "（元）");
								getFeeDetail(strpat_no, feeList.getDays());
								isfirst=false;
							}
						}
					}
				}
			}

			@Override
			public void onResponseEndErr(HospitalChargeDayReq commonReq,
					HospitalChargeDayRes commonRes, String errmsg, int responseCode) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void getFeeDetail(String strpat_no, String strDate) {
		HospitalChargeReq req = new HospitalChargeReq();
		req.setOperType("120");
		req.setPatNo(strpat_no);
		req.setReqDate(strDate);
		GsonServlet<HospitalChargeReq, HospitalChargeRes> gServlet = new GsonServlet<HospitalChargeReq, HospitalChargeRes>(
				this);
		gServlet.request(req, HospitalChargeRes.class);
		gServlet.setOnResponseEndListening(new GsonServlet.OnResponseEndListening<HospitalChargeReq, HospitalChargeRes>() {

			@Override
			public void onResponseEnd(HospitalChargeReq commonReq,
					HospitalChargeRes commonRes, boolean result, String errmsg,
					int responseCode) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onResponseEndSuccess(HospitalChargeReq commonReq,
					HospitalChargeRes commonRes, String errmsg, int responseCode) {
				// TODO Auto-generated method stub
				if (commonRes != null) {
					tempCharge = commonRes.getChargeInfos();
					ZlDayChargeAdapter chargeAdapter = new ZlDayChargeAdapter(
							DayFeeActivity.this, tempCharge);
					lv_feeItems.setAdapter(chargeAdapter);
					
					
				}
			}

			@Override
			public void onResponseEndErr(HospitalChargeReq commonReq,
					HospitalChargeRes commonRes, String errmsg, int responseCode) {
				// TODO Auto-generated method stub

			}
		});
	}
}
