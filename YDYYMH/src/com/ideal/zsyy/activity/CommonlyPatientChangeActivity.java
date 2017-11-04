package com.ideal.zsyy.activity;

import java.text.ParseException;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.ideal.zsyy.entity.PhCommonContactInfo;
import com.ideal.zsyy.request.PhCommonContactInfoReq;
import com.ideal.zsyy.request.ZJLXInfoReq;
import com.ideal.zsyy.response.PhCommonContactInfoRes;
import com.ideal.zsyy.response.ZJLXInfoRes;
import com.ideal.zsyy.utils.IDCardUtil;
import com.ideal2.base.gson.GsonServlet;
import com.ideal2.base.gson.GsonServlet.OnResponseEndListening;

/**
 * 
 * @author PYM
 * 
 */
public class CommonlyPatientChangeActivity extends Activity {

	private TextView ep_name;
	private TextView ep_sex;
	private EditText ep_tel_no;
	private EditText ep_id_card;
	private TextView ep_tv_card_type;
	private TextView ep_birthday;

	private String str_ep_name;
	private String str_ep_sex;
	private String str_ep_tel_no;
	private String str_ep_id_card;
	private String str_ep_tv_card_type;
	private String str_ep_birthday;
	private String str_contact_person_name;
	private String str_contact_person_phone;
	private String str_medical_cardnum;
	private String str_contact_add;

