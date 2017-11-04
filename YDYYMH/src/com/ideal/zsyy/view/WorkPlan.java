package com.ideal.zsyy.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ideal.wdm.tools.DataCache;
import com.ideal.zsyy.Config;
import com.jijiang.wtapp.R;
import com.ideal.zsyy.activity.ConfimAppointmentActivity;
import com.ideal.zsyy.activity.LoginActivity;
import com.ideal.zsyy.entity.DeptDutysInfo;
import com.ideal.zsyy.entity.DeptDutysInfo1;
import com.ideal.zsyy.utils.DataUtils;
import com.ideal.zsyy.utils.Utility;

public class WorkPlan extends BaseLayout {

	private TextView tx_date, tx_work_detail, tx_work_time;
	private GridView gv_calendar, gv_calendar_gone;
	private Calendar calendar;
	private Button btn_home;
	private View lay_foot, lay_time_line;
	private ImageView v_lastM;
	private ImageView v_nextM;
	private Context context;

	// private List<YspbInfo> lst;
	private List<DeptDutysInfo1> deptList;

	private List<String> timeList = new ArrayList<String>();
	// private Cadapter mAdapter;
	// 缓存数据
	// private HashMap<String, List<YspbInfo>> buffData;
	private HashMap<String, List<DeptDutysInfo1>> buffData;
	private HashMap<String, List<String>> buffTime;

	private int year, month, day, lastYear, lastMonth, nextYear, nextMonth;

	// 今天日期
	private String curDate;
	// 明天日期
	private String nextDate;
	// 开始日期
	private String startDate;
	// 结束日期
	private String endDate;
	
	private String [] list;

	// 大月
	private static int[] long_month = { 1, 3, 5, 7, 8, 10, 12 };
	// 上次选中日期索引
	private int lastIndex = -1;

	private String doctorID;

	private int height = 60;
	private View lay_grid;
	private Cadapter mAdapter;
	private List<DeptDutysInfo> resultList;

	public WorkPlan(Context context, List<DeptDutysInfo> deptList) {
		super(context, R.layout.calendar_item);
		this.context = context;
		resultList = deptList;
		initView();
		height = Utility.relativeLen(height, context);
		// getList(resultList);
	}

