package com.ideal.zsyy.utils;

import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.view.View;

public class BitmapUtil {

	private static Bitmap output;

	/**
	 * ��ͼƬ��ΪԲ��ͼƬ
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {

		output = null;
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
			output = Bitmap.createBitmap(width, height,
					android.graphics.Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(output);
			canvas.setDrawFilter(new PaintFlagsDrawFilter(0,
					Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
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

			bitmap = output;
			return bitmap;
		}
		output = null;
		return null;
	}

	public static Bitmap downloadBitmap(final View view, final String url,
			final Handler handler, final int messageWhat) {

		output = null;
		new Thread(new Runnable() {

			@Override
			public void run() {

				InputStream in = HttpUtil.getDataFromWeb(url);

				output = BitmapFactory.decodeStream(in);

				if (output != null) {

					Message message = new Message();
					message.what = messageWhat;
					Object[] objects = { output, view };
					message.obj = objects;
					handler.sendMessage(message);

				}

				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		}).start();

		return null;
	}

}
