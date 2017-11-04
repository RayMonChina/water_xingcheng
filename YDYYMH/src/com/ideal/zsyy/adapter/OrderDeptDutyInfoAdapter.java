package com.ideal.zsyy.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ideal.zsyy.Config;
import com.jijiang.wtapp.R;
import com.ideal.zsyy.activity.ConfimAppointmentActivity;
import com.ideal.zsyy.activity.LoginActivity;
import com.ideal.zsyy.entity.DeptDutysInfo;
import com.ideal.zsyy.entity.DeptInfo;
import com.ideal.zsyy.utils.DataUtils;
import com.ideal.zsyy.view.WorkPlan;

public class OrderDeptDutyInfoAdapter extends BaseAdapter {
	
//	private List<String> dateTimeStr;
//	private List<List<DeptDutysInfo>> deptdutyinfolists;
	private List<HashMap<String,List<DeptDutysInfo>>> deptdutymaps;
	private DeptInfo deptinfo;
	private LayoutInflater mInflater;
	private Context context;
	private String [] amtimelist;
	private String [] pmtimelist;
	public OrderDeptDutyInfoAdapter(Context context,List<HashMap<String,List<DeptDutysInfo>>> data){
		this.context = context;
		this.deptdutymaps = data;
		this.mInflater = LayoutInflater.from(context);
		amtimelist = DataUtils.amtimelist();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return deptdutymaps.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return deptdutymaps.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		HashMap<String,List<DeptDutysInfo>> hashMap = deptdutymaps.get(position);
		Set<Entry<String,List<DeptDutysInfo>>> entrySet = hashMap.entrySet();
		View linearlayoutview = mInflater.inflate(R.layout.deptlist_listll_item, null);
		LinearLayout linearlayout = (LinearLayout) linearlayoutview.findViewById(R.id.ll_deptduty_item);
		View view = mInflater.inflate(R.layout.deptduty_list_item, null);
		TextView indexTv = (TextView) view.findViewById(R.id.indexTv);
		List<DeptDutysInfo> deptdutyinfos = null;
		String datetimeStr = "";
		for (Entry<String, List<DeptDutysInfo>> entry : entrySet) {
			datetimeStr = entry.getKey();
			deptdutyinfos = entry.getValue();
		}
		indexTv.setText(datetimeStr);
		linearlayout.addView(view);
		for (final DeptDutysInfo deptDutysInfo : deptdutyinfos) {
			View view1 = mInflater.inflate(R.layout.deptduty_list_item2, null);
//			View view1 = new WorkPlan(context);
//			TextView itemTv1 = (TextView) view1.findViewById(R.id.itemTv1);
//			TextView itemTv2 = (TextView) view1.findViewById(R.id.itemTv2);
//			itemTv1.setText(deptDutysInfo.getDept_info_sname());
//			itemTv2.setText(deptDutysInfo.getLocate().equals("S")?"南院":"北院");
//			String[] list = null;
//			String noontype = "";
//			if (deptDutysInfo.getDeptweek_noon().endsWith("01")) {
//				list = amtimelist;
//				noontype = "01";
//			} else {
//				list = pmtimelist;
//				noontype = "02";
//			}
//			MyOnclickListener onclickListener = new MyOnclickListener(list,datetimeStr.substring(0, 11), deptDutysInfo,noontype);
//			view1.setOnClickListener(onclickListener);
			linearlayout.addView(view1);
		}
		return linearlayout;
	}
	
	private class MyOnclickListener implements OnClickListener{

		private String date;
		private DeptDutysInfo deutyinfos;
		private String[] list;
		private String noontype;
		public MyOnclickListener(String[] list, String date,DeptDutysInfo deutyinfos, String noontype){
			this.date = date;
			this.deutyinfos = deutyinfos;
			this.list = list;
			this.noontype = noontype;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setTitle("请选择时间...");
			builder.setItems(list, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					String time = list[which];
					if (Config.phUsers != null) {
						Intent intent = new Intent(context,ConfimAppointmentActivity.class);
						intent.putExtra("dutydate", date);
						intent.putExtra("noontype", noontype);
						intent.putExtra("dutytime", time);
						intent.putExtra("dept_id", deutyinfos.getDept_id());
						intent.putExtra("dept_name", deutyinfos.getDept_info_sname());
						intent.putExtra("doc_id", "");
						intent.putExtra("doc_name", "");
						intent.putExtra("locate", deutyinfos.getLocate());
						context.startActivity(intent); 
						((Activity) context).finish();
					} else {
						AlertDialog.Builder builder = new AlertDialog.Builder(context);
						builder.setTitle("请登录");
						builder.setNeutralButton("确定", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								Intent intent = new Intent(context,LoginActivity.class);
								intent.putExtra("logintype", "docinfo");
								context.startActivity(intent);
							}
						});
						
						builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								
							}
						});
					}
				}
			});
			builder.create().show();
		}
		
	}

}
