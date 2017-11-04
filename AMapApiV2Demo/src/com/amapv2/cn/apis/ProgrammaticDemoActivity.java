package com.amapv2.cn.apis;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.SupportMapFragment;
import com.amap.api.maps.model.CameraPosition;
import com.amapv2.cn.apis.util.AMapUtil;
import com.amapv2.cn.apis.util.Constants;

/**
 * 通过Java代码添加一个AMap对象
 */
public class ProgrammaticDemoActivity extends FragmentActivity {

	private static final String MAP_FRAGMENT_TAG = "map";
	static final CameraPosition LUJIAZUI = new CameraPosition.Builder()
			.target(Constants.SHANGHAI).zoom(18).bearing(0).tilt(70).build();
	private AMap aMap;
	private SupportMapFragment mMapFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	@Override
	protected void onResume() {
		super.onResume();
		initMap();
	}

	/**
	 * 初始化AMap对象
	 */
	private void init() {
		AMapOptions aOptions = new AMapOptions();
		aOptions.zoomGesturesEnabled(false);
		aOptions.camera(LUJIAZUI);
		mMapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentByTag(MAP_FRAGMENT_TAG);

		if (mMapFragment == null) {
			mMapFragment = SupportMapFragment.newInstance(aOptions);
			FragmentTransaction fragmentTransaction = getSupportFragmentManager()
					.beginTransaction();
			fragmentTransaction.add(android.R.id.content, mMapFragment,
					MAP_FRAGMENT_TAG);
			fragmentTransaction.commit();
		}

	}

	private void initMap() {
		if (aMap == null) {
			aMap = mMapFragment.getMap();
			if (AMapUtil.checkReady(this, aMap)) {
				// amap对象初始化成功
			}
		}
	}
}
