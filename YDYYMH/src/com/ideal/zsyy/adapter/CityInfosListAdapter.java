package com.ideal.zsyy.adapter;

import java.util.ArrayList;
import java.util.List;

import com.jijiang.wtapp.R;
import com.ideal.zsyy.entity.CityEntity;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

public class CityInfosListAdapter extends BaseAdapter implements Filterable {

	private Context context;
	private List<CityEntity> cityEntitys;
	private List<CityEntity> oldcityEntitys;
	private Handler mHandler;

	private List<String> cityNames;
	private boolean isSeacher;

	public CityInfosListAdapter(Context context, List<CityEntity> cityEntitys,
			Handler mHandler) {
		super();
		this.context = context;
		this.mHandler = mHandler;
		if (this.cityEntitys != null) {
			this.cityEntitys.clear();
		}
		this.cityEntitys = cityEntitys;
		this.oldcityEntitys = this.cityEntitys;
		initSomeList();
	}

	private void initSomeList() {
		cityNames = new ArrayList<String>();
		for (CityEntity entity : cityEntitys) {
			cityNames.add(entity.getCity_name());
		}
	}

	public void setSeacher(boolean isSeacher) {
		this.isSeacher = isSeacher;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return cityEntitys.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return cityEntitys.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public boolean isEnabled(int position) {
		// TODO Auto-generated method stub
		if (cityEntitys.get(position).getCity_name().length() == 1) {
			return false;
		}
		return super.isEnabled(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHodler hodler = new ViewHodler();
		if (!isSeacher) {
			String cityName = cityEntitys.get(position).getCity_name();
			if (cityName.length() == 1) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.city_list_item, null);
				hodler.tv_index = (TextView) convertView
						.findViewById(R.id.indexTv);
			} else {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.citys_list_item, null);
				hodler.tv_cityName = (TextView) convertView
						.findViewById(R.id.tv_cityname);
			}
			CityEntity cityEntity = cityEntitys.get(position);
			if (cityName.length() == 1) {
				hodler.tv_index.setText(cityEntity.getCity_name());
			} else {
				hodler.tv_cityName.setText(cityEntity.getCity_name());
			}
		} else {
			if (convertView != null) {
				convertView = null;
			}
			convertView = LayoutInflater.from(context).inflate(
					R.layout.citys_list_item, null);
			TextView tv_cityName = (TextView) convertView
					.findViewById(R.id.tv_cityname);
			CityEntity cityEntity = cityEntitys.get(position);
			if (cityEntity.getCity_name().length() > 1) {
				tv_cityName.setText(cityEntity.getCity_name());
			}
		}
		return convertView;
	}

	static class ViewHodler {
		TextView tv_cityName;
		TextView tv_index;
	}

	@Override
	public Filter getFilter() {
		// TODO Auto-generated method stub
		Filter filter = new Filter() {
			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				// TODO Auto-generated method stub
				FilterResults results = new FilterResults();
				ArrayList<CityEntity> cityentity = new ArrayList<CityEntity>();
				if (oldcityEntitys != null && oldcityEntitys.size() > 0) {
					for (int i = 0; i < cityNames.size(); i++) {
						if (cityNames.get(i).contains(constraint)) {
							cityentity.add(oldcityEntitys.get(i));
						}
					}
				}
				results.values = cityentity;
				results.count = cityentity.size();
				return results;
			}

			@Override
			protected void publishResults(CharSequence constraint,
					FilterResults results) {
				// TODO Auto-generated method stub
				cityEntitys = (List<CityEntity>) results.values;
				if (results.count > 0) {
					notifyDataSetChanged();
					Message msg = mHandler.obtainMessage(2, cityEntitys);
					mHandler.sendMessage(msg);
				} else {
					notifyDataSetInvalidated();
				}
			}
		};
		return filter;
	}

}
