package com.ideal.achartengine.series;

import java.util.ArrayList;
import java.util.List;

public class PieSet {
	
	private List<String> names;
	private List<Double> values;
	
	
	public PieSet(){
		this.names = new ArrayList<String>();
		this.values = new ArrayList<Double>();
	}
	
	public int size(){
		return values.size();
	}
	
	public void add(String name,Double value){
		this.names.add(name);
		this.values.add(value);
	}

	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public List<Double> getValues() {
		return values;
	}

	public void setValues(List<Double> values) {
		this.values = values;
	}
	
	
	
	
}
