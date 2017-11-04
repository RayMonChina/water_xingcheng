package com.amapv2.cn.apis;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.amapv2.cn.apis.offlinemap.OfflineMapDemo;
import com.amapv2.cn.apis.poisearch.PoisearchDemoActivity;
import com.amapv2.cn.apis.route.RouteDemoActivity;
import com.amapv2.cn.apis.view.FeatureView;

/**
 * AMapV2地图demo总汇
 */
public final class MainActivity extends ListActivity {
	private static class DemoDetails {
		private final int titleId;
		private final int descriptionId;
		private final Class<? extends android.app.Activity> activityClass;

		public DemoDetails(int titleId, int descriptionId,
				Class<? extends android.app.Activity> activityClass) {
			super();
			this.titleId = titleId;
			this.descriptionId = descriptionId;
			this.activityClass = activityClass;
		}
	}

	private static class CustomArrayAdapter extends ArrayAdapter<DemoDetails> {
		public CustomArrayAdapter(Context context, DemoDetails[] demos) {
			super(context, R.layout.feature, R.id.title, demos);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			FeatureView featureView;
			if (convertView instanceof FeatureView) {
				featureView = (FeatureView) convertView;
			} else {
				featureView = new FeatureView(getContext());
			}
			DemoDetails demo = getItem(position);
			featureView.setTitleId(demo.titleId);
			featureView.setDescriptionId(demo.descriptionId);
			return featureView;
		}
	}

	private static final DemoDetails[] demos = {
			new DemoDetails(R.string.basic_map, R.string.basic_description,
					BasicMapActivity.class),
			new DemoDetails(R.string.camera_demo, R.string.camera_description,
					CameraDemoActivity.class),
			new DemoDetails(R.string.events_demo, R.string.events_description,
					EventsDemoActivity.class),
			new DemoDetails(R.string.layers_demo, R.string.layers_description,
					LayersDemoActivity.class),
			new DemoDetails(R.string.locationsource_demo,
					R.string.locationsource_description,
					LocationSourceDemoActivity.class),
			new DemoDetails(R.string.locationGPS_demo,
					R.string.locationGPS_description,
					LocationGPSDemoActivity.class),
			new DemoDetails(R.string.locationNetwork_demo,
					R.string.locationNetwork_description,
					LocManagerProxyDemoActivity.class),
			new DemoDetails(R.string.geocoder_demo,
					R.string.geocoder_description, GeocoderDemoActivity.class),
			new DemoDetails(R.string.uisettings_demo,
					R.string.uisettings_description,
					UiSettingsDemoActivity.class),
			new DemoDetails(R.string.marker_demo, R.string.marker_description,
					MarkerDemoActivity.class),
			new DemoDetails(R.string.polygon_demo,
					R.string.polygon_description, PolygonDemoActivity.class),
			new DemoDetails(R.string.polyline_demo,
					R.string.polyline_description, PolylineDemoActivity.class),
			new DemoDetails(R.string.multi_map_demo,
					R.string.multi_map_description, MultiMapDemoActivity.class),
			new DemoDetails(R.string.programmatic_demo,
					R.string.programmatic_description,
					ProgrammaticDemoActivity.class),
			new DemoDetails(R.string.mapview_demo,
					R.string.mapview_description, MapViewDemoActivity.class),

			new DemoDetails(R.string.route_demo, R.string.reoute_description,
					RouteDemoActivity.class),

			new DemoDetails(R.string.poisearch_demo,
					R.string.poisearch_description, PoisearchDemoActivity.class),

			new DemoDetails(R.string.offlinemap_demo,
					R.string.offlinemap_description, OfflineMapDemo.class) };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		ListAdapter adapter = new CustomArrayAdapter(this, demos);
		setListAdapter(adapter);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		System.exit(0);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		DemoDetails demo = (DemoDetails) getListAdapter().getItem(position);
		startActivity(new Intent(this, demo.activityClass));
	}
}
