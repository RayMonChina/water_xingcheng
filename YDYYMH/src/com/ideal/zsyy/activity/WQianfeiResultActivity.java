package com.ideal.zsyy.activity;

import java.util.List;

import com.ideal.zsyy.adapter.WUQianfeiAdapter;
import com.ideal.zsyy.db.WdbManager;
import com.ideal.zsyy.entity.QianFeiItem;
import com.jijiang.wtapp.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class WQianfeiResultActivity  extends Activity{
	
	private ListView lv_searchresult;
	private WdbManager dbManager=null;
	private List<QianFeiItem> QFList;
	private WUQianfeiAdapter apUser;
	private String NoteNo,SearchKey;
	//private HashMap<String, Object> MonthMap;
	private Button btn_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.w_cb_qianfei_result);
		dbManager=new WdbManager(this);
		Intent intent=getIntent();
		NoteNo=intent.getStringExtra("NoteNo");
		SearchKey=intent.getStringExtra("SearchKey");
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
		QFList=dbManager.GetSSQianFei(NoteNo,SearchKey);
		if(QFList==null||QFList.size()==0)
		{
			Toast.makeText(WQianfeiResultActivity.this,"没有查询到数据", Toast.LENGTH_SHORT).show();
			finish();
			return;
		}
		apUser=new WUQianfeiAdapter(WQianfeiResultActivity.this,QFList);
		lv_searchresult.setAdapter(apUser);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
