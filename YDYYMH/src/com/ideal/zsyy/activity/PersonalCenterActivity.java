package com.ideal.zsyy.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.ideal.wdm.tools.DataCache;
import com.ideal.zsyy.Config;
import com.jijiang.wtapp.R;
import com.ideal.zsyy.adapter.CommonlyPatientListAdapter;
import com.ideal.zsyy.adapter.MyViewPagerAdapter;
import com.ideal.zsyy.adapter.PersonalCenterListAdapter;
import com.ideal.zsyy.entity.PhCommonContactInfo;
import com.ideal.zsyy.entity.PhUser;
import com.ideal.zsyy.entity.TopVisitResInfo;
import com.ideal.zsyy.request.CommonContactListReq;
import com.ideal.zsyy.request.SelectTopVisitReq;
import com.ideal.zsyy.response.CommonContactListRes;
import com.ideal.zsyy.response.SelectTopVisistRes;
import com.ideal.zsyy.utils.DataUtils;
import com.ideal2.base.gson.GsonServlet;
import com.ideal2.base.gson.GsonServlet.OnResponseEndListening;

public class PersonalCenterActivity extends Activity implements
		OnPageChangeListener {

	private LinearLayout pc_ll_yuyue;
	private TextView pc_tv_yuyue;
	private ImageView pc_im_yuyue;

	private LinearLayout pc_ll_cancel;
	private TextView pc_tv_cancel;
	private ImageView pc_im_cancel;

	private PhUser phUsers;
	private ViewPager pc_viewpager;

	private TextView user_name;
	private TextView user_sex;

	// private List<PersonalCenter> list_yuyue;
	// private List<PersonalCenter> list_cancel;
	private List<TopVisitResInfo> list_yuyue;
	private List<TopVisitResInfo> list_cancel;
	
	private PersonalCenterListAdapter listAdapter_yuyue;
	private ListView lv_pc_yuyue;
	private ListView lv_pc_cancel;
	private ListView lv_person_center_list;
	private List<PhCommonContactInfo> painte_list;
	private AlertDialog alert;
	private CommonlyPatientListAdapter commonlyPatientListAdapter;
	private List<Map<String, String>> product_type_list = new ArrayList<Map<String, String>>();
	private LinearLayout login_dialog_list;
	private ListView login_dialog_listview;
	private int pastdue = 0; 
	private int nopastdue = 0;  //预约成功次数
	

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				queryData();
				break;
 
			case 2:
				int count = list_yuyue.size(); 
				ll_level.removeAllViews(); 
				if (count > 5) {
					nopastdue = (int) Math.ceil((nopastdue * 5.0) / 10);
					pastdue = 5 - nopastdue; 
				}
				for (int i = 0; i < nopastdue; i++) { 
					ImageView view = new ImageView(PersonalCenterActivity.this);
					view.setBackgroundResource(R.drawable.solid_star);
					ll_level.addView(view);
				}
				for (int i = 0; i < pastdue; i++) {
					ImageView view = new ImageView(PersonalCenterActivity.this);
					view.setBackgroundResource(R.drawable.sky_star); 
					ll_level.addView(view);
				}
				break;
			case 3:
				ll_level.setVisibility(View.VISIBLE);
				tv_xydj.setVisibility(View.GONE); 
				break;
			case 4:
				onResume(); 
				queryPaintData();
				break;
			case 5:
				SimpleAdapter adapter = new SimpleAdapter(
						getApplicationContext(), product_type_list,
						R.layout.login_dialog_window,
						new String[] { "contactInfo_name" },
						new int[] { R.id.login_dialog_item, });
				login_dialog_listview.setAdapter(adapter);
				AlertDialog.Builder radio = new AlertDialog.Builder(
						PersonalCenterActivity.this);
				radio.setTitle("请选择常用就诊人");
				radio.setView(login_dialog_list);
				alert = radio.create();
				break;
			default:
				break;
			}
		};
	};
	private TextView pc_tv_content;
	private ImageView pc_im_content;
	private LinearLayout pc_ll_content;
	private BroadCast skinBroadCast;
	private LinearLayout ll_level;
	private LinearLayout ll_xydj;
	private TextView tv_xydj;
	private TextView tv_person_center_editPersn; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personal_center1);

		phUsers = Config.phUsers;
		
		boolean isFirst = getIntent().getBooleanExtra("isFirst", false);
		if (isFirst) {
			Intent intent = new Intent(PersonalCenterActivity.this,
					EditPersonInfoActivity.class);
			intent.putExtra("isFirst", isFirst); 
			startActivity(intent);
		}
		
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("change_contact");
		skinBroadCast = new BroadCast();
		registerReceiver(skinBroadCast, intentFilter);

		list_yuyue = new ArrayList<TopVisitResInfo>();
		list_cancel = new ArrayList<TopVisitResInfo>();

		initView();
	}

	private void initView() {

		pc_viewpager = (ViewPager) findViewById(R.id.pc_viewpager);
		pc_viewpager.setOnPageChangeListener(this);

		ArrayList<View> aViews = new ArrayList<View>();
		LayoutInflater lf = LayoutInflater.from(this);

		View main_viewpager_button_yuyue = lf.inflate(
				R.layout.pc_viewpager_page1, null);
		View main_viewpager_button_cancel = lf.inflate(
				R.layout.pc_viewpager_page2, null);
		View main_viewpager_button_content = lf.inflate(
				R.layout.pc_viewpager_page3, null);

		aViews.add(main_viewpager_button_yuyue);
		aViews.add(main_viewpager_button_cancel);
		aViews.add(main_viewpager_button_content);

		MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(aViews);
		pc_viewpager.setAdapter(myViewPagerAdapter);

		// 预约成功listview
		lv_pc_yuyue = (ListView) main_viewpager_button_yuyue
				.findViewById(R.id.lv_pc_yuyue);

		Button bt_pc_yuyue = (Button) main_viewpager_button_yuyue
				.findViewById(R.id.bt_pc_yuyue);
		bt_pc_yuyue.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (product_type_list.size() > 0) {

					if (alert != null) {

						alert.show();
					}

				} else {

					Toast.makeText(v.getContext(), "无常用就诊人", 0).show();
				}

			}
		});

		// 取消预约listview
		lv_pc_cancel = (ListView) main_viewpager_button_cancel
				.findViewById(R.id.lv_pc_cancel);

		Button bt_pc_yuyue1 = (Button) main_viewpager_button_cancel
				.findViewById(R.id.bt_pc_yuyue);
		bt_pc_yuyue1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (product_type_list.size() > 0) {

					if (alert != null) {

						alert.show();
					}

				} else {

					Toast.makeText(v.getContext(), "无常用就诊人", 0).show();
				}

			}
		});
		
		ll_xydj = (LinearLayout) findViewById(R.id.ll_xydj); 
		ll_level = (LinearLayout) findViewById(R.id.ll_level); 
		tv_xydj = (TextView) findViewById(R.id.tv_xydj); 
		
		ll_xydj.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tv_xydj.setVisibility(View.VISIBLE);
				ll_level.setVisibility(View.GONE);
				tv_xydj.setText("亲,您已成功挂号" + nopastdue + "次,失约" + pastdue + "次");
				mHandler.sendEmptyMessageDelayed(3, 3000);
			}
		});

		
		// /////////
		user_name = (TextView) findViewById(R.id.user_name);
		user_sex = (TextView) findViewById(R.id.user_sex);
		if (phUsers != null) {
			user_name.setText(phUsers.getName());
			String sex = phUsers.getSex();
			if (Config.man.equals(sex)) {
				sex = "男";
			} else if (Config.woman.equals(sex)) {
				sex = "女";
			} else {
				sex = "";
			}
			user_sex.setText(sex);
		}
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		login_dialog_list = (LinearLayout) inflater.inflate(
				R.layout.login_dialog_list, null);

		login_dialog_listview = (ListView) login_dialog_list
				.findViewById(R.id.login_dialog_listview);
		login_dialog_listview.setLayoutParams(new LinearLayout.LayoutParams(
				android.widget.LinearLayout.LayoutParams.FILL_PARENT,
				android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));
		login_dialog_listview.setOnItemClickListener(new OnItemClickListener() {

			private Map<String, String> map;

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {

				if (alert != null) {

					alert.dismiss();
				}

				map = product_type_list.get(position);

//				String contactInfo_id = map.get("contactInfo_id");
//				String contactInfo_zjtype = map.get("contactInfo_zjtype");
				String contactInfo_zjnumber = map.get("contactInfo_zjnumber");
				String contactInfo_name = map.get("contactInfo_name");
				String medical_cardnum = map.get("medical_cardnum");

				Bundle bundle = new Bundle();
//				bundle.putString("contactInfo_id", contactInfo_id);
//				bundle.putString("contactInfo_zjtype", contactInfo_zjtype);
				bundle.putString("contactInfo_zjnumber", contactInfo_zjnumber); 
				bundle.putString("contactInfo_name", contactInfo_name);
				bundle.putString("medical_cardnum", medical_cardnum);
				Intent intent = new Intent(PersonalCenterActivity.this,
						CyContactYYListActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);

			}
		});
		
		pc_tv_yuyue = (TextView) findViewById(R.id.pc_tv_yuyue);
		pc_im_yuyue = (ImageView) findViewById(R.id.pc_im_yuyue);
		pc_ll_yuyue = (LinearLayout) findViewById(R.id.pc_ll_yuyue);
		pc_ll_yuyue.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				setYuyue();
				pc_viewpager.setCurrentItem(0);
			}
		});

		pc_tv_cancel = (TextView) findViewById(R.id.pc_tv_cancel);
		pc_im_cancel = (ImageView) findViewById(R.id.pc_im_cancel);
		pc_ll_cancel = (LinearLayout) findViewById(R.id.pc_ll_cancel);
		pc_ll_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				setCancel();
				pc_viewpager.setCurrentItem(1);
			}
		});

		pc_tv_content = (TextView) findViewById(R.id.pc_tv_content);
		pc_im_content = (ImageView) findViewById(R.id.pc_im_content);
		pc_ll_content = (LinearLayout) findViewById(R.id.pc_ll_content);
		pc_ll_content.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				setContent();
				pc_viewpager.setCurrentItem(2);

			}
		});

		Button edit_info = (Button) findViewById(R.id.edit_info);
		edit_info.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(v.getContext(),
						EditPersonInfoActivity.class);
				startActivity(intent);
			}
		});

		TextView edit_tv_info = (TextView) findViewById(R.id.edit_tv_info);
		edit_tv_info.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(v.getContext(),
						EditPersonInfoActivity.class);
				startActivity(intent);
			}
		});

		// 联系人lv
		lv_person_center_list = (ListView) main_viewpager_button_content 
				.findViewById(R.id.lv_person_center_list);
		
		tv_person_center_editPersn = (TextView) main_viewpager_button_content.findViewById(R.id.tv_person_center_editPersn);
		tv_person_center_editPersn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				commonlyPatientListAdapter.setEdit(true);
				commonlyPatientListAdapter.notifyDataSetChanged();
			}
		});
		lv_person_center_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				PhCommonContactInfo phCommonContactInfo = painte_list
						.get(position);

				Bundle bundle = new Bundle();
				bundle.putString("use_id", phCommonContactInfo.getId());
				bundle.putString("str_ep_name",
						phCommonContactInfo.getContact_name());
				bundle.putString("str_ep_sex",
						phCommonContactInfo.getContact_sex());
				bundle.putString("str_ep_tel_no",
						phCommonContactInfo.getContact_phone());
				bundle.putString("str_ep_id_card",
						phCommonContactInfo.getZj_number());
				bundle.putString("str_ep_tv_card_type",
						phCommonContactInfo.getZj_type());
				bundle.putString("str_ep_birthday",
						phCommonContactInfo.getContact_brithday());
				bundle.putString("str_contact_add", phCommonContactInfo.getContact_add()); 
				bundle.putString("str_contact_person_name", phCommonContactInfo.getContact_person_name());
				bundle.putString("str_contact_person_phone", phCommonContactInfo.getContact_person_phone());
				bundle.putString("str_medical_cardnum", phCommonContactInfo.getMedical_cardnum());

				Intent intent = new Intent(view.getContext(),
						CommonlyPatientChangeActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);

			}
		});

		TextView lv_person_center_addPersn = (TextView) main_viewpager_button_content
				.findViewById(R.id.lv_person_center_addPersn);
		lv_person_center_addPersn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(v.getContext(),
						CommonlyPatientEditActivity.class);
				intent.putParcelableArrayListExtra("painte_list",(ArrayList<PhCommonContactInfo>)painte_list);
				startActivity(intent);

			}
		});

		pc_tv_cancel = (TextView) findViewById(R.id.pc_tv_cancel);
		pc_im_cancel = (ImageView) findViewById(R.id.pc_im_cancel);

		queryPaintData();
		Button btn_back = (Button) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private void setYuyue() {

		pc_tv_yuyue.setTextColor(Color.parseColor("#FFFFFF"));
		pc_im_yuyue.setVisibility(View.VISIBLE);

		pc_tv_cancel.setTextColor(Color.parseColor("#ededed"));
		pc_im_cancel.setVisibility(View.INVISIBLE);

		pc_tv_content.setTextColor(Color.parseColor("#ededed"));
		pc_im_content.setVisibility(View.INVISIBLE);

	}

	private void setCancel() {

		pc_tv_yuyue.setTextColor(Color.parseColor("#ededed"));
		pc_im_yuyue.setVisibility(View.INVISIBLE);

		pc_tv_cancel.setTextColor(Color.parseColor("#FFFFFF"));
		pc_im_cancel.setVisibility(View.VISIBLE);

		pc_tv_content.setTextColor(Color.parseColor("#ededed"));
		pc_im_content.setVisibility(View.INVISIBLE);

	}
	
	private void setContent() {

		pc_tv_yuyue.setTextColor(Color.parseColor("#ededed"));
		pc_im_yuyue.setVisibility(View.INVISIBLE);

		pc_tv_cancel.setTextColor(Color.parseColor("#ededed"));
		pc_im_cancel.setVisibility(View.INVISIBLE);

		pc_tv_content.setTextColor(Color.parseColor("#FFFFFF"));
		pc_im_content.setVisibility(View.VISIBLE);

	}

	private void setYuyueList() {

		listAdapter_yuyue = new PersonalCenterListAdapter(this, list_yuyue,
				true, mHandler, phUsers.getUser_Account());
		lv_pc_yuyue.setAdapter(listAdapter_yuyue);

	}

	private void setCancelList() {
		listAdapter_yuyue = new PersonalCenterListAdapter(this, list_cancel,
				false, mHandler, phUsers.getUser_Account());
		lv_pc_cancel.setAdapter(listAdapter_yuyue);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		login_dialog_list = (LinearLayout) inflater.inflate(
				R.layout.login_dialog_list, null);

		login_dialog_listview = (ListView) login_dialog_list
				.findViewById(R.id.login_dialog_listview);
		login_dialog_listview.setLayoutParams(new LinearLayout.LayoutParams(
				android.widget.LinearLayout.LayoutParams.FILL_PARENT,
				android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));
		login_dialog_listview.setOnItemClickListener(new OnItemClickListener() {

			private Map<String, String> map;

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {

				if (alert != null) {

					alert.dismiss();
				}

				map = product_type_list.get(position);

//				String contactInfo_id = map.get("contactInfo_id");
//				String contactInfo_zjtype = map.get("contactInfo_zjtype");
				String contactInfo_zjnumber = map.get("contactInfo_zjnumber");
				String contactInfo_name = map.get("contactInfo_name");
				String medical_cardnum = map.get("medical_cardnum");

				Bundle bundle = new Bundle();
//				bundle.putString("contactInfo_id", contactInfo_id);
//				bundle.putString("contactInfo_zjtype", contactInfo_zjtype);
				bundle.putString("contactInfo_zjnumber", contactInfo_zjnumber);  
				bundle.putString("contactInfo_name", contactInfo_name);
				bundle.putString("medical_cardnum", medical_cardnum);
				Intent intent = new Intent(PersonalCenterActivity.this,
						CyContactYYListActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		queryData();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();

		phUsers = Config.phUsers;
		if (phUsers != null) {
			user_name.setText(phUsers.getName());
			String sex = phUsers.getSex();
			if (Config.man.equals(sex)) {
				sex = "男";
			} else if (Config.woman.equals(sex)) {
				sex = "女";
			} else {
				sex = "";
			}
			user_sex.setText(sex);
//			user_sex.setText(phUsers.getSex().equals("01") ? "男" : "女");
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int item) {
		// TODO Auto-generated method stub

		switch (item) {

		case 0:
			setYuyue();
			// setYuyueList();
			break;
		case 1:
			setCancel();
			// setCancelList();
			break;
		case 2:
			setContent();
			// setCancelList();
			break;

		}

	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {

		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();

		// Toast.makeText(getApplicationContext(),"id="+info.id, 0).show();

		return super.onContextItemSelected(item);
	}

	public void queryData() {
		list_yuyue.clear();
		list_cancel.clear();
		pastdue =  0;
		nopastdue = 0;
		SelectTopVisitReq selectreq = new SelectTopVisitReq();
		/*selectreq.setPat_name(phUsers.getName());
		selectreq.setVst_card_no(phUsers.getMedical_cardnum());*/
		selectreq.setId_card(phUsers.getId_Card());
		selectreq.setOperType("33");
		GsonServlet<SelectTopVisitReq, SelectTopVisistRes> gsonServlet = new GsonServlet<SelectTopVisitReq, SelectTopVisistRes>(
				this);
		gsonServlet.request(selectreq, SelectTopVisistRes.class);
		gsonServlet
				.setOnResponseEndListening(new GsonServlet.OnResponseEndListening<SelectTopVisitReq, SelectTopVisistRes>() {
					@Override
					public void onResponseEnd(SelectTopVisitReq commonReq,
							SelectTopVisistRes commonRes, boolean result,
							String errmsg, int responseCode) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onResponseEndSuccess(
							SelectTopVisitReq commonReq,
							SelectTopVisistRes commonRes, String errmsg,
							int responseCode) {
						// TODO Auto-generated method stub
						if (commonRes != null) {
							List<TopVisitResInfo> visitInfo = commonRes
									.getVisitInfo();
							for (TopVisitResInfo topVisitResInfo : visitInfo) {
//								if (topVisitResInfo.getStatus().equals("0")) {
//									list_yuyue.add(topVisitResInfo);
//								} else if (topVisitResInfo.getStatus().equals(
//										"1")) {
//									list_cancel.add(topVisitResInfo);
//								}
								String convertString = DataUtils.convertString(topVisitResInfo.getReg_time(),topVisitResInfo.getReg_locate());
								convertString = convertString.substring(6);
								String compare_date = topVisitResInfo.getReg_date() + " " + convertString;
								boolean flag = DataUtils.compare_date(compare_date);
								if (topVisitResInfo.getStatus().equals("1") || flag) {
									list_cancel.add(topVisitResInfo);
								} else if(topVisitResInfo.getStatus().equals("0") && flag){
									list_cancel.add(topVisitResInfo);
								}else {
									list_yuyue.add(topVisitResInfo);
									if (flag) { //超过日期  -- 过期预约单
										pastdue++;
									} else {
										if (topVisitResInfo.getStatus().equals("2")) {//预约成功次数
											nopastdue++;
										} else if (topVisitResInfo.getStatus().equals("3")) {
											nopastdue++;
										}
									}
								}
							}
						}
						setYuyueList();
						setCancelList();
						Message msg = mHandler.obtainMessage(2);
						mHandler.sendMessage(msg); 
					}

					@Override
					public void onResponseEndErr(SelectTopVisitReq commonReq,
							SelectTopVisistRes commonRes, String errmsg,
							int responseCode) {
						// TODO Auto-generated method stub
					}
				});
	}
	
	private class BroadCast extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {

			if (intent.getAction().equals("change_contact")) {

				queryPaintData();
			}

		}
	}
	
	
	private void queryPaintData() {

		DataCache datacache = DataCache.getCache(this);
		datacache.setUrl(Config.url);

		CommonContactListReq req = new CommonContactListReq();
		req.setOperType("44");
		req.setUser_id(Config.phUsers.getId());

		GsonServlet<CommonContactListReq, CommonContactListRes> gServlet = new GsonServlet<CommonContactListReq, CommonContactListRes>(
				this);
		gServlet.request(req, CommonContactListRes.class);
		gServlet.setOnResponseEndListening(new OnResponseEndListening<CommonContactListReq, CommonContactListRes>() {


			@Override
			public void onResponseEnd(CommonContactListReq commonReq,
					CommonContactListRes commonRes, boolean result,
					String errmsg, int responseCode) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onResponseEndSuccess(CommonContactListReq commonReq,
					CommonContactListRes commonRes, String errmsg,
					int responseCode) {
				// TODO Auto-generated method stub
				if (commonRes != null) {

					painte_list = commonRes.getContactList();

					for (PhCommonContactInfo phCommonContactInfo : painte_list) {

						Map<String, String> map2 = new HashMap<String, String>();
						map2.put("contactInfo_id", phCommonContactInfo.getId());
						map2.put("contactInfo_name",
								phCommonContactInfo.getContact_name());
						map2.put("contactInfo_zjtype",
								phCommonContactInfo.getZj_type());
						map2.put("contactInfo_zjnumber",
								phCommonContactInfo.getZj_number());
						map2.put("medical_cardnum", phCommonContactInfo.getMedical_cardnum());
						product_type_list.add(map2);

					}
					Message msg = mHandler.obtainMessage(5);
					mHandler.sendMessage(msg); 
					/*SimpleAdapter adapter = new SimpleAdapter(
							getApplicationContext(), product_type_list,
							R.layout.login_dialog_window,
							new String[] { "contactInfo_name" },
							new int[] { R.id.login_dialog_item, });
					login_dialog_listview.setAdapter(adapter);
					AlertDialog.Builder radio = new AlertDialog.Builder(
							PersonalCenterActivity.this);
					radio.setTitle("请选择常用就诊人");
					radio.setView(login_dialog_list);
					alert = radio.create();*/

					commonlyPatientListAdapter = new CommonlyPatientListAdapter(
							PersonalCenterActivity.this, painte_list,mHandler);
					lv_person_center_list
							.setAdapter(commonlyPatientListAdapter);
				}
			}

			@Override
			public void onResponseEndErr(CommonContactListReq commonReq,
					CommonContactListRes commonRes, String errmsg,
					int responseCode) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), errmsg,
						Toast.LENGTH_SHORT).show();
			}

		});

	}
}
