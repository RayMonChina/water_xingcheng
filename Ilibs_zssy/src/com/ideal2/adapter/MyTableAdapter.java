package com.ideal2.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ideal2.base.Reflection;
import com.ideal2.demo.R;

public class MyTableAdapter extends BaseAdapter{

	private final static String TAG = "MyTableAdapter";
	
	private Context context;
	private String[] header;
	private String[] showValues;
	private List data;
	private LinearLayout ll ;
	public MyTableAdapter(Context context,List data, String[] header,String[] showValues){
		this.context = context;
		this.data = data;
		this.header = header;
		this.showValues = showValues;
	}
	
	@Override
	public int getCount() {

		return showValues.length+1;
	}

	@Override
	public Object getItem(int position) {
		if(position==0){
			return header;
		}
		return data.get(position-1);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v;
		
		  if (convertView == null) {
				LinearLayout ll = (LinearLayout)LinearLayout.inflate(context, R.layout.my_table, null);
				TextView text1 = (TextView)ll.findViewById(R.id.table_text1);
				TextView text2 = (TextView)ll.findViewById(R.id.table_text2);
				TextView text3 = (TextView)ll.findViewById(R.id.table_text3);
				TextView text4 = (TextView)ll.findViewById(R.id.table_text4);
				TextView text5 = (TextView)ll.findViewById(R.id.table_text5);
				TextView text6 = (TextView)ll.findViewById(R.id.table_text6);
				TextView text7 = (TextView)ll.findViewById(R.id.table_text7);
				
				if(null!=showValues){
					switch(showValues.length){
					case 0:
						text1.setVisibility(View.GONE);
					case 1:
						text2.setVisibility(View.GONE);
					case 2:
						text3.setVisibility(View.GONE);
					case 3:
						text4.setVisibility(View.GONE);
					case 4:
						text5.setVisibility(View.GONE);
					case 5:
						text6.setVisibility(View.GONE);
					case 6:
						text7.setVisibility(View.GONE);
					}
				}
		        v = ll;
		        v.setClickable(true);
		        v.setLongClickable(true);
	        } else {
	            v = convertView;
	        }
		
		  
		  if(position == 0){
			  v.setBackgroundResource(R.color.beige);
			  for(int i = 0;i<showValues.length;i++){
				  TextView text = null;
				  switch(i){
				  case 0:
					  text = (TextView)v.findViewById(R.id.table_text1);
					  break;
				  case 1:
					  text = (TextView)v.findViewById(R.id.table_text2);
					  break;
				  case 2:
					  text = (TextView)v.findViewById(R.id.table_text3);
					  break;
				  case 3:
					  text = (TextView)v.findViewById(R.id.table_text4);
					  break;
				  case 4:
					  text = (TextView)v.findViewById(R.id.table_text5);
					  break;
				  case 5:
					  text = (TextView)v.findViewById(R.id.table_text6);
					  break;
				  case 6:
					  text = (TextView)v.findViewById(R.id.table_text7);
					  break;
				  }
				  text.setText(header[i]);
			  }
		  }else{
			  v.setBackgroundResource(R.color.white);
			  Object obj = data.get(position-1);
			  for(int i = 0;i<showValues.length;i++){
				  TextView text = null;
				  switch(i){
				  case 0:
					  text = (TextView)v.findViewById(R.id.table_text1);
					  break;
				  case 1:
					  text = (TextView)v.findViewById(R.id.table_text2);
					  break;
				  case 2:
					  text = (TextView)v.findViewById(R.id.table_text3);
					  break;
				  case 3:
					  text = (TextView)v.findViewById(R.id.table_text4);
					  break;
				  case 4:
					  text = (TextView)v.findViewById(R.id.table_text5);
					  break;
				  case 5:
					  text = (TextView)v.findViewById(R.id.table_text6);
					  break;
				  case 6:
					  text = (TextView)v.findViewById(R.id.table_text7);
					  break;
				  }
				  text.setText(Reflection.getter(obj,showValues[i])+"");
			  }
		  }
		return v;
	}
	
	
	
}
