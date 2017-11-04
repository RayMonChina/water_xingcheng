package com.ideal.zsyy.activity;

import java.util.List;

import com.ideal.zsyy.adapter.WUserSearchNewAdapter;
import com.ideal.zsyy.db.WdbManager;
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

public class WCBSearchResultActivity extends Activity {

	private ListView lv_searchresult;
	private WdbManager dbManager=null;
	private List<WCBUserEntity>userList;
	private WUserSearchNewAdapter apUser;
	private String NoteNo,keywords;
	private int op,chaobiaotag;
	private Button btn_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.w_cb_search_result);
		dbManager=new WdbManager(this);
		Intent intent=getIntent();
		NoteNo=intent.getStringExtra("NoteNo");
		keywords=intent.getStringExtra("KeyWords");
		op=intent.getIntExtra("op",0);
		chaobiaotag=intent.getIntExtra("ChaoBiaoTag",-1);
		this.initView();
		this.InitData();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(dbManager!=null)
		{
			dbManager.closeDB();
		}
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
		userList=dbManager.SearchResults(op, keywords,NoteNo,chaobiaotag);
		if(userList==null||userList.size()==0)
		{
			Toast.makeText(WCBSearchResultActivity.this,"没有查询到数据", Toast.LENGTH_SHORT).show();
			finish();
			return;
		}
		apUser=new WUserSearchNewAdapter(WCBSearchResultActivity.this,userList,chaobiaotag);
		lv_searchresult.setAdapter(apUser);
	}
	@Override
	protected void onResume() {
		super.onResume();
		this.InitData();
	}


}
