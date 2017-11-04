package com.ideal.zsyy.activity;

import java.util.List;

import com.ideal.zsyy.adapter.WCBAdviceAdapter;
import com.ideal.zsyy.db.WdbManager;
import com.ideal.zsyy.entity.WCustomerAdviceInfo;
import com.jijiang.wtapp.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class AdviceListActivity extends Activity
{

	ListView lv_advice=null;
	WdbManager dbManager=null;
	List<WCustomerAdviceInfo>adviceInfos;
	WCBAdviceAdapter adviceAdapter;
	Button btn_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wcb_advice_list);
		this.initData();
	}
	
	private void initData()
	{
		lv_advice=(ListView)findViewById(R.id.lv_advice);
		btn_back=(Button)findViewById(R.id.btn_back);
		dbManager=new WdbManager(AdviceListActivity.this);
		adviceInfos=dbManager.GetAllAdviceInfos();
		adviceAdapter=new WCBAdviceAdapter(AdviceListActivity.this, adviceInfos);
		lv_advice.setAdapter(adviceAdapter);
		
		if(btn_back!=null)
		{
			btn_back.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					finish();
				}
			});
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(dbManager!=null)
		{
			dbManager.closeDB();
		}
	}

	
}
