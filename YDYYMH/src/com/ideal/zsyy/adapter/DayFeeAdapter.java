package com.ideal.zsyy.adapter;

import java.util.List;

import com.jijiang.wtapp.R;
import com.ideal.zsyy.entity.DayFeeList;
import com.ideal.zsyy.entity.HospitalChargeDayInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DayFeeAdapter extends BaseAdapter {

	private Context context;
	private List<HospitalChargeDayInfo> dayList;

	public DayFeeAdapter(Context context, List<HospitalChargeDayInfo> dlist) {
		this.context = context;
		this.dayList = dlist;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (dayList != null) {
			return dayList.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		if (dayList != null) {
			return dayList.get(arg0);
		}
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		HospitalChargeDayInfo dList = null;
		LayoutInflater inflater = LayoutInflater.from(context);
		LinearLayout layout = (LinearLayout) inflater.inflate(
				R.layout.dayfee_pop_list_item, null);
		TextView tv_time = (TextView) layout.findViewById(R.id.tv_dates);
		dList = dayList.get(arg0);
		if (dList != null) {
			
			tv_time.setText(dList.getDays());
		}
		return layout;
	}

}
