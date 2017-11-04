package com.amapv2.cn.apis;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.SupportMapFragment;
import com.amapv2.cn.apis.util.AMapUtil;
import com.amapv2.cn.apis.util.Constants;

/**
 * 一个手机屏幕中一次性展示多个AMapV2地图.
 */
public class MultiMapDemoActivity extends FragmentActivity {
	private AMap aMap1, aMap2, aMap3, aMap4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.multimap_demo);
		init();
	}

	/**
	 * 初始化AMap对象
	 */
	private void init() {
		// 第一个地图的中心点显示郑州市
		if (aMap1 == null) {
			aMap1 = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map1)).getMap();

			if (AMapUtil.checkReady(this, aMap1)) {
				aMap1.moveCamera(CameraUpdateFactory.newLatLngZoom(
						Constants.ZHENGZHOU, 8));
			}
		}
		// 第二个地图的中心点显示北京市
		if (aMap2 == null) {
			aMap2 = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map2)).getMap();
			if (AMapUtil.checkReady(this, aMap2)) {
				aMap2.moveCamera(CameraUpdateFactory.newLatLngZoom(
						Constants.BEIJING, 8));
			}
		}
		// 第三个地图的中心点显示上海市
		if (aMap3 == null) {
			aMap3 = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map3)).getMap();
			if (AMapUtil.checkReady(this, aMap3)) {
				aMap3.moveCamera(CameraUpdateFactory.newLatLngZoom(
						Constants.SHANGHAI, 8));
			}
		}
		// 第四个地图的中心点显示西安市
		if (aMap4 == null) {
			aMap4 = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map4)).getMap();
			if (AMapUtil.checkReady(this, aMap4)) {
				aMap4.moveCamera(CameraUpdateFactory.newLatLngZoom(
						Constants.XIAN, 8));
			}
		}
	}
}
