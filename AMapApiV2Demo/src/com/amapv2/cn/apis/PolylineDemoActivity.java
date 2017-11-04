package com.amapv2.cn.apis;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.SupportMapFragment;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amapv2.cn.apis.util.AMapUtil;
import com.amapv2.cn.apis.util.Constants;

/**
 * AMapV2地图中简单介绍一些Polyline的用法.
 */
public class PolylineDemoActivity extends FragmentActivity implements
		OnSeekBarChangeListener {
	private static final int WIDTH_MAX = 50;
	private static final int HUE_MAX = 360;
	private static final int ALPHA_MAX = 255;

	private AMap aMap;
	private Polyline mMutablePolyline;
	private SeekBar mColorBar;
	private SeekBar mAlphaBar;
	private SeekBar mWidthBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.polyline_demo);
		init();
	}

	/**
	 * 初始化AMap对象
	 */
	private void init() {
		mColorBar = (SeekBar) findViewById(R.id.hueSeekBar);
		mColorBar.setMax(HUE_MAX);
		mColorBar.setProgress(50);

		mAlphaBar = (SeekBar) findViewById(R.id.alphaSeekBar);
		mAlphaBar.setMax(ALPHA_MAX);
		mAlphaBar.setProgress(50);

		mWidthBar = (SeekBar) findViewById(R.id.widthSeekBar);
		mWidthBar.setMax(WIDTH_MAX);
		mWidthBar.setProgress(50);
		if (aMap == null) {
			aMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();
			if (AMapUtil.checkReady(this, aMap)) {
				setUpMap();
			}
		}
	}

	private void setUpMap() {
		// 缩放当前可见地图区域
		aMap.moveCamera(CameraUpdateFactory
				.newLatLngZoom(Constants.BEIJING, 12));
		// 绘制一条折线
		aMap.addPolyline((new PolylineOptions())
				.add(new LatLng(39.9588, 116.3181), Constants.BEIJING,
						new LatLng(39.9588, 116.5181)).color(Color.RED)
				.width(5));
		// 绘制一个三角形
		mMutablePolyline = aMap.addPolyline((new PolylineOptions())
				.add(new LatLng(39.8588, 116.3181), Constants.BEIJING,
						new LatLng(39.8588, 116.5181),
						new LatLng(39.8588, 116.3181)).width(5)
				.color(Color.BLUE));
		mColorBar.setOnSeekBarChangeListener(this);
		mAlphaBar.setOnSeekBarChangeListener(this);
		mWidthBar.setOnSeekBarChangeListener(this);
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	/**
	 * Polyline中对填充颜色，透明度，画笔宽度设置响应事件
	 */
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		if (mMutablePolyline == null) {
			return;
		}
		if (seekBar == mColorBar) {
			mMutablePolyline.setColor(Color.HSVToColor(
					Color.alpha(mMutablePolyline.getColor()), new float[] {
							progress, 1, 1 }));
		} else if (seekBar == mAlphaBar) {
			float[] prevHSV = new float[3];
			Color.colorToHSV(mMutablePolyline.getColor(), prevHSV);
			mMutablePolyline.setColor(Color.HSVToColor(progress, prevHSV));
		} else if (seekBar == mWidthBar) {
			mMutablePolyline.setWidth(progress);
		}
	}
}
