package com.ideal.achartengine.services;

import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;


import android.content.Context;
import android.graphics.Paint.Align;
import android.util.Log;


import com.ideal.achartengine.series.Line;
import com.ideal.achartengine.series.LineDateSeriesDatase;
import com.ideal.achartengine.series.LineDateSeriesRenderer;
import com.ideal.achartengine.series.LineSet;
import com.ideal2.demo.R;

public class BloodChartService {

	public GraphicalView getMedicalLineChart(Context context,List list,String subName,String hbpName){
		XYMultipleSeriesDataset dataset = null;
		XYMultipleSeriesRenderer renderer = null;
		GraphicalView graphicalView = null;
		int length = 2;
		
		LineSet lineSet = new LineSet();
//		for(int j = 0;j<2;j++){
//			Line line = new Line();
//			for(int i = 0;i<5;i++){
//				line.addPoint(i, (int)(Math.random()*20+1));
//			}
//			lineSet.addPoint(line);
//		}
		
		Line line1 = new Line();
		Line line2 = new Line();
		String[] sub = new String[list.size()];
//		for(int i=list.size()-1,j=0;i>=0&&j<list.size();i--,j++){
//			try {
//				Object hyperVisits = list.get(i);
//				String hbp = ReflectUtil.getter(hyperVisits, hbpName).toString();
//				sub[j] =  ReflectUtil.getter(hyperVisits, subName).toString();
//				String[] hbps = hbp.split("/");
//				line1.addPoint(i, Integer.parseInt(hbps[0]));
//				line2.addPoint(i, Integer.parseInt(hbps[1]));
//			} catch (NumberFormatException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		
		for(int i=list.size()-1,j=0;i>=0&&j<list.size();i--,j++){
			try {
				Object hyperVisits = list.get(i);
				String hbp = ReflectUtil.getter(hyperVisits, hbpName).toString();
				sub[j] =  ReflectUtil.getter(hyperVisits, subName).toString();
				String[] hbps = hbp.split("/");
				Log.d("hbps.size", "sub[j]:"+sub[j]+",hbp:"+hbp+",hbps:"+hbps.length);
				line1.addPoint(i, Integer.parseInt(hbps[0]));
				line2.addPoint(i, Integer.parseInt(hbps[1]));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		lineSet.addPoint(line1);
		lineSet.addPoint(line2);
		List titles = new ArrayList();
		
		titles.add("收缩压");
		titles.add("舒张压");
		lineSet.setTitles(titles);
		dataset = new LineDateSeriesDatase(lineSet);
		
		renderer = new LineDateSeriesRenderer(sub,length);
		
		
//		Log.d("MedicalCharService", renderer.getBackgroundColor()+"");
		renderer.setApplyBackgroundColor(true);
		renderer.setBackgroundColor(context.getResources().getColor(R.color.white));
		renderer.setMarginsColor(context.getResources().getColor(R.color.white));
		renderer.setAxesColor(context.getResources().getColor(R.color.black));
		renderer.setLabelsColor(context.getResources().getColor(R.color.blue));
		renderer.setXLabelsColor(context.getResources().getColor(R.color.blue));
		renderer.setYLabelsColor(0, context.getResources().getColor(R.color.blue));
		renderer.setYLabels(5);
		renderer.setLabelsTextSize(12);
		renderer.setBarSpacing(5);
		renderer.setYLabelsAlign(Align.CENTER, 0);
		renderer.setMargins(new int[] {10, 30, 15,10}); 
//		renderer.setXAxisMin(0.5);//设置X轴的最小值为0.5
//        renderer.setXAxisMax(5.5);//设置X轴的最大值为5
//        renderer.setYAxisMin(0);//设置Y轴的最小值为0
//        renderer.setYAxisMax(500);//设置Y轴最大值为500
//		renderer.setShowGrid(true);
//		renderer.setShowGridX(true);
		renderer.setShowGridY(true);
//		try {
//			int l = renderer.getSeriesRendererCount();
//			for (int i = 1; i < l; i++) {
//			  SimpleSeriesRenderer seriesRenderer = renderer.getSeriesRendererAt(i);
//			  seriesRenderer.setDisplayChartValues(true);
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		return ChartFactory.getCubeLineChartView(context, dataset, renderer, 0f);
		return ChartFactory.getLineChartView(context, dataset, renderer);
	}
	
}
