package com.ideal.zsyy.adapter;

import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jijiang.wtapp.R;
import com.ideal.zsyy.entity.TbLisReport;
import com.ideal.zsyy.entity.TbListReportX;

public class TbReportListAdapter extends BaseAdapter {

	private List<TbListReportX> reportlistdata;
	private LayoutInflater mInflater;
	private SharedPreferences preferences;
	
	public TbReportListAdapter(Context context,List<TbListReportX> reportlistdata,SharedPreferences preferences){
		this.mInflater = LayoutInflater.from(context);
		this.reportlistdata = reportlistdata;
		this.preferences = preferences;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return reportlistdata.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return reportlistdata.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHodler holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.tbreport_item, null);
			holder = new ViewHodler();
			holder.tv_bgdh = (TextView) convertView.findViewById(R.id.tv_bgdh);
			holder.tv_bgrq = (TextView) convertView.findViewById(R.id.tv_bgrq);
			holder.tv_isread = (TextView) convertView.findViewById(R.id.tv_isread);
			holder.tv_sampName = (TextView) convertView.findViewById(R.id.tv_sampName);
			convertView.setTag(holder);
		} else {
			holder = (ViewHodler) convertView.getTag();
		}
		
		TbListReportX report = reportlistdata.get(position);
		holder.tv_sampName.setText(report.getSampleTypeName());
		holder.tv_bgdh.setText("(" + report.getSimpleNo() + ")");
		holder.tv_bgrq.setText(report.getReportTime());
//		boolean isShow = preferences.getBoolean(report.getBgdh(), true);
//		Log.i("i", "-----bgdh:" + report.getBgdh() + "-----isShow:" + isShow);
//		if (isShow) {
//			holder.tv_isread.setVisibility(View.VISIBLE);
//		} else {
//			holder.tv_isread.setVisibility(View.GONE); 
//		}
		
		return convertView;
	}

	static class ViewHodler{
		TextView tv_bgdh;
		TextView tv_bgrq;
		TextView tv_sampName;
		TextView tv_isread;
	}
}
