package com.ideal.zsyy.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.HashMap;

import com.ideal.zsyy.Config;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.PorterDuff.Mode;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

public class AsynImageLoader {

	private HashMap<String, SoftReference<Bitmap>> imageCache; // 内存图片软引用缓冲

	public AsynImageLoader() {
		Log.i("i", "创建imageCache...");
		imageCache = new HashMap<String, SoftReference<Bitmap>>();
	}

	public Bitmap loadBitmap(final ImageView imageView, final String imageURL,
			final ImageCallBack imageCallBack) {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {

			if (imageCache.containsKey(imageURL)) { // 在内存中查找，
				SoftReference<Bitmap> reference = imageCache.get(imageURL);
				Bitmap bitmap = reference.get();
				if (bitmap != null) {
					return getRoundedCornerBitmap(bitmap);
				}
			} else { // 做sdcard中查找
				String bitmapName = imageURL.substring(imageURL
						.lastIndexOf("/") + 1);
				File file = new File(DataUtils.doctor_path);
				if (!file.exists()) {
					file.mkdirs();
				}
				Log.i("i", " filePath:" + file.getAbsolutePath()
						+ ",,filename:" + bitmapName);
				File[] cacheFiles = file.listFiles();
				int i = 0;
				if (cacheFiles != null) {
					for (; i < cacheFiles.length; i++) {
						if (bitmapName.equals(cacheFiles[i].getName())) {
							Log.i("i", "filepath:" + cacheFiles[i].getName());
							break;
						}
					}
					if (i < cacheFiles.length) {
						Bitmap bitmap = BitmapFactory
								.decodeFile(DataUtils.doctor_path
										+ File.separator + bitmapName);
						return getRoundedCornerBitmap(bitmap);
					}
				}
			}
			// 使用handler刷新界面
			final Handler handler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					// TODO Auto-generated method stub
					Log.i("i", "进入回调。。。。");
					imageCallBack.imageLoad(imageView, (Bitmap) msg.obj);
				}
			};

			// 开线程进行下载
			new Thread() {
				public void run() {
					String bitmapName = imageURL.substring(imageURL
							.lastIndexOf("/") + 1);
					String pic_down_url = "http://" + Config.post
							+ File.separator + "PalmHospital" + File.separator + imageURL;
//					String pic_down_url = "http://" + Config.post
//							+ File.separator + "PH" + File.separator + imageURL;
					Log.i("i", "下载URL：" + pic_down_url);
					InputStream bitmapIs = HttpUtil
							.getDataFromWeb(pic_down_url);
					
					if (bitmapIs != null) {
						Bitmap bitmap = BitmapFactory.decodeStream(bitmapIs);
						saveImgFile(bitmap, bitmapName);
						imageCache.put(imageURL, new SoftReference<Bitmap>(
								bitmap));
						bitmap = getRoundedCornerBitmap(bitmap);
						Message msg = handler.obtainMessage(0, bitmap);
						handler.sendMessage(msg);
					}
				};
			}.start();
		}
		return null;
	}
	
	/**
	 * 将图片写入sdcard
	 * @param mImg
	 * @param bitmapName
	 */
	public void saveImgFile(Bitmap mImg,String bitmapName) {
		BufferedOutputStream bos = null;
		File mFile = new File(DataUtils.doctor_path
				+ File.separator + bitmapName);
		if (mFile.exists())
			return;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(mFile));
			if (mImg != null) {
				mImg.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bos != null) {
					bos.flush();
					bos.close();
				}
				mFile = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
//	
//	public void writerSdcard(InputStream bitmapIs,String bitmapName){
//		try {
//			File file = new File(DataUtils.doctor_path
//					+ File.separator + bitmapName);
//			FileOutputStream fos = new FileOutputStream(file);
//			byte[] buffer = new byte[1024];
//			int len = 0;
//			while ((len = bitmapIs.read(buffer)) != -1) {
//				fos.write(buffer, 0, len);
//			}
//			fos.flush();
//			fos.close();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	public interface ImageCallBack {
		public void imageLoad(ImageView imageView, Bitmap bitmap);
	}
	
	private static Bitmap output;

	/**
	 * 将图片变为圆形图片
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
		if (bitmap != null) {
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();
			float roundPx;
			float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
			if (width <= height) {
				roundPx = width / 2;
				top = 0;
				bottom = width;
				left = 0;
				right = width;
				height = width;
				dst_left = 0;
				dst_top = 0;
				dst_right = width;
				dst_bottom = width;
			} else {
				roundPx = height / 2;
				float clip = (width - height) / 2;
				left = clip;
				right = width - clip;
				top = 0;
				bottom = height;
				width = height;
				dst_left = 0;
				dst_top = 0;
				dst_right = height;
				dst_bottom = height;
			}
			Bitmap output = Bitmap
					.createBitmap(width, height, android.graphics.Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(output);
			final int color = 0xff424242;
			final Paint paint = new Paint();
			final Rect src = new Rect((int) left, (int) top, (int) right,
					(int) bottom);
			final Rect dst = new Rect((int) dst_left, (int) dst_top,
					(int) dst_right, (int) dst_bottom);
			final RectF rectF = new RectF(dst);
			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);
			canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			canvas.drawBitmap(bitmap, src, dst, paint);
			return output;
		}
		return null;
	}

	/**
	 * 清楚缓存
	 */
	public static void destory(){
		if (output != null && !output.isRecycled()) {
			output.recycle();
			output = null;
		}
	}
	
	/**
	 * 删除之前图片
	 */
	public static void deleteImage(){
		File file = new File(DataUtils.doctor_path);
		if (file != null && file.listFiles().length > 0) {
			for (File f : file.listFiles()) {
				f.delete();
			}
		}
	}
}
