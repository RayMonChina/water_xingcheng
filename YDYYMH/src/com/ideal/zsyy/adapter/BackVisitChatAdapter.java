package com.ideal.zsyy.adapter;

import java.util.List;

import com.jijiang.wtapp.R;
import com.ideal.zsyy.entity.ComplainInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BackVisitChatAdapter extends BaseAdapter {

	private List<ComplainInfo>complainInfos;
	private Context context;
	private LayoutInflater inflater;
	
	public BackVisitChatAdapter(Context context,List<ComplainInfo>complainInfo)
	{
		this.context=context;
		this.complainInfos=complainInfo;
		inflater=LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(complainInfos!=null)
		{
			return complainInfos.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		if(complainInfos!=null&&complainInfos.size()>=arg0)
		{
			return complainInfos.get(arg0);
		}
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		LinearLayout ll_chat=(LinearLayout)inflater.inflate(R.layout.hf_chatting_item_msg_text_left, null);
		

		TextView tv_right_time=(TextView)ll_chat.findViewById(R.id.tv_right_sendtime);
		TextView tv_right_content=(TextView)ll_chat.findViewById(R.id.tv_right_chatcontent);
		TextView tv_left_time=(TextView)ll_chat.findViewById(R.id.tv_left_sendtime);
		TextView tv_left_content=(TextView)ll_chat.findViewById(R.id.tv_left_chatcontent);
		if(complainInfos!=null&&complainInfos.size()>=arg0)
		{
			ComplainInfo comInfo=complainInfos.get(arg0);
			tv_right_time.setText(comInfo.getComplaintTime());
			tv_right_content.setText(comInfo.getComplaintContent());
			if(comInfo.getReplayContent()==null||"".equals(comInfo.getReplayContent().trim()))
			{
				tv_left_content.setVisibility(View.GONE);
				tv_left_time.setVisibility(View.GONE);
			}
			else {
				tv_left_content.setText(comInfo.getReplayContent());
				tv_left_time.setText(comInfo.getReplayTime());
			}
		}
		return ll_chat;
	}

}
