package com.ideal.zsyy.adapter;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.ideal.zsyy.Config;
import com.jijiang.wtapp.R;
import com.ideal.zsyy.entity.PhCommonContactInfo;
import com.ideal.zsyy.request.DelCommonContactReq;
import com.ideal.zsyy.response.DelCommonContactRes;
import com.ideal2.base.gson.GsonServlet;

public class CommonlyPatientListAdapter extends BaseAdapter {

	private List<PhCommonContactInfo> list;
	private LayoutInflater mInflater;
	private Context context;
	private boolean isEdit;
	
	private Handler mHandler;
	private ViewHodler hodler = null;

	public CommonlyPatientListAdapter(Context context,
			List<PhCommonContactInfo> list,Handler mHandler) {

		this.list = list;
		this.mInflater = LayoutInflater.from(context);
		this.context = context;
		this.mHandler=mHandler;

	}
	
	public void setEdit(boolean isEdit) {
		this.isEdit = isEdit;
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {

			convertView = mInflater.inflate(
					R.layout.commonly_patient_list_item, null);
			hodler = new ViewHodler();
			hodler.commonly_patient_list_name = (TextView) convertView
					.findViewById(R.id.commonly_patient_list_name);
			hodler.commonly_patient_list_old = (TextView) convertView
					.findViewById(R.id.commonly_patient_list_old);
			hodler.commonly_patient_list_tel = (TextView) convertView
					.findViewById(R.id.commonly_patient_list_tel);
			hodler.commonly_patient_list_sex = (TextView) convertView
					.findViewById(R.id.commonly_patient_list_sex);
			hodler.tv_delete = (TextView) convertView
					.findViewById(R.id.tv_delete);
			convertView.setTag(hodler);
		} else {
			hodler = (ViewHodler) convertView.getTag();
		}

		final PhCommonContactInfo cityHospitalListInfo = list.get(position);

//		Log.v("tags", cityHospitalListInfo.getContact_sex());
//
		if (cityHospitalListInfo.getContact_sex().trim().equals(Config.man)) {

			hodler.commonly_patient_list_sex.setText("男");

		} else if (cityHospitalListInfo.getContact_sex().trim()
				.equals(Config.woman)) {

			hodler.commonly_patient_list_sex.setText("女");
		} else {

			hodler.commonly_patient_list_sex.setText("");
		}

//		hodler.commonly_patient_list_sex.setText(cityHospitalListInfo
//				.getContact_sex());

		hodler.commonly_patient_list_name.setText(cityHospitalListInfo
				.getContact_name());
		hodler.commonly_patient_list_old.setText(cityHospitalListInfo
				.getContact_brithday());
		hodler.commonly_patient_list_tel.setText("电话："
				+ cityHospitalListInfo.getContact_phone());
		if (isEdit) {
			hodler.tv_delete.setVisibility(View.VISIBLE);
			hodler.tv_delete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					AlertDialog.Builder builder = new AlertDialog.Builder(
							context);
					builder.setTitle("确定要删除吗？");
					builder.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									isEdit=false;
									delCommonContact(cityHospitalListInfo);
								}
							});

					builder.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
								}

							});
					builder.create().show();
				}
			});
		}
		return convertView;
	}

	public class ViewHodler {

		TextView commonly_patient_list_sex, commonly_patient_list_name,
				commonly_patient_list_old, commonly_patient_list_tel,tv_delete;
	}
	
	private void delCommonContact(final PhCommonContactInfo contactInfo) {
		DelCommonContactReq contactReq = new DelCommonContactReq();
		contactReq.setContact_id(contactInfo.getId());
		contactReq.setOperType("47");
		GsonServlet<DelCommonContactReq, DelCommonContactRes> gsonServlet = new GsonServlet<DelCommonContactReq, DelCommonContactRes>(
				context);
		gsonServlet.request(contactReq, DelCommonContactRes.class);
		gsonServlet
				.setOnResponseEndListening(new GsonServlet.OnResponseEndListening<DelCommonContactReq, DelCommonContactRes>() {

					@Override
					public void onResponseEnd(DelCommonContactReq commonReq,
							DelCommonContactRes commonRes, boolean result,
							String errmsg, int responseCode) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onResponseEndSuccess(
							DelCommonContactReq commonReq,
							DelCommonContactRes commonRes, String errmsg,
							int responseCode) {
						// TODO Auto-generated method stub
						if(commonRes!=null){
							String result = commonRes.getResult();
							if("1".equals(result)){
								Toast.makeText(context, "删除成功", 1).show();
							}else if("0".equals(result)){
								Toast.makeText(context, "删除失败", 1).show();
							}
						}
						hodler.tv_delete.setVisibility(View.INVISIBLE);
						Message msg = mHandler.obtainMessage(4);
						mHandler.sendMessage(msg); 
					}

					@Override
					public void onResponseEndErr(DelCommonContactReq commonReq,
							DelCommonContactRes commonRes, String errmsg,
							int responseCode) {
						// TODO Auto-generated method stub

					}
				});
	}
}
