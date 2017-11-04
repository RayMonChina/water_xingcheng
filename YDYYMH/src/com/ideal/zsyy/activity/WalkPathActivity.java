package com.ideal.zsyy.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.model.LatLng;
import com.jijiang.wtapp.R;
import com.ideal.zsyy.db.WdbManager;
import com.ideal.zsyy.entity.LocationInfo;
import com.ideal.zsyy.entity.WPointItem;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class WalkPathActivity extends Activity {

	// 定位相关
	// LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	private LocationMode mCurrentMode;
	BitmapDescriptor mCurrentMarker;
	Polyline mPolyline;
	private WdbManager dbManager;
	Button btn_back;
	MapView mMapView;
	BaiduMap mBaiduMap;
	boolean isFirstLoc = true;// 是否首次定位

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.w_walk_path);
		dbManager = new WdbManager(this);
		mCurrentMarker = null;
		mCurrentMode = LocationMode.NORMAL;

		try {

			// 地图初始化
			mMapView = (MapView) findViewById(R.id.bmapView);
			mBaiduMap = mMapView.getMap();
			mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true, null));
			// 开启定位图层
			mBaiduMap.setMyLocationEnabled(true);
			// 定位初始化
			// mLocClient = new LocationClient(this);
			// mLocClient.registerLocationListener(myListener);
			// LocationClientOption option = new LocationClientOption();
			// option.setOpenGps(true);// 打开gps
			// option.setCoorType("bd09ll"); // 设置坐标类型
			// option.setScanSpan(1000);
			// mLocClient.setLocOption(option);
			// mLocClient.start();
		} catch (Exception e) {

		}
		btn_back = (Button) findViewById(R.id.btn_back);
		if (btn_back != null) {
			btn_back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					finish();
				}
			});
		}
	}

	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null || mMapView == null) {
				return;
			}
			MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(location.getLatitude()).longitude(location.getLongitude()).build();
			mBaiduMap.setMyLocationData(locData);
			if (isFirstLoc) {
				isFirstLoc = false;
				LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				mBaiduMap.animateMapStatus(u);
			}
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}

	private void drawPath() {
		List<WPointItem> pointItems = dbManager.GetDisWalkPoints();
		LocationInfo currPoint = ((ZsyyApplication) getApplication()).currPoint;
		LatLng latLng = new LatLng(currPoint.getLatitude(), currPoint.getLontitude());
		MapStatus mMapStatus = new MapStatus.Builder().target(latLng).zoom(15).build();
		MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
		mBaiduMap.animateMapStatus(mMapStatusUpdate);
		List<LatLng> list = new ArrayList<LatLng>();
		for (int i = 0; i < pointItems.size(); i++) {
			double lau = pointItems.get(i).getLatitude();
			double lag = pointItems.get(i).getLongitude();
			LatLng p1 = new LatLng(lau, lag);
			list.add(p1);
		}
		if (list.size() < 1) {
			return;
		}
		OverlayOptions ooPolyline = new PolylineOptions().width(10).color(0xAAFF0000).points(list);
		mPolyline = (Polyline) mBaiduMap.addOverlay(ooPolyline);
	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		super.onResume();
		drawPath();
	}

	@Override
	protected void onDestroy() {
		// 退出时销毁定位
		// if(mLocClient!=null)
		// {
		// mLocClient.stop();
		// }
		// 关闭定位图层
		mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mMapView = null;
		super.onDestroy();
	}

}
