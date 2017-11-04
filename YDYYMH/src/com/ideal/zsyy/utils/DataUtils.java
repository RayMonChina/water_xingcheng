package com.ideal.zsyy.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ideal.zsyy.Config;
import com.jijiang.wtapp.R;
import com.ideal.zsyy.entity.SkinInfo;
import com.ideal.zsyy.imagecache.CommonUtil;
import com.ideal.zsyy.request.PhDoctorReq;
import com.ideal.zsyy.response.PhDoctorRes;
import com.ideal2.base.gson.GsonServlet;

public class DataUtils {
	public static final String doctor_path = Environment
			.getExternalStorageDirectory()
			+ File.separator
			+ Config.down_path
			+ File.separator + "doctor_image";
	public static final String download = Environment
			.getExternalStorageDirectory()
			+ File.separator
			+ Config.down_path
			+ File.separator + "download";
	
	public static String dataurl = CommonUtil.getRootFilePath() + Config.down_path+ "/datacache/";

	public static void getDoctorInfos(Context context, final PhDoctorReq req,
			final Handler mHandler) {
		GsonServlet<PhDoctorReq, PhDoctorRes> gServlet = new GsonServlet<PhDoctorReq, PhDoctorRes>(
				context);
		gServlet.request(req, PhDoctorRes.class);
		gServlet.setOnResponseEndListening(new GsonServlet.OnResponseEndListening<PhDoctorReq, PhDoctorRes>() {

			@Override
			public void onResponseEnd(PhDoctorReq commonReq,
					PhDoctorRes commonRes, boolean result, String errmsg,
					int responseCode) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onResponseEndSuccess(PhDoctorReq commonReq,
					PhDoctorRes commonRes, String errmsg, int responseCode) {
				// TODO Auto-generated method stub
				if (commonRes != null) {
					Message msg = mHandler.obtainMessage(0,
							commonRes.getPhDoctorInfos());
					mHandler.sendMessage(msg);
					GsonBuilder gsonBuilder = new GsonBuilder();
					Gson gson = gsonBuilder.create();
					String data = gson.toJson(commonRes);
					FileUtil.setDataFileCache(dataurl, data,"doctor.txt");
				}
			}

			@Override
			public void onResponseEndErr(PhDoctorReq commonReq,
					PhDoctorRes commonRes, String errmsg, int responseCode) {
				// TODO Auto-generated method stub

			}
		});
	}

	// 下载图片
	public static Uri getImage(String imgpath, String filename) {
		try {
			File file = new File(DataUtils.doctor_path + File.separator
					+ filename);
			if (file.exists()) {
				return Uri.fromFile(file);
			} else {
				InputStream is = HttpUtil.getDataFromWeb(imgpath);
				FileOutputStream fos = new FileOutputStream(file);
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = is.read(buffer)) != -1) {
					fos.write(buffer, 0, len);
				}
				is.close();
				fos.close();
				Log.i("download：", "下载。。");
				return Uri.fromFile(file);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}


	// 利用Toast弹出提示
	public static void showToast(Context context) {
		LayoutInflater mInflater = LayoutInflater.from(context);
		View view = mInflater.inflate(R.layout.dialog, null);
		view.getBackground().setAlpha(180);

		Toast toast = new Toast(context);
		toast.setGravity(Gravity.CENTER, 0, 10);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(view);
		toast.show();
	}

	// 从服务器下载apk:
	public static File getFileFromServer(String urlpath, File files,
			ProgressDialog pd) throws Exception {
		// 如果相等的话表示当前的sdcard挂载在手机上并且是可用的
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			String apkName = urlpath.substring(urlpath.lastIndexOf("/") + 1);
			String downloadpath = urlpath;
			URL url = new URL(downloadpath);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			// 获取到文件的大小
			pd.setMax(conn.getContentLength());
			InputStream is = conn.getInputStream();
			File file = new File(files, apkName);
			FileOutputStream fos = new FileOutputStream(file);
			BufferedInputStream bis = new BufferedInputStream(is);
			byte[] buffer = new byte[1024];
			int len;
			int total = 0;
			while ((len = bis.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
				total += len;
				// 获取当前下载量
				pd.setProgress(total);
			}
			fos.close();
			bis.close();
			is.close();
			return file;
		} else {
			return null;
		}
	}

