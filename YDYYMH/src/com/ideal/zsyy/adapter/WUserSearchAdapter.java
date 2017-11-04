package com.ideal.zsyy.adapter;

import java.util.List;
import com.jijiang.wtapp.R;
import com.ideal.zsyy.entity.WUserItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WUserSearchAdapter extends BaseAdapter {

	private Context context;
	private List<WUserItem> yzEntities;
	private LayoutInflater inflater;

	public WUserSearchAdapter(Context context, List<WUserItem> ptEntities) {
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
		WUserItem ptEntity = yzEntities.get(position);
		if (convertView != null) {
			if (convertView.getTag() != null) {
				viewHolder = (ViewHolder) convertView.getTag();
			}
		} else {
			LinearLayout layout = (LinearLayout) inflater.inflate(
					R.layout.w_user_search_item, null);
			TextView tv_yhbm = (TextView) layout
					.findViewById(R.id.tv_yhbm);
			TextView tv_xm = (TextView) layout
					.findViewById(R.id.tv_xm);
			TextView tv_dh = (TextView) layout.findViewById(R.id.tv_dh);
			
			TextView tv_dz = (TextView) layout.findViewById(R.id.tv_dz);
			TextView tv_syzd = (TextView) layout.findViewById(R.id.tv_syzd);
			TextView tv_byzd = (TextView) layout.findViewById(R.id.tv_byzd);
			TextView tv_sl = (TextView) layout.findViewById(R.id.tv_sl);
			
			viewHolder = new ViewHolder();
			viewHolder.setTv_byzd(tv_byzd);
			viewHolder.setTv_dh(tv_dh);
			viewHolder.setTv_dz(tv_dz);
			viewHolder.setTv_sl(tv_sl);
			viewHolder.setTv_syzd(tv_syzd);
			viewHolder.setTv_xm(tv_xm);
			viewHolder.setTv_yhbm(tv_yhbm);
			convertView = layout;
			convertView.setTag(viewHolder);
		}
		viewHolder.getTv_byzd().setText(String.valueOf(ptEntity.getByzd()));
		viewHolder.getTv_dh().setText(ptEntity.getDh());
		viewHolder.getTv_dz().setText(ptEntity.getDz());
		viewHolder.getTv_sl().setText(String.valueOf(ptEntity.getSl()));
		viewHolder.getTv_syzd().setText(String.valueOf(ptEntity.getSyzd()));
		viewHolder.getTv_xm().setText(ptEntity.getXm());
		viewHolder.getTv_yhbm().setText(ptEntity.getYhbm());
		
		return convertView;
	}

	private class ViewHolder {
		private TextView tv_yhbm;// 用户编码
		private TextView tv_xm;// 姓名
		private TextView tv_dh;// 电话
		private TextView tv_dz;//
		private TextView tv_syzd;//
		private TextView tv_byzd;//
		private TextView tv_sl;//
		public TextView getTv_yhbm() {
			return tv_yhbm;
		}
		public void setTv_yhbm(TextView tv_yhbm) {
			this.tv_yhbm = tv_yhbm;
		}
		public TextView getTv_xm() {
			return tv_xm;
		}
		public void setTv_xm(TextView tv_xm) {
			this.tv_xm = tv_xm;
		}
		public TextView getTv_dh() {
			return tv_dh;
		}
		public void setTv_dh(TextView tv_dh) {
			this.tv_dh = tv_dh;
		}
		public TextView getTv_dz() {
			return tv_dz;
		}
		public void setTv_dz(TextView tv_dz) {
			this.tv_dz = tv_dz;
		}
		public TextView getTv_syzd() {
			return tv_syzd;
		}
		public void setTv_syzd(TextView tv_syzd) {
			this.tv_syzd = tv_syzd;
		}
		public TextView getTv_byzd() {
			return tv_byzd;
		}
		public void setTv_byzd(TextView tv_byzd) {
			this.tv_byzd = tv_byzd;
		}
		public TextView getTv_sl() {
			return tv_sl;
		}
		public void setTv_sl(TextView tv_sl) {
			this.tv_sl = tv_sl;
		}
	}

}
