package com.ideal.zsyy.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jijiang.wtapp.R;
import com.ideal.zsyy.utils.FileUtil;

public class CardExplainActivity extends Activity {

	private int card_type;
	private Bitmap bitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cardexplain);

		card_type = getIntent().getIntExtra("card_type", -1);

		// Toast.makeText(getApplicationContext(), "card_type=" + card_type, 0)
		// .show();

		initView();
	}

	private void initView() {

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.widthPixels;
		int screenHeigh = dm.heightPixels;

		ImageView ce_iv_pic1 = (ImageView) findViewById(R.id.ce_iv_pic1);
		ImageView ce_iv_pic2 = (ImageView) findViewById(R.id.ce_iv_pic2);
		ImageView ce_iv_pic3 = (ImageView) findViewById(R.id.ce_iv_pic3);
		ImageView ce_iv_pic4 = (ImageView) findViewById(R.id.ce_iv_pic4);
		ImageView ce_iv_pic5 = (ImageView) findViewById(R.id.ce_iv_pic5);
		ImageView ce_iv_pic6 = (ImageView) findViewById(R.id.ce_iv_pic6);

		bitmap = FileUtil.findBitmap(getAssets(), "card/card_yibao_1.jpg");
		ce_iv_pic1.setLayoutParams(setLayoutParams(bitmap, screenWidth,
				screenHeigh));
		ce_iv_pic1.setImageBitmap(bitmap);

		bitmap = FileUtil.findBitmap(getAssets(), "card/card_yibao_2.jpg");
		ce_iv_pic2.setLayoutParams(setLayoutParams(bitmap, screenWidth,
				screenHeigh));
		ce_iv_pic2.setImageBitmap(bitmap);

		bitmap = FileUtil.findBitmap(getAssets(), "card/card_shebao_1.jpg");
		ce_iv_pic3.setLayoutParams(setLayoutParams(bitmap, screenWidth,
				screenHeigh));
		ce_iv_pic3.setImageBitmap(bitmap);

		bitmap = FileUtil.findBitmap(getAssets(), "card/card_shebao_2.jpg");
		ce_iv_pic4.setLayoutParams(setLayoutParams(bitmap, screenWidth,
				screenHeigh));
		ce_iv_pic4.setImageBitmap(bitmap);

		bitmap = FileUtil.findBitmap(getAssets(), "card/card_jiuyi_1.jpg");
		ce_iv_pic5.setLayoutParams(setLayoutParams(bitmap, screenWidth,
				screenHeigh));
		ce_iv_pic5.setImageBitmap(bitmap);

		bitmap = FileUtil.findBitmap(getAssets(), "card/card_jiuyi_2.jpg");
		ce_iv_pic6.setLayoutParams(setLayoutParams(bitmap, screenWidth,
				screenHeigh));
		ce_iv_pic6.setImageBitmap(bitmap);

		Button btn_back = (Button) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});

	}

	private LinearLayout.LayoutParams setLayoutParams(Bitmap bitmap,
			int screenWidth, int screenHeigh) {

		int bt_width = bitmap.getWidth();
		int bt_height = bitmap.getHeight();
		screenHeigh = (screenWidth * bt_height) / bt_width;

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				screenWidth, screenHeigh);
		params.setMargins(0, 10, 0, 10);

		return params;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub

		if (bitmap != null && !bitmap.isRecycled()) {

			bitmap.recycle();
			bitmap = null;
		}

		super.onDestroy();
	}

}
