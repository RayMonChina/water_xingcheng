package com.ideal.zsyy.activity;

import com.jijiang.wtapp.R;
import com.ideal.zsyy.entity.BackVisitInfo;
import com.ideal.zsyy.request.BackVisitDetailReq;
import com.ideal.zsyy.request.BackVisitUpdateReq;
import com.ideal.zsyy.response.BackVisitDetailRes;
import com.ideal.zsyy.response.BackVisitUpdateRes;
import com.ideal2.base.gson.GsonServlet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class BackVisitEditActivity extends Activity {

	private TextView tv_name, tv_dept, tv_bq, tv_patNo;
	private EditText et_hfqk;// 恢复情况
	private RadioGroup rg_ylzl, rg_ylfw;
	private Button btn_back, btn_hf_button;
	private String strylzl, strfwqk, strhfqk;
	private String hfId;// 回访id

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.back_visit_edit);
		init();
		setListener();
		getHfInfo();
	}

	private void init() {
		Intent intent = getIntent();
		hfId = intent.getStringExtra("id");

		tv_name = (TextView) findViewById(R.id.hf_name);
		tv_bq = (TextView) findViewById(R.id.hf_bq);
		tv_dept = (TextView) findViewById(R.id.hf_ks);
		tv_patNo = (TextView) findViewById(R.id.hf_zyh);

		rg_ylzl = (RadioGroup) findViewById(R.id.rg_ylzl);
		rg_ylfw = (RadioGroup) findViewById(R.id.rg_fwzl);

		et_hfqk = (EditText) findViewById(R.id.hf_hfqk);
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_hf_button = (Button) findViewById(R.id.btn_hf_submit);
	}

	private void setListener() {
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		btn_hf_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getylzl();
				getfwqk();
				strhfqk=et_hfqk.getText().toString();
				if(strhfqk==null||"".equals(strhfqk.trim()))
				{
					Toast.makeText(BackVisitEditActivity.this, "恢复情况不能为空...", Toast.LENGTH_SHORT).show();
					return;
				}
				strhfqk=strhfqk.replace("'","");
				UpdateHfInfo(strhfqk,strylzl,strfwqk);
			}
		});
	}

	// 获取用户评价 ，医疗质量
	public void getylzl() {
		int childId = rg_ylzl.getCheckedRadioButtonId();
		switch (childId) {
		case R.id.rb_ylzl_bmy:
			strylzl = "不满意";
			break;
		case R.id.rb_ylzl_jbmy:
			strylzl = "基本满意";
			break;
		case R.id.rb_ylzl_my:
			strylzl = "满意";
			break;
		default:
			break;
		}
	}
	
	//服务情况
	public void getfwqk() {
		int childId = rg_ylfw.getCheckedRadioButtonId();
		switch (childId) {
		case R.id.rb_fwzl_bmy:
			strfwqk = "不满意";
			break;
		case R.id.rb_fwzl_jbmy:
			strfwqk = "基本满意";
			break;
		case R.id.rb_fwzl_my:
			strfwqk = "满意";
			break;
		default:
			break;
		}
	}

	// 显示回访信息
	private void getHfInfo() {
		BackVisitDetailReq yyreReq = new BackVisitDetailReq();
		yyreReq.setOperType("115");
		yyreReq.setId(hfId);
		GsonServlet<BackVisitDetailReq, BackVisitDetailRes> gsonServlet = new GsonServlet<BackVisitDetailReq, BackVisitDetailRes>(
				BackVisitEditActivity.this);
		gsonServlet.request(yyreReq, BackVisitDetailRes.class);
		gsonServlet
				.setOnResponseEndListening(new GsonServlet.OnResponseEndListening<BackVisitDetailReq, BackVisitDetailRes>() {

					@Override
					public void onResponseEnd(BackVisitDetailReq commonReq,
							BackVisitDetailRes commonRes, boolean result,
							String errmsg, int responseCode) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onResponseEndSuccess(
							BackVisitDetailReq commonReq,
							BackVisitDetailRes commonRes, String errmsg,
							int responseCode) {
						// TODO Auto-generated method stub
						if (commonRes != null) {
							BackVisitInfo bInfo = commonRes.getbVisitInfo();
							if (bInfo != null) {
								tv_bq.setText(bInfo.getDisArea());
								tv_dept.setText(bInfo.getDept());
								tv_name.setText(bInfo.getpName());
								tv_patNo.setText(bInfo.getPatNo());
							}
						}
					}

					@Override
					public void onResponseEndErr(BackVisitDetailReq commonReq,
							BackVisitDetailRes commonRes, String errmsg,
							int responseCode) {
						// TODO Auto-generated method stub
						Toast.makeText(BackVisitEditActivity.this, errmsg,
								Toast.LENGTH_SHORT).show();
					}
				});
	}

	// 更新回访信息
	private void UpdateHfInfo(String hfcon, String hfylzl, String hffwzl) {
		BackVisitUpdateReq yyreReq = new BackVisitUpdateReq();
		yyreReq.setOperType("117");
		yyreReq.setId(hfId);
		yyreReq.setMedicalQuaility(hfylzl);
		yyreReq.setRecureCon(hfcon);
		yyreReq.setServeCon(hffwzl);
		GsonServlet<BackVisitUpdateReq, BackVisitUpdateRes> gsonServlet = new GsonServlet<BackVisitUpdateReq, BackVisitUpdateRes>(
				BackVisitEditActivity.this);
		gsonServlet.request(yyreReq, BackVisitUpdateRes.class);
		gsonServlet
				.setOnResponseEndListening(new GsonServlet.OnResponseEndListening<BackVisitUpdateReq, BackVisitUpdateRes>() {

					@Override
					public void onResponseEnd(BackVisitUpdateReq commonReq,
							BackVisitUpdateRes commonRes, boolean result,
							String errmsg, int responseCode) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onResponseEndSuccess(
							BackVisitUpdateReq commonReq,
							BackVisitUpdateRes commonRes, String errmsg,
							int responseCode) {
						// TODO Auto-generated method stub
						if (commonRes != null) {
							finish();
						}
					}

					@Override
					public void onResponseEndErr(BackVisitUpdateReq commonReq,
							BackVisitUpdateRes commonRes, String errmsg,
							int responseCode) {
						// TODO Auto-generated method stub
						Toast.makeText(BackVisitEditActivity.this, errmsg,
								Toast.LENGTH_SHORT).show();
					}
				});
	}
}
