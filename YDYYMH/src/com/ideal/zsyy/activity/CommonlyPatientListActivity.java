package com.ideal.zsyy.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.ideal.wdm.tools.DataCache;
import com.ideal.zsyy.Config;
import com.jijiang.wtapp.R;
import com.ideal.zsyy.adapter.CommonlyPatientListAdapter;
import com.ideal.zsyy.entity.PhCommonContactInfo;
import com.ideal.zsyy.request.CommonContactListReq;
import com.ideal.zsyy.response.CommonContactListRes;
import com.ideal2.base.gson.GsonServlet;
import com.ideal2.base.gson.GsonServlet.OnResponseEndListening;

/**
 * 常用就诊人
 * 
 * @author PYM
 * 
 */
public class CommonlyPatientListActivity extends Activity {

	private List<PhCommonContactInfo> list;
	private CommonlyPatientListAdapter commonlyPatientListAdapter;
	private ListView lv_commomly_patient_list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.commonly_patient_list);

		initView();

	}

	private void initView() {

		lv_commomly_patient_list = (ListView) findViewById(R.id.lv_commomly_patient_list);
		lv_commomly_patient_list
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub

						if (list != null) {

							PhCommonContactInfo phCommonContactInfo = list
									.get(position);

							Intent broadcast = new Intent("from_commomly");

							broadcast.putExtra("id",
									phCommonContactInfo.getId());
							broadcast.putExtra("name",
									phCommonContactInfo.getContact_name());

							if (phCommonContactInfo.getContact_sex().trim()
									.equals(Config.man)) {

								broadcast.putExtra("sex", "男");

							} else if (phCommonContactInfo.getContact_sex()
									.trim().equals(Config.woman)) {

								broadcast.putExtra("sex", "女");

							} else {

								broadcast.putExtra("sex", "");

							}

							broadcast.putExtra("brithday",
									phCommonContactInfo.getContact_brithday());
							broadcast.putExtra("card_type",
									phCommonContactInfo.getZj_type());
							broadcast.putExtra("card_number",
									phCommonContactInfo.getZj_number());
							broadcast.putExtra("phone",
									phCommonContactInfo.getContact_phone());
							broadcast.putExtra("contact_add", phCommonContactInfo.getContact_add());
							broadcast.putExtra("contact_person_name", phCommonContactInfo.getContact_person_name());
							broadcast.putExtra("contact_person_phone", phCommonContactInfo.getContact_person_phone());
							broadcast.putExtra("medical_cardnum", phCommonContactInfo.getMedical_cardnum());
							view.getContext().sendBroadcast(broadcast);

							finish();
						}

					}
				});

		Button btn_right = (Button) findViewById(R.id.btn_right);
		btn_right.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(v.getContext(),
						CommonlyPatientEditActivity.class);
				startActivity(intent);

			}
		});

		Button btn_back = (Button) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				finish();
			}
		});

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		queryData();
	}

	private void queryData() {

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
					list = commonRes.getContactList();
					
					PhCommonContactInfo phCommonContactInfo = new PhCommonContactInfo();
					phCommonContactInfo.setContact_name(Config.phUsers.getName());
					phCommonContactInfo.setContact_sex(Config.phUsers.getSex());
					phCommonContactInfo.setContact_brithday(Config.phUsers.getBirthday());
					phCommonContactInfo.setContact_phone(Config.phUsers.getUser_Account());
					phCommonContactInfo.setContact_add(Config.phUsers.getUser_add());
					phCommonContactInfo.setMedical_cardnum(Config.phUsers.getMedical_cardnum());
					phCommonContactInfo.setContact_person_name(Config.phUsers.getContact_person_name());
					phCommonContactInfo.setContact_person_phone(Config.phUsers.getContact_person_phone());
					
//					phCommonContactInfo.setZj_type(Config.phUsers.getZj_type());
//					phCommonContactInfo.setZj_number(Config.phUsers.getZj_number());
					if(list == null){
						
						list = new ArrayList<PhCommonContactInfo>();
					}
					list.add(phCommonContactInfo);
					
					commonlyPatientListAdapter = new CommonlyPatientListAdapter(
							getApplicationContext(), list,null);
					lv_commomly_patient_list
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
