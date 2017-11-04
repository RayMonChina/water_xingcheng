package com.ideal2.components;

import java.nio.Buffer;
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

import com.ideal2.adapter.MyMultipleChoiceAdapter;
import com.ideal2.base.IDomainObject;
import com.ideal2.demo.R;
import com.ideal2.http.XmlNode;
import com.ideal2.log.ILog;
import com.ideal2.services.SelService;
import com.ideal2.util.ReflectUtil;


/**
 * 多选控件
 * @author xufeng
 * 
 * @param <E>是List里的对像 
 * @param <DO>请求的对像
 */
public class MyMultipleChoice<E, DO extends IDomainObject> extends MyTextView {

	private final static String TAG = "MySingleChoice";
	private MyDialog myDialog;
	private MyListView myListView;
	private MyMultipleChoiceAdapter<E> myMultiplechoiceAdapter;
	private MyButton btn_invert;
	private MyButton btn_clear;
	private MyButton btn_ok;
	private MyTextView text_title;
	private String text;
	private SelService<DO> selService;
	private LinearLayout ll;
	private OnItemClickListener<E> onItemClickListener;
	private OnInvertListener onInvertListener;
	private OnClearListener onClearListener;
	private OnOkListener onOkListener;
	private boolean isEdit;// false:不可编辑 true:可编辑 默认：false

	// start 反选相关参数
	/*
	 * 当选中这些值时。别的值不可选
	 */
	private String[] exclusiveId;
	private int[] exclusiveIndex;
	private String[] exclusiveValue;
	// end 反选相关参数

	/*
	 * status 0:开启远程查询功能 1:关闭远程查询功能 2:开启本地查询功能 自动查询 3:开启本地查询功能 关闭查询 4:关闭本地查询功能
	 * 5: 6: 7: 8: 9:
	 */
	private int status;

	public MyMultipleChoice(Context context, AttributeSet attrs) {
		super(context, attrs);

		isEdit = false;// 默认：false

		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.Components);

		myDialog = new MyDialog(getContext(), R.style.proDialog);
		ll = (LinearLayout) LinearLayout.inflate(context,
				R.layout.my_multiplechoice_dialog, null);

		text_title = (MyTextView) ll.findViewById(R.id.text_title);
		myListView = (MyListView) ll.findViewById(R.id.list);
		myMultiplechoiceAdapter = new MyMultipleChoiceAdapter<E>();
		myListView.setAdapter(myMultiplechoiceAdapter);
		myDialog.setContentView(ll);

		// 设置标题
		text = a.getString(R.styleable.Components_text);
		if (null != text && !"".equals(text)) {
			text_title.setText(text_title.getText() + "[" + text + "]");
		}

		myListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						try {
							if (null != exclusiveIndex) {
								boolean result = true;
								for (int i = 0; i < exclusiveIndex.length; i++) {
									if (exclusiveIndex[i] == position) {
										boolean exclusive = myMultiplechoiceAdapter
												.getSelected()[position];
										myMultiplechoiceAdapter.clear();
										boolean[] bool = myMultiplechoiceAdapter
												.getSelected();
										bool[position] = exclusive;
										myMultiplechoiceAdapter
												.setSelected(bool);
										result = false;
										break;
									}
								}
								if (result) {
									myMultiplechoiceAdapter.changeIndex(
											exclusiveIndex, false);
								}
							}
							myMultiplechoiceAdapter.changeSelected(position);
							if (null != onItemClickListener) {
								onItemClickListener.onItemClick(
										myMultiplechoiceAdapter
												.getItem(position), parent,
										view, position, id);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});

