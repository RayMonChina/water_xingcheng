package com.ideal.zsyy.activity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.ideal.zsyy.db.WdbManager;
import com.ideal.zsyy.entity.LocationInfo;
import com.ideal.zsyy.entity.WPointItem;
import com.ideal.zsyy.request.WPointReq;
import com.ideal.zsyy.response.WPointRes;
import com.ideal.zsyy.service.LocationService;
import com.ideal.zsyy.service.PreferencesService;
import com.ideal.zsyy.service.TickService;
import com.ideal.zsyy.utils.CrashHandler;
import com.ideal.zsyy.utils.FileUtils;
import com.ideal.zsyy.utils.HttpUtil;
import com.ideal2.base.gson.GsonServlet;
import com.ideal2.base.gson.GsonServlet.OnResponseEndListening;

import android.app.Application;
import android.app.Service;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;

public class ZsyyApplication extends Application {

	public LocationClient mLocationClient;
	public MyLocationListener mMyLocationListener;

	public Vibrator mVibrator;

	public LocationInfo currPoint=null;
	public LocationInfo tempPoint=null;
	LatLng pointEnd;
	LatLng pointBegin;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		SDKInitializer.initialize(this);
//		CrashHandler crashHandler = CrashHandler.getInstance();  
//        crashHandler.init(getApplicationContext());  
        
		mLocationClient = new LocationClient(this.getApplicationContext());
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		mVibrator = (Vibrator) getApplicationContext().getSystemService(
				Service.VIBRATOR_SERVICE);
		
		Intent iservice=new Intent(getApplicationContext(),LocationService.class);
		startService(iservice);
		
		Intent iserviceTick=new Intent(getApplicationContext(),TickService.class);
		startService(iserviceTick);
		try {
			FileUtils.createSDDir("");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// Receive Location
			Boolean hasValue=false;
			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());// 单位：公里每小时
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
				sb.append("\nheight : ");
				sb.append(location.getAltitude());// 单位：米
				sb.append("\ndirection : ");
				sb.append(location.getDirection());
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				sb.append("\ndescribe : ");
				sb.append("gps定位成功");
				//
				LocationInfo lInfo=new LocationInfo();
				lInfo.setGetDate(new Date());
				lInfo.setLatitude(location.getLatitude());
				lInfo.setLontitude(location.getLongitude());
				lInfo.setRadius(location.getRadius());
				currPoint=lInfo;
				hasValue=true;

			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				// 运营商信息
				sb.append("\noperationers : ");
				sb.append(location.getOperators());
				sb.append("\ndescribe : ");
				sb.append("网络定位成功");
				//
				LocationInfo lInfo=new LocationInfo();
				lInfo.setGetDate(new Date());
				lInfo.setLatitude(location.getLatitude());
				lInfo.setLontitude(location.getLongitude());
				lInfo.setRadius(location.getRadius());
				currPoint=lInfo;
				hasValue=true;
			} else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
				sb.append("\ndescribe : ");
				sb.append("离线定位成功，离线定位结果也是有效的");
				//
				LocationInfo lInfo=new LocationInfo();
				lInfo.setGetDate(new Date());
				lInfo.setLatitude(location.getLatitude());
				lInfo.setLontitude(location.getLongitude());
				lInfo.setRadius(location.getRadius());
				currPoint=lInfo;
				hasValue=true;
			} else if (location.getLocType() == BDLocation.TypeServerError) {
				sb.append("\ndescribe : ");
				sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
			} else if (location.getLocType() == BDLocation.TypeNetWorkException) {
				sb.append("\ndescribe : ");
				sb.append("网络不同导致定位失败，请检查网络是否通畅");
			} else if (location.getLocType() == BDLocation.TypeCriteriaException) {
				sb.append("\ndescribe : ");
				sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
			}
			
			logMsg(sb.toString());
			Log.i("BaiduLocationApiDem", sb.toString());
			if(hasValue)
			{
				if(tempPoint==null)
				{
					tempPoint=currPoint;
				}
				else {
					pointBegin=new LatLng(tempPoint.getLatitude(),tempPoint.getLontitude());
					pointEnd=new LatLng(currPoint.getLatitude(),currPoint.getLontitude());
					if(Math.abs(DistanceUtil.getDistance(pointBegin, pointEnd)) <= 20)
					{
						return;
					}
				}
				tempPoint=currPoint;
				Map<String, Object>userInfo=new PreferencesService(getApplicationContext()).getLoginInfo();
//				if(userInfo!=null&&userInfo.containsKey("use_id")&&userInfo.containsKey("userName"))
//				{
//					WdbManager wManager=new WdbManager(getApplicationContext());
//					WPointItem pointItem=new WPointItem();
//					pointItem.setAddDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//					pointItem.setAlreadyUpload(1);
//					pointItem.setLatitude(currPoint.getLatitude());
//					pointItem.setLongitude(currPoint.getLontitude());
//					pointItem.setUserId(userInfo.get("use_id").toString());
//					pointItem.setUserName(userInfo.get("userName").toString());
//					pointItem.setMeterDateTimeBegin(Integer.parseInt(userInfo.get("meterdatetimebegin").toString()));
//					pointItem.setMeterDateTimeEnd(Integer.parseInt(userInfo.get("meterdatetimeend").toString()));
//					wManager.AddWalkPoint(pointItem);
//					UploadLocation(wManager);
//				}
				//wManager.closeDB();
			}
		}

	}
	
	private void UploadLocation(final WdbManager dbManager)
	{
//		WPointReq req = new WPointReq();
//		req.setOperType("10");
//		req.setPointItems(dbManager.GetWalkPoints());
//		if(req.getPointItems()==null||req.getPointItems().size()==0)
//		{
//			if(dbManager!=null)
//			{
//				dbManager.closeDB();
//			}
//			return;
//		}
//		if(!HttpUtil.checkNet(getApplicationContext()))
//		{
//			if(dbManager!=null)
//			{
//				dbManager.closeDB();
//			}
//			return;
//		}
//		GsonServlet<WPointReq, WPointRes> gServlet = new GsonServlet<WPointReq, WPointRes>(
//				this);
//		gServlet.request(req, WPointRes.class);
//		gServlet.setShowDialog(false);
//		gServlet.setOnResponseEndListening(new OnResponseEndListening<WPointReq, WPointRes>() {
//
//			@Override
//			public void onResponseEnd(WPointReq commonReq, WPointRes commonRes,
//					boolean result, String errmsg, int responseCode) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void onResponseEndSuccess(WPointReq commonReq,
//					WPointRes commonRes, String errmsg, int responseCode) {
//				// TODO Auto-generated method stub
//				if(commonRes!=null)
//				{
//					dbManager.UpdateWPointTag();
//					dbManager.closeDB();
//				}
//
//			}
//
//			@Override
//			public void onResponseEndErr(WPointReq commonReq,
//					WPointRes commonRes, String errmsg, int responseCode) {
//				// TODO Auto-generated method stub
//			}

	//	});
	}

	public void logMsg(String str) {
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
