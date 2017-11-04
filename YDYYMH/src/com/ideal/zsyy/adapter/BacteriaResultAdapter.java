package com.ideal.zsyy.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jijiang.wtapp.R;
import com.ideal.zsyy.entity.TbLisBacteriaResult;

public class BacteriaResultAdapter extends BaseAdapter {

	
	private List<TbLisBacteriaResult> tbbrs;
	private LayoutInflater mInflater;
	
	public BacteriaResultAdapter(Context context,List<TbLisBacteriaResult> tbbrs){
		this.mInflater = LayoutInflater.from(context);
		this.tbbrs = tbbrs;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return tbbrs.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return tbbrs.get(position);
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
			convertView = mInflater.inflate(R.layout.bacteriaresult_item, null);
			holder = new ViewHolder();
			holder.tv_jcjg = (TextView) convertView.findViewById(R.id.tv_jcjg);
			holder.tv_jcjgwz = (TextView) convertView.findViewById(R.id.tv_jcjgwz);
			holder.tv_xjmc = (TextView) convertView.findViewById(R.id.tv_xjmc);
			holder.tv_bgrq = (TextView) convertView.findViewById(R.id.tv_bgrq);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		TbLisBacteriaResult bacteriaResult = tbbrs.get(position);
		holder.tv_jcjg.setText(bacteriaResult.getJcjg());
		holder.tv_jcjgwz.setText(bacteriaResult.getJcjgwz());
		holder.tv_xjmc.setText(bacteriaResult.getXjmc());
		holder.tv_bgrq.setText(bacteriaResult.getBgrq());
		
		return convertView;
	}
	static class ViewHolder{
		TextView tv_xjmc;
		TextView tv_jcjg;
		TextView tv_jcjgwz;
		TextView tv_bgrq;
	}
}
