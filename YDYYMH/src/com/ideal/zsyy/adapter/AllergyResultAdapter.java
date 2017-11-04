package com.ideal.zsyy.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jijiang.wtapp.R;
import com.ideal.zsyy.entity.TbLisAllergyResult;

public class AllergyResultAdapter extends BaseAdapter {

	private List<TbLisAllergyResult> tblars;
	private LayoutInflater mInflater;
	
	public AllergyResultAdapter(Context context,List<TbLisAllergyResult> tblars){
		this.mInflater = LayoutInflater.from(context);
		this.tblars = tblars;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return tblars.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return tblars.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.allergyresult_item, null);
			holder = new ViewHolder();
			holder.tv_jcjg = (TextView) convertView.findViewById(R.id.tv_jcjg);
			holder.tv_ymmc = (TextView) convertView.findViewById(R.id.tv_ymmc);
			holder.tv_bgrq = (TextView) convertView.findViewById(R.id.tv_bgrq);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		TbLisAllergyResult allergyResult = tblars.get(position);
		holder.tv_jcjg.setText(allergyResult.getJcjg());
		holder.tv_ymmc.setText(allergyResult.getYmmc()); 
		holder.tv_bgrq.setText(allergyResult.getBgrq()); 
		return convertView;
	}

	static class ViewHolder{
		TextView tv_ymmc;
		TextView tv_jcjg;
		TextView tv_bgrq;
	}
}
