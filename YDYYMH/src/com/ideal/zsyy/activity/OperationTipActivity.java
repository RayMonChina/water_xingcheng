package com.ideal.zsyy.activity;

import com.jijiang.wtapp.R;
import com.ideal.zsyy.entity.MdsdSspb;
import com.ideal.zsyy.entity.TopVisitResInfo;
import com.ideal.zsyy.request.MdsdSspbDetailReq;
import com.ideal.zsyy.request.YuYueTipReq;
import com.ideal.zsyy.response.MdsdPbDetailRes;
import com.ideal.zsyy.response.SelectTopVisistRes;
import com.ideal.zsyy.service.PreferencesService;
import com.ideal.zsyy.utils.DataUtils;
import com.ideal2.base.gson.GsonServlet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class OperationTipActivity extends Activity {

	private TextView tv_Pname, tv_kssj, tv_opName, tv_ssRoom, tv_ssLevel,
			tv_tc, tv_doctor, tv_mzs;
	private Button btn_back;
	private String pbid;
	private PreferencesService preferencesService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.opertaionplan_deatil);
		pbid = getIntent().getStringExtra("id");
		init();
		if(pbid!=null)
		{
			if(pbid.indexOf("【")>0)
			{
				pbid=pbid.substring(0, pbid.indexOf("【"));
			}
			if(!preferencesService.getIsLogin())
			{
				Intent intent=new Intent(OperationTipActivity.this,LoginActivity.class);
				intent.putExtra("logintype", "operationTip");
				startActivityForResult(intent, 1);
			}
			else {
				getData(pbid);
			}
		}
		
	}
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		getData(pbid);
	}


	private void init() {
		preferencesService=new PreferencesService(getApplicationContext());
		tv_Pname = (TextView) findViewById(R.id.edit_brxm);
		tv_kssj = (TextView) findViewById(R.id.edit_sskssj);
		tv_opName = (TextView) findViewById(R.id.edit_ssName);
		tv_ssRoom = (TextView) findViewById(R.id.edit_ssfj);
		tv_ssLevel = (TextView) findViewById(R.id.edit_ssjb);
		tv_tc = (TextView) findViewById(R.id.edit_sstc);
		tv_doctor = (TextView) findViewById(R.id.edit_ssz);
		tv_mzs = (TextView) findViewById(R.id.edit_mzs);
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	private void getData(String sid) {
		MdsdSspbDetailReq yyreReq = new MdsdSspbDetailReq();
		yyreReq.setOperType("104");
		yyreReq.setPbid(sid);
		GsonServlet<MdsdSspbDetailReq, MdsdPbDetailRes> gsonServlet = new GsonServlet<MdsdSspbDetailReq, MdsdPbDetailRes>(
				OperationTipActivity.this);
		gsonServlet.request(yyreReq, MdsdPbDetailRes.class);
		gsonServlet
				.setOnResponseEndListening(new GsonServlet.OnResponseEndListening<MdsdSspbDetailReq, MdsdPbDetailRes>() {

					@Override
					public void onResponseEnd(MdsdSspbDetailReq commonReq,
							MdsdPbDetailRes commonRes, boolean result,
							String errmsg, int responseCode) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onResponseEndSuccess(MdsdSspbDetailReq commonReq,
							MdsdPbDetailRes commonRes, String errmsg,
							int responseCode) {
						// TODO Auto-generated method stub
						MdsdSspb sspb=null;
						if (commonRes != null) {
							sspb=commonRes.getmSspb();
							if(sspb!=null)
							{
								tv_doctor.setText(sspb.getSURGEON());
								tv_kssj.setText(sspb.getSCHEDULED_DATE_TIME());
								tv_mzs.setText(sspb.getANESTHESIA_DOCTOR());
								tv_opName.setText(sspb.getOPERATION_NAME());
								tv_Pname.setText(sspb.getpName());
								tv_ssLevel.setText(sspb.getOPERATION_SCALE());
								tv_ssRoom.setText(sspb.getOPERATING_ROOM());
								tv_tc.setText(sspb.getSEQUENCE());
							}
						}
					}

					@Override
					public void onResponseEndErr(MdsdSspbDetailReq commonReq,
							MdsdPbDetailRes commonRes, String errmsg,
							int responseCode) {
						// TODO Auto-generated method stub

					}
				});
	}

}
