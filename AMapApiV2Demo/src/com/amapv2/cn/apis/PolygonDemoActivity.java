package com.amapv2.cn.apis;

import java.util.Arrays;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.SupportMapFragment;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Polygon;
import com.amap.api.maps.model.PolygonOptions;
import com.amapv2.cn.apis.util.AMapUtil;
import com.amapv2.cn.apis.util.Constants;

/**
 * AMapV2地图中简单介绍一些Polygon的用法.
 */
public class PolygonDemoActivity extends FragmentActivity implements
		OnSeekBarChangeListener {
	private static final int WIDTH_MAX = 50;
	private static final int HUE_MAX = 360;
	private static final int ALPHA_MAX = 255;
	private AMap aMap;
	private Polygon ovalPolygon;
	private SeekBar mColorBar;
	private SeekBar mAlphaBar;
	private SeekBar mWidthBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.polygon_demo);
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
		mWidthBar.setProgress(4);
		if (aMap == null) {
			aMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();
			if (AMapUtil.checkReady(this, aMap)) {
				setUpMap();
			}
		}
	}

	private void setUpMap() {
		// 绘制一个圆形
		aMap.addCircle(new CircleOptions().center(Constants.CHENGDU)
				.radius(400000).strokeColor(Color.BLUE).fillColor(Color.WHITE)
				.strokeWidth(3));
		// 绘制一个长方形
		aMap.addPolygon(new PolygonOptions()
				.addAll(createRectangle(Constants.SHANGHAI, 2, 2))
				.fillColor(Color.LTGRAY).strokeColor(Color.RED).strokeWidth(1));
		PolygonOptions options = new PolygonOptions();
		int numPoints = 400;
		float semiHorizontalAxis = 10f;
		float semiVerticalAxis = 5f;
		double phase = 2 * Math.PI / numPoints;
		for (int i = 0; i <= numPoints; i++) {
			options.add(new LatLng(Constants.BEIJING.latitude
					+ semiVerticalAxis * Math.sin(i * phase),
					Constants.BEIJING.longitude + semiHorizontalAxis
							* Math.cos(i * phase)));
		}

		int fillColor = Color.HSVToColor(mAlphaBar.getProgress(), new float[] {
				mColorBar.getProgress(), 1, 1 });
		// 绘制一个椭圆
		ovalPolygon = aMap.addPolygon(options
				.strokeWidth(mWidthBar.getProgress()).strokeColor(Color.BLACK)
				.fillColor(fillColor));

		mColorBar.setOnSeekBarChangeListener(this);
		mAlphaBar.setOnSeekBarChangeListener(this);
		mWidthBar.setOnSeekBarChangeListener(this);
		aMap.moveCamera(CameraUpdateFactory.newLatLng(Constants.BEIJING));
	}

	private List<LatLng> createRectangle(LatLng center, double halfWidth,
			double halfHeight) {
		return Arrays.asList(new LatLng(center.latitude - halfHeight,
				center.longitude - halfWidth), new LatLng(center.latitude
				- halfHeight, center.longitude + halfWidth), new LatLng(
				center.latitude + halfHeight, center.longitude + halfWidth),
				new LatLng(center.latitude + halfHeight, center.longitude
						- halfWidth));
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	/**
	 * Polygon中对填充颜色，透明度，画笔宽度设置响应事件
	 */
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		if (ovalPolygon == null) {
			return;
		}
		if (seekBar == mColorBar) {
			ovalPolygon.setFillColor(Color.HSVToColor(
					Color.alpha(ovalPolygon.getFillColor()), new float[] {
							progress, 1, 1 }));
		} else if (seekBar == mAlphaBar) {
			int prevColor = ovalPolygon.getFillColor();
			ovalPolygon.setFillColor(Color.argb(progress, Color.red(prevColor),
					Color.green(prevColor), Color.blue(prevColor)));
		} else if (seekBar == mWidthBar) {
			ovalPolygon.setStrokeWidth(progress);
		}
	}
}
