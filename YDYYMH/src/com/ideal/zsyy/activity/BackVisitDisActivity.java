package com.ideal.zsyy.activity;

import com.jijiang.wtapp.R;
import com.ideal.zsyy.entity.BackVisitInfo;
import com.ideal.zsyy.request.BackVisitDetailReq;
import com.ideal.zsyy.response.BackVisitDetailRes;
import com.ideal2.base.gson.GsonServlet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BackVisitDisActivity extends Activity {

	private TextView tv_hf_name,tv_hf_ks,tv_hf_zyh,tv_hf_bq,tv_hf_ylzl,tv_hf_fwzl,tv_hf_hfqk;
	private Button btn_back;
	String hfId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.back_visit_info);
		init();
		getHfInfo();
	}
	
	private void init()
	{
		Intent intent=getIntent();
		hfId=intent.getStringExtra("id");
		tv_hf_name=(TextView)findViewById(R.id.hf_name);
		tv_hf_bq=(TextView)findViewById(R.id.hf_bq);
		tv_hf_fwzl=(TextView)findViewById(R.id.tv_fwqk);
		tv_hf_ks=(TextView)findViewById(R.id.hf_ks);
		tv_hf_ylzl=(TextView)findViewById(R.id.tv_ylzl);
		tv_hf_zyh=(TextView)findViewById(R.id.hf_zyh);
		btn_back=(Button)findViewById(R.id.btn_back);
		tv_hf_hfqk=(TextView)findViewById(R.id.hf_hfqk);
		
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	
	// 显示回访信息
		private void getHfInfo() {
			BackVisitDetailReq yyreReq = new BackVisitDetailReq();
			yyreReq.setOperType("115");
			yyreReq.setId(hfId);
			GsonServlet<BackVisitDetailReq, BackVisitDetailRes> gsonServlet = new GsonServlet<BackVisitDetailReq, BackVisitDetailRes>(
					BackVisitDisActivity.this);
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
									tv_hf_bq.setText(bInfo.getDisArea());
									tv_hf_ks.setText(bInfo.getDept());
									tv_hf_name.setText(bInfo.getpName());
									tv_hf_zyh.setText(bInfo.getPatNo());
									tv_hf_fwzl.setText(bInfo.getServiceCon());
									tv_hf_ylzl.setText(bInfo.getMedicalQuality());
									tv_hf_hfqk.setText(bInfo.getRecureCon());
								}
							}
						}

						@Override
						public void onResponseEndErr(BackVisitDetailReq commonReq,
								BackVisitDetailRes commonRes, String errmsg,
								int responseCode) {
							// TODO Auto-generated method stub
							Toast.makeText(BackVisitDisActivity.this, errmsg,
									Toast.LENGTH_SHORT).show();
						}
					});
		}
	
	
}
