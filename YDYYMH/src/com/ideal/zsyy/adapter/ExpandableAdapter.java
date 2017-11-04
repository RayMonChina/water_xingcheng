package com.ideal.zsyy.adapter;

import java.util.List;

import org.jivesoftware.smackx.pubsub.ChildrenAssociationPolicy;

import com.jijiang.wtapp.R;
import com.ideal.zsyy.entity.DeptDutyInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class ExpandableAdapter extends BaseExpandableListAdapter {

	private List<String> deptdutygroups;
	private List<List<DeptDutyInfo>> deptdutychilds;
	private LayoutInflater mInflater;
	public ExpandableAdapter(Context context,List<String> deptdutygroups,List<List<DeptDutyInfo>> deptdutychilds){
		this.deptdutychilds = deptdutychilds;
		this.deptdutygroups = deptdutygroups;
		this.mInflater = LayoutInflater.from(context);
	}
	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return deptdutygroups.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return deptdutychilds.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return deptdutygroups.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return deptdutychilds.get(groupPosition).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		String dutyInfo = deptdutygroups.get(groupPosition);
		View view = mInflater.inflate(R.layout.order_deptinfo_group_item, null);
		TextView tvGroup = (TextView) view.findViewById(R.id.textGroup);
		tvGroup.setText(dutyInfo);
		return view;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		DeptDutyInfo dutyInfo = deptdutychilds.get(groupPosition).get(childPosition);
		View view1 = mInflater.inflate(R.layout.deptduty_list_item2, null);
		TextView itemTv1 = (TextView) view1.findViewById(R.id.itemTv1);
		TextView itemTv2 = (TextView) view1.findViewById(R.id.itemTv2);
		itemTv1.setText(dutyInfo.getDept_infosName());
		itemTv2.setText(dutyInfo.getLocate().equals("S")?"南院":"北院");
		return view1;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}
}
