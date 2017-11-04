package com.ideal.achartengine.app;

import java.util.List;

import org.achartengine.GraphicalView;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;


import com.ideal.achartengine.services.BloodChartService;
import com.ideal2.demo.R;

public class BloodChartDialog extends Dialog{

	private LinearLayout ll;
	private GraphicalView graphicalView;
	private BloodChartService medicalChartService;
	private Dialog dialog;
	private List list;
	private String subName;
	private String hbpName;
	private int layout;
	private Context context;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		dialog = new Dialog(context,R.style.Theme_blooddialog);
//		ll = (LinearLayout) LinearLayout.inflate(context,layout, null);
//		dialog.setContentView(layout);
		
		setContentView(layout);
	
		medicalChartService = new BloodChartService();
		

//		LinearLayout liner = (LinearLayout) ll.findViewById(R.id.linerChart);
		LinearLayout liner = (LinearLayout) findViewById(R.id.linerChart);
		
		graphicalView = medicalChartService.getMedicalLineChart(context, list, subName, hbpName);
		liner.setBackgroundColor(Color.BLACK);
		liner.addView(graphicalView);
		
		
//		Button btn_close = (Button)ll.findViewById(R.id.btn_close);
		Button btn_close = (Button)findViewById(R.id.btn_close);
		btn_close.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				dialog.dismiss();
				dismiss();
			}
		});
		
		WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
		layoutParams.width = WindowManager.LayoutParams.FILL_PARENT;
		layoutParams.height = WindowManager.LayoutParams.FILL_PARENT;
		getWindow().setAttributes(layoutParams);
	}


	public BloodChartDialog(Context context,int layout,List list,String subName,String hbpName) {
		super(context,R.style.Theme_blooddialog);
		this.context = context;
		this.layout  = layout;
		this.list = list;
		this.subName = subName;
		this.hbpName = hbpName;
	}
	
	
	public BloodChartDialog(Context context,List list,String subName,String hbpName) {
		this(context, R.layout.bloodchart, list, subName, hbpName);
	}
	
	
	
//	public void show(){
//		WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
//		layoutParams.width = WindowManager.LayoutParams.FILL_PARENT;
//		layoutParams.height = WindowManager.LayoutParams.FILL_PARENT;
//		dialog.getWindow().setAttributes(layoutParams);
//		dialog.show();
//	}

}
