package com.ideal.zsyy.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.Button;
import android.widget.DatePicker;

public class DiyDatePickerDialog {
	private Button btnDeptTime;
	private Context context;
	private AlertDialog dialog = null;
	private String title = "设置日期";

	public DiyDatePickerDialog(final Button btnDeptTime, Context context,String title) {
		this.btnDeptTime = btnDeptTime;
		if (title!=null&&!title.equals("")) {
			this.title = title;
		}
		this.context = context;
	}

	public void show() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(System.currentTimeMillis());
		String dateStr = sdf.format(date);
//		editText.setText(dateStr);

		String[] dates = dateStr.split("-");
		int year = Integer.parseInt(dates[0]);
		int month = Integer.parseInt(dates[1]) - 1;
		int day = Integer.parseInt(dates[2]);

		DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker datePicker, int year, int month,
					int dayOfMonth) {
				String dateStr = year + "-" + (month + 1) + "-" + dayOfMonth;
				SimpleDateFormat sdf = new SimpleDateFormat("MM-dd E");
				Date date = null;
				try {
					date = new SimpleDateFormat("yyyy-M-d").parse(dateStr);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				btnDeptTime.setText("预约时间 "+sdf.format(date));
			}
		};

		dialog = new DatePickerDialog(context, dateListener, year, month,
				day);
		dialog.setTitle(title);
		dialog.show();
	}
}
