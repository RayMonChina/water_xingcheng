package com.ideal.zsyy.activity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jijiang.wtapp.R;
import com.ideal.zsyy.adapter.IndicatorsAdapter;
import com.ideal.zsyy.entity.TbLisIndicatorsX;
import com.ideal.zsyy.request.ReportDetailReq;
import com.ideal.zsyy.response.ReportDetailXRes;
import com.ideal.zsyy.service.PreferencesService;
import com.ideal2.base.gson.GsonServlet;
import com.ideal2.base.gson.GsonServlet.OnResponseEndListening;

public class TbReportDetailActivity extends Activity {

	private String bgdh;
	private LinearLayout ll_tblins;
	private ListView lv_tblins;
	private PreferencesService preService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reportdetail_info1);
		initData();
		initView();
		if(bgdh!=null)
		{
			if(bgdh.indexOf("【")>0)
			{
				bgdh=bgdh.substring(0,bgdh.indexOf("【"));	
			}
			if(!preService.getIsLogin())
			{
				Intent intent=new Intent(TbReportDetailActivity.this,LoginActivity.class);
				intent.putExtra("logintype", "reportDetail");
				startActivityForResult(intent, 1);
			}
			else {
				queryData();
			}
		}
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		queryData();
	}

	private void initData() {
		preService=new PreferencesService(TbReportDetailActivity.this);
		Intent intent = getIntent();
		bgdh = intent.getStringExtra("bgdh");
	}

	private void initView() {
		Button btn_back = (Button) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();

			}
		});

		ll_tblins = (LinearLayout) findViewById(R.id.ll_tblins);
		lv_tblins = (ListView) findViewById(R.id.lv_tblins);

		TextView tv_hyjg_bgdh = (TextView) findViewById(R.id.tv_hyjg_bgdh);
		tv_hyjg_bgdh.setText("化验结果(No:" + bgdh + ")");



	}
	


	private void queryData() {
		ReportDetailReq req = new ReportDetailReq();
		req.setBgdh(bgdh);
		req.setOperType("28");
		GsonServlet<ReportDetailReq, ReportDetailXRes> gServlet = new GsonServlet<ReportDetailReq, ReportDetailXRes>(
				this);
		gServlet.request(req, ReportDetailXRes.class);
		gServlet.setOnResponseEndListening(new OnResponseEndListening<ReportDetailReq, ReportDetailXRes>() {
			@Override
			public void onResponseEnd(ReportDetailReq commonReq,
					ReportDetailXRes commonRes, boolean result, String errmsg,
					int responseCode) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onResponseEndSuccess(ReportDetailReq commonReq,
					ReportDetailXRes commonRes, String errmsg, int responseCode) {
				// TODO Auto-generated method stub
				if (commonRes != null) {
					List<TbLisIndicatorsX> tbbrs = commonRes.getIndicatorsx();
					if (tbbrs == null || tbbrs.size() <= 0) {
						ll_tblins.setVisibility(View.GONE);
					} else {
						ll_tblins.setVisibility(View.VISIBLE);
						IndicatorsAdapter inAdapter = new IndicatorsAdapter(
								TbReportDetailActivity.this, tbbrs);
						lv_tblins.setAdapter(inAdapter);
					}
				}
			}

			@Override
			public void onResponseEndErr(ReportDetailReq commonReq,
					ReportDetailXRes commonRes, String errmsg, int responseCode) {
				// TODO Auto-generated method stub

			}
		});
	}

	public static int setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return 0;
		}

		int totalHeight = 0;
		if (listAdapter.getCount() > 8) {
			for (int i = 0; i < 8; i++) {
				View listItem = listAdapter.getView(i, null, listView);
				listItem.measure(0, 0);
				totalHeight += listItem.getMeasuredHeight();
			}
		} else {
			for (int i = 0; i < listAdapter.getCount(); i++) {
				View listItem = listAdapter.getView(i, null, listView);
				listItem.measure(0, 0);
				totalHeight += listItem.getMeasuredHeight();
			}

		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
		return totalHeight;
	}

//	private void setParentScollAble(boolean flag) {
//		this.parentScroll.requestDisallowInterceptTouchEvent(!flag);
//	}
}
