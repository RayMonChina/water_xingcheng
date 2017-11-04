package com.ideal.zsyy.activity;

import java.util.ArrayList;
import java.util.List;

import com.ideal.zsyy.db.WdbManager;
import com.ideal.zsyy.entity.WBBItem;
import com.ideal.zsyy.entity.WCBUserEntity;
import com.ideal.zsyy.entity.WSearchType;
import com.ideal.zsyy.entity.WUserItem;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class WCBSearchNewActivity extends Activity {

	private Button btn_back, btn_search;
	private Spinner sp_bbh, sp_searchType;
	private EditText edit_content;
	private ArrayAdapter<WSearchType> apTypes;
	private ArrayAdapter<WBBItem> apBBH;
	private List<WSearchType> listSearchTypes = new ArrayList<WSearchType>();
	PreferencesService preferencesService = null;
	WdbManager dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.w_cb_search_new);
		dbHelper = new WdbManager(this);
		preferencesService = new PreferencesService(this);
		this.initView();
		this.initData();
	}

	private void initView() {
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_search = (Button) findViewById(R.id.btn_search);
		sp_bbh = (Spinner) findViewById(R.id.sp_bbh);
		sp_searchType = (Spinner) findViewById(R.id.sp_search);

		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		btn_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				searchData();
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
	
	private void initData() {
		listSearchTypes.clear();
		listSearchTypes.add(new WSearchType(1, "户号"));
		listSearchTypes.add(new WSearchType(2, "表号"));
		listSearchTypes.add(new WSearchType(3, "户名"));
		listSearchTypes.add(new WSearchType(4, "地址"));
		apTypes = new ArrayAdapter<WSearchType>(WCBSearchNewActivity.this,
				android.R.layout.simple_spinner_item, listSearchTypes);
		apTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		if (sp_searchType != null) {
			sp_searchType.setAdapter(apTypes);
		}
		if (sp_bbh != null) {
			List<WBBItem> ltAp = dbHelper.GetBiaoBenInfo();
			if (ltAp == null || ltAp.size() == 0) {
				GetBBData(preferencesService.getLoginInfo().get("use_id")
						.toString());
			}
			WBBItem bbItemAll=new WBBItem();
			bbItemAll.setBId(0);
			bbItemAll.setNoteNo("全部");
			if(ltAp!=null)
			{
				ltAp.add(0, bbItemAll);
			}
			apBBH = new ArrayAdapter<WBBItem>(this,
					android.R.layout.simple_spinner_item, ltAp);
			apBBH.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			sp_bbh.setAdapter(apBBH);
		}
	}

	private void searchData(){
		String content="";
		int op=0;
		WBBItem bbItem=null;
		if(edit_content!=null)
		{
			content=edit_content.getText().toString();
		}
		if(sp_bbh!=null)
		{
			bbItem=(WBBItem)sp_bbh.getSelectedItem();
		}
		if(sp_searchType!=null)
		{
			WSearchType searchItem=(WSearchType)sp_searchType.getSelectedItem();
			if(searchItem!=null)
			{
				op=searchItem.getNumber();
			}
		}
		Intent intent=new Intent(this,WCBSearchResultActivity.class);
		intent.putExtra("op",op);
		intent.putExtra("KeyWords",content);
		intent.putExtra("PianNo",bbItem.getPianNo());
		intent.putExtra("AreaNo", bbItem.getAreaNo());
		intent.putExtra("NoteNo",bbItem.getBId()==0?"0":bbItem.getNoteNo());
		intent.putExtra("DuanNo",bbItem.getDuanNo());
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
				WBBItem bbItemAll=new WBBItem();
				bbItemAll.setBId(0);
				bbItemAll.setNoteNo("全部");
				if(ltAp!=null)
				{
					ltAp.add(0, bbItemAll);
				}
				apBBH = new ArrayAdapter<WBBItem>(WCBSearchNewActivity.this,
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