		btn_invert();
		btn_clear();
		btn_ok();
		setSearchable(false);
	}
	
	
	/*
	 * 反选
	 */
	public void btn_invert() {
		btn_invert = (MyButton) ll.findViewById(R.id.btn_invert);
		btn_invert.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					if (null != onInvertListener) {
						onInvertListener.onInvert(MyMultipleChoice.this);
					}
					myMultiplechoiceAdapter.invert();
					if(null!=exclusiveIndex){
						for (int i = 0; i < exclusiveIndex.length; i++) {
							myMultiplechoiceAdapter
									.getSelected()[exclusiveIndex[i]]=false;

						}
					}
					myMultiplechoiceAdapter.refresh();
//					myDialog.dismiss();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	/*
	 * 清除
	 */
	public void btn_clear() {
		btn_clear = (MyButton) ll.findViewById(R.id.btn_clear);
		btn_clear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					myMultiplechoiceAdapter.clear();
					setText("");
					if (null != onClearListener) {
						onClearListener.onClear(MyMultipleChoice.this);
					}
					myDialog.dismiss();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	
	public void btn_ok() {
		btn_ok = (MyButton) ll.findViewById(R.id.btn_ok);
		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					if (null != onOkListener) {
						onOkListener.onOk(MyMultipleChoice.this);
					}
					setText(toStringList(getSelectedValue()));
					myDialog.dismiss();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 是否有查询功能
	 * 
	 * @param Searchable
	 *            true 有 false 无
	 */
	public void setSearchable(boolean Searchable) {
		LinearLayout searchll = (LinearLayout) ll
				.findViewById(R.id.layout_search);
		if (Searchable) {
			searchll.setVisibility(VISIBLE);
		} else {
			searchll.setVisibility(GONE);
		}
	}

	/**
	 * 关闭远程查询功能
	 */
	public void closeWebSearchable() {
		setWebSearchable(false, null, null, null, null, null);
	}

	/**
	 * 开启远程查询功能
	 * 
	 * @param result
	 *            true 开启 false 关闭
	 */
	public void setWebSearchable(boolean result, final List<E> dataScorce,
			final DO domainObject, final String likeName, final String showId,
			final String showValue) {
		LinearLayout searchll = (LinearLayout) ll
				.findViewById(R.id.layout_search);
		final MyEditText text_search = (MyEditText) searchll
				.findViewById(R.id.text_search);
		final MyButton btn_search = (MyButton) searchll
				.findViewById(R.id.btn_search);
		if (null == selService) {
			selService = new SelService<DO>(getContext());
		}
		if (result) {
			this.status = 0;
			myMultiplechoiceAdapter.setShowId(showId);
			myMultiplechoiceAdapter.setShowValue(showValue);
			searchll.setVisibility(VISIBLE);

			btn_search.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					ReflectUtil.setter(domainObject, likeName, text_search
							.getText().toString().trim(),
							domainObject.getClass());
					selService.doRequest(domainObject);
					selService
							.setSelResponseListener(new SelService.SelResponseListener<DO>() {

								@Override
								public boolean sel_ok(DO domainObject,
										XmlNode xNode) {
									myMultiplechoiceAdapter
											.setDataScorce(dataScorce);
									myListView
											.setAdapter(myMultiplechoiceAdapter);
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
		} else {
			this.status = 1;
			searchll.setVisibility(GONE);
			btn_search.setOnClickListener(null);
		}
	}

	/**
	 * 开启本地查询功能
	 * 
	 * @param result
	 *            true 开启 false 关闭
	 * @param timelySearch
	 *            true 自动查询
	 */
	public void setLocalSearchable(boolean result, boolean timelySearch) {
		LinearLayout searchll = (LinearLayout) ll
				.findViewById(R.id.layout_search);
		final MyEditText text_search = (MyEditText) searchll
				.findViewById(R.id.text_search);
		final MyButton btn_search = (MyButton) searchll
				.findViewById(R.id.btn_search);
		if (result) {
			final List<E> listAll = myMultiplechoiceAdapter.getDataScorce();
			final String[] arrValuesAll = myMultiplechoiceAdapter
					.getArrValues();
			searchll.setVisibility(VISIBLE);
			if (timelySearch) {
				this.status = 2;
				btn_search.setVisibility(GONE);
				text_search.addTextChangedListener(new TextWatcher() {

					@Override
					public void onTextChanged(CharSequence s, int start,
							int before, int count) {
						List<E> list;
						if ("".equals(s.toString().trim())) {
							myMultiplechoiceAdapter.setDataScorce(listAll);
							myListView.setAdapter(myMultiplechoiceAdapter);
						} else {
							list = new ArrayList<E>();
							String str_search = s.toString().trim();
							for (int i = 0; i < arrValuesAll.length; i++) {
								if (arrValuesAll[i].contains(str_search)) {
									list.add(listAll.get(i));
								}
							}
							myMultiplechoiceAdapter.setDataScorce(list);
							myListView.setAdapter(myMultiplechoiceAdapter);
						}
					}

					@Override
					public void beforeTextChanged(CharSequence s, int start,
							int count, int after) {

					}

					@Override
					public void afterTextChanged(Editable s) {

					}
				});
			} else {
				this.status = 3;
				btn_search.setVisibility(VISIBLE);
				btn_search.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						List<E> list;
						if ("".equals(text_search.getText().toString().trim())) {
							myMultiplechoiceAdapter.setDataScorce(listAll);
							myListView.setAdapter(myMultiplechoiceAdapter);
						} else {
							list = new ArrayList<E>();
							String str_search = text_search.getText()
									.toString().trim();
							for (int i = 0; i < arrValuesAll.length; i++) {
								if (arrValuesAll[i].contains(str_search)) {
									list.add(listAll.get(i));
								}
							}
							myMultiplechoiceAdapter.setDataScorce(list);
							myListView.setAdapter(myMultiplechoiceAdapter);
						}
					}
				});
			}
		} else {
			this.status = 4;
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
	 * 
	 * @param result
	 *            true 显示 false 不显示
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
			// myListView.setSelectionFromTop(getSelectedIndex().get(0), 0);
			myDialog.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 显示dialog 单击请求数据 isRepeat true重复请求 false请求一次 isLocalSerchable true开启本地查询
	 * false关闭本地查询
	 */

	public void setOnClickShow(final boolean isRepeat,
			final boolean isLocalSerchable, final boolean timelySearch,
			final List<E> dataScorce, final DO domainObject,
			final String showId, final String showValue) {
		setOnClickListener(new View.OnClickListener() {
			boolean isfirst = true;

			@Override
			public void onClick(View v) {

				if (isRepeat) {
					if (null == selService) {
						selService = new SelService<DO>(getContext());
					}
					selService.doRequest(domainObject);
					selService
							.setSelResponseListener(new SelService.SelResponseListener<DO>() {

								@Override
								public boolean sel_ok(DO domainObject,
										XmlNode xNode) {
									setData(dataScorce, showId, showValue);
									setLocalSearchable(isLocalSerchable,
											timelySearch);
									myListView
											.setAdapter(myMultiplechoiceAdapter);
									show();
									return false;
								}

								@Override
								public boolean sel_err(String errmsg) {
									// TODO Auto-generated method stub
									return false;
								}
							});

				} else {

					if (isfirst) {

						if (null == selService) {
							selService = new SelService<DO>(getContext());
						}
						selService.doRequest(domainObject);
						selService
								.setSelResponseListener(new SelService.SelResponseListener<DO>() {

									@Override
									public boolean sel_ok(DO domainObject,
											XmlNode xNode) {
										isfirst = false;
										setData(dataScorce, showId, showValue);
										setLocalSearchable(isLocalSerchable,
												timelySearch);
										myListView
												.setAdapter(myMultiplechoiceAdapter);
										show();
										return false;
									}

									@Override
									public boolean sel_err(String errmsg) {
										isfirst = true;
										return false;
									}
								});

					} else {
						show();
					}
				}
			}
		});
	}

	/**
	 * 通过请求获取数据
	 */
	public void setDate(final List<E> dataScorce, DO domainObject,
			final String showId, final String showValue) {
		if (null == selService) {
			selService = new SelService<DO>(getContext());
		}
		selService.doRequest(domainObject);
		selService
				.setSelResponseListener(new SelService.SelResponseListener<DO>() {

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

	/**
	 * @return true:可以编辑 false:不可以编辑
	 */
	public boolean isEdit() {
		return isEdit;
	}

	/**
	 * 
	 * @param isEdit
	 *            true:可以编辑 false:不可以编辑
	 */
	public void setEdit(boolean isEdit) {
		LinearLayout searchll = (LinearLayout) ll
				.findViewById(R.id.layout_search);
		final MyEditText text_search = (MyEditText) searchll
				.findViewById(R.id.text_search);
		searchll.setVisibility(VISIBLE);
		if (isEdit) {
			btn_ok.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						setText(text_search.getText().toString().trim());
						myDialog.dismiss();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		} else {
			switch (this.status) {
			case 0:
			case 2:
			case 3:
				break;
			default:
				searchll.setVisibility(GONE);
				break;
			}
			btn_ok();
		}
		this.isEdit = isEdit;
	}

	public interface OnInvertListener {
		public void onInvert(MyMultipleChoice v);
	}

	public interface OnClearListener {
		public void onClear(MyMultipleChoice v);
	}

	public interface OnOkListener {
		public void onOk(MyMultipleChoice v);
	}

	public interface OnItemClickListener<E> {
		public void onItemClick(E obj, AdapterView<?> parent, View view,
				int position, long id);
	}

	/**
	 * 
	 * @param onItemClickListener
	 *            设置单击选项监听
	 */
	public void setOnItemClickListener(
			OnItemClickListener<E> onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}

	public void setOnClearListener(OnClearListener onClearListener) {
		this.onClearListener = onClearListener;
	}

	public void setOnInvertListener(OnInvertListener onInvertListener) {
		this.onInvertListener = onInvertListener;
	}

	public void setOnOkListener(OnOkListener onOkListener) {
		this.onOkListener = onOkListener;
	}

	public void setData(List<E> dataScorce) {
		setData(getContext(), dataScorce, "", "string");
	}

	public void setData(List<E> dataScorce, String showId, String showValue) {
		setData(getContext(), dataScorce, showId, showValue);
	}

	public void setData(Context context, List<E> dataScorce, String showId,
			String showValue) {
		myMultiplechoiceAdapter.setData(context, dataScorce, showId, showValue);
	}

	/**
	 * list 需String方法
	 * 
	 * @param listseparation
	 *            分割线
	 * @return 转换的串
	 */
	public String toStringList(List list) {
		String separation = "|";
		return toStringList(list, separation);
	}

	/**
	 * list 需String方法
	 * 
	 * @param list
	 *            需转换的list
	 * @param separation
	 *            分割线
	 * @return 转换的串
	 */
	public String toStringList(List list, String separation) {
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			switch (i) {
			case 0:
				str.append(list.get(i).toString());
				break;
			default:
				str.append(separation);
				str.append(list.get(i).toString());
				break;
			}
		}
		return str.toString();
	}

	/**
	 * 设置互斥的ID
	 * 
	 * @param exclusiveId
	 */
	public void setExclusiveId(String[] exclusiveId) {
		int[] exclusiveIndex = new int[exclusiveId.length];
		for (int i = 0; i < exclusiveId.length; i++) {
			exclusiveIndex[i] = (Integer) myMultiplechoiceAdapter.getIds().get(
					exclusiveId[i]);
		}
		this.exclusiveIndex = exclusiveIndex;
		this.exclusiveId = exclusiveId;
	}

	/**
	 * 取得互斥的Index
	 * 
	 * @param exclusiveId
	 */
	public int[] getExclusiveIndex() {
		return exclusiveIndex;
	}

	/**
	 * 设置互斥的Index
	 * 
	 * @param exclusiveId
	 */
	public void setExclusiveIndex(int[] exclusiveIndex) {
		this.exclusiveIndex = exclusiveIndex;
	}

	/**
	 * 设置互斥的Value
	 * 
	 * @param exclusiveId
	 */
	public void setExclusiveValue(String[] exclusiveValue) {
		int[] exclusiveIndex = new int[exclusiveValue.length];
		for (int i = 0; i < exclusiveValue.length; i++) {
			exclusiveIndex[i] = (Integer) myMultiplechoiceAdapter.getValues()
					.get(exclusiveValue[i]);
		}
		ILog.d(TAG, "aa:" + exclusiveIndex[0]);
		this.exclusiveIndex = exclusiveIndex;
		this.exclusiveValue = exclusiveValue;
	}

	public List<String> getSelectedId() {
		return myMultiplechoiceAdapter.getSelectedId();
	}

	public void setSelectedId(List<String> selectedId) {
		// TODO Auto-generated method stub
		myMultiplechoiceAdapter.setSelectedId(selectedId);
	}

	public List<Integer> getSelectedIndex() {
		// TODO Auto-generated method stub
		return myMultiplechoiceAdapter.getSelectedIndex();
	}

	public void setSelectedIndex(List<Integer> selectedIndex) {
		// TODO Auto-generated method stub
		myMultiplechoiceAdapter.setSelectedIndex(selectedIndex);
	}

	public List<String> getSelectedValue() {
		// TODO Auto-generated method stub
		return myMultiplechoiceAdapter.getSelectedValue();
	}

	public void setSelectedValue(List<String> selectedValue) {
		// TODO Auto-generated method stub
		myMultiplechoiceAdapter.setSelectedValue(selectedValue);
	}
}
