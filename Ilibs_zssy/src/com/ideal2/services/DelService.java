package com.ideal2.services;


import android.content.Context;
import android.widget.Toast;

import com.ideal2.base.ClientBaseDao;
import com.ideal2.base.IDomainObject;
import com.ideal2.components.MyToast;
import com.ideal2.demo.R;
import com.ideal2.http.XmlNode;

public class DelService <DO extends IDomainObject>{
	private static final String TAG = "DelService";
	private Context context;
	private DO domainObject;
	private DelResponseListener<DO> delResponseListener;
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

	public DelService(Context context) {
		this.context = context;
		cbd = new ClientBaseDao<DO>(this.context);
		
	}

	public  void doRequest(DO domainObject){
		this.domainObject = domainObject;
		cbd.request(domainObject);
		cbd.setOnResponseEndListening(new ClientBaseDao.OnResponseEndListening<DO>() {

			@Override
			public void onResponseEnd(DO domainObject,
					boolean result, String errmsg, XmlNode xNode,
					int responseCode) {
				if(null!=delResponseListener){
					try {
						if(result){
							if(delResponseListener.del_ok(domainObject, xNode)){
								return;
							};
						}else{
							if(delResponseListener.del_err(errmsg)){
								return;
							}
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(result){
					MyToast.makeText(context, context.getString(R.string.del_ok), Toast.LENGTH_SHORT).show();
				}else{
					MyToast.makeText(context, context.getString(R.string.del_err), Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	public void setUrl(String url){
		cbd.setUrl(url);
	}
	
	public DelResponseListener<DO> getDelResponseListener() {
		return delResponseListener;
	}

	public void setDelResponseListener(DelResponseListener<DO> delResponseListener) {
		this.delResponseListener = delResponseListener;
	}


	public interface DelResponseListener<DO extends IDomainObject>{
		public boolean del_ok(DO domainObject, XmlNode xNode);
		public boolean del_err(String errmsg);
	}
	
}