	private void initView() {
		v_lastM = (ImageView) findViewById(R.id.iv_lastM);
		v_nextM = (ImageView) findViewById(R.id.iv_nextM);
		btn_home = (Button) findViewById(R.id.btn_menu);
		tx_date = (TextView) findViewById(R.id.text_date);
		lay_grid = findViewById(R.id.lay_grid);
		gv_calendar = (GridView) findViewById(R.id.calendar);
		gv_calendar_gone = (GridView) findViewById(R.id.calendar_gone);
		lay_grid.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				height * 7));
		postInvalidate();
		bindListener();
		init();
	}

	/**
	 * 绑定监听
	 */
	private void bindListener() {
		tx_date.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DatePickerDialog dateDia = new DatePickerDialog(context,
						new DatePickerDialog.OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker view, int year,
									int monthOfYear, int dayOfMonth) {

								tx_date.setText(year + "年" + (monthOfYear + 1)
										+ "月");
								// 缓存数据
								WorkPlan.this.year = year;
								WorkPlan.this.month = monthOfYear;
								String key = year + "" + monthOfYear + 1;

								// requestData(key);
								showData(key);
							}
						}, year, month, day);
				dateDia.show();
			}
		});

		v_lastM.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Animation animi = AnimationUtils.loadAnimation(context,
						R.anim.anim_left_in);
				animi.setAnimationListener(new AnimationListener() {

					@Override
					public void onAnimationStart(Animation animation) {

					}

					@Override
					public void onAnimationRepeat(Animation animation) {

					}

					@Override
					public void onAnimationEnd(Animation animation) {
						year = lastYear;
						month = lastMonth - 1;
						tx_date.setText(year + "年" + (month + 1) + "月");
						// requestData(year + "" + month + 1);
						showData(year + "" + month + 1);
					}
				});
				gv_calendar.startAnimation(animi);
			}
		});
		v_nextM.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Animation animi = AnimationUtils.loadAnimation(context,
						R.anim.anim_right_in);
				animi.setAnimationListener(new AnimationListener() {

					@Override
					public void onAnimationStart(Animation animation) {
					}

					@Override
					public void onAnimationRepeat(Animation animation) {

					}

					@Override
					public void onAnimationEnd(Animation animation) {
						year = nextYear;
						month = nextMonth - 1;
						tx_date.setText(year + "年" + (month + 1) + "月");
						// requestData(year + "" + month + 1);
						showData(year + "" + month + 1);
					}
				});
				gv_calendar.startAnimation(animi);

			}
		});
		btn_home.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				openMenu();
			}
		});
		gv_calendar.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				final DeptDutysInfo1 dutysInfo1 = deptList.get(arg2);
				String noon = dutysInfo1.getDeptweek_noon();
				if (noon != null && !"".equals(noon)) {
					if (noon.length() == 10) {
						if (noon.endsWith("01")) {
							list = DataUtils.amtimelist();
						} else if (noon.endsWith("02")) {
							String locate = dutysInfo1.getLocate();
							if ("N".equals(locate)) {
								list = DataUtils.pmtimelist_n();
							} else if ("S".equals(locate)) {
								list = DataUtils.pmtimelist_s();
							}
						}
					} else if (noon.length() > 11) {
						String locate = dutysInfo1.getLocate();
						if ("N".equals(locate)) {
							list = DataUtils.quantiamlist_n();
						} else if ("S".equals(locate)) {
							list = DataUtils.quantiamlist_s();  
						}
					}
				}
				if (dutysInfo1.getDept_info_sname() != null
						&& !"".equals(dutysInfo1.getDept_info_sname().length())) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							context);
					builder.setTitle("请选择时间...");
					builder.setItems(list,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									String time = list[which];
									String noontype = "";
									String tim = time.substring(0, 2);
									if (tim != null && "".equals(tim)) {
										int seletime = Integer.parseInt(tim);
										if (seletime <= 12) {
											noontype = "01";
										} else if (seletime > 12) {
											noontype = "02";
										}
									}
									String date = dutysInfo1.getDutydate().replace("H", "");
									if (Config.phUsers != null) {
										Intent intent = new Intent(context,
												ConfimAppointmentActivity.class);
										intent.putExtra("dutydate", date);
										intent.putExtra("noontype", noontype);
										intent.putExtra("dutytime", time);
										intent.putExtra("dept_id",
												dutysInfo1.getDept_id());
										intent.putExtra("dept_name",
												dutysInfo1.getDept_info_sname());
										intent.putExtra("doc_id", "");
										intent.putExtra("doc_name", "");
										intent.putExtra("locate",
												dutysInfo1.getLocate());
										context.startActivity(intent);
										((Activity) context).finish();
									} else {
										AlertDialog.Builder builder = new AlertDialog.Builder(
												context);
										builder.setTitle("请登录");
										builder.setNeutralButton(
												"确定",
												new DialogInterface.OnClickListener() {
													@Override
													public void onClick(
															DialogInterface dialog,
															int which) {
														// TODO Auto-generated
														// method stub
														Intent intent = new Intent(
																context,
																LoginActivity.class);
														intent.putExtra(
																"logintype",
																"docinfo");
														context.startActivity(intent);
													}
												});

										builder.setNegativeButton(
												"取消",
												new DialogInterface.OnClickListener() {
													@Override
													public void onClick(
															DialogInterface dialog,
															int which) {
														// TODO Auto-generated
														// method stub

													}
												});
									}
								}
							});
					builder.create().show();
				}
			}
		});
	}

	/**
	 * 判断工作还是休息日
	 * 
	 * @param obj
	 *            排班对象
	 * @param position
	 *            日期索引
	 */
	private void workOrRest(DeptDutysInfo1 obj, int position) {
		if (obj.getDept_info_sname() == null
				|| obj.getDept_info_sname().length() == 0) {
			// 休息
			// tx_work_detail.setVisibility(View.GONE);
			// lay_time_line.setVisibility(View.GONE);
			// lay_foot.setBackgroundDrawable(getResources().getDrawable(
			// R.drawable.coffee));
		} else {
			// 工作
			// lay_foot.setBackgroundColor(getResources().getColor(
			// R.color.transparent));
			// tx_work_detail.setVisibility(View.VISIBLE);
			// lay_time_line.setVisibility(View.VISIBLE);
			// tx_work_time.setText(timeList.get(position));
			// tx_work_detail.setText(getResources().getString(R.string.cblb)
			// + obj.getCblb() + "\n"
			// + getResources().getString(R.string.zblx) + obj.getZblb()
			// + "\n" + getResources().getString(R.string.ksmc)
			// + obj.getKsmc());
		}
	}

	/**
	 * 初始化方法
	 */
	private void init() {
		buffData = new HashMap<String, List<DeptDutysInfo1>>();
		buffTime = new HashMap<String, List<String>>();
		DataCache dataCache = DataCache.getCache(context);
		doctorID = dataCache.getUserID();
		calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH);
		day = calendar.get(Calendar.DAY_OF_MONTH);

		curDate = year + "-" + (month + 1) + "-" + day;
		nextDate = year + "-" + (month + 1) + "-" + (day + 1);
		tx_date.setText(year + "年" + (month + 1) + "月");

		// 初始化日历集合,获得开始日期和结束日期
		initCalendar(year, month, calendar);
		// 请求排班数据集合

		showData(year + "" + month + 1);
	}

	@Override
	public void onFirstLoad() {
		// TODO Auto-generated method stub
		super.onFirstLoad();
		// requestData("-1");
		showData("-1");
	}

	private void showData(String key) {
		initCalendar(year, month, calendar);
		if (buffData.containsKey(key)) {
			deptList = buffData.get(key);
			timeList = buffTime.get(key);
			mAdapter = new Cadapter(deptList, context);
			gv_calendar.setAdapter(mAdapter);
			gv_calendar_gone.setAdapter(mAdapter);
			return;
		}
		getList(resultList);
		mAdapter = new Cadapter(deptList, context);
		gv_calendar.setAdapter(mAdapter);
		gv_calendar_gone.setAdapter(mAdapter);
	}

	/**
	 * 请求医生排班表
	 * 
	 * @param key
	 *            缓存索引
	 * @param startDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 */
	// private void requestData(String key) {
	// initCalendar(year, month, calendar);
	// // 是否有缓存
	// if (buffData.containsKey(key)) {
	// lst = buffData.get(key);
	// timeList = buffTime.get(key);
	// mAdapter = new Cadapter(lst, context);
	// gv_calendar.setAdapter(mAdapter);
	// gv_calendar_gone.setAdapter(mAdapter);
	// return;
	// }
	// lastIndex = -1;
	// YspbReq req = new YspbReq();
	// req.setStartDate(startDate);
	// req.setEndDate(endDate);
	// req.setDoctorID(doctorID);
	// GsonServlet<YspbReq, YspbRes> doreq = new GsonServlet<YspbReq, YspbRes>(
	// context);
	// doreq.request(req, YspbRes.class);
	// doreq.setOnResponseEndListening(new OnResponseEndListening<YspbReq,
	// YspbRes>() {
	//
	// @Override
	// public void onResponseEnd(YspbReq commonReq, YspbRes commonRes,
	// boolean result, String errmsg, int responseCode) {
	//
	// }
	//
	// @Override
	// public void onResponseEndSuccess(YspbReq commonReq,
	// YspbRes commonRes, String errmsg, int responseCode) {
	// if (commonRes != null) {
	// List<YspbInfo> resultList = commonRes.getYspbInfosLt();
	// getList(resultList);
	// mAdapter = new Cadapter(lst, context);
	// gv_calendar.setAdapter(mAdapter);
	// gv_calendar_gone.setAdapter(mAdapter);
	// }
	// }
	//
	// @Override
	// public void onResponseEndErr(YspbReq commonReq, YspbRes commonRes,
	// String errmsg, int responseCode) {
	// if (null != errmsg) {
	// Toast.makeText(context, errmsg, 3000).show();
	// }
	// }
	// });
	// }

	/**
	 * 对应年的对应月的天数
	 * 
	 * @param y
	 * @param month
	 * @return
	 */
	private int totalDay(int year, int month) {
		int leap_year = 0;
		boolean flag = false;
		if ((year % 4 == 0 && year % 1 != 0) || (year % 400 == 0)) {
			leap_year = 29;
		} else {
			leap_year = 28;
		}
		for (int i = 0; i < long_month.length; i++) {
			if (long_month[i] == month) {
				flag = true;
			}
		}
		if (month == 2) {
			return leap_year;
		} else if (flag) {
			return 31;
		} else {
			return 30;
		}
	}

	/**
	 * 生成日历集合
	 * 
	 * @param year
	 * @param month
	 * @param calendar
	 */
	private void initCalendar(int year, int month, Calendar calendar) {
		// lst = new ArrayList<YspbInfo>();
		deptList = new ArrayList<DeptDutysInfo1>();
		timeList = new ArrayList<String>();
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.DAY_OF_MONTH, 1);

		// 当前月
		int select_month = calendar.get(Calendar.MONTH) + 1;
		// 当天星期几
		int today = calendar.get(Calendar.DAY_OF_WEEK);

		int day = 0;

		// 上个月
		int lastM = select_month - 1;
		// 上一年
		int lastY = year;
		// 下一年
		int nextY = year;
		// 下个月
		int nextM = -1;

		// 判断是否跨年
		if (select_month - 1 == 0) {
			lastM = 12;
			lastY = year - 1;
		}
		if (select_month + 1 == 13) {
			nextY = year + 1;
		}

		// 标识日历的第一天
		boolean flag = true;
		for (int i = 1; i <= 6; i++) {
			for (int j = 1; j <= 7; j++) {
				// YspbInfo obj = new YspbInfo();
				DeptDutysInfo1 dutyinfos = new DeptDutysInfo1();
				// 加载下个月天数
				if (day == totalDay(year, select_month)) {
					day = 0;
					nextM = select_month + 1 == 13 ? 1 : select_month + 1;
				}
				if (i == 1 && j <= today - 1) {
					// 上个月最后一天是多少号
					int lastMonthTotal = totalDay(lastY, lastM);
					// 第一格距上个月最后一天
					int lastDay = (lastMonthTotal - today + j + 1);
					// obj.setCbrq("H" + comboString(lastY, lastM, lastDay));
					dutyinfos.setDutydate("H"
							+ comboString(lastY, lastM, lastDay));
					// 日历第一天（第一格开始）
					if (flag) {
						// startDate = obj.getCbrq();
						startDate = "H" + comboString(lastY, lastM, lastDay);
					}
					flag = false;
				} else {
					day++;
					String date = "";
					// 是否到达下个月
					if (nextM == -1) {
						date = comboString(year, select_month, day);
					} else {
						date = "H" + comboString(nextY, nextM, day);
					}
					// 日历中最后一天(最后一格)
					// endDate = date.replace("H", "");
					endDate = comboString(year, select_month, day);
					// obj.setCbrq(date);
					dutyinfos.setDutydate(date);
				}
				// lst.add(obj);
				deptList.add(dutyinfos);
				timeList.add(dutyinfos.getDutydate());
			}
		}
		// 当第一格为当月的第一天
		if (flag == true) {
			startDate = comboString(year, select_month, 1);
		}
		startDate = startDate.replace("H", "");
		lastYear = lastY;
		lastMonth = lastM;
		nextYear = nextY;
		nextMonth = nextM;
		// Log.i("start", startDate);
		// Log.i("end", endDate);
	}

	/**
	 * 向日历集合中插入请求数据
	 * 
	 * @param resultList
	 */
	private void getList(List<DeptDutysInfo> resultList) {
		// List<String> dateTimeStr = DataUtils.getDateTimeStr(14, false);
		if (null == resultList || resultList.size() == 0) {
			buffData.put(year + "" + month + 1, deptList);
			buffTime.put(year + "" + month + 1, timeList);
			return;
		}
		boolean flag = false;
		int count = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i < deptList.size(); i++) {
			DeptDutysInfo1 tmp = deptList.get(i);
			if (compareDate(tmp.getDutydate().replace("H", ""))) {
				Date parse = null;
				try {
					parse = sdf.parse(tmp.getDutydate().replace("H", ""));
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String day = parse.getDay() + "";
				String noontype = "";
				for (int j = 0; j < resultList.size(); j++) {
					DeptDutysInfo obj = resultList.get(j);
					if (day.equals(obj.getDeptweek_day())) {
						noontype = obj.getDeptweek_noon() + "-" + noontype;
						timeList.set(i, tmp.getDutydate());
						tmp.setDept_id(obj.getDept_id());
						tmp.setDept_info_sname(obj.getDept_info_sname());
						tmp.setDeptweek_avil(obj.getDeptweek_avil());
						tmp.setDeptweek_day(obj.getDeptweek_day());
						tmp.setId(obj.getId());
						tmp.setLocate(obj.getLocate());
					}
				}
				if (!"".equals(noontype)) {
					noontype = noontype.substring(0, noontype.length() - 1);
					tmp.setDeptweek_noon(noontype);
				}
			}
//			DeptDutysInfo1 tmp = deptList.get(i);
//			if (compareDate(tmp.getDutydate().replace("H", ""), nextDate)) {
//				flag = true;
//				// continue;
//			}
//			if (flag && count < 14) {
//				count++;
//				Date parse = null;
//				try {
//					parse = sdf.parse(tmp.getDutydate().replace("H", ""));
//				} catch (java.text.ParseException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				String day = parse.getDay() + "";
//				String noontype = "";
//				for (int j = 0; j < resultList.size(); j++) {
//					DeptDutysInfo obj = resultList.get(j);
//					if (day.equals(obj.getDeptweek_day())) {
//						noontype = obj.getDeptweek_noon() + "-" + noontype;
//						timeList.set(i, tmp.getDutydate());
//						tmp.setDept_id(obj.getDept_id());
//						tmp.setDept_info_sname(obj.getDept_info_sname());
//						tmp.setDeptweek_avil(obj.getDeptweek_avil());
//						tmp.setDeptweek_day(obj.getDeptweek_day());
//						tmp.setId(obj.getId());
//						tmp.setLocate(obj.getLocate());
//					}
//				}
//				if (!"".equals(noontype)) {
//					noontype = noontype.substring(0, noontype.length() - 1);
//					tmp.setDeptweek_noon(noontype);
//				}
//			}
		}
		buffData.put(year + "" + month + 1, deptList);
		buffTime.put(year + "" + month + 1, timeList);
	}

	/**
	 * 比较日期大小
	 * 
	 * @param dateA
	 * @param dateB
	 * @return
	 */
	private boolean compareDate(String dateA, String dateB) {
		dateB = dateB.substring(0, 10);
		if (java.sql.Date.valueOf(dateA).equals(java.sql.Date.valueOf(dateB))) {
			return true;
		}
		return false;
	}
	private List<String> datelist = DataUtils.getDateTimeStr(14, false);
	private boolean compareDate(String dateA) {
		for (String date : datelist) {
			if (java.sql.Date.valueOf(dateA).equals(java.sql.Date.valueOf(date))) { 
				return true;
			}
		}
		return false;
	}

	private String comboString(int y, int m, int d) {
		return y + "-" + m + "-" + d;
	}

	class Cadapter extends BaseAdapter {

		// List<YspbInfo> lst;
		List<DeptDutysInfo1> deptList;

		Context context;

		public Cadapter(List<DeptDutysInfo1> deptList, Context context) {
			this.deptList = deptList;
			this.context = context;

		}

		@Override
		public int getCount() {
			return deptList.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			DeptDutysInfo1 obj = deptList.get(position);
			WorkplanInfo seat = new WorkplanInfo();
			if (convertView == null) {
				View v = LayoutInflater.from(context).inflate(
						R.layout.grid_item, null);
				v.setLayoutParams(new AbsListView.LayoutParams(
						LayoutParams.FILL_PARENT, height));
				seat = new WorkplanInfo();
				seat.num = (TextView) v.findViewById(R.id.tx_num);
				seat.lay_ground = v.findViewById(R.id.lay_background);
				seat.img_noon_am = (ImageView) v.findViewById(R.id.img_noon_am);
				seat.img_noon_pm = (ImageView) v.findViewById(R.id.img_noon_pm);
				v.setTag(seat);
				convertView = v;
			} else {
				seat = (WorkplanInfo) convertView.getTag();
			}
			String date = obj.getDutydate();
			String day = date.substring(date.lastIndexOf("-") + 1);

			// 非本月日期变灰
			if (date.contains("H")) {
				// 变灰
				seat.num.setTextColor(getResources().getColor(R.color.l_grey));
				seat.lay_ground.setBackgroundColor(getResources().getColor(
						R.color.n_grey));
			} else {
				// 周六周日日期变红
				if (position % 7 == 0 || (position + 1) % 7 == 0) {
					seat.num.setTextColor(getResources()
							.getColor(R.color.l_red));
				}
			}
			seat.num.setText(day);
			String nowD = date.replace("H", "");
			// 默认选中系统当日
			if (curDate.equals(nowD)) {
				seat.lay_ground.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.quan1));
				convertView.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.daybj));
				lastIndex = position;
				workOrRest(obj, position);
			}
			// if (obj.getKsmc() != null && obj.getKsmc().length() != 0) {
			// seat.img.setVisibility(View.VISIBLE);
			// }
			String noon = obj.getDeptweek_noon();
			if (noon != null && !"".equals(noon)) {
				if (noon.length() == 10) {
					if (noon.endsWith("01")) {
						seat.img_noon_am.setVisibility(View.VISIBLE);
					} else if (noon.endsWith("02")) {
						seat.img_noon_pm.setVisibility(View.VISIBLE);
					}
				} else if (noon.length() > 11) {
					String[] split = noon.split("-");
					for (int i = 0; i < split.length; i++) {
						if (split[i].endsWith("01")) {
							seat.img_noon_am.setVisibility(View.VISIBLE);
						} else if (split[i].endsWith("02")) {
							seat.img_noon_pm.setVisibility(View.VISIBLE);
						}
					}
				}
			}
			return convertView;
		}

		class WorkplanInfo {
			TextView num;
			View lay_ground;
			ImageView img_noon_am;
			ImageView img_noon_pm;
		}

	}

}
