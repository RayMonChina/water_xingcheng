package com.ideal.zsyy.activity;

import java.util.List;
import java.util.Map;

import com.ideal.zsyy.db.WdbManager;
import com.ideal.zsyy.entity.ActionItem;
import com.ideal.zsyy.entity.WAnalysisItem;
import com.ideal.zsyy.entity.WBBItem;
import com.ideal.zsyy.request.WDownUserReq;
import com.ideal.zsyy.response.QianFeiRes;
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
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class WQianfeiActivity extends Activity {

	private Spinner spinnerBBH = null;
	private TextView tv_yhzs = null, tv_qfyhs = null, tv_wcyhs = null, tv_qfje = null, tv_updateqianfei = null;
	private Button btn_back = null, btn_upload = null;
	private EditText edit_search = null;
	private WdbManager dbManage = null;
	private Map<String, Object> userInfo;
	private TitlePopup titlePopup;
	// private Map<String, Integer> userDateInfo;
	private String NoteNo = "0";
	// private PreferencesService preferencesService;
	private float defaultsize = 18f;
	
	private LinearLayout ll_top_menu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.w_cb_qianfei);
		dbManage = new WdbManager(WQianfeiActivity.this);
		PreferencesService pService = new PreferencesService(WQianfeiActivity.this);
		userInfo = pService.getLoginInfo();
		titlePopup = new TitlePopup(WQianfeiActivity.this);
		titlePopup.addAction(new ActionItem(getResources().getDrawable(R.drawable.wcb_download_24), "数据更新", 1));
		
		// userDateInfo = pService.GetCBDateInfo();
		this.initView();
		this.setEventListener();
		SetDefaultFontSize();

	}

	private void initView() {
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_upload = (Button) findViewById(R.id.btn_upload);
		spinnerBBH = (Spinner) findViewById(R.id.sp_bbh);
		tv_yhzs = (TextView) findViewById(R.id.tv_yhzs);
		tv_qfyhs = (TextView) findViewById(R.id.tv_qfyhs);
		tv_wcyhs = (TextView) findViewById(R.id.tv_wcyhs);
		tv_qfje = (TextView) findViewById(R.id.tv_qfje);
		edit_search = (EditText) findViewById(R.id.edit_search);
		tv_updateqianfei = (TextView) findViewById(R.id.tv_updateqianfei);
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
					GetSSQianFei();
					break;
				}

			}
		});
		
		
	}

	private void SetDefaultFontSize() {
		LinearLayout loginLayout = (LinearLayout) findViewById(R.id.ly_showpannl);
		SetFontseize(loginLayout);
		// defaultsize = preferencesService.GetZoomSize();
	}

	private void GetQianfeiList() {
		// WBBItem bbItem = null;
		// if (spinnerBBH != null) {
		// bbItem = (WBBItem) spinnerBBH.getSelectedItem();
		// }
		String SearchKey = edit_search.getText().toString();
		Intent intent = new Intent(this, WQianfeiResultActivity.class);
		intent.putExtra("NoteNo", NoteNo);
		intent.putExtra("SearchKey", SearchKey);
		startActivity(intent);
	}

	private void SetFontseize(ViewGroup viewGroup) {

		int count = viewGroup.getChildCount();
		for (int i = 0; i < count; i++) {
			View view = viewGroup.getChildAt(i);
			if (view instanceof TextView) {
				TextView newDtv = (TextView) view;
				newDtv.setTextSize(defaultsize);
			} else if (view instanceof ViewGroup) {
				this.SetFontseize((ViewGroup) view);
			}
		}
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
		if (tv_updateqianfei != null) {
			tv_updateqianfei.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					GetSSQianFei();
				}

			});
		}
		if (btn_upload != null) {
			btn_upload.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					GetQianfeiList();
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
		WAnalysisItem wItem = new WAnalysisItem();
		wItem = dbManage.GetSSQianFeiTJ(NoteNo);
		if (wItem != null) {
			// 欠费用户数
			if (tv_qfyhs != null) {
				tv_qfyhs.setText(String.valueOf(wItem.getQFyhs()));
			}
			if (tv_qfje != null) {
				if (wItem.getWsje() != null) {
					tv_qfje.setText(String.valueOf(wItem.getWsje()));
				} else {
					tv_qfje.setText("0");
				}
			}
		}
	}

	private void GetSSQianFei() {
		WDownUserReq req = new WDownUserReq();
		req.setLoginid(userInfo.get("use_id").toString());
		req.setOperType("15");
		req.setMeterReadingNO("0");

		GsonServlet<WDownUserReq, QianFeiRes> gServlet = new GsonServlet<WDownUserReq, QianFeiRes>(this);
		gServlet.request(req, QianFeiRes.class);
		gServlet.setOnResponseEndListening(new OnResponseEndListening<WDownUserReq, QianFeiRes>() {
			@Override
			public void onResponseEnd(WDownUserReq commonReq, QianFeiRes commonRes, boolean result, String errmsg,
					int responseCode) {

			}

			@Override
			public void onResponseEndSuccess(WDownUserReq commonReq, QianFeiRes commonRes, String errmsg,
					int responseCode) {
				dbManage.InsertSSQianFei(commonRes.getQianFeiItem());
				FillData();
				Toast.makeText(WQianfeiActivity.this, "更新成功！", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onResponseEndErr(WDownUserReq commonReq, QianFeiRes commonRes, String errmsg,
					int responseCode) {

			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (dbManage != null) {
			dbManage.closeDB();
		}
	}
}
