package com.ideal.zsyy.imagecache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.ideal.zsyy.utils.BitmapUtil;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageLoader {

	private AbstractFileCache fileCache;
	private static MemoryCache memoryCache = new MemoryCache();
	private Map<ImageView, String> imageViews = Collections
			.synchronizedMap(new WeakHashMap<ImageView, String>());
	// �̳߳�
	private ExecutorService executorService;

	private boolean isRoundedCornerBitmap;

	// Util

	public ImageLoader(Context context) {

		this.isRoundedCornerBitmap = false;
		fileCache = new FileCache(context);
		executorService = Executors.newFixedThreadPool(5);
	}

	// ����Ҫ�ķ���
	public void DisplayImage(String url, ImageView imageView,
			BaseAdapter baseAdapter, boolean isLoadOnlyFromCache) {

		imageViews.put(imageView, url);
		// �ȴ��ڴ滺���в���
		Bitmap bitmap = memoryCache.get(url);

		if (bitmap != null) {
			// ��ȡԲ��ͼƬ

			if (isRoundedCornerBitmap == true) {

				bitmap = BitmapUtil.getRoundedCornerBitmap(bitmap);
			}

			// bitmap = Util.getRoundedCornerBitmap(bitmap, 20.0f);
			imageView.setImageBitmap(bitmap);

		} else if (!isLoadOnlyFromCache) {
			// ��û�еĻ��������̼߳���ͼƬ
			queuePhoto(url, baseAdapter, imageView);
		}
	}

	private void queuePhoto(String url, BaseAdapter baseAdapter,
			ImageView imageView) {

		PhotoToLoad p = new PhotoToLoad(url, baseAdapter, imageView);
		executorService.submit(new PhotosLoader(p));
	}

	private Bitmap getBitmap(String url) {

		File f = fileCache.getFile(url);

		// �ȴ��ļ������в����Ƿ���
		Bitmap b = null;
		if (f != null && f.exists()) {
			b = decodeFile(f);
			// ��ȡԲ��ͼƬ
			if (isRoundedCornerBitmap == true) {

				b = BitmapUtil.getRoundedCornerBitmap(b);
			}

			// b = Util.getRoundedCornerBitmap(b, 20.0f);
		}
		if (b != null) {
			return b;
		}
		// ����ָ����url������ͼƬ
		try {
			Bitmap bitmap = null;
			URL imageUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) imageUrl
					.openConnection();
			conn.setConnectTimeout(6 * 1000);
			conn.setReadTimeout(15 * 1000);
			conn.setInstanceFollowRedirects(true);
			InputStream is = conn.getInputStream();
			String status = Environment.getExternalStorageState();
			// �ж��Ƿ���sdcard
			if (!status.equals(Environment.MEDIA_MOUNTED)) {

				// bitmap = Util.getRoundedCornerBitmap(
				// BitmapFactory.decodeStream(is), 20.0f);

				if (isRoundedCornerBitmap == true) {

					bitmap = BitmapUtil.getRoundedCornerBitmap(BitmapFactory
							.decodeStream(is));
				} else {

					bitmap = BitmapFactory.decodeStream(is);

				}

			} else {

				OutputStream os = new FileOutputStream(f);
				CopyStream(is, os);
				os.close();
				bitmap = decodeFile(f);
			}

			return bitmap;
		} catch (Exception ex) {
			Log.e("",
					"getBitmap catch Exception...\nmessage = "
							+ ex.getMessage());
			return null;
		}
	}

	// decode���ͼƬ���Ұ����������Լ����ڴ���ģ�������ÿ��ͼƬ�Ļ����СҲ�������Ƶ�
	private Bitmap decodeFile(File f) {
		try {
			// decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);

			// Find the correct scale value. It should be the power of 2.
			final int REQUIRED_SIZE = 100;
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			while (true) {
				if (width_tmp / 2 < REQUIRED_SIZE
						|| height_tmp / 2 < REQUIRED_SIZE)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}

			// decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (FileNotFoundException e) {
		}
		return null;
	}

	// Task for the queue
	private class PhotoToLoad {

		public String url;
		public ImageView imageView;
		public BaseAdapter baseAdapter;

		public PhotoToLoad(String u, BaseAdapter baseAdapter, ImageView i) {

			url = u;
			imageView = i;
			this.baseAdapter = baseAdapter;
		}
	}

	class PhotosLoader implements Runnable {

		PhotoToLoad photoToLoad;

		PhotosLoader(PhotoToLoad photoToLoad) {
			this.photoToLoad = photoToLoad;
		}

		@Override
		public void run() {

			if (imageViewReused(photoToLoad))
				return;
			Bitmap bmp = getBitmap(photoToLoad.url);
			memoryCache.put(photoToLoad.url, bmp);
			if (imageViewReused(photoToLoad))
				return;

			BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad);

			// ���µĲ�������UI�߳���
			if (photoToLoad.baseAdapter == null) {

				Activity a = (Activity) photoToLoad.imageView.getContext();
				a.runOnUiThread(bd);
			} else {
				new Thread(bd).start();
			}

		}
	}

	/**
	 * ��ֹͼƬ��λ
	 * 
	 * @param photoToLoad
	 * @return
	 */
	boolean imageViewReused(PhotoToLoad photoToLoad) {
		String tag = imageViews.get(photoToLoad.imageView);
		if (tag == null || !tag.equals(photoToLoad.url))
			return true;
		return false;
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			Object[] object;

			switch (msg.what) {
			case 0:

				object = (Object[]) msg.obj;
				PhotoToLoad photoToLoad = (PhotoToLoad) object[0];
				photoToLoad.imageView.setImageBitmap((Bitmap) object[1]);
				photoToLoad.baseAdapter.notifyDataSetChanged();
				break;
			}
		}
	};

	// ������UI�߳��и��½���
	class BitmapDisplayer implements Runnable {

		Bitmap bitmap;
		PhotoToLoad photoToLoad;

		public BitmapDisplayer(Bitmap b, PhotoToLoad p) {

			bitmap = b;
			photoToLoad = p;
		}

		public void run() {

			if (imageViewReused(photoToLoad))
				return;
			if (bitmap != null) {

				if (photoToLoad.baseAdapter != null) {

					Message message = new Message();
					Object[] objects = { photoToLoad, bitmap };
					message.obj = objects;
					handler.sendMessage(message);

				} else {

					photoToLoad.imageView.setImageBitmap(bitmap);
				}

			}

		}
	}

	public void clearCache() {
		memoryCache.clear();
		fileCache.clear();
	}

	public static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
			Log.e("", "CopyStream catch Exception...");
		}
	}

	public boolean isRoundedCornerBitmap() {
		return isRoundedCornerBitmap;
	}

	public void setRoundedCornerBitmap(boolean isRoundedCornerBitmap) {
		
		this.isRoundedCornerBitmap = isRoundedCornerBitmap;
		
//		this.isRoundedCornerBitmap = false;
	}

}