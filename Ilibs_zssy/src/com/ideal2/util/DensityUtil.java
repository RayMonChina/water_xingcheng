package com.ideal2.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.logging.Logger;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;

//计算公式 pixels = dips * (density / 160)

public class DensityUtil {

	private static final String TAG = DensityUtil.class.getSimpleName();

	// 当前屏幕的densityDpi
	private static float dmDensityDpi = 0.0f;
	private static DisplayMetrics dm;
	private static float scale = 0.0f;

	public DensityUtil(Context context) {
		// 获取当前屏幕
		dm = new DisplayMetrics();
		// 返回当前资源对象的DispatchMetrics信息。
		dm = context.getApplicationContext().getResources().getDisplayMetrics();
		// 设置DensityDpi
		setDmDensityDpi(dm.densityDpi);
		// 密度因子
		scale = getDmDensityDpi() / 160;// 等于 scale=dm.density;
	}

	// dip转像素
	public static int DipToPixels(Context context, int dip) {
		final float SCALE = context.getResources().getDisplayMetrics().density;
		float valueDips = dip;
		int valuePixels = (int) (valueDips * SCALE + 0.5f);
		return valuePixels;

	}

	// 像素转dip
	public static float PixelsToDip(Context context, int Pixels) {
		final float SCALE = context.getResources().getDisplayMetrics().density;
		float dips = Pixels / SCALE;
		return dips;

	}

	// 指定图片长宽生成新图片

	public static Bitmap decodeBitmap(Bitmap initialBitmap, int height,
			int weight) {
		int bmpHeight = initialBitmap.getHeight();
		int bmpWeight = initialBitmap.getWidth();
		float scale = Math.min(height / bmpHeight, weight / bmpWeight);
		Bitmap mutableBitmap = Bitmap.createScaledBitmap(initialBitmap,
				(int) (bmpWeight * scale), (int) (bmpHeight * scale), true);// 指定图片长宽，生成新图片
		return mutableBitmap;
	}

	// 将Bitmap另存为指定的JPG文件

	public static void writePhotoJpg(Bitmap data, String pathName) {
		File file = new File(pathName);
		try {
			file.createNewFile();
			// BufferedOutputStream os = new BufferedOutputStream(
			// new FileOutputStream(file));

			FileOutputStream os = new FileOutputStream(file);
			data.compress(Bitmap.CompressFormat.JPEG, 100, os);
			os.flush();
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 将Bitmap另存为指定的PNG文件
	public static void writePhotoPng(Bitmap data, String pathName) {
		File file = new File(pathName);
		try {
			file.createNewFile();
			FileOutputStream os = new FileOutputStream(file);
			// BufferedOutputStream os = new BufferedOutputStream(
			// new FileOutputStream(file));
			data.compress(Bitmap.CompressFormat.PNG, 100, os);
			os.flush();
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static float getDmDensityDpi() {
		return dmDensityDpi;
	}

	public static void setDmDensityDpi(float dmDensityDpi) {
		DensityUtil.dmDensityDpi = dmDensityDpi;
	}

	public static int dip2px(float dipValue) {

		return (int) (dipValue * scale + 0.5f);

	}

	public int px2dip(float pxValue) {
		return (int) (pxValue / scale + 0.5f);
	}

	@Override
	public String toString() {
		return " dmDensityDpi:" + dmDensityDpi;
	}
}
