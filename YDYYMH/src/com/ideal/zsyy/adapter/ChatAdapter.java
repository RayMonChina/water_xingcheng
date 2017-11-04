package com.ideal.zsyy.adapter;

import java.util.HashMap;
import java.util.List;

import com.jijiang.wtapp.R;
import com.ideal.zsyy.entity.ChatContent;
import com.ideal.zsyy.entity.PatientChatContent;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChatAdapter extends BaseAdapter{
	private LayoutInflater inflater;
	private List<PatientChatContent> patient_content ;
	private Context mContext;
	private HashMap<String, ChatContent> doctor_content;
	public ChatAdapter(Context context,List<PatientChatContent> patient_content,HashMap<String, ChatContent> doctor_content) {
		// TODO Auto-generated constructor stub
		mContext=context;
		inflater=LayoutInflater.from(context);
		this.patient_content=patient_content;
		this.doctor_content=doctor_content;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return patient_content.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		 LinearLayout layout=new LinearLayout(mContext);
		// layout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		 layout.setOrientation(LinearLayout.VERTICAL);
		 View view;
		 View view1;
		 PatientChatContent record=patient_content.get(position);
		 view=inflater.inflate(R.layout.chatting_item_msg_text_left, null);
		 TextView content=(TextView) view.findViewById(R.id.tv_chatcontent);
		 TextView time=(TextView)view.findViewById(R.id.tv_sendtime);
		 content.setText(record.getContent());
		 time.setText(record.getDatetime());
		 layout.addView(view);
		 if(doctor_content.containsKey(record.getChatId()))
		 {
			  view1=inflater.inflate(R.layout.chatting_item_msg_text_right, null);
			  ChatContent record1=doctor_content.get(record.getChatId());
			  TextView content1=(TextView) view1.findViewById(R.id.tv_chatcontent);
			  TextView time1=(TextView)view1.findViewById(R.id.tv_sendtime);
			  TextView username1=(TextView)view1.findViewById(R.id.tv_username);
			  content1.setText(record1.getContent());
			  time1.setText(record1.getDatetime());
			  username1.setText(record1.getDoctorName());
			  layout.addView(view1);
		 }
		return layout;
	}
	
   public void updateData(List<PatientChatContent> patient_content,HashMap<String, ChatContent> doctor_content)
   {
	   this.patient_content=patient_content;
	   this.doctor_content=doctor_content;
	   notifyDataSetChanged();
   }
}
