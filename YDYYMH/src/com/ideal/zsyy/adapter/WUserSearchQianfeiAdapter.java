package com.ideal.zsyy.adapter;

import java.util.List;

import com.ideal.zsyy.activity.WCBRealNewActivity;
import com.ideal.zsyy.adapter.WUserSearchNewAdapter.ViewHolder;
import com.ideal.zsyy.entity.WCBUserEntity;
import com.jijiang.wtapp.R;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WUserSearchQianfeiAdapter  extends BaseAdapter {

	private Context context;
	private List<WCBUserEntity> yzEntities;
	private LayoutInflater inflater;
	private int chaobiaotag=-1;
	
	public WUserSearchQianfeiAdapter(Context context, List<WCBUserEntity> ptEntities) {
		this.context = context;
		this.yzEntities = ptEntities;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (null != yzEntities) {
			return yzEntities.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if (null != yzEntities && position < yzEntities.size()) {
			return yzEntities.get(position);
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
		ViewHolder viewHolder = null;
		final WCBUserEntity ptEntity = yzEntities.get(position);
		if (convertView != null) {
			if (convertView.getTag() != null) {
				viewHolder = (ViewHolder) convertView.getTag();
			}
		} else {
			LinearLayout layout = (LinearLayout) inflater.inflate(
					R.layout.w_user_qianfei_item, null);
			 TextView tv_username=(TextView)layout.findViewById(R.id.tv_username);// 户名
			 TextView tv_chaobiaotag=(TextView)layout.findViewById(R.id.tv_chaobiaotag);// 状态
			 TextView tv_userno=(TextView)layout.findViewById(R.id.tv_userno);// 用户编码
			 TextView tv_address=(TextView)layout.findViewById(R.id.tv_address);// 地址
			 TextView tv_benyuebiaoshu=(TextView)layout.findViewById(R.id.tv_benyuebiaoshu);// 本月读数
			 TextView tv_shangyuebiaoshu=(TextView)layout.findViewById(R.id.tv_shangyuebiaoshu);// 上月表数
			 TextView tv_benyueshuifei=(TextView)layout.findViewById(R.id.tv_benyueshuifei);// 本月水费
			 LinearLayout ly_container=(LinearLayout)layout.findViewById(R.id.ly_container);
			 Button btn_detail=(Button)layout.findViewById(R.id.btn_checkdetai);// 查看详细

			viewHolder = new ViewHolder();
			viewHolder.setTv_username(tv_username);
			viewHolder.setTv_chaobiaotag(tv_chaobiaotag);
			viewHolder.setTv_userno(tv_userno);
			viewHolder.setTv_address(tv_address);
			viewHolder.setTv_benyuebiaoshu(tv_benyuebiaoshu);
			viewHolder.setTv_shangyuebiaoshu(tv_shangyuebiaoshu);
			viewHolder.setTv_benyueshuifei(tv_benyueshuifei);
			viewHolder.setBtn_detail(btn_detail);
			viewHolder.setLyContainer(ly_container);
			convertView = layout;
			convertView.setTag(viewHolder);
		}
		
		viewHolder.getTv_username().setText(ptEntity.getUserFName());
		int cbTag=ptEntity.getChaoBiaoTag();
		if(cbTag==0)
		{
			viewHolder.getTv_chaobiaotag().setText("未抄表");
			int colors= context.getResources().getColor(R.color.red);
			viewHolder.getTv_chaobiaotag().setTextColor(colors);
			viewHolder.getLyContainer().setBackgroundColor(context.getResources().getColor(R.color.white));
			
		}
		else if(cbTag==1){
			viewHolder.getTv_chaobiaotag().setText("已抄表");
			viewHolder.getLyContainer().setBackgroundColor(context.getResources().getColor(R.color.wcb_bg_already_record));
			int colors= context.getResources().getColor(R.color.black);
			viewHolder.getTv_chaobiaotag().setTextColor(colors);
		}
		else if(cbTag==3){
			viewHolder.getTv_chaobiaotag().setText("已收费");
			viewHolder.getLyContainer().setBackgroundColor(context.getResources().getColor(R.color.wcb_bg_already_fee));
			int colors= context.getResources().getColor(R.color.black);
			viewHolder.getTv_chaobiaotag().setTextColor(colors);
		}
		viewHolder.getTv_userno().setText(ptEntity.getUserNo());
		viewHolder.getTv_address().setText(ptEntity.getAddress());
		viewHolder.getTv_benyuebiaoshu().setText(String.valueOf((int)ptEntity.getCurrentMonthValue()));
		viewHolder.getTv_shangyuebiaoshu().setText(String.valueOf((int)ptEntity.getLastMonthValue()));
		viewHolder.getTv_benyueshuifei().setText(String.valueOf(ptEntity.getTotalCharge()));
		viewHolder.getBtn_detail().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(context,WCBRealNewActivity.class);
				intent.putExtra("StealNo",ptEntity.getStealNo());
				intent.putExtra("ChaoBiaoTag",chaobiaotag);
				context.startActivity(intent);
			}
		});

		return convertView;
	}

	public int getChaobiaotag() {
		return chaobiaotag;
	}

	public void setChaobiaotag(int chaobiaotag) {
		this.chaobiaotag = chaobiaotag;
	}

	public class ViewHolder {
		private TextView tv_username;// 户名
		private TextView tv_chaobiaotag;// 状态
		private TextView tv_userno;// 用户编码
		private TextView tv_address;// 地址
		private TextView tv_benyuebiaoshu;// 本月读数
		private TextView tv_shangyuebiaoshu;// 上月表数
		private TextView tv_lijiqianfei;// 累计欠费
		private TextView tv_benyueshuifei;// 本月水费
		private Button btn_detail;// 查看详细
		private LinearLayout lyContainer;//parent Container
		public TextView getTv_username() {
			return tv_username;
		}
		public void setTv_username(TextView tv_username) {
			this.tv_username = tv_username;
		}
		public TextView getTv_chaobiaotag() {
			return tv_chaobiaotag;
		}
		public void setTv_chaobiaotag(TextView tv_chaobiaotag) {
			this.tv_chaobiaotag = tv_chaobiaotag;
		}
		public TextView getTv_userno() {
			return tv_userno;
		}
		public void setTv_userno(TextView tv_userno) {
			this.tv_userno = tv_userno;
		}
		public TextView getTv_address() {
			return tv_address;
		}
		public void setTv_address(TextView tv_address) {
			this.tv_address = tv_address;
		}
		public TextView getTv_benyuebiaoshu() {
			return tv_benyuebiaoshu;
		}
		public void setTv_benyuebiaoshu(TextView tv_benyuebiaoshu) {
			this.tv_benyuebiaoshu = tv_benyuebiaoshu;
		}
		public TextView getTv_shangyuebiaoshu() {
			return tv_shangyuebiaoshu;
		}
		public void setTv_shangyuebiaoshu(TextView tv_shangyuebiaoshu) {
			this.tv_shangyuebiaoshu = tv_shangyuebiaoshu;
		}
		public TextView getTv_lijiqianfei() {
			return tv_lijiqianfei;
		}
		public void setTv_lijiqianfei(TextView tv_lijiqianfei) {
			this.tv_lijiqianfei = tv_lijiqianfei;
		}
		public TextView getTv_benyueshuifei() {
			return tv_benyueshuifei;
		}
		public void setTv_benyueshuifei(TextView tv_benyueshuifei) {
			this.tv_benyueshuifei = tv_benyueshuifei;
		}
		public Button getBtn_detail() {
			return btn_detail;
		}
		public void setBtn_detail(Button btn_detail) {
			this.btn_detail = btn_detail;
		}
		public LinearLayout getLyContainer() {
			return lyContainer;
		}
		public void setLyContainer(LinearLayout lyContainer) {
			this.lyContainer = lyContainer;
		}
		
	}

}