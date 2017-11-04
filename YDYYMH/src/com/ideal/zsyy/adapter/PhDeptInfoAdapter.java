package com.ideal.zsyy.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.jijiang.wtapp.R;
import com.ideal.zsyy.entity.DeptInfo;

public class PhDeptInfoAdapter extends BaseAdapter implements Filterable {
	private List<DeptInfo> phDeptInfos; // 当前收索到的信息列表
	private List<DeptInfo> oldphDeptInfos;// 所有的信息
	private LayoutInflater inflater;
	private Handler mHandler;

	private List<String> nameList;
	private List<String> floorList;

	public PhDeptInfoAdapter(List<DeptInfo> list, Context context,
			Handler mHandler) {
		this.inflater = LayoutInflater.from(context);
		this.mHandler = mHandler;
		if (phDeptInfos != null) {
			phDeptInfos.clear();
		}
		this.phDeptInfos = list;
		this.oldphDeptInfos = this.phDeptInfos;
		initSomeList();
	}

	private void initSomeList() {
		nameList = new ArrayList<String>();
		floorList = new ArrayList<String>();
		for (DeptInfo deptinfo : phDeptInfos) {
			nameList.add(deptinfo.getDept_Name());
			floorList.add(deptinfo.getDept_Build_no() + "号楼"
					+ deptinfo.getDept_Floor() + "楼");
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return phDeptInfos.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return phDeptInfos.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
//		if (convertView == null) {
			convertView = inflater.inflate(R.layout.deptinfo_item, null);
			holder = new ViewHolder();
			holder.tvDeptname = (TextView) convertView
					.findViewById(R.id.tv_deptname);
			holder.tvDeptfloor = (TextView) convertView
					.findViewById(R.id.tv_deptfloor);
			holder.tvWardfloor = (TextView) convertView
					.findViewById(R.id.tv_wardfloor);
			convertView.setTag(holder);
//		} else {
//			holder = (ViewHolder) convertView.getTag();
//		}

		final DeptInfo deptInfo = phDeptInfos.get(position);
		Spanned spandeptname = Html.fromHtml(deptInfo.getDept_Name());
		if (spandeptname != null) {
			holder.tvDeptname.setText(spandeptname);
		} else {
			holder.tvDeptname.setText(deptInfo.getDept_Name());
		}
		if (deptInfo.getDept_Floor() != null
				&& !"".equals(deptInfo.getDept_Floor())) {

			holder.tvDeptfloor.setText("门诊楼:" + deptInfo.getDept_Floor());
		} else {

			holder.tvDeptfloor.setText("门诊楼:");
		}

		if (deptInfo.getWard_Floor() != null
				&& !"".equals(deptInfo.getWard_Floor())) {
			
			holder.tvWardfloor.setText("住院部:" + deptInfo.getWard_Floor());
		} else {

			holder.tvWardfloor.setText("住院部:");
		}
		return convertView;
	}

	@Override
	public Filter getFilter() {
		// TODO Auto-generated method stub
		Filter filter = new Filter() {

			@Override
			protected FilterResults performFiltering(CharSequence con) {
				// TODO Auto-generated method stub
				FilterResults results = new FilterResults();
				List<DeptInfo> deptinfo = new ArrayList<DeptInfo>();

				if (oldphDeptInfos != null && oldphDeptInfos.size() != 0) {
					for (int i = 0; i < nameList.size(); i++) {
						if (nameList.get(i).contains(con)) {
							String name = nameList.get(i);
							int index = name.indexOf(String.valueOf(con));// 收索关键字的下标
							int length = con.length();
							String highText = name.substring(0, index)
									+ "<font color=#0080ff>"
									+ name.substring(index, index + length)
									+ "</font>"
									+ name.substring(index + length,
											name.length());
							oldphDeptInfos.get(i).setDept_Name(highText);
							deptinfo.add(oldphDeptInfos.get(i));
						} else if (floorList.get(i).contains(con)) {
							deptinfo.add(oldphDeptInfos.get(i));
						}
					}
				}
				results.values = deptinfo;
				results.count = deptinfo.size();
				return results;
			}

			@Override
			protected void publishResults(CharSequence constraint,
					FilterResults results) {
				phDeptInfos = (ArrayList<DeptInfo>) results.values;
				if (results.count > 0) {
					notifyDataSetChanged();
					Message msg = mHandler.obtainMessage(0, phDeptInfos);
					mHandler.sendMessage(msg);
				} else {
					notifyDataSetInvalidated();
				}
			}

		};
		return filter;
	}

	static class ViewHolder {
		TextView tvDeptname;
		TextView tvDeptfloor;
		TextView tvWardfloor;

	}
}
