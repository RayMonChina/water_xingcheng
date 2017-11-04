package com.ideal.zsyy.adapter;

import com.jijiang.wtapp.R;
import com.ideal.zsyy.utils.Utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/*
 * 指示图片位置
 * */
public class IndicationDotList1 extends View {

	private int count = 0;
	private int space = 8;
	private int index = 0;
	private Bitmap emptyDotImg;
	private Bitmap fullDotImg;
	private static int dotImgWidth = 0;
	private static int dotImgHeight = 0;

	public IndicationDotList1(Context context, AttributeSet attrs) {

		super(context, attrs);

		emptyDotImg = Utility.drawableToBitmap(getResources().getDrawable(
				R.drawable.empty_dot));
		fullDotImg = Utility.drawableToBitmap(getResources().getDrawable(
				R.drawable.full_dot));
		dotImgWidth = emptyDotImg.getWidth();
		dotImgHeight = emptyDotImg.getHeight();
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
		invalidate();
	}

	public void setSpace(int space) {
		this.space = space;
	}

	public void setIndex(int index) {
		this.index = index;
		invalidate();
	}

	@Override
	public void draw(Canvas canvas) {
		 int left = getWidth() / 2 - count * dotImgWidth / 2 - (count - 1) *
		 space / 2;
//		int left = (getWidth() - count * dotImgWidth - (count - 1) * space) - 10;
		int top = (getHeight() - dotImgHeight) / 2 ;
		Paint p = new Paint();
		for (int i = 0; i < count; ++i) {
			if (i == index) {
				// draw full dot
				float l = left + i * dotImgWidth + i * space;
				canvas.drawBitmap(fullDotImg, l, top, p);
			} else {
				// draw empty dot
				float l = left + i * dotImgWidth + i * space;
				canvas.drawBitmap(emptyDotImg, l, top, p);
			}
		}
	}
}
