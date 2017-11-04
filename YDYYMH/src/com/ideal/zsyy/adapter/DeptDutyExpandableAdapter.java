package com.ideal.zsyy.adapter;

import java.util.List;

import org.jivesoftware.smackx.pubsub.ChildrenAssociationPolicy;

import com.jijiang.wtapp.R;
import com.ideal.zsyy.entity.DeptDutyInfo;
import com.ideal.zsyy.entity.DeptDutyList;
import com.ideal.zsyy.entity.DeptDutysInfo;
import com.ideal.zsyy.view.WorkPlan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DeptDutyExpandableAdapter extends BaseExpandableListAdapter {

	private List<String> deptdutygroups;
//	private List<List<DeptDutysInfo>> deptdutychilds;
	private List<DeptDutyList> deptdutychilds;
	private LayoutInflater mInflater;
	private Context context;
	public DeptDutyExpandableAdapter(Context context,List<String> deptdutygroups,List<DeptDutyList> deptdutychilds){
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
		return 1;
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return deptdutygroups.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return deptdutychilds.get(groupPosition);
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
		LinearLayout ll_noontype = (LinearLayout) view.findViewById(R.id.ll_noontype);
		ll_noontype.setVisibility(View.VISIBLE); 
		tvGroup.setText(dutyInfo);
		return view;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View linearlayoutview = mInflater.inflate(R.layout.deptlist_listll_item, null);
		LinearLayout linearlayout = (LinearLayout) linearlayoutview.findViewById(R.id.ll_deptduty_item);
		DeptDutyList dutyList = deptdutychilds.get(groupPosition); 
		WorkPlan view1 = new WorkPlan(linearlayoutview.getContext(),dutyList.getDeptList()); 
		linearlayout.addView(view1); 
		return linearlayoutview; 
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
