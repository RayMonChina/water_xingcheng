package com.ideal.zsyy.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.ideal.zsyy.Config;
import com.jijiang.wtapp.R;
import com.ideal.zsyy.entity.PhDoctorInfo;
import com.ideal.zsyy.imagecache.ImageLoader;
import com.ideal.zsyy.utils.AsynImageLoader;
import com.ideal.zsyy.utils.AsynImageLoader.ImageCallBack;
import com.ideal.zsyy.view.CircleImageView;

public class PhDoctorAdapter extends BaseAdapter implements Filterable {

	private List<PhDoctorInfo> phDoctorInfos; // 当前收索到的信息
	private List<PhDoctorInfo> oldDoctorInfos;
	private LayoutInflater mInflater;
	private Handler mHandler;
	private Context context;

	private List<String> docNameList;
	private List<String> docJobList;
	private List<String> docexpertiseList;

	private ImageLoader imageLoader;

	// private AsynImageLoader asynLoad = null;

	public PhDoctorAdapter(List<PhDoctorInfo> phDoctorInfos, Context context,
			Handler mHandler) {
		this.context = context;
		mInflater = LayoutInflater.from(context);
		this.mHandler = mHandler;
		if (this.phDoctorInfos != null) {
			this.phDoctorInfos.clear();
		}
		this.phDoctorInfos = phDoctorInfos;
		this.oldDoctorInfos = this.phDoctorInfos;
		// asynLoad = new AsynImageLoader();

		imageLoader = new ImageLoader(context);
		imageLoader.setRoundedCornerBitmap(true);
		initSomeList();
	}

