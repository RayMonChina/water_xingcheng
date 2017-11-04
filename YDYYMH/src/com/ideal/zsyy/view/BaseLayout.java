package com.ideal.zsyy.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class BaseLayout extends LinearLayout {

	private Interaction i;

	protected LinearLayout layout;

	private int a = 1;

	public void setInteraction(Interaction i) {
		this.i = i;
	}

	public BaseLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public BaseLayout(Context context, int layoutId) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layout = (LinearLayout) inflater.inflate(layoutId, null);
		// layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
		// LayoutParams.FILL_PARENT));
		this.addView(layout, new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		
//		View btn = findViewById(R.id.btn_home);
//		if (btn != null) {
//			btn.setBackgroundResource(R.drawable.selector_back_left);
//
//		}
//		btn.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				openMenu();
//			}
//		});
		
	}

	protected View BfindViewById(int id) {
		return layout.findViewById(id);
	}

	public boolean onBackPressed() {
		return false;
	}

	public interface Interaction {
		public Object onInteraction(int OperId, Object msg);
	}

	public void openMenu() {
		if (i != null) {
			i.onInteraction(0, null);
		}
	}

	public void onFirstLoad() {

	}

	public final void onLoad() {
		if (a > 0) {
			onFirstLoad();
			a--;
		}
		onViewLoad();
	}

	public void onViewLoad() {

	}

	public void foucs() {

	}

	public void onDestroy() {
		// if(layout!=null){
		// layout.setBackgroundResource(0);
		// }
	}

	public void onViewMove() {

	}

	public Interaction getInteraction() {
		return i;
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
	}

}
