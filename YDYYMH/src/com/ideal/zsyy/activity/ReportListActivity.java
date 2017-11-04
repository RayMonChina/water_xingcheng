package com.ideal.zsyy.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ideal.zsyy.Config;
import com.jijiang.wtapp.R;
import com.ideal.zsyy.entity.PhUser;

/**
 * 取报告单
 * 
 * @author KOBE
 * 
 */
public class ReportListActivity extends Activity {

	private int card_type = 0;
	private TextView et_brxm;
	private TextView et_brkh;
	private PhUser phUsers;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reportlist);
		phUsers = Config.phUsers; 
		initView();
	}

	@SuppressWarnings("deprecation")
	private void initView() {

		LayoutInflater inflater = LayoutInflater.from(this);
		View view = inflater.inflate(R.layout.rl_pop_view, null);
		// 创建PopupWindow对象
		final PopupWindow pop = new PopupWindow(view,
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, false);
		// 需要设置一下此参数，点击外边可消失
		pop.setBackgroundDrawable(new BitmapDrawable());
		// 设置点击窗口外边窗口消失
		pop.setOutsideTouchable(true);
		// 设置此参数获得焦点，否则无法点击
		pop.setFocusable(true);

		final TextView rl_tv_card = (TextView) findViewById(R.id.rl_tv_card);

		LinearLayout ll_tv_card = (LinearLayout) findViewById(R.id.ll_tv_card);
//		ll_tv_card.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				if (pop.isShowing()) {
//
//					pop.dismiss();
//				} else {
//
//					pop.showAsDropDown(v);
//				}
//			}
//		});

		TextView rl_pop_tv_card_1 = (TextView) view
				.findViewById(R.id.rl_pop_tv_card_1);
		rl_pop_tv_card_1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				rl_tv_card.setText("医保卡");
				card_type = 0;
				pop.dismiss();

			}
		});

		TextView rl_pop_tv_card_2 = (TextView) view
				.findViewById(R.id.rl_pop_tv_card_2);

		rl_pop_tv_card_2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				rl_tv_card.setText("社保卡");
				card_type = 1;
				pop.dismiss();

			}
		});

		TextView rl_pop_tv_card_3 = (TextView) view
				.findViewById(R.id.rl_pop_tv_card_3);
		rl_pop_tv_card_3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				rl_tv_card.setText("就医卡");
				card_type = 2;
				pop.dismiss();
			}
		});

		LinearLayout rl_pop_bj = (LinearLayout) view
				.findViewById(R.id.rl_pop_bj);
		rl_pop_bj.getBackground().setAlpha(180);

		// ///////////////////////////////

		TextView rl_tv_card_info = (TextView) findViewById(R.id.rl_tv_card_info);
		rl_tv_card_info
				.setText("1.网上查询比到医院取报告滞后一至两天\n2.医保卡（白玉兰卡）用户请输入28位卡号（检验回执单上有）\n3.检验结果正常与否请咨询相关医生\n4.查询结果仅供参考，请至医院取纸质报告");

		final Button rl_bt_sh = (Button) findViewById(R.id.rl_bt_sh);
		final Button rl_bt_yx = (Button) findViewById(R.id.rl_bt_yx);
		rl_bt_sh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				rl_bt_sh.setBackgroundResource(R.drawable.navigation_tab_left_down);
				rl_bt_sh.setTextColor(Color.parseColor("#FFFFFF"));

				rl_bt_yx.setBackgroundResource(R.drawable.navigation_tab_right_up);
				rl_bt_yx.setTextColor(Color.parseColor("#0079ff"));

			}
		});

		rl_bt_yx.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				rl_bt_sh.setBackgroundResource(R.drawable.navigation_tab_left_up);
				rl_bt_sh.setTextColor(Color.parseColor("#0079ff"));

				rl_bt_yx.setBackgroundResource(R.drawable.navigation_tab_right_down);
				rl_bt_yx.setTextColor(Color.parseColor("#FFFFFF"));

			}
		});

		Button btn_back = (Button) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});

		Button rl_bt_seecard = (Button) findViewById(R.id.rl_bt_seecard);
		rl_bt_seecard.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(v.getContext(),
						CardExplainActivity.class);

				intent.putExtra("card_type", card_type);
				startActivity(intent);
			}
		});
		
		et_brxm = (TextView) findViewById(R.id.et_brxm);
		et_brkh = (TextView) findViewById(R.id.et_brkh);
		Button rl_bt_submit = (Button) findViewById(R.id.rl_bt_submit);
		rl_bt_submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(et_brxm.getText().toString()==null || et_brxm.getText().toString().equals("")){
					if(et_brkh.getText().toString()==null || et_brkh.getText().toString().equals("")){
						AlertDialog.Builder builder = new AlertDialog.Builder(ReportListActivity.this);
						builder.setTitle("请登录....");
						builder.setNeutralButton("确定", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								Intent intent = new Intent(ReportListActivity.this,LoginActivity.class);
								intent.putExtra("logintype", "report");
								startActivity(intent);
							}
						});
						builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
							}
						});
						builder.create().show();
					}
				}else if(et_brkh.getText().toString()==null || et_brkh.getText().toString().equals("")){
					AlertDialog.Builder builder = new AlertDialog.Builder(ReportListActivity.this);
					builder.setTitle("就诊卡号为空，请先完善个人信息!");
					builder.setNeutralButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(ReportListActivity.this,EditPersonInfoActivity.class);
							intent.putExtra("editmsg", "report");
							startActivity(intent);
						}
					});
					builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
					});
					builder.create().show();
				} else {
					if (phUsers.getReport_flag().equals("0")) {
						AlertDialog.Builder builder = new AlertDialog.Builder(ReportListActivity.this);
						builder.setTitle("您尚未开通本服务");
						builder.setMessage("为了保护您的隐私数据，此查询功能必须到医院服务中心实名开通后方可使用，谢谢配合！");
						builder.setNeutralButton("确定", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
							}
						});
						builder.create().show();
					} else if (phUsers.getReport_flag().equals("1")) {
						String kh = et_brkh.getText().toString();
						String brxm = et_brxm.getText().toString();
						Intent intent = new Intent(ReportListActivity.this,TbReportlistActivity.class);
						intent.putExtra("brxm", brxm);
						intent.putExtra("kh", kh);
						startActivity(intent);
						finish();
					}
				}
			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		phUsers = Config.phUsers;
		if (phUsers == null) {
			AlertDialog.Builder builder = new AlertDialog.Builder(ReportListActivity.this);
			builder.setTitle("请先登录....");
			builder.setNeutralButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(ReportListActivity.this,LoginActivity.class);
					intent.putExtra("logintype", "report");
					startActivity(intent);
				}
			});
			builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
				}
			});
			builder.create().show();
		}else{
			if(phUsers.getMedical_cardnum()!=null && !phUsers.getMedical_cardnum().equals("")){
				String cardnum = phUsers.getMedical_cardnum().trim();
				StringBuffer sb=new StringBuffer(cardnum);
				if(cardnum.length()<=10){
					for (int i = 0; i < 10-cardnum.length(); i++) {
						sb.insert(0, "0");
					}
				}
				et_brkh.setText(sb);
			}else{
				AlertDialog.Builder builder = new AlertDialog.Builder(ReportListActivity.this);
				builder.setTitle("就诊卡号为空，请先完善个人信息!");
				builder.setNeutralButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(ReportListActivity.this,EditPersonInfoActivity.class);
						intent.putExtra("editmsg", "report");
						startActivity(intent);
					}
				});
				builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				});
				builder.create().show();
			}
			et_brxm.setText(Config.phUsers.getName()); 
		} 
	}

}
