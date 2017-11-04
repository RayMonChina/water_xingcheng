package com.ideal.zsyy.view;

import com.ideal.zsyy.activity.WAnalysisActivity;
import com.ideal.zsyy.activity.WCBInOrderActivity;
import com.ideal.zsyy.activity.WCBRealNewActivity;
import com.ideal.zsyy.activity.WCBWeiChaoActivity;
import com.ideal.zsyy.activity.WNFCTagActivity;
import com.ideal.zsyy.activity.WQianfeiActivity;
import com.ideal.zsyy.activity.WQianfeiHistoryActivity;
import com.ideal.zsyy.service.PreferencesService;
import com.jijiang.wtapp.R;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class WCBMenuView extends BaseLayout {

	private Context _context;
	Button btn_nfc,btn_shuxu,btn_xuchao,btn_weichao,btn_tongji,btn_qianfei,btn_qianfeiuser;
	private PreferencesService preService;
	public WCBMenuView(Context context) {
		super(context,R.layout.w_cb_menu);
		// TODO Auto-generated constructor stub
		this._context=context;
		this.initView();
		preService=new PreferencesService(_context);
	}
	
	private void initView()
	{
		 btn_nfc=(Button)this.findViewById(R.id.btn_nfc);
		 btn_shuxu=(Button)this.findViewById(R.id.btn_shunxuchaobiao);
		 btn_xuchao=(Button)this.findViewById(R.id.btn_xuchao);
		 btn_weichao=(Button)this.findViewById(R.id.btn_weichao);
		 btn_tongji=(Button)this.findViewById(R.id.btn_tongji);
		 btn_qianfei=(Button)this.findViewById(R.id.btn_qianfei);
		 btn_qianfeiuser=(Button)this.findViewById(R.id.btn_qianfeiuser);
		btn_nfc.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentNfc=new Intent(_context,WNFCTagActivity.class);
				_context.startActivity(intentNfc);
			}
		});
		
		btn_shuxu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentShunxu=new Intent(_context,WCBInOrderActivity.class);
				_context.startActivity(intentShunxu);
			}
		});
		btn_xuchao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String stealNo=preService.GetCurrentCustomerInfo().get("StealNo").toString();
				Intent intent=new Intent(_context,WCBRealNewActivity.class);
				intent.putExtra("StealNo",stealNo);
				_context.startActivity(intent);
			}
		});
		btn_weichao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(_context,WCBWeiChaoActivity.class);
				_context.startActivity(intent);
			}
		});
		btn_tongji.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentTongji=new Intent(_context,WAnalysisActivity.class);
				_context.startActivity(intentTongji);
			}
		});
		btn_qianfei.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intentQainfei=new Intent(_context,WQianfeiHistoryActivity.class);
				_context.startActivity(intentQainfei);
			}
		});
		btn_qianfeiuser.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intentQainfeiUser=new Intent(_context,WQianfeiActivity.class);
				_context.startActivity(intentQainfeiUser);
			}
		});
	}
}
