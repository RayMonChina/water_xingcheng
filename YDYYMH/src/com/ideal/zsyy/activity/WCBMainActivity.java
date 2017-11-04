package com.ideal.zsyy.activity;

import com.ideal.zsyy.db.WdbManager;
import com.ideal.zsyy.entity.WUserItem;
import com.jijiang.wtapp.R;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class WCBMainActivity extends Activity {

	Button btn_cb, btn_shoufei, btn_upFault, btn_back,btn_advice,btn_next;
	private TextView tv_title,tv_w_userno,tv_w_bbh,tv_w_fname,tv_w_dh,
	tv_w_dz,tv_w_last_month_bs,tv_w_lst_month_water,tv_w_last_month_fee,
	tv_w_price_type,tv_w_steal_no,tv_w_cb_tag,tv_w_premoney,tv_w_totle_owemoney,
	tv_w_currmonth_bs;
	private String userNo="";
	private String bbh="";
	private int currUserId=0;
	private WdbManager dbmanager=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.w_cb_main);
		userNo=getIntent().getStringExtra("UserNo");
		bbh=getIntent().getStringExtra("bbh");
		dbmanager=new WdbManager(getApplicationContext());
		this.intiView();
		this.initData();
	}

	private void intiView() {
		btn_cb = (Button) findViewById(R.id.btn_cb);
		btn_shoufei = (Button) findViewById(R.id.btn_shoufei);
		btn_upFault = (Button) findViewById(R.id.btn_upfault);
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_advice=(Button)findViewById(R.id.btn_advice);
		tv_title=(TextView)findViewById(R.id.tv_title);
		tv_w_bbh=(TextView)findViewById(R.id.tv_w_bbh);
		tv_w_cb_tag=(TextView)findViewById(R.id.tv_w_cb_tag);
		tv_w_currmonth_bs=(TextView)findViewById(R.id.tv_w_currmonth_bs);
		tv_w_dh=(TextView)findViewById(R.id.tv_w_dh);
		tv_w_dz=(TextView)findViewById(R.id.tv_w_dz);
		tv_w_fname=(TextView)findViewById(R.id.tv_w_fname);
		tv_w_last_month_bs=(TextView)findViewById(R.id.tv_w_last_month_bs);
		tv_w_last_month_fee=(TextView)findViewById(R.id.tv_w_last_month_fee);
		tv_w_lst_month_water=(TextView)findViewById(R.id.tv_w_lst_month_water);
		tv_w_premoney=(TextView)findViewById(R.id.tv_w_premoney);
		tv_w_price_type=(TextView)findViewById(R.id.tv_w_price_type);
		tv_w_steal_no=(TextView)findViewById(R.id.tv_w_steal_no);
		tv_w_totle_owemoney=(TextView)findViewById(R.id.tv_w_totle_owemoney);
		tv_w_userno=(TextView)findViewById(R.id.tv_w_userno);
		btn_next=(Button)findViewById(R.id.btn_next);
		if(null!=bbh&&!"".equals(bbh))
		{
			btn_next.setVisibility(View.VISIBLE);
			btn_next.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					initData();
				}
			});
		}
		else {
			btn_next.setVisibility(View.VISIBLE);
		}
		if (btn_cb != null) {
			btn_cb.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(WCBMainActivity.this,
							WCBRealActivity.class);
					intent.putExtra("UserNo", userNo);
					startActivity(intent);
				}
			});
		}

		if (btn_shoufei != null) {

			btn_shoufei.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(WCBMainActivity.this,
							WCBChargeFeeActivity.class);
					intent.putExtra("UserNo", userNo);
					startActivity(intent);
				}
			});
		}
		if (btn_upFault != null) {
			btn_upFault.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(WCBMainActivity.this,
							WFaultReportActivity.class);
					intent.putExtra("UserNo", userNo);
					startActivity(intent);
				}
			});
		}
		if(btn_advice!=null)
		{
			btn_advice.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(WCBMainActivity.this,
							WCustomAdviceActivity.class);
					intent.putExtra("UserNo", userNo);
					startActivity(intent);
				}
			});
		}
		if (btn_back != null) {
			btn_back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
				}
			});
		}
	}

	private void initData()
	{
		if((userNo==null|| userNo=="")&&"".equals(bbh))
		{
			return;
		}
		WUserItem userItem=null;
		if(null!=bbh&&!"".equals(bbh))
		{
			
			if(userItem==null)
			{
				Toast.makeText(WCBMainActivity.this,"没有相应信息",Toast.LENGTH_SHORT).show();
				finish();
				return;
			}
			currUserId=userItem.getUserID();
			userNo=userItem.getYhbm();
		}
		else {
			userItem=dbmanager.GetUserItemByNo(userNo);
		}
		
		if(userItem==null)
		{
			return;
		}
		this.tv_title.setText(userItem.getXm()+"("+userItem.getYhbm()+")");
		tv_w_bbh.setText(userItem.getBbh());
		tv_w_cb_tag.setText(userItem.getCbbz()==2?"已抄表":"未抄表");
		tv_w_currmonth_bs.setText(String.valueOf(userItem.getByzd()));
		tv_w_dh.setText(userItem.getDh());
		tv_w_dz.setText(userItem.getDz());
		tv_w_fname.setText(userItem.getXm());
		tv_w_last_month_bs.setText(String.valueOf(userItem.getSyzd()));
		tv_w_last_month_fee.setText(String.valueOf(userItem.getSysf()));
		tv_w_lst_month_water.setText(String.valueOf(userItem.getSysl()));
		tv_w_premoney.setText(String.valueOf(userItem.getYcje()));
		tv_w_price_type.setText(userItem.getJglxName());
		tv_w_steal_no.setText(userItem.getBgh());
		tv_w_totle_owemoney.setText(String.valueOf(userItem.getLjqf()));
		tv_w_userno.setText(userItem.getYhbm());
	}

	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		this.initData();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(dbmanager!=null)
		{
			dbmanager.closeDB();
		}
	}
	
}
