package com.ideal2.services;

import android.content.Context;
import android.widget.Toast;

import com.ideal2.base.ClientBaseDao;
import com.ideal2.base.IDomainObject;
import com.ideal2.components.MyToast;
import com.ideal2.demo.R;
import com.ideal2.http.XmlNode;
import com.ideal2.log.ILog;


public class InsertService<DO extends IDomainObject> {
	private static final String TAG = "InsertService";
	private Context context;
	private DO domainObject;
	private InsertResponseListener<DO> insertResponseListener;
	private ClientBaseDao<DO> cbd;
	
	public ClientBaseDao<DO> getCbd() {
		return cbd;
	}

	public void setCbd(ClientBaseDao<DO> cbd) {
		this.cbd = cbd;
	}

	public Context getContext() {
		return context;
	}

	public DO getDomainObject() {
		return domainObject;
	}

	public void setDomainObject(DO domainObject) {
		this.domainObject = domainObject;
	}

	public InsertService(Context context) {
		this.context = context;
		cbd = new ClientBaseDao<DO>(this.context);
		
	}

	public InsertResponseListener<DO> getInsertResponseListener() {
		return insertResponseListener;
	}

	public void setInsertResponseListener(
			InsertResponseListener<DO> insertResponseListener) {
		this.insertResponseListener = insertResponseListener;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public void doRequest(DO domainObject){
		this.domainObject = domainObject;
		cbd.request(domainObject);
		cbd.setOnResponseEndListening(new ClientBaseDao.OnResponseEndListening<DO>() {

			@Override
			public void onResponseEnd(DO domainObject,
					boolean result, String errmsg, XmlNode xNode,
					int responseCode) {
				/*
				if(null!=insertResponseListener){
					if(responseCode==200){
						if(insertResponseListener.insert_ok(domainObject, xNode)){
//							return;
						};
					}else{
						if(insertResponseListener.insert_err(errmsg)){
//							return;
						}
					}
				}*/
				if(null!=insertResponseListener){
					try {
						if(result){
								if(insertResponseListener.insert_ok(domainObject, xNode)){
									return;
								};
						}else{
								if(insertResponseListener.insert_err(errmsg)){
									return;
								}
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				if (result) {
					ILog.d(TAG, "新增成功");
					MyToast.makeText(context, context.getString(R.string.insert_ok), Toast.LENGTH_SHORT).show();
				} else {
					ILog.d(TAG, "新增失败");
					if ("".equals(errmsg.trim())) {
						MyToast.makeText(context, context.getString(R.string.insert_err), Toast.LENGTH_SHORT).show();
					} else {
						MyToast.makeText(context, errmsg.trim(),
								Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
	}
	
	public void setUrl(String url){
		cbd.setUrl(url);
	}
	
	public interface InsertResponseListener<DO extends IDomainObject>{
		public boolean insert_ok(DO domainObject, XmlNode xNode);
		public boolean insert_err(String errmsg);
	}
}
