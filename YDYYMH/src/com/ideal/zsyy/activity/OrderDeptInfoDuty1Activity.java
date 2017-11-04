package com.ideal.zsyy.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.jijiang.wtapp.R;
import com.ideal.zsyy.adapter.DeptDutyExpandableAdapter;
import com.ideal.zsyy.adapter.ExpandableAdapter;
import com.ideal.zsyy.adapter.OrderDeptDutyInfoAdapter;
import com.ideal.zsyy.entity.DeptDutyInfo;
import com.ideal.zsyy.entity.DeptDutyList;
import com.ideal.zsyy.entity.DeptDutysInfo;
import com.ideal.zsyy.request.DeptDutyInfo1Req;
import com.ideal.zsyy.response.DeptDutyInfo1Res;
import com.ideal.zsyy.utils.DataUtils;
import com.ideal2.base.gson.GsonServlet;

import android.app.Activity;
import android.app.ExpandableListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;

public class OrderDeptInfoDuty1Activity extends Activity {

	private ListView lvDeptinfoDuty;
	private String dept_id;
	private Button btn_hospital_n;
	private Button btn_hospital_s;

	private List<String> deptnamegroup;
	private List<String> deptnamegroup_n;
	private List<String> deptnamegroup_s;
//	private List<List<DeptDutyList>> deptdutychild_n;
//	private List<List<DeptDutyList>> deptdutychild_s;
	
	private List<DeptDutyList> deptdutychild_n;
	private List<DeptDutyList> deptdutychild_s;
	
	private DeptDutyExpandableAdapter adapter_hospital_n;
	private DeptDutyExpandableAdapter adapter_hospital_s;
	private ExpandableListView elvOrderDeptsinfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_deptinfo_duty1);
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
		elvOrderDeptsinfo = (ExpandableListView) findViewById(R.id.elv_order_deptsinfo); 
		
		btn_hospital_n = (Button) findViewById(R.id.btn_hospital_n);
		btn_hospital_s = (Button) findViewById(R.id.btn_hospital_s);
		btn_hospital_n.setOnClickListener(new View.OnClickListener() {

			

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				btn_hospital_n.setBackgroundResource(R.drawable.navigation_tab_left_down);
				btn_hospital_n.setTextColor(getResources().getColor(android.R.color.white));
				btn_hospital_s.setBackgroundResource(R.drawable.navigation_tab_right_up);
				btn_hospital_s.setTextColor(getResources().getColor(R.color.textcolor1));
				if (adapter_hospital_n == null) {
					adapter_hospital_n = new DeptDutyExpandableAdapter(OrderDeptInfoDuty1Activity.this, deptnamegroup_n, deptdutychild_n);
				}
				elvOrderDeptsinfo.setAdapter(adapter_hospital_n);
				for (int i = 0; i < deptnamegroup_n.size(); i++) {
					elvOrderDeptsinfo.expandGroup(i);
				}
			}
		});
		btn_hospital_s.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				btn_hospital_n.setBackgroundResource(R.drawable.navigation_tab_left_up);
				btn_hospital_n.setTextColor(getResources().getColor(R.color.textcolor1));
				btn_hospital_s.setBackgroundResource(R.drawable.navigation_tab_right_down);
				btn_hospital_s.setTextColor(getResources().getColor(android.R.color.white));
				if (adapter_hospital_s == null) {
					adapter_hospital_s = new DeptDutyExpandableAdapter(OrderDeptInfoDuty1Activity.this, deptnamegroup_s, deptdutychild_s);
				}
				elvOrderDeptsinfo.setAdapter(adapter_hospital_s);
				for (int i = 0; i < deptnamegroup_s.size(); i++) {
					elvOrderDeptsinfo.expandGroup(i);
				}
			}
		});
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
					showData(deptinfos);
					adapter_hospital_n = new DeptDutyExpandableAdapter(OrderDeptInfoDuty1Activity.this, deptnamegroup_n, deptdutychild_n);
					elvOrderDeptsinfo.setAdapter(adapter_hospital_n);
					for (int i = 0; i < deptnamegroup_n.size(); i++) {
						elvOrderDeptsinfo.expandGroup(i);
					}
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
	public void showData(List<DeptDutysInfo> deptinfos) {
		deptnamegroup = removeDuplicateWithOrder(deptinfos);
		deptdutychild_n = new ArrayList<DeptDutyList>(); 
		deptdutychild_s = new ArrayList<DeptDutyList>(); 
		deptnamegroup_n = new ArrayList<String>();
		deptnamegroup_s = new ArrayList<String>();
		for (String deptname : deptnamegroup) {
			DeptDutyList deptlist_n = new DeptDutyList();
			DeptDutyList deptlist_s = new DeptDutyList();
			List<DeptDutysInfo> childers_n = new ArrayList<DeptDutysInfo>();
			List<DeptDutysInfo> childers_s = new ArrayList<DeptDutysInfo>();
			for (DeptDutysInfo deptduty : deptinfos) {
				if (deptname.equals(deptduty.getDept_info_sname())) {
					if ("N".equals(deptduty.getLocate())) {
						childers_n.add(deptduty);
					} else if ("S".equals(deptduty.getLocate())){
						childers_s.add(deptduty);
					}
				}
			}
			if (childers_n.size() > 0) {
				deptnamegroup_n.add(deptname);
				deptlist_n.setDeptList(childers_n);
				deptdutychild_n.add(deptlist_n);
			}
			if (childers_s.size() > 0) {
				deptnamegroup_s.add(deptname);
				deptlist_s.setDeptList(childers_s);
				deptdutychild_s.add(deptlist_s);
			} 
		}
	}
	public static List<String> removeDuplicateWithOrder(
			List<DeptDutysInfo> deptdutyinfos) {
		Set set = new HashSet();
		List<String> grouplist = new ArrayList<String>();
		for (Iterator iter = deptdutyinfos.iterator(); iter.hasNext();) {
			DeptDutysInfo element = (DeptDutysInfo) iter.next();
			set.add(element.getDept_info_sname()); 
		}
		grouplist.clear();
		grouplist.addAll(set);
		return grouplist;
	}
}
