package com.ideal.zsyy.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.jijiang.wtapp.R;
import com.ideal.zsyy.entity.SkinInfo;

public class SkinChangeAdapter extends BaseAdapter {

	private List<SkinInfo> skininfos;
	private LayoutInflater mInflater;
	
	public SkinChangeAdapter(Context context,List<SkinInfo> skininfos){
		this.skininfos = skininfos;
		this.mInflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return skininfos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return skininfos.get(position);
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
//		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.skin_change_item, null);
			holder = new ViewHolder();
			holder.fl_skin = (FrameLayout) convertView.findViewById(R.id.fl_skin);
			holder.iv_choose = (ImageView) convertView.findViewById(R.id.iv_choose);
			convertView.setTag(holder);
//		} else {
//			holder = (ViewHolder) convertView.getTag();
//		}
		
		SkinInfo skinInfo = skininfos.get(position);
		holder.fl_skin.setBackgroundResource(skinInfo.getSkinPath());
		if ("1".equals(skinInfo.getIsChoose())) {
			holder.iv_choose.setVisibility(View.VISIBLE);
		} else if ("0".equals(skinInfo.getIsChoose())) {
			holder.iv_choose.setVisibility(View.GONE);
		}
		
		return convertView;
	}

	static class ViewHolder{
		FrameLayout fl_skin;
		ImageView iv_choose;
	}
}
