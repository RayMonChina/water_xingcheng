package com.ideal.zsyy.adapter;

import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ideal.zsyy.Config;
import com.jijiang.wtapp.R;
import com.ideal.zsyy.entity.FloorInfo;
import com.ideal.zsyy.imagecache.ImageLoader;
import com.ideal.zsyy.utils.BitmapUtil;

public class DeptInfoFloorPicAdapter extends BaseAdapter {
	private List<FloorInfo> buildlist;
	private LayoutInflater mInflater;
	private String dept_Name;
	private ImageLoader imageLoader; 
	private Handler mHandler;
	
	public DeptInfoFloorPicAdapter(Context context,List<FloorInfo> buildlist,String dept_Name,Handler mHandler){
		this.buildlist = buildlist;
		this.mInflater = LayoutInflater.from(context);
		this.dept_Name = dept_Name;
		imageLoader = new ImageLoader(context);
		imageLoader.setRoundedCornerBitmap(false);
		this.mHandler = mHandler;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return buildlist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return buildlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.floorpic_item, null);
			holder = new ViewHolder();
			holder.deptinfo_floorname = (TextView) convertView.findViewById(R.id.deptinfo_floorname);
			holder.hfd_img_text = (TextView) convertView.findViewById(R.id.hfd_img_text);
			holder.hfd_pic = (ImageView) convertView.findViewById(R.id.hfd_pic);
			holder.hfd_ll_text = (LinearLayout) convertView.findViewById(R.id.hfd_ll_text); 
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		FloorInfo floorInfo = buildlist.get(position);
		String floorname = "";
		String floor_pic = "";
		if (floorInfo.getFloor_name() == null) {
			floorname += ""  + floorInfo.getBuild_name();
			floor_pic += floorInfo.getBuild_pic();
		} else if (floorInfo.getFloor_name() != null && !"".equals(floorInfo.getFloor_name())) {
			floorname += "" + floorInfo.getBuild_name() + " " + floorInfo.getFloor_name() + " - " + dept_Name;
			if ("1".equals(floorInfo.getDept_type())) {
				floorname += "门诊平面图";
			} else if ("2".equals(floorInfo.getDept_type())) {
				floorname += "病房平面图";
			}
			floor_pic += floorInfo.getFloor_pic();
		}
		
		holder.deptinfo_floorname.setText(floorname);
		if (floor_pic != null && !"".equals(floor_pic)) {
			BitmapUtil.downloadBitmap(holder.hfd_pic, Config.down_url + floor_pic,
					mHandler, 0); 
//			imageLoader.DisplayImage(
//					Config.down_url + floor_pic,
//					holder.hfd_pic, this, false); 
			holder.hfd_ll_text.setVisibility(View.GONE);
		} else {
			holder.hfd_img_text.setText("图片暂无");
		}
		return convertView;
	}

	class ViewHolder{
		TextView deptinfo_floorname;
		TextView hfd_img_text;
		ImageView hfd_pic;
		LinearLayout hfd_ll_text;;
	}
}
