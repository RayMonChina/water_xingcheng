package com.amapv2.cn.apis.route;

import java.util.List;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.InfoWindowAdapter;
import com.amap.api.maps.AMap.OnInfoWindowClickListener;
import com.amap.api.maps.AMap.OnMapClickListener;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.SupportMapFragment;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.search.core.AMapException;
import com.amap.api.search.core.Inputtips;
import com.amap.api.search.core.Inputtips.InputtipsListener;
import com.amap.api.search.core.LatLonPoint;
import com.amap.api.search.poisearch.PoiItem;
import com.amap.api.search.poisearch.PoiPagedResult;
import com.amap.api.search.poisearch.PoiSearch;
import com.amap.api.search.poisearch.PoiSearch.Query;
import com.amap.api.search.poisearch.PoiTypeDef;
import com.amap.api.search.route.Route;
import com.amapv2.cn.apis.R;
import com.amapv2.cn.apis.route.RouteSearchPoiDialog.OnListItemClick;
import com.amapv2.cn.apis.util.AMapUtil;
import com.amapv2.cn.apis.util.Constants;

public class RouteDemoActivity extends FragmentActivity implements
		OnMarkerClickListener, OnMapClickListener, OnInfoWindowClickListener,
		InfoWindowAdapter {
	private AMap mMap;
	private Button drivingButton;
	private Button transitButton;
	private Button walkButton;

	private ImageButton startImageButton;
	private ImageButton endImageButton;
	private ImageButton routeSearchImagebtn;

	private AutoCompleteTextView startTextView;
	private AutoCompleteTextView endTextView;

	private int mode = Route.BusDefault;
	private ProgressDialog progDialog;

	private PoiPagedResult startSearchResult;
	private PoiPagedResult endSearchResult;
	private String strStart;
	private String strEnd;
	private LatLonPoint startPoint = null;
	private LatLonPoint endPoint = null;
	private List<Route> routeResult;
	private LinearLayout routeNav;
	private ImageButton routePre, routeNext;
	private RouteOverlay routeOverlay;
	private Route route;
	private boolean isClickStart = false;
	private boolean isClickTarget = false;
	private Marker startMk, targetMk;
	public ArrayAdapter<String> aAdapter;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.route);
		startTextView = (AutoCompleteTextView) findViewById(R.id.autotextview_roadsearch_start);
		startTextView.addTextChangedListener(new TextWatcher() {

			public void afterTextChanged(Editable editable) {

			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String newText = s.toString();
				Inputtips inputTips = new Inputtips(RouteDemoActivity.this,
						new InputtipsListener() {

							@Override
							public void onGetInputtips(List<String> list) {
								aAdapter = new ArrayAdapter<String>(
										getApplicationContext(), R.layout.item,
										list);
								startTextView.setAdapter(aAdapter);
								aAdapter.notifyDataSetChanged();
							}
						});
				try {
					inputTips.requestInputtips(newText, "010");
				} catch (AMapException e) {
					e.printStackTrace();
				}
			}

		});
		endTextView = (AutoCompleteTextView) findViewById(R.id.autotextview_roadsearch_goals);
		endTextView.addTextChangedListener(new TextWatcher() {

			public void afterTextChanged(Editable editable) {

			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String newText = s.toString();
				Inputtips inputTips = new Inputtips(RouteDemoActivity.this,
						new InputtipsListener() {

							@Override
							public void onGetInputtips(List<String> list) {
								aAdapter = new ArrayAdapter<String>(
										getApplicationContext(), R.layout.item,
										list);
								endTextView.setAdapter(aAdapter);
								aAdapter.notifyDataSetChanged();
							}
						});
				try {
					inputTips.requestInputtips(newText, "010");
				} catch (AMapException e) {
					e.printStackTrace();
				}
			}

		});
		routeNav = (LinearLayout) findViewById(R.id.LinearLayoutLayout_index_bottom);
		drivingButton = (Button) findViewById(R.id.imagebtn_roadsearch_tab_driving);
		transitButton = (Button) findViewById(R.id.imagebtn_roadsearch_tab_transit);
		walkButton = (Button) findViewById(R.id.imagebtn_roadsearch_tab_walk);
		drivingButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mode = Route.DrivingDefault;
				drivingButton.setBackgroundResource(R.drawable.mode_driving_on);
				transitButton
						.setBackgroundResource(R.drawable.mode_transit_off);
				walkButton.setBackgroundResource(R.drawable.mode_walk_off);
			}
		});
		transitButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mode = Route.BusDefault;
				drivingButton
						.setBackgroundResource(R.drawable.mode_driving_off);
				transitButton.setBackgroundResource(R.drawable.mode_transit_on);
				walkButton.setBackgroundResource(R.drawable.mode_walk_off);

			}
		});

		walkButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mode = Route.WalkDefault;
				drivingButton
						.setBackgroundResource(R.drawable.mode_driving_off);
				transitButton
						.setBackgroundResource(R.drawable.mode_transit_off);
				walkButton.setBackgroundResource(R.drawable.mode_walk_on);

			}
		});

		startImageButton = (ImageButton) findViewById(R.id.imagebtn_roadsearch_startoption);
		startImageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showToast("在地图上点击您的起点");
				isClickStart = true;
				isClickTarget = false;
				registerListener();
			}
		});
		endImageButton = (ImageButton) findViewById(R.id.imagebtn_roadsearch_goalsoption);
		endImageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showToast("在地图上点击您的终点");
				isClickTarget = true;
				isClickStart = false;
				registerListener();
			}
		});

		routeSearchImagebtn = (ImageButton) findViewById(R.id.imagebtn_roadsearch_search);
		routeSearchImagebtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				strStart = startTextView.getText().toString().trim();
				strEnd = endTextView.getText().toString().trim();
				if (strStart == null || strStart.length() == 0) {
					Toast.makeText(RouteDemoActivity.this, "请选择起点",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if (strEnd == null || strEnd.length() == 0) {
					Toast.makeText(RouteDemoActivity.this, "请选择终点",
							Toast.LENGTH_SHORT).show();
					return;
				}

				startSearchResult();
			}
		});
		routePre = (ImageButton) findViewById(R.id.pre_index);
		routePre.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (routeOverlay != null) {
					boolean enablePre = routeOverlay.showPrePopInfo();
					if (!enablePre) {
						routePre.setBackgroundResource(R.drawable.prev_disable);
						routeNext
								.setBackgroundResource(R.drawable.btn_route_next);
					} else {
						routePre.setBackgroundResource(R.drawable.btn_route_pre);
						routeNext
								.setBackgroundResource(R.drawable.btn_route_next);
					}
				}
			}
		});
		routeNext = (ImageButton) findViewById(R.id.next_index);
		routeNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (routeOverlay != null) {
					boolean enableNext = routeOverlay.showNextPopInfo();
					if (!enableNext) {
						routePre.setBackgroundResource(R.drawable.btn_route_pre);
						routeNext
								.setBackgroundResource(R.drawable.next_disable);
					} else {
						routePre.setBackgroundResource(R.drawable.btn_route_pre);
						routeNext
								.setBackgroundResource(R.drawable.btn_route_next);
					}
				}
			}
		});

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onResume() {
		super.onResume();
		setUpMapIfNeeded();
	}

	@Override
	public void onInfoWindowClick(Marker arg0) {
		// TODO Auto-generated method stub
		isClickStart = false;
		isClickTarget = false;
		if (startMk.equals(arg0)) {
			startTextView.setText("地图上的点");
			startPoint = AMapUtil.convertToLatLonPoint(startMk.getPosition());
			startMk.hideInfoWindow();
			startMk.remove();
		} else if (targetMk.equals(arg0)) {
			endTextView.setText("地图上的点");
			endPoint = AMapUtil.convertToLatLonPoint(targetMk.getPosition());
			targetMk.hideInfoWindow();
			targetMk.remove();
		}

		releaseListener();
	}

	@Override
	public boolean onMarkerClick(Marker arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onMapClick(LatLng latng) {
		mMap.clear();
		if (isClickStart) {
			startMk = mMap.addMarker(new MarkerOptions()
					.anchor(0.5f, 1)
					.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.point)).position(latng)
					.title("点击选择为起点"));
			startMk.getPosition();
			startMk.showInfoWindow();
		} else if (isClickTarget) {
			targetMk = mMap.addMarker(new MarkerOptions()
					.anchor(0.5f, 1)
					.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.point)).position(latng)
					.title("点击选择为目的地"));
			targetMk.showInfoWindow();
		}

	}

	@Override
	public View getInfoContents(Marker arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public View getInfoWindow(Marker arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	private void registerListener() {
		mMap.setOnMapClickListener(RouteDemoActivity.this);
		mMap.setOnMarkerClickListener(RouteDemoActivity.this);
		mMap.setOnInfoWindowClickListener(RouteDemoActivity.this);
		mMap.setInfoWindowAdapter(RouteDemoActivity.this);
	}

	private void releaseListener() {
		mMap.setOnMapClickListener(null);
		mMap.setOnMarkerClickListener(null);
		mMap.setOnInfoWindowClickListener(null);
		mMap.setInfoWindowAdapter(null);
	}

	private void setUpMapIfNeeded() {
		if (mMap == null) {
			mMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();
			if (mMap != null) {
				setUpMap();
			}
		}
	}

	private void setUpMap() {
		mMap.getUiSettings().setZoomControlsEnabled(true);
	}

	public void showToast(String showString) {
		Toast.makeText(getApplicationContext(), showString, Toast.LENGTH_SHORT)
				.show();
	}

	// 查询路径规划起点
	public void startSearchResult() {
		strStart = startTextView.getText().toString().trim();
		if (startPoint != null && strStart.equals("地图上的点")) {
			endSearchResult();
		} else {
			final Query startQuery = new Query(strStart, PoiTypeDef.All, "010");
			progDialog = ProgressDialog.show(RouteDemoActivity.this, null,
					"正在搜索您所需信息...", true, true);
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					// 调用搜索POI方法
					PoiSearch poiSearch = new PoiSearch(RouteDemoActivity.this,
							startQuery); // 设置搜索字符串
					try {
						startSearchResult = poiSearch.searchPOI();
						if (progDialog.isShowing()) {
							routeHandler.sendMessage(Message.obtain(
									routeHandler, Constants.ROUTE_START_SEARCH));
						}
					} catch (AMapException e) {
						Message msg = new Message();
						msg.what = Constants.ROUTE_SEARCH_ERROR;
						msg.obj = e.getErrorMessage();
						routeHandler.sendMessage(msg);
					}
				}

			});
			t.start();
		}
	}

	// 查询路径规划终点
	public void endSearchResult() {

		strEnd = endTextView.getText().toString().trim();
		if (endPoint != null && strEnd.equals("地图上的点")) {
			searchRouteResult(startPoint, endPoint);
		} else {
			final Query endQuery = new Query(strEnd, PoiTypeDef.All, "010");
			progDialog = ProgressDialog.show(RouteDemoActivity.this, null,
					"正在搜索您所需信息...", true, false);
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					PoiSearch poiSearch = new PoiSearch(RouteDemoActivity.this,
							endQuery); // 设置搜索字符串
					try {
						endSearchResult = poiSearch.searchPOI();
						if (progDialog.isShowing()) {
							routeHandler.sendMessage(Message.obtain(
									routeHandler, Constants.ROUTE_END_SEARCH));
						}
					} catch (AMapException e) {
						Message msg = new Message();
						msg.what = Constants.ROUTE_SEARCH_ERROR;
						msg.obj = e.getErrorMessage();
						routeHandler.sendMessage(msg);
					}
				}

			});
			t.start();
		}
	}

	public void searchRouteResult(LatLonPoint startPoint, LatLonPoint endPoint) {
		progDialog = ProgressDialog.show(RouteDemoActivity.this, null,
				"正在获取线路", true, true);
		final Route.FromAndTo fromAndTo = new Route.FromAndTo(startPoint,
				endPoint);
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {

				try {
					routeResult = Route.calculateRoute(RouteDemoActivity.this,
							fromAndTo, mode);
					if (progDialog.isShowing()) {
						if (routeResult != null || routeResult.size() > 0)
							routeHandler.sendMessage(Message
									.obtain(routeHandler,
											Constants.ROUTE_SEARCH_RESULT));
					}
				} catch (AMapException e) {
					Message msg = new Message();
					msg.what = Constants.ROUTE_SEARCH_ERROR;
					msg.obj = e.getErrorMessage();
					routeHandler.sendMessage(msg);
				}
			}
		});
		t.start();

	}

	private Handler routeHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == Constants.ROUTE_START_SEARCH) {
				progDialog.dismiss();
				try {
					List<PoiItem> poiItems;
					if (startSearchResult != null
							&& (poiItems = startSearchResult.getPage(1)) != null
							&& poiItems.size() > 0) {
						RouteSearchPoiDialog dialog = new RouteSearchPoiDialog(
								RouteDemoActivity.this, poiItems);

						dialog.setTitle("您要找的起点是:");
						dialog.show();
						dialog.setOnListClickListener(new OnListItemClick() {
							@Override
							public void onListItemClick(
									RouteSearchPoiDialog dialog,
									PoiItem startpoiItem) {
								startPoint = startpoiItem.getPoint();
								strStart = startpoiItem.getTitle();
								startTextView.setText(strStart);
								endSearchResult();
							}

						});
					} else {
						showToast("无搜索起点结果,建议重新设定...");
					}
				} catch (AMapException e) {
					e.printStackTrace();
				}

			} else if (msg.what == Constants.ROUTE_END_SEARCH) {
				progDialog.dismiss();
				try {
					List<PoiItem> poiItems;
					if (endSearchResult != null
							&& (poiItems = endSearchResult.getPage(1)) != null
							&& poiItems.size() > 0) {
						RouteSearchPoiDialog dialog = new RouteSearchPoiDialog(
								RouteDemoActivity.this, poiItems);
						dialog.setTitle("您要找的终点是:");
						dialog.show();
						dialog.setOnListClickListener(new OnListItemClick() {
							@Override
							public void onListItemClick(
									RouteSearchPoiDialog dialog,
									PoiItem endpoiItem) {
								// TODO Auto-generated method stub
								endPoint = endpoiItem.getPoint();
								strEnd = endpoiItem.getTitle();
								endTextView.setText(strEnd);
								searchRouteResult(startPoint, endPoint);
							}

						});
					} else {
						showToast("无搜索起点结果,建议重新设定...");
					}
				} catch (AMapException e) {
					e.printStackTrace();
				}

			} else if (msg.what == Constants.ROUTE_SEARCH_RESULT) {
				progDialog.dismiss();
				if (routeResult != null && routeResult.size() > 0) {
					route = routeResult.get(0);
					if (route != null) {
						routeOverlay = new RouteOverlay(RouteDemoActivity.this,
								mMap, route);
						routeOverlay.removeFormMap();
						routeOverlay.addMarkerLine();
						routeNav.setVisibility(View.VISIBLE);
						routePre.setBackgroundResource(R.drawable.prev_disable);
						routeNext
								.setBackgroundResource(R.drawable.btn_route_next);
					}
				}
			} else if (msg.what == Constants.ROUTE_SEARCH_ERROR) {
				progDialog.dismiss();
				showToast((String) msg.obj);
			}
		}
	};
}
