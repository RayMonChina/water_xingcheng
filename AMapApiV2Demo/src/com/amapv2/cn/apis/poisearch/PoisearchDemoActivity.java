package com.amapv2.cn.apis.poisearch;

import java.util.List;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.SearchRecentSuggestions;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.InfoWindowAdapter;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.SupportMapFragment;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.search.core.AMapException;
import com.amap.api.search.poisearch.PoiItem;
import com.amap.api.search.poisearch.PoiPagedResult;
import com.amap.api.search.poisearch.PoiSearch;
import com.amap.api.search.poisearch.PoiTypeDef;
import com.amapv2.cn.apis.R;
import com.amapv2.cn.apis.util.AMapUtil;
import com.amapv2.cn.apis.util.Constants;
import com.amapv2.cn.apis.util.ToastUtil;

/**
 * poisearch搜索介绍
 */
public class PoisearchDemoActivity extends FragmentActivity implements
		OnMarkerClickListener, InfoWindowAdapter {
	private AMap aMap;
	private TextView searchTextView;
	private String query = null;
	private PoiPagedResult result;
	private ProgressDialog progDialog = null;
	private Button btn;
	private int curpage = 1;
	private int cnt = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.poisearch_demo);
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
		aMap.setOnMarkerClickListener(this);
		aMap.setInfoWindowAdapter(this);
		searchTextView = (TextView) findViewById(R.id.TextViewSearch);
		searchTextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onSearchRequested();
			}

		});

		btn = (Button) findViewById(R.id.next);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (cnt > curpage) {
					handler.sendMessage(Message.obtain(handler,
							Constants.POISEARCH_NEXT));
				} else {
					ToastUtil.show(PoisearchDemoActivity.this, "没有搜索到数据");
				}
			}

		});
	}

	@Override
	protected void onNewIntent(final Intent newIntent) {
		super.onNewIntent(newIntent);
		String ac = newIntent.getAction();
		if (Intent.ACTION_SEARCH.equals(ac)) {
			doSearchQuery(newIntent);
		}
	}

	/**
	 * 显示进度框
	 */
	private void showProgressDialog() {
		if (progDialog == null)
			progDialog = new ProgressDialog(this);
		progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progDialog.setIndeterminate(false);
		progDialog.setCancelable(true);
		progDialog.setMessage("正在搜索:\n" + query);
		progDialog.show();
	}

	/**
	 * 隐藏进度框
	 */
	private void dissmissProgressDialog() {
		if (progDialog != null) {
			progDialog.dismiss();
		}
	}

	protected void doSearchQuery(Intent intent) {
		query = intent.getStringExtra(SearchManager.QUERY);
		SearchRecentSuggestions suggestions = new SearchRecentSuggestions(
				PoisearchDemoActivity.this, MySuggestionProvider.AUTHORITY,
				MySuggestionProvider.MODE);
		suggestions.saveRecentQuery(query, null);
		curpage = 1;
		cnt = 0;
		showProgressDialog();// 显示进度框
		new Thread(new Runnable() {
			public void run() {
				try {
					PoiSearch poiSearch = new PoiSearch(
							PoisearchDemoActivity.this, new PoiSearch.Query(
									query, PoiTypeDef.All, "")); // 设置搜索字符串，poi搜索类型，poi搜索区域（空字符串代表全国）
					poiSearch.setPageSize(10);// 设置搜索每次最多返回结果数
					result = poiSearch.searchPOI();
					if (result != null) {
						cnt = result.getPageCount();
					}
					handler.sendMessage(Message.obtain(handler,
							Constants.POISEARCH));
				} catch (AMapException e) {
					handler.sendMessage(Message
							.obtain(handler, Constants.ERROR));
					e.printStackTrace();
				}
			}
		}).start();

	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		marker.showInfoWindow();
		return false;
	}

	@Override
	public View getInfoContents(Marker arg0) {
		return null;
	}

	@Override
	public View getInfoWindow(Marker arg0) {
		return null;
	}

	/**
	 * 一次性打印多个Marker出来
	 */
	private void addMarkers(List<PoiItem> poiItems) {
		for (int i = 0; i < poiItems.size(); i++) {
			aMap.addMarker(new MarkerOptions()
					.position(
							new LatLng(
									poiItems.get(i).getPoint().getLatitude(),
									poiItems.get(i).getPoint().getLongitude()))
					.title(poiItems.get(i).getTitle())
					.snippet(poiItems.get(i).getSnippet())
					.icon(BitmapDescriptorFactory.defaultMarker()));
		}

	}

	private LatLngBounds getLatLngBounds(List<PoiItem> poiItems) {
		LatLngBounds.Builder b = LatLngBounds.builder();
		for (int i = 0; i < poiItems.size(); i++) {
			b.include(new LatLng(poiItems.get(i).getPoint().getLatitude(),
					poiItems.get(i).getPoint().getLongitude()));
		}
		return b.build();
	}

	private void showPoiItem(List<PoiItem> poiItems) {
		if (poiItems != null && poiItems.size() > 0) {
			if (aMap == null)
				return;
			aMap.clear();
			LatLngBounds bounds = getLatLngBounds(poiItems);
			aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 5));
			addMarkers(poiItems);
		} else {
			ToastUtil.show(getApplicationContext(), "没有搜索到数据！");
		}
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == Constants.POISEARCH) {
				dissmissProgressDialog();// 隐藏对话框

				if (result != null) {
					new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								final List<PoiItem> poiItems = result
										.getPage(1);
								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										showPoiItem(poiItems);// 每页显示10个poiitem
									}
								});

							} catch (AMapException e) {
								e.printStackTrace();

							}
						}
					}).start();
				}

			} else if (msg.what == Constants.ERROR) {
				dissmissProgressDialog();// 隐藏对话框
				ToastUtil.show(getApplicationContext(), "搜索失败,请检查网络连接！");
			} else if (msg.what == Constants.POISEARCH_NEXT) {
				curpage++;
				new Thread(new Runnable() {

					@Override
					public void run() {
						final List<PoiItem> poiItems;
						try {
							poiItems = result.getPage(curpage);
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									showPoiItem(poiItems);// 每页显示10个poiitem
								}
							});
						} catch (AMapException e) {
							e.printStackTrace();
						}
					}
				}).start();
			}
		}
	};
}
