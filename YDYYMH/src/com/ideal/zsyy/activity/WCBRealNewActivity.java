package com.ideal.zsyy.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ideal.zsyy.db.WdbManager;
import com.ideal.zsyy.entity.ActionItem;
import com.ideal.zsyy.entity.LocationInfo;
import com.ideal.zsyy.entity.WBBItem;
import com.ideal.zsyy.entity.WCBUserEntity;
import com.ideal.zsyy.entity.WCBWaterChargeItem;
import com.ideal.zsyy.request.WBBReq;
import com.ideal.zsyy.request.WDownUserReq;
import com.ideal.zsyy.request.WSingleUserItemReq;
import com.ideal.zsyy.request.WUploadUserReq;
import com.ideal.zsyy.request.WaterChargeReq;
import com.ideal.zsyy.response.ServerDateTimeRes;
import com.ideal.zsyy.response.WBBRes;
import com.ideal.zsyy.response.WDownUserRes;
import com.ideal.zsyy.response.WUploadUserRes;
import com.ideal.zsyy.response.WaterChageRes;
import com.ideal.zsyy.service.BluetoothPrintService;
import com.ideal.zsyy.service.BluetoothService;
import com.ideal.zsyy.service.BluetoothService.bondFinishListener;
import com.ideal.zsyy.service.PreferencesService;
import com.ideal.zsyy.utils.PublicWay;
import com.ideal.zsyy.utils.Utility;
import com.ideal.zsyy.utils.WaterUtils;
import com.ideal.zsyy.view.LayoutFontSize;
import com.ideal.zsyy.view.PrintTemplate;
import com.ideal.zsyy.view.TitlePopup;
import com.ideal.zsyy.view.TitlePopup.OnItemOnClickListener;
import com.ideal2.base.gson.GsonServlet;
import com.ideal2.base.gson.GsonServlet.OnResponseEndListening;
import com.jijiang.wtapp.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class WCBRealNewActivity extends Activity {

	public static final int TAKE_PICTURE = 1;

	//============
	
	//============
	private Button btn_back = null;
	private TextView tv_w_userno, tv_w_bbh, tv_w_username, tv_w_dh, tv_w_dz, tv_w_sybs, tv_w_price_type, tv_w_steal_no,
			tv_w_cb_tag, tv_w_gps, tv_confirm, tv_cancel, tv_w_benqishuiliang, tv_w_benqishuifei, tv_w_lastchaobiadate,
			tv_update_gps, tv_w_avprice, tv_w_charge, tv_w_agv_wushuiprice, tv_w_agv_wushui_charge,
			tv_w_agv_fujia_price, tv_w_agv_fujia_charge, tv_title, tv_confirm_phone, tv_cancel_phone, tv_w_ponsition,
			tv_w_cb_Memo, tv_save_memo1, tv_cannle_memo1;
	private EditText edit_currvalue, edit_phone, edit_memo1;
	private ImageView img_editmonthvalue, img_editphone;
	private TextView tv_monthvalue, tv_btn_next, tv_btn_pre;
	private LinearLayout ll_top_menu, ly_currentvalue, ly_edit_phone, ly_memo_onoff, ly_Memo1, ly_currvalue,
			ly_currvalue_swith;
	private String stealNo = "";
	private WdbManager dbmanager = null;
	private WCBUserEntity userItem = null;
	@SuppressLint("SimpleDateFormat")
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private boolean hasUnFenbiao;
	private PreferencesService preferencesService;
	private Map<String, Object> userInfo;
	// private int op;// 1、顺序抄表
	private int chaobiaoTag = -1;
	private TitlePopup titlePopup;
	private BluetoothService bService;
	private BluetoothPrintService printService;
	private String[] printData;
	private InputMethodManager mInputMethodManager;
	private boolean IsPrint = false;
	private boolean IsUpTimes = true;
	//private int UpCount = 0;
	private int IsUoload;// 0-重新下载，1-上传抄表数据下载 ，2-上传收费下载

	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.w_cb_real_new);
		preferencesService = new PreferencesService(WCBRealNewActivity.this);
		dbmanager = new WdbManager(getApplicationContext());
		stealNo = getIntent().getStringExtra("StealNo");
		userInfo = preferencesService.getLoginInfo();
		SimpleDateFormat simDay = new SimpleDateFormat("d");
		SimpleDateFormat simHour = new SimpleDateFormat("HH");
		SimpleDateFormat simMonth = new SimpleDateFormat("M");
		int CBMonth = 0;
		int MeterDateTimeEnd = (Integer) userInfo.get("meterdatetimeend");
		int Today = Integer.parseInt(simDay.format(new Date()));
		int ThisMonth = Integer.parseInt(simMonth.format(new Date()));

		// op = getIntent().getIntExtra("op", 0);
		chaobiaoTag = getIntent().getIntExtra("ChaoBiaoTag", -1);
		bService = new BluetoothService(this);
		titlePopup = new TitlePopup(WCBRealNewActivity.this);
		titlePopup.addAction(new ActionItem(getResources().getDrawable(R.drawable.wcb_repair_24), "故障报修", 1));
		titlePopup.addAction(new ActionItem(getResources().getDrawable(R.drawable.wcb_address_book_24), "用户建议", 3));
		titlePopup.addAction(new ActionItem(getResources().getDrawable(R.drawable.wcb_history_24), "历史查询", 4));
		titlePopup.addAction(new ActionItem(getResources().getDrawable(R.drawable.wcb_update_24), "重新下载", 7));
		titlePopup.addAction(new ActionItem(getResources().getDrawable(R.drawable.wcb_upload_24), "数据上传", 5));
		// titlePopup.addAction(new
		// ActionItem(getResources().getDrawable(R.drawable.wcb_print), "打印催费",
		// 6));
		List<WBBItem> bbList = dbmanager.GetBiaoBenInfo();
		if (bbList != null && bbList.size() > 0) {
			CBMonth = bbList.get(0).getCBMonth();
		}
		if (ThisMonth == CBMonth) {
			if (Today > MeterDateTimeEnd) {
				IsUpTimes = false;
			} else if (Today == MeterDateTimeEnd) {
				int Hour = Integer.parseInt(simHour.format(new Date()));
				if (Hour > 8) {
					IsUpTimes = false;
				}
			}
		} else {
			IsUpTimes = false;
		}
		initView();
		this.InitData();

		LinearLayout rl = (LinearLayout) findViewById(R.id.ly_maintop);
		LayoutFontSize.SetDefaultFontSize(rl, WCBRealNewActivity.this);
		rl.setOnTouchListener(new View.OnTouchListener() {

			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction() & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_POINTER_DOWN:
					LayoutFontSize.zoomOut(2f, (LinearLayout) findViewById(R.id.ly_maintop), WCBRealNewActivity.this);
					break;
				}
				return true;
			}
		});
	}

	private void initView() {
		btn_back = (Button) findViewById(R.id.btn_back);
		tv_w_bbh = (TextView) findViewById(R.id.tv_w_bbh);
		tv_w_cb_tag = (TextView) findViewById(R.id.tv_w_cb_tag);
		tv_w_dh = (TextView) findViewById(R.id.tv_w_dh);
		tv_w_dz = (TextView) findViewById(R.id.tv_w_dz);
		tv_w_price_type = (TextView) findViewById(R.id.tv_w_price_type);
		tv_w_steal_no = (TextView) findViewById(R.id.tv_w_steal_no);
		tv_w_sybs = (TextView) findViewById(R.id.tv_w_sybs);
		tv_w_username = (TextView) findViewById(R.id.tv_w_username);
		tv_w_userno = (TextView) findViewById(R.id.tv_w_userno);
		tv_w_ponsition = (TextView) findViewById(R.id.tv_w_ponsition);
		tv_w_gps = (TextView) findViewById(R.id.tv_w_gps);
		tv_confirm = (TextView) findViewById(R.id.tv_confirm);
		tv_cancel = (TextView) findViewById(R.id.tv_cancel);
		tv_w_benqishuiliang = (TextView) findViewById(R.id.tv_w_benqishuiliang);
		tv_w_benqishuifei = (TextView) findViewById(R.id.tv_w_benqishuifei);
		tv_w_lastchaobiadate = (TextView) findViewById(R.id.tv_w_lastchaobiadate);
		edit_currvalue = (EditText) findViewById(R.id.edit_currvalue);
		edit_phone = (EditText) findViewById(R.id.edit_phone);
		img_editmonthvalue = (ImageView) findViewById(R.id.img_editmonthvalue);
		img_editphone = (ImageView) findViewById(R.id.img_editphone);
		tv_monthvalue = (TextView) findViewById(R.id.tv_w_benqidushu);
		tv_btn_next = (TextView) findViewById(R.id.tv_btn_next);
		tv_btn_pre = (TextView) findViewById(R.id.tv_btn_pre);
		ll_top_menu = (LinearLayout) findViewById(R.id.ll_top_menu);
		tv_update_gps = (TextView) findViewById(R.id.tv_update_gps);
		tv_w_avprice = (TextView) findViewById(R.id.tv_w_avprice);
		tv_w_charge = (TextView) findViewById(R.id.tv_w_charge);
		tv_w_agv_wushuiprice = (TextView) findViewById(R.id.tv_w_agv_wushuiprice);
		tv_w_agv_wushui_charge = (TextView) findViewById(R.id.tv_w_agv_wushui_charge);
		tv_w_agv_fujia_price = (TextView) findViewById(R.id.tv_w_agv_fujia_price);
		tv_w_agv_fujia_charge = (TextView) findViewById(R.id.tv_w_agv_fujia_charge);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_confirm_phone = (TextView) findViewById(R.id.tv_confirm_phone);
		tv_cancel_phone = (TextView) findViewById(R.id.tv_cancel_phone);
		ly_currentvalue = (LinearLayout) findViewById(R.id.ly_currentvalue);
		ly_edit_phone = (LinearLayout) findViewById(R.id.ly_edit_phone);
		tv_w_cb_Memo = (TextView) findViewById(R.id.tv_w_cb_Memo);
		ly_Memo1 = (LinearLayout) findViewById(R.id.ly_Memo1);
		ly_memo_onoff = (LinearLayout) findViewById(R.id.ly_memo_onoff);
		tv_save_memo1 = (TextView) findViewById(R.id.tv_save_memo1);
		tv_cannle_memo1 = (TextView) findViewById(R.id.tv_cannle_memo1);
		edit_memo1 = (EditText) findViewById(R.id.edit_memo1);
		ly_currvalue = (LinearLayout) findViewById(R.id.ly_currvalue);
		ly_currvalue_swith = (LinearLayout) findViewById(R.id.ly_currvalue_swith);

		TextView TV_Pre = (TextView) this.findViewById(R.id.tv_btn_pre);
		TV_Pre.setTextSize(18);
		TextView TV_Nexp = (TextView) this.findViewById(R.id.tv_btn_next);
		TV_Nexp.setTextSize(18);

		// 备注开关
		if (ly_memo_onoff != null) {
			ly_memo_onoff.setOnClickListener(clickListener);
		}

		if (btn_back != null) {
			btn_back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					finish();
				}
			});
		}
		// 判断是否在上传区间
		if (IsUpTimes) {
			if (ly_currvalue_swith != null) {
				ly_currvalue_swith.setOnClickListener(clickListener);
			}
			if (edit_currvalue != null) {
				edit_currvalue.setOnFocusChangeListener(new OnFocusChangeListener() {

					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						if (!hasFocus) {
							mInputMethodManager.hideSoftInputFromWindow(edit_currvalue.getWindowToken(),
									InputMethodManager.HIDE_NOT_ALWAYS);
						}
					}
				});
			}
			if (tv_confirm != null) {
				tv_confirm.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						int waterValue = 0;
						try {
							String wValue = edit_currvalue.getText().toString();
							if (wValue.isEmpty()) {
								ShowDiagDel(1, "是否删除本条抄表数据？");
								return;
							}
							waterValue = Integer.parseInt(wValue);
							if (waterValue < 0) {
								Toast.makeText(WCBRealNewActivity.this, "抄表数值不能小于0！", Toast.LENGTH_SHORT).show();
								return;
							}

							if (waterValue < userItem.getLastMonthValue()) {
								Toast.makeText(WCBRealNewActivity.this, "本月读数不能小于上月！", Toast.LENGTH_SHORT).show();
								return;
							}
							edit_currvalue.setEnabled(true);
							ChaoBiaoCinfirm(waterValue);

						} catch (Exception e) {
							Toast.makeText(WCBRealNewActivity.this, "抄表数值应为整数！", Toast.LENGTH_SHORT).show();
							return;
						}
					}
				});

			}
			if (tv_cancel != null) {
				tv_cancel.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						HiddenInput();
					}
				});
			}
		}
		// 备注保存
		if (tv_save_memo1 != null) {
			tv_save_memo1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					String MemoText = edit_memo1.getText().toString();

					if (userItem.getMemo1() != MemoText) {
						userItem.setMemo1(MemoText);
						userItem.setAlreadyUpload(1);
						userItem.setOrMemoTag(1);
						dbmanager.UpdateUserItem(userItem);
					}
					tv_w_cb_Memo.setText(MemoText);
					HiddenMemo1();
					// edit_memo1.setVisibility(View.GONE);
					// tv_save_memo1.setVisibility(View.GONE);
					// tv_cannle_memo1.setVisibility(View.GONE);
				}
			});
		}
		// 备注取消
		if (tv_cannle_memo1 != null) {
			tv_cannle_memo1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					HiddenMemo1();
				}
			});
		}

		if (tv_btn_next != null) {
			tv_btn_next.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (img_editmonthvalue != null) {
						img_editmonthvalue.setVisibility(View.VISIBLE);
					}
					if (img_editphone != null) {
						HiddenPhoneEditer();
					}
					GetInfoByOrder(true);
				}
			});
		}
		if (tv_btn_pre != null) {
			tv_btn_pre.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (img_editmonthvalue != null) {
						img_editmonthvalue.setVisibility(View.VISIBLE);
					}
					if (img_editphone != null) {
						HiddenPhoneEditer();
					}
					GetInfoByOrder(false);
				}
			});
		}

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
					OPFaultReport();
					break;
				case 2:// 打印
					titlePopup.removeAction(2);
					OPPrint();
					break;
				case 3:
					OpAdvice();
					break;
				case 4:
					OpHistory();
					break;
				case 5:
					if (userItem.getAlreadyUpload() == 1) {
						OpUploadSingle();
					} else {
						Toast.makeText(WCBRealNewActivity.this, "没有需要上传的数据", Toast.LENGTH_SHORT).show();
					}
					break;
				case 6:
					// OpPrintBeforeFee();
					break;
				case 7:
					ShowDownLoad(1, "注意：你将清除设备中本条数据并从服务器重新下载！");
					break;
				}

			}
		});

		if (tv_update_gps != null) {
			tv_update_gps.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					LocationInfo locationInfo = ((ZsyyApplication) getApplication()).currPoint;
					if (locationInfo != null) {
						userItem.setLatitude(locationInfo.getLatitude());
						userItem.setLongitude(locationInfo.getLontitude());
					}
					userItem.setAlreadyUpload(1);
					userItem.setOrGpsTag(1);
					dbmanager.UpdateUserItem(userItem);
					String disGps = "纬度：" + userItem.getLatitude() + " \n\n经度：" + userItem.getLongitude();
					tv_w_gps.setText(disGps);
				}
			});
		}

		if (ly_edit_phone != null) {
			ly_edit_phone.setOnClickListener(clickListener);
		}

		if (img_editphone != null) {
			img_editphone.setOnClickListener(clickListener);
		}
		if (tv_w_dh != null) {
			// tv_w_dh.setOnClickListener(clickListener);
		}

		if (tv_confirm_phone != null) {
			tv_confirm_phone.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					PhoneEditConfirm(v);
				}
			});
		}
		if (edit_phone != null) {
			edit_phone.setOnFocusChangeListener(new OnFocusChangeListener() {

				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					if (!hasFocus) {
						HiddenPhoneEditer();
					}
				}
			});
		}
		if (tv_cancel_phone != null) {
			tv_cancel_phone.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					HiddenPhoneEditer();
				}
			});
		}
	}

	private void InitData() {
		if (stealNo == null || stealNo == "") {
			return;
		}
		userItem = dbmanager.GetWCBUserItemByNo(stealNo);
		if (userItem == null) {
			Toast.makeText(WCBRealNewActivity.this, "没有查询到数据", Toast.LENGTH_SHORT).show();
			finish();
			return;
		}
		if (userItem != null) {
			preferencesService.saveCurrentCustomerInfo(userItem.getStealNo(), userItem.getOrderNumber(),
					userItem.getPianNo(), userItem.getAreaNo(), userItem.getDuanNo(), userItem.getNoteNo());
		}

		FillData(userItem);
	}

	private void FillData(WCBUserEntity userItems) {
		userItem = dbmanager.GetWCBUserItemByNo(stealNo);
		if (userItem == null) {
			return;
		}
		tv_w_bbh.setText(userItem.getNoteNo());
		if (userItem.getChaoBiaoTag() == 0) {
			tv_w_cb_tag.setText("未抄表");
		}
		if (userItem.getChaoBiaoTag() == 1) {

			tv_w_cb_tag.setText("已抄表");
		}
		if (userItem.getChaoBiaoTag() == 3) {
			tv_w_cb_tag.setText("已收费");
		}
		if (userItem.getOrChaoBiaoTag() > 0) {
			img_editmonthvalue.setVisibility(View.GONE);
		}
		if (!IsUpTimes) {
			img_editmonthvalue.setVisibility(View.GONE);
		}
		tv_w_username.setText(userItem.getUserFName());
		tv_w_userno.setText(userItem.getUserNo());
		tv_w_dh.setText(userItem.getPhone());
		tv_w_dz.setText(userItem.getAddress());
		tv_w_price_type.setText(userItem.getPriceTypeName());
		tv_w_steal_no.setText(userItem.getStealNo());
		tv_w_ponsition.setText(userItem.getWaterMeterPositionName());
		tv_w_sybs.setText(String.valueOf((int) userItem.getLastMonthValue()));
		tv_monthvalue.setText(String.valueOf((int) userItem.getCurrentMonthValue()));
		tv_w_benqishuiliang.setText(String.valueOf((int) userItem.getCurrMonthWNum()));
		tv_w_benqishuifei.setText(String.valueOf(userItem.getTotalCharge()));
		tv_w_lastchaobiadate.setText(userItem.getLastChaoBiaoDate());
		tv_w_avprice.setText(String.valueOf(userItem.getAvePrice()));
		tv_w_charge.setText(String.valueOf(userItem.getCurrMonthFee()));
		tv_w_agv_wushuiprice.setText(String.valueOf(userItem.getExtraChargePrice1()));
		tv_w_agv_wushui_charge.setText(String.valueOf(userItem.getExtraCharge1()));
		tv_w_agv_fujia_price.setText(String.valueOf(userItem.getExtraChargePrice2()));
		tv_w_agv_fujia_charge.setText(String.valueOf(userItem.getExtraCharge2()));
		edit_currvalue.setText(String.valueOf((int) userItem.getCurrentMonthValue()));
		tv_w_cb_Memo.setText(String.valueOf(userItem.getMemo1()));

		IsPrint = false;
		IsUoload = 0;
		//UpCount = 0;
		String disGps = "纬度：" + userItem.getLatitude() + " \n\n经度：" + userItem.getLongitude();
		tv_w_gps.setText(disGps);
		if (userItem.getUserFName().length() > 10) {
			tv_title.setText(userItem.getUserFName().subSequence(0, 10));
		} else {
			tv_title.setText(userItem.getUserFName());
		}
		edit_phone.setText(userItem.getPhone());

		tv_w_cb_Memo.setText(userItem.getMemo1());

		if (userItem.getIsSummaryMeter() == 2) {
			tv_title.setText(userItem.getUserFName() + "-总表");
		}

		HiddenInput();
		HiddenMemo1();
		HiddenPhoneEditer();

		boolean IsShowPrint = false;
		if (IsUpTimes) {
			if (this.userItem != null && this.userItem.getWaterUserchargeType() != null
					&& this.userItem.getWaterUserchargeType().trim().equals("0")) {
				if (!this.userItem.getNoteNo().startsWith("a") && !this.userItem.getNoteNo().startsWith("A")) {
					if (userItem.getOrChaoBiaoTag() == 3) {
						if (userItem.getIsPrint() == 1) {
							IsShowPrint = true;
						}
					} else if (userItem.getOrChaoBiaoTag() == 1) {
						IsShowPrint = true;
					}
				}
			}
		}
		if (IsShowPrint) {
			if (!titlePopup.checkExists(2)) {
				titlePopup.addAction(new ActionItem(getResources().getDrawable(R.drawable.wcb_print), "打印小票", 2));
			}
		} else {
			if (titlePopup.checkExists(2)) {
				titlePopup.removeAction(2);
			}
		}

	}

	// 隐藏电话输入框
	private void HiddenPhoneEditer() {
		tv_w_dh.setVisibility(View.VISIBLE);
		ly_edit_phone.setVisibility(View.VISIBLE);
		tv_confirm_phone.setVisibility(View.GONE);
		tv_cancel_phone.setVisibility(View.GONE);
		edit_phone.setVisibility(View.GONE);
	}

	// 提示框-清除抄表
	protected void ShowDiagDel(final int operate, String title) {
		AlertDialog.Builder builder = new AlertDialog.Builder(WCBRealNewActivity.this);
		builder.setTitle(title);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				if (operate == 1) {
					ClearChaoBiaodata();
				}
			}
		});

		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}

		});
		builder.create().show();
	}

	// 隐藏输入框
	private void HiddenInput() {
		edit_currvalue.clearFocus();
		tv_confirm.setVisibility(View.GONE);
		tv_cancel.setVisibility(View.GONE);
		edit_currvalue.setVisibility(View.GONE);
	}

	// 隐藏备注
	private void HiddenMemo1() {
		edit_memo1.setVisibility(View.GONE);
		tv_save_memo1.setVisibility(View.GONE);
		tv_cannle_memo1.setVisibility(View.GONE);
	}

	// 重新下载提示框
	protected void ShowDownLoad(final int operate, String title) {
		AlertDialog.Builder builder = new AlertDialog.Builder(WCBRealNewActivity.this);
		builder.setTitle(title);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				if (operate == 1) {
					IsUoload = 0;
					GetSingleMeterData();
				}
			}
		});

		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}

		});
		builder.create().show();
	}

	// 清除抄表数据
	private void ClearChaoBiaodata() {
		userItem.setCurrMonthWNum(0);
		userItem.setCurrentMonthValue(0);
		userItem.setCurrMonthFee(0);
		userItem.setAvePrice(0);
		userItem.setTotalCharge(0);
		userItem.setExtraTotalCharge(0);
		userItem.setExtraChargePrice1(0);
		userItem.setExtraChargePrice2(0);
		userItem.setExtraCharge1(0);
		userItem.setExtraCharge2(0);
		userItem.setPreMoney(0);
		userItem.setOweMoney(0);
		userItem.setChaoBiaoTag(0);
		userItem.setAlreadyUpload(0);
		userItem.setOrMemoTag(0);
		userItem.setIsChaoBiao(0);

		ly_currentvalue.setVisibility(View.VISIBLE);
		edit_currvalue.setVisibility(View.GONE);
		tv_confirm.setVisibility(View.GONE);
		tv_cancel.setVisibility(View.GONE);

		dbmanager.UpdateUserItem(userItem);
		FillData(userItem);
	}

	// 抄表数据保存
	private void ChaoBiaoCinfirm(double waterValue) {
		double monthNum = Math.abs(waterValue - userItem.getLastMonthValue());
		if (userItem.getIsSummaryMeter() == 2) {
			monthNum = monthNum - dbmanager.GetDisWaterMeterValue(userItem.getStealNo());
		}
		double avgPrice = WaterUtils.GetAvgPrice(userItem.getStepPrice());
		Map<String, Double> dicExtPrice = WaterUtils.GetExtPrice(userItem.getExtraPrice());
		double waterCharge = WaterUtils.round(monthNum * avgPrice, 2);
		double extCharge1 = WaterUtils.round(dicExtPrice.get("F1") * monthNum, 2);
		double extCharge2 = WaterUtils.round(dicExtPrice.get("F2") * monthNum * avgPrice, 2);
		double dbWPrice = WaterUtils.round(waterCharge + extCharge1 + extCharge2, 2);
		userItem.setCurrMonthWNum((int) monthNum);
		userItem.setCurrentMonthValue(waterValue);
		userItem.setCurrMonthFee(waterCharge);
		userItem.setAvePrice(avgPrice);
		userItem.setTotalCharge(dbWPrice);
		userItem.setExtraTotalCharge(WaterUtils.round(extCharge1 + extCharge2, 3));
		userItem.setExtraChargePrice1(dicExtPrice.get("F1"));
		userItem.setExtraChargePrice2(dicExtPrice.get("F2"));
		userItem.setExtraCharge1(extCharge1);
		userItem.setExtraCharge2(extCharge2);
		double preMoney = userItem.getOrPreMoney();
		double oweMoney = userItem.getOrOweFee();
		double levMoney = userItem.getShouFei() + preMoney - dbWPrice - oweMoney;
		if (levMoney < 0) {
			preMoney = 0;
			oweMoney = Math.abs(levMoney);
		} else {
			preMoney = levMoney;
			oweMoney = 0;
		}
		userItem.setPreMoney(WaterUtils.round(preMoney, 3));
		userItem.setOweMoney(WaterUtils.round(oweMoney, 3));
		userItem.setChaoBiaoTag(1);
		userItem.setCheckState("1");
		userItem.setCheckDateTime(simpleDateFormat.format(new Date()));
		userItem.setChaoBiaoDate(simpleDateFormat.format(new Date()));
		userItem.setAlreadyUpload(1);
		userItem.setIsChaoBiao(1);

		ly_currentvalue.setVisibility(View.VISIBLE);
		edit_currvalue.setVisibility(View.GONE);
		tv_confirm.setVisibility(View.GONE);
		tv_cancel.setVisibility(View.GONE);

		dbmanager.UpdateUserItem(userItem);

		FillData(userItem);
	}

	// 下一条、上一条
	private void GetInfoByOrder(boolean isnext) {
		Map<String, Object> currCustomer = preferencesService.GetCurrentCustomerInfo();
		String NoteNo = currCustomer.get("NoteNo").toString();
		int orderNo = Integer.parseInt(currCustomer.get("OrderNo").toString());
		String StealNo = currCustomer.get("StealNo").toString();
		userItem = dbmanager.GetWCBUserItemByOrder(NoteNo, StealNo, orderNo, isnext, chaobiaoTag);
		if (userItem == null) {
			if (isnext) {
				Toast.makeText(WCBRealNewActivity.this, "没有下一条数据", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(WCBRealNewActivity.this, "没有上一条数据", Toast.LENGTH_SHORT).show();
			}
			return;
		}
		preferencesService.saveCurrentCustomerInfo(userItem.getStealNo(), userItem.getOrderNumber(),
				userItem.getPianNo(), userItem.getAreaNo(), userItem.getDuanNo(), userItem.getNoteNo());
		stealNo = userItem.getStealNo();

		FillData(userItem);
	}

	// 总分表检查
	private void ShowDiag(final int operate, String title) {
		AlertDialog.Builder builder = new AlertDialog.Builder(WCBRealNewActivity.this);
		builder.setTitle(title);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				if (operate == 1) {
					String wValue = edit_currvalue.getText().toString();
					double waterValue = Double.parseDouble(wValue);
					if (userItem != null) {
						userItem.setIsReverse(1);
						ChaoBiaoCinfirm(waterValue);
					}

				}
			}
		});

		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}

		});
		builder.create().show();
	}

	// 按钮事件
	private OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			// case R.id.tv_w_benqidushu:
			// // case R.id.img_editmonthvalue:
			// case R.id.tv_w_lastchaobiadate:
			// case R.id.tv_w_benqishuiliang:
			// case R.id.ly_currentvalue:
			case R.id.ly_currvalue_swith:
				// EditMonthClickMethod();
				if (img_editphone != null) {
					HiddenPhoneEditer();
				}
				ChangeMonthMethod();
				break;
			case R.id.img_editphone:
			case R.id.tv_w_dh:
			case R.id.ly_edit_phone:
				if (img_editmonthvalue != null) {
					img_editmonthvalue.setVisibility(View.VISIBLE);
				}
				EditPhoneClickMethod();
				break;
			case R.id.ly_memo_onoff:
				if (img_editmonthvalue != null) {
					img_editmonthvalue.setVisibility(View.VISIBLE);
				}
				if (img_editphone != null) {
					HiddenPhoneEditer();
				}
				EditMemoSwitch();
				break;
			}
		}

		private void ChangeMonthMethod() {
			if (!(edit_currvalue.getVisibility() == View.VISIBLE)) {
				EditMonthClickMethod();
			}

		}
	};

	// 备注编辑显示
	private void EditMemoSwitch() {
		edit_memo1.setVisibility(View.VISIBLE);
		tv_save_memo1.setVisibility(View.VISIBLE);
		tv_cannle_memo1.setVisibility(View.VISIBLE);

		edit_memo1.setText(tv_w_cb_Memo.getText());
	}

	// 电话编辑显示
	private void EditPhoneClickMethod() {
		tv_w_dh.setVisibility(View.GONE);
		ly_edit_phone.setVisibility(View.GONE);
		tv_confirm_phone.setVisibility(View.VISIBLE);
		tv_cancel_phone.setVisibility(View.VISIBLE);
		edit_phone.setVisibility(View.VISIBLE);
	}

	// 电话编辑保存
	private void PhoneEditConfirm(View v) {
		String phoneNumber = edit_phone.getText().toString();
		if (!phoneNumber.equals(userItem.getPhone())) {
			userItem.setPhone(phoneNumber);
			userItem.setAlreadyUpload(1);
			userItem.setOrPhoneTag(1);
			tv_w_dh.setText(phoneNumber);
			dbmanager.UpdateUserItem(userItem);
		}
		HiddenPhoneEditer();
	}

	// 抄表框打开
	private void EditMonthClickMethod() {
		if (userItem.getOrChaoBiaoTag() != 0) {
			Toast.makeText(WCBRealNewActivity.this, "该表已经抄过", Toast.LENGTH_SHORT).show();
			return;
		}
		if (userItem.getIsSummaryMeter() == 2) {
			hasUnFenbiao = dbmanager.HasUnChildWaterMeter(userItem.getStealNo(), userItem.getUserNo());
			if (hasUnFenbiao) {
				ShowDiag(2, "该表为总表，请先抄分表");
				return;
			}

		}

		mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

		edit_currvalue.setVisibility(View.VISIBLE);
		edit_currvalue.setEnabled(true);
		edit_currvalue.setFocusable(true);
		edit_currvalue.setFocusableInTouchMode(true);
		edit_currvalue.requestFocus();
		mInputMethodManager.showSoftInput(edit_currvalue, 0);
		tv_cancel.setVisibility(View.VISIBLE);
		tv_confirm.setVisibility(View.VISIBLE);
		if (userItem != null) {
			if (userItem.getOrChaoBiaoTag() > 0 || userItem.getChaoBiaoTag() == 3) {
				edit_currvalue.setVisibility(View.GONE);
				tv_cancel.setVisibility(View.GONE);
				tv_confirm.setVisibility(View.GONE);
			}
			if (userItem.getCurrentMonthValue() == 0) {
				edit_currvalue.setText("");
			}
			// 定量用水
			if (userItem.getWaterFixValue() > 0) {
				edit_currvalue.setText(
						String.valueOf((int) userItem.getWaterFixValue() + (int) userItem.getLastMonthValue()));
				edit_currvalue.setEnabled(false);
				edit_currvalue.clearFocus();
				mInputMethodManager.hideSoftInputFromWindow(edit_currvalue.getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
	}

	// 故障保修
	private void OPFaultReport() {
		Intent intent = new Intent(WCBRealNewActivity.this, WFaultReportActivity.class);
		intent.putExtra("UserNo", userItem.getUserNo());
		startActivity(intent);
	}

	// 小票打印
	private void OPPrint() {
		if (userItem.getAlreadyUpload() == 1) {
			Toast.makeText(WCBRealNewActivity.this, "请先进行数据上传！", Toast.LENGTH_SHORT).show();
			return;
		}

		if (userItem.getTotalCharge() == 0d) {
			Toast.makeText(WCBRealNewActivity.this, "该用户不能打印小票-0", Toast.LENGTH_SHORT).show();
			return;
		}

		if (userItem == null || userItem.getChaoBiaoTag() == 0) {
			Toast.makeText(WCBRealNewActivity.this, "现在不能打印小票", Toast.LENGTH_SHORT).show();
			return;
		}

		if (userItem.getChaoBiaoTag() == 3 && userItem.getIsPrint() == 0) {
			Toast.makeText(WCBRealNewActivity.this, "已经打印过小票，不能重复打印", Toast.LENGTH_SHORT).show();
			return;
		}

		IsPrint = true;
		UploadUserFeeState();
	}

	// 蓝牙打印机打印
	private void BluePrint() {
		printData = PrintTemplate.GetPrintData(userItem, WCBRealNewActivity.this);
		if (!bService.isOpen()) {
			bService.open();
		}

		if (printService == null) {
			printService = new BluetoothPrintService(WCBRealNewActivity.this);
		}
		if (printService.connect()) {
			if (printService.send(printData) == 0) {
				handler.sendEmptyMessage(1);
			}
		} else {
			bService.searchDevice();
		}
		bService.setBonListener(new bondFinishListener() {

			@Override
			public void doOperate() {
				if (printService == null) {
					printService = new BluetoothPrintService(WCBRealNewActivity.this);
				}
				if (printService.connect()) {
					if (printService.send(printData) == 0) {
						handler.sendEmptyMessage(1);
					}
				}
			}
		});
	}

	// 判断打印机状态，上传修改收费信息
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				dbmanager.ChangePrintState(0, userItem.getReadMeterRecordId());
				//IsPrint=false;
				FillData(userItem);
				// 改变打印状态
				// userItem.setIsPrint(0);
				// dbmanager.UpdateUserItem(userItem);
				break;
			default:
				titlePopup.addAction(new ActionItem(getResources().getDrawable(R.drawable.wcb_print), "打印小票", 2));
				break;
			}
		}

	};

	// 用户建议
	private void OpAdvice() {
		Intent intent = new Intent(WCBRealNewActivity.this, WCustomAdviceActivity.class);
		intent.putExtra("UserNo", userItem.getUserNo());
		startActivity(intent);
	}

	// 历史查询
	private void OpHistory() {
		Intent intent = new Intent(WCBRealNewActivity.this, WCBHistoryActivity.class);
		intent.putExtra("stealNo", userItem.getStealNo());
		startActivity(intent);
	}

	// 上传用户收费状态
	private void UploadUserFeeState() {
		WSingleUserItemReq req = new WSingleUserItemReq();
		req.setOperType("19");
		req.setReadMeterRecordId(userItem.getReadMeterRecordId());

		GsonServlet<WSingleUserItemReq, WUploadUserRes> gServlet = new GsonServlet<WSingleUserItemReq, WUploadUserRes>(
				this);
		gServlet.request(req, WUploadUserRes.class);
		gServlet.setOnResponseEndListening(new OnResponseEndListening<WSingleUserItemReq, WUploadUserRes>() {

			@Override
			public void onResponseEnd(WSingleUserItemReq commonReq, WUploadUserRes commonRes, boolean result,
					String errmsg, int responseCode) {
			}

			@Override
			public void onResponseEndSuccess(WSingleUserItemReq commonReq, WUploadUserRes commonRes, String errmsg,
					int responseCode) {
				IsUoload = 2;
				dbmanager.ChangeState(3, userItem.getReadMeterRecordId());
				//FillData(userItem);
				
				GetSingleMeterDataOver();
			}

			@Override
			public void onResponseEndErr(WSingleUserItemReq commonReq, WUploadUserRes commonRes, String errmsg,
					int responseCode) {
				Toast.makeText(getApplicationContext(), errmsg, Toast.LENGTH_SHORT).show();
			}

		});

	}
	private void GetSingleMeterDataOver() {
		WSingleUserItemReq req = new WSingleUserItemReq();
		req.setOperType("14");
		req.setReadMeterRecordId(userItem.getReadMeterRecordId());

		GsonServlet<WSingleUserItemReq, WDownUserRes> gServlet = new GsonServlet<WSingleUserItemReq, WDownUserRes>(
				this);
		gServlet.request(req, WDownUserRes.class);
		gServlet.setOnResponseEndListening(new OnResponseEndListening<WSingleUserItemReq, WDownUserRes>() {
			@Override
			public void onResponseEnd(WSingleUserItemReq commonReq, WDownUserRes commonRes, boolean result,
					String errmsg, int responseCode) {

			}

			@Override
			public void onResponseEndSuccess(WSingleUserItemReq commonReq, WDownUserRes commonRes, String errmsg,
					int responseCode) {
				if (commonRes != null && commonRes.getUserItems() != null && commonRes.getUserItems().size() > 0) {
					dbmanager.EditCustomerInfo(commonRes.getUserItems());

						dbmanager.ChangePrintState(1, userItem.getReadMeterRecordId());
						
						BluePrint();
					
					return;
				}

			}

			@Override
			public void onResponseEndErr(WSingleUserItemReq commonReq, WDownUserRes commonRes, String errmsg,
					int responseCode) {
				Toast.makeText(getApplicationContext(), errmsg, Toast.LENGTH_SHORT).show();
			}

		});
	}

	// 单条数据下载
	private void GetSingleMeterData() {
		WSingleUserItemReq req = new WSingleUserItemReq();
		req.setOperType("14");
		req.setReadMeterRecordId(userItem.getReadMeterRecordId());

		GsonServlet<WSingleUserItemReq, WDownUserRes> gServlet = new GsonServlet<WSingleUserItemReq, WDownUserRes>(
				this);
		gServlet.request(req, WDownUserRes.class);
		gServlet.setOnResponseEndListening(new OnResponseEndListening<WSingleUserItemReq, WDownUserRes>() {
			@Override
			public void onResponseEnd(WSingleUserItemReq commonReq, WDownUserRes commonRes, boolean result,
					String errmsg, int responseCode) {

			}

			@Override
			public void onResponseEndSuccess(WSingleUserItemReq commonReq, WDownUserRes commonRes, String errmsg,
					int responseCode) {
				if (commonRes != null && commonRes.getUserItems() != null && commonRes.getUserItems().size() > 0) {
					// 上传和下载数据不一致
					// 要判断是否上传数据
					WCBUserEntity item = commonRes.getUserItems().get(0);
					//IsUoload:0-直接下载；1-打印上传后下载；2-单条上传后下载；
//					if (IsUoload != 0 ) {
//						if(item.getCurrentMonthValue() != userItem.getCurrentMonthValue() && item.getChaoBiaoTag() != userItem.getChaoBiaoTag()){
						//UpCount++;
						//if (UpCount > 3) {
//							if (IsUoload == 1) {
//								OpUploadSingle();
//								return;
//							}
//							if (IsUoload == 2) {
//								UploadUserFeeState();
//								return;
							//}
						//} else {
							//UpCount = 0;
							//Toast.makeText(WCBRealNewActivity.this, "上传数据出错，请重新操作！", Toast.LENGTH_SHORT).show();
						//}
						//return;
//						}
//					}
					dbmanager.EditCustomerInfo(commonRes.getUserItems());

					if (IsPrint) {
						dbmanager.ChangePrintState(1, userItem.getReadMeterRecordId());
						//FillData(item);
						// IsPrint = false;
						// 改变打印状态

						// userItem.setIsPrint(1);
						// dbmanager.UpdateUserItem(userItem);
						// IsPrint = true;
						//FillData(userItem);
						BluePrint();
					} else {
						String ShowText=IsUoload==0?"下载成功！":IsUoload==1?"上传成功！":IsUoload==2?"收费数据已上传！":"未知错误";
							
						FillData(item);
						
						Toast.makeText(WCBRealNewActivity.this,ShowText, Toast.LENGTH_SHORT).show();
					}

					return;
				}

			}

			@Override
			public void onResponseEndErr(WSingleUserItemReq commonReq, WDownUserRes commonRes, String errmsg,
					int responseCode) {
				Toast.makeText(getApplicationContext(), errmsg, Toast.LENGTH_SHORT).show();
			}

		});
	}

	// 单条数据上传
	private void OpUploadSingle() {
		tv_confirm.setVisibility(View.GONE);
		tv_cancel.setVisibility(View.GONE);
		edit_currvalue.setVisibility(View.GONE);

		if (userItem == null) {
			return;
		}
		WUploadUserReq req = new WUploadUserReq();
		req.setOperType("5");
		WCBUserEntity userEntity = userItem;
		req.setAvePrice(userEntity.getAvePrice());
		req.setChargeID(userEntity.getChargeID());
		req.setChargeState(String.valueOf(userEntity.getChaoBiaoTag()));
		req.setCheckDateTime(userEntity.getCheckDateTime());
		req.setChecker(userEntity.getChecker());
		req.setCheckState(userEntity.getCheckState());
		req.setExtraCharge1(userEntity.getExtraCharge1());
		req.setExtraCharge2(userEntity.getExtraCharge2());
		req.setExtraChargePrice1(userEntity.getExtraChargePrice1());
		req.setExtraChargePrice2(userEntity.getExtraChargePrice2());
		req.setExtraTotalCharge(userEntity.getExtraTotalCharge());
		req.setOVERDUEMONEY(userEntity.getOVERDUEMONEY());
		req.setReadMeterRecordDate(userEntity.getChaoBiaoDate());
		req.setReadMeterRecordId(userEntity.getReadMeterRecordId());
		req.setTotalCharge(userEntity.getTotalCharge());
		req.setTotalNumber((int) userEntity.getCurrMonthWNum());
		req.setWaterMeterEndNumber((int) userEntity.getCurrentMonthValue());
		req.setWaterTotalCharge(userEntity.getCurrMonthFee());
		req.setLatitude(String.valueOf(userEntity.getLatitude()));
		req.setLongitude(String.valueOf(userEntity.getLongitude()));
		req.setPhone(userEntity.getPhone());
		req.setMemo1(userEntity.getMemo1());
		GsonServlet<WUploadUserReq, WUploadUserRes> gServlet = new GsonServlet<WUploadUserReq, WUploadUserRes>(this);
		gServlet.request(req, WUploadUserRes.class);
		gServlet.setOnResponseEndListening(new OnResponseEndListening<WUploadUserReq, WUploadUserRes>() {

			@Override
			public void onResponseEnd(WUploadUserReq commonReq, WUploadUserRes commonRes, boolean result, String errmsg,
					int responseCode) {

			}

			@Override
			public void onResponseEndSuccess(WUploadUserReq commonReq, WUploadUserRes commonRes, String errmsg,
					int responseCode) {
				if (commonRes != null) {
					userItem.setAlreadyUpload(0);
					userItem.setOrChaoBiaoTag(1);
					userItem.setOrPhoneTag(0);
					userItem.setOrMemoTag(0);
					userItem.setOrGpsTag(0);
					userItem.setIsChaoBiao(0);
					dbmanager.UpdateUserUploadTag(userItem.getUserID());
					// 单条上传,如果打印失败，不重新下载数据，打印成功IsPrint=0，IsPrint=1未打印
					if (userItem.getIsPrint() != 1) {
						IsUoload = 1;
						GetSingleMeterData();
					} else {
						Toast.makeText(WCBRealNewActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
						FillData(userItem);
					}
					return;
				}
			}

			@Override
			public void onResponseEndErr(WUploadUserReq commonReq, WUploadUserRes commonRes, String errmsg,
					int responseCode) {
				Toast.makeText(getApplicationContext(), errmsg, Toast.LENGTH_SHORT).show();
			}

		});
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			for (int i = 0; i < PublicWay.activityList.size(); i++) {
				if (null != PublicWay.activityList.get(i)) {
					PublicWay.activityList.get(i).finish();
				}
			}
			finish();
		}
		return true;
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (dbmanager != null) {
			dbmanager.closeDB();
		}
		if (bService != null) {
			bService.unRegisterBlue();
		}
		if (printService != null) {
			printService.disconnect();
		}
	}
}