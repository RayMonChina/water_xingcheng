package com.amapv2.cn.apis.offlinemap;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapsInitializer;
import com.amap.api.maps.SupportMapFragment;
import com.amap.api.offlinemap.MOfflineMapStatus;
import com.amap.api.offlinemap.OfflineMapManager;
import com.amap.api.offlinemap.OfflineMapManager.OfflineMapDownloadListener;
import com.amapv2.cn.apis.R;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class OfflineMapDemo extends FragmentActivity implements
		OfflineMapDownloadListener {
	private OfflineMapManager mOffline = null;
	private EditText mEditCityName;
	private AMap aMap;

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		//设置应用单独的地图存储目录，在下载离线地图或初始化地图时设置
		MapsInitializer.sdcardDir = getSdCacheDir(this);
		setContentView(R.layout.offlinemap);
		initAMap();
		mOffline = new OfflineMapManager(this, this);
		mEditCityName = (EditText) findViewById(R.id.city);
		Button btn = (Button) findViewById(R.id.start);
		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				boolean start = mOffline.downloadByCityName(mEditCityName.getText()
						.toString());
				if(!start){
					Toast.makeText(OfflineMapDemo.this, "开启下载失败，请检查网络是否开启！",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		btn = (Button) findViewById(R.id.stop);
		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mOffline.stop();
			}
		});

		btn = (Button) findViewById(R.id.del);
		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mOffline.remove(mEditCityName.getText().toString());
			}
		});

		btn = (Button) findViewById(R.id.pause);
		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mOffline.pause();
			}
		});

	}

	@Override
	public void onDownload(int status, int completeCode) {
		// TODO Auto-generated method stub
		switch (status) {
		case MOfflineMapStatus.LOADING:
			Toast.makeText(this, "正在下载," + "已完成：" + completeCode + "%",
					Toast.LENGTH_SHORT).show();
			break;
		case MOfflineMapStatus.PAUSE:
			Toast.makeText(this, "暂停",
					Toast.LENGTH_SHORT).show();
			break;
		case MOfflineMapStatus.STOP:
			Toast.makeText(this, "停止",
					Toast.LENGTH_SHORT).show();
			break;
		case MOfflineMapStatus.ERROR:
			Toast.makeText(this, "下载出错",
					Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}

	/**
	 * 初始化AMap对象
	 */
	private void initAMap() {
		if (aMap == null) {
			aMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();
		}
	}
	/**
	 * 获取map 缓存和读取目录
	 * @param context
	 * @return
	 */
	private  String getSdCacheDir(Context context) {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			java.io.File fExternalStorageDirectory = Environment
					.getExternalStorageDirectory();
			java.io.File autonaviDir = new java.io.File(
					fExternalStorageDirectory, "companyname");
			boolean result = false;
			if (!autonaviDir.exists()) {
				result = autonaviDir.mkdir();
			}
			java.io.File minimapDir = new java.io.File(autonaviDir,
					"objectname");
			if (!minimapDir.exists()) {
				result = minimapDir.mkdir();
			}
			return minimapDir.toString() + "/";
		} else {
			return null;
		}
	}
}
