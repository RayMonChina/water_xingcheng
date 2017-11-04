package com.ideal.achartengine.series;

import java.util.Calendar;

import org.achartengine.chart.PointStyle;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.graphics.Color;

public class PieDateRenderer extends DefaultRenderer{
	
	int[] colors = new int[] { Color.BLUE, Color.GREEN, Color.MAGENTA,
			Color.YELLOW, Color.CYAN,Color.DKGRAY,Color.LTGRAY,Color.TRANSPARENT,Color.GRAY,Color.DKGRAY };
	
	public PieDateRenderer(int length){

		this.setLabelsTextSize(15);
//		this.setLegendTextSize(15);
		this.setStartAngle(90);
//		this.setShowLabels(true);
		this.setShowLegend(false);
		this.setClickEnabled(true);
		this.setSelectableBuffer(50);
		this.setInScroll(true);
//		this.setScale(1);
//		this.setShowAxes(true);
//		renderer.setMargins(new int[] { 20, 30, 15, 10 });
		for (int i = 0;i<length;i++) {
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(colors[i%10]);
			this.addSeriesRenderer(r);
		}
	}
	
	
	
	
	
}
