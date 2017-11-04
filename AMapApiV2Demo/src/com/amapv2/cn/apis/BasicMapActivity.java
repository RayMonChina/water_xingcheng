package com.amapv2.cn.apis;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.OnCameraChangeListener;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.SupportMapFragment;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.VisibleRegion;
import com.amapv2.cn.apis.util.AMapUtil;
import com.amapv2.cn.apis.util.Constants;
import com.amapv2.cn.apis.util.ToastUtil;

/**
 * AMapV2地图简单显示，添加一个marker并且对marker响应点击事件
 */
public class BasicMapActivity extends FragmentActivity implements
		OnMarkerClickListener {
	private AMap aMap;
	private Marker defaultMarker;
	private static final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyyMMddHHmmss", Locale.CHINA);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.basic_demo);
		init();
	}

	/**
	 * 初始化AMap对象
	 */
	private void init() {
		if (aMap == null) {
			aMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();
			if (AMapUtil.checkReady(this, aMap)) {
				setUpMap();
			}
		}
	}

	private void setUpMap() {
		// 对地图添加一个marker
		defaultMarker = aMap.addMarker(new MarkerOptions()
				.position(Constants.FANGHENG).title("方恒").snippet("方恒国际中心大楼A座")
				.icon(BitmapDescriptorFactory.defaultMarker()));
		aMap.getUiSettings().setZoomControlsEnabled(true);// 设置系统默认缩放按钮可见
		aMap.setOnMarkerClickListener(this);// 对marker添加点击监听器
		aMap.setOnCameraChangeListener(new OnCameraChangeListener() { // 设置可视区域改变监听器

			@Override
			public void onCameraChange(CameraPosition cameraPosion) {
			}

			@Override
			public void onCameraChangeFinish(CameraPosition cameraPosion) {
				VisibleRegion visibleRegion = aMap.getProjection()
						.getVisibleRegion(); // 获取可视区域
				LatLngBounds latLngBounds = aMap.getProjection()
						.getVisibleRegion().latLngBounds;// 获取可视区域的Bounds
				boolean isContain = latLngBounds.contains(Constants.SHANGHAI);// 判断上海经纬度是否包括在北京区域
				Log.d("BasicMapActivity",
						"visible region: " + visibleRegion.toString()
								+ "LatLngBounds：" + latLngBounds.toString()
								+ "上海经纬度是否在北京范围内" + isContain);
			}

		});
	}

	/**
	 * 对marker标注点点击响应事件
	 */
	@Override
	public boolean onMarkerClick(Marker marker) {
		if (marker.equals(defaultMarker)) {
			ToastUtil.show(BasicMapActivity.this, marker.getSnippet());
		}
		return false;
	}

	/**
	 * 对地图进行截屏
	 */
	public void getMapScreenShot(View v) {

		aMap.getMapPrintScreen(new AMap.onMapPrintScreenListener() {

			@Override
			public void onMapPrint(Drawable r) {
				Bitmap bitmap = ((BitmapDrawable) r).getBitmap();
				try {
					FileOutputStream fos = new FileOutputStream(Environment
							.getExternalStorageDirectory()
							+ "/test_"
							+ sdf.format(new Date()) + ".png");
					boolean b = bitmap.compress(CompressFormat.PNG, 100, fos);
					try {
						fos.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
					try {
						fos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					if (b)
						Toast.makeText(BasicMapActivity.this, "截屏成功",
								Toast.LENGTH_SHORT).show();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}

		});

	}

	/**
	 * 1像素代表多少米
	 */
	public void getScale(View v) {
		float scale = aMap.getScalePerPixel();
		Toast.makeText(BasicMapActivity.this, "每像素代表" + scale + "米",
				Toast.LENGTH_SHORT).show();
	}
}
