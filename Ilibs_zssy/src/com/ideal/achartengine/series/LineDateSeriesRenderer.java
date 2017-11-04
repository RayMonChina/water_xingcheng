package com.ideal.achartengine.series;

import java.util.Calendar;

import org.achartengine.chart.PointStyle;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.graphics.Color;

public class LineDateSeriesRenderer extends XYMultipleSeriesRenderer{
	
	
	int[] colors = new int[] { Color.BLUE, Color.GREEN, Color.MAGENTA,
			Color.YELLOW, Color.CYAN,Color.DKGRAY,Color.LTGRAY,Color.TRANSPARENT,Color.GRAY,Color.DKGRAY };
	
	
	public LineDateSeriesRenderer(String[] sub,int length){
		for (int i = 0; i < length; i++){
			XYSeriesRenderer r = new XYSeriesRenderer();
//			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(colors[i%10]);
			r.setPointStyle(PointStyle.CIRCLE);
			r.setFillPoints(true);
			this.addSeriesRenderer(r);
			
		}
		
		for(int i=0;i<sub.length;i++){
			this.addTextLabel(i, sub[i]);
		}
		
		this.setXLabelsAngle(-25);
		this.setXLabels(0);
//		this.setShowGridX(true);
//		this.setShowGridY(true);
		this.setShowLegend(true);
		this.setBackgroundColor(Color.BLACK);
		this.setExternalZoomEnabled(true);
		this.setZoomEnabled(true);
//		this.setZoomEnabled(true, true);
//		this.setZoomButtonsVisible(true);
//		this.setChartTitle("Line Chart Demo");
//		this.setXTitle("X");
//		this.setYTitle("Y");
//		this.setXAxisMin(-1);
//		this.setXAxisMax(12);
//		this.setYAxisMin(0);
//		this.setYAxisMax(35);
		this.setAxesColor(Color.WHITE);
		this.setLabelsColor(Color.WHITE);
		
	}
	
	
	public LineDateSeriesRenderer(int DateType,Calendar form,Calendar to,int length){

		for (int i = 0; i < length; i++){
			XYSeriesRenderer r = new XYSeriesRenderer();
			r.setColor(colors[i%10]);
			r.setPointStyle(PointStyle.CIRCLE);
			r.setFillPoints(true);
			this.addSeriesRenderer(r);
		}
		
		addTextLabelByDateType(DateType, form, to);
		this.setXLabelsAngle(-25);
		this.setXLabels(0);
		this.setShowGridX(true);
		this.setShowGridY(true);
		this.setShowLegend(true);
		this.setBackgroundColor(Color.BLACK);
		this.setExternalZoomEnabled(true);
		this.setZoomEnabled(true);
//		this.setZoomEnabled(true, true);
//		this.setZoomButtonsVisible(true);
//		this.setChartTitle("Line Chart Demo");
//		this.setXTitle("X");
//		this.setYTitle("Y");
//		this.setXAxisMin(-1);
//		this.setXAxisMax(12);
//		this.setYAxisMin(0);
//		this.setYAxisMax(35);
		this.setAxesColor(Color.WHITE);
		this.setLabelsColor(Color.WHITE);
	}
	
	
	
	private void addTextLabelByDateType(int DateType,Calendar form,Calendar to){
		int i = 0;
		switch(DateType){
		case 1:
			for(form.getTime().getTime();form.getTime().getTime()<to.getTime().getTime();i++){
				this.addTextLabel(i,form.get(Calendar.DATE)+"ÈÕ");
				form.add(Calendar.DATE, 1);
			}
			break;
		case 2:
			for(form.getTime().getTime();form.getTime().getTime()<to.getTime().getTime();i++){
				this.addTextLabel(i,form.get(Calendar.MONTH)+1+"ÔÂ");
				form.add(Calendar.MONTH, 1);
			}
			break;
		default:
			for(form.getTime().getTime();form.getTime().getTime()<to.getTime().getTime();i++){
				this.addTextLabel(i,form.get(Calendar.YEAR)+"Äê");
				form.add(Calendar.YEAR, 1);
			}
			break;
		}
	}
	
}
