package com.ideal.zsyy.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.jijiang.wtapp.R;
import com.ideal.zsyy.adapter.ChatAdapter;
import com.ideal.zsyy.entity.ChatContent;
import com.ideal.zsyy.entity.PatientChatContent;
import com.ideal.zsyy.request.GetChatRecordReq;
import com.ideal.zsyy.request.InsertChatRecordReq;
import com.ideal.zsyy.response.GetChatRecordRes;
import com.ideal.zsyy.utils.HttpTask;
import com.ideal.zsyy.utils.HttpTask.OnResponseListening;
import com.ideal2.base.gson.CommonRes;
import com.ideal2.base.gson.GsonServlet;
import com.ideal2.base.gson.GsonServlet.OnResponseEndListening;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

public class DoctorChatDetailActivity extends Activity {

	private ScrollView sv;
	private ListView chat_record_list;
	private Button sendBtn;
	private Button btnBack; 
	private EditText editMessage;
	private Context mContext;
	private String patient_id;
	private String doctor_id;
	private String doctor_name;
	private Timer timer;
	private String lastContentId="";
	private int size;
	private ChatAdapter adapter;
	HttpTask<GetChatRecordReq, GetChatRecordRes> task;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.doctor_chat_detail);
		patient_id=getIntent().getStringExtra("patient_id");
		doctor_id=getIntent().getStringExtra("doctor_id");
		doctor_name=getIntent().getStringExtra("doctor_name");
		initView();
		//fillData();
		//openAutoLoad();
	}
	
	
	public void initView()
	{
		mContext=DoctorChatDetailActivity.this;
		sv=(ScrollView) findViewById(R.id.chat_scroll);
		chat_record_list=(ListView) findViewById(R.id.chat_record_list);
		editMessage=(EditText) findViewById(R.id.chat_editmessage);
		sendBtn=(Button) findViewById(R.id.btn_send_text);
		btnBack=(Button) findViewById(R.id.btn_back);
		
		//editMessage.
		
		sendBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String text=editMessage.getText().toString();
				editMessage.setText("");
				if(text!=null&&!text.equals(""))
				{
					InsertChatRecordReq req=new InsertChatRecordReq();
					req.setDoctor_id(doctor_id);
					req.setMessage(text);
					req.setPatient_id_card(patient_id);
					req.setDoctor_name(doctor_name);
					GsonServlet<InsertChatRecordReq , CommonRes> gsonServlet=new GsonServlet<InsertChatRecordReq, CommonRes>(mContext);
					gsonServlet.request(req, CommonRes.class);
					gsonServlet.setOnResponseEndListening(new OnResponseEndListening<InsertChatRecordReq, CommonRes>() {

						@Override
						public void onResponseEnd(
								InsertChatRecordReq commonReq,
								CommonRes commonRes, boolean result,
								String errmsg, int responseCode) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void onResponseEndSuccess(
								InsertChatRecordReq commonReq,
								CommonRes commonRes, String errmsg,
								int responseCode) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void onResponseEndErr(
								InsertChatRecordReq commonReq,
								CommonRes commonRes, String errmsg,
								int responseCode) {
							// TODO Auto-generated method stub
							Toast.makeText(mContext, "发送错误"+errmsg, Toast.LENGTH_SHORT).show();
							
						}
					});
				}else
				{
					Toast.makeText(mContext, "请输入咨询内容！", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		
		btnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		chat_record_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
			}
		});
		chat_record_list.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				sv.requestDisallowInterceptTouchEvent(true);
				return false;
			}
		});
	}
	
	public void openAutoLoad()
	{
		
		/*timer=new Timer();
		final GsonServlet<GetChatRecordReq, GetChatRecordRes> gServlet=new GsonServlet<GetChatRecordReq, GetChatRecordRes>(mContext);
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				
				fillData(gServlet);
				Log.d("dd","hello world!");
			}
		}, 1000,5000);*/
		 task=new HttpTask<GetChatRecordReq, GetChatRecordRes>(mContext);
		GetChatRecordReq req=new GetChatRecordReq();
		req.setDoctor_id(doctor_id);
		req.setPatient_id_card(patient_id);
		task.start(req,GetChatRecordRes.class);
		task.setOnResponseListening(new OnResponseListening<GetChatRecordReq, GetChatRecordRes>() {

			public void onResponseSuccess(GetChatRecordRes commonRes) {
				if(commonRes!=null)
				{
					HashMap<String,ChatContent> doctor_content=commonRes.getDoctor_content();
					List <PatientChatContent> patient_content=commonRes.getPatient_content();
					if(patient_content.size()>0)
					{
					String chatId=patient_content.get(patient_content.size()-1).getChatId();
					int mSize=doctor_content.size();
					//新的消息过来
					if(!lastContentId.equals(chatId)||size!=mSize)
					{
						if(adapter==null)
					
						{
							adapter=new ChatAdapter(mContext, patient_content, doctor_content);
							chat_record_list.setAdapter(adapter);
						}else
						{
							adapter.updateData(patient_content, doctor_content);
						}
					//chat_record_list.setAdapter(adapter);
					    chat_record_list.setSelection(patient_content.size()-1);
					    size=mSize;
					    lastContentId=chatId;
					}
					}
				}
			}
		});
		
		
	}
	public void fillData(GsonServlet<GetChatRecordReq, GetChatRecordRes> gServlet)
	{
		GetChatRecordReq req=new GetChatRecordReq();
		req.setDoctor_id(doctor_id);
		req.setPatient_id_card(patient_id);
	//	gServlet=new GsonServlet<GetChatRecordReq, GetChatRecordRes>(mContext);
	    gServlet.request(req, GetChatRecordRes.class);
	    gServlet.setOnResponseEndListening(new OnResponseEndListening<GetChatRecordReq, GetChatRecordRes>() {

			@Override
			public void onResponseEnd(GetChatRecordReq commonReq,
					GetChatRecordRes commonRes, boolean result, String errmsg,
					int responseCode) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onResponseEndSuccess(GetChatRecordReq commonReq,
					GetChatRecordRes commonRes, String errmsg, int responseCode) {
				// TODO Auto-generated method stub
				if(commonRes!=null)
				{
					HashMap<String,ChatContent> doctor_content=commonRes.getDoctor_content();
					List <PatientChatContent> patient_content=commonRes.getPatient_content();
					if(patient_content.size()>0)
					{
					String chatId=patient_content.get(patient_content.size()-1).getChatId();
					int mSize=doctor_content.size();
					//新的消息过来
					if(!lastContentId.equals(chatId)||size!=mSize)
					{
						if(adapter==null)
					
						{
							adapter=new ChatAdapter(mContext, patient_content, doctor_content);
							chat_record_list.setAdapter(adapter);
						}else
						{
							adapter.updateData(patient_content, doctor_content);
						}
					//chat_record_list.setAdapter(adapter);
					    chat_record_list.setSelection(patient_content.size()-1);
					    size=mSize;
					    lastContentId=chatId;
					}
					}
				}
				
			}

			@Override
			public void onResponseEndErr(GetChatRecordReq commonReq,
					GetChatRecordRes commonRes, String errmsg, int responseCode) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		openAutoLoad();
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		//timer.cancel();
		task.stop();
	}
}
