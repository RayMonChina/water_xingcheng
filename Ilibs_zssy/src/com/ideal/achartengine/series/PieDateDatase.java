package com.ideal.achartengine.series;

import java.util.ArrayList;
import java.util.List;

import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.graphics.Color;

public class PieDateDatase extends CategorySeries{

	public PieDateDatase(String[] names,double[] values,int length) {
		super("Բ");
		for (int i = 0;i<length;i++) {
			this.add(names[i], values[i]);
		}
	}
	
	public PieDateDatase(PieSet pieSet) {
		super("Բ");
		for (int i = 0;i<pieSet.size();i++) {
			this.add(pieSet.getNames().get(i), pieSet.getValues().get(i));
		}
	}
}
