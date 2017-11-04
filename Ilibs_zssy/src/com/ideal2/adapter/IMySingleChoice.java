package com.ideal2.adapter;

import java.util.List;

import android.content.Context;

public interface IMySingleChoice <E>{

	public void setData(Context context, List<E> dataScorce, String showId,
			String showValue);

	public String getSelectedId();

	public void setSelectedId(String selectedId);

	public int getSelectedIndex();

	public void setSelectedIndex(int selectedIndex);

	public String getSelectedValue();

	public void setSelectedValue(String selectedValue);


}
