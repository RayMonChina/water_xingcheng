package com.ideal.zsyy.activity;

import com.ideal.zsyy.Config;
import com.jijiang.wtapp.R;
import com.ideal.zsyy.entity.PhUser;
import com.ideal.zsyy.entity.TopVisitResInfo;
import com.ideal.zsyy.activity.CommonlyPatientListActivity;
import com.ideal.zsyy.activity.ConfimAppointmentActivity;
import com.ideal.zsyy.request.SmsReq;
import com.ideal.zsyy.request.TopVisitReq;
import com.ideal.zsyy.request.VisitBoundsReq;
import com.ideal.zsyy.response.SmsRes;
import com.ideal.zsyy.response.TopVisitRes;
import com.ideal.zsyy.response.VisitBoundsRes;
import com.ideal2.base.gson.GsonServlet;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ConfimAppointmentActivity extends Activity {

	private TextView tvappointmentDate, tvappointmentTime,
			tv_deptname_or_docname, tv_titel, tv_name, 
			tv_sex, tv_birthday, tv_id_card, tv_contact_person_name,
			tv_contact_person_phone;
	private String dept_id = "";
	private String doc_id = "";
	private PhUser phUsers;
	private TextView tv_user_add;
	private TextView tvLocate;
	private String locate;
	private String doc_name;
	private String dept_name;
	private int visitCount;
	private BroadCast skinBroadCast;
	private TextView tv_user_phone;
	private EditText et_medical_cardnum; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.confirm_appointment);
		
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("from_editPeron");
		intentFilter.addAction("from_commomly");
		skinBroadCast = new BroadCast();
		registerReceiver(skinBroadCast, intentFilter);
		initView(); 
	}

	private void initView() {
		tvLocate = (TextView) findViewById(R.id.tv_locate);
		tvappointmentDate = (TextView) findViewById(R.id.tv_appointment_date);
		tvappointmentTime = (TextView) findViewById(R.id.tv_appointment_time);
		tv_deptname_or_docname = (TextView) findViewById(R.id.tv_deptname_or_docname);
		tv_titel = (TextView) findViewById(R.id.tv_titel);
		tv_name = (TextView) findViewById(R.id.tv_name);
		et_medical_cardnum = (EditText) findViewById(R.id.et_medical_cardnum);
		tv_sex = (TextView) findViewById(R.id.tv_sex);
		tv_birthday = (TextView) findViewById(R.id.tv_birthday);
		tv_id_card = (TextView) findViewById(R.id.tv_id_card);
		tv_contact_person_name = (TextView) findViewById(R.id.tv_contact_person_name);
		tv_contact_person_phone = (TextView) findViewById(R.id.tv_contact_person_phone);
		tv_user_add = (TextView) findViewById(R.id.tv_user_add);
		tv_user_phone = (TextView) findViewById(R.id.tv_user_phone); 
		showData();
		
		Button btn_edit_info = (Button) findViewById(R.id.btn_edit_info);
		btn_edit_info.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				Intent intent = new Intent(ConfimAppointmentActivity.this, EditPersonInfoActivity.class);
//				startActivity(intent);
				Intent intent = new Intent(ConfimAppointmentActivity.this,
						CommonlyPatientListActivity.class);
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
		//确认预约
		Button btn_submit = (Button) findViewById(R.id.btn_submit);
		btn_submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				if (visitCount < 2) {
					sendTopVisitInfo();
//				} else {
//					AlertDialog.Builder builder = new AlertDialog.Builder(ConfimAppointmentActivity.this);
//					builder.setTitle("温馨提示:");
//					builder.setMessage("每天只能预约两个号,谢谢!"); 
//					builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//							// TODO Auto-generated method stub
//							finish();
//						}
//					});
//					builder.create().show(); 
//				}
			}
		});
	}
	
	@SuppressLint("NewApi")
	protected void sendTopVisitInfo() {
		String dutydate = tvappointmentDate.getText().toString();
		String[] split = dutydate.split(" ");
		String dutytime = tvappointmentTime.getText().toString();
		String deptname_or_docname = tv_deptname_or_docname.getText().toString();
		String medical_cardnum = et_medical_cardnum.getText().toString();
		String name = tv_name.getText().toString();
		String birthday = tv_birthday.getText().toString();
		String sex = tv_sex.getText().toString();
		String contactName = tv_contact_person_name.getText().toString();
		String contactPhone = tv_contact_person_phone.getText().toString();
		String userAdd = tv_user_add.getText().toString();
		String userIDCard = tv_id_card.getText().toString();
		String user_phone = tv_user_phone.getText().toString();
		/*if (medical_cardnum.equals("")) {
			Toast.makeText(ConfimAppointmentActivity.this, "预约人就诊卡号不能为空", 1).show();
			return;
		} */
		if (name.equals("")) {
			Toast.makeText(ConfimAppointmentActivity.this, "预约人姓名不能为空", 1).show();
			return;
		}
		if (sex.equals("")) {
			Toast.makeText(ConfimAppointmentActivity.this, "预约人性别不能为空", 1).show();
			return;
		}
		if (birthday.equals("")) {
			Toast.makeText(ConfimAppointmentActivity.this, "预约人出生日期不能为空", 1).show();
			return;
		}
		if (userIDCard.equals("")) {
			Toast.makeText(ConfimAppointmentActivity.this, "预约人身份证不能为空", 1).show();
			return;
		}
		if (userAdd.equals("")) {
			Toast.makeText(ConfimAppointmentActivity.this, "预约人住址不能为空", 1).show();
			return;
		}
		if (user_phone.equals("")) { 
			Toast.makeText(ConfimAppointmentActivity.this, "预约人手机号码不能为空", 1).show();
			return;
		}
		
//		if (!medical_cardnum.isEmpty() && !deptname_or_docname.isEmpty() && !name.isEmpty() && !birthday.isEmpty()
//				&& !sex.isEmpty() && !contactName.isEmpty() && !contactPhone.isEmpty() && !userAdd.isEmpty() && !userIDCard.isEmpty()) {
			TopVisitReq visitreq = new TopVisitReq();
			TopVisitResInfo visitinfo = new TopVisitResInfo(); 
			visitinfo.setPat_dob(birthday);
			visitinfo.setPat_name(name);
			if(sex.equals("男")){
				visitinfo.setPat_sex(Config.man);
			}else if(sex.equals("女")){
				visitinfo.setPat_sex(Config.woman);
			}else{
				visitinfo.setPat_sex("0");
			}
//			visitinfo.setPat_sex(sex.equals("男")?"01":"02");
			visitinfo.setPat_add(userAdd);
			visitinfo.setPat_phone(user_phone); 
			visitinfo.setPat_id_card(userIDCard);
			visitinfo.setReg_locate(locate);
			visitinfo.setReg_date(split[0]); 
			visitinfo.setReg_time(dutytime.replace(":", "").replace("-", ""));
			visitinfo.setVst_card_no(medical_cardnum);
			if ("".equals(doc_id)) {
				visitinfo.setReg_dept_id(dept_id);
				visitinfo.setReg_dept_name(deptname_or_docname);
				visitinfo.setReg_doc_id("");
				visitinfo.setReg_doc_name("");
			} else {
//				visitinfo.setReg_dept_id("");
//				visitinfo.setReg_dept_name("");
				visitinfo.setReg_doc_id(doc_id);
				visitinfo.setReg_doc_name(doc_name);
				visitinfo.setReg_dept_id(dept_id);
				visitinfo.setReg_dept_name(dept_name);
			}
			visitreq.setTopvisitinfo(visitinfo);
			visitreq.setOperType("32"); 
			sendTopVisitInfoReq(visitreq);
//		} else {
//			Toast.makeText(this, "信息不能为空!!", 1).show(); 
//		}
	}

	private void sendTopVisitInfoReq(final TopVisitReq visitreq) {
		GsonServlet<TopVisitReq, TopVisitRes> gsonServlet = new GsonServlet<TopVisitReq, TopVisitRes>(this);
		gsonServlet.request(visitreq, TopVisitRes.class);
		gsonServlet.setOnResponseEndListening(new GsonServlet.OnResponseEndListening<TopVisitReq, TopVisitRes>() {

			@Override
			public void onResponseEnd(TopVisitReq commonReq,
					TopVisitRes commonRes, boolean result, String errmsg,
					int responseCode) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onResponseEndSuccess(TopVisitReq commonReq,
					TopVisitRes commonRes, String errmsg, int responseCode) {
				// TODO Auto-generated method stub
				if (commonRes != null) {
					AlertDialog.Builder builder = new AlertDialog.Builder(ConfimAppointmentActivity.this);
					View view = LayoutInflater.from(ConfimAppointmentActivity.this).inflate(R.layout.dialog1, null);
					TextView showContent = (TextView) view.findViewById(R.id.tv_show_content);
					TextView tv_select_visitlist = (TextView) view.findViewById(R.id.tv_select_visitlist);
					tv_select_visitlist.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(ConfimAppointmentActivity.this,PersonalCenterActivity.class);
							startActivity(intent); 
							finish();
						}
					});
					
					TopVisitResInfo topvisitinfo = visitreq.getTopvisitinfo();
					showContent.setText(commonRes.getSmscontent());
					builder.setView(view);
					builder.create().show();
					SmsReq smsreq = new SmsReq();
					smsreq.setDest_number(topvisitinfo.getPat_phone()); 
					smsreq.setContent(commonRes.getSmscontent());
					smsreq.setOperType("102");
					sendSmsMessge(smsreq);
					
					
//					StringBuilder sb = new StringBuilder();
//					sb.append(topvisitinfo.getPat_name() + ",您好,您已成功预约" + tvLocate.getText().toString().trim() + "," + tvappointmentDate.getText().toString().trim() + "," + tvappointmentTime.getText().toString().trim());
//					if (!"".equals(topvisitinfo.getReg_doc_id())) {
//						sb.append(topvisitinfo.getReg_doc_name() + "的" + topvisitinfo.getReg_dept_name());
//					} else if (!"".equals(doc_id)) {
//						sb.append(topvisitinfo.getReg_dept_name());
//					}
//					sb.append(",医院北部公交直达线路：13、63、921、17、910、6、61、55、14、147、123、100、576、21、22、145、轨道交通4号线                  医院南部公交直达线路：松莘线、松昆线、松江9路、松江11路、地铁9号线");
//					sb.append(",请准时来院挂号处或一站式自助机上办理,请带好您的就诊卡号和最近化验单来院就诊,您的预约编号为:" + commonRes.getSeqn().substring(16));
//					showContent.setText(sb.toString());
//					builder.setView(view);
//					builder.create().show();
//					SmsReq smsreq = new SmsReq();
//					smsreq.setDest_number(topvisitinfo.getPat_phone()); 
//					smsreq.setContent(sb.toString() + "(市一医院)");
//					smsreq.setOperType("102");
//					sendSmsMessge(smsreq);
//					
//					if (!phUsers.getUser_Account().equals(topvisitinfo.getPat_phone())) {
//						String msg2 = phUsers.getName() + ",您好,您已成功预约" + tvLocate.getText().toString() + tvappointmentDate.getText().toString() + tvappointmentTime.getText().toString();
//						if (!"".equals(topvisitinfo.getReg_doc_id())) {
//							msg2 += topvisitinfo.getReg_doc_name() + "的" + topvisitinfo.getReg_dept_name();
//						} else if (!"".equals(doc_id)) {
//							msg2 += topvisitinfo.getReg_dept_name();
//						}
//						msg2 += ",请就诊人"+topvisitinfo.getPat_name()+"准时来院挂号处或一站式自助机上办理,您的预约编号为:" + commonRes.getSeqn().substring(16);
//						SmsReq smsreq2 = new SmsReq();
//						smsreq2.setDest_number(phUsers.getUser_Account());  
//						smsreq2.setContent(msg2 + "(市一医院)"); 
//						smsreq2.setOperType("102");
//						sendSmsMessge(smsreq2); 
//					}
				}
			}

			@Override
			public void onResponseEndErr(TopVisitReq commonReq,
					TopVisitRes commonRes, String errmsg, int responseCode) {
				// TODO Auto-generated method stub
				Toast.makeText(ConfimAppointmentActivity.this, errmsg, 1).show(); 
			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}

	private void showData() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		String dutydate = intent.getStringExtra("dutydate");
		String noontype = intent.getStringExtra("noontype");
		String dutytime = intent.getStringExtra("dutytime");
		locate = intent.getStringExtra("locate");
		dept_id = intent.getStringExtra("dept_id");
		doc_id = intent.getStringExtra("doc_id"); 
		dept_name = intent.getStringExtra("dept_name");
		doc_name = intent.getStringExtra("doc_name"); 
		if ("01".equals(noontype)) {
			dutydate += " 上午";
		} else if ("02".equals(noontype)) {
			dutydate += " 下午";
		}
		tvappointmentDate.setText(dutydate);
		tvappointmentTime.setText(dutytime);
		if ("".equals(doc_id)) { 
			tv_titel.setText("预约科室:");
			tv_deptname_or_docname.setText(dept_name);
			
		} else {
			tv_titel.setText("预约专家:");
			String str = doc_name;
			if (dept_name != null && !"".equals(dept_name)) {
				str = str  + "(" + dept_name + ")";
			}
			tv_deptname_or_docname.setText(str);
		}
