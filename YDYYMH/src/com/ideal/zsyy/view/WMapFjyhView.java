package com.ideal.zsyy.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.jijiang.wtapp.R;
import com.ideal.zsyy.activity.ZsyyApplication;
import com.ideal.zsyy.db.WdbManager;
import com.ideal.zsyy.entity.LocationInfo;
import com.ideal.zsyy.entity.WUserItem;

import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

public class WMapFjyhView extends BaseLayout {

	private MapView mMapView;
	private BaiduMap mBaiduMap;
	private LocationClient mLocClient;
	private Context context;
	boolean isFirstLoc = true;// 是否首次定位
	private InfoWindow infoWindow;
	private WdbManager wManager;
	List<Marker> markers = new ArrayList<Marker>();
	public MyLocationListenner myListener = new MyLocationListenner();
	BitmapDescriptor bdA = BitmapDescriptorFactory
			.fromResource(R.drawable.icon_marka);

	public WMapFjyhView(Context context) {
		super(context, R.layout.w_map_fjyh);
		// TODO Auto-generated constructor stub
		this.context = context;
		wManager=new WdbManager(context);
		this.InitMapView();
	}

	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null || mMapView == null) {
				return;
			}
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			mBaiduMap.setMyLocationData(locData);
			if (isFirstLoc) {
				isFirstLoc = false;
				LatLng ll = new LatLng(location.getLatitude(),
						location.getLongitude());
				mBaiduMap.animateMapStatus(MapStatusUpdateFactory.zoomTo(13));
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				mBaiduMap.animateMapStatus(u);
			}
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}

	private void InitMapView() {
		// 地图初始化
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			
			@Override
			public boolean onMarkerClick(Marker arg0) {
				// TODO Auto-generated method stub
				TextView tv_tip=new TextView(context);
				tv_tip.setBackgroundResource(R.drawable.popup);
				tv_tip.setText(arg0.getTitle());
				infoWindow=new InfoWindow(tv_tip, arg0.getPosition(),(-47));
				mBaiduMap.showInfoWindow(infoWindow);
				return false;
			}
		});
		// 定位初始化
		mLocClient = new LocationClient(this.context);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mLocClient.start();
		
		this.drawPoints();
	}

	
	
	public void onPause() {
		mMapView.onPause();
	}

	
	public void onResume() {
		mMapView.onResume();
		
	}

	
	public void onDestroy() {
		// 退出时销毁定位
		mLocClient.stop();
		// 关闭定位图层
		mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mMapView = null;
		super.onDestroy();
	}
	
	public void drawPoints() {
		mBaiduMap.clear();

		Random rad=new Random();
		LocationInfo currPoint=((ZsyyApplication)this.context.getApplicationContext()).currPoint;
		List<WUserItem>userItems=wManager.GetNearUserLocation(2000,currPoint);
		if(userItems.size()<1)
		{
			return;
		}
		//绘制在地图
		markers.clear();
		for (int i=0; i<userItems.size();i++) {
			WUserItem userItem=userItems.get(i);
			LatLng location=new LatLng(userItem.getLatitude(), userItem.getLongitude());
			MarkerOptions option = new MarkerOptions().title(userItem.getDz()).icon(bdA).position(location);
			/*Bundle b = new Bundle();
			b.putString("id", list.get(i).getID());
			option.extraInfo(b);*/
			markers.add((Marker)mBaiduMap.addOverlay(option));
		}
		
	}
}
