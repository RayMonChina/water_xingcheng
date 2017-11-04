package com.ideal.zsyy.activity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ideal.zsyy.db.WdbManager;
import com.ideal.zsyy.entity.LocationInfo;
import com.ideal.zsyy.entity.WPicItem;
import com.ideal.zsyy.entity.WUserItem;
import com.ideal.zsyy.service.PreferencesService;
import com.ideal.zsyy.utils.Bimp;
import com.ideal.zsyy.utils.ImageItem;
import com.ideal.zsyy.utils.PublicWay;
import com.jijiang.wtapp.R;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class WCBRealActivity extends Activity {
	public static final int TAKE_PICTURE = 1;
	
	private Button btn_back = null;
	private GridView noScrollgridview;
	private GridAdapter adapter;
	private View parentView;
	private PopupWindow pop = null;
	private LinearLayout ll_popup;
	public static Bitmap bimap;
	private Button btn_save;
	private TextView tv_w_userno,tv_w_bbh,tv_w_username,tv_w_dh,tv_w_dz,
	tv_w_sybs,tv_w_sysl,tv_w_sysf,tv_w_price_type,tv_w_steal_no,
	tv_w_cb_tag,tv_w_totle_owemoney;
	private EditText et_w_byds;
	private String userNo="";
	private WdbManager dbmanager=null;
	private WUserItem userItem=null;
	private String currPicPath="";
	private SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		parentView = getLayoutInflater().inflate(R.layout.w_cb_real, null);
		setContentView(parentView);
		dbmanager=new WdbManager(getApplicationContext());
		userNo=getIntent().getStringExtra("UserNo");
		initView();
		Init();
		this.InitData();
	}

	private void initView() {
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_save=(Button)findViewById(R.id.btn_save);
		
		tv_w_bbh=(TextView)findViewById(R.id.tv_w_bbh);
		tv_w_cb_tag=(TextView)findViewById(R.id.tv_w_cb_tag);
		tv_w_dh=(TextView)findViewById(R.id.tv_w_dh);
		tv_w_dz=(TextView)findViewById(R.id.tv_w_dz);
		tv_w_price_type=(TextView)findViewById(R.id.tv_w_price_type);
		tv_w_steal_no=(TextView)findViewById(R.id.tv_w_steal_no);
		tv_w_sybs=(TextView)findViewById(R.id.tv_w_sybs);
		tv_w_sysf=(TextView)findViewById(R.id.tv_w_sysf);
		tv_w_sysl=(TextView)findViewById(R.id.tv_w_sysl);
		tv_w_totle_owemoney=(TextView)findViewById(R.id.tv_w_totle_owemoney);
		tv_w_username=(TextView)findViewById(R.id.tv_w_username);
		tv_w_userno=(TextView)findViewById(R.id.tv_w_userno);
		et_w_byds=(EditText)findViewById(R.id.et_w_byds);
		if (btn_back != null) {
			btn_back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
				}
			});
		}
		if(btn_save!=null)
		{
			btn_save.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ChaoBiao();
				}
			});
		}
	}

	public void Init() {
		Bimp.tempSelectBitmap.clear();
		Bimp.max=0;
		pop = new PopupWindow(WCBRealActivity.this);

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
				Intent intent = new Intent(WCBRealActivity.this,
						AlbumActivity.class);
				intent.putExtra("className",
						WCBRealActivity.class.getName());
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
					ll_popup.startAnimation(AnimationUtils.loadAnimation(WCBRealActivity.this,
							R.anim.activity_translate_in));
					pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
				} else {
					Intent intent = new Intent(WCBRealActivity.this,
							GalleryActivity.class);
					intent.putExtra("position", "1");
					intent.putExtra("ID", arg2);
					intent.putExtra("className",
							WCBRealActivity.class.getName());
					startActivity(intent);
				}
			}
		});

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
			if (Bimp.tempSelectBitmap.size() == 3) {
				return 3;
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
				if (position == 3) {
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

	public void photo() {
		currPicPath=Environment.getExternalStorageDirectory()+ "/Photo_LJ/"+userNo+"_"+(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())) + ".jpg";
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		 openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri
		 .fromFile(new File(currPicPath)));
		startActivityForResult(openCameraIntent, TAKE_PICTURE);
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		adapter.update();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case TAKE_PICTURE:
			if (Bimp.tempSelectBitmap.size() < 3 && resultCode == RESULT_OK) {
				ImageItem takePhoto = new ImageItem();
				takePhoto.setImagePath(currPicPath);
				takePhoto.getBitmap();
				Bimp.tempSelectBitmap.add(takePhoto);
			}
			break;
		}
	}

	@Override
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

	private void InitData()
	{
		if(userNo==null||userNo=="")
		{
			return;
		}
		userItem=dbmanager.GetUserItemByNo(userNo);
		if(userItem==null)
		{
			return;
		}
		tv_w_bbh.setText(userItem.getBbh());
		tv_w_cb_tag.setText(userItem.getCbbz()==2?"已抄表":"未抄表");
		tv_w_dh.setText(userItem.getDh());
		tv_w_dz.setText(userItem.getDz());
		tv_w_price_type.setText(userItem.getJglxName());
		tv_w_steal_no.setText(userItem.getBgh());
		tv_w_sybs.setText(String.valueOf(userItem.getSyzd()));
		tv_w_sysf.setText(String.valueOf(userItem.getSysf()));
		tv_w_sysl.setText(String.valueOf(userItem.getSysl()));
		tv_w_totle_owemoney.setText(String.valueOf(userItem.getLjqf()));
		tv_w_username.setText(userItem.getXm());
		tv_w_userno.setText(userItem.getYhbm());
		et_w_byds.setText(String.valueOf(userItem.getByzd()));
		List<WPicItem>picItems=dbmanager.GetPicItem(userNo,1);
		userItem.setPicItems(picItems);
		if(picItems!=null&&picItems.size()>0)
		{
			for(WPicItem pic:picItems)
			{
				ImageItem imgItem=new ImageItem();
				imgItem.setImagePath(pic.getPicPath());
				Bimp.tempSelectBitmap.add(imgItem);
			}
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(dbmanager!=null)
		{
			dbmanager.closeDB();
		}
	}
	
	private void ChaoBiao()
	{
		if(userItem==null)
		{
			userItem=new WUserItem();
		}
		double dbysl=0;
		try {
			dbysl=Double.parseDouble(et_w_byds.getText().toString());
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(WCBRealActivity.this,"请输入正确的数字",Toast.LENGTH_SHORT).show();
			return;
		}
		Map<String, Object>userInfo=new PreferencesService(WCBRealActivity.this).getLoginInfo();
		ZsyyApplication application=(ZsyyApplication)getApplication();
		LocationInfo locationInfo=application.currPoint;
		userItem.setYhbm(userNo);
		if(locationInfo!=null)
		{
			userItem.setLatitude(locationInfo.getLatitude());
			userItem.setLongitude(locationInfo.getLontitude());
		}
		userItem.setCbbz(2);
		userItem.setCbrq(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		userItem.setByzd(dbysl);
		
		if(userItem.getSl()>0)
		{
			double currMonthFee=dbmanager.CalcPrice(userItem.getSl(),userItem.getJglx());
			if(currMonthFee==0)
			{
				Toast.makeText(WCBRealActivity.this,"请在设置中获取最新的水价",Toast.LENGTH_SHORT).show();
				Intent intentSetting=new Intent(WCBRealActivity.this,WSystemSettingActivity.class);
				startActivity(intentSetting);
				return;
			}
			userItem.setCurrentMonthFee(currMonthFee);
		}
		
		double dTotleOweMoney=userItem.getYcje()-(userItem.getCurrentMonthFee()+userItem.getLjqf());
		dTotleOweMoney=Math.round(dTotleOweMoney*1000)/1000;
		if(dTotleOweMoney>0)
		{
			userItem.setLjqf(0);
			userItem.setYcje(dTotleOweMoney);
		}
		else {
			userItem.setYcje(0);
			userItem.setLjqf(Math.abs(dTotleOweMoney));
		}
		if(Bimp.tempSelectBitmap.size()>0)
		{
			if(userItem.getPicItems()==null)
			{
				userItem.setPicItems(new ArrayList<WPicItem>());
			}
			for(ImageItem itm:Bimp.tempSelectBitmap)
			{
				WPicItem picItem=null;
				boolean alreadyExists=false;
				for(WPicItem pItem:userItem.getPicItems())
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
					if(locationInfo!=null)
					{
						picItem.setLatitude(locationInfo.getLatitude());
						picItem.setLongitude(locationInfo.getLontitude());
					}
					picItem.setAlreadyUpload(1);
					userItem.getPicItems().add(picItem);
				}
				picItem.setAddDate(simpleDateFormat.format(new Date()));
				picItem.setAddUser(userInfo.get("userName").toString());
				picItem.setAddUserId(userInfo.get("use_id").toString());
				picItem.setPicName(new File(itm.getImagePath()).getName());
				picItem.setPicPath(itm.getImagePath());
				picItem.setPicType(1);
				picItem.setUserNo(userNo);
				picItem.setNoteNo(userItem.getBbh());
			}
		}
		dbmanager.ChaoBiao(userItem);
		Toast.makeText(WCBRealActivity.this, "保存成功",Toast.LENGTH_SHORT).show();
		finish();
	}

}
