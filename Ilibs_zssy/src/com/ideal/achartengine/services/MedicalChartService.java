package com.ideal.achartengine.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import android.R;
import android.content.Context;
import android.util.Log;

import com.ideal.achartengine.series.Line;
import com.ideal.achartengine.series.LineDateSeriesDatase;
import com.ideal.achartengine.series.LineDateSeriesRenderer;
import com.ideal.achartengine.series.LineSet;
import com.ideal.achartengine.series.PieDateDatase;
import com.ideal.achartengine.series.PieDateRenderer;
import com.ideal.achartengine.series.PieSet;



public class MedicalChartService {
	
	
	public GraphicalView getMedicalLineChart(Context context,String[] sub){
		XYMultipleSeriesDataset dataset = null;
		XYMultipleSeriesRenderer renderer = null;
		GraphicalView graphicalView = null;
		int length = 2;
		
		LineSet lineSet = new LineSet();
		for(int j = 0;j<2;j++){
			Line line = new Line();
			for(int i = 0;i<5;i++){
				line.addPoint(i, (int)(Math.random()*20+1));
			}
			lineSet.addPoint(line);
		}
		List titles = new ArrayList();
		titles.add("ÊÕËõÑ¹");
		titles.add("ÊæÕÅÑ¹");
		lineSet.setTitles(titles);
		dataset = new LineDateSeriesDatase(lineSet);
		renderer = new LineDateSeriesRenderer(sub,length);
		Log.d("MedicalCharService", renderer.getBackgroundColor()+"");
		renderer.setApplyBackgroundColor(true);
		renderer.setBackgroundColor(context.getResources().getColor(R.color.darker_gray));
		return ChartFactory.getLineChartView(context, dataset, renderer);
	}
	
	
	public GraphicalView getMedicalLineChart(Context context,int dateType,Calendar form,Calendar to,int type){
		XYMultipleSeriesDataset dataset = null;
		XYMultipleSeriesRenderer renderer = null;
		GraphicalView graphicalView = null;
		int length = 2;
		
		LineSet lineSet = new LineSet();
		for(int j = 0;j<2;j++){
			Line line = new Line();
			for(int i = 0;i<5;i++){
				line.addPoint(i, (int)(Math.random()*20+1));
			}
			lineSet.addPoint(line);
		}

		dataset = new LineDateSeriesDatase(lineSet);
		renderer = new LineDateSeriesRenderer(dateType,form,to,length);
		
		return ChartFactory.getLineChartView(context, dataset, renderer);
	}
	
	public GraphicalView getMedicalPieChart(Context context,int dateType,Calendar form,Calendar to,int type){
		DefaultRenderer mRenderer= null;
		CategorySeries mSeries = null;
		GraphicalView graphicalView = null;
		
		PieSet pieSet = new PieSet();
		
		mRenderer = new PieDateRenderer(pieSet.size());
		mSeries = new PieDateDatase(pieSet);
		return graphicalView = ChartFactory.getPieChartView(context,mSeries,mRenderer);
	}
	
}
