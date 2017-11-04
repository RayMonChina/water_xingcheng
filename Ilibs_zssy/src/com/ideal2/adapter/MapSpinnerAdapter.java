package com.ideal2.adapter;

import java.lang.reflect.Method;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MapSpinnerAdapter extends BaseAdapter{

	private Context Context;
	private List<Object> list;
	private Spinner spinner;
	private String id;
	private String value;

	public MapSpinnerAdapter(Context context,Spinner spinner,List<Object> list,String id,String value){
		this.Context = context;
		this.list = list;
		this.spinner = spinner;
		this.id = id;
		this.value = value;
	}
	
	
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView text = new TextView(getContext());
		text.setText(this.getter(list.get(position), value));
		text.setTextSize(20);
		spinner.setTag(this.getter(list.get(position), id));
		return text;
	}
	
	public static String getter(Object obj, String att) {
		try {
			att = att.substring(0, 1).toUpperCase()+att.substring(1);
			Method method = obj.getClass().getMethod("get" + att);
			String str = (String) method.invoke(obj);
			return str;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public Context getContext() {
		return Context;
	}

	public void setContext(Context context) {
		Context = context;
	}


}