	// 23.87MB 00.87MB/KB byte
	public static String formatByte(long data) {
		DecimalFormat format = new DecimalFormat("##.##");
		if (data < 1024) {
			return data + "bytes";
		} else if (data < 1024 * 1024) {
			return format.format(data / 1024f) + "KB";
		} else if (data < 1024 * 1024 * 1024) {
			return format.format(data / 1024f / 1024f) + "MB";
		} else if (data < 1024 * 1024 * 1024 * 1024) {
			return format.format(data / 1024f / 1024f / 1024f) + "GB";
		} else {
			return "超出统计返回";
		}
	}

	public static List<String> getDateTimeStr(int count, boolean flag) {
		List<String> timestr = new ArrayList<String>();
		for (int i = 0; i < count; i++) {
			String dateStr = getDateStr(i + 1, flag);
			timestr.add(dateStr);
		}
		return timestr;
	}

	public static String[] getDateTimeStr1(int count) {
		String[] timestr = new String[count];
		for (int i = 0; i < count; i++) {
			String dateStr = getDateStr(i , false);
			timestr[i] = dateStr;
		}
		return timestr;
	}

	public static String getDateStr(int mon, boolean flag) {
		String time = "";
		if (flag) {
			if (mon % 2 == 1) {
				time = "01 ";
				mon = mon / 2 + 1;
			} else if (mon % 2 == 0) {
				time = "02 ";
				mon = mon / 2;
			}
		}
		Calendar cal = Calendar.getInstance();
		int _year = cal.get(Calendar.YEAR);
		int _month = cal.get(Calendar.MONTH) + 1;
		int _day = cal.get(Calendar.DAY_OF_MONTH) + mon;
		int hour=cal.get(Calendar.HOUR_OF_DAY);
		if(hour>12){
			_day=_day+1;
		}
		int lastday = getLastDay();
		if (_day > lastday) {
			_month = _month + 1;
			_day = _day - lastday;
		}
		String month = "";
		String day = "";
		if (_month < 10) {
			month = "0" + _month;
		} else {
			month = _month + "";
		}
		if (_day < 10) {
			day = "0" + _day;
		} else {
			day = _day + "";
		}
		if(_month>12){
			month="01";
			_year=_year+1;
		}
		time = time + _year + "-" + month + "-" + day;
		return time;
	}

	public static int getLastDay() {
		int lastday;
		Calendar d = Calendar.getInstance();
		int month = d.get(Calendar.MONTH);
		do {
			lastday = d.get(Calendar.DAY_OF_MONTH);
			d.add(Calendar.DAY_OF_MONTH, 1);
		} while (d.get(Calendar.MONTH) == month);
		return lastday;
	}

