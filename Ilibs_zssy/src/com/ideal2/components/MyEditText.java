package com.ideal2.components;

import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.BitmapDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ideal2.dao.EditTestPromptDto;
import com.ideal2.dao.EditTestPromptService;
import com.ideal2.demo.R;
import com.ideal2.log.ILog;

public class MyEditText extends EditText {

	private final static String TAG = "MyEditText";

	private View m_view;
	private ListView m_listView;
	private ListViewAdapter m_adapter_listview;
	private PopupWindow m_popupwindow;
	private Context m_context;
	private String mark;
	/*
	 * private Button m_Button; private EditText m_EditText;
	 */
	private EditTestPromptService editTestPromptService;
	private List<EditTestPromptDto> listEditTextPrompt;
	private boolean isPrompt;

	public MyEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.m_context = context;
		TypedArray a = this.m_context.obtainStyledAttributes(attrs,
				R.styleable.MyEditText);
		isPrompt = a.getBoolean(R.styleable.MyEditText_isPrompt, false);
		isPrompt(isPrompt);

	}

	public void isPrompt(boolean rusult) {
		if (rusult) {
			editTestPromptService = new EditTestPromptService(m_context);

			m_adapter_listview = new ListViewAdapter(m_context);
			m_view = LayoutInflater.from(m_context).inflate(
					R.layout.my_edittext_listview, null);
			m_listView = (ListView) m_view.findViewById(R.id.listview);
			m_listView.setAdapter(m_adapter_listview);
			m_listView.setClickable(true);
			m_listView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					setText(listEditTextPrompt.get(position).getItem());
					m_popupwindow.dismiss();

				}
			});

			super.addTextChangedListener(new TextWatcher() {

				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub

				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					listEditTextPrompt = editTestPromptService.findAllByItem(
							s.toString(), 0, 5);
					m_listView.setAdapter(m_adapter_listview);
					if (m_popupwindow == null) {
						m_popupwindow = new PopupWindow(m_view, MyEditText.this
								.getWidth(), 300);// LayoutParams.WRAP_CONTENT);

						// 点击PopUpWindow外面的控件也可以使得PopUpWindow dimiss。
						// 需要顺利让PopUpWindow dimiss；PopUpWindow的背景不能为空。
						m_popupwindow
								.setBackgroundDrawable(new BitmapDrawable());

						// 获得焦点，并且在调用setFocusable（true）方法后，可以通过Back(返回)菜单使PopUpWindow
						// dimiss
						// pop.setFocusable(true)
						m_popupwindow.setFocusable(true);
						m_popupwindow.setTouchInterceptor(new OnTouchListener() {
							
							@Override
							public boolean onTouch(View v, MotionEvent event) {
								ILog.d(TAG, "event:"+event.getAction());
								switch(event.getAction()){
								case MotionEvent.ACTION_DOWN:
									m_popupwindow.showAsDropDown(MyEditText.this);
//									ILog.d(TAG, "Focusable:");
									break;
								case MotionEvent.ACTION_UP:
									requestFocus();
									break;
								}
								return false;
							}
						});
						m_popupwindow.setOutsideTouchable(true);
						m_popupwindow.showAsDropDown(MyEditText.this, 0, 0);

					} else if (m_popupwindow.isShowing()) {
						m_popupwindow.dismiss();
					} else {
						m_popupwindow.showAsDropDown(MyEditText.this);
					}
				}

			});
			/*
			 * super.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			 * 
			 * @Override public void onFocusChange(View v, boolean hasFocus) {
			 * if(hasFocus){ listEditTextPrompt =
			 * editTestPromptService.findAllByItem(getText().toString().trim(),
			 * 1, 5); m_listView.setAdapter(m_adapter_listview);
			 * if(m_popupwindow == null){ m_popupwindow = new
			 * PopupWindow(m_view, MyEditText.this.getWidth(),
			 * 300);//LayoutParams.WRAP_CONTENT);
			 * 
			 * //点击PopUpWindow外面的控件也可以使得PopUpWindow dimiss。 //需要顺利让PopUpWindow
			 * dimiss；PopUpWindow的背景不能为空。
			 * m_popupwindow.setBackgroundDrawable(new BitmapDrawable());
			 * 
			 * //获得焦点，并且在调用setFocusable（true）方法后，可以通过Back(返回)菜单使PopUpWindow
			 * dimiss //pop.setFocusable(true) m_popupwindow.setFocusable(true);
			 * m_popupwindow.setOutsideTouchable(true);
			 * m_popupwindow.showAsDropDown(MyEditText.this, 0, 0);
			 * 
			 * }else if(m_popupwindow.isShowing()){ m_popupwindow.dismiss();
			 * }else{ m_popupwindow.showAsDropDown(MyEditText.this); } }
			 * 
			 * } });
			 */
		} else {

		}
	}

	public void savePrompt() {
		savePrompt(getText().toString());
	}

	public void savePrompt(String item) {
		EditTestPromptDto editTestPromptDto = new EditTestPromptDto(item);
		editTestPromptService.save(editTestPromptDto);
	}

	class ListViewAdapter extends BaseAdapter {
		private LayoutInflater m_inflate;

		public ListViewAdapter(Context context) {
			// TODO Auto-generated constructor stub
			m_inflate = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listEditTextPrompt == null ? 0 : listEditTextPrompt.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			TextView textview = null;

			if (convertView == null) {
				convertView = m_inflate.inflate(
						R.layout.my_edittext_listview_item, null);
				textview = (TextView) convertView.findViewById(R.id.text);
				convertView.setTag(textview);
			} else {
				textview = (TextView) convertView.getTag();
			}

			textview.setText(listEditTextPrompt.get(position).getItem());
			

			return convertView;
		}
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

}
