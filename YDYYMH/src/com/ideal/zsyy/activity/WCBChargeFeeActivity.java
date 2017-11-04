package com.ideal.zsyy.activity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ideal.zsyy.db.WdbManager;
import com.ideal.zsyy.entity.LocationInfo;
import com.ideal.zsyy.entity.WUserItem;
import com.jijiang.wtapp.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class WCBChargeFeeActivity extends Activity {

	Button btn_back,btn_save;
	private TextView tv_title,tv_w_userno,tv_w_bbh,tv_w_username,tv_w_dh,tv_w_dz,
	tv_w_sybs,tv_w_sysl,tv_w_sysf,tv_w_jglx,tv_w_bgh,tv_w_cbbz,tv_w_ycje,tv_w_ljqf;
	private EditText et_money;
	private WUserItem userItem;
	private String userNo="";
	private WdbManager dbManager=null;
	private SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.w_cb_getcharge);
		userNo=getIntent().getStringExtra("UserNo");
		dbManager=new WdbManager(getApplicationContext());
		initView();
		this.initData();
	}

	private void initView()
	{
		tv_title=(TextView)findViewById(R.id.tv_title);
		tv_w_userno=(TextView)findViewById(R.id.tv_w_userno);
		tv_w_bbh=(TextView)findViewById(R.id.tv_w_bbh);
		tv_w_username=(TextView)findViewById(R.id.tv_w_username);
		tv_w_dh=(TextView)findViewById(R.id.tv_w_dh);
		tv_w_dz=(TextView)findViewById(R.id.tv_w_dz);
		tv_w_sybs=(TextView)findViewById(R.id.tv_w_sybs);
		tv_w_sysl=(TextView)findViewById(R.id.tv_w_sysl);
		tv_w_sysf=(TextView)findViewById(R.id.tv_w_sysf);
		tv_w_jglx=(TextView)findViewById(R.id.tv_w_jglx);
		tv_w_bgh=(TextView)findViewById(R.id.tv_w_bgh);
		tv_w_cbbz=(TextView)findViewById(R.id.tv_w_cbbz);
		tv_w_ycje=(TextView)findViewById(R.id.tv_w_ycje);
		tv_w_ljqf=(TextView)findViewById(R.id.tv_w_ljqf);
		et_money=(EditText)findViewById(R.id.et_money);
		btn_back=(Button)findViewById(R.id.btn_back);
		btn_save=(Button)findViewById(R.id.btn_save);
		if(btn_back!=null)
		{
			btn_back.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
				}
			});

			btn_save.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					shouFei();
				}
			});
		}
	}

	private void initData()
	{
		userItem=dbManager.GetUserItemByNo(userNo);
		if(userItem!=null)
		{
			tv_title.setText(userItem.getXm()+"("+userItem.getYhbm()+")");
			tv_w_bbh.setText(userItem.getBbh());
			tv_w_bgh.setText(userItem.getBgh());
			tv_w_cbbz.setText(userItem.getCbbz()==2?"已抄表":"未抄表");
			tv_w_dh.setText(userItem.getDh());
			tv_w_dz.setText(userItem.getDz());
			tv_w_jglx.setText(userItem.getJglxName());
			tv_w_ljqf.setText(String.valueOf(userItem.getLjqf()));
			tv_w_sybs.setText(String.valueOf(userItem.getSyzd()));
			tv_w_sysf.setText(String.valueOf(userItem.getSysf()));
			tv_w_sysl.setText(String.valueOf(userItem.getSysl()));
			tv_w_username.setText(userItem.getXm());
			tv_w_userno.setText(userItem.getYhbm());
			tv_w_ycje.setText(String.valueOf(userItem.getYcje()));
			et_money.setText(String.valueOf(userItem.getBysf()));
		}
	}

	private void shouFei()
	{
		double dbFee=0;
		String strFee="";
		ZsyyApplication application=(ZsyyApplication)getApplication();
		LocationInfo locationInfo=application.currPoint;
		strFee=et_money.getText().toString();
		if(strFee=="")
		{
			Toast.makeText(WCBChargeFeeActivity.this,"请输入费用",Toast.LENGTH_SHORT).show();
			return;
		}
		try {
			dbFee=Double.parseDouble(strFee);
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(WCBChargeFeeActivity.this,"请输入正确的费用",Toast.LENGTH_SHORT).show();
			return;
		}
		if(dbFee<=0)
		{
			Toast.makeText(WCBChargeFeeActivity.this,"费用金额需大于0",Toast.LENGTH_SHORT).show();
			return;
		}
		if(userItem==null)
		{
			userItem=new WUserItem();
		}
		userItem.setYhbm(userNo);
		userItem.setBysf(dbFee);
		userItem.setSfrq(simpleDateFormat.format(new Date()));
		if(locationInfo!=null)
		{
			userItem.setLatitude(locationInfo.getLatitude());
			userItem.setLongitude(locationInfo.getLontitude());
		}
		if(userItem.getLjqf()>0)
		{
			double dbljqf=userItem.getLjqf()-(dbFee+userItem.getYcje());
			if(dbljqf<0)
			{
				double dValue=Math.round((userItem.getYcje()+Math.abs(dbljqf))*1000);
				userItem.setLjqf(0);
				userItem.setYcje(dValue/1000);
			}
			else {
				userItem.setLjqf(Math.round(dbljqf*1000)/1000);
				userItem.setYcje(0);
			}
		}
		dbManager.ShouFei(userItem);
		Toast.makeText(WCBChargeFeeActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
		finish();
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
