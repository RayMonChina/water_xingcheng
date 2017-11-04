package com.ideal.zsyy.activity;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.ideal.zsyy.Config;
import com.jijiang.wtapp.R;
import com.ideal.zsyy.adapter.BackVisitAdapter;
import com.ideal.zsyy.adapter.BackVisitChatAdapter;
import com.ideal.zsyy.entity.BackVisitInfo;
import com.ideal.zsyy.entity.ComplainInfo;
import com.ideal.zsyy.entity.PhUser;
import com.ideal.zsyy.request.BackVisitChatReq;
import com.ideal.zsyy.request.BackVisitReq;
import com.ideal.zsyy.request.ComplainReq;
import com.ideal.zsyy.response.BackVisitChatRes;
import com.ideal.zsyy.response.BackVisitRes;
import com.ideal.zsyy.response.ComplainRes;
import com.ideal.zsyy.service.PreferencesService;
import com.ideal2.base.gson.GsonServlet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;

public class BackVisitActivity extends Activity {
	
	private RadioGroup rg_menz;
	private RadioButton rb_hf, rb_tousu, rb_zixun, rb_jianyi;
	private ListView lv_info;
	private Button btn_back;
	private RelativeLayout rb_bottom;
	private EditText et_sendmessage;
	private Button btn_send;
	private List<BackVisitInfo> bcVisitInfos;// 回访信息
	private List<ComplainInfo> com_ts, com_zx, com_jy;
	private PhUser phUsers;
	private PreferencesService preferencesService;
	private String serType = "0";
	private Timer timer;
	private Handler handler,handlerUi;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.back_visit_list);
		initView();
		setListener();

	}

	private void initView() {

		preferencesService = new PreferencesService(BackVisitActivity.this);

		rg_menz = (RadioGroup) findViewById(R.id.rg_menz);
		rb_hf = (RadioButton) findViewById(R.id.rb_hf);
		rb_tousu = (RadioButton) findViewById(R.id.rb_ts);
		rb_zixun = (RadioButton) findViewById(R.id.rb_zx);
		rb_jianyi = (RadioButton) findViewById(R.id.rb_jy);
		lv_info = (ListView) findViewById(R.id.lv_zlinfo);
		rb_bottom = (RelativeLayout) findViewById(R.id.rl_bottom);
		et_sendmessage = (EditText) findViewById(R.id.et_sendmessage);
		btn_send = (Button) findViewById(R.id.btn_send);

		btn_back = (Button) findViewById(R.id.btn_back);
		
		handler=new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch (msg.what) {
				case 1:
					getTsData("1",false);
					break;
				case 2:
					getTsData("2",false);
					break;
				case 3:
					getTsData("3",false);
					break;
				default:
					break;
				}
			}
			
		};
		
		timer=new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if("1".equals(serType))
				{
					handler.sendEmptyMessage(1);
				}
				else if ("2".equals(serType)) {
					handler.sendEmptyMessage(2);
				}
				else if ("3".equals(serType)) {
					handler.sendEmptyMessage(3);
				}
			}
		}, 1000, 10000);
		
	}

	private void setListener() {
		rg_menz.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.rb_hf:
					rb_bottom.setVisibility(View.GONE);
					serType="0";
					gethfData();
					break;
				case R.id.rb_ts:
					rb_bottom.setVisibility(View.VISIBLE);
					serType = "1";
					getTsData(serType,true);
					break;
				case R.id.rb_zx:
					rb_bottom.setVisibility(View.VISIBLE);
					serType = "2";
					getTsData(serType,true);
					break;
				case R.id.rb_jy:
					rb_bottom.setVisibility(View.VISIBLE);
					serType = "3";
					getTsData(serType,true);
					break;
				default:
					break;
				}
				changeCheck();
			}
		});

		// 点击列表项
		lv_info.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				switch (rg_menz.getCheckedRadioButtonId()) {
				case R.id.rb_hf:
					if (bcVisitInfos != null && bcVisitInfos.size() >= arg2) {
						BackVisitInfo bInfo = bcVisitInfos.get(arg2);
						String strId = bInfo.getId();
						Intent intentTo = new Intent();
						intentTo.putExtra("id", strId);
						if ("".equals(bInfo.getStatus())
								|| "0".equals(bInfo.getStatus())) {
							intentTo.setClass(BackVisitActivity.this,
									BackVisitEditActivity.class);
						} else if ("1".equals(bInfo.getStatus())) {
							intentTo.setClass(BackVisitActivity.this,
									BackVisitDisActivity.class);
						}
						startActivity(intentTo);
					}

					break;
				case R.id.rb_ts:
					rb_bottom.setVisibility(View.VISIBLE);
					break;
				case R.id.rb_zx:
					rb_bottom.setVisibility(View.VISIBLE);
					break;
				case R.id.rb_jy:
					rb_bottom.setVisibility(View.VISIBLE);
					break;
				default:
					break;
				}
			}

		});

		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		btn_send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String strContent=et_sendmessage.getText().toString().replace("'","");
				if("".equals(strContent.trim()))
				{
					Toast.makeText(BackVisitActivity.this, "发送信息不能为空", Toast.LENGTH_SHORT).show();
					return;
				}
				insertChartData(strContent);
			}
		});
	}

	// 选中更改时改变字体颜色
	private void changeCheck() {
		int checkCount = rg_menz.getChildCount();
		RadioButton rButton = null;
		int checkId = rg_menz.getCheckedRadioButtonId();
		for (int i = 0; i < checkCount; i++) {
			if (rg_menz.getChildAt(i) instanceof RadioButton) {
				rButton = (RadioButton) rg_menz.getChildAt(i);
				if (rButton.getId() == checkId) {
					rButton.setTextColor(getResources().getColor(
							android.R.color.white));
				} else {
					rButton.setTextColor(getResources().getColor(
							R.color.textcolor1));
				}
			}

		}
	}

	
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	
	

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(timer!=null)
		{
			timer.cancel();
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		phUsers = Config.phUsers;
		if (phUsers == null || !preferencesService.getIsLogin()) {
			AlertDialog.Builder builder = new AlertDialog.Builder(
					BackVisitActivity.this);
			builder.setTitle("请先登录....");
			builder.setNeutralButton("确定",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(BackVisitActivity.this,
									LoginActivity.class);
							intent.putExtra("logintype", "huifang");
							startActivity(intent);
						}
					});
			builder.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							finish();
						}
					});
			builder.create().show();
		} else {
			// if (phUsers.getId_Card() == null
			// || phUsers.getId_Card().equals("")) {
			// AlertDialog.Builder builder = new AlertDialog.Builder(
			// BackVisitActivity.this);
			// builder.setTitle("身份证号为空，请先完善个人信息!");
			// builder.setNeutralButton("确定",
			// new DialogInterface.OnClickListener() {
			// @Override
			// public void onClick(DialogInterface dialog,
			// int which) {
			// // TODO Auto-generated method stub
			// Intent intent = new Intent(
			// BackVisitActivity.this,
			// EditPersonInfoActivity.class);
			// intent.putExtra("editmsg", "report");
			// startActivity(intent);
			// }
			// });
			// builder.setNegativeButton("取消",
			// new DialogInterface.OnClickListener() {
			// @Override
			// public void onClick(DialogInterface dialog,
			// int which) {
			// // TODO Auto-generated method stub
			// finish();
			// }
			// });
			// builder.create().show();
			// }
			gethfData();// 回访
		}
		
	}

	// 回访信息
	private void gethfData() {
		phUsers = Config.phUsers;
		if (phUsers == null) {
			return;
		}
		BackVisitReq yyreReq = new BackVisitReq();
		// yyreReq.setIdCard(phUsers.getId_Card());
		yyreReq.setOperType("114");
		GsonServlet<BackVisitReq, BackVisitRes> gsonServlet = new GsonServlet<BackVisitReq, BackVisitRes>(
				BackVisitActivity.this);
		gsonServlet.request(yyreReq, BackVisitRes.class);
		gsonServlet
				.setOnResponseEndListening(new GsonServlet.OnResponseEndListening<BackVisitReq, BackVisitRes>() {

					@Override
					public void onResponseEnd(BackVisitReq commonReq,
							BackVisitRes commonRes, boolean result,
							String errmsg, int responseCode) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onResponseEndSuccess(BackVisitReq commonReq,
							BackVisitRes commonRes, String errmsg,
							int responseCode) {
						// TODO Auto-generated method stub
						if (commonRes != null) {
							bcVisitInfos = commonRes.getBackVisitInfos();
							BackVisitAdapter bAdapter = new BackVisitAdapter(
									BackVisitActivity.this, bcVisitInfos);
							lv_info.setAdapter(bAdapter);
						}
					}

					@Override
					public void onResponseEndErr(BackVisitReq commonReq,
							BackVisitRes commonRes, String errmsg,
							int responseCode) {
						// TODO Auto-generated method stub
						Toast.makeText(BackVisitActivity.this, errmsg,
								Toast.LENGTH_SHORT).show();
					}
				});
	}

	// 获取投诉数据 1、投诉 2、咨询 3、建议
	private void getTsData(String sType,boolean showDialog) {
		
		phUsers = Config.phUsers;
		if (phUsers == null) {
			return;
		}
		ComplainReq yyreReq = new ComplainReq();
		yyreReq.setType(sType);
		yyreReq.setOperType("116");
		GsonServlet<ComplainReq, ComplainRes> gsonServlet = new GsonServlet<ComplainReq, ComplainRes>(
				BackVisitActivity.this);
		gsonServlet.setShowDialog(showDialog);
		gsonServlet.request(yyreReq, ComplainRes.class);
		gsonServlet
				.setOnResponseEndListening(new GsonServlet.OnResponseEndListening<ComplainReq, ComplainRes>() {

					@Override
					public void onResponseEnd(ComplainReq commonReq,
							ComplainRes commonRes, boolean result,
							String errmsg, int responseCode) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onResponseEndSuccess(ComplainReq commonReq,
							ComplainRes commonRes, String errmsg,
							int responseCode) {
						// TODO Auto-generated method stub
						if (commonRes != null) {
							if ("1".equals(serType)) {
								com_ts = commonRes.getComplainInfos();
							} else if ("2".equals(serType)) {
								com_zx = commonRes.getComplainInfos();
							} else if ("3".equals(serType)) {
								com_jy = commonRes.getComplainInfos();
							}
						}
						BackVisitChatAdapter chatAdapter = new BackVisitChatAdapter(
								BackVisitActivity.this, commonRes
										.getComplainInfos());
						lv_info.setAdapter(chatAdapter);
						lv_info.setSelection(lv_info.getCount() - 1);
					}

					@Override
					public void onResponseEndErr(ComplainReq commonReq,
							ComplainRes commonRes, String errmsg,
							int responseCode) {
						// TODO Auto-generated method stub
						Toast.makeText(BackVisitActivity.this, errmsg,
								Toast.LENGTH_SHORT).show();
					}
				});
	}
	
	

	// 插入投诉信息
	private void insertChartData(String chatContent) {
		phUsers = Config.phUsers;
		String str_patNo;
		if (phUsers == null) {
			return;
		}
		if (bcVisitInfos == null || bcVisitInfos.size() == 0) {
			Toast.makeText(BackVisitActivity.this, "发送失败", Toast.LENGTH_SHORT)
					.show();
			return;
		}
		str_patNo = bcVisitInfos.get(0).getPatNo();
		BackVisitChatReq yyreReq = new BackVisitChatReq();
		yyreReq.setChatType(serType);
		yyreReq.setPat_No(str_patNo);
		yyreReq.setSendContent(chatContent);
		// yyreReq.setIdCard(phUsers.getId_Card());
		yyreReq.setOperType("118");
		GsonServlet<BackVisitChatReq, BackVisitChatRes> gsonServlet = new GsonServlet<BackVisitChatReq, BackVisitChatRes>(
				BackVisitActivity.this);
		gsonServlet.request(yyreReq, BackVisitChatRes.class);
		gsonServlet
				.setOnResponseEndListening(new GsonServlet.OnResponseEndListening<BackVisitChatReq, BackVisitChatRes>() {

					@Override
					public void onResponseEnd(BackVisitChatReq commonReq,
							BackVisitChatRes commonRes, boolean result,
							String errmsg, int responseCode) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onResponseEndSuccess(
							BackVisitChatReq commonReq,
							BackVisitChatRes commonRes, String errmsg,
							int responseCode) {
						// TODO Auto-generated method stub
						getTsData(serType,true);
						et_sendmessage.setText("");
					}

					@Override
					public void onResponseEndErr(BackVisitChatReq commonReq,
							BackVisitChatRes commonRes, String errmsg,
							int responseCode) {
						// TODO Auto-generated method stub
						Toast.makeText(BackVisitActivity.this, errmsg,
								Toast.LENGTH_SHORT).show();
					}
				});
	}

}
