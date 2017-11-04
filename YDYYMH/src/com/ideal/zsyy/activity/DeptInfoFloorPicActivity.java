package com.ideal.zsyy.activity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.jijiang.wtapp.R;
import com.ideal.zsyy.adapter.DeptInfoFloorPicAdapter;
import com.ideal.zsyy.entity.DeptInfoDetail;
import com.ideal.zsyy.entity.DoctorServiceClinic;
import com.ideal.zsyy.entity.FloorInfo;
import com.ideal.zsyy.utils.DataCache;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

public class DeptInfoFloorPicActivity extends Activity {

	private Button btn_mz;
	private Button btn_bf;
	private ListView lvDeptinfoPic;
	private List<FloorInfo> picfloorlist = new ArrayList<FloorInfo>();
	public static Bitmap bitmap;

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			Object[] object;
			ImageView iv_icon;

			switch (msg.what) {

			case 0:
				object = (Object[]) msg.obj;
				bitmap = (Bitmap) object[0];

				if (bitmap != null) {

//					hfd_ll_text.setVisibility(View.GONE);

					iv_icon = (ImageView) object[1];
					iv_icon.setImageBitmap(bitmap);
				}

				break;
			}
		};
	};
	private DeptInfoFloorPicAdapter adapter_mz;
	private DeptInfoFloorPicAdapter adapter_bf;
	private DeptInfoDetail infodetail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deptinfofloorpic);
		infodetail = DataCache.deptInfodetail;
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		Button btn_back = (Button) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		btn_mz = (Button) findViewById(R.id.btn_deptinfo_mz);
		btn_bf = (Button) findViewById(R.id.btn_deptinfo_bf);
		btn_mz.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				btn_mz.setBackgroundResource(R.drawable.navigation_tab_left_down);
				btn_mz.setTextColor(getResources().getColor(
						android.R.color.white));
				btn_bf.setBackgroundResource(R.drawable.navigation_tab_right_up);
				btn_bf.setTextColor(Color.parseColor("#0079ff"));
				if (infodetail != null) {
					List<FloorInfo> buildlist_mz = infodetail.getBuildlist_mz();
					picfloorlist.clear();
					getList(buildlist_mz);
					if (buildlist_mz != null) {
						if (adapter_mz == null) {
							adapter_mz = new DeptInfoFloorPicAdapter(
									DeptInfoFloorPicActivity.this,
									picfloorlist, infodetail.getDept_Name(),mHandler);
						}
						lvDeptinfoPic.setAdapter(adapter_mz);
					}
				}
			}
		});

		btn_bf.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				btn_mz.setBackgroundResource(R.drawable.navigation_tab_left_up);
				btn_mz.setTextColor(Color.parseColor("#0079ff"));
				btn_bf.setBackgroundResource(R.drawable.navigation_tab_right_down);
				btn_bf.setTextColor(getResources().getColor(
						android.R.color.white));
				if (infodetail != null) {
					List<FloorInfo> buildlist_bf = infodetail.getBuildlist_bf();
					picfloorlist.clear();
					getList(buildlist_bf);
					if (buildlist_bf != null) {
						if (adapter_bf == null) {
							adapter_bf = new DeptInfoFloorPicAdapter(
									DeptInfoFloorPicActivity.this,
									picfloorlist, infodetail.getDept_Name(),mHandler);
						}
						lvDeptinfoPic.setAdapter(adapter_bf);
					}
				}
			}
		});

		lvDeptinfoPic = (ListView) findViewById(R.id.lv_deptinfo_pic);
		showData();
	}

	private void showData() {
		// TODO Auto-generated method stub
		if (infodetail != null) {
			List<FloorInfo> buildlist_mz = infodetail.getBuildlist_mz();
			if (buildlist_mz != null) {
				picfloorlist.clear();
				getList(buildlist_mz);
				if (adapter_mz == null) {
					adapter_mz = new DeptInfoFloorPicAdapter(this,
							picfloorlist, infodetail.getDept_Name(),mHandler);
				}
				lvDeptinfoPic.setAdapter(adapter_mz);
			}
		}
	}

	public void getList(List<FloorInfo> deptdutyinfos) {
		List<String> order = removeDuplicateWithOrder(deptdutyinfos);
		for (String buildname : order) {
			String bname = "";
			for (FloorInfo floorinfo : deptdutyinfos) {
				if ("".equals(bname)) {
					FloorInfo info = new FloorInfo();
					bname = buildname;
					info.setBuild_name(floorinfo.getBuild_name());
					info.setBuild_pic(floorinfo.getBuild_pic());
					picfloorlist.add(info);
				} 
				FloorInfo info2 = new FloorInfo();
				if (!bname.equals(buildname)) { 
					bname = buildname;
					info2.setBuild_name(floorinfo.getBuild_name());
					info2.setBuild_pic(floorinfo.getBuild_pic());
					picfloorlist.add(info2);
				} else {
					info2.setBuild_name(floorinfo.getBuild_name());
					info2.setBuild_pic(floorinfo.getBuild_pic());
					info2.setFloor_name(floorinfo.getFloor_name());
					info2.setFloor_pic(floorinfo.getFloor_pic());
				}
				picfloorlist.add(info2);
			}
		}
	}

	public List<String> removeDuplicateWithOrder(List<FloorInfo> deptdutyinfos) {
		Set set = new HashSet();
		List<String> grouplist = new ArrayList<String>();
		for (Iterator iter = deptdutyinfos.iterator(); iter.hasNext();) {
			FloorInfo element = (FloorInfo) iter.next();
			set.add(element.getBuild_name());
		}
		grouplist.clear();
		grouplist.addAll(set);
		return grouplist;
	}
	
	@Override
	protected void onDestroy() {
		if (bitmap != null && !bitmap.isRecycled()) {
			bitmap.recycle();
			bitmap = null;
		}
		super.onDestroy();
	}
}
