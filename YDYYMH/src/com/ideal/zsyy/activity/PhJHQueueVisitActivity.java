package com.ideal.zsyy.activity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jijiang.wtapp.R;
import com.ideal.zsyy.adapter.JHQueueDoctorAdapter;
import com.ideal.zsyy.adapter.PhJHQueueforDeptAdapter;
import com.ideal.zsyy.entity.PhJHQueue;
import com.ideal.zsyy.entity.PhJHQueueDoctor;
import com.ideal.zsyy.entity.PhJHQueueforDept;
import com.ideal.zsyy.request.PhJHQueueReq;
import com.ideal.zsyy.response.PhJHQueueRes;
import com.ideal.zsyy.utils.StringHelper;
import com.ideal2.base.gson.GsonServlet;

/**
 * 叫号查询
 * 
 * @author PYM
 * 
 */
public class PhJHQueueVisitActivity extends Activity {

	private ListView liJhqueuelist;
	private TextView tvRegNo;
	private TextView tvJhxx;
	private ListView liZslist;
	
	private Timer timer = new Timer();

	private List<PhJHQueueforDept> jhqueuefordept;
	private PhJHQueueforDeptAdapter jhqueuefordeptadapter;
	
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				queryData(); 
				break;

			default:
				break;
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timeshar_visit);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		// 返回
		Button btn_back = (Button) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});

		liJhqueuelist = (ListView) findViewById(R.id.lv_jhqueuelist);
		tvRegNo = (TextView) findViewById(R.id.tv_reg_no);
		tvJhxx = (TextView) findViewById(R.id.tv_jhxx);
		liZslist = (ListView) findViewById(R.id.lv_zslist);
		liJhqueuelist.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				PhJHQueueforDept queueforDept = jhqueuefordept.get(position);
				for (PhJHQueueforDept jhqueueinfo : jhqueuefordept) {
					if (queueforDept.getDept_name().equals(jhqueueinfo.getDept_name())) {
						jhqueueinfo.setClike(true);
					} else {
						jhqueueinfo.setClike(false);
					}
				}
				jhqueuefordeptadapter.notifyDataSetChanged();
				showData(queueforDept);
			}

		});
		queryData(); 
	}
	
	private void showData(PhJHQueueforDept queueforDept) {
		// TODO Auto-generated method stub
		tvRegNo.setText(queueforDept.getReg_no());
		tvJhxx.setText("当前就诊 "+queueforDept.getReg_no()+"号-"+queueforDept.getPat_name().substring(0,1)+"XX");
		List<PhJHQueueDoctor> doctorlist = queueforDept.getDoctorlist();
		JHQueueDoctorAdapter doctorAdapter = new JHQueueDoctorAdapter(PhJHQueueVisitActivity.this, doctorlist);
		liZslist.setAdapter(doctorAdapter); 
	}

	private void queryData() {
		PhJHQueueReq req = new PhJHQueueReq();
		req.setOperType("46");
		GsonServlet<PhJHQueueReq, PhJHQueueRes> gsonServlet = new GsonServlet<PhJHQueueReq, PhJHQueueRes>(
				this);
		gsonServlet.request(req, PhJHQueueRes.class);
		gsonServlet
				.setOnResponseEndListening(new GsonServlet.OnResponseEndListening<PhJHQueueReq, PhJHQueueRes>() {

					@Override
					public void onResponseEnd(PhJHQueueReq commonReq,
							PhJHQueueRes commonRes, boolean result,
							String errmsg, int responseCode) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onResponseEndSuccess(PhJHQueueReq commonReq,
							PhJHQueueRes commonRes, String errmsg,
							int responseCode) {
						// TODO Auto-generated method stub
						if (commonRes != null) {
							List<PhJHQueue> jhqueue = commonRes.getJhqueue();
							List<String> deptlist = removeDuplicateWithOrder(jhqueue);

							jhqueuefordept = new ArrayList<PhJHQueueforDept>();
							for (String deptname : deptlist) {
								PhJHQueueforDept jhqueuedept = new PhJHQueueforDept();
								int persno = 0;
								List<PhJHQueueDoctor> doctor = new ArrayList<PhJHQueueDoctor>();
								for (PhJHQueue jhqueueinfo : jhqueue) {
									PhJHQueueDoctor doctor2 = new PhJHQueueDoctor();
									if (deptname.equals(jhqueueinfo
											.getDept_name())) {
										if (persno == 0) {
											persno = Integer
													.parseInt(jhqueueinfo
															.getReg_no());
											jhqueuedept.setDept_name(deptname);
											jhqueuedept.setReg_no(jhqueueinfo
													.getReg_no());
											jhqueuedept.setPat_name(jhqueueinfo
													.getPat_name());
											doctor2.setDoc_name(jhqueueinfo
													.getDoc_name());
											doctor2.setRoom_no(jhqueueinfo
													.getRoom_no());
											doctor.add(doctor2);
										} else {
											int reg_no = Integer
													.parseInt(jhqueueinfo
															.getReg_no());
											if (reg_no > persno) {
												jhqueuedept
														.setReg_no(jhqueueinfo
																.getReg_no());
												jhqueuedept
														.setPat_name(jhqueueinfo
																.getPat_name());
											}
											doctor2.setDoc_name(jhqueueinfo
													.getDoc_name());
											doctor2.setRoom_no(jhqueueinfo
													.getRoom_no());
											doctor.add(doctor2);
										}
									}
									jhqueuedept.setDoctorlist(doctor);
								}
								jhqueuefordept.add(jhqueuedept);
							}
							Collections.sort(jhqueuefordept, new Comparator<PhJHQueueforDept>() {
								@Override
								public int compare(PhJHQueueforDept lhs,
										PhJHQueueforDept rhs) {
									// TODO Auto-generated method stub
									String lhs_pingYin = StringHelper.getPingYin(lhs.getDept_name());
									String rhs_pingYin = StringHelper.getPingYin(rhs.getDept_name());
									return lhs_pingYin.compareTo(rhs_pingYin); 
								}
							});
							jhqueuefordept.get(0).setClike(true);  
							jhqueuefordeptadapter = new PhJHQueueforDeptAdapter(PhJHQueueVisitActivity.this, jhqueuefordept);
							liJhqueuelist.setAdapter(jhqueuefordeptadapter); 
							showData(jhqueuefordept.get(0)); 
							setTimerTask();
						
						}
					}

					@Override
					public void onResponseEndErr(PhJHQueueReq commonReq,
							PhJHQueueRes commonRes, String errmsg,
							int responseCode) {
						// TODO Auto-generated method stub
						Toast.makeText(getApplicationContext(), errmsg,
								Toast.LENGTH_SHORT).show();
					}
				});
	}
	
	
	private void setTimerTask(){
		timer.schedule(new TimerTask() {
			

			@Override
			public void run() {
				// TODO Auto-generated method stub
                Message message = new Message();  
                message.what = 1;  
                handler.sendMessage(message);  
			}
		}, 1000*60);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub 
		timer.cancel(); 
		super.onDestroy();
	}
	
	

	public static List<String> removeDuplicateWithOrder(
			List<PhJHQueue> Jhqueuelist) {
		Set set = new HashSet();
		List<String> grouplist = new ArrayList<String>();
		for (Iterator iter = Jhqueuelist.iterator(); iter.hasNext();) {
			PhJHQueue element = (PhJHQueue) iter.next();
			set.add(element.getDept_name());
		}
		grouplist.clear();
		grouplist.addAll(set);
		return grouplist;
	}
}
