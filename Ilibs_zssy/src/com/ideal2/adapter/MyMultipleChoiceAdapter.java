package com.ideal2.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.ideal2.components.MyCheckBox;
import com.ideal2.components.MyRadioButton;
import com.ideal2.components.MyTextView;
import com.ideal2.demo.R;
import com.ideal2.log.ILog;
import com.ideal2.util.ReflectUtil;

public class MyMultipleChoiceAdapter<E> extends BaseAdapter {

	private final static String TAG = "MyMultipleChoiceAdapter";
	private Context context;
	private List<E> dataScorce;
	private String showValue;
	private String showId;
	private List<String> selectedId;
	private List<Integer> selectedIndex;
	private List<String> selectedValue;
	private boolean[] selected;

	private ViewGroup parent;
	private Map ids;
	private Map values;
	private int count;
	private String[] arrIds;
	private String[] arrValues;
	private int length;

	public MyMultipleChoiceAdapter() {
	}

	public MyMultipleChoiceAdapter(Context context, String[] arrIds,
			String[] arrValues, String showId, String showValue) {
		setData(context, dataScorce, showId, showValue);
	}

	public MyMultipleChoiceAdapter(Context context, List<E> dataScorce,
			String showId, String showValue) {
		setData(context, dataScorce, showId, showValue);
	}

	@Override
	public int getCount() {
		if (null == dataScorce) {
			return 0;
		}
		return dataScorce.size();
	}

