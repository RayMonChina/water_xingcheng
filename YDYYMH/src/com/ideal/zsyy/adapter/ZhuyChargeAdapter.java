package com.ideal.zsyy.adapter;

import java.util.List;

import com.jijiang.wtapp.R;
import com.ideal.zsyy.entity.HospitalChargeInfo;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ZhuyChargeAdapter extends BaseAdapter {

	private Context context;
	private List<HospitalChargeInfo> chargeInfos;
	private LayoutInflater inflater;

	public ZhuyChargeAdapter(Context context, List<HospitalChargeInfo> lInfos) {
		this.context = context;
		this.chargeInfos = lInfos;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (null != chargeInfos) {
			return chargeInfos.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if (null != chargeInfos && position < chargeInfos.size()) {
			return chargeInfos.get(position);
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
		HospitalChargeInfo ptEntity = chargeInfos.get(position);
		if (convertView != null) {
			if (convertView.getTag() != null) {
				viewHolder = (ViewHolder) convertView.getTag();
			}
		} else {
			RelativeLayout layout = (RelativeLayout) inflater.inflate(
					R.layout.zl_zy_fee_day_item, null);
			TextView tv_type = (TextView) layout.findViewById(R.id.tv_item_type);
			TextView tv_name = (TextView) layout.findViewById(R.id.tv_item_name);
			TextView tv_gg = (TextView) layout.findViewById(R.id.tv_gg);
			TextView tv_num=(TextView)layout.findViewById(R.id.tv_num);
			TextView tv_price=(TextView)layout.findViewById(R.id.tv_price);
			TextView tv_totle=(TextView)layout.findViewById(R.id.tv_totle_price);
			TextView tv_feeTime=(TextView)layout.findViewById(R.id.tv_item_fee_time);
			tv_feeTime.setVisibility(View.GONE);		
			viewHolder = new ViewHolder();
			viewHolder.setTv_Name(tv_name);
			viewHolder.setTv_num(tv_num);
			viewHolder.setTv_price(tv_price);
			viewHolder.setTv_totlePrice(tv_totle);
			viewHolder.setTv_tv_gg(tv_gg);
			viewHolder.setTv_Type(tv_type);
			viewHolder.setTv_feeTime(tv_feeTime);
			convertView = layout;
			convertView.setTag(viewHolder);
		}
		String strUnit=ptEntity.getItemUnit();
		if(strUnit==null||"".equals(strUnit.trim()))
		{
			strUnit="";
		}
		else {
			strUnit="（"+strUnit+"）";
		}
		viewHolder.getTv_Type().setText("项目大类：" + ptEntity.getItemType());
		viewHolder.getTv_Name().setText("项目名称：" + ptEntity.getItemName());
		viewHolder.getTv_tv_gg().setText("规格：" + ptEntity.getItemGG());
		viewHolder.getTv_num().setText("数量："+ptEntity.getItemNum()+strUnit);
		viewHolder.getTv_price().setText("单价："+ptEntity.getItemPrice());
		viewHolder.getTv_totlePrice().setText("总价："+ptEntity.getTotlePrice());
		viewHolder.getTv_feeTime().setText("费用时间："+ptEntity.getAddDate());
		return convertView;
	}

	public class ViewHolder {
		private TextView tv_Type;// 项目大类
		private TextView tv_Name;// 项目名称
		private TextView tv_tv_gg;// 规格
		private TextView tv_num;// 数量
		private TextView tv_price;// 单价
		private TextView tv_totlePrice;// 总价
		private TextView tv_feeTime;//费用发生时间

		public TextView getTv_Type() {
			return tv_Type;
		}

		public void setTv_Type(TextView tv_Type) {
			this.tv_Type = tv_Type;
		}

		public TextView getTv_Name() {
			return tv_Name;
		}

		public void setTv_Name(TextView tv_Name) {
			this.tv_Name = tv_Name;
		}

		public TextView getTv_tv_gg() {
			return tv_tv_gg;
		}

		public void setTv_tv_gg(TextView tv_tv_gg) {
			this.tv_tv_gg = tv_tv_gg;
		}

		public TextView getTv_num() {
			return tv_num;
		}

		public void setTv_num(TextView tv_num) {
			this.tv_num = tv_num;
		}

		public TextView getTv_price() {
			return tv_price;
		}

		public void setTv_price(TextView tv_price) {
			this.tv_price = tv_price;
		}

		public TextView getTv_totlePrice() {
			return tv_totlePrice;
		}

		public void setTv_totlePrice(TextView tv_totlePrice) {
			this.tv_totlePrice = tv_totlePrice;
		}

		public TextView getTv_feeTime() {
			return tv_feeTime;
		}

		public void setTv_feeTime(TextView tv_feeTime) {
			this.tv_feeTime = tv_feeTime;
		}
		
	}

}
