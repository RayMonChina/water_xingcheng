package com.ideal2.services;

import android.content.Context;
import android.widget.Toast;

import com.ideal2.base.ClientBaseDao;
import com.ideal2.base.IDomainObject;
import com.ideal2.components.MyToast;
import com.ideal2.demo.R;
import com.ideal2.http.XmlNode;

public class UpdateService <DO extends IDomainObject> {
	private static final String TAG = "InsertService";
	private Context context;
	private DO domainObject;
	private UpdateResponseListener<DO> updateResponseListener;
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

	public UpdateService(Context context) {
		this.context = context;
		cbd = new ClientBaseDao<DO>(this.context);
		
	}

	public void doRequest(DO domainObject){
		this.domainObject = domainObject;
		cbd.request(domainObject);
		cbd.setOnResponseEndListening(new ClientBaseDao.OnResponseEndListening<DO>() {

			@Override
			public void onResponseEnd(DO domainObject,
					boolean result, String errmsg, XmlNode xNode,
					int responseCode) {
				if(null!=updateResponseListener){
					try {
						if(result){
							if(updateResponseListener.update_ok(domainObject, xNode)){
								return;
							};
						}else{
							if(updateResponseListener.update_err(errmsg)){
								return;
							}
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(result){
					MyToast.makeText(context, context.getString(R.string.update_ok), Toast.LENGTH_SHORT).show();
				}else{
					MyToast.makeText(context, context.getString(R.string.update_err), Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	public void setUrl(String url){
		cbd.setUrl(url);
	}
	
	public UpdateResponseListener<DO> getUpdateResponseListener() {
		return updateResponseListener;
	}

	public void setUpdateResponseListener(
			UpdateResponseListener<DO> updateResponseListener) {
		this.updateResponseListener = updateResponseListener;
	}

	public interface UpdateResponseListener <DO extends IDomainObject>{
		public boolean update_ok(DO domainObject, XmlNode xNode);
		public boolean update_err(String errmsg);
	}
}
