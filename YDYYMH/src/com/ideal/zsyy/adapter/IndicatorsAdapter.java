package com.ideal.zsyy.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jijiang.wtapp.R;
import com.ideal.zsyy.entity.TbLisIndicators;
import com.ideal.zsyy.entity.TbLisIndicatorsX;

public class IndicatorsAdapter extends BaseAdapter {

	private List<TbLisIndicatorsX> tblins;
	private LayoutInflater mInflater;
	
	public IndicatorsAdapter(Context context,List<TbLisIndicatorsX> tblins){
		this.mInflater = LayoutInflater.from(context);
		this.tblins = tblins;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return tblins.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return tblins.get(position);
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
			convertView = mInflater.inflate(R.layout.indicators_item, null);
			holder = new ViewHodler();
			holder.tv_ckz = (TextView) convertView.findViewById(R.id.tv_ckz);
			holder.tv_jczbjg = (TextView) convertView.findViewById(R.id.tv_jczbjg);
			holder.tv_jczbmc = (TextView) convertView.findViewById(R.id.tv_jczbmc);
			holder.tv_jldw = (TextView) convertView.findViewById(R.id.tv_jldw);
			holder.tv_bgrq = (TextView) convertView.findViewById(R.id.tv_bgrq);
			convertView.setTag(holder);
		} else {
			holder = (ViewHodler) convertView.getTag();
		}
		TbLisIndicatorsX indicators = tblins.get(position);
		holder.tv_ckz.setText(indicators.getReferenceValue());
		holder.tv_jczbjg.setText(indicators.getResult());
		holder.tv_jczbmc.setText(indicators.getTestItemName());
		holder.tv_jldw.setText(indicators.getResultUnit());
		holder.tv_bgrq.setText(indicators.getTestTime()); 
		return convertView;
	}

	static class ViewHodler{
		TextView tv_jczbmc;
		TextView tv_jczbjg;
		TextView tv_jldw;
		TextView tv_ckz;
		TextView tv_bgrq;
	}
}
