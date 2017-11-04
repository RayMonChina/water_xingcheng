package com.ideal.zsyy.activity;

import com.jijiang.wtapp.R;
import com.ideal.zsyy.entity.OperationRecureTip;
import com.ideal.zsyy.request.OperationRecureTipReq;
import com.ideal.zsyy.response.OperationRecureTipRes;
import com.ideal.zsyy.service.PreferencesService;
import com.ideal2.base.gson.GsonServlet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class OperationRecureActivity extends Activity {

	private String tipId;
	private Button btn_back;
	private TextView tv_pname,tv_psex,tv_pdoctor,tv_tip;
	private PreferencesService preService;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.operation_recure_tip);
		init();
	}
	
	private void init()
	{
		tv_pname=(TextView)findViewById(R.id.tv_pname);
		tv_psex=(TextView)findViewById(R.id.tv_psex);
		tv_pdoctor=(TextView)findViewById(R.id.tv_pdoctor);
		tv_tip=(TextView)findViewById(R.id.tv_tip);
		btn_back=(Button)findViewById(R.id.btn_back);
		preService=new PreferencesService(OperationRecureActivity.this);
		Intent intent=getIntent();
		tipId=intent.getStringExtra("id");
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(!preService.getIsLogin())
		{
			Intent intent=new Intent(OperationRecureActivity.this,LoginActivity.class);
			intent.putExtra("logintype", "oprecure");
			startActivity(intent);
		}
		else {
			getData(tipId);
		}
	}

	private void getData(String strId) {
		OperationRecureTipReq yyreReq = new OperationRecureTipReq();
		yyreReq.setOperType("112");
		yyreReq.setTipId(strId);
		GsonServlet<OperationRecureTipReq, OperationRecureTipRes> gsonServlet = new GsonServlet<OperationRecureTipReq, OperationRecureTipRes>(
				OperationRecureActivity.this);
		gsonServlet.request(yyreReq, OperationRecureTipRes.class);
		gsonServlet
				.setOnResponseEndListening(new GsonServlet.OnResponseEndListening<OperationRecureTipReq, OperationRecureTipRes>() {

					@Override
					public void onResponseEnd(OperationRecureTipReq commonReq,
							OperationRecureTipRes commonRes, boolean result,
							String errmsg, int responseCode) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onResponseEndSuccess(
							OperationRecureTipReq commonReq,
							OperationRecureTipRes commonRes, String errmsg,
							int responseCode) {
						// TODO Auto-generated method stub
						OperationRecureTip operTip=null;
						if (commonRes != null) {
							operTip=commonRes.getRecureTip();
							if(operTip!=null)
							{
								tv_pdoctor.setText(operTip.getDoctorName());
								tv_pname.setText(operTip.getpName());
								tv_psex.setText(operTip.getpSex());
								tv_tip.setText(operTip.getTipContent());
							}
						}
					}

					@Override
					public void onResponseEndErr(OperationRecureTipReq commonReq,
							OperationRecureTipRes commonRes, String errmsg,
							int responseCode) {
						// TODO Auto-generated method stub
					}
				});
	}
	
}
