package com.ideal.zsyy.adapter;

import java.util.List;

import com.ideal.zsyy.entity.WCustomerAdviceInfo;
import com.jijiang.wtapp.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WCBAdviceAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private Context _context;
	private List<WCustomerAdviceInfo> adviceInfos;

	public WCBAdviceAdapter(Context context, List<WCustomerAdviceInfo> adviceInfos) {
		this._context = context;
		inflater = LayoutInflater.from(context);
		this.adviceInfos = adviceInfos;
	}

	@Override
	public int getCount() {

		if (adviceInfos != null) {
			return adviceInfos.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int arg0) {

		if (adviceInfos != null) {
			return adviceInfos.get(arg0);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {

		return 0;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder = null;
		WCustomerAdviceInfo ptEntity = adviceInfos.get(position);
		if (convertView != null) {
			if (convertView.getTag() != null) {
				viewHolder = (ViewHolder) convertView.getTag();
			}
		} else {
			LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.wcb_advice_item, null);
			TextView tv_username = (TextView) layout.findViewById(R.id.tv_name);// 户名
			TextView tv_date = (TextView) layout.findViewById(R.id.tv_date);// 状态
			TextView tv_content = (TextView) layout.findViewById(R.id.tv_content);// 用户编码
			viewHolder = new ViewHolder();
			viewHolder.setTv_username(tv_username);
			viewHolder.setTv_date(tv_date);
			viewHolder.setTv_content(tv_content);

			convertView = layout;
			convertView.setTag(viewHolder);
		}

		viewHolder.getTv_username().setText(ptEntity.getUserName());
		viewHolder.getTv_date().setText(ptEntity.getAddDate());
		viewHolder.getTv_content().setText(ptEntity.getAdvice());
		return convertView;
	}

	private class ViewHolder {
		private TextView tv_username;// 户名
		private TextView tv_date;// 日期
		private TextView tv_content;// 用户编码

		public TextView getTv_username() {
			return tv_username;
		}

		public void setTv_username(TextView tv_username) {
			this.tv_username = tv_username;
		}

		public TextView getTv_date() {
			return tv_date;
		}

		public void setTv_date(TextView tv_date) {
			this.tv_date = tv_date;
		}

		public TextView getTv_content() {
			return tv_content;
		}

		public void setTv_content(TextView tv_content) {
			this.tv_content = tv_content;
		}

	}
}
