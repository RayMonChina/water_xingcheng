package com.amapv2.cn.apis;

import java.util.List;

import com.amap.api.maps.AMap;
import com.amap.api.maps.SupportMapFragment;
import com.amap.api.search.core.AMapException;
import com.amap.api.search.geocoder.Geocoder;
import com.amapv2.cn.apis.util.Constants;

import android.app.ProgressDialog;
import android.location.Address;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * 用给定的坐标数据实现逆地理编码，并将得到的地名用Toast打印在地图上
 */
public class GeocoderDemoActivity extends FragmentActivity {
	private Button btn;
	private Button resBtn;
	private ProgressDialog progDialog = null;
	private Geocoder coder;
	private double mLat = 39.982402;
	private double mLon = 116.305304;
	private String addressName;
	private AMap aMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.geocoder);
		btn = (Button) this.findViewById(R.id.geobtn);
		init();
		coder = new Geocoder(this);
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getLatlon("王府井");
			}
		});
		resBtn = (Button) this.findViewById(R.id.resgeobtn);
		resBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getAddress(mLat, mLon);
			}

		});
		progDialog = new ProgressDialog(this);
	}

	/**
	 * 初始化AMap对象
	 */
	private void init() {
		if (aMap == null) {
			aMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();
		}
	}

	// 地理编码
	public void getLatlon(final String name) {
		Thread t = new Thread(new Runnable() {
			public void run() {
				try {
					List<Address> address = coder.getFromLocationName(name, 3);
					if (address != null && address.size() > 0) {
						Address addres = address.get(0);
						addressName = addres.getLatitude() + ","
								+ addres.getLongitude();
						handler.sendMessage(Message.obtain(handler,
								Constants.REOCODER_RESULT));

					}
				} catch (AMapException e) {
					handler.sendMessage(Message
							.obtain(handler, Constants.ERROR));
				}

			}
		});

		progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progDialog.setIndeterminate(false);
		progDialog.setCancelable(true);
		progDialog.setMessage("正在获取地址");
		progDialog.show();
		t.start();
	}

	// 逆地理编码
	public void getAddress(final double mlat, final double mLon) {
		Thread t = new Thread(new Runnable() {
			public void run() {

				try {
					List<List<Address>> lists = coder.getFromLocation(mlat,
							mLon, 3, 3, 3, 500);
					List<Address> address = lists.get(0);// 0代表poi搜索结果，1代表rode搜索结果，2代表cross搜索结果
					if (address != null && address.size() > 0) {
						Address addres = address.get(0);
						addressName = addres.getAdminArea()
								+ addres.getSubLocality()
								+ addres.getFeatureName() + "附近";
						handler.sendMessage(Message.obtain(handler,
								Constants.REOCODER_RESULT));

					}
				} catch (AMapException e) {
					handler.sendMessage(Message
							.obtain(handler, Constants.ERROR));
				}

			}
		});

		progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progDialog.setIndeterminate(false);
		progDialog.setCancelable(true);
		progDialog.setMessage("正在获取地址");
		progDialog.show();
		t.start();
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == Constants.REOCODER_RESULT) {
				progDialog.dismiss();
				Toast.makeText(getApplicationContext(), addressName,
						Toast.LENGTH_SHORT).show();
			} else if (msg.what == Constants.ERROR) {
				progDialog.dismiss();
				Toast.makeText(getApplicationContext(), "请检查网络连接是否正确?",
						Toast.LENGTH_SHORT).show();
			}
		}
	};
}