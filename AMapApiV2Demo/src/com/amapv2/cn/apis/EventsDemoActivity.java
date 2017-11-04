package com.amapv2.cn.apis;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.OnCameraChangeListener;
import com.amap.api.maps.AMap.OnMapClickListener;
import com.amap.api.maps.AMap.OnMapLongClickListener;
import com.amap.api.maps.SupportMapFragment;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amapv2.cn.apis.util.AMapUtil;

/**
 * AMapV2地图中简单介绍OnMapClickListener, OnMapLongClickListener,
 * OnCameraChangeListener三种监听器用法
 */

public class EventsDemoActivity extends FragmentActivity implements
		OnMapClickListener, OnMapLongClickListener, OnCameraChangeListener {
	private AMap aMap;
	private TextView mTapTextView;
	private TextView mCameraTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.events_demo);
		init();
	}

	/**
	 * 初始化AMap对象
	 */
	private void init() {
		mTapTextView = (TextView) findViewById(R.id.tap_text);
		mCameraTextView = (TextView) findViewById(R.id.camera_text);
		if (aMap == null) {
			aMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();
			if (AMapUtil.checkReady(this, aMap)) {
				setUpMap();
			}
		}
	}

	private void setUpMap() {
		aMap.setOnMapClickListener(this);// 对amap添加单击地图事件监听器
		aMap.setOnMapLongClickListener(this);// 对amap添加长按地图事件监听器
		aMap.setOnCameraChangeListener(this);// 对amap添加移动地图事件监听器
	}

	/**
	 * 对点击地图事件响应
	 */
	@Override
	public void onMapClick(LatLng point) {
		mTapTextView.setText("tapped, point=" + point);
	}

	/**
	 * 对长按地图事件响应
	 */
	@Override
	public void onMapLongClick(LatLng point) {
		mTapTextView.setText("long pressed, point=" + point);
	}

	/**
	 * 对移动地图事件响应
	 */
	@Override
	public void onCameraChange(final CameraPosition position) {
		mCameraTextView.setText("onCameraChange:" + position.toString());
	}

	@Override
	public void onCameraChangeFinish(CameraPosition cameraPosition) {
		mCameraTextView.setText("onCameraChangeFinish:"
				+ cameraPosition.toString());
	}
}
