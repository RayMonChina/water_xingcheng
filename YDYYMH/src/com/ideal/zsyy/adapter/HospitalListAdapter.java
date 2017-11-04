package com.ideal.zsyy.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jijiang.wtapp.R;
import com.ideal.zsyy.entity.BasiHosInfo;
import com.ideal.zsyy.entity.PhHospitalInfo;

public class HospitalListAdapter extends BaseAdapter {

	private List<BasiHosInfo> hospitallist;
	private LayoutInflater mInflater;
	
	public HospitalListAdapter(Context context,List<BasiHosInfo> hospitallist){
		this.hospitallist = hospitallist;
		this.mInflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return hospitallist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return hospitallist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHodler hodler = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.hospitallist_item, null);
			hodler = new ViewHodler();
//			hodler.iv_hosp_icon = (ImageView) convertView.findViewById(R.id.iv_hosp_icon);
//			hodler.iv_hosp_bg = (ImageView) convertView.findViewById(R.id.iv_hosp_bg);
			hodler.tv_hospName = (TextView) convertView.findViewById(R.id.tv_hospName);
			convertView.setTag(hodler);
		} else {
			hodler = (ViewHodler) convertView.getTag();
		}
		
		BasiHosInfo hospitalInfo = hospitallist.get(position);
//		int iv_hops_icon = Integer.parseInt(hospitalInfo.getHosp_Url());
//		hodler.iv_hosp_icon.setImageResource(iv_hops_icon); 
		hodler.tv_hospName.setText(hospitalInfo.getHosName());  
//		hodler.iv_hosp_bg.setVisibility(View.INVISIBLE); 
		return convertView;
	}

	static class ViewHodler{
//		ImageView iv_hosp_icon;
//		ImageView iv_hosp_bg;
		TextView tv_hospName;
	}
}
