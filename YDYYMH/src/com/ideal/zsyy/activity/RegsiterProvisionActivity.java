package com.ideal.zsyy.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.jijiang.wtapp.R;

public class RegsiterProvisionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.regsiter_provision);
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
		Button btn_confim = (Button) findViewById(R.id.btn_confim);
		btn_confim.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(RegsiterProvisionActivity.this,RegisterActivity.class);
				startActivity(intent); 
				finish();  
			}
		});
		
	}
}
