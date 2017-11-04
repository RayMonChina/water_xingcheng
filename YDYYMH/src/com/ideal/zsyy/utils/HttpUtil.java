package com.ideal.zsyy.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;

public class HttpUtil {

	// private static Bitmap bitmap;

	/**
	 * 下载图片并保存
	 * 
	 * @param downPath
	 *            sd卡位置
	 * @param url
	 *            下载url
	 * @param name
	 *            图片名称
	 */
	public static void createPic_new(String downPath, String url, String name) {

		String sd_path = "";

		// Environment.getExternalStorageDirectory()
		if (url != null && !url.trim().equals("")) {

			File file = new File(sd_path + downPath);
			if (!file.exists()) {
				file.mkdirs();
			}

			File file2 = new File(file.getPath(), name);
			OutputStream out = null;
			InputStream in = null;
			Bitmap bitmap = null;
			try {
				if (!file2.exists()) {

					// Log.v("tags", "!!!!");

					in = getDataFromWeb(url);
					if (in != null) {

						file2.createNewFile();
						out = new FileOutputStream(file2);
						byte[] buffer = new byte[1024];
						int len = 0;
						while ((len = in.read(buffer)) != -1) {
							out.write(buffer, 0, len);
						}
					}

				} else {

					// Log.v("tags", "%%%%%");

					bitmap = BitmapFactory.decodeFile(file.getPath()
							+ File.separator + name);
					if (bitmap == null) {
						file2.delete();
						in = getDataFromWeb(url);
						if (in != null) {

							file2.createNewFile();
							out = new FileOutputStream(file2);
							byte[] buffer = new byte[1024];
							int len = 0;
							while ((len = in.read(buffer)) != -1) {
								out.write(buffer, 0, len);
							}
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {

				try {

					if (bitmap != null && !bitmap.isRecycled()) {
						bitmap.recycle();
						bitmap = null;
					}

					if (in != null) {
						in.close();
					}
					if (out != null) {
						out.flush();
						out.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static Bitmap readPic(String url) {

		InputStream in = getDataFromWeb(url);

		Bitmap bitmap = BitmapFactory.decodeStream(in);

		if (in != null) {
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return bitmap;

	}

	/**
	 * 判断网络
	 * 
	 * @param context
	 * @return
	 */
	public static boolean checkNet(Context context) {
		try {
			ConnectivityManager connectivity = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				// 获取网络连接管理的对象
				NetworkInfo info = connectivity.getActiveNetworkInfo();
				if (info != null && info.isConnected()) {
					// 判断当前网络是否已经连接
					if (info.getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		} catch (Exception e) {
		}
		return false;
	}

	public static InputStream getDataFromWeb(String urlPath) {

		HttpURLConnection conn = null;
		InputStream inStream = null;
		try {
			if (urlPath != null && !"".equals(urlPath.trim())) {

				// String sDest=URLEncoder.encode(urlPath , "utf-8");

				URL url = new URL(urlPath);
				conn = (HttpURLConnection) url.openConnection();

				conn.setRequestMethod("GET");
				// 请求超时 4 秒钟
				conn.setConnectTimeout(6 * 1000);
				conn.setReadTimeout(20 * 1000);
				conn.connect();
				if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
					inStream = conn.getInputStream();
				}
				return inStream;
			}
		} catch (MalformedURLException e) {
			// requestStatus = -1;
			e.printStackTrace();
		} catch (ProtocolException e) {
			// requestStatus = -1;
			e.printStackTrace();
		} catch (IOException e) {
			// requestStatus = -1;
			e.printStackTrace();
		}
		return inStream;
	}

	public static String getServerData(String url) {

		HttpPost request;
		String out = null;
		try {
			HttpClient client = new DefaultHttpClient();
			request = new HttpPost(new URI(url));
			HttpResponse response = client.execute(request);
			// 判断请求是否成功
			if (response.getStatusLine().getStatusCode() == 200) {

				HttpEntity entity = response.getEntity();
				if (entity != null) {

					out = EntityUtils.toString(entity);
					return out;
				}
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out;
	}

}
