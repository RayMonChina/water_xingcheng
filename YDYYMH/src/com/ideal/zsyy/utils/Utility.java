package com.ideal.zsyy.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;

import com.ideal2.components.ScreenParameter;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

/*import android.graphics.Bitmap;
 import android.graphics.Canvas;
 import android.graphics.PixelFormat;
 import android.graphics.drawable.Drawable;
 */
public class Utility {

	public static Bitmap drawableToBitmap(Drawable drawable) {

		Bitmap bitmap = Bitmap
				.createBitmap(
						drawable.getIntrinsicWidth(),
						drawable.getIntrinsicHeight(),
						drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
								: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;
	}

	private static Calendar c;
	private static int _year;
	private static int _month;
	private static int _day;

	public static void setListViewHeightBasedOnChildren(ListView listView) {

		ListAdapter listAdapter = listView.getAdapter();

		if (listAdapter == null) {

			// pre-condition

			return;

		}

		int totalHeight = 0;

		for (int i = 0; i < listAdapter.getCount(); i++) {
			Log.d("count", listAdapter.getCount() + "");
			Log.d("ddddd", listView.toString());
			View listItem = listAdapter.getView(i, null, listView);
			Log.d("i", i + "");

			listItem.measure(0, 0);
			Log.d("item", "--" + listItem.getMeasuredHeight());

			totalHeight += listItem.getMeasuredHeight();

		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();

		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		Log.e("", "hight======" + params.height);

		listView.setLayoutParams(params);
	}

	/**
	 * �õ���ǰ��ȷʱ��
	 * 
	 * @return
	 */
	public static String getCurrentTimeAccurate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		String str = sdf.format(date);
		return str;
	}

	/**
	 * �Ի���
	 * 
	 * @param str
	 * @param context
	 */
	public static void dialog(String str, Context context) {
		Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("��Ϣ");
		builder.setMessage(str);
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.show();
	}

	/**
	 * ��ȡ�豸����IP��ַ
	 * 
	 * @return
	 */
	public static String getLocalIpAddress() {
		String ipaddress = "";

		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						ipaddress = ipaddress + ";"
								+ inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
			Log.e("WifiPreference IpAddress", ex.toString());
		}
		return ipaddress;
	}

	/**
	 * ��֤���֤
	 * 
	 * @param str
	 * @return
	 */
	public static boolean checkID(String str) {
		String regex = "\\d{15}$|^\\d{17}([0-9]|X|x)";
		return str.matches(regex);
	}

	/**
	 * ��ʼ��ʱ��ؼ�
	 * 
	 * @param tv
	 */
	public static void initTime(TextView tv) {
		c = Calendar.getInstance();
		_year = c.get(Calendar.YEAR);
		_month = c.get(Calendar.MONTH) + 1;
		_day = c.get(Calendar.DAY_OF_MONTH);
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
		tv.setText(_year + "-" + month + "-" + day);
	}

	public static Bitmap returnBitMap(String url) {
		Log.i("returnBitMap", "url=" + url);
		URL myFileUrl = null;
		Bitmap bitmap = null;
		try {
			myFileUrl = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			HttpURLConnection conn = (HttpURLConnection) myFileUrl
					.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	public static String resetDateFormat(String src_data) {
		if (null != src_data && !"".equals(src_data)) {
			if (src_data.contains(" ")) {
				String str[] = src_data.split(" ");
				return str[0];
			} else if (src_data.length() > 10) {
				return src_data.substring(0, 10).toString();
			} else {
				return src_data;
			}
		} else {
			return "";
		}
	}

	/**
	 * 根据屏幕密度获取相对长度， 参照密度240
	 * 
	 * @param norm
	 *            基准长度
	 * @param context
	 * @return 相对长度
	 */
	public static int relativeLen(int norm, Context context) {
		ScreenParameter sParameter = ScreenParameter.getInstance(context);
		Log.d("Utility", "relativeLen: " + sParameter.getDensity() * norm / 240);
		return (int) (sParameter.getDensity() * norm / 240);
	}
	
	public static String PaddingLeft(String orStr,char padd,int length)
	{
		while(orStr.length()<length)
		{
			orStr=padd+orStr;
		}
		return orStr;
	}
}
