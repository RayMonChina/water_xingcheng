package com.ideal.zsyy.activity;

import com.jijiang.wtapp.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class WNFCTagActivity extends Activity {

	private Button btn_back;
	private NfcAdapter nfcAdapter;
	private PendingIntent pendingIntent;
	private IntentFilter[] mFilters;
	private String[][] mTechLists;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.w_cb_nfc);
		nfcAdapter = NfcAdapter.getDefaultAdapter(this);
		pendingIntent = PendingIntent.getActivity(this, 0,new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
		IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
		ndef.addCategory("*/*");
		mFilters = new IntentFilter[] { ndef };// 过滤器
		mTechLists = new String[][] {
				new String[] { MifareClassic.class.getName() },
				new String[] { NfcA.class.getName() },new String[] { NfcB.class.getName() },new String[] { NfcF.class.getName() } };// 允许扫描的标签类型
		onNewIntent(getIntent());
		this.initView();
		this.refreshStatus();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (nfcAdapter != null)
			nfcAdapter.enableForegroundDispatch(this, pendingIntent, mFilters, mTechLists);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
			String result = processIntent(intent);
			Log.i("NFC Content",result);
		}
	}

	private String processIntent(Intent intent) {
		Parcelable[] rawmsgs = intent
				.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
		NdefMessage msg = (NdefMessage) rawmsgs[0];
		NdefRecord[] records = msg.getRecords();
		String resultStr = new String(records[0].getPayload());
		return resultStr;
	}
	
	private void initView()
	{
		btn_back=(Button)findViewById(R.id.btn_back);
		if(btn_back!=null)
		{
			btn_back.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
				}
			});
		}
	}

	private void refreshStatus()
	{
		Log.d("NFC","refreshStatus");
		if (nfcAdapter == null)
		{
			Toast.makeText(WNFCTagActivity.this,"手机没有NFC功能",Toast.LENGTH_SHORT).show();
			finish();
		}
		else if(!nfcAdapter.isEnabled())
		{
			Toast.makeText(WNFCTagActivity.this,"请启用NFC功能",Toast.LENGTH_SHORT).show();
			startActivityForResult(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS), 0);
			//finish();
		}
			
	}
}
