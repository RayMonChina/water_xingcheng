package com.ideal.zsyy.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.jijiang.wtapp.R;
import com.ideal.zsyy.adapter.OrderDeptDutyInfoAdapter;
import com.ideal.zsyy.entity.DeptDutysInfo;
import com.ideal.zsyy.request.DeptDutyInfo1Req;
import com.ideal.zsyy.response.DeptDutyInfo1Res;
import com.ideal.zsyy.utils.DataUtils;
import com.ideal2.base.gson.GsonServlet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class OrderDeptInfoDutyActivity extends Activity {

	private ListView lvDeptinfoDuty;
	private String dept_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_deptinfo_duty);
		Intent intent = getIntent();
		dept_id = intent.getStringExtra("dept_id"); 
		initView();
		queryData(dept_id); 
	}

	private void initView() {
		// TODO Auto-generated method stub
		// 返回
		Button btnBack = (Button) findViewById(R.id.btn_back);
		btnBack.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		lvDeptinfoDuty = (ListView) findViewById(R.id.lv_deptInfo_duty); 
	}

	public void queryData(String dept_id) {
		DeptDutyInfo1Req req_duty = new DeptDutyInfo1Req();
		req_duty.setDept_id(dept_id);
		req_duty.setOperType("35");
		GsonServlet<DeptDutyInfo1Req, DeptDutyInfo1Res> gsonServlet = new GsonServlet<DeptDutyInfo1Req, DeptDutyInfo1Res>(this);
		gsonServlet.request(req_duty, DeptDutyInfo1Res.class);
		gsonServlet.setOnResponseEndListening(new GsonServlet.OnResponseEndListening<DeptDutyInfo1Req, DeptDutyInfo1Res>() {
			@Override
			public void onResponseEnd(DeptDutyInfo1Req commonReq,
					DeptDutyInfo1Res commonRes, boolean result, String errmsg,
					int responseCode) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onResponseEndSuccess(DeptDutyInfo1Req commonReq,
					DeptDutyInfo1Res commonRes, String errmsg, int responseCode) {
				// TODO Auto-generated method stub
				if (commonRes != null) {
					List<DeptDutysInfo> deptinfos = commonRes.getDeptinfos();
					List<HashMap<String,List<DeptDutysInfo>>> data = showData(deptinfos);
					OrderDeptDutyInfoAdapter adapter = new OrderDeptDutyInfoAdapter(OrderDeptInfoDutyActivity.this,data);
					lvDeptinfoDuty.setAdapter(adapter);
				}
			}

			@Override
			public void onResponseEndErr(DeptDutyInfo1Req commonReq,
					DeptDutyInfo1Res commonRes, String errmsg, int responseCode) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	@SuppressWarnings("deprecation")
	public static List<HashMap<String, List<DeptDutysInfo>>> showData(List<DeptDutysInfo> deptinfos) {
		List<String> dateTimeStr = DataUtils.getDateTimeStr(28, true);
		List<HashMap<String, List<DeptDutysInfo>>> deptdutyinfolists = new ArrayList<HashMap<String, List<DeptDutysInfo>>>();
		for (String datetime : dateTimeStr) {
		HashMap<String, List<DeptDutysInfo>> deptdutymaps = new HashMap<String, List<DeptDutysInfo>>();
			String[] split = datetime.split(" ");
			String noontype = split[0];
			String date = split[1];
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			List<DeptDutysInfo> deptdutysinfos = new ArrayList<DeptDutysInfo>();
			Date parse = null;
			try {
				parse = sdf.parse(date);
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String day = parse.getDay() + "";
			for (DeptDutysInfo deptDutysInfo : deptinfos) {
				if (day.equals(deptDutysInfo.getDeptweek_day())
						&& deptDutysInfo.getDeptweek_noon().endsWith(noontype)) {
					deptdutysinfos.add(deptDutysInfo);
				}
			}
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd E");
			String datetimes = sdf1.format(parse);
			if (noontype.equals("01")) {
				datetimes += " 上午";
			} else if (noontype.equals("02")) {
				datetimes += " 下午";
			}
			if (deptdutysinfos.size() > 0) {
				deptdutymaps.put(datetimes, deptdutysinfos);
				deptdutyinfolists.add(deptdutymaps);
			}
		}
		return deptdutyinfolists;
	}
}
