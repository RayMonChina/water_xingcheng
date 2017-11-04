package com.ideal.zsyy.adapter;

import java.util.List;

import com.jijiang.wtapp.R;
import com.ideal.zsyy.entity.MZChargeGroupInfo;
import com.ideal.zsyy.entity.MZChargeInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MzChargeExpandAdapter extends BaseExpandableListAdapter {

	private Context context;
	private List<MZChargeGroupInfo> mzInfos;
	private LayoutInflater inflater;

	public MzChargeExpandAdapter(Context context, List<MZChargeGroupInfo> mzList) {
		this.context = context;
		this.mzInfos = mzList;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public Object getChild(int arg0, int arg1) {
		// TODO Auto-generated method stub
		if (mzInfos != null) {
			if (arg0 < mzInfos.size()) {
				if (mzInfos.get(arg0).getChargeInfos() != null
						&& mzInfos.get(arg0).getChargeInfos().size() > arg1) {
					return mzInfos.get(arg0).getChargeInfos().get(arg1);
				}
			}
		}
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		RelativeLayout ll_child = (RelativeLayout) inflater.inflate(
				R.layout.zl_mz_charge_item, null);

		TextView tv_item_name = (TextView) ll_child
				.findViewById(R.id.tv_item_name);
		TextView tv_item_gg = (TextView) ll_child.findViewById(R.id.tv_gg);
		TextView tv_num = (TextView) ll_child.findViewById(R.id.tv_num);
		TextView tv_price = (TextView) ll_child.findViewById(R.id.tv_price);
		TextView tv_totle_price = (TextView) ll_child
				.findViewById(R.id.tv_totle_price);

		MZChargeGroupInfo groupInfo = null;
		MZChargeInfo chargeInfo = null;
		if (mzInfos != null && mzInfos.size() > groupPosition) {
			groupInfo = mzInfos.get(groupPosition);
		}
		if (groupInfo != null) {
			if (groupInfo.getChargeInfos() != null
					&& groupInfo.getChargeInfos().size() > childPosition) {
				chargeInfo = groupInfo.getChargeInfos().get(childPosition);
			}
		}

		if (chargeInfo != null) {
			String strUnit=chargeInfo.getUnit();
			if(strUnit==null||"".equals(strUnit.trim()))
			{
				strUnit="";
			}
			else {
				strUnit="（"+strUnit+"）";
			}
			tv_item_name.setText("名称：" + chargeInfo.getItemName());
			tv_item_gg.setText("规格：" + chargeInfo.getItemGG());
			tv_num.setText("数量：" + chargeInfo.getItemNum()+strUnit);
			tv_price.setText("单价：" + chargeInfo.getItemPrice());
			tv_totle_price.setText("总价：" + chargeInfo.getItemTotle());
		} else {
			tv_item_name.setText("名称：");
			tv_item_gg.setText("规格：");
			tv_num.setText("数量：");
			tv_price.setText("单价：");
			tv_totle_price.setText("总价：");
		}

		return ll_child;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		if (mzInfos != null && mzInfos.size() > groupPosition
				&& mzInfos.get(groupPosition).getChargeInfos() != null) {
			return mzInfos.get(groupPosition).getChargeInfos().size();
		}
		return 0;
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		if (mzInfos != null && mzInfos.size() > groupPosition) {
			return mzInfos.get(groupPosition);
		}
		return null;
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		if (mzInfos != null) {
			return mzInfos.size();
		}
		return 0;
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub

		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LinearLayout ll_group = (LinearLayout) inflater.inflate(
				R.layout.mz_charge_group, null);
		TextView tv_rq = (TextView) ll_group.findViewById(R.id.tv_rq);
		TextView tv_totle = (TextView) ll_group.findViewById(R.id.tv_totle);
		MZChargeGroupInfo groupInfo = null;
		if (mzInfos != null && mzInfos.size() > groupPosition) {
			groupInfo = mzInfos.get(groupPosition);
		}
		if (groupInfo != null) {
			tv_rq.setText("  日期："+groupInfo.getFeeTime());
			tv_totle.setText("总计：" + groupInfo.getTotle() + "（元）");
		} else {

		}
		return ll_group;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}

}
