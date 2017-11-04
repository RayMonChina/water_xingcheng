package com.ideal.zsyy.activity;

import java.util.List;

import com.jijiang.wtapp.R;
import com.ideal.zsyy.adapter.MzChargeExpandAdapter;
import com.ideal.zsyy.adapter.TbReportListAdapter;
import com.ideal.zsyy.adapter.ZlChargeAdapter;
import com.ideal.zsyy.adapter.ZlYiZhuAdapter;
import com.ideal.zsyy.entity.DiseaseHistoryInfo;
import com.ideal.zsyy.entity.HosChargeInfo;
import com.ideal.zsyy.entity.MZChargeGroupInfo;
import com.ideal.zsyy.entity.PtYzEntity;
import com.ideal.zsyy.entity.TbListReportX;
import com.ideal.zsyy.request.DiseaseHistoryReq;
import com.ideal.zsyy.request.HosChargeReq;
import com.ideal.zsyy.request.MzChargeReq;
import com.ideal.zsyy.request.PatYzReq;
import com.ideal.zsyy.request.ReportReq;
import com.ideal.zsyy.response.DiseaseHistoryRes;
import com.ideal.zsyy.response.HosChargeRes;
import com.ideal.zsyy.response.MzChargeRes;
import com.ideal.zsyy.response.PtYzRes;
import com.ideal.zsyy.response.ReportXRes;
import com.ideal.zsyy.service.PreferencesService;
import com.ideal2.base.gson.GsonServlet;
import com.ideal2.base.gson.GsonServlet.OnResponseEndListening;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ScrollView;

public class Zl_menzhenActivity extends Activity {

	private RadioGroup rg_menu;
	private List<PtYzEntity> tempYz;// 医嘱
	private List<HosChargeInfo> tempCharge;// 费用
	private DiseaseHistoryInfo disHisInfo;// 疾病史
	private List<TbListReportX> reportlistdata;// 医技报告
	private ListView lv_items, reportlist;
	private int type = 1;// 区分在那个选项卡下
	private String pat_no;
	private Button btn_back;
	private ScrollView scrollView;
	private SharedPreferences preferences;
	private RadioButton rb_cf,rb_fy,rb_bl,rb_yjbg;
	private LayoutInflater mInflater;
	private TextView tv_rq,tv_topTitle;
	private PopupWindow pop;
	private LinearLayout lay_reportlist;
	private LinearLayout ll_header;
	private ExpandableListView eListView;
	private List<MZChargeGroupInfo>temp_groupInfos;
	
