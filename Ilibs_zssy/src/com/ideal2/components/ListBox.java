package com.ideal2.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ideal2.demo.R;

/**
 * 实现多选，单选框
 * 
 * @author Admin
 * 
 */
public class ListBox extends LinearLayout {

	private ListView mListView;
	private ArrayList<Data> datas;
	private boolean isMulSelection = true;
	private IOnSelectedCallBack callBack;
	private IOnSelectedBackCallBack backCallBack;
	private boolean hasBackValue = false;
	private int allSelect = -1;
	private int allNoSelect = -1;
	private String attr0;
	private String attr1;
	private ListAdapter adapter;
	public void setBackCallBack(IOnSelectedBackCallBack l) {
		this.backCallBack = l;
	}

	private TextView vTvSelection;
	private int mCurrentPosition;

	public void setMulSelection(boolean isMulSelection) {
		this.isMulSelection = isMulSelection;
	}

	public ListBox(Context context, String[] val) {
		super(context);
		setBackgroundColor(Color.WHITE);
		// setOrientation(HORIZONTAL);

		// this.addView(vTvSelection, 0, new
		// LayoutParams(LayoutParams.FILL_PARENT,
		// 60));

		mListView = new ListView(context);
		mListView.setCacheColorHint(Color.TRANSPARENT);
		// this.addView(mListView,1, new LayoutParams(LayoutParams.FILL_PARENT,
		// LayoutParams.WRAP_CONTENT));
		setDatas(context, val);
		vTvSelection = new TextView(context);
		this.addView(vTvSelection, 0, new LayoutParams(
				LayoutParams.FILL_PARENT, 60));
	}

	public void setDatas(Context context, String[] val) {
		datas = new ArrayList<ListBox.Data>();
		for (int i = 0; i < val.length; i++) {
			Data data = new Data();
			data.value = val[i];
			datas.add(data);
		}

		adapter = new ListAdapter(context, datas);
		mListView.setAdapter(adapter);
	}

	public void refreshSelected(String arg) {
		if (!TextUtils.isEmpty(arg)) {
			String[] selected = arg.split(",");
			Log.d("ListBox", "selected length:" + selected.length);
			hasBackValue = true;
			for (int i = 0; i < datas.size(); i++) {
				datas.get(i).isCheck = false;
				for (int j = 0; j < selected.length; j++) {
					if (selected[j].trim().equals(datas.get(i).value)) {
						datas.get(i).isCheck = true;
						break;
					}
				}
			}
			adapter.notifyDataSetChanged();
		}
		
	}

	public void setDatas(Context context,
			ArrayList<HashMap<String, String>> val, String arg) {
		HashMap<String, String> head = val.get(0);
		attr0 = head.get("attr0");
		attr1 = head.get("attr1");
		allSelect = head.get("attr2") != null && !"".equals(head.get("attr2"))? Integer.parseInt(head
				.get("attr2")) : -1;
		allNoSelect = head.get("attr3") != null && !"".equals(head.get("attr3"))? Integer.parseInt(head
				.get("attr3")) : -1;
		val.remove(0);
		datas = new ArrayList<ListBox.Data>();
		if (!TextUtils.isEmpty(arg)) {
			String[] selected = arg.split(",");
			Log.d("ListBox", "selected length:" + selected.length);
			hasBackValue = true;
			for (int i = 0; i < val.size(); i++) {
				Data data = new Data();
				data.value = val.get(i).get(attr0);
				for (int j = 0; j < selected.length; j++) {
					if (selected[j].trim().equals(data.value)) {
						data.isCheck = true;
						break;
					}
				}
				data.backValue = val.get(i).get(attr1);
				datas.add(data);
			}
		} else {
			hasBackValue = false;
			for (int i = 0; i < val.size(); i++) {
				Data data = new Data();
				data.value = val.get(i).get(attr0);
				data.backValue = val.get(i).get(attr1);
				datas.add(data);
			}
		}
		adapter = new ListAdapter(context, datas);
		mListView.setAdapter(adapter);
	}

	public ListBox(Context context) {
		super(context);
		initiative(context);

	}

	public ListBox(Context context, AttributeSet attrs) {
		super(context, attrs);
		initiative(context);
	}

