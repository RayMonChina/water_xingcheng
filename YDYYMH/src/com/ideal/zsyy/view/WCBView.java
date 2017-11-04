package com.ideal.zsyy.view;

import java.util.List;

import com.ideal.wdm.tools.DataCache;
import com.ideal.zsyy.activity.LoginActivity;
import com.ideal.zsyy.activity.WCBMainActivity;
import com.ideal.zsyy.activity.WCustomerSearchActivity;
import com.ideal.zsyy.db.WdbManager;
import com.ideal.zsyy.entity.WBBItem;
import com.jijiang.wtapp.R;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class WCBView extends BaseLayout{

	private Context _context;
	private Dialog dialog;
	List<WBBItem>ltAp=null;
	ArrayAdapter<WBBItem> adapter=null;
	WdbManager dbHelper;
	Spinner spinnerBBH;
	public WCBView(Context context) {
		super(context,R.layout.w_cb);
		// TODO Auto-generated constructor stub
		this._context=context;
		dbHelper=new WdbManager(_context);
		this.initView();
	}

	private void initView()
	{
		TextView tView=	(TextView)this.findViewById(R.id.tv_search);
		TextView tViewOrder=(TextView)this.findViewById(R.id.tvv_cborder);
		if(tView!=null)
		{
			tView.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = null;
					intent = new Intent(_context,
							WCustomerSearchActivity.class);
					intent.putExtra("pageS", 1);
					_context.startActivity(intent);
				}
			});
		}
		
		if(tViewOrder!=null)
		{
			dialog = new Dialog(_context, R.style.dialog);
			dialog.setContentView(R.layout.w_bbselect__dialog);
			Button btn_ok = (Button) dialog.findViewById(R.id.ok);
			Button btn_cancel=(Button)dialog.findViewById(R.id.cancel);
			spinnerBBH=(Spinner)dialog.findViewById(R.id.sp_bbh);
			btn_ok.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String bbh=spinnerBBH.getSelectedItem().toString();
					Intent intent=new Intent(_context,WCBMainActivity.class);
					intent.putExtra("bbh",bbh);
					_context.startActivity(intent);
					if(dialog!=null)
					{
						dialog.dismiss();
					}
					
				}
			});
			btn_cancel.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(dialog!=null)
					{
						dialog.dismiss();
					}
				}
			});
			ltAp=dbHelper.GetBiaoBenInfo();
			adapter = new ArrayAdapter<WBBItem>(_context,
					android.R.layout.simple_spinner_item, ltAp);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinnerBBH.setAdapter(adapter);
			
			tViewOrder.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(dialog!=null)
					{
						dialog.show();
					}
				}
			});
		}
	}
}
