package com.ideal.zsyy.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ideal.zsyy.adapter.WQianfeiAdapter;
import com.ideal.zsyy.db.WdbManager;
import com.ideal.zsyy.entity.ActionItem;
import com.ideal.zsyy.entity.QianFeiMerge;
import com.ideal.zsyy.entity.WBBItem;
import com.ideal.zsyy.request.WDownUserReq;
import com.ideal.zsyy.response.QianFeiHistoryRes;
import com.ideal.zsyy.service.PreferencesService;
import com.ideal.zsyy.view.TitlePopup;
import com.ideal.zsyy.view.TitlePopup.OnItemOnClickListener;
import com.ideal2.base.gson.GsonServlet;
import com.ideal2.base.gson.GsonServlet.OnResponseEndListening;
import com.jijiang.wtapp.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class WQianfeiHistoryActivity extends Activity {

	private Spinner spinnerBBH = null;
	private Button btn_back = null, btn_qianfeiview = null;
	private ScrollView sv_content = null;
	private Switch sw_union = null;
	private ListView lv_content = null;
	private WdbManager dbManage = null;
	private String NoteNo = "0";
	private TitlePopup titlePopup;
	private PreferencesService preferencesService;
	public ArrayList<String> MonthList = null;
	private Map<String, Object> userInfo;


	private WQianfeiAdapter wqianfeiAdapter;

	private LinearLayout ll_top_menu;
	// private float defaultsize = 18f;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.w_cb_qianfei_history);
		PreferencesService pService = new PreferencesService(WQianfeiHistoryActivity.this);
		userInfo = pService.getLoginInfo();
		titlePopup = new TitlePopup(WQianfeiHistoryActivity.this);
		titlePopup.addAction(new ActionItem(getResources().getDrawable(R.drawable.wcb_download_24), "数据更新", 1));
		dbManage = new WdbManager(WQianfeiHistoryActivity.this);
		this.initView();
		this.setEventListener();
		// SetDefaultFontSize();
		FillData();
	}

	// private void SetDefaultFontSize() {
	// LinearLayout loginLayout = (LinearLayout) findViewById(R.id.lv_content);
	// SetFontseize(loginLayout);
	// // defaultsize = preferencesService.GetZoomSize();
	// }

	// private void SetFontseize(ViewGroup viewGroup) {
	//
	// int count = viewGroup.getChildCount();
	// for (int i = 0; i < count; i++) {
	// View view = viewGroup.getChildAt(i);
	// if (view instanceof TextView) {
	// TextView newDtv = (TextView) view;
	// newDtv.setTextSize(defaultsize);
	// } else if (view instanceof ViewGroup) {
	// this.SetFontseize((ViewGroup) view);
	// }
	// }
	// }

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (dbManage != null) {
			dbManage.closeDB();
		}
	}

	private void initView() {
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_qianfeiview = (Button) findViewById(R.id.btn_qianfeiview);
		spinnerBBH = (Spinner) findViewById(R.id.sp_bbh);
		lv_content = (ListView) findViewById(R.id.lv_content);
		// sw_union = (Switch) findViewById(R.id.sw_union);
		// tv_updateqianfei = (TextView) findViewById(R.id.tv_updateqianfei);
		ll_top_menu = (LinearLayout) findViewById(R.id.ll_top_menu);

		if (ll_top_menu != null) {
			ll_top_menu.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					titlePopup.show(v);
				}
			});
		}
		
		titlePopup.setItemOnClickListener(new OnItemOnClickListener() {

			@Override
			public void onItemClick(ActionItem item, int position) {
				switch (item.operateId) {
				case 1:
					DownQianFeiHistory();
					break;
				}

			}
		});
	}

	private void DownQianFeiHistory() {
		WDownUserReq req = new WDownUserReq();
		req.setLoginid(userInfo.get("use_id").toString());
		req.setOperType("17");
		req.setMeterReadingNO("0");

		GsonServlet<WDownUserReq, QianFeiHistoryRes> gServlet = new GsonServlet<WDownUserReq, QianFeiHistoryRes>(this);

		gServlet.request(req, QianFeiHistoryRes.class);
		gServlet.setOnResponseEndListening(new OnResponseEndListening<WDownUserReq, QianFeiHistoryRes>() {
			@Override
			public void onResponseEnd(WDownUserReq commonReq, QianFeiHistoryRes commonRes, boolean result,
					String errmsg, int responseCode) {
			}

			@Override
			public void onResponseEndSuccess(WDownUserReq commonReq, QianFeiHistoryRes commonRes, String errmsg,
					int responseCode) {
				dbManage.AddQianFeiHistory(commonRes.getQianFeiHistoryItem());
				FillData();
				Toast.makeText(WQianfeiHistoryActivity.this, "更新成功！", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onResponseEndErr(WDownUserReq commonReq, QianFeiHistoryRes commonRes, String errmsg,
					int responseCode) {
				Toast.makeText(getApplicationContext(), errmsg, Toast.LENGTH_SHORT).show();

			}
		});
	}
	
	private void GetQianfeiHistoryList() {
		if (wqianfeiAdapter != null)
			MonthList = wqianfeiAdapter.MonthList;
		Intent intent = new Intent(this, WQianfeiHistoryResultActivity.class);
		intent.putExtra("NoteNo", NoteNo);
		intent.putStringArrayListExtra("MonthList", MonthList);
		startActivity(intent);
	}

	private void setEventListener() {
		if (btn_back != null) {
			btn_back.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					finish();
				}
			});
		}
		// if (tv_updateqianfei != null) {
		// tv_updateqianfei.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		//
		// }
		// });
		// }
		if (btn_qianfeiview != null) {
			btn_qianfeiview.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					GetQianfeiHistoryList();
				}
			});
		}

		if (spinnerBBH != null) {
			List<WBBItem> bbList = dbManage.GetBiaoBenInfo();
			WBBItem bbItemAll = new WBBItem();
			bbItemAll.setBId(0);
			bbItemAll.setNoteNo("全部");
			if (bbList != null) {
				bbList.add(0, bbItemAll);
			}
			ArrayAdapter<WBBItem> adapter = new ArrayAdapter<WBBItem>(this, android.R.layout.simple_spinner_item,
					bbList);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinnerBBH.setAdapter(adapter);
			spinnerBBH.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
					WBBItem bbItem = (WBBItem) arg0.getItemAtPosition(position);
					NoteNo = bbItem.getBId() == 0 ? "0" : bbItem.getNoteNo();
					FillData();
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {

				}
			});
		}

	}

	private void FillData() {

		List<QianFeiMerge> qf = dbManage.GetQianFeiMerge(NoteNo);
		if (qf != null && qf.size() > 0) {
			wqianfeiAdapter = new WQianfeiAdapter(WQianfeiHistoryActivity.this, qf);
			lv_content.setAdapter(wqianfeiAdapter);
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

}
