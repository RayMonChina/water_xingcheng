/*package com.ideal2.page;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.widget.LinearLayout;

import com.ideal2.adapter.TwoAttObj;
import com.ideal2.components.MyMultipleChoice;
import com.ideal2.components.MySingleChoice;
import com.ideal2.demo.R;
import com.ideal2.domain.ZoneDo;
import com.ideal2.entity.ZoneDto;

public class Main extends LinearLayout{
	
	private LinearLayout main;
	private MySingleChoice<ZoneDto,ZoneDo> mySingleChoice;
	public Main(Context context) {
		super(context, null);
		main = (LinearLayout)LinearLayout.inflate(context, R.layout.main, null);
	
		main.findViewById(R.id.btn);
		main.setBackgroundResource(R.color.white);
		main.findViewById(R.id.list);
//		mySingleChoice = (MySingleChoice<ZoneDto,ZoneDo>)main.findViewById(R.id.mySingleChoice);
		
		
		List data = new ArrayList();
		data.add(new TwoAttObj("aaa", "bbb"));
		data.add(new TwoAttObj("ccc", "ddd"));
		data.add(new TwoAttObj("aaa", "ad"));
		data.add(new TwoAttObj("ccc", "weqwe"));
		data.add(new TwoAttObj("aaa", "df"));
		data.add(new TwoAttObj("ccc", "qwe"));
		data.add(new TwoAttObj("aaa", "bbb"));
		data.add(new TwoAttObj("ccc", "ddd"));
		data.add(new TwoAttObj("aaa", "dsf"));
		data.add(new TwoAttObj("ccc", "df"));
		data.add(new TwoAttObj("aaa", "bbb"));
		data.add(new TwoAttObj("ccc", "asd"));
		data.add(new TwoAttObj("aaa", "bbb"));
		data.add(new TwoAttObj("ccc", "qwe"));
		data.add(new TwoAttObj("aaa", "bbb"));
		data.add(new TwoAttObj("ccc", "asd"));
		data.add(new TwoAttObj("aaa", "bbb"));
		data.add(new TwoAttObj("ccc", "ddd"));
		data.add(new TwoAttObj("aaa", "bbb"));
		data.add(new TwoAttObj("ccc", "ddd"));
		data.add(new TwoAttObj("aaa", "bbb"));
		data.add(new TwoAttObj("ccc", "ddd"));
		
//		MySingleChoiceAdapter adapter = new MySingleChoiceAdapter(context,data,"key","value");
//		MyListView list = (MyListView)main.findViewById(R.id.list);
//		adapter.setSelectedId("aaa");
//		list.setAdapter(adapter);
//	
//		List l = new ArrayList();
//		l.add("aa");
//		
		<?xml version="1.0" encoding="utf-8"?><Body>
 * <Request OperType="2" OrgCode="320482000030001"><
 * User ID="1">admin</User><Group ID=""></Group></Request></Body>
		
//		SelService<ZoneDo> selService = new SelService<ZoneDo>(getContext());
		ZoneDo zoneDo = new ZoneDo();
		zoneDo.setRequest_OrgCode("");
		zoneDo.setRequest_OperType("2");

		zoneDo.setUser_ID("1");
		zoneDo.setUser("admin");
		zoneDo.setGroup_ID("");
		zoneDo.setGroup("");
		
		mySingleChoice.setOnClickShow(false,true,true,zoneDo.getListZones(), zoneDo, "zone_ID", "zone");
		
		
		
//		BaseDao bd = new BaseDao(context, "http://116.228.145.5:8085/PADDL/PADWebService.asmx/HealthPADBus", "xml");
//		bd.conn("<?xml version=\"1.0\" encoding=\"utf-8\"?><Body><Request OperType=\"1\"><User>admin</User><PassWord>admin</PassWord></Request></Body>");
		
		
//		mySingleChoice.setData(context, data, "key", "value");
//		mySingleChoice.setOnClickShow(true);
//		mySingleChoice.setLocalSearchable(true);
//		mySingleChoice.setOnItemClickListener(new OnItemClickListener<TwoAttObj>() {
//
//			@Override
//			public void onItemClick(TwoAttObj obj, AdapterView<?> parent,
//					View view, int position, long id) {
////				MyToast.makeText(getContext(), ConfigBase.getConfig().getidealXml("idealConfigLocation").get("name")+"aa", Toast.LENGTH_SHORT).show();
//				
//			}
//		});
		
		
		MyMultipleChoice  mymultipple = (MyMultipleChoice)main.findViewById(R.id.mymultipple);
		mymultipple.setData(context,data, "key", "value");
		
		
		
		
		this.addView(main,LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT);
		
	}
	public LinearLayout getMain() {
		return main;
	}
	public void setMain(LinearLayout main) {
		this.main = main;
	}
	
	
	
}
*/