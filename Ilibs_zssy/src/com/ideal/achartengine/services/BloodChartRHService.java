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
/*
 * 健康宝血压业务
 * 包含血压，脉搏
 * */
public class BloodChartRHService {
	
	
	private String sbpName;//必填。收缩压
	private String dbpName;//必填。舒张压
	private String hrtName;//必填。脉搏
	private String detectDayName;//必填。检测日期,格式：yyyy-mm-dd 时时:分分:秒秒
	
	
	public GraphicalView getMedicalLineChart(Context context,List list,String sbpName,String dbpName,String hrtName,String detectDayName){
		this.sbpName = sbpName;
		this.dbpName = dbpName;
		this.hrtName = hrtName;
		this.detectDayName = detectDayName;
		
		XYMultipleSeriesDataset dataset = null;
		XYMultipleSeriesRenderer renderer = null;
		GraphicalView graphicalView = null;
		int length = 3;
		
		LineSet lineSet = new LineSet();
		
		Line line1 = new Line();
		Line line2 = new Line();
		Line line3 = new Line();
		String[] sub = new String[list.size()];
		
		for(int i =0;i<list.size();i++){
			try {
				Object hyperVisits = list.get(i);
				String sbp = ReflectUtil.getter(hyperVisits, sbpName).toString().trim();
				String dbp = ReflectUtil.getter(hyperVisits, dbpName).toString().trim();
				String hrt = ReflectUtil.getter(hyperVisits, hrtName).toString().trim();
				sub[i] = ReflectUtil.getter(hyperVisits, detectDayName).toString().trim();
				line1.addPoint(i, Integer.parseInt(sbp));
				line2.addPoint(i, Integer.parseInt(dbp));
				line3.addPoint(i, Integer.parseInt(hrt));
			} catch (NumberFormatException e) {
				Log.d("BloodChartRHService","数字类型转换错误。");
				e.printStackTrace();
			}
		}
		
		lineSet.addPoint(line1);
		lineSet.addPoint(line2);
		lineSet.addPoint(line3);
		List titles = new ArrayList();
		
		titles.add("收缩压");
		titles.add("舒张压");
		titles.add("心率(跳/分钟)");
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
			int l = renderer.getSeriesRendererCount();
			for (int i = 1; i < l; i++) {
			  SimpleSeriesRenderer seriesRenderer = renderer.getSeriesRendererAt(i);
			  seriesRenderer.setDisplayChartValues(true);
			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		return ChartFactory.getCubeLineChartView(context, dataset, renderer, 0f);
		return ChartFactory.getLineChartView(context, dataset, renderer);
	}
	
}
