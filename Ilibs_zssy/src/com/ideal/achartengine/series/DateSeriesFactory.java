package com.ideal.achartengine.series;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import android.content.Context;

public class DateSeriesFactory {
	
	
	public GraphicalView createLineDateView(Context context,LineSet lineSet,int dateType,Calendar form,Calendar to){
		XYMultipleSeriesDataset dataset = null;
		XYMultipleSeriesRenderer renderer = null;
		GraphicalView graphicalView = null;
		int length = 2;
		
		String[] titles = new String[] { "第一项", "第二项"};
		List x = new ArrayList();
		List y = new ArrayList();
		
		x.add(new double[] { 0, 1, 2, 3, 4, 5 });
		y.add(new double[] { 300, 140, 500, 210, 210, 250 });

		x.add(new double[] { 0, 1, 2, 3, 4, 5 });
		y.add(new double[] { 11, 2, 3, 23, 21, 6 });
		

		dataset = new LineDateSeriesDatase(titles, x, y,length);
		renderer = new LineDateSeriesRenderer(dateType,form,to,length);
		
		return ChartFactory.getLineChartView(context, dataset, renderer);
	}
	
	public GraphicalView createPieDateView(Context context){
		DefaultRenderer mRenderer= null;
		CategorySeries mSeries = null;
		GraphicalView graphicalView = null;
		int length = 5;
		
		String[] names = new String[]{"1日","4日","23日","12日","8日"};
		double[] values = new double[] { 12, 14, 11, 10, 19,20 };
		
		mRenderer = new PieDateRenderer(length);
		mSeries = new PieDateDatase(names, values, length);
		graphicalView = ChartFactory.getPieChartView(context,mSeries,mRenderer);
		return graphicalView;
	}
	
	
	
	
	
}
