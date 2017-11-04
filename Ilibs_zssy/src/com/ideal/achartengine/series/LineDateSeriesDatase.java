package com.ideal.achartengine.series;

import java.util.ArrayList;
import java.util.List;

import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;

public class LineDateSeriesDatase extends XYMultipleSeriesDataset{
	
	
	public LineDateSeriesDatase(String[] titles,List x,List y,int length){
		
		// 有几条线
		for (int i = 0; i < length; i++){

			XYSeries series = new XYSeries(titles[i]);
			// 根据每条线的名称创建
			double[] xV = (double[]) x.get(i);
			// 获取第i条线的数据
			double[] yV = (double[]) y.get(i);
			int seriesLength = xV.length;
			// 有几个点
			for (int k = 0; k < seriesLength; k++){
				// 每条线里有几个点
				series.add(xV[k], yV[k]);
			}
			this.addSeries(series);
		}
	}
	
	public LineDateSeriesDatase(LineSet lineSet){
		
		// 有几条线
		for (int i = 0; i <lineSet.size() ; i++){
			XYSeries series = null;
			if(null!=lineSet.getTitles()){
				series = new XYSeries(lineSet.getTitles().get(i));
			}else{
				series = new XYSeries((i+1)+"");
			}
			// 根据每条线的名称创建
			List<Double> xV = lineSet.getX().get(i);
			// 获取第i条线的数据
			List<Double> yV = lineSet.getY().get(i);
			int seriesLength = xV.size();
			// 有几个点
			for (int k = 0; k < seriesLength; k++){
				// 每条线里有几个点
				series.add(xV.get(k), yV.get(k));
			}
			this.addSeries(series);
		}
	}
}
