package com.ideal2.components;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.ideal2.adapter.IMySingleChoice;
import com.ideal2.adapter.MySingleChoiceAdapter;
import com.ideal2.base.IDomainObject;
import com.ideal2.demo.R;
import com.ideal2.http.XmlNode;
import com.ideal2.services.SelService;
import com.ideal2.util.ReflectUtil;

/***
 * 
 * @author xufeng 单选控件
 * E 是List里的对像 
 * DO请求的对像
 */
public class MySingleChoice_bak20120807 <E,DO extends IDomainObject> extends MyTextView implements IMySingleChoice<E> {
	
	private final static String TAG = "MySingleChoice";
	private MyDialog myDialog;
	private MyListView myListView;
	private MySingleChoiceAdapter<E> mySingleChoiceAdapter;
	private MyButton btn_quit;
	private MyButton btn_clear;
	private MyButton btn_ok;
	private MyTextView text_title;
	private String text;
	private SelService<DO> selService;
	private LinearLayout ll;
	private OnItemClickListener<E> onItemClickListener;

	public MySingleChoice_bak20120807(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.Components);

		myDialog = new MyDialog(getContext(), R.style.proDialog);
		ll = (LinearLayout) LinearLayout.inflate(context,
				R.layout.my_singlechoice_dialog, null);

		text_title = (MyTextView) ll.findViewById(R.id.text_title);
		btn_quit = (MyButton) ll.findViewById(R.id.btn_quit);
		btn_clear = (MyButton) ll.findViewById(R.id.btn_clear);
		btn_ok = (MyButton) ll.findViewById(R.id.btn_ok);
		myListView = (MyListView) ll.findViewById(R.id.list);
		mySingleChoiceAdapter = new MySingleChoiceAdapter<E>();
		myListView.setAdapter(mySingleChoiceAdapter);
		myDialog.setContentView(ll);

		// 设置标题
		text = a.getString(R.styleable.Components_text);
		if (null != text && !"".equals(text)) {
			text_title.setText(text_title.getText() + "[" + text + "]");
		}

		myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				try {
					setSelectedIndex(position);
					setText(mySingleChoiceAdapter.getSelectedValue());
					if(null!=onItemClickListener){
						onItemClickListener.onItemClick(mySingleChoiceAdapter.getItem(position),parent,view,
								position,id);
					}
					myDialog.dismiss();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		btn_quit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					myDialog.dismiss();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btn_clear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					mySingleChoiceAdapter.clear();
					setText("");
					myDialog.dismiss();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					setText(getSelectedValue());
					myDialog.dismiss();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		setSearchable(false);
	}
	
	/**
	 * 是否有查询功能
	 * @param Searchable
	 * true 有
	 * false 无
	 */
	public void setSearchable(boolean Searchable){
		LinearLayout searchll = (LinearLayout)ll.findViewById(R.id.layout_search);
		if(Searchable){
			searchll.setVisibility(VISIBLE);
		}else{
			searchll.setVisibility(GONE);
		}
	}
	
	
	
