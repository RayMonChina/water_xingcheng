package com.ideal.zsyy.activity;

import java.util.List;

import org.xbill.DNS.tests.primary;

import com.jijiang.wtapp.R;
import com.ideal.zsyy.db.WdbManager;
import com.ideal.zsyy.entity.WBBItem;
import com.ideal.zsyy.request.WBBReq;
import com.ideal.zsyy.request.WDownUserReq;
import com.ideal.zsyy.request.WSingleUserItemReq;
import com.ideal.zsyy.response.QianFeiHistoryRes;
import com.ideal.zsyy.response.ServerDateTimeRes;
import com.ideal.zsyy.response.WBBRes;
import com.ideal.zsyy.response.WDownUserRes;
import com.ideal.zsyy.service.PreferencesService;
import com.ideal.zsyy.utils.DialogCirleProgress;
import com.ideal2.base.gson.GsonServlet;
import com.ideal2.base.gson.GsonServlet.OnResponseEndListening;

import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class WDownloadDataActivity extends Activity {

	private Button btn_back = null;
	private Spinner spinnerBBH = null;
	private TextView tv_w_name = null, tv_w_month = null, tv_w_totle = null;
	private Button btn_downLoad = null;
	WdbManager dbHelper;
	DialogCirleProgress cProgress = null;
	PreferencesService preferencesService = null;
	List<WBBItem> ltAp = null;
	ArrayAdapter<WBBItem> adapter = null;
	public static int reqUpCode = 1;
	private String userId = "";
	private int bbIndex = 0;
	private String NoteNO = "全部";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.w_download_data);
		dbHelper = new WdbManager(getApplicationContext());
		cProgress = new DialogCirleProgress(WDownloadDataActivity.this);
		preferencesService = new PreferencesService(WDownloadDataActivity.this);
		userId = preferencesService.getLoginInfo().get("use_id").toString();
		// dbHelper.ResetTable();
		initView();
		setEventListener();
		// 判断表本是否为空
		if (dbHelper.BiaoBenInfoExist()) {
			InitSpinner();
		} else {
			GetBBData(this.userId);
		}
	}

	private void initView() {
		btn_back = (Button) findViewById(R.id.btn_back);
		spinnerBBH = (Spinner) findViewById(R.id.sp_bbh);
		tv_w_name = (TextView) findViewById(R.id.tv_w_name);
		tv_w_month = (TextView) findViewById(R.id.tv_w_month);
		tv_w_totle = (TextView) findViewById(R.id.tv_w_totle);
		btn_downLoad = (Button) findViewById(R.id.btn_download);
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

		if (btn_downLoad != null) {
			btn_downLoad.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					CheckUnUpload();
				}
			});
		}
	}

	private void CheckUnUpload() {
		if (dbHelper.CheckUnUploadAll(NoteNO)) {
			Intent intent = new Intent(WDownloadDataActivity.this, WUploadDataActivity.class);
			startActivity(intent);
			Toast.makeText(WDownloadDataActivity.this, "您有未上传的数据", Toast.LENGTH_SHORT).show();
			return;
		}
		DownLoadData();
	}

	@Override
	protected void onResume() {
		super.onResume();
		InitSpinner();
	}

	private void InitSpinner() {
		if (spinnerBBH != null) {
			ltAp = dbHelper.GetBiaoBenInfo();
			if (ltAp == null || ltAp.size() == 0) {
				// GetBBData(preferencesService.getLoginInfo().get("use_id").toString());
				return;
			}
			WBBItem bbItemAll = new WBBItem();
			bbItemAll.setBId(0);
			bbItemAll.setNoteNo("全部");
			bbItemAll.setCBMonth(ltAp.get(0).getCBMonth());
			bbItemAll.setCBYear(ltAp.get(0).getCBYear());
			int totleCount = 0;
			for (WBBItem bb : ltAp) {
				totleCount += bb.getCustomerCount();
			}
			bbItemAll.setCustomerCount(totleCount);
			ltAp.add(0, bbItemAll);
			adapter = new ArrayAdapter<WBBItem>(this, android.R.layout.simple_spinner_item, ltAp);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinnerBBH.setAdapter(adapter);
			spinnerBBH.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
					// TODO Auto-generated method stub

					// arg0.getItemAtPosition(position).
					// String
					// keyString=arg0.getItemAtPosition(position).toString();
					WBBItem wDataItem = (WBBItem) arg0.getItemAtPosition(position);
					if (wDataItem != null) {
						if (tv_w_month != null) {
							tv_w_month.setText(wDataItem.getCBMonth() + "月");
						}
						if (tv_w_name != null) {
							tv_w_name.setText(preferencesService.getLoginInfo().get("userName").toString());
						}
						if (tv_w_totle != null) {
							tv_w_totle.setText(String.valueOf(wDataItem.getCustomerCount()));
						}
						NoteNO = wDataItem.getNoteNo();
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub

				}
			});
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (dbHelper != null) {
			dbHelper.closeDB();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}

	// 获取表本数据
	private void GetBBData(final String userID) {
		WBBReq req = new WBBReq();
		req.setOperType("2");// 获取表本信息
		req.setLoginID(userID);

		GsonServlet<WBBReq, WBBRes> gServlet = new GsonServlet<WBBReq, WBBRes>(this);
		gServlet.request(req, WBBRes.class);
		gServlet.setOnResponseEndListening(new OnResponseEndListening<WBBReq, WBBRes>() {

			@Override
			public void onResponseEnd(WBBReq commonReq, WBBRes commonRes, boolean result, String errmsg,
					int responseCode) {
			}

			@Override
			public void onResponseEndSuccess(WBBReq commonReq, WBBRes commonRes, String errmsg, int responseCode) {
				if (commonRes != null && commonRes.getBbItems() != null && commonRes.getBbItems().size() > 0) {
					dbHelper.AddBiaoBenInfo(commonRes.getBbItems(), userID);
					handler.sendEmptyMessage(1);
				}
			}

			@Override
			public void onResponseEndErr(WBBReq commonReq, WBBRes commonRes, String errmsg, int responseCode) {
				Toast.makeText(getApplicationContext(), errmsg, Toast.LENGTH_SHORT).show();
			}
		});
	}

	// 根据表本号下载用户信息
	private void DownLoadData() {
		// 判断是否为全部
		if (NoteNO.equals("全部")) {
			dbHelper.ResetTB_UserInfo();
			DownUserItems();
		} else {
			// dbHelper.ResetTable();
			dbHelper.ResetTB_UserInfoByNote(NoteNO);
			DownUserItemByNoteNO();
		}
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
		if (bbiItem != null) {
			req.setCbMonth(String.valueOf(bbiItem.getCBMonth()));
			req.setCbYear(String.valueOf(bbiItem.getCBYear()));
			req.setMeterReadingNO(bbiItem.getNoteNo());
		} else {
			Toast.makeText(WDownloadDataActivity.this, "没有查询到数据", Toast.LENGTH_SHORT).show();
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
					dbHelper.AddCustomerInfo(commonRes.getUserItems());
					bbIndex++;
					if (bbIndex == ltAp.size()) {
						Toast.makeText(WDownloadDataActivity.this, "下载成功！", Toast.LENGTH_SHORT).show();	
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
		WBBItem bbiItem=(WBBItem)spinnerBBH.getSelectedItem();
		req.setOperType("3");
		
		req.setCbMonth(String.valueOf(bbiItem.getCBMonth()));
		req.setCbYear(String.valueOf(bbiItem.getCBYear()));
		req.setMeterReadingNO(NoteNO);

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
					dbHelper.AddCustomerInfo(commonRes.getUserItems());
					Toast.makeText(WDownloadDataActivity.this, "下载成功！", Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onResponseEndErr(WDownUserReq commonReq, WDownUserRes commonRes, String errmsg,
					int responseCode) {
				Toast.makeText(getApplicationContext(), errmsg, Toast.LENGTH_SHORT).show();
			}

		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				InitSpinner();
				// DownUserItems();
				break;
			default:
				break;
			}
		}
	};
}
