package com.ideal2.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.ideal2.components.MyRadioButton;
import com.ideal2.components.MyTextView;
import com.ideal2.demo.R;
import com.ideal2.util.ReflectUtil;

public class MySingleChoiceAdapter <E> extends BaseAdapter implements IMySingleChoice<E>{

	
	private Context context;
	private List<E> dataScorce;
	private String showValue;
	private String showId;
	private String selectedId;
	private int selectedIndex;
	private String selectedValue;
	private ViewGroup parent;
	private Map ids;
	private Map values;
	private int count;
	private String[] arrIds;
	private String[] arrValues;
	private int length;
	
	public MySingleChoiceAdapter(){}
	public MySingleChoiceAdapter(Context context){
		this.context = context;
	}
	
	public MySingleChoiceAdapter(Context context,String[] arrIds,String[] arrValues,String showId,String showValue){
		setData(context, dataScorce, showId, showValue);
	}
	
	public MySingleChoiceAdapter(Context context,List<E> dataScorce,String showId,String showValue){
		setData(context, dataScorce, showId, showValue);
	}
	
	@Override
	public int getCount() {
		if(null==dataScorce){
			return 0;
		}
		return dataScorce.size();
	}

	@Override
	public E getItem(int position) {
		if(null==dataScorce){
			return null;
		}
		return dataScorce.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(final int position, View convertView,final ViewGroup parent) {
		this.parent = parent;
		View v;
		MyTextView text;
//		RadioGroup radioGroup;
		final MyRadioButton radio;
		if(null==convertView){
//			radioGroup = new RadioGroup(context);
			LinearLayout ll = (LinearLayout)LinearLayout.inflate(context, R.layout.my_singlechoice, null);
			text = (MyTextView)ll.findViewById(R.id.text_value);
			radio = (MyRadioButton)ll.findViewById(R.id.radio_select);
			v =ll;
		}else{
			v = convertView;
			text = (MyTextView)v.findViewById(R.id.text_value);
			radio = (MyRadioButton)v.findViewById(R.id.radio_select);
		}
		
//		v.setClickable(true);
//	    v.setLongClickable(true);
	    v.setTag(position);
	    radio.setClickable(false);
	    radio.setFocusable(false);
	    v.setFocusable(false);
	    v.setClickable(false);
	    
	    if(position==selectedIndex){
	    	try {
				MyRadioButton radioOld = (MyRadioButton)v.findViewById(R.id.radio_select);
				radioOld.setChecked(true);
				this.selectedId = arrIds[selectedIndex]==null?"":arrIds[selectedIndex];
				this.selectedValue = arrValues[selectedIndex]==null?"":arrValues[selectedIndex];
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }else{
	    	MyRadioButton radioOld = (MyRadioButton)v.findViewById(R.id.radio_select);
	    	radioOld.setChecked(false);
	    	
	    }
	    
	    try {
			if("".equals(showValue)||"string".equalsIgnoreCase(showValue)){
				text.setText(dataScorce.get(position).toString());
			}else{
				text.setText(arrValues[position]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		v.setOnClickListener(new View.OnClickListener() {
//			private int pos;
//			public View.OnClickListener setPosition(int p){
//				this.pos = p;
//				return this;
//			} 
//			
//			@Override
//			public void onClick(View v) {
//				selectedIndex = pos;
//				indexChange(selectedIndex);
//				
//				if(null!=onSelectedItemListenner){
//					onSelectedItemListenner.onSelectedItem(v, pos, dataScorce.get(pos));
//				}
//			}
//		}.setPosition(position));
		
		return v;
	}
	
	
	public void indexChange(int newSelectedIndex){
		try {
//			if(null==this.parent){
//				return;
//			}
			for(int i =0;i<this.parent.getChildCount();i++){
				if(((Integer)this.parent.getChildAt(i).getTag())==newSelectedIndex){
					MyRadioButton radioOld = (MyRadioButton)this.parent.getChildAt(i).findViewById(R.id.radio_select);
			    	radioOld.setChecked(true);
			    	selectedId =arrIds[newSelectedIndex]==null?"":arrIds[newSelectedIndex];
					selectedValue = arrValues[newSelectedIndex]==null?"":arrValues[newSelectedIndex];
				}else{
					MyRadioButton radioOld = (MyRadioButton)this.parent.getChildAt(i).findViewById(R.id.radio_select);
			    	radioOld.setChecked(false);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public String getSelectedId() {
		return selectedId;
	}

	public void setSelectedId(String selectedId) {
		try {
			if(null==selectedId||"".equals(selectedId)){
				setSelectedIndex(-1);
			}else{
				int selectedIndex = Integer.parseInt(ids.get(selectedId).toString());
				setSelectedIndex(selectedIndex);
			}
			this.selectedId = selectedId;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getSelectedIndex() {
		return selectedIndex;
	}

	public void setSelectedIndex(int selectedIndex) {
		indexChange(selectedIndex);
		this.selectedIndex = selectedIndex;
	}

	public String getSelectedValue() {
		return selectedValue;
	}

	public void setSelectedValue(String selectedValue) {
		try {
			if(null==selectedValue||"".equals(selectedValue)){
				setSelectedIndex(-1);
			}else{
				int selectedIndex = Integer.parseInt(values.get(selectedValue).toString());
				setSelectedIndex(selectedIndex);
			}
			this.selectedValue = selectedValue;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void setData(Context context,String[] arrIds,String[] arrValues,String showId,String showValue){
		this.context = context;
		this.arrIds = arrIds;
		this.arrValues = arrValues;
		this.showId = showId;
		this.showValue = showValue;
		this.length = arrValues.length;
	}

	@Override
	public void setData(Context context, List<E> dataScorce, String showId,
			String showValue) {
		this.context = context;
		this.dataScorce = dataScorce;
		this.showId = showId;
		this.showValue = showValue;
		this.selectedIndex = -1;
		this.length = this.dataScorce.size();
		this.arrIds = new String[this.length];
		this.arrValues = new String[this.length];
		
		ids = new HashMap();
		values = new HashMap();
		
		for(int i=0;i<dataScorce.size();i++){
			try {
				if(null!=showId&&!"".equals(showId)){
					arrIds[i] = ReflectUtil.getter(dataScorce.get(i), showId).toString();
					ids.put(arrIds[i], i);
				}
				if("".equals(showValue)||"string".equalsIgnoreCase(showValue)){
					arrValues[i] = dataScorce.get(i).toString();
					values.put(arrValues[i], i);
				}else{
					arrValues[i] = ReflectUtil.getter(dataScorce.get(i), showValue).toString();
					values.put(arrValues[i],i);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void clear(){
		this.selectedId = "";
		this.selectedIndex = -1;
		this.selectedValue = "";
		
		setSelectedIndex(selectedIndex);
	}
	
	public void clearAll(){
		clear();
		this.ids = null;
		this.values = null;
		this.dataScorce = null;
		this.parent = null;
		this.showId = null;
		this.showValue = null;
		
		this.length = 0;
		this.arrIds = null;
		this.arrValues = null;
	}
	
	public void clearDate(){
		clear();
		this.ids = null;
		this.values = null;
		this.dataScorce = null;
		this.parent = null;
	
		this.length = 0;
		this.arrIds = null;
		this.arrValues = null;
	}

	public List<E> getDataScorce() {
		return dataScorce;
	}

	public void setDataScorce(List<E> dataScorce) {
		clearDate();
		this.dataScorce = dataScorce;
		setData(context, dataScorce, showId, showValue);
	}

	public String[] getArrIds() {
		return arrIds;
	}

	public void setArrIds(String[] arrIds) {
		this.arrIds = arrIds;
	}

	public String[] getArrValues() {
		return arrValues;
	}

	public void setArrValues(String[] arrValues) {
		this.arrValues = arrValues;
	}

	public String getShowValue() {
		return showValue;
	}

	public void setShowValue(String showValue) {
		this.showValue = showValue;
	}

	public String getShowId() {
		return showId;
	}

	public void setShowId(String showId) {
		this.showId = showId;
	}
	
	

}
