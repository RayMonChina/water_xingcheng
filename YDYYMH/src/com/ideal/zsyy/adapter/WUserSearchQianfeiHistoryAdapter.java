package com.ideal.zsyy.adapter;

import java.util.List;

import com.ideal.zsyy.adapter.WQianfeiAdapter.ViewHolder;
import com.ideal.zsyy.entity.QianFeiHistoryItem;
import com.ideal.zsyy.entity.WCBUserEntity;
import com.jijiang.wtapp.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WUserSearchQianfeiHistoryAdapter extends BaseAdapter {

	private Context context;
	private List<QianFeiHistoryItem> yzEntities;
	private LayoutInflater inflater;

	public WUserSearchQianfeiHistoryAdapter(Context context, List<QianFeiHistoryItem> ptEntities) {
		this.context = context;
		this.yzEntities = ptEntities;
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		final QianFeiHistoryItem yzEntitiesItem = yzEntities.get(position);
		if (convertView != null) {
			if (convertView.getTag() != null) {
				viewHolder = (ViewHolder) convertView.getTag();
			}
		} else {
			LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.w_user_qianfei_result_item, null);
			TextView tv_waterUserName = (TextView) layout.findViewById(R.id.tv_waterUserName);
			TextView tv_waterPhone = (TextView) layout.findViewById(R.id.tv_waterPhone);
			TextView tv_readMeterRecordMonth = (TextView) layout.findViewById(R.id.tv_readMeterRecordMonth);
			TextView tv_waterUserNO = (TextView) layout.findViewById(R.id.tv_waterUserNO);
			TextView tv_waterUserAddress = (TextView) layout.findViewById(R.id.tv_waterUserAddress);
			TextView tv_WATERUSERQQYE = (TextView) layout.findViewById(R.id.tv_WATERUSERQQYE);
			TextView tv_totalCharge = (TextView) layout.findViewById(R.id.tv_totalCharge);
			TextView tv_totalNumber = (TextView) layout.findViewById(R.id.tv_totalNumber);
			TextView tv_WATERUSERJSYE = (TextView) layout.findViewById(R.id.tv_WATERUSERJSYE);
			TextView tv_waterMeterLastNumber = (TextView) layout.findViewById(R.id.tv_waterMeterLastNumber);
			TextView tv_waterMeterEndNumber = (TextView) layout.findViewById(R.id.tv_waterMeterEndNumber);
			viewHolder = new ViewHolder();
			viewHolder.setTv_waterUserName(tv_waterUserName);
			viewHolder.setTv_waterPhone(tv_waterPhone);
			viewHolder.setTv_readMeterRecordMonth(tv_readMeterRecordMonth);
			viewHolder.setTv_waterUserNO(tv_waterUserNO);
			viewHolder.setTv_waterUserAddress(tv_waterUserAddress);
			viewHolder.setTv_WATERUSERQQYE(tv_WATERUSERQQYE);
			viewHolder.setTv_totalCharge(tv_totalCharge);
			viewHolder.setTv_totalNumber(tv_totalNumber);
			viewHolder.setTv_WATERUSERJSYE(tv_WATERUSERJSYE);
			viewHolder.setTv_waterMeterLastNumber(tv_waterMeterLastNumber);
			viewHolder.setTv_waterMeterEndNumber(tv_waterMeterEndNumber);
			convertView = layout;
			convertView.setTag(viewHolder);
		}
		viewHolder.getTv_waterUserName().setText(yzEntitiesItem.getWaterUserName());
		viewHolder.getTv_waterPhone().setText(yzEntitiesItem.getWaterPhone());
		viewHolder.getTv_readMeterRecordMonth().setText(String.valueOf(yzEntitiesItem.getReadMeterRecordMonth()) + "月");
		viewHolder.getTv_waterUserNO().setText(yzEntitiesItem.getWaterUserNO());
		viewHolder.getTv_waterUserAddress().setText(yzEntitiesItem.getWaterUserAddress());
		viewHolder.getTv_WATERUSERQQYE().setText(String.valueOf(yzEntitiesItem.getWATERUSERQQYE()));
		viewHolder.getTv_totalCharge().setText(String.valueOf(yzEntitiesItem.getTotalCharge()));
		viewHolder.getTv_totalNumber().setText(String.valueOf(yzEntitiesItem.getTotalNumber()));
		viewHolder.getTv_WATERUSERJSYE().setText(String.valueOf(yzEntitiesItem.getWATERUSERJSYE()));
		viewHolder.getTv_waterMeterLastNumber().setText(String.valueOf(yzEntitiesItem.getWaterMeterLastNumber()));
		viewHolder.getTv_waterMeterEndNumber().setText(String.valueOf(yzEntitiesItem.getWaterMeterEndNumber()));

		return convertView;
	}

	public class ViewHolder {
		private TextView tv_waterUserName;
		private TextView tv_waterPhone;
		private TextView tv_readMeterRecordMonth;
		private TextView tv_waterUserNO;
		private TextView tv_waterUserAddress;
		private TextView tv_WATERUSERQQYE;
		private TextView tv_totalCharge;
		private TextView tv_totalNumber;
		private TextView tv_WATERUSERJSYE;
		private TextView tv_waterMeterLastNumber;
		private TextView tv_waterMeterEndNumber;

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

		public TextView getTv_readMeterRecordMonth() {
			return tv_readMeterRecordMonth;
		}

		public void setTv_readMeterRecordMonth(TextView tv_readMeterRecordMonth) {
			this.tv_readMeterRecordMonth = tv_readMeterRecordMonth;
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

		public TextView getTv_WATERUSERQQYE() {
			return tv_WATERUSERQQYE;
		}

		public void setTv_WATERUSERQQYE(TextView tv_WATERUSERQQYE) {
			this.tv_WATERUSERQQYE = tv_WATERUSERQQYE;
		}

		public TextView getTv_totalCharge() {
			return tv_totalCharge;
		}

		public void setTv_totalCharge(TextView tv_totalCharge) {
			this.tv_totalCharge = tv_totalCharge;
		}

		public TextView getTv_totalNumber() {
			return tv_totalNumber;
		}

		public void setTv_totalNumber(TextView tv_totalNumber) {
			this.tv_totalNumber = tv_totalNumber;
		}

		public TextView getTv_WATERUSERJSYE() {
			return tv_WATERUSERJSYE;
		}

		public void setTv_WATERUSERJSYE(TextView tv_WATERUSERJSYE) {
			this.tv_WATERUSERJSYE = tv_WATERUSERJSYE;
		}

		public TextView getTv_waterMeterLastNumber() {
			return tv_waterMeterLastNumber;
		}

		public void setTv_waterMeterLastNumber(TextView tv_waterMeterLastNumber) {
			this.tv_waterMeterLastNumber = tv_waterMeterLastNumber;
		}

		public TextView getTv_waterMeterEndNumber() {
			return tv_waterMeterEndNumber;
		}

		public void setTv_waterMeterEndNumber(TextView tv_waterMeterEndNumber) {
			this.tv_waterMeterEndNumber = tv_waterMeterEndNumber;
		}

	}
}
