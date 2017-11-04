package com.ideal.zsyy.activity;

import java.text.ParseException;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ideal.wdm.tools.DataCache;
import com.ideal.zsyy.Config;
import com.jijiang.wtapp.R;
import com.ideal.zsyy.entity.PhUser;
import com.ideal.zsyy.entity.UserEntity;
import com.ideal.zsyy.request.UserModifyReq;
import com.ideal.zsyy.response.UserModifyRes;
import com.ideal.zsyy.service.PreferencesService;
import com.ideal.zsyy.utils.IDCardUtil;
import com.ideal2.base.gson.GsonServlet;
import com.ideal2.base.gson.GsonServlet.OnResponseEndListening;

public class EditPersonInfoActivity extends Activity {

	private PhUser phUsers;

	private TextView ep_name;
	private TextView ep_sex;
	private EditText ep_email;
	private EditText ep_tel_no;
	private EditText ep_id_card;

	private EditText et_contact_person_name;

	private EditText et_contact_person_phone;

	private TextView ep_birthday;

	private EditText et_medical_cardnum;

	private Calendar c;

	private int _year;

	private int _month;

	private int _day;

	private EditText et_user_add;

	private String editmsg;

	private boolean isFirst;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editpersoninfo);

		phUsers = Config.phUsers;
		c = Calendar.getInstance();
		_year = 1990;
		_month = 00;
		_day = 01;

		editmsg = getIntent().getStringExtra("editmsg");
		if (phUsers.getBirthday() != null && !"".equals(phUsers.getBirthday())) {
			String birthday = phUsers.getBirthday();
			if (birthday.contains("-")) {
				String[] split = birthday.split("-");

				if (split.length == 3) {
					_year = Integer.parseInt(split[0]);
					_month = Integer.parseInt(split[1]) - 1;
					_day = Integer.parseInt(split[2]);
				}
			}
		}
		isFirst = getIntent().getBooleanExtra("isFirst", false); 
		initView();

	}

	private void initView() {

		ep_name = (TextView) findViewById(R.id.ep_name);
		ep_name.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(v.getContext(), "姓名不可再修改", 0).show();

			}
		});
		ep_sex = (TextView) findViewById(R.id.ep_sex);
		final String[] sexlist = new String[] { "男", "女" };
		ep_sex.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(
						EditPersonInfoActivity.this);
				builder.setTitle("请选择性别...");
				builder.setItems(sexlist,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								String sex = sexlist[which];
								ep_sex.setText(sex);
							}
						});
				builder.create().show();
			}
		});
		ep_email = (EditText) findViewById(R.id.ep_email);
		// ep_tel_no = (EditText) findViewById(R.id.ep_tel_no);
		ep_id_card = (EditText) findViewById(R.id.ep_id_card);
		et_medical_cardnum = (EditText) findViewById(R.id.et_medical_cardnum);
		et_contact_person_name = (EditText) findViewById(R.id.et_contact_person_name);
		et_contact_person_phone = (EditText) findViewById(R.id.et_contact_person_phone);
		et_user_add = (EditText) findViewById(R.id.et_user_add);
		ep_birthday = (TextView) findViewById(R.id.ep_birthday);
		ep_birthday.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(0);
			}
		});
		if (phUsers != null) {

			ep_name.setText(phUsers.getName());
			String sex = phUsers.getSex();
			if (sex.equals(Config.man)) {
				ep_sex.setText("男");
			} else if (sex.equals(Config.woman)) {
				ep_sex.setText("女");
			} else {
				ep_sex.setText("");
			}
			// ep_sex.setText(phUsers.getSex().equals("01") ? "男" : "女");
			ep_email.setText(phUsers.getEMail());
			// ep_tel_no.setText(phUsers.getRegister_Phone());
			ep_id_card.setText(phUsers.getId_Card());
			if(phUsers.getId_Card()==null || phUsers.getId_Card().equals("")){
				ep_id_card.setEnabled(true);
			}else{
				ep_id_card.setInputType(InputType.TYPE_NULL);
				ep_id_card.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Toast.makeText(v.getContext(), "身份证号码不可再修改", 0).show();
						
					}
				});
			}
			ep_birthday.setText(phUsers.getBirthday());
			et_medical_cardnum.setText(phUsers.getMedical_cardnum());
			et_contact_person_name.setText(phUsers.getContact_person_name());
			et_contact_person_phone.setText(phUsers.getContact_person_phone());
			et_user_add.setText(phUsers.getUser_add());
		}

		Button ep_submit = (Button) findViewById(R.id.ep_submit);
		ep_submit.setOnClickListener(new OnClickListener() {
			private UserEntity entity; 
			@Override
			public void onClick(View v) {

				entity = new UserEntity();

				PreferencesService preferencesService = new PreferencesService(
						v.getContext());

				String use_id = preferencesService.getLoginInfo().get("use_id")
						.toString();

				if (use_id != null) {

					entity.setUserId(use_id);
				}
				try {
					if (ep_name.getText().toString().trim().equals("")) {
						Toast.makeText(EditPersonInfoActivity.this, "您的姓名未填写",
								1).show();
						ep_name.setBackgroundResource(R.drawable.settext_bg);
						return;
					} else {
						ep_name.setBackgroundDrawable(null);
					}
					/*
					 * if (et_medical_cardnum.getText().toString().trim()
					 * .equals("")) {
					 * Toast.makeText(EditPersonInfoActivity.this, "您的就诊卡号未填写",
					 * 1).show(); et_medical_cardnum
					 * .setBackgroundResource(R.drawable.settext_bg); return; }
					 * else { et_medical_cardnum.setBackgroundDrawable(null); }
					 */

					if (ep_id_card.getText().toString().trim().equals("")) {
						Toast.makeText(EditPersonInfoActivity.this, "您的身份证未填写",
								1).show();
						ep_id_card.setBackgroundResource(R.drawable.settext_bg);
						return;
					} else {
						ep_id_card.setBackgroundDrawable(null);
						try {
							if (IDCardUtil.IDCardValidate(
									ep_id_card.getText().toString().trim())
									.equals("")) {

								if (!IDCardUtil.isBirthday(
										ep_id_card.getText().toString().trim())
										.equals(ep_birthday.getText()
												.toString().trim())) {
									Toast.makeText(getApplicationContext(),
											"生日与身份证不符合", 0).show();
									return;
								}
							} else {

								Toast.makeText(
										getApplicationContext(),
										IDCardUtil.IDCardValidate(ep_id_card
												.getText().toString().trim()),
										0).show();
								return;
							}

						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							Toast.makeText(getApplicationContext(), "失败", 0)
									.show();
							e1.printStackTrace();
						}
					}

					if (et_user_add.getText().toString().trim().equals("")) {
						Toast.makeText(EditPersonInfoActivity.this, "您的住址未填写",
								1).show();
						et_user_add
								.setBackgroundResource(R.drawable.settext_bg);
						return;
					} else {
						et_user_add.setBackgroundDrawable(null);
					}

					String sex = ep_sex.getText().toString();
					if (sex.equals("男")) {
						entity.setSex(Config.man);
					} else if (sex.equals("女")) {
						entity.setSex(Config.woman);
					} else {
						entity.setSex("0");
					}
					entity.setName(ep_name.getText().toString());
					// entity.setSex(sex.equals("男") ? "01" : "02");
					entity.setEMail(ep_email.getText().toString());
					// entity.set(ep_tel_no.getText().toString());
					entity.setId_Card(ep_id_card.getText().toString());
					entity.setBirthday(ep_birthday.getText().toString());
					entity.setContact_person_name(et_contact_person_name
							.getText().toString());
					entity.setContact_person_phone(et_contact_person_phone
							.getText().toString());
					entity.setMedical_cardnum(et_medical_cardnum.getText()
							.toString());
					entity.setUser_add(et_user_add.getText().toString());
					if (isFirst) {
						AlertDialog.Builder builder = new AlertDialog.Builder(EditPersonInfoActivity.this);
						builder.setTitle("温馨提示:");
						builder.setMessage("提交后不能再修改"); 
						builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								queryData(entity);
							}
						});
						builder.create().show(); 
					}
					queryData(entity);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		Button btn_back = (Button) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

	}

	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		if (id == 0) {
			return new DatePickerDialog(this, new OnDateSetListener() {

				@Override
				public void onDateSet(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					// TODO Auto-generated method stub
					_year = year;
					_month = monthOfYear + 1;
					_day = dayOfMonth;
					String month = "";
					String day = "";
					if (_month < 10) {
						month = "0" + _month;
					} else {
						month = _month + "";
					}
					if (_day < 10) {
						day = "0" + _day;
					} else {
						day = _day + "";
					}
					ep_birthday.setText(_year + "-" + month + "-" + day);
				}

			}, _year, _month, _day);
		}
		return null;
	}

	private void queryData(UserEntity userEntity) {

		DataCache datacache = DataCache.getCache(this);
		datacache.setUrl(Config.url);

		UserModifyReq req = new UserModifyReq();
		req.setOperType("11");
		req.setPhUser(userEntity);

		GsonServlet<UserModifyReq, UserModifyRes> gServlet = new GsonServlet<UserModifyReq, UserModifyRes>(
				this);
		gServlet.request(req, UserModifyRes.class);
		gServlet.setOnResponseEndListening(new OnResponseEndListening<UserModifyReq, UserModifyRes>() {

			@Override
			public void onResponseEnd(UserModifyReq commonReq,
					UserModifyRes commonRes, boolean result, String errmsg,
					int responseCode) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onResponseEndSuccess(UserModifyReq commonReq,
					UserModifyRes commonRes, String errmsg, int responseCode) {

				// TODO Auto-generated method stub
				if (commonRes != null) {
					if ("report".equals(editmsg)) {
						finish();
					}
					if (commonRes.getResult() == 1) {

						updateInfo();
						// startActivity(new Intent(EditPersonInfoActivity.this,
						// PersonalCenterActivity.class));
					} else {

						Toast.makeText(getApplicationContext(), "修改失败",
								Toast.LENGTH_SHORT).show();
					}

				} else {

					Toast.makeText(getApplicationContext(), "修改失败",
							Toast.LENGTH_SHORT).show();
				}

				finish();

			}

			@Override
			public void onResponseEndErr(UserModifyReq commonReq,
					UserModifyRes commonRes, String errmsg, int responseCode) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), errmsg,
						Toast.LENGTH_SHORT).show();
				finish();
			}

		});

	}

	private void updateInfo() {

		if (Config.phUsers != null) {

			String sex = ep_sex.getText().toString();
			Config.phUsers.setName(ep_name.getText().toString());
			if (sex.equals("男")) {
				Config.phUsers.setSex(Config.man);
			} else if (sex.equals("女")) {
				Config.phUsers.setSex(Config.woman);
			} else {
				Config.phUsers.setSex("0");
			}
			// Config.phUsers.setSex(sex.equals("男") ? "01" : "02");

			Config.phUsers.setEMail(ep_email.getText().toString());

			// Config.phUsers.setRegister_Phone(ep_tel_no.getText().toString());

			Config.phUsers.setId_Card(ep_id_card.getText().toString());

			Config.phUsers.setBirthday(ep_birthday.getText().toString());
			Config.phUsers.setContact_person_name(et_contact_person_name
					.getText().toString());
			Config.phUsers.setContact_person_phone(et_contact_person_phone
					.getText().toString());
			Config.phUsers.setMedical_cardnum(et_medical_cardnum.getText()
					.toString());
			Config.phUsers.setUser_add(et_user_add.getText().toString());
			finish();
		}

	}

}
