package com.ideal.zsyy.adapter;

import java.util.List;

import com.jijiang.wtapp.R;
import com.ideal.zsyy.entity.PhJHQueueforDept;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class PhJHQueueforDeptAdapter extends BaseAdapter {

	private List<PhJHQueueforDept> jhqueuelist;
	private LayoutInflater mInflater;
	public PhJHQueueforDeptAdapter(Context context,List<PhJHQueueforDept> jhqueuelist){
		this.jhqueuelist = jhqueuelist;
		this.mInflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return jhqueuelist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return jhqueuelist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
//		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.jhqueuefordept_item, null);
			holder = new ViewHolder();
			holder.iv_deptclike = (ImageView) convertView.findViewById(R.id.iv_deptclike);
			holder.tv_deptname = (TextView) convertView.findViewById(R.id.tv_deptname);
			convertView.setTag(holder);
//		} else {
//			holder = (ViewHolder) convertView.getTag();
//		}
		PhJHQueueforDept jhQueueforDept = jhqueuelist.get(position);
		holder.tv_deptname.setText(jhQueueforDept.getDept_name());
		if (jhQueueforDept.isClike()) {
			holder.iv_deptclike.setVisibility(View.VISIBLE);
		} else {
			holder.iv_deptclike.setVisibility(View.INVISIBLE);  
		}
		return convertView;
	}

	class ViewHolder{
		TextView tv_deptname;
		ImageView iv_deptclike;
	}
}