//		if (!"".equals(dept_id)) {
//		} else if (!"".equals(doc_id)) {
//		}
		if ("S".equals(locate)) {
			tvLocate.setText("上海市第一个人民医院南院");
		} else if ("N".equals(locate)) {
			tvLocate.setText("上海市第一个人民医院北院");
		}
		phUsers = Config.phUsers;
		if (phUsers != null) {
			et_medical_cardnum.setText(phUsers.getMedical_cardnum());
			tv_name.setText(phUsers.getName());
			String sex_no = phUsers.getSex();
			if(sex_no.equals(Config.man)){
				tv_sex.setText("男");
			}else if(sex_no.equals(Config.woman)){
				tv_sex.setText("女");
			}else{
				tv_sex.setText("");
			}
//			tv_sex.setText(phUsers.getSex().equals("01")?"男":"女"); 
			tv_id_card.setText(phUsers.getId_Card());
			tv_birthday.setText(phUsers.getBirthday());
			tv_contact_person_name.setText(phUsers.getContact_person_name());
			tv_contact_person_phone.setText(phUsers.getContact_person_phone());
			tv_user_add.setText(phUsers.getUser_add());
			tv_user_phone.setText(phUsers.getUser_Account()); 
		}
//		getBoundsCount(tv_name.getText().toString());  
	}
	protected void sendSmsMessge(SmsReq smsreq) {
		// TODO Auto-generated method stub
		GsonServlet<SmsReq, SmsRes> gsonServlet = new GsonServlet<SmsReq, SmsRes>(this);
		gsonServlet.request(smsreq, SmsRes.class);
		gsonServlet.setShowDialog(false);
		gsonServlet.setOnResponseEndListening(new GsonServlet.OnResponseEndListening<SmsReq, SmsRes>() {
			@Override
			public void onResponseEnd(SmsReq commonReq, SmsRes commonRes,
					boolean result, String errmsg, int responseCode) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onResponseEndSuccess(SmsReq commonReq,
					SmsRes commonRes, String errmsg, int responseCode) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onResponseEndErr(SmsReq commonReq, SmsRes commonRes,
					String errmsg, int responseCode) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public void getBoundsCount(String name){
		VisitBoundsReq visitReq = new VisitBoundsReq();
		visitReq.setPat_name(name);
		visitReq.setOperType("42"); 
		GsonServlet<VisitBoundsReq, VisitBoundsRes> gsonServlet = new GsonServlet<VisitBoundsReq, VisitBoundsRes>(this);
		gsonServlet.request(visitReq, VisitBoundsRes.class);
		gsonServlet.setShowDialog(false);
		gsonServlet.setOnResponseEndListening(new GsonServlet.OnResponseEndListening<VisitBoundsReq, VisitBoundsRes>() {
			@Override
			public void onResponseEnd(VisitBoundsReq commonReq,
					VisitBoundsRes commonRes, boolean result, String errmsg,
					int responseCode) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onResponseEndSuccess(VisitBoundsReq commonReq,
					VisitBoundsRes commonRes, String errmsg, int responseCode) {
				// TODO Auto-generated method stub
				visitCount = commonRes.getCount();
			}

			@Override
			public void onResponseEndErr(VisitBoundsReq commonReq,
					VisitBoundsRes commonRes, String errmsg, int responseCode) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private class BroadCast extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {

			if (intent.getAction().equals("from_editPeron")) {

//				phUsers = Config.phUsers;
//
//				if (phUsers != null) {
//
//					flag_content_id = phUsers.getId();
//
//					tv_name.setText(phUsers.getName());
//
//					if (phUsers.getSex() != null) {
//
//						if (phUsers.getSex().trim().equals("1")) {
//
//							// sex = "1";
//							tv_sex.setText("男");
//
//						} else if (phUsers.getSex().trim().equals("2")) {
//
//							// sex = "2";
//							tv_sex.setText("女");
//						} else {
//
//							tv_sex.setText("");
//						}
//					}
//
//					tv_birthday.setText(phUsers.getBirthday());
//
//					tv_id_cardType.setText(phUsers.getZj_type());
//
//					tv_id_card.setText(phUsers.getZj_number());
//
//					tv_contact_person_name.setText(phUsers.getRegister_Phone());
//				}

			} else if (intent.getAction().equals("from_commomly")) {

				String id = intent.getStringExtra("id");

//				flag_content_id = id;

				String name = intent.getStringExtra("name");
				String sex = intent.getStringExtra("sex");
				String brithday = intent.getStringExtra("brithday");
				String card_type = intent.getStringExtra("card_type");
				String card_number = intent.getStringExtra("card_number");
				String phone = intent.getStringExtra("phone");
				String contact_add = intent.getStringExtra("contact_add");
				String contact_person_name = intent.getStringExtra("contact_person_name");
				String contact_person_phone = intent.getStringExtra("contact_person_phone");
				String medical_cardnum = intent.getStringExtra("medical_cardnum");

				et_medical_cardnum.setText(medical_cardnum);
				tv_name.setText(name);
				tv_sex.setText(sex);
				tv_birthday.setText(brithday);
				tv_id_card.setText(card_number);
				tv_user_add.setText(contact_add);
				tv_user_phone.setText(phone); 
				tv_contact_person_name.setText(contact_person_name);
				tv_contact_person_phone.setText(contact_person_phone);
				
				
//				tv_id_cardType.setText(card_type);
//				tv_id_card.setText(card_number);

			}

		}
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub

		unregisterReceiver(skinBroadCast);

		super.onDestroy();
	}
}
