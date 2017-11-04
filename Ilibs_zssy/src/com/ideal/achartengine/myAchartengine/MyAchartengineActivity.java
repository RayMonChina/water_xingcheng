package com.ideal.achartengine.myAchartengine;


import java.util.Calendar;
import java.util.Date;

import org.achartengine.GraphicalView;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.ideal.achartengine.services.MedicalChartService;
import com.ideal2.demo.R;

public class MyAchartengineActivity extends Activity {
	
	private LinearLayout linerChart;
	private GraphicalView graphicalView;
	private MedicalChartService medicalChartService;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_achartengine);
        
        medicalChartService = new MedicalChartService();
       
        String[] sub = new String[]{"2012-5-11","2012-5-12","2012-5-14","2012-6-11","2012-8-21"};
        
		
        linerChart = (LinearLayout) findViewById(R.id.linerChart);
        graphicalView = medicalChartService.getMedicalLineChart(MyAchartengineActivity.this, sub);
		linerChart.setBackgroundColor(Color.BLACK);
		linerChart.addView(graphicalView);
    }
}