	private void initSomeList() {
		docNameList = new ArrayList<String>();
		docJobList = new ArrayList<String>();
		docexpertiseList = new ArrayList<String>();
		for (PhDoctorInfo doctorInfo : phDoctorInfos) {
			docNameList.add(doctorInfo.getDoctor_Name());
			docJobList.add(doctorInfo.getJob_title());
			docexpertiseList.add(doctorInfo.getExpertise());
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return phDoctorInfos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return phDoctorInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		PhDoctorInfo doctorInfo = phDoctorInfos.get(position);
		String dept_name = doctorInfo.getDept_name_cn();
		ViewHolder holder;
		holder = new ViewHolder();

		if (dept_name != null && !"".equals(dept_name)) {
			convertView = mInflater.inflate(R.layout.doctorinfo_item1, null);
			holder.tv_deptname = (TextView) convertView.findViewById(R.id.tv_deptname); 
		} else {
			convertView = mInflater.inflate(R.layout.doctorinfo_item, null);
		}
		holder.tv_doctorName = (TextView) convertView
				.findViewById(R.id.tv_doctorname);
		holder.tv_doctorjob = (TextView) convertView
				.findViewById(R.id.tv_doctorjob);
		holder.tv_doctorexpertise = (TextView) convertView
				.findViewById(R.id.tv_doctorexpertise);
		holder.iv_doctorIcon = (ImageView) convertView
				.findViewById(R.id.iv_doctorIcon);
		holder.iv_ispro = (ImageView) convertView.findViewById(R.id.iv_ispro);
		holder.tv_deptname = (TextView) convertView
				.findViewById(R.id.tv_deptname);
		convertView.setTag(holder);

		if (doctorInfo != null) {
			Spanned docnamespan = Html.fromHtml(doctorInfo.getDoctor_Name());
			if (docnamespan != null) {
				holder.tv_doctorName.setText(docnamespan);
			} else {
				holder.tv_doctorName.setText(doctorInfo.getDoctor_Name());
			}

			Spanned jobspan = Html.fromHtml(doctorInfo.getJob_title());
			if (jobspan != null) {
				holder.tv_doctorjob.setText(jobspan);
			} else {
				holder.tv_doctorjob.setText(doctorInfo.getJob_title());
			}

			// String locate = doctorInfo.getLocate();
			// if (locate != null && !"".equals(locate)) {
			// if (locate.trim().equals("S")) {// 南院
			// holder.iv_locate.setImageResource(R.drawable.s);
			// } else if (locate.trim().equals("N")) {
			// holder.iv_locate.setImageResource(R.drawable.n);
			// }
			// } else {
			// holder.iv_locate.setVisibility(View.GONE);
			// }
			if (dept_name != null && !"".equals(dept_name)) {
				holder.tv_deptname.setText(dept_name);
			} 
			
			Spanned expertisespan = Html.fromHtml(doctorInfo.getExpertise());
			if (expertisespan != null) {
				holder.tv_doctorexpertise.setText(expertisespan);
			} else {
				holder.tv_doctorexpertise.setText(doctorInfo.getExpertise());
			}

			if ("0".equals(doctorInfo.getIs_Specialist())) {
				holder.iv_ispro.setVisibility(View.GONE);
			}

			if (doctorInfo.getPhoto() != null
					&& !"".equals(doctorInfo.getPhoto().trim())) {
				imageLoader.DisplayImage(
						Config.down_url + doctorInfo.getPhoto(),
						holder.iv_doctorIcon, this, false);
			} else {
				holder.iv_doctorIcon
						.setImageResource(R.drawable.default_doctor);
			}
		}
		return convertView;
	}

	static class ViewHolder {
		ImageView iv_doctorIcon;
		TextView tv_doctorName;
		TextView tv_doctorjob;
		TextView tv_doctorexpertise;
		TextView tv_deptname;
		ImageView iv_locate;
		ImageView iv_ispro;
	}

	@Override
	public Filter getFilter() {
		// TODO Auto-generated method stub
		Filter filter = new Filter() {

			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				// TODO Auto-generated method stub
				FilterResults results = new FilterResults();
				ArrayList<PhDoctorInfo> doctorInfo = new ArrayList<PhDoctorInfo>();
				int length = constraint.length();

				if (oldDoctorInfos != null && oldDoctorInfos.size() != 0) {
					for (int i = 0; i < docNameList.size(); i++) {
						if (docNameList.get(i).contains(constraint)) {
							// 高亮显示
							String name = docNameList.get(i);
							int index = name
									.indexOf(String.valueOf(constraint));
							String highText = name.substring(0, index)
									+ "<font color=#0080ff>"
									+ name.substring(index, index + length)
									+ "</font>"
									+ name.substring(index + length,
											name.length());
							oldDoctorInfos.get(i).setDoctor_Name(highText);
							doctorInfo.add(oldDoctorInfos.get(i));
						} else if (docJobList.get(i).contains(constraint)) {
							String job = docJobList.get(i);
							int index = job.indexOf(String.valueOf(constraint));
							String jobText = job.substring(0, index)
									+ "<font color=#0080ff>"
									+ job.substring(index, index + length)
									+ "</font>"
									+ job.substring(index + length,
											job.length());
							oldDoctorInfos.get(i).setJob_title(jobText);
							doctorInfo.add(oldDoctorInfos.get(i));
						} else if (docexpertiseList.get(i).contains(constraint)) {
							String expertise = docexpertiseList.get(i);
							int index = expertise.indexOf(String
									.valueOf(constraint));
							String expertiseText = expertise
									.substring(0, index)
									+ "<font color=#0080ff>"
									+ expertise
											.substring(index, index + length)
									+ "</font>"
									+ expertise.substring(index + length,
											expertise.length());
							oldDoctorInfos.get(i).setExpertise(expertiseText);
							doctorInfo.add(oldDoctorInfos.get(i));
						}
					}
				}
				results.values = doctorInfo;
				results.count = doctorInfo.size();
				return results;
			}

			@Override
			protected void publishResults(CharSequence constraint,
					FilterResults results) {
				phDoctorInfos = (List<PhDoctorInfo>) results.values;
				if (results.count > 0) {
					notifyDataSetChanged();
					Message msg = mHandler.obtainMessage(1, phDoctorInfos);
					mHandler.sendMessage(msg);
				} else {
					notifyDataSetInvalidated();
				}
			}
		};
		return filter;
	}

}
