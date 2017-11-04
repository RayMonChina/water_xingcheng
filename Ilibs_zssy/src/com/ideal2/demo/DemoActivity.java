//package com.ideal2.demo;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.KeyEvent;
//import android.view.View;
//import android.view.ViewGroup.LayoutParams;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.gson.Gson;
//import com.ideal2.adapter.TwoAttObj;
//import com.ideal2.components.MyDate;
//import com.ideal2.components.MyEditText;
//import com.ideal2.components.MyMultipleChoice;
//import com.ideal2.components.MyTextView;
//import com.ideal2.components.MyToast;
//import com.ideal2.domain.ZoneDo;
//import com.ideal2.log.ILog;
//import com.ideal2.page.Main;
//
//public class DemoActivity extends Activity {
//	private static final TextWatcher TextWatcher = null;
//	private Main main;
//	private Button btn;
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//
//		super.onCreate(savedInstanceState);
//		 setContentView(R.layout.main);
//		 
//			List data = new ArrayList();
//			data.add(new TwoAttObj("aaa", "bbb"));
//			data.add(new TwoAttObj("ccc", "ddd"));
//			data.add(new TwoAttObj("aaa", "ad"));
//			data.add(new TwoAttObj("ccc", "weqwe"));
//			data.add(new TwoAttObj("aaa", "df"));
//			data.add(new TwoAttObj("ccc", "qwe"));
//			data.add(new TwoAttObj("aaa", "bbb"));
//			data.add(new TwoAttObj("ccc", "ddd"));
//			data.add(new TwoAttObj("aaa", "dsf"));
//			data.add(new TwoAttObj("ccc", "df"));
//			data.add(new TwoAttObj("aaa", "bbb"));
//			data.add(new TwoAttObj("ccc", "asd"));
//			data.add(new TwoAttObj("aaa", "bbb"));
//			data.add(new TwoAttObj("ccc", "qwe"));
//			data.add(new TwoAttObj("aaa", "bbb"));
//			data.add(new TwoAttObj("ccc", "asd"));
//			data.add(new TwoAttObj("aaa", "bbb"));
//			data.add(new TwoAttObj("ccc", "ddd"));
//			data.add(new TwoAttObj("aaa", "bbb"));
//			data.add(new TwoAttObj("ccc", "ddd"));
//			data.add(new TwoAttObj("aaa", "bbb"));
//			data.add(new TwoAttObj("ccc", "ddd"));
//			
//			
//			MyMultipleChoice  mymultipple = (MyMultipleChoice)findViewById(R.id.mymultipple);
//			mymultipple.setData(this,data, "key", "value");
////			mymultipple.setExclusiveIndex(new int[]{0});
//			mymultipple.setExclusiveValue(new String[]{"ddd"});
//			mymultipple.setOnClickShow(true);
//			
//			MyDate mydate = (MyDate)findViewById(R.id.mydate);
//			mydate.setOnClickShow(true);
//			MyDate mydate2 = (MyDate)findViewById(R.id.mydate2);
//			mydate2.setOnClickShow(true);
//	}
//
//	@Override
//	protected void onStart() {
//		super.onStart();
//
//	}
//
//	@Override
//	protected void onResume() {
//		super.onResume();
//
//	}
//
//}