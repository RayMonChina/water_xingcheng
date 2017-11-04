package com.ideal.zsyy.activity;

import com.ideal.zsyy.db.WdbManager;
import com.ideal.zsyy.entity.WBBItem;
import com.ideal.zsyy.entity.WCBUserEntity;
import com.ideal.zsyy.request.WCBHistoryReq;
import com.ideal.zsyy.response.WBBRes;
import com.ideal.zsyy.response.WCBHistoryRes;
import com.ideal2.base.gson.GsonServlet;
import com.ideal2.base.gson.GsonServlet.OnResponseEndListening;
import com.jijiang.wtapp.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class WCBHistoryActivity extends Activity {

	private TextView tv_w_userno, tv_w_bbh, tv_w_username, tv_w_dh, tv_w_dz,
	tv_w_sybs, tv_w_price_type, tv_w_steal_no, tv_w_cb_tag, tv_w_gps,
	tv_w_benqishuiliang, tv_w_benqishuifei,tv_w_lastchaobiadate, tv_w_avprice, tv_w_charge,
	tv_w_agv_wushuiprice, tv_w_agv_wushui_charge, tv_w_agv_fujia_price,
	tv_w_agv_fujia_charge, tv_title,tv_btn_pre,tv_btn_next,tv_monthvalue;
	private Button btn_back;
	private int searchMonth=0;
	private int searchYear=0;
	WdbManager dbManager;
	private int currentMonth;
	private int currentYear;
	private String stealNo="";
	private WCBUserEntity userEntity;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.w_cb_history);
		stealNo=getIntent().getStringExtra("stealNo");
		this.initView();
		dbManager=new WdbManager(WCBHistoryActivity.this);
		userEntity=dbManager.GetWCBUserItemByNo(stealNo);
		WBBItem bbItem=dbManager.GetSingleBBItem();
		currentMonth=bbItem.getCBMonth();
		currentYear=bbItem.getCBYear();
		searchMonth=currentMonth;
		searchYear=currentYear;
		this.initData(userEntity);
	}

	private void initView()
	{
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
		tv_w_gps = (TextView) findViewById(R.id.tv_w_gps);
		tv_w_benqishuiliang = (TextView) findViewById(R.id.tv_w_benqishuiliang);
		tv_w_benqishuifei = (TextView) findViewById(R.id.tv_w_benqishuifei);
		tv_w_lastchaobiadate = (TextView) findViewById(R.id.tv_w_lastchaobiadate);
		tv_btn_next = (TextView) findViewById(R.id.tv_btn_next);
		tv_btn_pre = (TextView) findViewById(R.id.tv_btn_pre);
		tv_w_avprice = (TextView) findViewById(R.id.tv_w_avprice);
		tv_w_charge = (TextView) findViewById(R.id.tv_w_charge);
		tv_w_agv_wushuiprice = (TextView) findViewById(R.id.tv_w_agv_wushuiprice);
		tv_w_agv_wushui_charge = (TextView) findViewById(R.id.tv_w_agv_wushui_charge);
		tv_w_agv_fujia_price = (TextView) findViewById(R.id.tv_w_agv_fujia_price);
		tv_w_agv_fujia_charge = (TextView) findViewById(R.id.tv_w_agv_fujia_charge);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_monthvalue = (TextView) findViewById(R.id.tv_w_benqidushu);
		if(btn_back!=null)
		{
			btn_back.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
				}
			});
		}
		if(tv_btn_next!=null)
		{
			tv_btn_next.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					CalcCurrDate(true);
					SearchHistory();
				}
			});
		}
		if(tv_btn_pre!=null)
		{
			tv_btn_pre.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					CalcCurrDate(false);
					SearchHistory();
				}
			});
		}
	}

	private void initData(WCBUserEntity userItem)
	{
		if(userItem==null)
		{
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
		tv_w_username.setText(userItem.getUserFName());
		tv_w_userno.setText(userItem.getUserNo());
		tv_w_dh.setText(userItem.getPhone());
		tv_w_dz.setText(userItem.getAddress());
		tv_w_price_type.setText(userItem.getPriceTypeName());
		tv_w_steal_no.setText(userItem.getStealNo());
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
		String disGps = "纬度：" + userItem.getLatitude() + " \n\n经度："
				+ userItem.getLongitude();
		tv_w_gps.setText(disGps);
		tv_title.setText(userItem.getUserFName()+"("+searchYear+"年"+String.valueOf(searchMonth)+"月)");
	}

	private void SearchHistory()
	{
		if(searchYear>currentYear||(searchYear==currentYear&&searchMonth>currentMonth))
		{
			Toast.makeText(WCBHistoryActivity.this,"没有下一月数据", Toast.LENGTH_SHORT).show();
			return;
		}
		if(searchMonth==currentMonth&&searchYear==currentYear)
		{
			userEntity=dbManager.GetWCBUserItemByNo(stealNo);
			this.initData(userEntity);
			return;
		}
		WCBHistoryReq req = new WCBHistoryReq();
		req.setOperType("12");
		req.setCbMonth(searchMonth);
		req.setCbYear(searchYear);
		req.setStealNo(stealNo);

		GsonServlet<WCBHistoryReq, WCBHistoryRes> gServlet = new GsonServlet<WCBHistoryReq, WCBHistoryRes>(
				this);
		gServlet.request(req, WCBHistoryRes.class);
		gServlet.setOnResponseEndListening(new OnResponseEndListening<WCBHistoryReq, WCBHistoryRes>() {

			@Override
			public void onResponseEnd(WCBHistoryReq commonReq, WCBHistoryRes commonRes,
					boolean result, String errmsg, int responseCode) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onResponseEndSuccess(WCBHistoryReq commonReq,
					WCBHistoryRes commonRes, String errmsg, int responseCode) {
				// TODO Auto-generated method stub
				if(commonRes!=null&&commonRes.getUserItem()!=null)
				{
					userEntity=commonRes.getUserItem();
					initData(userEntity);
				}
				else {
					Toast.makeText(WCBHistoryActivity.this, "没有查到数据",Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onResponseEndErr(WCBHistoryReq commonReq,
					WCBHistoryRes commonRes, String errmsg, int responseCode) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), errmsg,
						Toast.LENGTH_SHORT).show();
			}

		});
	}

	private void CalcCurrDate(boolean isNext)
	{
		if(isNext)
		{
			if(searchMonth==12)
			{
				searchYear=searchYear+1;
				searchMonth=1;
			}
			else {
				searchMonth=searchMonth+1;
			}
		}
		else {
			if(searchMonth==1)
			{
				searchYear=searchYear-1;
				searchMonth=12;
			}
			else {
				searchMonth=searchMonth-1;
			}
		}
	}
}
