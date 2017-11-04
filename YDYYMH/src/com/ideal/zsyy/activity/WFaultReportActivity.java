package com.ideal.zsyy.activity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import com.jijiang.wtapp.R;
import com.ideal.zsyy.db.WdbManager;
import com.ideal.zsyy.entity.LocationInfo;
import com.ideal.zsyy.entity.WCBUserEntity;
import com.ideal.zsyy.entity.WFaultReportInfo;
import com.ideal.zsyy.entity.WPicItem;
import com.ideal.zsyy.entity.WUserItem;
import com.ideal.zsyy.service.PreferencesService;
import com.ideal.zsyy.utils.Bimp;
import com.ideal.zsyy.utils.ImageItem;
import com.ideal.zsyy.utils.PublicWay;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class WFaultReportActivity extends Activity {
	public static final int TAKE_PHOTO = 1;
	public static final int CROP_PHOTO = 2;
	public static final int TAKE_PICTURE = 1;
	private Button btn_back = null;

	private GridView noScrollgridview;
	private GridAdapter adapter;
	private View parentView;
	private PopupWindow pop = null;
	private LinearLayout ll_popup;
	public static Bitmap bimap;
	private Button btn_save;
	private TextView tv_userno, tv_bbh, tv_username, tv_dh, tv_address;
	private EditText et_descript;
	//private String userNo = "";
	private WdbManager dbManager;
	private WCBUserEntity userItem;
	private WFaultReportInfo reportInfo;
	private SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat simTimeSpan=new SimpleDateFormat("yyyyMMddHHmmss");
	private String currPicPath="";
	private String userNo="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		parentView = getLayoutInflater().inflate(R.layout.w_fault_report, null);
		setContentView(parentView);
		userNo=getIntent().getStringExtra("UserNo");
		dbManager = new WdbManager(getApplicationContext());
		initView();
		setEventListener();
		Init();
		this.InitData();
	}

	private void initView() {
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_save = (Button) findViewById(R.id.btn_save);
		tv_bbh = (TextView) findViewById(R.id.tv_bbh);
		tv_address = (TextView) findViewById(R.id.tv_address);
		tv_dh = (TextView) findViewById(R.id.tv_dh);
		tv_username = (TextView) findViewById(R.id.tv_username);
		tv_userno = (TextView) findViewById(R.id.tv_userno);
		et_descript = (EditText) findViewById(R.id.et_descript);
	}

	private void setEventListener() {
		// 返回按钮点击事件
		if (btn_back != null) {
			btn_back.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
				}
			});
		}
		if (btn_save != null) {
			btn_save.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					AddReport();
				}
			});
		}
	}

	public void Init() {
		Bimp.tempSelectBitmap.clear();
		Bimp.max=0;
		pop = new PopupWindow(WFaultReportActivity.this);

		View view = getLayoutInflater().inflate(R.layout.item_popupwindows,
				null);

		ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);

		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(LayoutParams.WRAP_CONTENT);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		pop.setContentView(view);

		RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
		Button bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
		Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
		Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
		parent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				photo();
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(WFaultReportActivity.this,
						AlbumActivity.class);
				intent.putExtra("className",
						WFaultReportActivity.class.getName());
				// startActivityForResult(intent, TAKE_ALBUM);
				startActivity(intent);
				overridePendingTransition(R.anim.activity_translate_in,
						R.anim.activity_translate_out);
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});

		noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
		noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new GridAdapter(this);
		adapter.update();
		noScrollgridview.setAdapter(adapter);
		noScrollgridview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 == Bimp.tempSelectBitmap.size()) {
					Log.i("ddddddd", "----------");
					ll_popup.startAnimation(AnimationUtils.loadAnimation(
							WFaultReportActivity.this,
							R.anim.activity_translate_in));
					pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
				} else {
					Intent intent = new Intent(WFaultReportActivity.this,
							GalleryActivity.class);
					intent.putExtra("position", "1");
					intent.putExtra("ID", arg2);
					intent.putExtra("className",
							WFaultReportActivity.class.getName());
					startActivity(intent);
				}
			}
		});

	}

	private void InitData() {
		if (userNo == "") {
			return;
		}
		userItem = dbManager.GetWCBUserItemByUserNo(userNo);
		reportInfo = dbManager.GetFaultReport(userNo);
		if (userItem != null) {
			tv_bbh.setText(userItem.getNoteNo());
			tv_address.setText(userItem.getAddress());
			tv_dh.setText(userItem.getPhone());
			tv_username.setText(userItem.getUserFName());
			tv_userno.setText(userItem.getUserNo());
		}
		if (reportInfo != null) {
			et_descript.setText(reportInfo.getFaultDescript());
			if (reportInfo.getPicItems() != null
					&& reportInfo.getPicItems().size() > 0) {
				for (WPicItem itm : reportInfo.getPicItems()) {
					ImageItem imageItem = new ImageItem();
					imageItem.setImagePath(itm.getPicPath());
					Bimp.tempSelectBitmap.add(imageItem);
				}
				adapter.update();
			}
		}
	}

	@SuppressLint("HandlerLeak")
	public class GridAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private int selectedPosition = -1;
		private boolean shape;

		public boolean isShape() {
			return shape;
		}

		public void setShape(boolean shape) {
			this.shape = shape;
		}

		public GridAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public void update() {
			loading();
		}

		public int getCount() {
			if (Bimp.tempSelectBitmap.size() == 1) {
				return 1;
			}
			return (Bimp.tempSelectBitmap.size() + 1);
		}

		public Object getItem(int arg0) {
			return null;
		}

		public long getItemId(int arg0) {
			return 0;
		}

		public void setSelectedPosition(int position) {
			selectedPosition = position;
		}

		public int getSelectedPosition() {
			return selectedPosition;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_published_grida,
						parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.item_grida_image);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (position == Bimp.tempSelectBitmap.size()) {
				holder.image.setImageBitmap(BitmapFactory.decodeResource(
						getResources(), R.drawable.icon_addpic_unfocused));
				if (position == 1) {
					holder.image.setVisibility(View.GONE);
				}
			} else {
				holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(position)
						.getBitmap());
			}

			return convertView;
		}

		public class ViewHolder {
			public ImageView image;
		}

		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					adapter.notifyDataSetChanged();
					break;
				}
				super.handleMessage(msg);
			}
		};

		public void loading() {
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						if (Bimp.max == Bimp.tempSelectBitmap.size()) {
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
							break;
						} else {
							Bimp.max += 1;
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
						}
					}
				}
			}).start();
		}
	}

	public String getString(String s) {
		String path = null;
		if (s == null)
			return "";
		for (int i = s.length() - 1; i > 0; i++) {
			s.charAt(i);
		}
		return path;
	}

	protected void onRestart() {
		adapter.update();
		super.onRestart();
	}

	public void photo() {
		currPicPath=Environment.getExternalStorageDirectory()
				 + "/Photo_LJ/"+userItem.getUserNo()+"_"+simTimeSpan.format(new Date()) + ".jpg";
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		 openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri
		 .fromFile(new File(currPicPath)));
		startActivityForResult(openCameraIntent, TAKE_PICTURE);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case TAKE_PICTURE:
			if (Bimp.tempSelectBitmap.size() < 3 && resultCode == RESULT_OK) {


				ImageItem takePhoto = new ImageItem();
				takePhoto.setImagePath(currPicPath);
				Bimp.tempSelectBitmap.add(takePhoto);
			}
			break;
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			for (int i = 0; i < PublicWay.activityList.size(); i++) {
				if (null != PublicWay.activityList.get(i)) {
					PublicWay.activityList.get(i).finish();
				}
			}

			finish();
		}
		return true;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (dbManager != null) {
			dbManager.closeDB();
		}
	}
	
	private void AddReport()
	{
		Map<String, Object>userInfo=new PreferencesService(WFaultReportActivity.this).getLoginInfo();
		String repContent=et_descript.getText().toString();
		if(repContent=="")
		{
			Toast.makeText(WFaultReportActivity.this,"报告内容不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		if(reportInfo==null)
		{
			reportInfo=new WFaultReportInfo();
		}
		reportInfo.setUserNo(userItem.getUserNo());
		reportInfo.setFaultDescript(repContent);
		reportInfo.setAlreadyUpload(1);
		reportInfo.setAddUserID(userInfo.get("userName").toString());
		reportInfo.setAddUser(userInfo.get("userName").toString());
		reportInfo.setBId(userItem.getBId());
		reportInfo.setAddDate(simpleDateFormat.format(new Date()));
		if(Bimp.tempSelectBitmap.size()>0)
		{
			ZsyyApplication application=(ZsyyApplication)getApplication();
			LocationInfo locationInfo=application.currPoint;
			if(reportInfo.getPicItems()==null)
			{
				reportInfo.setPicItems(new ArrayList<WPicItem>());
			}
			for(ImageItem itm:Bimp.tempSelectBitmap)
			{
				WPicItem picItem=null;
				boolean alreadyExists=false;
				for(WPicItem pItem:reportInfo.getPicItems())
				{
					if(pItem.getPicPath().equalsIgnoreCase(itm.imagePath))
					{
						alreadyExists=true;
						picItem=pItem;
						break;
					}
				}
				if(!alreadyExists)
				{
					picItem=new WPicItem();
					picItem.setAlreadyUpload(1);
					if(locationInfo!=null)
					{
						picItem.setLatitude(locationInfo.getLatitude());
						picItem.setLongitude(locationInfo.getLontitude());
					}
					reportInfo.getPicItems().add(picItem);
				}
				picItem.setAddDate(simpleDateFormat.format(new Date()));
				picItem.setAddUser(userInfo.get("userName").toString());
				picItem.setAddUserId(userInfo.get("use_id").toString());
				picItem.setPicName(new File(itm.getImagePath()).getName());
				picItem.setPicPath(itm.getImagePath());
				picItem.setPicType(2);
				picItem.setUserNo(userItem.getUserNo());
				picItem.setBId(userItem.getBId());
			}
		}
		dbManager.AddFaultReport(reportInfo);
		Toast.makeText(WFaultReportActivity.this,"保存成功！",Toast.LENGTH_SHORT).show();
		finish();
	}

}
