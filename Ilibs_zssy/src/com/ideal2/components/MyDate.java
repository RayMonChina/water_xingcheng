/*package com.ideal2.components;

import java.sql.Date;
import java.util.Calendar;

import com.ideal2.demo.R;
import com.ideal2.log.ILog;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelClickedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import kankan.wheel.widget.adapters.NumericWheelAdapter;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

*//**
 * 日期控件
 * 
 * @author xufeng
 * 
 *//*
public class MyDate extends MyTextView {

	private final static String TAG = "MyDate";
	private MyDialog myDialog;
	private LinearLayout ll;
	private static PopupWindow m_popupwindow;
	private WheelView wv_month;
	private WheelView wv_year;
	private WheelView wv_day;
	private Calendar curCalendar;
	private int year;
	private int month;
	private int day;
	private String time;

	public MyDate(Context context, AttributeSet attrs) {
		super(context, attrs);
		// myDialog = new MyDialog(getContext(), R.style.proDialog);
		ll = (LinearLayout) LinearLayout.inflate(context, R.layout.my_date,
				null);

		curCalendar = Calendar.getInstance();
		year = curCalendar.get(Calendar.YEAR);
		month = curCalendar.get(Calendar.MONTH);
		day = curCalendar.get(Calendar.DAY_OF_MONTH);

		wv_month = (WheelView) ll.findViewById(R.id.month);
		wv_year = (WheelView) ll.findViewById(R.id.year);
		wv_day = (WheelView) ll.findViewById(R.id.day);

		OnWheelChangedListener listener = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				updateDays(wv_year, wv_month, wv_day);
				ILog.d(TAG, "oldValue:" + oldValue + "newValue:" + newValue);
			}
		};

		
		final String months[] = new String[] { "一月", "二月", "三月", "四月", "五月",
				"六月", "七月", "八月", "九月", "十月", "十一月", "十二月" };
		wv_month.setViewAdapter(new DateArrayAdapter(context, months, month));
		wv_month.setCurrentItem(month);
	
		wv_year.setViewAdapter(new DateNumericAdapter(context, 1990, year + 10,
				year - 1990));
		wv_year.setCurrentItem(year - 1990);
		
		wv_day.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView arg0, int arg1, int arg2) {
				day = arg2 + 1;
				curCalendar.set(Calendar.DAY_OF_MONTH, day);
				refreshText();
			}
		});
		wv_year.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView arg0, int arg1, int arg2) {
				year = 1990 + arg2;
				curCalendar.set(Calendar.YEAR, year);
				calDay();
				refreshText();

			}
		});
		wv_month.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView arg0, int arg1, int arg2) {
				month = arg2;
				curCalendar.set(Calendar.MONTH, month);
				calDay();
				refreshText();
			}
		});
		calDay();
		refreshText();

		Button btn_ok = (Button) ll.findViewById(R.id.btn_ok);
		btn_ok.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (m_popupwindow.isShowing()) {
					m_popupwindow.dismiss();
				}

			}
		});
		// updateDays(wv_year, wv_month, wv_day);
		// myDialog.setContentView(ll);
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public String getTime() {
		
		return time;
	}
	*//**
	 * 
	 * @param time yyyy-MM-dd
	 *//*
	public void setTime(String time) {
		String[] ts = time.split("-");
		this.year = Integer.parseInt(ts[0]);
		this.month = Integer.parseInt(ts[1])-1;
		this.day = Integer.parseInt(ts[2]);
		curCalendar.set(Calendar.YEAR, this.year);
		curCalendar.set(Calendar.MONTH, this.month);
		curCalendar.set(Calendar.DAY_OF_MONTH, this.day);
		this.time = time;
		refreshText();
	}

	public void calDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		int curDay = Math.min(maxDays, day);
		wv_day.setViewAdapter(new DateNumericAdapter(getContext(), 1, maxDays,
				curDay - 1));
		wv_day.setCurrentItem(curDay - 1, false);
		// curCalendar.set(Calendar.DAY_OF_MONTH, curDay);
	}

	public void refreshText() {
		time = curCalendar.get(Calendar.YEAR) + "-"
				+ (curCalendar.get(Calendar.MONTH) + 1) + "-"
				+ curCalendar.get(Calendar.DAY_OF_MONTH);
		setText(time);
	}

	*//**
	 * 单击显示dialog
	 * 
	 * @param result
	 *            true 显示 false 不显示
	 *//*
	public void setOnClickShow(boolean result) {
		if (result) {
			setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					show();
				}
			});
		} else {
			setOnClickListener(null);
		}
	}

	*//**
	 * 显示dialog
	 *//*
	public void show() {
		try {
			if (m_popupwindow == null) {
				m_popupwindow = new PopupWindow(ll, LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT);// LayoutParams.WRAP_CONTENT);

				// 点击PopUpWindow外面的控件也可以使得PopUpWindow dimiss。
				// 需要顺利让PopUpWindow dimiss；PopUpWindow的背景不能为空。
				m_popupwindow.setBackgroundDrawable(new BitmapDrawable());

				// 获得焦点，并且在调用setFocusable（true）方法后，可以通过Back(返回)菜单使PopUpWindow
				// dimiss
				// pop.setFocusable(true)
				m_popupwindow.setFocusable(true);
				m_popupwindow.setOutsideTouchable(true);
				// m_popupwindow.showAsDropDown(MyDate.this, 0, 0);
				m_popupwindow.showAtLocation(MyDate.this, Gravity.CENTER, 0, 0);
			} else if (m_popupwindow.isShowing()) {
				m_popupwindow.dismiss();
			} else {
				m_popupwindow.showAtLocation(MyDate.this, Gravity.CENTER, 0, 0);
			}
			// myDialog.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	*//**
	 * Updates day wheel. Sets max days according to selected month and year
	 *//*

	void updateDays(WheelView year, WheelView month, WheelView day) {
		// curCalendar = Calendar.getInstance();
		curCalendar.set(Calendar.YEAR, 1990 + wv_year.getCurrentItem());
		curCalendar.set(Calendar.MONTH, wv_month.getCurrentItem());

		int maxDays = curCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		int curDay = Math.min(maxDays, wv_day.getCurrentItem() + 1);
		wv_day.setViewAdapter(new DateNumericAdapter(getContext(), 1, maxDays,
				8));

		wv_day.setCurrentItem(curDay - 1);
		curCalendar.set(Calendar.DAY_OF_MONTH, curDay);
		// wv_day.setCurrentItem(curDay-1);
		setText(curCalendar.get(Calendar.YEAR) + "-"
				+ (curCalendar.get(Calendar.MONTH) + 1) + "-"
				+ curCalendar.get(Calendar.DAY_OF_MONTH));
	}

	*//**
	 * Adapter for numeric wheels. Highlights the current value.
	 *//*
	private class DateNumericAdapter extends NumericWheelAdapter {
		// Index of current item
		int currentItem;
		// Index of item to be highlighted
		int currentValue;

		*//**
		 * Constructor
		 *//*
		public DateNumericAdapter(Context context, int minValue, int maxValue,
				int current) {
			super(context, minValue, maxValue);
			this.currentValue = current;
			setTextSize(16);
		}

		@Override
		protected void configureTextView(TextView view) {
			super.configureTextView(view);
			if (currentItem == currentValue) {
				view.setTextColor(0xFF0000F0);
			}
			view.setTypeface(Typeface.SANS_SERIF);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			currentItem = index;
			return super.getItem(index, cachedView, parent);
		}
	}

	*//**
	 * Adapter for string based wheel. Highlights the current value.
	 *//*
	private class DateArrayAdapter extends ArrayWheelAdapter<String> {
		// Index of current item
		int currentItem;
		// Index of item to be highlighted
		int currentValue;

		*//**
		 * Constructor
		 *//*
		public DateArrayAdapter(Context context, String[] items, int current) {
			super(context, items);
			this.currentValue = current;
			setTextSize(16);
		}

		@Override
		protected void configureTextView(TextView view) {
			super.configureTextView(view);
			if (currentItem == currentValue) {
				view.setTextColor(0xFF0000F0);
			}
			view.setTypeface(Typeface.SANS_SERIF);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			currentItem = index;
			return super.getItem(index, cachedView, parent);
		}
	}

}
*/