package com.ideal.zsyy.adapter;

import java.util.List;

import com.jijiang.wtapp.R;
import com.ideal.zsyy.entity.PtYzEntity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ZlYiZhuAdapter extends BaseAdapter {

	private Context context;
	private List<PtYzEntity> yzEntities;
	private LayoutInflater inflater;

	public ZlYiZhuAdapter(Context context, List<PtYzEntity> ptEntities) {
		this.context = context;
		this.yzEntities = ptEntities;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (null != yzEntities) {
			return yzEntities.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if (null != yzEntities && position < yzEntities.size()) {
			return yzEntities.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		PtYzEntity ptEntity = yzEntities.get(position);
		if (convertView != null) {
			if (convertView.getTag() != null) {
				viewHolder = (ViewHolder) convertView.getTag();
			}
		} else {
			RelativeLayout layout = (RelativeLayout) inflater.inflate(
					R.layout.zl_yz_item, null);
			TextView tv_beginTime = (TextView) layout
					.findViewById(R.id.tv_begintime);
			TextView tv_beginDoctor = (TextView) layout
					.findViewById(R.id.tv_begindoctor);
			TextView tv_yznr = (TextView) layout.findViewById(R.id.tv_yznr);
			viewHolder = new ViewHolder();
			viewHolder.setTv_beginDoctor(tv_beginDoctor);
			viewHolder.setTv_beginTime(tv_beginTime);
			viewHolder.setTv_yzContent(tv_yznr);
			convertView = layout;
			convertView.setTag(viewHolder);
		}
		viewHolder.getTv_beginTime().setText("开始时间：" + ptEntity.getBeginTime());
		viewHolder.getTv_beginDoctor()
				.setText("开始医生：" + ptEntity.getKsDoctor());
		viewHolder.getTv_yzContent().setText("处方内容：" + ptEntity.getYzContent());
		return convertView;
	}

	private class ViewHolder {
		private TextView tv_beginTime;// 开始时间
		private TextView tv_beginDoctor;// 开始医生
		private TextView tv_yzContent;// 医嘱内容

		public TextView getTv_beginTime() {
			return tv_beginTime;
		}

		public void setTv_beginTime(TextView tv_beginTime) {
			this.tv_beginTime = tv_beginTime;
		}

		public TextView getTv_beginDoctor() {
			return tv_beginDoctor;
		}

		public void setTv_beginDoctor(TextView tv_beginDoctor) {
			this.tv_beginDoctor = tv_beginDoctor;
		}

		public TextView getTv_yzContent() {
			return tv_yzContent;
		}

		public void setTv_yzContent(TextView tv_yzContent) {
			this.tv_yzContent = tv_yzContent;
		}

	}

}
