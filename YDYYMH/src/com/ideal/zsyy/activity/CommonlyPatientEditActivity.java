package com.ideal.zsyy.activity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.ideal.zsyy.entity.PhUser;
import com.ideal.zsyy.request.PhCommonContactInfoReq;
import com.ideal.zsyy.request.ZJLXInfoReq;
import com.ideal.zsyy.response.PhCommonContactInfoRes;
import com.ideal.zsyy.response.ZJLXInfoRes;
import com.ideal.zsyy.service.PreferencesService;
import com.ideal.zsyy.utils.IDCardUtil;
import com.ideal2.base.gson.GsonServlet;
import com.ideal2.base.gson.GsonServlet.OnResponseEndListening;

/**
 * 增加联系人
 * 
 * @author PYM
 * 
 */
public class CommonlyPatientEditActivity extends Activity {

	private EditText ep_name;
	private TextView ep_sex;
	private EditText ep_tel_no;
	private EditText ep_id_card;
	private TextView ep_tv_card_type;
	private TextView ep_birthday;

	private Calendar c;
	private int _year;
	private int _month;
	private int _day;
	private String[] cardTypelist;
	private EditText contact_person_name;
	private EditText contact_person_phone;
	private EditText medical_cardnum;
	private EditText contact_add;
	private ArrayList<PhCommonContactInfo> phCommonContactInfos;
	private PhUser phUsers;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		phUsers = Config.phUsers; 
		setContentView(R.layout.commonly_patient_edit);
		ArrayList<PhCommonContactInfo> arrayListExtra = this.getIntent()
				.getParcelableArrayListExtra("painte_list");
		if (arrayListExtra == null) {
			phCommonContactInfos = new ArrayList<PhCommonContactInfo>();
		} else {
			phCommonContactInfos = arrayListExtra;
		}
		c = Calendar.getInstance();
		_year = 1990;
		_month = 00;
		_day = 01;

		initView();

