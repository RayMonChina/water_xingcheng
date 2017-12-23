package com.ideal.zsyy.activity;

import java.util.List;

import com.ideal.zsyy.adapter.WUserSearchNewAdapter;
import com.ideal.zsyy.db.WdbManager;
import com.ideal.zsyy.entity.ActionItem;
import com.ideal.zsyy.entity.WBBItem;
import com.ideal.zsyy.entity.WCBUserEntity;
import com.ideal.zsyy.request.WBBReq;
import com.ideal.zsyy.request.WDownUserReq;
import com.ideal.zsyy.response.WBBRes;
import com.ideal.zsyy.response.WDownUserRes;
import com.ideal.zsyy.service.PreferencesService;
import com.ideal.zsyy.view.TitlePopup;
import com.ideal.zsyy.view.TitlePopup.OnItemOnClickListener;
import com.ideal2.base.gson.GsonServlet;
import com.ideal2.base.gson.GsonServlet.OnResponseEndListening;
import com.jijiang.wtapp.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
	private WdbManager dbManager = null;
	private List<WCBUserEntity> userList;
	private TitlePopup titlePopup;
	private WUserSearchNewAdapter apUser;
	private LinearLayout ll_top_menu;
	private EditText edit_search = null;
	private int bbIndex = 0;
	private List<WBBItem> ltAp;
	private WBBItem currBBItem;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.w_cb_wechat_list);
		dbHelper = new WdbManager(WCBWeChatActivity.this);
		preferencesService = new PreferencesService(WCBWeChatActivity.this);
		this.InitView();
		this.InitData();
	}

	private void InitView() {
		btn_back = (Button) findViewById(R.id.btn_back);
		ll_top_menu = (LinearLayout) findViewById(R.id.ll_top_menu);
		btn_search = (Button) findViewById(R.id.btn_search);
		sp_bbh = (Spinner) findViewById(R.id.sp_bbh);
		lv_wechat = (ListView) findViewById(R.id.lv_wechat_result);
		titlePopup = new TitlePopup(WCBWeChatActivity.this);
		edit_search = (EditText) findViewById(R.id.edit_search);
		titlePopup.addAction(new ActionItem(getResources().getDrawable(R.drawable.wcb_download_24), "数据更新", 1));
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		btn_search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Object ob=sp_bbh.getSelectedItem();
				WBBItem bItem=(WBBItem)sp_bbh.getSelectedItem();
				if (bItem.getBId() > 0) {
					searchChaoBiaoData(bItem.getNoteNo());
				} else {
					searchChaoBiaoData("0");
				}
			}
		});
		
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
					if(currBBItem.getNoteNo().equals("全部")){
						DownUserItems();
					}else {
						DownUserItemByNoteNO();
					}
					break;
				}
			}
		});
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (dbHelper != null) {
			dbHelper.closeDB();
		}
	}

	private void InitData() {
		dbManager = new WdbManager(this);
		if (sp_bbh != null) {
			 ltAp= dbHelper.GetBiaoBenInfo();
			if (ltAp == null || ltAp.size() == 0) {
				GetBBData(preferencesService.getLoginInfo().get("use_id").toString());
			}
			WBBItem bbItemAll = new WBBItem();
			bbItemAll.setBId(0);
			bbItemAll.setNoteNo("全部");
			if (ltAp != null) {
				ltAp.add(0, bbItemAll);
			}
			apBBH = new ArrayAdapter<WBBItem>(this, android.R.layout.simple_spinner_item, ltAp);
			apBBH.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			sp_bbh.setAdapter(apBBH);

			sp_bbh.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					// TODO Auto-generated method stub
					currBBItem = apBBH.getItem(arg2);
					if (currBBItem.getBId() > 0) {
						searchChaoBiaoData(currBBItem.getNoteNo());
					} else {
						searchChaoBiaoData("0");
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub

				}

			});

		}

	}

	private void searchChaoBiaoData(String nNo) {
		String keyword = this.edit_search.getText().toString();
		userList = dbManager.SearchResults(0, keyword, nNo, -1, 6);
		if (userList == null || userList.size() == 0) {
			Toast.makeText(WCBWeChatActivity.this, "没有查询到数据", Toast.LENGTH_SHORT).show();
		}
		apUser = new WUserSearchNewAdapter(WCBWeChatActivity.this, userList, -1);
		lv_wechat.setAdapter(apUser);
	}
	
	private void DownUserItems() {
		WDownUserReq req = new WDownUserReq();
		WBBItem bbiItem = ltAp.get(bbIndex);

		if (bbiItem.getBId() == 0) {
			bbIndex++;
			if (bbIndex == ltAp.size()) {
				return;
			}
			bbiItem = ltAp.get(bbIndex);
		}

		req.setOperType("3");// 用户数据下载
		req.setChargeType(6);
		if (bbiItem != null) {
			req.setCbMonth(String.valueOf(bbiItem.getCBMonth()));
			req.setCbYear(String.valueOf(bbiItem.getCBYear()));
			req.setMeterReadingNO(bbiItem.getNoteNo());
		} else {
			Toast.makeText(WCBWeChatActivity.this, "没有查询到数据", Toast.LENGTH_SHORT).show();
			return;
		}
		req.setLoginid(preferencesService.getLoginInfo().get("use_id").toString());
		GsonServlet<WDownUserReq, WDownUserRes> gServlet = new GsonServlet<WDownUserReq, WDownUserRes>(this);
		gServlet.request(req, WDownUserRes.class);
		gServlet.setOnResponseEndListening(new OnResponseEndListening<WDownUserReq, WDownUserRes>() {

			@Override
			public void onResponseEnd(WDownUserReq commonReq, WDownUserRes commonRes, boolean result, String errmsg,
					int responseCode) {
			}

			@Override
			public void onResponseEndSuccess(WDownUserReq commonReq, WDownUserRes commonRes, String errmsg,
					int responseCode) {
				// TODO Auto-generated method stub
				if (commonRes != null && commonRes.getUserItems() != null && commonRes.getUserItems().size() > 0) {
					dbHelper.EditCustomerInfo(commonRes.getUserItems());
					bbIndex++;
					if (bbIndex == ltAp.size()) {
						Toast.makeText(WCBWeChatActivity.this, "下载成功！", Toast.LENGTH_SHORT).show();	
						return;
					}
					DownUserItems();
				}
			}

			@Override
			public void onResponseEndErr(WDownUserReq commonReq, WDownUserRes commonRes, String errmsg,
					int responseCode) {
				Toast.makeText(getApplicationContext(), errmsg, Toast.LENGTH_SHORT).show();
			}

		});
	}

	private void DownUserItemByNoteNO() {
		WDownUserReq req = new WDownUserReq();
		req.setOperType("3");
		req.setChargeType(6);
		req.setCbMonth(String.valueOf(currBBItem.getCBMonth()));
		req.setCbYear(String.valueOf(currBBItem.getCBYear()));
		req.setMeterReadingNO(currBBItem.getNoteNo());

		req.setLoginid(preferencesService.getLoginInfo().get("use_id").toString());
		GsonServlet<WDownUserReq, WDownUserRes> gServlet = new GsonServlet<WDownUserReq, WDownUserRes>(this);
		gServlet.request(req, WDownUserRes.class);
		gServlet.setOnResponseEndListening(new OnResponseEndListening<WDownUserReq, WDownUserRes>() {

			@Override
			public void onResponseEnd(WDownUserReq commonReq, WDownUserRes commonRes, boolean result, String errmsg,
					int responseCode) {
			}

			@Override
			public void onResponseEndSuccess(WDownUserReq commonReq, WDownUserRes commonRes, String errmsg,
					int responseCode) {
				if (commonRes != null && commonRes.getUserItems() != null && commonRes.getUserItems().size() > 0) {
					dbHelper.EditCustomerInfo(commonRes.getUserItems());
					Toast.makeText(WCBWeChatActivity.this, "下载成功！", Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onResponseEndErr(WDownUserReq commonReq, WDownUserRes commonRes, String errmsg,
					int responseCode) {
				Toast.makeText(getApplicationContext(), errmsg, Toast.LENGTH_SHORT).show();
			}

		});
	}

	// 获取表本数据
	private void GetBBData(final String userID) {
		WBBReq req = new WBBReq();
		req.setOperType("2");
		req.setLoginID(userID);

		GsonServlet<WBBReq, WBBRes> gServlet = new GsonServlet<WBBReq, WBBRes>(this);
		gServlet.request(req, WBBRes.class);
		gServlet.setOnResponseEndListening(new OnResponseEndListening<WBBReq, WBBRes>() {

			@Override
			public void onResponseEnd(WBBReq commonReq, WBBRes commonRes, boolean result, String errmsg,
					int responseCode) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onResponseEndSuccess(WBBReq commonReq, WBBRes commonRes, String errmsg, int responseCode) {
				// TODO Auto-generated method stub
				if (commonRes != null && commonRes.getBbItems() != null && commonRes.getBbItems().size() > 0) {
					dbHelper.AddBiaoBenInfo(commonRes.getBbItems(), userID);
					handler.sendEmptyMessage(1);
				}

			}

			@Override
			public void onResponseEndErr(WBBReq commonReq, WBBRes commonRes, String errmsg, int responseCode) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), errmsg, Toast.LENGTH_SHORT).show();
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
				WBBItem bbItemAll = new WBBItem();
				bbItemAll.setBId(0);
				bbItemAll.setNoteNo("全部");
				if (ltAp != null) {
					ltAp.add(0, bbItemAll);
				}
				apBBH = new ArrayAdapter<WBBItem>(WCBWeChatActivity.this, android.R.layout.simple_spinner_item, ltAp);
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
