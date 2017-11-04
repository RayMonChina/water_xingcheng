package com.ideal.zsyy.activity;

import com.jijiang.wtapp.R;
import com.ideal.zsyy.entity.TopVisitResInfo;
import com.ideal.zsyy.request.CancelTopVisitReq;
import com.ideal.zsyy.request.SmsReq;
import com.ideal.zsyy.request.YuYueTipReq;
import com.ideal.zsyy.response.CancelTopVisitRes;
import com.ideal.zsyy.response.SelectTopVisistRes;
import com.ideal.zsyy.service.PreferencesService;
import com.ideal.zsyy.utils.DataUtils;
import com.ideal2.base.gson.GsonServlet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class YuyueTipActivity extends Activity {

	private TextView tv_hos, tv_seq, tv_dept, tv_professional, tv_yuyueTime,
			tv_pName;
	private Button btn_back;
	private String id;// 预约编号
	PreferencesService preferencesService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.yuyue_tip);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Intent intent = getIntent();
		id = intent.getStringExtra("id");
		if(id.indexOf("【")>-1)
		{
			id=id.substring(0,id.indexOf("【"));
		}
		init();
		if(!preferencesService.getIsLogin())
		{
			Intent intentLogin=new Intent(YuyueTipActivity.this,LoginActivity.class);
			intentLogin.putExtra("logintype","yuyueTip");
			startActivityForResult(intentLogin,1);
		}
		else {
			getYuyueInfo(id);
		}
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(preferencesService.getIsLogin())
		{
			getYuyueInfo(id);
		}
	}

	private void init() {
		preferencesService=new PreferencesService(getApplicationContext());
		tv_hos = (TextView) findViewById(R.id.tv_locate);
		tv_seq = (TextView) findViewById(R.id.pc_yuyue_no);
		tv_dept = (TextView) findViewById(R.id.pc_yuyue_room_or_person);
		tv_professional = (TextView) findViewById(R.id.pc_yuyue_professional);
		tv_yuyueTime = (TextView) findViewById(R.id.pc_yuyue_time);
		tv_pName = (TextView) findViewById(R.id.pc_pat_name);
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	private void getYuyueInfo(String sid) {
		YuYueTipReq yyreReq = new YuYueTipReq();
		yyreReq.setOperType("103");
		yyreReq.setId(sid);
		GsonServlet<YuYueTipReq, SelectTopVisistRes> gsonServlet = new GsonServlet<YuYueTipReq, SelectTopVisistRes>(
				YuyueTipActivity.this);
		gsonServlet.request(yyreReq, SelectTopVisistRes.class);
		gsonServlet
				.setOnResponseEndListening(new GsonServlet.OnResponseEndListening<YuYueTipReq, SelectTopVisistRes>() {

					@Override
					public void onResponseEnd(YuYueTipReq commonReq,
							SelectTopVisistRes commonRes, boolean result,
							String errmsg, int responseCode) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onResponseEndSuccess(YuYueTipReq commonReq,
							SelectTopVisistRes commonRes, String errmsg,
							int responseCode) {
						// TODO Auto-generated method stub
						TopVisitResInfo topInfo = null;
						String locate = "";
						if (commonRes != null) {
							if (commonRes.getVisitInfo() != null
									&& commonRes.getVisitInfo().size() > 0) {
								topInfo = commonRes.getVisitInfo().get(0);
								locate = topInfo.getReg_locate();

								if ("S".equals(locate)) {
									tv_hos.setText("上海市第一个人民医院南院");
								} else if ("N".equals(locate)) {
									tv_hos.setText("上海市第一个人民医院北院");
								} else {
									tv_hos.setText("上海市第一个人民医院");
								}
								tv_seq.setText(topInfo.getSeqn());
								String time1 = DataUtils.convertString(
										topInfo.getReg_time(), locate);
								String time = topInfo.getReg_date() + "  "
										+ time1;
								tv_yuyueTime.setText(time);
								tv_dept.setText(topInfo.getReg_dept_name());
								tv_pName.setText(topInfo.getPat_name());
								tv_professional.setText(topInfo
										.getReg_doc_name());
							}
						}
					}

					@Override
					public void onResponseEndErr(YuYueTipReq commonReq,
							SelectTopVisistRes commonRes, String errmsg,
							int responseCode) {
						// TODO Auto-generated method stub

					}
				});
	}
}
