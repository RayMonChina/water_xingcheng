package com.ideal.zsyy.adapter;

import java.util.List;

import com.jijiang.wtapp.R;
import com.ideal.zsyy.entity.BackVisitInfo;

import android.content.Context;
import android.hardware.Camera.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BackVisitAdapter extends BaseAdapter {

	private List<BackVisitInfo> backVisitInfos;
	private Context context;
	private LayoutInflater inflater;

	public BackVisitAdapter(Context context, List<BackVisitInfo> bList) {
		this.backVisitInfos=bList;
		this.context=context;
		inflater=LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(backVisitInfos!=null)
		{
			return backVisitInfos.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if(backVisitInfos!=null)
		{
			return backVisitInfos.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		RelativeLayout llItem=(RelativeLayout)inflater.inflate(R.layout.back_visit_item, null);
		TextView tv_name=(TextView)llItem.findViewById(R.id.tv_name);
		TextView tv_dept=(TextView)llItem.findViewById(R.id.tv_dept);
		TextView tv_bq=(TextView)llItem.findViewById(R.id.tv_bq);
		TextView tv_time=(TextView)llItem.findViewById(R.id.tv_time);
		TextView tv_stauts=(TextView)llItem.findViewById(R.id.tv_status);
		if(backVisitInfos!=null&&backVisitInfos.size()>=position)
		{
			BackVisitInfo bInfo=backVisitInfos.get(position);
			String status="0";
			tv_name.setText("姓名："+bInfo.getpName());
			tv_dept.setText("科室："+bInfo.getDept());
			tv_bq.setText("病区："+bInfo.getDisArea());
			tv_time.setText("时间："+bInfo.getVisitTime());
			status=bInfo.getStatus()==null?"0":bInfo.getStatus();
			if("0".equals(status))
			{
				tv_stauts.setText("未回复");
			}
			else if("1".equals(status)) {
				tv_stauts.setText("已回复");
			}
		}
		return llItem;
	}

}