	/**
	 * 关闭远程查询功能
	 */
	public void closeWebSearchable(){
		setWebSearchable(false, null,null, null, null, null);
	}
	/**
	 * 开启远程查询功能
	 * @param result
	 * true 开启
	 * false 关闭
	 */
	public void setWebSearchable(boolean result,final List<E> dataScorce,final DO domainObject,final String likeName,final String showId,
			final String showValue){
		LinearLayout searchll = (LinearLayout)ll.findViewById(R.id.layout_search);
		final MyEditText text_search = (MyEditText)searchll.findViewById(R.id.text_search);
		final MyButton btn_search = (MyButton)searchll.findViewById(R.id.btn_search);
		if(null==selService){
			selService = new SelService<DO>(getContext());
		}
		if(result){
			mySingleChoiceAdapter.setShowId(showId);
			mySingleChoiceAdapter.setShowValue(showValue);
			searchll.setVisibility(VISIBLE);
			
			btn_search.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					ReflectUtil.setter(domainObject, likeName, text_search.getText().toString().trim(), domainObject.getClass());
					selService.doRequest(domainObject);
					selService.setSelResponseListener(new SelService.SelResponseListener<DO>() {

						@Override
						public boolean sel_ok(DO domainObject, XmlNode xNode) {
							mySingleChoiceAdapter.setDataScorce(dataScorce);
							myListView.setAdapter(mySingleChoiceAdapter);
							return false;
						}

						@Override
						public boolean sel_err(String errmsg) {
							// TODO Auto-generated method stub
							return false;
						}
					});
				}
			});
		}else{
			searchll.setVisibility(GONE);
			btn_search.setOnClickListener(null);
		}
	}
	
	/**
	 * 开启本地查询功能
	 * @param result
	 * true 开启
	 * false 关闭
	 * @param timelySearch
	 * true 自动查询
	 */
	public void setLocalSearchable(boolean result,boolean timelySearch){
		LinearLayout searchll = (LinearLayout)ll.findViewById(R.id.layout_search);
		final MyEditText text_search = (MyEditText)searchll.findViewById(R.id.text_search);
		final MyButton btn_search = (MyButton)searchll.findViewById(R.id.btn_search);
		if(result){
			final List<E> listAll = mySingleChoiceAdapter.getDataScorce();
			final String[] arrValuesAll = mySingleChoiceAdapter.getArrValues();
			searchll.setVisibility(VISIBLE);
			if(timelySearch){
				btn_search.setVisibility(GONE);
				text_search.addTextChangedListener(new TextWatcher() {
					
					@Override
					public void onTextChanged(CharSequence s, int start, int before, int count) {
						List<E> list;
						if("".equals(s.toString().trim())){
							mySingleChoiceAdapter.setDataScorce(listAll);
							myListView.setAdapter(mySingleChoiceAdapter);
						}else{
							list = new ArrayList<E>();
							String str_search = s.toString().trim();
							for(int i = 0;i<arrValuesAll.length;i++){
								if(arrValuesAll[i].contains(str_search)){
									list.add(listAll.get(i));
								}
							}
							mySingleChoiceAdapter.setDataScorce(list);
							myListView.setAdapter(mySingleChoiceAdapter);
						}
					}
					
					@Override
					public void beforeTextChanged(CharSequence s, int start, int count,
							int after) {
						
						
					}
					
					@Override
					public void afterTextChanged(Editable s) {
						
					}
				});
			}else{
				btn_search.setVisibility(VISIBLE);
				btn_search.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						List<E> list;
						if("".equals(text_search.getText().toString().trim())){
							mySingleChoiceAdapter.setDataScorce(listAll);
							myListView.setAdapter(mySingleChoiceAdapter);
						}else{
							list = new ArrayList<E>();
							String str_search = text_search.getText().toString().trim();
							for(int i = 0;i<arrValuesAll.length;i++){
								if(arrValuesAll[i].contains(str_search)){
									list.add(listAll.get(i));
								}
							}
							mySingleChoiceAdapter.setDataScorce(list);
							myListView.setAdapter(mySingleChoiceAdapter);
						}
					}
				});
			}
		}else{
			searchll.setVisibility(GONE);
			btn_search.setOnClickListener(null);
		}
		
		
	}
	
	/**
	 * 关闭dialog
	 */
	public void dismiss() {
		myDialog.dismiss();
	}
	
	/**
	 * 单击显示dialog
	 * @param result
	 * true 显示
	 * false 不显示
	 */
	public void setOnClickShow(boolean result) {
		if (result) {
			setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					show();
				}
			});
		} else {
			setOnClickListener(null);
		}
	}
	
	/**
	 * 显示dialog
	 */
	public void show() {
		try {
			myListView.setSelectionFromTop(getSelectedIndex(), 0);
			myDialog.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 显示dialog
	 * 单击请求数据
	 * isRepeat  true重复请求  false请求一次
	 * isLocalSerchable true开启本地查询  false关闭本地查询
	 */

	public void setOnClickShow(final boolean isRepeat,final boolean isLocalSerchable,final boolean timelySearch,final List<E> dataScorce,final DO domainObject,final String showId,
			final String showValue) {
		setOnClickListener(new View.OnClickListener() {
			boolean isfirst = true;
			@Override
			public void onClick(View v) {
				
				if(isRepeat){
					if(null==selService){
						selService = new SelService<DO>(getContext());
					}
					selService.doRequest(domainObject);
					selService.setSelResponseListener(new SelService.SelResponseListener<DO>() {

						@Override
						public boolean sel_ok(DO domainObject, XmlNode xNode) {
							setData(dataScorce, showId, showValue);
							setLocalSearchable(isLocalSerchable,timelySearch);
							myListView.setAdapter(mySingleChoiceAdapter);
							show();
							return false;
						}

						@Override
						public boolean sel_err(String errmsg) {
							// TODO Auto-generated method stub
							return false;
						}
					});
					
				}else{
					
					if(isfirst){
						
						if(null==selService){
							selService = new SelService<DO>(getContext());
						}
						selService.doRequest(domainObject);
						selService.setSelResponseListener(new SelService.SelResponseListener<DO>() {
							
							@Override
							public boolean sel_ok(DO domainObject, XmlNode xNode) {
								isfirst = false;
								setData(dataScorce, showId, showValue);
								setLocalSearchable(isLocalSerchable,timelySearch);
								myListView.setAdapter(mySingleChoiceAdapter);
								show();
								return false;
							}
							
							@Override
							public boolean sel_err(String errmsg) {
								isfirst = true;
								return false;
							}
						});
						
					}else{
						show();
					}
				}
			}
		});
	}
	
	/**
	 * 通过请求获取数据
	 */
	public void setDate(final List<E> dataScorce,DO domainObject, final String showId,
			final String showValue){
		if(null==selService){
			selService = new SelService<DO>(getContext());
		}
		selService.doRequest(domainObject);
		selService.setSelResponseListener(new SelService.SelResponseListener<DO>() {
			
			@Override
			public boolean sel_ok(DO domainObject, XmlNode xNode) {
				setData(dataScorce, showId, showValue);
				return false;
			}
			
			@Override
			public boolean sel_err(String errmsg) {
				// TODO Auto-generated method stub
				return false;
			}
		});
	}
	
	public interface OnItemClickListener <E>{
		public void onItemClick(E obj,AdapterView<?> parent, View view,
				int position, long id);
	}
	/**
	 * 
	 * @param onItemClickListener
	 * 设置单击选项监听
	 */
	public void setOnItemClickListener(OnItemClickListener<E> onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}

	public void setData(List<E> dataScorce) {
		setData(getContext(), dataScorce, "", "string");
	}

	public void setData(List<E> dataScorce, String showId, String showValue) {
		setData(getContext(), dataScorce, showId, showValue);
	}

	@Override
	public  void setData(Context context, List<E> dataScorce, String showId,
			String showValue) {
			mySingleChoiceAdapter.setData(context, dataScorce, showId, showValue);
	}

	@Override
	public String getSelectedId() {
		// TODO Auto-generated method stub
		return mySingleChoiceAdapter.getSelectedId();
	}

	@Override
	public void setSelectedId(String selectedId) {
		// TODO Auto-generated method stub
		mySingleChoiceAdapter.setSelectedId(selectedId);
	}

	@Override
	public int getSelectedIndex() {
		// TODO Auto-generated method stub
		return mySingleChoiceAdapter.getSelectedIndex();
	}

	@Override
	public void setSelectedIndex(int selectedIndex) {
		// TODO Auto-generated method stub
		mySingleChoiceAdapter.setSelectedIndex(selectedIndex);
	}

	@Override
	public String getSelectedValue() {
		// TODO Auto-generated method stub
		return mySingleChoiceAdapter.getSelectedValue();
	}

	@Override
	public void setSelectedValue(String selectedValue) {
		// TODO Auto-generated method stub
		mySingleChoiceAdapter.setSelectedValue(selectedValue);
	}

}
