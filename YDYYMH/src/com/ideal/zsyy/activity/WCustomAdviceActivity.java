package com.ideal.zsyy.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.ideal.zsyy.db.WdbManager;
import com.ideal.zsyy.entity.WCustomerAdviceInfo;
import com.ideal.zsyy.entity.WUserItem;
import com.ideal.zsyy.service.PreferencesService;
import com.jijiang.wtapp.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class WCustomAdviceActivity extends Activity {

	private Button btn_back,btn_save;
	private TextView tv_userno,tv_noteno,tv_fname,tv_dh,tv_address;
	private EditText et_advice;
	private String userNo="";
	private WUserItem userItem;
	private WdbManager dbManager;
	private WCustomerAdviceInfo adviceInfo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.w_fault_custom_advice);
		userNo=getIntent().getStringExtra("UserNo");
		dbManager=new WdbManager(getApplicationContext());
		userItem=dbManager.GetUserItemByNo(userNo);
		adviceInfo=dbManager.GetCustomerAdvice(userNo);
		initView();
		setEventListener();
	}
	private void initView()
	{
		btn_back=(Button)findViewById(R.id.btn_back);
		btn_save=(Button)findViewById(R.id.btn_save);
		tv_userno=(TextView)findViewById(R.id.tv_userno);
		tv_noteno=(TextView)findViewById(R.id.tv_noteno);
		tv_fname=(TextView)findViewById(R.id.tv_fname);
		tv_dh=(TextView)findViewById(R.id.tv_dh);
		tv_address=(TextView)findViewById(R.id.tv_address);
		et_advice=(EditText)findViewById(R.id.et_advice);
		
		if(userItem!=null)
		{
			tv_userno.setText(userItem.getYhbm());
			tv_noteno.setText(userItem.getBbh());
			tv_fname.setText(userItem.getXm());
			tv_dh.setText(userItem.getDh());
			tv_address.setText(userItem.getDz());
		}
		if(adviceInfo!=null)
		{
			et_advice.setText(adviceInfo.getAdvice());
			if(adviceInfo.getAlreadyUpload()==0&&adviceInfo.getAdvice()!=null&&adviceInfo.getAdvice().length()>0)
			{
				et_advice.setEnabled(false);
			}
			else {
				et_advice.setEnabled(true);
			}
		}
	}
	
	private void setEventListener()
	{
		if(btn_back!=null)
		{
			btn_back.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
				}
			});
		}
		
		if(btn_save!=null)
		{
			btn_save.setOnClickListener(new OnClickListener() {
				
				@SuppressLint("SimpleDateFormat")
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String strContent=et_advice.getText().toString().trim();
					if(strContent.length()==0)
					{
						Toast.makeText(WCustomAdviceActivity.this,"建议内容不能为空",Toast.LENGTH_SHORT).show();
						return;
					}
					if(adviceInfo==null)
					{
						adviceInfo=new WCustomerAdviceInfo();
					}
					Map<String, Object>userInfo=new PreferencesService(WCustomAdviceActivity.this).getLoginInfo();
					adviceInfo.setAddDate((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()));
					adviceInfo.setAddUser(userInfo.get("userName").toString());
					adviceInfo.setAddUserId(userInfo.get("use_id").toString());
					adviceInfo.setAdvice(strContent);
					adviceInfo.setAlreadyUpload(1);
					adviceInfo.setUserNo(userNo);
					adviceInfo.setNoteNo(userItem.getBbh());
					adviceInfo.setBId(userItem.getBid());
					dbManager.AddCustomerAdvice(adviceInfo);
					Toast.makeText(WCustomAdviceActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
					finish();
				}
			});
		}
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(dbManager!=null)
		{
			dbManager.closeDB();
		}
	}

	
}
