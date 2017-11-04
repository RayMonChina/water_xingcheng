package com.ideal2.components;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ideal2.components.ListBox.IOnSelectedBackCallBack;
import com.ideal2.demo.R;

public class MulSelection extends Dialog implements 
		IOnSelectedBackCallBack, android.view.View.OnClickListener {

	private ListBox vLbSelect;
	private Button vBtnConfirm;
	private Button vBtnCancel;
	private int mRawId;
	private Context mContext;
	private String mForntVlaue;
	private String mBackValue;
	private ICallJsBack iJsBack;
	private String mSelected;
	private static MulSelection mDialog;

//	public static MulSelection getDialog(Context mContext, String selected) {
//		if (mDialog == null) {
//			mDialog = new MulSelection(mContext, 0);
//			mDialog.mSelected = selected;
//		}else {
//			Log.d("MulSelection", "refreshSelected");
//			mDialog.mSelected=selected;
//			mDialog.vLbSelect.refreshSelected(selected.replace("|", ","));
//		}
//		return mDialog;
//	}
	
	public void setiJsBack(ICallJsBack l) {
		this.iJsBack = l;
	}

	public MulSelection(Context context,String selected,String selectedId) {
		super(context, R.style.Theme_downDialog);
		this.mContext = context;
		mSelected = selected;
		mForntVlaue=selected;
		mBackValue=selectedId;
		vLbSelect = new ListBox(mContext);
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.me_mulselection);
		LinearLayout layout = (LinearLayout) findViewById(R.id.ll_select);
		layout.addView(vLbSelect, new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		vLbSelect.setBackCallBack(this);
		vBtnCancel = (Button) findViewById(R.id.btn_cancel);
		vBtnCancel.setOnClickListener(this);
		vBtnConfirm = (Button) findViewById(R.id.btn_confirm);
		vBtnConfirm.setOnClickListener(this);
		WindowManager.LayoutParams windowParams = getWindow().getAttributes();
		windowParams.width =400; // 设置宽度
		windowParams.height = 500;// 设置高度
		getWindow().setAttributes(windowParams);
	}
	private ArrayList<HashMap<String, String>> maps = new ArrayList<HashMap<String, String>>();
	public void parseXml(String[] ids,String[] item,String selectedIndex) {
		HashMap<String, String> head = new HashMap<String, String>();
		head.put("attr0", "value");
		head.put("attr1", "id");
		head.put("attr3", ""+selectedIndex);
		maps.add(head);
		for(int i =0;i<item.length;i++){
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("value", item[i]);
			hashMap.put("id", ids[i]+"");
			maps.add(hashMap);
		}
	
		vLbSelect.setDatas(mContext, maps, mSelected.replace("|", ","));
		
	}

	
	@Override
	public void OnSelected(String[] arg0) {
		mForntVlaue = arg0[0].replace("\\", "|");
		mBackValue = arg0[1].replace("\\", "|");
		
	}

	@Override
	public void onClick(View arg0) {
		if(arg0.getId()== R.id.btn_cancel){
			this.dismiss();
		}else if(arg0.getId()== R.id.btn_confirm){
			if (iJsBack != null) {
				iJsBack.callJs(mForntVlaue, mBackValue);
				this.dismiss();
			}
		}
	}

	
	public static void destory() {
		mDialog = null;
	}
	public static String getJson(String[] keys, TextView[] views) {
		JSONObject rData=new JSONObject();
		JSONArray array=new JSONArray();
		int len=keys.length>views.length? views.length:keys.length;
		for (int i = 0; i <len; i++) {
			JSONObject unit=new JSONObject();
		try {
			unit.put("Key", keys[i]);
			unit.put("Value", views[i].getText().toString().trim());
			array.put(unit);
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		}
		try {
			rData.put("ReturnData", array);
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		return rData.toString();
	}
	interface ICallJsBack {
		void callJs(String arg0, String arg1);
	}
	
}
