package com.ideal.zsyy.view;

import com.jijiang.wtapp.R;
import com.ideal.zsyy.activity.AdviceListActivity;
import com.ideal.zsyy.activity.FalultListActivity;
import com.ideal.zsyy.activity.LoginActivity;
import com.ideal.zsyy.activity.WAnalysisActivity;
import com.ideal.zsyy.activity.WCBoticeAtivity;
import com.ideal.zsyy.activity.WCustomerSearchActivity;
import com.ideal.zsyy.activity.WDownloadDataActivity;
import com.ideal.zsyy.activity.WSystemSettingActivity;
import com.ideal.zsyy.activity.WUploadDataActivity;
import com.ideal.zsyy.activity.WalkPathActivity;
import com.ideal.zsyy.db.WdbManager;
import com.ideal.zsyy.entity.ActionItem;
import com.ideal.zsyy.view.TitlePopup.OnItemOnClickListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainPageView extends BaseLayout {

	private View vanalysis = null, vfalult = null, vcustomAdvice = null,
			vdowndata = null, vuploadData = null, vwalkPathView = null,
			vsysSetting = null,ll_notice;
	private LinearLayout img_top_menu;
	private Context vContext;
	private TitlePopup popup;
	private WdbManager dbManager;
	public MainPageView(Context context) {
		super(context, R.layout.main);
		this.vContext = context;
		popup = new TitlePopup(vContext);
		dbManager=new WdbManager(this.vContext);
		this.initView();
		this.setEventListener();
		this.initData();
	}

	private void initData() {
		popup.addAction(new ActionItem(getResources().getDrawable(
				R.drawable.wcb_switch_24), "退出登录", 1));
	}

	private void initView() {
		vanalysis = BfindViewById(R.id.ll_yscx);
		vfalult = BfindViewById(R.id.ll_repair);
		vcustomAdvice = BfindViewById(R.id.ll_advice);
		vdowndata = BfindViewById(R.id.ll_download);
		vuploadData = BfindViewById(R.id.ll_upload);
		vwalkPathView = BfindViewById(R.id.ll_track);
		vsysSetting = BfindViewById(R.id.ll_systemset);
		img_top_menu = (LinearLayout) BfindViewById(R.id.img_top_menu);
		ll_notice=BfindViewById(R.id.ll_notice);
	}

	private void setEventListener() {
		if (vanalysis != null) {
			vanalysis.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(vContext,
							WAnalysisActivity.class);
					vContext.startActivity(intent);
				}
			});
		}

		if (vfalult != null) {
			vfalult.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// Intent intent = new Intent(vContext,
					// FalultListActivity.class);
					// intent.putExtra("pageS", 2);
					// vContext.startActivity(intent);
				}
			});
		}

		if (vcustomAdvice != null) {
			vcustomAdvice.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(vContext,AdviceListActivity.class);
					intent.putExtra("pageS", 3);
					vContext.startActivity(intent);
				}
			});
		}

		if (vdowndata != null) {
			vdowndata.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(vContext,
							WDownloadDataActivity.class);
					vContext.startActivity(intent);
				}
			});
		}

		if (vuploadData != null) {
			vuploadData.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(vContext,
							WUploadDataActivity.class);
					vContext.startActivity(intent);
				}
			});
		}

		if (vwalkPathView != null) {
			vwalkPathView.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(vContext, WalkPathActivity.class);
					vContext.startActivity(intent);
				}
			});
		}

		if (vsysSetting != null) {
			vsysSetting.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(vContext,
							WSystemSettingActivity.class);
					vContext.startActivity(intent);
				}
			});
		}

		if (img_top_menu != null) {
			img_top_menu.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					popup.show(v);
				}
			});
		}
		if (popup != null) {
			popup.setItemOnClickListener(new OnItemOnClickListener() {

				@Override
				public void onItemClick(ActionItem item, int position) {
					switch (item.operateId) {
					case 1:
						LogOut();
						break;
					}
				}
			});
		}
		if(ll_notice!=null)
		{
			ll_notice.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent=new Intent(vContext,WCBoticeAtivity.class);
					vContext.startActivity(intent);
				}
			});
		}
	}
	
	private void LogOut()
	{
		if(dbManager.CheckUnUpload())
		{
			Toast.makeText(this.vContext,"有未上传的数据，请先上传数据！",Toast.LENGTH_SHORT).show();
			Intent intent=new Intent(this.vContext,WUploadDataActivity.class);
			this.vContext.startActivity(intent);
			return;
		}
		Intent nextIntent = new Intent(this.vContext,
				LoginActivity.class);
		this.vContext.startActivity(nextIntent);
		Activity activity=(Activity)this.vContext;
		if(activity!=null)
		{
			activity.finish();
		}
	}
}