	private String xm="";//姓名
	private String kh="";//卡号
	private String strmonth="3";//近三个月
	private String contentType="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zl_menzhen_info);
		preferences = getSharedPreferences(PreferencesService.SPF,
				Context.MODE_PRIVATE);
		mInflater = LayoutInflater.from(Zl_menzhenActivity.this);
		init();
		setListener();
	}

	private void init() {
		rg_menu = (RadioGroup) findViewById(R.id.rg_menz);
		lv_items = (ListView) findViewById(R.id.lv_zlinfo);
		btn_back = (Button) findViewById(R.id.btn_back);
		scrollView = (ScrollView) findViewById(R.id.sv_bs);
		reportlist = (ListView) findViewById(R.id.lv_reportlist);
		lay_reportlist=(LinearLayout)findViewById(R.id.ll_reportlist);
		tv_topTitle=(TextView)findViewById(R.id.top_title);
		rb_bl=(RadioButton)findViewById(R.id.rb_bl);
		rb_cf=(RadioButton)findViewById(R.id.rb_cf);
		rb_fy=(RadioButton)findViewById(R.id.rb_fy);
		rb_yjbg=(RadioButton)findViewById(R.id.rb_yjbg);
		ll_header=(LinearLayout)findViewById(R.id.ll_header);
		eListView=(ExpandableListView)findViewById(R.id.elv_mz_charge);
		
		Intent intent=getIntent();
		xm = intent.getStringExtra("brxm");
		kh = intent.getStringExtra("kh");
		pat_no =kh; //intent.getStringExtra("id");
		contentType=intent.getStringExtra("ctype");
//		pat_no = "33158";
//		xm="裴建全";
//		kh="12331863394456215634";
//		contentType="2";
		
		if("1".equals(contentType))
		{
			tv_topTitle.setText("门诊信息");
			rb_bl.setText("病历");
			rb_cf.setText("处方");
			rb_fy.setText("费用");
			rb_yjbg.setText("医技报告");
		}
		else if("2".equals(contentType)){
			tv_topTitle.setText("住院信息");
			rb_bl.setText("病史");
			rb_cf.setText("医嘱");
			rb_fy.setText("费用");
			rb_yjbg.setText("医技报告");
		}
		
		View view = mInflater.inflate(R.layout.rep_pop_view, null);
		pop = new PopupWindow(view, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, false);
		// 需要设置一下此参数，点击外边可消失
		pop.setBackgroundDrawable(new BitmapDrawable());
		// 设置点击窗口外边窗口消失
		pop.setOutsideTouchable(true);
		// 设置此参数获得焦点，否则无法点击
		pop.setFocusable(true);

		TextView tv_month1 = (TextView) view.findViewById(R.id.tv_month1);
		tv_month1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				tv_rq.setText("近一个月");
				queryData("1");
				strmonth="1";
				pop.dismiss();

			}
		});
		TextView tv_month2 = (TextView) view.findViewById(R.id.tv_month2);
		tv_month2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				tv_rq.setText("近两个月");
				queryData("2");
				strmonth="2";
				pop.dismiss();

			}
		});
		TextView tv_month3 = (TextView) view.findViewById(R.id.tv_month3);
		tv_month3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				tv_rq.setText("近三个月");
				queryData("3");
				strmonth="3";
				pop.dismiss();

			}
		});
		
		getData();
	}

	private void setListener() {
		rg_menu.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.rb_cf:
					type = 1;
					scrollView.setVisibility(View.GONE);
					lay_reportlist.setVisibility(View.GONE);
					lv_items.setVisibility(View.VISIBLE);
					eListView.setVisibility(View.GONE);
					break;
				case R.id.rb_fy:
					type = 2;
					scrollView.setVisibility(View.GONE);
					lay_reportlist.setVisibility(View.GONE);
					lv_items.setVisibility(View.GONE);
					eListView.setVisibility(View.VISIBLE);
					break;
				case R.id.rb_bl:
					type = 3;
					scrollView.setVisibility(View.VISIBLE);
					lay_reportlist.setVisibility(View.GONE);
					lv_items.setVisibility(View.GONE);
					eListView.setVisibility(View.GONE);
					break;
				case R.id.rb_yjbg:
					type = 4;
					scrollView.setVisibility(View.GONE);
					lay_reportlist.setVisibility(View.VISIBLE);
					lv_items.setVisibility(View.GONE);
					eListView.setVisibility(View.GONE);
					break;

				default:
					break;
				}
				changeCheck();
				getData();
			}
		});

		lv_items.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				switch (type) {
				case 1:
					break;
				case 2:
					break;
				case 3:
					break;
				case 4:
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

		reportlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						if (arg2 > 0) {
							TbListReportX report = reportlistdata.get(arg2 - 1);
							String bgdh = report.getSimpleNo();
							Intent intent = new Intent(Zl_menzhenActivity.this,
									TbReportDetailActivity.class);
							intent.putExtra("bgdh", bgdh);
							startActivity(intent);
						}
					}
				});

	}

	private void changeCheck() {
		int checkCount = rg_menu.getChildCount();
		RadioButton rButton = null;
		int checkId = rg_menu.getCheckedRadioButtonId();
		for (int i = 0; i < checkCount; i++) {
			if (rg_menu.getChildAt(i) instanceof RadioButton) {
				rButton = (RadioButton) rg_menu.getChildAt(i);
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

	private void getData() {
		switch (type) {
		case 1:
			if (tempYz == null) {
				getYzData(pat_no);
			} else {
				ZlYiZhuAdapter yzAdapter = new ZlYiZhuAdapter(
						Zl_menzhenActivity.this, tempYz);
				lv_items.setAdapter(yzAdapter);
			}
			break;
		case 2:
			if (temp_groupInfos == null) {
				getExpChargeData(pat_no);
			} else {
				MzChargeExpandAdapter yzAdapter = new MzChargeExpandAdapter(
						Zl_menzhenActivity.this, temp_groupInfos);
				eListView.setAdapter(yzAdapter);
				expandView();
			}
			break;
		case 3:
			if (disHisInfo != null) {
				bindBs(disHisInfo);
			} else {
				getDisHistoryData(pat_no);
			}
			break;
		case 4:
			queryData(strmonth);
			break;
		default:
			break;
		}
	}

	private void getYzData(String pat_no) {
		PatYzReq req = new PatYzReq();
		req.setOperType("105");
		req.setPat_no(pat_no);
		if("1".equals(contentType))
		{
			req.setStatus("0");
		}
		else if ("2".equals(contentType)) {
			req.setStatus("1");
		}
		GsonServlet<PatYzReq, PtYzRes> gServlet = new GsonServlet<PatYzReq, PtYzRes>(
				this);
		gServlet.request(req, PtYzRes.class);
		gServlet.setOnResponseEndListening(new GsonServlet.OnResponseEndListening<PatYzReq, PtYzRes>() {

			@Override
			public void onResponseEnd(PatYzReq commonReq, PtYzRes commonRes,
					boolean result, String errmsg, int responseCode) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onResponseEndSuccess(PatYzReq commonReq,
					PtYzRes commonRes, String errmsg, int responseCode) {
				// TODO Auto-generated method stub
				if (commonRes != null) {
					ZlYiZhuAdapter yzAdapter = new ZlYiZhuAdapter(
							Zl_menzhenActivity.this, commonRes.getPtEntities());
					lv_items.setAdapter(yzAdapter);
					tempYz = commonRes.getPtEntities();
				}
			}

			@Override
			public void onResponseEndErr(PatYzReq commonReq, PtYzRes commonRes,
					String errmsg, int responseCode) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void getChargeData(String pat_no) {
		HosChargeReq req = new HosChargeReq();
		req.setOperType("106");
		req.setPat_no(pat_no);
		if("1".equals(contentType))
		{
			req.setStatus("0");
		}
		else if ("2".equals(contentType)) {
			req.setStatus("1");
		}
		GsonServlet<HosChargeReq, HosChargeRes> gServlet = new GsonServlet<HosChargeReq, HosChargeRes>(
				this);
		gServlet.request(req, HosChargeRes.class);
		gServlet.setOnResponseEndListening(new GsonServlet.OnResponseEndListening<HosChargeReq, HosChargeRes>() {

			@Override
			public void onResponseEnd(HosChargeReq commonReq,
					HosChargeRes commonRes, boolean result, String errmsg,
					int responseCode) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onResponseEndSuccess(HosChargeReq commonReq,
					HosChargeRes commonRes, String errmsg, int responseCode) {
				// TODO Auto-generated method stub
				if (commonRes != null) {
					ZlChargeAdapter yzAdapter = new ZlChargeAdapter(
							Zl_menzhenActivity.this, commonRes
									.getHosChargeInfos());
					lv_items.setAdapter(yzAdapter);
					tempCharge = commonRes.getHosChargeInfos();
				}
			}

			@Override
			public void onResponseEndErr(HosChargeReq commonReq,
					HosChargeRes commonRes, String errmsg, int responseCode) {
				// TODO Auto-generated method stub

			}
		});
	}
	
	//new 14-02-19
	private void getExpChargeData(String pat_no) {
		MzChargeReq req = new MzChargeReq();
		req.setOperType("119");
		req.setPid(pat_no);
		
		GsonServlet<MzChargeReq, MzChargeRes> gServlet = new GsonServlet<MzChargeReq, MzChargeRes>(
				this);
		gServlet.request(req, MzChargeRes.class);
		gServlet.setOnResponseEndListening(new GsonServlet.OnResponseEndListening<MzChargeReq, MzChargeRes>() {

			@Override
			public void onResponseEnd(MzChargeReq commonReq,
					MzChargeRes commonRes, boolean result, String errmsg,
					int responseCode) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onResponseEndSuccess(MzChargeReq commonReq,
					MzChargeRes commonRes, String errmsg, int responseCode) {
				// TODO Auto-generated method stub
				if (commonRes != null) {
					temp_groupInfos=commonRes.getMzChargeInfos();
					MzChargeExpandAdapter mzAdapter=new MzChargeExpandAdapter(Zl_menzhenActivity.this, temp_groupInfos);
					eListView.setAdapter(mzAdapter);
					expandView();
				}
			}

			@Override
			public void onResponseEndErr(MzChargeReq commonReq,
					MzChargeRes commonRes, String errmsg, int responseCode) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void getDisHistoryData(String pat_no) {
		DiseaseHistoryReq req = new DiseaseHistoryReq();
		req.setOperType("107");
		req.setPat_no(pat_no);
		if("1".equals(contentType))
		{
			req.setStatus("0");
		}
		else if ("2".equals(contentType)) {
			req.setStatus("1");
		}
		GsonServlet<DiseaseHistoryReq, DiseaseHistoryRes> gServlet = new GsonServlet<DiseaseHistoryReq, DiseaseHistoryRes>(
				this);
		gServlet.request(req, DiseaseHistoryRes.class);
		gServlet.setOnResponseEndListening(new GsonServlet.OnResponseEndListening<DiseaseHistoryReq, DiseaseHistoryRes>() {

			@Override
			public void onResponseEnd(DiseaseHistoryReq commonReq,
					DiseaseHistoryRes commonRes, boolean result, String errmsg,
					int responseCode) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onResponseEndSuccess(DiseaseHistoryReq commonReq,
					DiseaseHistoryRes commonRes, String errmsg, int responseCode) {
				// TODO Auto-generated method stub
				if (commonRes != null) {
					disHisInfo = commonRes.getDisHisInfo();
					bindBs(disHisInfo);
				}
			}

			@Override
			public void onResponseEndErr(DiseaseHistoryReq commonReq,
					DiseaseHistoryRes commonRes, String errmsg, int responseCode) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void bindBs(DiseaseHistoryInfo disinfo) {
		LinearLayout layout = (LinearLayout) LinearLayout.inflate(
				Zl_menzhenActivity.this, R.layout.zl_jbs_item, null);
		TextView tv_xbs = (TextView) layout.findViewById(R.id.tv_xbs);
		TextView tv_crbs = (TextView) layout.findViewById(R.id.tv_crbs);
		TextView tv_yfjzs = (TextView) layout.findViewById(R.id.tv_yfjzs);
		TextView tv_sswss = (TextView) layout.findViewById(R.id.tv_sswss);
		TextView tv_ywgms = (TextView) layout.findViewById(R.id.tv_ywgms);
		TextView tv_grs = (TextView) layout.findViewById(R.id.tv_grs);
		if(disinfo==null)
		{
			tv_xbs.setText("未查询到数据");
			tv_crbs.setText("未查询到数据");
			tv_yfjzs.setText("未查询到数据");
			tv_sswss.setText("未查询到数据");
			tv_ywgms.setText("未查询到数据");
			tv_grs.setText("未查询到数据");
			return;
		}
		tv_xbs.setText(disHisInfo.getXbs());
		tv_crbs.setText(disHisInfo.getCrbs());
		tv_yfjzs.setText(disHisInfo.getYfjzs());
		tv_sswss.setText(disHisInfo.getSswss());
		tv_ywgms.setText(disHisInfo.getYwgms());
		tv_grs.setText(disHisInfo.getGrs());
		if (scrollView != null) {
			scrollView.removeAllViews();
			scrollView.addView(layout);
		}

	}

	//月，姓名 卡号
	private void queryData(String month) {
		ReportReq req = new ReportReq();
		req.setOperType("113");
		req.setBrxm(xm);
		req.setKh(kh);
		req.setMonth(month);
		if("1".equals(contentType))
		{
			req.setStatus("0");
		}
		else if ("2".equals(contentType)) {
			req.setStatus("1");
		}
		GsonServlet<ReportReq, ReportXRes> gServlet = new GsonServlet<ReportReq, ReportXRes>(
				this);
		gServlet.request(req, ReportXRes.class);
		gServlet.setOnResponseEndListening(new OnResponseEndListening<ReportReq, ReportXRes>() {
			@Override
			public void onResponseEnd(ReportReq commonReq,
					ReportXRes commonRes, boolean result, String errmsg,
					int responseCode) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onResponseEndSuccess(ReportReq commonReq,
					ReportXRes commonRes, String errmsg, int responseCode) {
				// TODO Auto-generated method stub
				if (commonRes != null) {
					reportlistdata = commonRes.getTblistreportx();
					if (reportlistdata != null && reportlistdata.size() > 0) {
						if (reportlist.getHeaderViewsCount() <= 0) {
							View v = getHeaderView(reportlistdata.get(0));
							reportlist.addHeaderView(v);
							//ll_header.addView(v);
						}
						TbReportListAdapter adapter = new TbReportListAdapter(
								Zl_menzhenActivity.this, reportlistdata,
								preferences);
						reportlist.setAdapter(adapter);
					} else {

					}
				}
			}

			@Override
			public void onResponseEndErr(ReportReq commonReq,
					ReportXRes commonRes, String errmsg, int responseCode) {
				// TODO Auto-generated method stub

			}
		});
	}

	protected View getHeaderView(TbListReportX tbLisReport) {
		View reportbr_info = mInflater.inflate(R.layout.reportbr_info, null);
		TextView tv_brxm = (TextView) reportbr_info.findViewById(R.id.tv_brxm);
		TextView tv_brxb = (TextView) reportbr_info.findViewById(R.id.tv_brxb);
		TextView tv_brnl = (TextView) reportbr_info.findViewById(R.id.tv_brnl);
		tv_rq = (TextView) reportbr_info.findViewById(R.id.tv_rq);
		tv_brxm.setText(tbLisReport.getPatientName());
		tv_brxb.setText(tbLisReport.getSexName());
		tv_brnl.setText(tbLisReport.getPatientAge() + "岁");
		tv_rq.setText("近三个月");
		tv_rq.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (pop.isShowing()) {
					pop.dismiss();
				} else {
					pop.showAsDropDown(v);
				}
			}
		});
		return reportbr_info;
	}

	//展开expandlistview
	private void expandView()
	{
		if(eListView!=null)
		{
			int icount=eListView.getCount();
			for(int i=0;i<icount;i++)
			{
				eListView.expandGroup(i);
			}
		}
		
	}
}
