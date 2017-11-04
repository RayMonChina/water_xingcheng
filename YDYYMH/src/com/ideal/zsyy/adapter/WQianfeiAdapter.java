package com.ideal.zsyy.adapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.ideal.zsyy.entity.QianFeiMerge;
import com.jijiang.wtapp.R;

import android.R.string;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WQianfeiAdapter extends BaseAdapter {

	public ArrayList<String> MonthList = new ArrayList<String>();
	private List<QianFeiMerge> qfMerge;
	private LayoutInflater inflater;

	public WQianfeiAdapter(Context context, List<QianFeiMerge> qf) {
		this.qfMerge = qf;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		if (null != qfMerge) {
			return qfMerge.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if (null != qfMerge && position < qfMerge.size()) {
			return qfMerge.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public class ViewHolder {
		private CheckBox CB_Month;
		private TextView TV_Month;
		private TextView TV_Info;

		public CheckBox getCB_Month() {
			return CB_Month;
		}

		public void setCB_Month(CheckBox cB_Month) {
			CB_Month = cB_Month;
		}
		
//		public void setCB_Tag(string month) {
//			CB_Month.setTag(month);
//		}
//		
		public TextView getTV_Month() {
			return TV_Month;
		}
		
//		public String getCB_Tag() {
//			return CB_Month.getTag().toString();
//		}
		
		public void setTV_Month(TextView tV_Month) {
			TV_Month = tV_Month;
		}

		public TextView getTV_Info() {
			return TV_Info;
		}

		public void setTV_Info(TextView tV_Info) {
			TV_Info = tV_Info;
		}

	}

	@SuppressLint({ "InflateParams", "DefaultLocale" })
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		final QianFeiMerge qfMergeItem = qfMerge.get(position);
		if (convertView != null) {
			if (convertView.getTag() != null) {
				viewHolder = (ViewHolder) convertView.getTag();
			}
		} else {
			LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.w_cb_qianfei_history_month, null);
			TextView TV_Month = (TextView) layout.findViewById(R.id.TV_Month);
			TextView TV_Info = (TextView) layout.findViewById(R.id.TV_Info);
			final CheckBox CB_Month = (CheckBox) layout.findViewById(R.id.CB_Month);
			CB_Month.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//					String Mon = String.format("%d%d", qfMergeItem.getReadMeterRecordYear(),
//							qfMergeItem.getReadMeterRecordMonth());
					String Mon=CB_Month.getTag().toString();
					if (isChecked) {
						MonthList.add(Mon);

					} else {
						MonthList.remove(Mon);
					}
					
				}
			});
			viewHolder = new ViewHolder();
			viewHolder.setTV_Month(TV_Month);
			viewHolder.setTV_Info(TV_Info);
			viewHolder.setCB_Month(CB_Month);
			convertView = layout;
			convertView.setTag(viewHolder);
		}
		try {
			BigDecimal TotalCharge = new BigDecimal(qfMergeItem.getTotalCharge());
			String Montrstr = String.format("%d年%d月", qfMergeItem.getReadMeterRecordYear(),
					qfMergeItem.getReadMeterRecordMonth());
			String Infostr = String.format("欠费用户-%d；水量-%d；欠费-%s", qfMergeItem.getUserCount(),
					(int) qfMergeItem.getTotalNumber(),
					TotalCharge.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
			String Mon = String.format("%d%d", qfMergeItem.getReadMeterRecordYear(),
					qfMergeItem.getReadMeterRecordMonth());
			viewHolder.getTV_Month().setText(Montrstr);
			viewHolder.getTV_Info().setText(Infostr);
			viewHolder.getCB_Month().setTag(Mon);
		} catch (Exception e) {

		}
		return convertView;
	}
}
