package com.amapv2.cn.apis;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.amap.api.maps.AMap;
import com.amap.api.maps.SupportMapFragment;
import com.amapv2.cn.apis.util.AMapUtil;

/**
 * AMapV2地图中简单介绍矢量地图和卫星地图模式切换
 */
public class LayersDemoActivity extends FragmentActivity implements
		OnItemSelectedListener {
	private AMap aMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layers_demo);
		init();
	}

	/**
	 * 初始化AMap对象
	 */
	private void init() {
		if (aMap == null) {
			aMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();
		}
		Spinner spinner = (Spinner) findViewById(R.id.layers_spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.layers_array,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);
	}

	/**
	 * 对选择是否显示交通状况事件的响应
	 */
	public void onTrafficToggled(View view) {
		if (AMapUtil.checkReady(this, aMap)) {
			aMap.setTrafficEnabled(((CheckBox) view).isChecked());
		}

	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		if (AMapUtil.checkReady(this, aMap)) {
			setLayer((String) parent.getItemAtPosition(position));
		}

	}

	/**
	 * 选择矢量地图和卫星地图事件的响应
	 */
	private void setLayer(String layerName) {
		if (layerName.equals(getString(R.string.normal))) {
			aMap.setMapType(AMap.MAP_TYPE_NORMAL);
		} else if (layerName.equals(getString(R.string.satellite))) {
			aMap.setMapType(AMap.MAP_TYPE_SATELLITE);
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
	}
}