	private void initiative(Context context) {
		setBackgroundColor(Color.TRANSPARENT);
		setOrientation(VERTICAL);
		vTvSelection = new TextView(context);
		vTvSelection.setSingleLine();
		vTvSelection.setEllipsize(TruncateAt.END);
		vTvSelection.setGravity(Gravity.CENTER_VERTICAL);
		vTvSelection.setTextAppearance(context, R.style.TitleStyle);
		vTvSelection.setPadding(8, 0, 0, 0);
		vTvSelection.setVisibility(GONE);
		this.addView(vTvSelection, 0, new LayoutParams(
				LayoutParams.FILL_PARENT, 40));
		mListView = new ListView(context);
		View line = new View(context);
		line.setBackgroundColor(Color.BLACK);
		line.setVisibility(GONE);
		this.addView(line, 1, new LayoutParams(LayoutParams.FILL_PARENT, 2));
		mListView.setCacheColorHint(Color.TRANSPARENT);
		this.addView(mListView, 2, new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
	}

	public void setOnSelectedListener(IOnSelectedCallBack l) {
		this.callBack = l;
	}

	static class Data {
		String value = "test";
		String backValue = "back";
		boolean isCheck = false;
	}

	private class ListAdapter extends BaseAdapter {
		private Context mContext;
		private ArrayList<Data> mDatas;

		public ListAdapter(Context context, ArrayList<Data> mDatas) {
			mContext = context;
			this.mDatas = mDatas;
		}

		@Override
		public int getCount() {

			return mDatas.size();
		}

		@Override
		public Object getItem(int arg0) {

			return mDatas.get(arg0);
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			CheckBox checkBox = new CheckBox(mContext);
			checkBox.setChecked(mDatas.get(position).isCheck);
			checkBox.setTextAppearance(mContext, R.style.TitleStyle);
			checkBox.setText(" " + mDatas.get(position).value);
			// checkBox.set
			checkBox.setButtonDrawable(R.drawable.selector_check);
			checkBox.setLayoutParams(new ListView.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				int position;

				public OnCheckedChangeListener setPostion(int position) {
					this.position = position;
					return this;
				}

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					if (position == allSelect) {
						if (isChecked) {
							for (Data data : mDatas) {
								data.isCheck = true;
							}

						}
					}
					if (position == allNoSelect) {
						if (isChecked) {
							for (Data data : mDatas) {
								data.isCheck = false;
							}

						}
						mDatas.get(position).isCheck = isChecked;
					} else if (allNoSelect != -1) {
						mDatas.get(allNoSelect).isCheck = false;
					}
					if (!isMulSelection) {
						// setSingleCheck(position);
						mDatas.get(mCurrentPosition).isCheck = false;
						mCurrentPosition = position;
					}
					mDatas.get(position).isCheck = isChecked;
					ListAdapter.this.notifyDataSetChanged();
					if (backCallBack != null) {
						backCallBack.OnSelected(getSelectedDatas());

					}
					if (hasBackValue) {
						return;
					}
					if (callBack != null) {
						if (!isMulSelection) {
							if (isChecked) {
								callBack.OnSelected(mDatas.get(position).value);
								vTvSelection.setText(mDatas.get(position).value);
							} else {
								callBack.OnSelected("");
								vTvSelection.setText("");
							}
						} else {
							callBack.OnSelected(getSelectedDatas()[0]);
							vTvSelection.setText(getSelectedDatas()[0]);
						}

					}
				}
			}.setPostion(position));

			return checkBox;
		}

	}

	private void setSingleCheck(int Position) {

		// Iterator<Data> dIterator=datas.iterator();
		// while (dIterator.hasNext()) {
		// dIterator.next().isCheck=false;
		//
		// }
		// for (Data item : datas) {
		// item.isCheck = false;
		// }
	}

	private String[] getSelectedDatas() {
		StringBuffer buffer = new StringBuffer();
		StringBuffer buffer2 = new StringBuffer();
		// for (Data item : datas) {
		// if (item.isCheck) {
		// buffer.append(item.value);
		// buffer.append("\\\\");
		// }
		// }
		Iterator<Data> dIterator = datas.iterator();
		while (dIterator.hasNext()) {
			Data itemData = dIterator.next();
			if (itemData.isCheck) {
				buffer.append(itemData.value);
				buffer2.append(itemData.backValue);
				buffer2.append("\\");
				buffer.append("\\");
			}
		}
		String relust = buffer.toString();
		String relust2 = buffer2.toString();

		if (TextUtils.isEmpty(relust)) {
			relust = "";
		} else {
			relust = relust.substring(0, relust.lastIndexOf("\\"));
		}
		if (TextUtils.isEmpty(relust2)) {
			relust2 = "";
		} else if (hasBackValue) {
			relust2 = relust2.substring(0, relust2.lastIndexOf("\\"));
		}
		return new String[] { relust, relust2 };
	}

	interface IOnSelectedCallBack {
		void OnSelected(String arg0);

	}

	public interface IOnSelectedBackCallBack {
		public void OnSelected(String[] arg0);
	}
}
