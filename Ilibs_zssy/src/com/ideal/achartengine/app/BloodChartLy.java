package com.ideal.achartengine.app;

import java.util.List;

import org.achartengine.GraphicalView;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;


import com.ideal.achartengine.services.BloodChartRHService;
import com.ideal.achartengine.services.BloodChartService;

public class BloodChartLy extends LinearLayout{

	private LinearLayout ll;
	private GraphicalView graphicalView;
	private BloodChartRHService bloodChartRHService;
	private Dialog dialog;
	private List list;
	private String subName;
	private String hbpName;
	private int layout;
	private Context context;

	
	
	public BloodChartLy(Context context) {
		super(context, null);
		bloodChartRHService = new BloodChartRHService();
	
		LinearLayout liner = new LinearLayout(context);
		
//		graphicalView = bloodChartRHService.getMedicalLineChart(context, list, subName, hbpName);
		liner.setBackgroundColor(Color.BLACK);
		liner.addView(graphicalView);
		
		addView(liner);
		

	}



}
