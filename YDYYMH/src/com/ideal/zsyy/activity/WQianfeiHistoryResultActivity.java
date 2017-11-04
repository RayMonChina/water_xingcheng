package com.ideal.zsyy.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ideal.zsyy.adapter.WUserSearchQianfeiAdapter;
import com.ideal.zsyy.adapter.WUserSearchQianfeiHistoryAdapter;
import com.ideal.zsyy.db.WdbManager;
import com.ideal.zsyy.entity.QianFeiHistoryItem;
import com.ideal.zsyy.entity.WCBUserEntity;
import com.jijiang.wtapp.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class WQianfeiHistoryResultActivity extends Activity {
	private ListView lv_searchresult;
	private WdbManager dbManager=null;
	private List<QianFeiHistoryItem>userList;
	private WUserSearchQianfeiHistoryAdapter apUser;
	private String NoteNo,keywords;
	private ArrayList<String> MonthList;
	private Button btn_back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.w_cb_qianfei_history_result);
		dbManager=new WdbManager(this);
		Intent intent=getIntent();
		NoteNo=intent.getStringExtra("NoteNo");
		MonthList=intent.getStringArrayListExtra("MonthList");
		this.initView();
		this.InitData();
	}
	
	private void initView()
	{
		lv_searchresult=(ListView)findViewById(R.id.lv_searchresult);
		btn_back=(Button)findViewById(R.id.btn_back);
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
	
	private void InitData()
	{
		userList=dbManager.SearchQianfeiHistoryResults(NoteNo,MonthList);
		if(userList==null||userList.size()==0)
		{
			Toast.makeText(WQianfeiHistoryResultActivity.this,"没有查询到数据", Toast.LENGTH_SHORT).show();
			finish();
			return;
		}
		apUser=new WUserSearchQianfeiHistoryAdapter(WQianfeiHistoryResultActivity.this,userList);
		lv_searchresult.setAdapter(apUser);
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