	private Calendar c;
	private int _year;
	private int _month;
	private int _day;
	private String[] cardTypelist;
	private String use_id;
	private EditText contact_person_name;
	private EditText contact_person_phone;
	private EditText medical_cardnum;
	private EditText contact_add;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.commonly_patient_edit);
		Bundle bundle = getIntent().getExtras();
		use_id = bundle.getString("use_id");
		str_ep_name = bundle.getString("str_ep_name");
		str_ep_sex = bundle.getString("str_ep_sex");
		str_ep_tel_no = bundle.getString("str_ep_tel_no");
		str_ep_id_card = bundle.getString("str_ep_id_card");
		str_ep_tv_card_type = bundle.getString("str_ep_tv_card_type");
		str_ep_birthday = bundle.getString("str_ep_birthday");
		str_contact_person_name = bundle.getString("str_contact_person_name");
		str_contact_person_phone = bundle.getString("str_contact_person_phone");
		str_medical_cardnum = bundle.getString("str_medical_cardnum");
		str_contact_add = bundle.getString("str_contact_add");

		if (str_ep_sex.equals(Config.man)) {

			str_ep_sex = "男";
		} else if (str_ep_sex.equals(Config.woman)) {

			str_ep_sex = "女";
		} else {

			str_ep_sex = "";
		}

		c = Calendar.getInstance();
		_year = 1990;
		_month = 00;
		_day = 01;
		if (str_ep_birthday != null && !"".equals(str_ep_birthday)) {
			String birthday = str_ep_birthday;
			if (birthday.contains("-")) {
				String[] split = birthday.split("-");

				if (split.length == 3) {
					_year = Integer.parseInt(split[0]);
					_month = Integer.parseInt(split[1]) - 1;
					_day = Integer.parseInt(split[2]);
				}
			}
		}

		initView();

		// queryCardTypeData();

	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	private void initView() {

		TextView top_title = (TextView) findViewById(R.id.top_title);
		top_title.setText("修改常用就诊人");

		ep_name = (EditText) findViewById(R.id.ep_name);
		ep_name.setText(str_ep_name);
		ep_name.setInputType(InputType.TYPE_NULL);
		ep_name.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(v.getContext(), "姓名不可再修改", 0).show();

			}
		});

		ep_sex = (TextView) findViewById(R.id.ep_sex);
		ep_sex.setText(str_ep_sex);
		final String[] sexlist = new String[] { "男", "女" };
		ep_sex.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(
						CommonlyPatientChangeActivity.this);
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

		ep_tel_no = (EditText) findViewById(R.id.ep_tel_no);
		ep_tel_no.setText(str_ep_tel_no);

		ep_id_card = (EditText) findViewById(R.id.ep_id_card);
		ep_id_card.setText(str_ep_id_card);
		ep_id_card.setInputType(InputType.TYPE_NULL);
		ep_id_card.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(v.getContext(), "身份证号码不可再修改", 0).show();

			}
		});

		ep_birthday = (TextView) findViewById(R.id.ep_birthday);
		ep_birthday.setText(str_ep_birthday);
		ep_birthday.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(0);
			}
		});
		ep_tv_card_type = (TextView) findViewById(R.id.ep_tv_card_type);
		ep_tv_card_type.setText(str_ep_tv_card_type);
		/*
		 * ep_tv_card_type.setOnClickListener(new View.OnClickListener() {
		 * 
		 * @Override public void onClick(View v) {
		 * 
		 * Toast.makeText(v.getContext(), "不允许修改", 0).show(); } });
		 */

		// ep_tv_card_type.setOnClickListener(new View.OnClickListener() {
		// @Override
		// public void onClick(View v) {
		//
		// if(!ep_tv_card_type.getText().toString().trim().equals("")){
		//
		// Toast.makeText(v.getContext(), "不允许修改", 0).show();
		//
		// }else{
		//
		// AlertDialog.Builder builder = new AlertDialog.Builder(
		// CommonlyPatientChangeActivity.this);
		// builder.setTitle("请选择卡类型...");
		// builder.setItems(cardTypelist,
		// new DialogInterface.OnClickListener() {
		// @Override
		// public void onClick(DialogInterface dialog,
		// int which) {
		// // TODO Auto-generated method stub
		// String type = cardTypelist[which];
		// ep_tv_card_type.setText(type);
		// }
		// });
		// builder.create().show();
		// }
		//
		//
		// }
		// });

		contact_person_name = (EditText) findViewById(R.id.et_contact_person_name);
		contact_person_name.setText(str_contact_person_name);

		contact_person_phone = (EditText) findViewById(R.id.et_contact_person_phone);
		contact_person_phone.setText(str_contact_person_phone);

		medical_cardnum = (EditText) findViewById(R.id.et_medical_cardnum);
		medical_cardnum.setText(str_medical_cardnum);

		contact_add = (EditText) findViewById(R.id.et_contact_add);
		contact_add.setText(str_contact_add);

		Button ep_submit = (Button) findViewById(R.id.ep_submit);
		ep_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (!ep_name.getText().toString().trim().equals("")
						&& !ep_sex.getText().toString().trim().equals("")
						&& !ep_tel_no.getText().toString().trim().equals("")
						&& !ep_id_card.getText().toString().trim().equals("")
						&& !ep_tv_card_type.getText().toString().trim()
								.equals("")
						&& !ep_birthday.getText().toString().trim().equals("")
						&& !medical_cardnum.getText().toString().trim()
								.equals("")
						&& !contact_add.getText().toString().equals("")
						&& !contact_person_name.getText().toString().equals("")
						&& !contact_person_phone.getText().toString()
								.equals("")) {

					if (ep_id_card.getText().toString().trim().equals("")) {
						Toast.makeText(CommonlyPatientChangeActivity.this,
								"您的身份证未填写", 1).show();
						ep_id_card.setBackgroundResource(R.drawable.settext_bg);
						ep_id_card.setEnabled(true);
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
									ep_id_card.setEnabled(true);
									ep_birthday
											.setBackgroundResource(R.drawable.settext_bg);
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
					ep_birthday.setBackgroundDrawable(null);
					if (!IDCardUtil.isMobileNO(ep_tel_no.getText().toString()
							.trim())) {
						Toast.makeText(CommonlyPatientChangeActivity.this,
								"您输入的手机号码无效。", 1).show();
						return;
					}
					if (!IDCardUtil.isMobileNO(contact_person_phone.getText()
							.toString().trim())) {
						Toast.makeText(CommonlyPatientChangeActivity.this,
								"您输入的联系人手机号码无效。", 1).show();
						return;
					}
					PhCommonContactInfo entity = new PhCommonContactInfo();

					if (use_id != null) {

						entity.setId(use_id);
						entity.setContact_name(ep_name.getText().toString());

						if (ep_sex.getText().toString().trim().equals("男")) {

							entity.setContact_sex(Config.man);

						} else if (ep_sex.getText().toString().trim()
								.equals("女")) {

							entity.setContact_sex(Config.woman);
						} else {

							entity.setContact_sex("");
						}

						// entity.setEMail(ep_email.getText().toString());
						entity.setContact_phone(ep_tel_no.getText().toString());
						// entity.setId_Card(ep_id_card.getText().toString());
						entity.setContact_brithday(ep_birthday.getText()
								.toString());

						entity.setZj_type(ep_tv_card_type.getText().toString());
						entity.setZj_number(ep_id_card.getText().toString());
						entity.setContact_add(contact_add.getText().toString());
						entity.setContact_person_name(contact_person_name
								.getText().toString());
						entity.setContact_person_phone(contact_person_phone
								.getText().toString());
						entity.setMedical_cardnum(medical_cardnum.getText()
								.toString());
						// entity.setIsvisit("0");
						queryAddData(entity);
					}
				} else {

					Toast.makeText(getApplicationContext(), "请将信息填写完整", 0)
							.show();
				}

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

	private void queryAddData(PhCommonContactInfo contactInfo) {

		DataCache datacache = DataCache.getCache(this);
		datacache.setUrl(Config.url);

		PhCommonContactInfoReq req = new PhCommonContactInfoReq();
		req.setOperType("45");
		req.setContactInfo(contactInfo);

		GsonServlet<PhCommonContactInfoReq, PhCommonContactInfoRes> gServlet = new GsonServlet<PhCommonContactInfoReq, PhCommonContactInfoRes>(
				this);
		gServlet.request(req, PhCommonContactInfoRes.class);
		gServlet.setOnResponseEndListening(new OnResponseEndListening<PhCommonContactInfoReq, PhCommonContactInfoRes>() {

			@Override
			public void onResponseEnd(PhCommonContactInfoReq commonReq,
					PhCommonContactInfoRes commonRes, boolean result,
					String errmsg, int responseCode) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onResponseEndSuccess(PhCommonContactInfoReq commonReq,
					PhCommonContactInfoRes commonRes, String errmsg,
					int responseCode) {

				// TODO Auto-generated method stub

				if (commonRes != null) {

					if (commonRes.getResult().equals("1")) {

						Intent broadcast = new Intent("change_contact");
						sendBroadcast(broadcast);
						Toast.makeText(getApplicationContext(), "成功",
								Toast.LENGTH_SHORT).show();

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
			public void onResponseEndErr(PhCommonContactInfoReq commonReq,
					PhCommonContactInfoRes commonRes, String errmsg,
					int responseCode) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), errmsg,
						Toast.LENGTH_SHORT).show();
				finish();
			}

		});

	}

	private void queryCardTypeData() {

		DataCache datacache = DataCache.getCache(this);
		datacache.setUrl(Config.url);

		ZJLXInfoReq req = new ZJLXInfoReq();
		req.setOperType("42");

		GsonServlet<ZJLXInfoReq, ZJLXInfoRes> gServlet = new GsonServlet<ZJLXInfoReq, ZJLXInfoRes>(
				this);
		gServlet.request(req, ZJLXInfoRes.class);
		gServlet.setOnResponseEndListening(new OnResponseEndListening<ZJLXInfoReq, ZJLXInfoRes>() {

			@Override
			public void onResponseEnd(ZJLXInfoReq commonReq,
					ZJLXInfoRes commonRes, boolean result, String errmsg,
					int responseCode) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onResponseEndSuccess(ZJLXInfoReq commonReq,
					ZJLXInfoRes commonRes, String errmsg, int responseCode) {

				// TODO Auto-generated method stub
				if (commonRes != null) {

					cardTypelist = new String[commonRes.getZjlxInfos().size()];

					for (int i = 0; i < commonRes.getZjlxInfos().size(); i++) {

						cardTypelist[i] = commonRes.getZjlxInfos().get(i)
								.getZjlx_value();
					}
				}

			}

			@Override
			public void onResponseEndErr(ZJLXInfoReq commonReq,
					ZJLXInfoRes commonRes, String errmsg, int responseCode) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), errmsg,
						Toast.LENGTH_SHORT).show();
			}

		});

	}

}
