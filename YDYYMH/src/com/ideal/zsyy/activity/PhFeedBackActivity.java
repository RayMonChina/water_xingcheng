package com.ideal.zsyy.activity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ideal.zsyy.Config;
import com.jijiang.wtapp.R;
import com.ideal.zsyy.entity.PhFeedBack;
import com.ideal.zsyy.entity.PhUser;
import com.ideal.zsyy.request.PhFeedBackReq;
import com.ideal.zsyy.response.PhFeedBackRes;
import com.ideal2.base.gson.GsonServlet;
import com.ideal2.base.gson.GsonServlet.OnResponseEndListening;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PhFeedBackActivity extends Activity {

	private EditText et_username;
	private EditText et_feedback;
	private EditText et_user_phone;
	private EditText et_e_mail;
	private PhUser phUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.phfeedback);
		phUser = Config.phUsers;
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		Button btn_back = (Button) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();

			}
		});
		et_username = (EditText) findViewById(R.id.et_user_name);
		et_feedback = (EditText) findViewById(R.id.et_feedback);
		et_user_phone = (EditText) findViewById(R.id.et_user_phone);
		et_e_mail = (EditText) findViewById(R.id.et_e_mail);

		if (phUser != null) {
			et_username.setText(phUser.getName());
			et_user_phone.setText(phUser.getUser_Account());
			et_e_mail.setText(phUser.getEMail());
		}

		Button pf_bt_submit = (Button) findViewById(R.id.pf_bt_submit);
		pf_bt_submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getFeedBack(et_e_mail.getText().toString(), et_username
						.getText().toString(), et_user_phone.getText()
						.toString(), et_feedback.getText().toString());
			}
		});
	}

	public void getFeedBack(final String email, final String username,
			final String userphone, final String feedback) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		PhFeedBackReq req = new PhFeedBackReq();
		PhFeedBack feedBack = new PhFeedBack();
		feedBack.setE_mail(email);
		feedBack.setFeed_back(feedback);
		feedBack.setFeed_datetime(sdf.format(new Date()));
		feedBack.setHosp_id(Config.hosId);
		String userDevice = android.os.Build.MODEL + " OS " + android.os.Build.VERSION.RELEASE;
		 feedBack.setUser_device(userDevice);
		String id = "";
		if (phUser != null) {
			id = phUser.getId();
		}
		feedBack.setUser_id(id);
		feedBack.setUser_name(username);
		feedBack.setUser_phone(userphone);
		req.setOperType("25"); 
		req.setFeedback(feedBack);

		GsonServlet<PhFeedBackReq, PhFeedBackRes> gSerlvet = new GsonServlet<PhFeedBackReq, PhFeedBackRes>(
				this);
		gSerlvet.request(req, PhFeedBackRes.class);
		gSerlvet.setOnResponseEndListening(new OnResponseEndListening<PhFeedBackReq, PhFeedBackRes>() {

			@Override
			public void onResponseEnd(PhFeedBackReq commonReq,
					PhFeedBackRes commonRes, boolean result, String errmsg,
					int responseCode) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onResponseEndSuccess(PhFeedBackReq commonReq,
					PhFeedBackRes commonRes, String errmsg, int responseCode) {
				// TODO Auto-generated method stub
				if (commonRes != null) {
					String result = commonRes.getResult();
					if ("1".equals(result)) {
						Toast.makeText(PhFeedBackActivity.this, "谢谢你的反馈", 1).show();
						finish();
					}
				}
			}

			@Override
			public void onResponseEndErr(PhFeedBackReq commonReq,
					PhFeedBackRes commonRes, String errmsg, int responseCode) {
				// TODO Auto-generated method stub

			}
		});

	}
}
