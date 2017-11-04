package com.ideal2.services;

import android.content.Context;
import android.widget.Toast;

import com.ideal2.base.ClientBaseDao;
import com.ideal2.base.IDomainObject;
import com.ideal2.components.MyToast;
import com.ideal2.demo.R;
import com.ideal2.http.XmlNode;
import com.ideal2.log.ILog;

public class SelService<DO extends IDomainObject> {
	private static final String TAG = "SelService";
	private Context context;
	private DO domainObject;
	private SelResponseListener<DO> selResponseListener;
	private ClientBaseDao<DO> cbd;
	

	public Context getContext() {
		return context;
	}

	public DO getDomainObject() {
		return domainObject;
	}

	public void setDomainObject(DO domainObject) {
		this.domainObject = domainObject;
	}

	public SelService(Context context) {
		this.context = context;
		cbd = new ClientBaseDao<DO>(this.context);

	}
	public SelService(Context context,boolean hasErrMsg ) {
		this.context = context;
		cbd = new ClientBaseDao<DO>(this.context);
		cbd.setHasErrMsg(false);
	}
	

	public void doRequest(DO domainObject) {
		this.domainObject = domainObject;
		cbd.request(domainObject);
		cbd.setOnResponseEndListening(new ClientBaseDao.OnResponseEndListening<DO>() {

			@Override
			public void onResponseEnd(DO domainObject, boolean result,
					String errmsg, XmlNode xNode, int responseCode) {
				if (null != selResponseListener) {
					try {
						if (result) {
							if (selResponseListener.sel_ok(domainObject, xNode)) {
								return;
							}
						} else {
							if (selResponseListener.sel_err(errmsg)) {
								return;
							}
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (result) {
					ILog.d(TAG, "查询成功");
				} else {
					ILog.d(TAG, "查询失败");
					try {
						if ("".equals(errmsg.trim())) {
							MyToast.makeText(context,
									context.getString(R.string.sel_err),
									Toast.LENGTH_SHORT).show();
						} else {
							MyToast.makeText(context, errmsg.trim(),
									Toast.LENGTH_SHORT).show();
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		});
	}
	
	public void setUrl(String url){
		cbd.setUrl(url);
	}

	public void setSelResponseListener(
			SelResponseListener<DO> selResponseListener) {
		this.selResponseListener = selResponseListener;
	}

	public interface SelResponseListener<DO extends IDomainObject> {
		public boolean sel_ok(DO domainObject, XmlNode xNode);

		public boolean sel_err(String errmsg);
	}
}
