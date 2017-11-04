package com.ideal.zsyy.activity;

import java.util.ArrayList;
import java.util.List;

import com.ideal.zsyy.adapter.WUserSearchAdapter;
import com.ideal.zsyy.db.WdbManager;
import com.ideal.zsyy.entity.WUserItem;
import com.jijiang.wtapp.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

public class WCustomerSearchActivity extends Activity {

	private ListView lv_userInfo = null;
	private RadioGroup rGroup = null;
	private EditText ed_search = null;
	private Button btn_search = null;
	private Button btn_back=null;
	private List<WUserItem> userItems;
	WdbManager wManager = null;
	Intent intent=null;
	int parmw=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.w_user_search);
		wManager = new WdbManager(getApplicationContext());
		intent=getIntent();
		parmw=intent.getIntExtra("pageS",0);
		initView();
	}

	private void initView() {
		lv_userInfo = (ListView) findViewById(R.id.lv_userinfo);
		rGroup = (RadioGroup) findViewById(R.id.rg_search_type);
		ed_search = (EditText) findViewById(R.id.ed_searchcontent);
		btn_search = (Button) findViewById(R.id.btn_w_search);
		btn_back=(Button)findViewById(R.id.btn_back);
		if(btn_back!=null)
		{
			btn_back.setVisibility(View.VISIBLE);
			btn_back.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					finish();
				}
			});
		}
		lv_userInfo.setAdapter(new WUserSearchAdapter(WCustomerSearchActivity.this, userItems));
		btn_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				List<WUserItem> tempList = new ArrayList<WUserItem>();
				String seContent = ed_search.getText().toString();
				int serType = 0;
				switch (rGroup.getCheckedRadioButtonId()) {
				case R.id.rb_user:
					serType = 1;
					break;
				case R.id.rb_eq_num:
					serType = 2;
					break;
				case R.id.rb_family_name:
					serType = 3;
					break;
				case R.id.rb_address:
					serType = 4;
					break;
				default:
					break;
				}
				tempList = wManager.getCustomerInfo(serType, seContent);
				if(tempList.size()==0)
				{
					Toast.makeText(WCustomerSearchActivity.this,"该用户不存在",Toast.LENGTH_SHORT).show();
				}
				if(tempList.size()==1)
				{
					WUserItem userItem=tempList.get(0);
					openNewWindow(userItem,parmw);
				}
				lv_userInfo.setAdapter(new WUserSearchAdapter(WCustomerSearchActivity.this,
						tempList));

			}
		});
	
		lv_userInfo.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				WUserItem userItem=(WUserItem)arg0.getAdapter().getItem(arg2);
				openNewWindow(userItem,parmw);
			}
		});
	}

	private void openNewWindow(WUserItem uItem,int par)
	{
		if(par==1)
		{
			Intent intentNew=new Intent(WCustomerSearchActivity.this,WCBMainActivity.class);
			intentNew.putExtra("UserNo", uItem.getYhbm());
			startActivity(intentNew);
			finish();
		}
		else if(par==2) {
			Intent intentNew=new Intent(WCustomerSearchActivity.this,WFaultReportActivity.class);
			intentNew.putExtra("StealNo", uItem.getBgh());
			startActivity(intentNew);
			finish();
		}
		else if(par==3) {
			Intent intentNew=new Intent(WCustomerSearchActivity.this,WCustomAdviceActivity.class);
			intentNew.putExtra("UserNo", uItem.getYhbm());
			startActivity(intentNew);
			finish();
		}
	}

	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			finish();
		}
		return true;
	}

	@Override
	protected void onDestroy() {
		
		super.onDestroy();
		if(wManager!=null)
		{
			wManager.closeDB();
		}
	}

}
