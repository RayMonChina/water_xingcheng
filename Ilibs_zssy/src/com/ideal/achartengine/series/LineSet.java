package com.ideal.achartengine.series;

import java.util.ArrayList;
import java.util.List;

public class LineSet {
	
	private List<String> titles;
	private List<List<Double>> x;
	private List<List<Double>> y;
	public LineSet(){
		x = new ArrayList<List<Double>>();
		y = new ArrayList<List<Double>>();
	}
	
	public int size() {
		return x.size();
	}

	public void addPoint(Line line){
		this.x.add(line.getxLine());
		this.y.add(line.getyLine());
	}
	
	public void addPoint(List<Double> xLine,List<Double> yLine){
		this.x.add(xLine);
		this.y.add(yLine);
	}
	
	public void addPoint(String title,List<Double> xLine,List<Double> yLine){
		this.titles.add(title);
		this.x.add(xLine);
		this.y.add(yLine);
	}

	public List<String> getTitles() {
		return titles;
	}

	public void setTitles(List<String> titles) {
		this.titles = titles;
	}

	public List<List<Double>> getX() {
		return x;
	}

	public void setX(List<List<Double>> x) {
		this.x = x;
	}

	public List<List<Double>> getY() {
		return y;
	}

	public void setY(List<List<Double>> y) {
		this.y = y;
	}
	
	
}
