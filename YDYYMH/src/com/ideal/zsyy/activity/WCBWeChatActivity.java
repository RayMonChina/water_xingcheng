package com.ideal.zsyy.activity;

import java.util.List;

import com.ideal.zsyy.adapter.WUserSearchNewAdapter;
import com.ideal.zsyy.db.WdbManager;
import com.ideal.zsyy.entity.WBBItem;
import com.ideal.zsyy.entity.WCBUserEntity;
import com.ideal.zsyy.request.WBBReq;
import com.ideal.zsyy.response.WBBRes;
import com.ideal.zsyy.service.PreferencesService;
import com.ideal2.base.gson.GsonServlet;
import com.ideal2.base.gson.GsonServlet.OnResponseEndListening;
import com.jijiang.wtapp.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class WCBWeChatActivity extends Activity {

	private Button btn_back, btn_search;
	private Spinner sp_bbh;
	private ListView lv_wechat;
	WdbManager dbHelper; 
	private ArrayAdapter<WBBItem> apBBH;
	private PreferencesService preferencesService;
	private WdbManager dbManager=null;
	private List<WCBUserEntity>userList;
	private String nodeNo="";
	private WUserSearchNewAdapter apUser;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.w_cb_wechat_list);
		dbHelper=new WdbManager(WCBWeChatActivity.this);
		preferencesService=new PreferencesService(WCBWeChatActivity.this);
		this.InitView();
		this.InitData();
	}
	
	private void InitView()
	{
		btn_back=(Button)findViewById(R.id.btn_back);
		btn_search=(Button)findViewById(R.id.btn_search);
		sp_bbh=(Spinner)findViewById(R.id.sp_bbh);
		lv_wechat=(ListView)findViewById(R.id.lv_wechat_result);
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(dbHelper!=null)
		{
			dbHelper.closeDB();
		}
	}
	
	private void InitData()
	{
		dbManager=new WdbManager(this);
		if (sp_bbh != null) {
			List<WBBItem> ltAp = dbHelper.GetBiaoBenInfo();
			if (ltAp == null || ltAp.size() == 0) {
				GetBBData(preferencesService.getLoginInfo().get("use_id")
						.toString());
			}
			apBBH = new ArrayAdapter<WBBItem>(this,
					android.R.layout.simple_spinner_item, ltAp);
			apBBH.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			sp_bbh.setAdapter(apBBH);
			
			sp_bbh.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					// TODO Auto-generated method stub
					WBBItem bItem=apBBH.getItem(arg2);
					searchChaoBiaoData(bItem.getNoteNo());
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
				
			});
			
		}
	
	}
	
	private void searchChaoBiaoData(String nNo){
		userList=dbManager.SearchResults(0,"",nNo,-1,6);
		if(userList==null||userList.size()==0)
		{
			Toast.makeText(WCBWeChatActivity.this,"没有查询到数据", Toast.LENGTH_SHORT).show();
		}
		apUser=new WUserSearchNewAdapter(WCBWeChatActivity.this,userList,-1);
		lv_wechat.setAdapter(apUser);
	}
	
	private void searchData(){
		WBBItem bbItem=null;
		if(sp_bbh!=null)
		{
			bbItem=(WBBItem)sp_bbh.getSelectedItem();
		}
		Intent intent=new Intent(this,WCBSearchResultActivity.class);
		intent.putExtra("NoteNo",bbItem.getNoteNo());
		intent.putExtra("ChaoBiaoTag",0);
		intent.putExtra("op",2);
		startActivity(intent);
	}
	// 获取表本数据
		private void GetBBData(final String userID) {
			WBBReq req = new WBBReq();
			req.setOperType("2");
			req.setLoginID(userID);

			GsonServlet<WBBReq, WBBRes> gServlet = new GsonServlet<WBBReq, WBBRes>(
					this);
			gServlet.request(req, WBBRes.class);
			gServlet.setOnResponseEndListening(new OnResponseEndListening<WBBReq, WBBRes>() {

				@Override
				public void onResponseEnd(WBBReq commonReq, WBBRes commonRes,
						boolean result, String errmsg, int responseCode) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onResponseEndSuccess(WBBReq commonReq,
						WBBRes commonRes, String errmsg, int responseCode) {
					// TODO Auto-generated method stub
					if (commonRes != null && commonRes.getBbItems() != null
							&& commonRes.getBbItems().size() > 0) {
						dbHelper.AddBiaoBenInfo(commonRes.getBbItems(), userID);
						handler.sendEmptyMessage(1);
					}

				}

				@Override
				public void onResponseEndErr(WBBReq commonReq, WBBRes commonRes,
						String errmsg, int responseCode) {
					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(), errmsg,
							Toast.LENGTH_SHORT).show();
				}

			});
		}

		private Handler handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch (msg.what) {
				case 1:
					List<WBBItem> ltAp = dbHelper.GetBiaoBenInfo();
					apBBH = new ArrayAdapter<WBBItem>(WCBWeChatActivity.this,
							android.R.layout.simple_spinner_item, ltAp);
					apBBH.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					if (sp_bbh != null) {
						sp_bbh.setAdapter(apBBH);
					}
					break;
				default:
					break;
				}
			}

		};
}
