package com.amapv2.cn.apis;

import android.app.Activity;
import android.os.Bundle;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.MarkerOptions;
import com.amapv2.cn.apis.util.AMapUtil;
import com.amapv2.cn.apis.util.Constants;

public class MapViewDemoActivity extends Activity {
	private MapView aMapView;
	private AMap aMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapview_demo);
		aMapView = (MapView) findViewById(R.id.map);
		aMapView.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();
		aMapView.onResume();
		setUpMapIfNeeded();
	}

	private void setUpMapIfNeeded() {
		if (aMap == null) {
			aMap = aMapView.getMap();
			if (AMapUtil.checkReady(MapViewDemoActivity.this, aMap)) {
				setUpMap();
			}
		}
	}

	private void setUpMap() {
		aMap.addMarker(new MarkerOptions().position(Constants.BEIJING));
	}

	@Override
	protected void onPause() {
		super.onPause();
		aMapView.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		aMapView.onDestroy();
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		aMapView.onLowMemory();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		aMapView.onSaveInstanceState(outState);
	}
}