		// queryCardTypeData();

	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	private void initView() {
		ep_name = (EditText) findViewById(R.id.ep_name);
		ep_sex = (TextView) findViewById(R.id.ep_sex);
		final String[] sexlist = new String[] { "男", "女" };
		ep_sex.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(
						CommonlyPatientEditActivity.this);
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

		ep_id_card = (EditText) findViewById(R.id.ep_id_card);

		contact_person_name = (EditText) findViewById(R.id.et_contact_person_name);
		contact_person_phone = (EditText) findViewById(R.id.et_contact_person_phone);
		medical_cardnum = (EditText) findViewById(R.id.et_medical_cardnum);
		contact_add = (EditText) findViewById(R.id.et_contact_add);

		ep_birthday = (TextView) findViewById(R.id.ep_birthday);
		ep_birthday.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(0);
			}
		});
		ep_tv_card_type = (TextView) findViewById(R.id.ep_tv_card_type);
		// ep_tv_card_type.setOnClickListener(new View.OnClickListener() {
		// @Override
		// public void onClick(View v) {
		//
		// AlertDialog.Builder builder = new AlertDialog.Builder(
		// CommonlyPatientEditActivity.this);
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
		// });

		Button ep_submit = (Button) findViewById(R.id.ep_submit);
		ep_submit.setOnClickListener(new OnClickListener() {

			private PhCommonContactInfo entity;

			@Override
			public void onClick(View v) {
				String conName = ep_name.getText().toString();
				if (ep_name.getText().toString().trim().equals("")) {
					Toast.makeText(CommonlyPatientEditActivity.this, "您的姓名未填写",
							1).show();
					ep_name.setBackgroundResource(R.drawable.settext_bg);
					return;
				} else {
					ep_name.setBackgroundDrawable(null);
				}
				String conSex = ep_sex.getText().toString();
				if (ep_sex.getText().toString().trim().equals("")) {
					Toast.makeText(CommonlyPatientEditActivity.this, "您的常用就诊人性别未选择",
							1).show();
					ep_sex.setBackgroundResource(R.drawable.settext_bg);
					return;
				} else {
					ep_sex.setBackgroundDrawable(null);
				}
				String conPhone = ep_tel_no.getText().toString();
				if (ep_tel_no.getText().toString().trim().equals("")) {
					Toast.makeText(CommonlyPatientEditActivity.this, "您的常用就诊人手机号码未填写",
							1).show();
					ep_tel_no.setBackgroundResource(R.drawable.settext_bg);
					return;
				} else {
					ep_tel_no.setBackgroundDrawable(null);
				}
				String id_card = ep_id_card.getText().toString();
				if (id_card.trim().equals("")) {
					Toast.makeText(CommonlyPatientEditActivity.this, "您的常用就诊人身份证未填写",
							1).show();
					ep_id_card.setBackgroundResource(R.drawable.settext_bg);
					return;
				} else {
					ep_id_card.setBackgroundDrawable(null);
				}
				String conAdd = contact_add.getText().toString();
				if (conAdd.trim().equals("")) {
					Toast.makeText(CommonlyPatientEditActivity.this, "您的常用就诊人住址未填写",
							1).show();
					contact_add.setBackgroundResource(R.drawable.settext_bg);
					return;
				} else {
					contact_add.setBackgroundDrawable(null);
				}
				if (contact_person_name.getText().toString().equals("")) {
					Toast.makeText(CommonlyPatientEditActivity.this, "您的常用就诊人联系人未填写",
							1).show();
					contact_person_name.setBackgroundResource(R.drawable.settext_bg);
					return;
				}else {
					contact_person_name.setBackgroundDrawable(null);
				}
				entity = new PhCommonContactInfo(); 

				PreferencesService preferencesService = new PreferencesService(
						v.getContext());

				String use_id = preferencesService.getLoginInfo().get("use_id")
						.toString();

				if (use_id != null) {
					if (use_id != null) {

						if (ep_tv_card_type.getText().toString().trim()
								.equals("身份证")) {

							try {
								if (IDCardUtil.IDCardValidate(
										ep_id_card.getText().toString().trim())
										.equals("")) {
									if (ep_id_card.getText().toString().trim().equals(phUsers.getId_Card())) {
										Toast.makeText(CommonlyPatientEditActivity.this, "您的常用就诊人的身份证不能和自己的一样",
												1).show();
										ep_id_card.setBackgroundResource(R.drawable.settext_bg);
										return;
									}else {
										ep_id_card.setBackgroundDrawable(null);
									}
									ep_id_card.setBackgroundDrawable(null); 

									if (IDCardUtil.isBirthday(
											ep_id_card.getText().toString().trim())
											.equals(ep_birthday.getText()
													.toString().trim())) {
										if (!IDCardUtil.isMobileNO(ep_tel_no
												.getText().toString().trim())) {
											Toast.makeText(
													CommonlyPatientEditActivity.this,
													"您输入的手机号码无效。", 1).show();
											ep_tel_no.setBackgroundResource(R.drawable.settext_bg);
											return;
										} else {
											ep_tel_no.setBackgroundDrawable(null);
										}
										if (!IDCardUtil
												.isMobileNO(contact_person_phone
														.getText().toString()
														.trim())) {
											Toast.makeText(
													CommonlyPatientEditActivity.this,
													"您输入的联系人手机号码无效。", 1).show();
											contact_person_phone.setBackgroundResource(R.drawable.settext_bg);
											return;
										}else {
											contact_person_phone.setBackgroundDrawable(null);
										}

										entity.setUser_id(use_id);
										entity.setContact_name(conName);

										if (conSex.trim()
												.equals("男")) {

											entity.setContact_sex(Config.man);

										} else if (conSex.trim().equals("女")) {

											entity.setContact_sex(Config.woman);
										} else {

											entity.setContact_sex("");
										}

										// entity.setEMail(ep_email.getText().toString());
										entity.setContact_phone(conPhone);
										// entity.setId_Card(ep_id_card.getText().toString());
										entity.setContact_brithday(ep_birthday
												.getText().toString());

										entity.setZj_type(ep_tv_card_type.getText()
												.toString());
										entity.setZj_number(id_card); 
										// entity.setIsvisit("0");
										entity.setContact_person_name(contact_person_name.getText().toString());
										entity.setContact_person_phone(contact_person_phone
												.getText().toString());
										entity.setMedical_cardnum(medical_cardnum
												.getText().toString());
										entity.setContact_add(conAdd);
										if(phCommonContactInfos.size()>0 && phCommonContactInfos!=null){
											for(int i=0;i<phCommonContactInfos.size();i++){
												PhCommonContactInfo info = phCommonContactInfos.get(i);
												if(ep_name.getText().toString().equals(info.getContact_name()) && ep_id_card.getText().toString().equals(info.getZj_number())){
													Toast.makeText(getApplicationContext(), "该就诊人已经存在",0).show();
													return;
												}
											}
										}
										
										AlertDialog.Builder builder = new AlertDialog.Builder(CommonlyPatientEditActivity.this);
										builder.setTitle("温馨提示:");
										builder.setMessage("提交后姓名、身份证号码不可再修改"); 
										builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
											@Override
											public void onClick(DialogInterface dialog, int which) {
												// TODO Auto-generated method stub
												queryAddData(entity);
											}
										});
										builder.create().show(); 
										

									} else {

										Toast.makeText(getApplicationContext(),
												"生日与身份证不符合", 0).show();
										ep_birthday.setBackgroundResource(R.drawable.settext_bg);
									}

								} else {

									Toast.makeText(
											getApplicationContext(),
											IDCardUtil.IDCardValidate(ep_id_card
													.getText().toString().trim()),
											0).show();
									ep_id_card.setBackgroundResource(R.drawable.settext_bg);

								}

							} catch (ParseException e1) {
								// TODO Auto-generated catch block
								Toast.makeText(getApplicationContext(), "失败", 0).show();
								e1.printStackTrace();
							}

						}else {

							entity.setUser_id(use_id);
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
							entity.setContact_phone(ep_tel_no.getText()
									.toString());
							// entity.setId_Card(ep_id_card.getText().toString());
							entity.setContact_brithday(ep_birthday.getText()
									.toString());

							entity.setZj_type(ep_tv_card_type.getText()
									.toString());
							entity.setZj_number(ep_id_card.getText().toString());
							// entity.setIsvisit("0");
							queryAddData(entity);

						}

					} else {

						Toast.makeText(getApplicationContext(), "失败", 0).show();

					}
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
		req.setOperType("43");
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

						Toast.makeText(getApplicationContext(), "添加失败",
								Toast.LENGTH_SHORT).show();
					}

				} else {

					Toast.makeText(getApplicationContext(), "添加失败",
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