	public static boolean compare_date(String DATE1) {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = new Date();
			if (dt1.getTime() > dt2.getTime()) {
				System.out.println("还没到...");
				return false;
			} else if (dt1.getTime() < dt2.getTime()) {
				System.out.println("超过当期日期...");
				return true;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return false;
	}

	public static String convertString(String time, String locate) {
		String str = "";
		String[] timeStr1 = DataUtils.amtimelist();
		for (int i = 0; i < timeStr1.length; i++) {
			String time1 = timeStr1[i].replace("-", "").replace(":", "");
			if (time.equals(time1)) {
				str = timeStr1[i];
				break;
			}
		}
		if ("".equals(str)) {
			String[] timeStr2 = null;
			if ("N".equals(locate)) {
				timeStr2 = DataUtils.pmtimelist_n();
			} else if ("S".equals(locate)) {
				timeStr2 = DataUtils.pmtimelist_s();
			}
			for (int i = 0; i < timeStr2.length; i++) {
				String time1 = timeStr2[i].replace("-", "").replace(":", "");
				if (time.equals(time1)) {
					str = timeStr2[i];
					break;
				}
			}
		}
		return str;
	}

	public static String[] amtimelist() {
		String[] amtimelist = new String[8];
		amtimelist[0] = "08:00-08:30";
		amtimelist[1] = "08:30-09:00";
		amtimelist[2] = "09:00-09:30";
		amtimelist[3] = "09:30-10:00";
		amtimelist[4] = "10:00-10:30";
		amtimelist[5] = "10:30-11:00";
		amtimelist[6] = "11:00-11:30";
		amtimelist[7] = "11:30-12:00";
		return amtimelist;
	}

	public static String[] pmtimelist_n() {
		String[] pmtimelist = new String[9];
		pmtimelist[0] = "12:00-12:30";
		pmtimelist[1] = "12:30-13:00";
		pmtimelist[2] = "13:00-13:30";
		pmtimelist[3] = "13:30-14:00";
		pmtimelist[4] = "14:00-14:30";
		pmtimelist[5] = "14:30-15:00";
		pmtimelist[6] = "15:00-15:30";
		pmtimelist[7] = "15:30-16:00";
		pmtimelist[8] = "16:00-16:30";
		return pmtimelist;
	}

	public static String[] pmtimelist_s() {
		String[] pmtimelist = new String[8];
		pmtimelist[0] = "12:00-12:30";
		pmtimelist[1] = "12:30-13:00";
		pmtimelist[2] = "13:00-13:30";
		pmtimelist[3] = "13:30-14:00";
		pmtimelist[4] = "14:00-14:30";
		pmtimelist[5] = "14:30-15:00";
		pmtimelist[6] = "15:00-15:30";
		pmtimelist[7] = "15:30-16:00";
		return pmtimelist;
	}

	public static String[] quantiamlist_n() {
		String[] quantiamlist = new String[17];
		quantiamlist[0] = "08:00-08:30";
		quantiamlist[1] = "08:30-09:00";
		quantiamlist[2] = "09:00-09:30";
		quantiamlist[3] = "09:30-10:00";
		quantiamlist[4] = "10:00-10:30";
		quantiamlist[5] = "10:30-11:00";
		quantiamlist[6] = "11:00-11:30";
		quantiamlist[7] = "11:30-12:00";
		quantiamlist[8] = "12:00-12:30";
		quantiamlist[9] = "12:30-13:00";
		quantiamlist[10] = "13:00-13:30";
		quantiamlist[11] = "13:30-14:00";
		quantiamlist[12] = "14:00-14:30";
		quantiamlist[13] = "14:30-15:00";
		quantiamlist[14] = "15:00-15:30";
		quantiamlist[15] = "15:30-16:00";
		quantiamlist[16] = "16:00-16:30";
		return quantiamlist;
	}

	public static String[] quantiamlist_s() {
		String[] quantiamlist = new String[16];
		quantiamlist[0] = "08:00-08:30";
		quantiamlist[1] = "08:30-09:00";
		quantiamlist[2] = "09:00-09:30";
		quantiamlist[3] = "09:30-10:00";
		quantiamlist[4] = "10:00-10:30";
		quantiamlist[5] = "10:30-11:00";
		quantiamlist[6] = "11:00-11:30";
		quantiamlist[7] = "11:30-12:00";
		quantiamlist[8] = "12:00-12:30";
		quantiamlist[9] = "12:30-13:00";
		quantiamlist[10] = "13:00-13:30";
		quantiamlist[11] = "13:30-14:00";
		quantiamlist[12] = "14:00-14:30";
		quantiamlist[13] = "14:30-15:00";
		quantiamlist[14] = "15:00-15:30";
		quantiamlist[15] = "15:30-16:00";
		return quantiamlist;
	}
}
