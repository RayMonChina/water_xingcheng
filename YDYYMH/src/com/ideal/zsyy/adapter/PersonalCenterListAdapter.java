package com.ideal.zsyy.adapter;

import java.util.List;

import com.jijiang.wtapp.R;
import com.ideal.zsyy.base.PersonalCenter;
import com.ideal.zsyy.entity.TopVisitResInfo;
import com.ideal.zsyy.request.CancelTopVisitReq;
import com.ideal.zsyy.request.SmsReq;
import com.ideal.zsyy.response.CancelTopVisitRes;
import com.ideal.zsyy.response.SmsRes;
import com.ideal.zsyy.utils.DataUtils;
import com.ideal2.base.gson.GsonServlet;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class PersonalCenterListAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	// private List<PersonalCenter> list;
	private List<TopVisitResInfo> list;
	private boolean flag = true;
	private Handler mHandler;
	private String phonenumber;

	public PersonalCenterListAdapter(Context context,
			List<TopVisitResInfo> list, boolean flag, Handler mHandler,
			String phonenumber) {

		this.inflater = LayoutInflater.from(context);
		this.context = context;
		this.list = list;
		this.flag = flag;
		this.mHandler = mHandler;
		this.phonenumber = phonenumber;
	}

	public int getCount() {

		return list.size();

	}

	public Object getItem(int position) {

		return list.get(position);

	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		// if (convertView == null) {
		convertView = inflater
				.inflate(R.layout.personal_center_list_item, null);
		holder = new ViewHolder();

		holder.pc_yuyue_no = (TextView) convertView
				.findViewById(R.id.pc_yuyue_no);
		holder.pc_yuyue_room_or_person = (TextView) convertView
				.findViewById(R.id.pc_yuyue_room_or_person);
		holder.pc_yuyue_time = (TextView) convertView
				.findViewById(R.id.pc_yuyue_time);
		holder.pc_yuyue_result = (TextView) convertView
				.findViewById(R.id.pc_yuyue_result);
		holder.pc_item_text = (TextView) convertView
				.findViewById(R.id.pc_item_text);
		holder.tv_quiet = (TextView) convertView.findViewById(R.id.tv_quiet);
		holder.tv_locate = (TextView) convertView.findViewById(R.id.tv_locate);
		holder.pc_pat_name = (TextView) convertView
				.findViewById(R.id.pc_pat_name);
		convertView.setTag(holder);
		// } else {
		// holder = (ViewHolder) convertView.getTag();
		// }

		final TopVisitResInfo visitResInfo = list.get(position);
		String locate = visitResInfo.getReg_locate();

		if ("S".equals(locate)) {
			holder.tv_locate.setText("上海市第一个人民医院南院");
		} else if ("N".equals(locate)) {
			holder.tv_locate.setText("上海市第一个人民医院北院");
		} else {
			holder.tv_locate.setText("上海市第一个人民医院");
		}

		holder.pc_yuyue_no.setText(visitResInfo.getSeqn().substring(16));
		holder.pc_pat_name.setText(visitResInfo.getPat_name());
		String dept_name = visitResInfo.getReg_dept_name();
		if ("".equals(visitResInfo.getReg_doc_id())) {
			holder.pc_item_text.setText("预约科室:");
			holder.pc_yuyue_room_or_person.setText(dept_name);
		} else {
			holder.pc_item_text.setText("预约专家:");
			String docname = visitResInfo.getReg_doc_name();
			if (dept_name != null && !"".equals(dept_name)) {
				docname = docname + "(" + dept_name + ")";
			}
			holder.pc_yuyue_room_or_person.setText(docname);
		}
		String time1 = DataUtils.convertString(visitResInfo.getReg_time(),
				locate);
		String time = visitResInfo.getReg_date() + "  " + time1;
		holder.pc_yuyue_time.setText(time);
		String convertString = time1.substring(6);
		String compare_date = visitResInfo.getReg_date() + " " + convertString;
		boolean flag2 = DataUtils.compare_date(compare_date);
		if (flag) {
			if (!flag2) {
				if ("0".equals(visitResInfo.getStatus())) {
					holder.pc_yuyue_result.setText("预约成功");
					holder.tv_quiet
							.setOnClickListener(new View.OnClickListener() {
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									AlertDialog.Builder builder = new AlertDialog.Builder(
											context);
									builder.setTitle("确定取消预约?");
									builder.setNeutralButton(
											"确定",
											new DialogInterface.OnClickListener() {
												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
													// TODO Auto-generated
													// method stub
													cancelVisitInfo(visitResInfo);
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
									builder.create().show();
								}
							});
				} else {
					holder.pc_yuyue_result.setText("已挂号成功");
					holder.tv_quiet.setText("已挂号成功");
					holder.tv_quiet.setBackgroundResource(R.drawable.guahao);
					holder.tv_quiet.setVisibility(View.VISIBLE);
				}
			}
		} else if ("0".equals(visitResInfo.getStatus()) && flag2) {
			holder.pc_yuyue_result.setText("预约成功");
			holder.tv_quiet.setText("预约已过期");
			holder.tv_quiet.setBackgroundResource(R.drawable.pastdue);
			holder.tv_quiet.setVisibility(View.VISIBLE);
		} else {
			holder.pc_yuyue_result.setText("已取消预约");
			holder.tv_quiet.setVisibility(View.GONE);
		}

		return convertView;
	}

	public final class ViewHolder {

		public TextView pc_yuyue_no, pc_yuyue_room_or_person, pc_yuyue_time,
				pc_yuyue_result, pc_item_text, tv_quiet, tv_locate,
				pc_pat_name;

	}

	public void cancelVisitInfo(final TopVisitResInfo visitResInfo) {
		CancelTopVisitReq cancelReq = new CancelTopVisitReq();
		cancelReq.setSeqn(visitResInfo.getSeqn());
		cancelReq.setVstCardNo(visitResInfo.getVst_card_no());
		cancelReq.setOperType("34");
		GsonServlet<CancelTopVisitReq, CancelTopVisitRes> gsonServlet = new GsonServlet<CancelTopVisitReq, CancelTopVisitRes>(
				context);
		gsonServlet.request(cancelReq, CancelTopVisitRes.class);
		gsonServlet
				.setOnResponseEndListening(new GsonServlet.OnResponseEndListening<CancelTopVisitReq, CancelTopVisitRes>() {

					@Override
					public void onResponseEnd(CancelTopVisitReq commonReq,
							CancelTopVisitRes commonRes, boolean result,
							String errmsg, int responseCode) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onResponseEndSuccess(
							CancelTopVisitReq commonReq,
							CancelTopVisitRes commonRes, String errmsg,
							int responseCode) {
						// TODO Auto-generated method stub
						if (commonRes != null) {
							String result = commonRes.getResult();
							if ("1".equals(result)) {
								Toast.makeText(context, "成功取消预约!!", 1).show();
								// String msg = "您已取消上海市第一人民医院" +
								// visitResInfo.getReg_date() + "  "
								// + visitResInfo.getReg_time();
								// if (visitResInfo.getReg_dept_id() != null &&
								// !"".equals(visitResInfo.getReg_dept_id())) {
								// msg += visitResInfo.getReg_dept_name();
								// } else if(visitResInfo.getReg_doc_id() !=
								// null &&
								// !"".equals(visitResInfo.getReg_doc_id())){
								// msg += visitResInfo.getReg_doc_name();
								// }
								// msg += "(市一医院)";

								SmsReq smsreq = new SmsReq();
								smsreq.setDest_number(phonenumber);
								smsreq.setContent(commonRes.getSmscontent());
								smsreq.setOperType("102");
								sendSmsMessge(smsreq);
							} else if ("0".equals(result)) {
								Toast.makeText(context, "预约取消失败!!", 1).show();
							}
							Message msg = mHandler.obtainMessage(1);
							mHandler.sendMessage(msg);
						}
					}

					@Override
					public void onResponseEndErr(CancelTopVisitReq commonReq,
							CancelTopVisitRes commonRes, String errmsg,
							int responseCode) {
						// TODO Auto-generated method stub

					}
				});
	}

	protected void sendSmsMessge(SmsReq smsreq) {
		// TODO Auto-generated method stub
		GsonServlet<SmsReq, SmsRes> gsonServlet = new GsonServlet<SmsReq, SmsRes>(
				context);
		gsonServlet.request(smsreq, SmsRes.class);
		gsonServlet.setShowDialog(false);
		gsonServlet
				.setOnResponseEndListening(new GsonServlet.OnResponseEndListening<SmsReq, SmsRes>() {
					@Override
					public void onResponseEnd(SmsReq commonReq,
							SmsRes commonRes, boolean result, String errmsg,
							int responseCode) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onResponseEndSuccess(SmsReq commonReq,
							SmsRes commonRes, String errmsg, int responseCode) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onResponseEndErr(SmsReq commonReq,
							SmsRes commonRes, String errmsg, int responseCode) {
						// TODO Auto-generated method stub

					}
				});
	}

}
