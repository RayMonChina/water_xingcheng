package com.ideal.achartengine.myAchartengine;

import java.util.List;

import org.achartengine.GraphicalView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


import com.ideal.achartengine.services.BloodChartService;
import com.ideal2.demo.R;

public class BloodChartActivity extends Activity{

	private LinearLayout ll;
	private GraphicalView graphicalView;
	private BloodChartService medicalChartService;

	public BloodChartActivity(Context context,List list,String subName,String hbpName) {
		ll = (LinearLayout) LinearLayout.inflate(context, R.layout.bloodchart, null);
		setContentView(ll);
		medicalChartService = new BloodChartService();
		
//		String[] sub = new String[] { "2012-5-11", "2012-5-12", "2012-5-14",
//				"2012-6-11", "2012-8-21" };
		LinearLayout liner = (LinearLayout) ll.findViewById(R.id.linerChart);
		
		graphicalView = medicalChartService.getMedicalLineChart(context, list, subName, hbpName);
		liner.setBackgroundColor(Color.BLACK);
		liner.addView(graphicalView);
		
		Button btn_close = (Button)ll.findViewById(R.id.btn_close);
		btn_close.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

}
