package com.ideal.zsyy.adapter;

import java.util.List;

import com.jijiang.wtapp.R;
import com.ideal.zsyy.entity.PhJHQueueDoctor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class JHQueueDoctorAdapter extends BaseAdapter {

	private List<PhJHQueueDoctor> doctorlist;
	private LayoutInflater mInflater;
	public JHQueueDoctorAdapter(Context context,List<PhJHQueueDoctor> doctorlist){
		this.doctorlist = doctorlist;
		this.mInflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return doctorlist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return doctorlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView = mInflater.inflate(R.layout.jhqueuefordoc_item, null);
		TextView tv_room_no = (TextView) convertView.findViewById(R.id.tv_room_no);
		TextView tv_doctor = (TextView) convertView.findViewById(R.id.tv_doctor);
		
		PhJHQueueDoctor queueDoctor = doctorlist.get(position);
		tv_room_no.setText(queueDoctor.getRoom_no());
		tv_doctor.setText(queueDoctor.getDoc_name());
		
		return convertView;
	}

	class ViewHolder{
		TextView tv_room_no;
		TextView tv_doctor;
	}
}
