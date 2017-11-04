package com.ideal.zsyy.adapter;

import java.util.List;

import com.ideal.zsyy.entity.QianFeiItem;
import com.jijiang.wtapp.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WUQianfeiAdapter extends BaseAdapter {

	private List<QianFeiItem> yzEntities;
	private LayoutInflater inflater;

	public WUQianfeiAdapter(Context context, List<QianFeiItem> qFList) {
		this.yzEntities = qFList;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		if (null != yzEntities) {
			return yzEntities.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if (null != yzEntities && position < yzEntities.size()) {
			return yzEntities.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	private class ViewHolder {
		private TextView tv_waterUserName;
		private TextView tv_waterPhone;
		private TextView tv_waterUserNO;
		private TextView tv_waterUserAddress;
		private TextView tv_prestore;
		private TextView tv_TOTALFEE;
		private TextView tv_TotalCharge;

		public TextView getTv_waterUserName() {
			return tv_waterUserName;
		}

		public void setTv_waterUserName(TextView tv_waterUserName) {
			this.tv_waterUserName = tv_waterUserName;
		}

		public TextView getTv_waterPhone() {
			return tv_waterPhone;
		}

		public void setTv_waterPhone(TextView tv_waterPhone) {
			this.tv_waterPhone = tv_waterPhone;
		}

		public TextView getTv_waterUserNO() {
			return tv_waterUserNO;
		}

		public void setTv_waterUserNO(TextView tv_waterUserNO) {
			this.tv_waterUserNO = tv_waterUserNO;
		}

		public TextView getTv_waterUserAddress() {
			return tv_waterUserAddress;
		}

		public void setTv_waterUserAddress(TextView tv_waterUserAddress) {
			this.tv_waterUserAddress = tv_waterUserAddress;
		}

		public TextView getTv_prestore() {
			return tv_prestore;
		}

		public void setTv_prestore(TextView tv_prestore) {
			this.tv_prestore = tv_prestore;
		}

		public TextView getTv_TOTALFEE() {
			return tv_TOTALFEE;
		}

		public void setTv_TOTALFEE(TextView tv_TOTALFEE) {
			this.tv_TOTALFEE = tv_TOTALFEE;
		}

		public TextView getTv_TotalCharge() {
			return tv_TotalCharge;
		}

		public void setTv_TotalCharge(TextView tv_TotalCharge) {
			this.tv_TotalCharge = tv_TotalCharge;
		}
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		final QianFeiItem yzEntitiesItem = yzEntities.get(position);
		if (convertView != null) {
			if (convertView.getTag() != null) {
				viewHolder = (ViewHolder) convertView.getTag();
			}
		} else {
			LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.w_user_qianfei_search_item, null);
			TextView tv_waterUserName = (TextView) layout.findViewById(R.id.tv_waterUserName);
			TextView tv_waterPhone = (TextView) layout.findViewById(R.id.tv_waterPhone);
			TextView tv_waterUserNO = (TextView) layout.findViewById(R.id.tv_waterUserNO);
			TextView tv_waterUserAddress = (TextView) layout.findViewById(R.id.tv_waterUserAddress);
			TextView tv_prestore = (TextView) layout.findViewById(R.id.tv_prestore);
			TextView tv_TOTALFEE = (TextView) layout.findViewById(R.id.tv_TOTALFEE);
			TextView tv_TotalCharge = (TextView) layout.findViewById(R.id.tv_TotalCharge);
			viewHolder = new ViewHolder();
			viewHolder.setTv_waterUserName(tv_waterUserName);
			viewHolder.setTv_waterPhone(tv_waterPhone);
			viewHolder.setTv_waterUserNO(tv_waterUserNO);
			viewHolder.setTv_waterUserAddress(tv_waterUserAddress);
			viewHolder.setTv_prestore(tv_prestore);
			viewHolder.setTv_TOTALFEE(tv_TOTALFEE);
			viewHolder.setTv_TotalCharge(tv_TotalCharge);
			convertView = layout;
			convertView.setTag(viewHolder);
		}
		viewHolder.getTv_waterUserName().setText(yzEntitiesItem.getWaterUserName());
		viewHolder.getTv_waterPhone().setText(yzEntitiesItem.getWaterPhone());
		viewHolder.getTv_waterUserNO().setText(yzEntitiesItem.getWaterUserNO());
		viewHolder.getTv_waterUserAddress().setText(yzEntitiesItem.getWaterUserAddress());
		viewHolder.getTv_prestore().setText(String.valueOf(yzEntitiesItem.getPrestore()));
		viewHolder.getTv_TOTALFEE().setText(String.valueOf(yzEntitiesItem.getTOTALFEE()));
		viewHolder.getTv_TotalCharge().setText(String.valueOf(yzEntitiesItem.getTotalCharge()));
		return convertView;
	}

}
