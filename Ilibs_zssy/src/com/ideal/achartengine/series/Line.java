package com.ideal.achartengine.series;

import java.util.ArrayList;
import java.util.List;

public class Line {
	
	private List<Double> xLine;
	private List<Double> yLine;

	public Line(){
		xLine = new ArrayList<Double>();
		yLine = new ArrayList<Double>();
	}
	
	public void addPoint(Double xPoint,Double yPoint){
		this.xLine.add(xPoint);
		this.yLine.add(yPoint);
	}
	
	public void addPoint(int xPoint,int yPoint){
		this.xLine.add((double)xPoint);
		this.yLine.add((double)yPoint);
	}
	
	public List<Double> getxLine() {
		return xLine;
	}

	public void setxLine(List<Double> xLine) {
		this.xLine = xLine;
	}

	public List<Double> getyLine() {
		return yLine;
	}

	public void setyLine(List<Double> yLine) {
		this.yLine = yLine;
	}
	
	
}
