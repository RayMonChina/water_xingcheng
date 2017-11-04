package com.ideal.zsyy.activity;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ideal.wdm.tools.DataCache;
import com.ideal.zsyy.Config;
import com.jijiang.wtapp.R;
import com.ideal.zsyy.adapter.TbReportListAdapter;
import com.ideal.zsyy.entity.TbLisReport;
import com.ideal.zsyy.entity.TbListReportX;
import com.ideal.zsyy.request.ReportReq;
import com.ideal.zsyy.response.ReportRes;
import com.ideal.zsyy.response.ReportXRes;
import com.ideal.zsyy.service.PreferencesService;
import com.ideal2.base.gson.GsonServlet;
import com.ideal2.base.gson.GsonServlet.OnResponseEndListening;

public class TbReportlistActivity extends Activity {

	private ListView reportlist;
	private ReportReq req;
	private List<TbListReportX> reportlistdata;
//	private List<TbLisReport> reportlistdata;
	private LayoutInflater mInflater;
	private PopupWindow pop;
	private TextView tv_rq;
	private RelativeLayout rl_changer;
	private LinearLayout ll_repLayout;
	private TbReportListAdapter adapter;
	private SharedPreferences preferences;
	private DataCache dataCache;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tbreportlist);
		mInflater = LayoutInflater.from(this);
		dataCache = new DataCache(this); 
		preferences = getSharedPreferences(PreferencesService.SPF, 
				Context.MODE_PRIVATE);
		initView();
		initData();
		queryData("1");
	}

	//初始化数据
	private void initData() {
		Intent intent = getIntent();
		String brxm = intent.getStringExtra("brxm");
		String kh = intent.getStringExtra("kh");
		req = new ReportReq();
		req.setBrxm(brxm);
		req.setKh(kh);
		req.setErq("");
		req.setOperType("27");
	}

	private void initView() {
		
		View view = mInflater.inflate(R.layout.rep_pop_view, null);
		pop = new PopupWindow(view,
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, false);
		// 需要设置一下此参数，点击外边可消失
		pop.setBackgroundDrawable(new BitmapDrawable());
		// 设置点击窗口外边窗口消失
		pop.setOutsideTouchable(true);
		// 设置此参数获得焦点，否则无法点击
		pop.setFocusable(true);  
		
		TextView tv_month1 = (TextView) view
				.findViewById(R.id.tv_month1);
		tv_month1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				tv_rq.setText("近一个月");
//				card_type = 0;
				queryData("1");
				pop.dismiss();

			}
		});
		TextView tv_month2 = (TextView) view
				.findViewById(R.id.tv_month2);
		tv_month2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				tv_rq.setText("近两个月");
//				card_type = 0;
				queryData("2");
				pop.dismiss();
				
			}
		});
		TextView tv_month3 = (TextView) view
				.findViewById(R.id.tv_month3);
		tv_month3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				tv_rq.setText("近三个月");
//				card_type = 0;
				queryData("3");
				pop.dismiss();
				
			}
		});
		
		
		Button btn_back = (Button) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();

			}
		});
		reportlist = (ListView) findViewById(R.id.lv_reportlist);
		reportlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (arg2 > 0) {
					TbListReportX report = reportlistdata.get(arg2 - 1);
					String bgdh = report.getSimpleNo();
//					Editor edit = preferences.edit();
//					edit.putBoolean(bgdh, false);
//					edit.commit();
					Intent intent = new Intent(TbReportlistActivity.this,TbReportDetailActivity.class);
					intent.putExtra("bgdh", bgdh);
					startActivity(intent);
				}
			}
		});
		
		
		rl_changer = (RelativeLayout) findViewById(R.id.rl_changer);
		ll_repLayout = (LinearLayout) findViewById(R.id.ll_reportlist);
		
		TextView tv_report_month = (TextView) findViewById(R.id.tv_report_month);
		tv_report_month.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				rl_changer.setVisibility(View.GONE);
				ll_repLayout.setVisibility(View.VISIBLE); 
				queryData("3"); 
			}
		});
	}
	private void queryData(String month) { 
//		if (TextUtils.isEmpty(dataCache.getUrl())) {
//			return;
//		}
		req.setMonth(month);
		GsonServlet<ReportReq, ReportXRes> gServlet = new GsonServlet<ReportReq, ReportXRes>(this);
		gServlet.request(req, ReportXRes.class);
		gServlet.setOnResponseEndListening(new OnResponseEndListening<ReportReq, ReportXRes>() {
			@Override
			public void onResponseEnd(ReportReq commonReq, ReportXRes commonRes,
					boolean result, String errmsg, int responseCode) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onResponseEndSuccess(ReportReq commonReq,
					ReportXRes commonRes, String errmsg, int responseCode) {
				// TODO Auto-generated method stub
				if (commonRes != null) {
					reportlistdata = commonRes.getTblistreportx();
					if (reportlistdata != null && reportlistdata.size() > 0) {
						if (reportlist.getHeaderViewsCount() <= 0) {
							View v = getHeaderView(reportlistdata.get(0));
							reportlist.addHeaderView(v);
						}
//						for (TbLisReport report : reportlistdata) {
//							Editor edit = preferences.edit();
//							edit.putBoolean(report.getBgdh(), true);
//							edit.commit();
//						}
						adapter = new TbReportListAdapter(TbReportlistActivity.this, reportlistdata,preferences);
						reportlist.setAdapter(adapter); 
					} else {
//						Toast.makeText(TbReportlistActivity.this, "无数据", 1).show();
						rl_changer.setVisibility(View.VISIBLE);
						ll_repLayout.setVisibility(View.GONE); 
					}
				}
			}
			@Override
			public void onResponseEndErr(ReportReq commonReq,
					ReportXRes commonRes, String errmsg, int responseCode) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	protected View getHeaderView(TbListReportX tbLisReport) {
		View reportbr_info = mInflater.inflate(R.layout.reportbr_info, null);
		TextView tv_brxm = (TextView) reportbr_info.findViewById(R.id.tv_brxm);
		TextView tv_brxb = (TextView) reportbr_info.findViewById(R.id.tv_brxb);
		TextView tv_brnl = (TextView) reportbr_info.findViewById(R.id.tv_brnl);
		tv_rq = (TextView) reportbr_info.findViewById(R.id.tv_rq);
		tv_brxm.setText(tbLisReport.getPatientName());
		tv_brxb.setText(tbLisReport.getSexName());
		tv_brnl.setText(tbLisReport.getPatientAge() + "岁");
		tv_rq.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View v) {
				if (pop.isShowing()) {
					pop.dismiss();
				} else {
					pop.showAsDropDown(v);
				}
			}
		});
		return reportbr_info;
	}
	
//	@Override
//	protected void onResume() {
//		// TODO Auto-generated method stub
//		super.onResume();
//		if (adapter != null) {
//			adapter.notifyDataSetChanged();
//		}
//	}
}