	@Override
	public E getItem(int position) {
		if (null == dataScorce) {
			return null;
		}
		return dataScorce.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView,
			final ViewGroup parent) {
		this.parent = parent;
		View v;
		MyTextView text;
		RadioGroup radioGroup;
		final MyCheckBox radio;
		if (null == convertView) {
			radioGroup = new RadioGroup(context);
			LinearLayout ll = (LinearLayout) LinearLayout.inflate(context,
					R.layout.my_multiplechoice, null);
			text = (MyTextView) ll.findViewById(R.id.text_value);
			radio = (MyCheckBox) ll.findViewById(R.id.check_select);
			v = ll;
		} else {
			v = convertView;
			text = (MyTextView) v.findViewById(R.id.text_value);
			radio = (MyCheckBox) v.findViewById(R.id.check_select);
		}

		// v.setClickable(true);
		// v.setLongClickable(true);
		v.setTag(position);
		radio.setClickable(false);
		radio.setFocusable(false);
		v.setFocusable(false);
		v.setClickable(false);

		if (selected[position]) {
			try {
				MyCheckBox checkOld = (MyCheckBox) v
						.findViewById(R.id.check_select);
				checkOld.setChecked(true);

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			MyCheckBox checkOld = (MyCheckBox) v
					.findViewById(R.id.check_select);
			checkOld.setChecked(false);
		}

		try {
			if ("".equals(showValue) || "string".equalsIgnoreCase(showValue)) {
				text.setText(dataScorce.get(position).toString());
			} else {
				text.setText(arrValues[position]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return v;
	}

	public List<String> getSelectedId() {
		this.selectedId.clear();
		try {
			for (int i = 0; i < this.selected.length; i++) {
				if (this.selected[i]) {
					this.selectedId.add(ReflectUtil.getter(dataScorce.get(i),
							showId).toString());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.selectedId;
	}

	public void setSelectedId(List<String> selectedId) {
		try {
			for (int i = 0; i < selectedId.size(); i++) {
				this.selected[Integer.parseInt(ids.get(selectedId.get(i))
						.toString())] = true;
			}
			this.selectedId = selectedId;
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<Integer> getSelectedIndex() {
		this.selectedIndex.clear();
		for (int i = 0; i < this.selected.length; i++) {
			if (this.selected[i]) {
				this.selectedIndex.add(i);
			}
		}
		return this.selectedIndex;
	}

	public void setSelectedIndex(List<Integer> selectedIndex) {
		try {
			for (int i = 0; i < selectedIndex.size(); i++) {
				this.selected[selectedIndex.get(i)] = true;
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.selectedIndex = selectedIndex;
	}

	public List<String> getSelectedValue() {
		this.selectedValue.clear();
		try {
			for (int i = 0; i < this.selected.length; i++) {
				if (this.selected[i]) {
					if("".equals(this.showValue)||"string".equals(this.showValue)){
						this.selectedValue.add(dataScorce.get(i).toString());
					}else{
						this.selectedValue.add(ReflectUtil.getter(
								dataScorce.get(i), this.showValue).toString());
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.selectedValue;
	}

	public void setSelectedValue(List<String> selectedValue) {
		try {
			for (int i = 0; i < selectedId.size(); i++) {
				this.selected[Integer.parseInt(values.get(selectedValue.get(i))
						.toString())] = true;
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.selectedValue = selectedValue;
	}

	public void setData(Context context, String[] arrIds, String[] arrValues,
			String showId, String showValue) {
		this.selectedId = new ArrayList<String>();
		this.selectedIndex  = new ArrayList<Integer>();
		this.selectedValue  = new ArrayList<String>();
		
		this.context = context;
		this.arrIds = arrIds;
		this.arrValues = arrValues;
		this.showId = showId;
		this.showValue = showValue;
		this.length = arrValues.length;
		this.selected = new boolean[this.length];
	}

	public void setData(Context context, List<E> dataScorce, String showId,
			String showValue) {
		this.selectedId = new ArrayList<String>();
		this.selectedIndex  = new ArrayList<Integer>();
		this.selectedValue  = new ArrayList<String>();
		
		this.context = context;
		this.dataScorce = dataScorce;
		this.showId = showId;
		this.showValue = showValue;
		this.length = this.dataScorce.size();
		this.arrIds = new String[this.length];
		this.arrValues = new String[this.length];
		this.selected = new boolean[this.length];

		ids = new HashMap();
		values = new HashMap();

		for (int i = 0; i < dataScorce.size(); i++) {
			try {
				if (null != showId && !"".equals(showId)) {
					arrIds[i] = ReflectUtil.getter(dataScorce.get(i), showId)
							.toString();
					ids.put(arrIds[i], i);
				}
				if ("".equals(showValue)
						|| "string".equalsIgnoreCase(showValue)) {
					arrValues[i] = dataScorce.get(i).toString();
					values.put(arrValues[i], i);
				} else {
					arrValues[i] = ReflectUtil.getter(dataScorce.get(i),
							showValue).toString();
					values.put(arrValues[i], i);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void refresh(){
//		ILog.d(TAG, "this.parent.getChildCount():"+this.parent.getChildCount());
		for(int i =0;i<this.parent.getChildCount();i++){
			MyCheckBox checkOld;
			try {
				checkOld = (MyCheckBox)this.parent.getChildAt(i).findViewById(R.id.check_select);
				checkOld.setChecked(selected[Integer.parseInt(this.parent.getChildAt(i).getTag().toString())]);
//				ILog.d(TAG,  this.parent.getChildAt(i).getTag().toString());
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public void invert(){
		for(int i = 0;i<this.selected.length;i++){
			selected[i] = !selected[i];
		}
	}
	
	
	public void changeSelected(int index){
		this.selected[index] = !this.selected[index];
		try {
			for(int i =0;i<this.parent.getChildCount();i++){
				if(((Integer)this.parent.getChildAt(i).getTag())==index){
					MyCheckBox checkOld = (MyCheckBox)this.parent.getChildAt(i).findViewById(R.id.check_select);
					checkOld.setChecked(this.selected[index]);
			    	break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void changeIndex(int[] index,boolean result){
		for(int j = 0;j<index.length;j++){
			this.selected[index[j]] =result;
			try {
				for(int i =0;i<this.parent.getChildCount();i++){
					if(((Integer)this.parent.getChildAt(i).getTag())==index[j]){
						MyCheckBox checkOld = (MyCheckBox)this.parent.getChildAt(i).findViewById(R.id.check_select);
						checkOld.setChecked(result);
				    	break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void clear() {
		this.selectedId.clear();
		this.selectedIndex.clear();
		this.selectedValue.clear();
		this.selected = new boolean[this.length];
		for(int i =0;i<this.parent.getChildCount();i++){
			MyCheckBox checkOld = (MyCheckBox)this.parent.getChildAt(i).findViewById(R.id.check_select);
			checkOld.setChecked(false);
		}
	}

	public void clearAll() {
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
		this.selected = null;
	}

	public void clearDate() {
		clear();
		this.ids = null;
		this.values = null;
		this.dataScorce = null;
		this.parent = null;

		this.length = 0;
		this.arrIds = null;
		this.arrValues = null;
		this.selected = null;
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

	public Map getIds() {
		return ids;
	}

	public void setIds(Map ids) {
		this.ids = ids;
	}

	public Map getValues() {
		return values;
	}

	public void setValues(Map values) {
		this.values = values;
	}

	public boolean[] getSelected() {
		return selected;
	}

	public void setSelected(boolean[] selected) {
		this.selected = selected;
	}
	
}
