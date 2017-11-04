package com.amapv2.cn.apis;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amapv2.cn.apis.util.AMapUtil;

public class LocationGPSDemoActivity extends Activity implements
		AMapLocationListener {
	private LocationManagerProxy locationManager;
	private TextView myLocation;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			myLocation.setText((String) msg.obj);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location);
		init();
	}

	private void init() {
		myLocation = (TextView) findViewById(R.id.myLocation);
		locationManager = LocationManagerProxy.getInstance(this);
		// Location API定位采用GPS定位方式，时间最短是5000毫秒
		locationManager.requestLocationUpdates(
				LocationManagerProxy.GPS_PROVIDER, 5000, 10, this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (locationManager != null) {
			locationManager.removeUpdates(this);
		}
	}

	@Override
	protected void onDestroy() {
		if (locationManager != null) {
			locationManager.removeUpdates(this);
			locationManager.destory();
		}
		locationManager = null;
		super.onDestroy();
	}

	/**
	 * 此方法已经废弃
	 */
	@Override
	public void onLocationChanged(Location location) {

	}

	@Override
	public void onProviderDisabled(String provider) {

	}

	@Override
	public void onProviderEnabled(String provider) {

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

	/**
	 * 过滤gps定位返回的数据
	 */
	@Override
	public void onLocationChanged(AMapLocation location) {
		if (location != null) {
			Double geoLat = location.getLatitude();
			Double geoLng = location.getLongitude();
			String str = ("定位成功:(" + geoLng + "," + geoLat + ")"
					+ "\n精    度    :" + location.getAccuracy() + "米"
					+ "\n定位方式:" + location.getProvider() + "\n定位时间:" + AMapUtil
					.convertToTime(location.getTime()));
			Message msg = new Message();
			msg.obj = str;
			if (handler != null) {
				handler.sendMessage(msg);
			}
		}
	}
}
