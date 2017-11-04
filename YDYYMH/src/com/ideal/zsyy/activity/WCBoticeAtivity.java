package com.ideal.zsyy.activity;

import com.jijiang.wtapp.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class WCBoticeAtivity extends Activity {

	WebView wView = null;
	Button btn_back=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.w_cb_notice);
		this.initView();
	}

	private void initView() {
		wView = (WebView) findViewById(R.id.wv_notice);
		btn_back=(Button)findViewById(R.id.btn_back);
		if(btn_back!=null)
		{
			btn_back.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
				}
			});
		}
		if (wView != null) {
			wView.getSettings().setJavaScriptEnabled(true);
			//wView.loadUrl("http://www.baidu.com");
			wView.setWebViewClient(new WebViewClient() {
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					// TODO Auto-generated method stub
					// 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
					view.loadUrl(url);
					return true;
				}
			});
		}
	}
}
