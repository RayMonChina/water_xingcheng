package com.ideal.zsyy.activity;

import com.jijiang.wtapp.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CheckRangeActivity extends Activity {

	private String curr_no,deptName;
	private TextView tv_curr_no,tv_deptName;
	private Button btn_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.check_rang_tip);
		
		tv_curr_no=(TextView)findViewById(R.id.tv_current_no);
		tv_deptName=(TextView)findViewById(R.id.tv_dept);
		btn_back=(Button)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		Intent intent=getIntent();
		curr_no=intent.getStringExtra("curr_no");
		deptName=intent.getStringExtra("deptname");
		
		tv_curr_no.setText(curr_no);
		tv_deptName.setText(deptName);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}

	
}
