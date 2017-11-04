package com.ideal2.base;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;

import com.ideal2.components.ScreenParameter;
import com.ideal2.demo.R;
import com.ideal2.http.XmlNode;
import com.ideal2.http.XmlReader;
import com.ideal2.log.ILog;
import com.ideal2.util.DensityUtil;

/**
 * 
 * @author xufeng
 * @version 1.0 客户数据传输基类
 * 
 */
public class ClientBaseDao<DO extends IDomainObject> extends BaseDao implements
		BaseDao.OnResponseEndListening, BaseDao.ConnStateListening {
	public final static int REQUESTTYPE_NO = 0;
	public final static int REQUESTTYPE_INSERT = 1;
	public final static int REQUESTTYPE_DELETE = 2;
	public final static int REQUESTTYPE_UPDATE = 3;
	public final static int REQUESTTYPE_SELECT = 4;

	private DO domainObject;
	private XmlReader mReader;
	private OnResponseEndListening<DO> onResponseEndListening;
	private int requestType;
	private boolean isRequest = true;
	private Dialog dialog;
	private int connstate;
	private boolean isShowDialog = true;
	private boolean hasErrMsg = true;

	public ClientBaseDao(Context context) {
		super(context, ConfigBase.create(context).getClientText(
				ConfigBase.CLIENT_URL), ConfigBase.create(context)
				.getClientText(ConfigBase.CLIENT_XMLNAME));
		super.setOnResponseEndListening(this);
		super.setConnStateListening(this);
		mReader = new XmlReader();

		ScreenParameter screenParameter = ScreenParameter.getInstance(context);
		int width = screenParameter.getWidth();
		int height = screenParameter.getHeight();

		dialog = new Dialog(context, R.style.pro2Dialog);
		dialog.setContentView(R.layout.progressbar_layout);
//		dialog.setContentView(R.layout.my_progress_dialog);
//		WindowManager.LayoutParams windowParams2 = dialog.getWindow()
//				.getAttributes();
////		windowParams2.width =  280;
////		windowParams2.height = 100;
//		windowParams2.x = 0;
//		windowParams2.y = height / 3;
//		dialog.getWindow().setAttributes(windowParams2);
		
		dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
			//当dialog销毁时关闭当前请求   设置isRequest 为true
			@Override
			public void onDismiss(DialogInterface dialog) {
				ClientBaseDao.this.destroy();
				isRequest = true;
			}
		});
	}

	public boolean request(DO domainObject) {
		this.domainObject = domainObject;
		if (null == this.domainObject.requestXml()) {
			return false;
		} else {
			return request(this.domainObject,
					mReader.toXml(this.domainObject.requestXml()));
		}
		
	}

	public boolean request(DO domainObject, XmlNode xNode) {
		if (null == xNode) {
			return false;
		}
		return request(domainObject, mReader.toXml(xNode));
	}

	public boolean request(DO domainObject, String xmlStr) {
		if (!this.isRequest) {
			return false;
		}
		if (null == domainObject) {
			return false;
		} else {
			this.domainObject = domainObject;
		}
		if (null == xmlStr) {
			return false;
		}
		return super.conn(xmlStr);
	}

	@Override
	public boolean connState(int connstate) {
		this.connstate = connstate;
		switch (connstate) {
		case CONNSTATE_CREATEREQUEST:
			this.isRequest = false;
			break;
		case CONNSTATE_RESPONSEING:
			if(isShowDialog()){
				dialog.show();
				
			}
			break;
		case CONNSTATE_RESPONSEEND:
			if(isShowDialog()){
				dialog.dismiss();
			}
			this.isRequest = true;
			break;
		}
		return true;
	}

	public int getConnstate() {
		return connstate;
	}

	private XmlNode xNode;

	@Override
	public void onResponseEnd(XmlNode xNode, boolean result, int responseCode,
			String requestXml) {
		this.xNode = xNode;
		Message msg = new Message();
		Bundle data = new Bundle();
		data.putBoolean("result", result);
		data.putInt("responseCode", responseCode);
		msg.setData(data);
		responseHandler.sendMessage(msg);
	}

	private Handler responseHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Bundle data = msg.getData();
			boolean result = data.getBoolean("result");
			int responseCode = data.getInt("responseCode");
			if (result) {
				BuildDomainObject<DO> buildDomainObject = new BuildDomainObject<DO>(
						domainObject);
				ConfigBase configBase = ConfigBase.create(getContext());
				buildDomainObject.setErrMsgNodeName(configBase.getClientText(ConfigBase.CLIENT_ERRORNODE));
				buildDomainObject.foreachNode(xNode, null);
				ILog.d(TAG, "执行buildDomainObject.getErrMsg():"+buildDomainObject.getErrMsg());
				if (null != onResponseEndListening) {
					if(hasErrMsg){
						if (null != buildDomainObject.getErrMsg()
								&& "".equals(buildDomainObject.getErrMsg())) {
							onResponseEndListening.onResponseEnd(domainObject,
									true, buildDomainObject.getErrMsg(), xNode,
									responseCode);
						} else {
							onResponseEndListening.onResponseEnd(domainObject,
									false, buildDomainObject.getErrMsg(), xNode,
									responseCode);
						}
					}else{
						onResponseEndListening.onResponseEnd(domainObject,
								true, "", xNode,
								responseCode);
					}
				}
			} else {
				 MessageFacotry.responseErrMessage(getContext(),
				 responseCode);
//				responseErrHandler.sendEmptyMessage(responseCode);
			}
		}
	};


	public interface OnResponseEndListening<DO extends IDomainObject> {
		public void onResponseEnd(DO domainObject, boolean result,
				String errmsg, XmlNode xNode, int responseCode);
	}

	
	
	public boolean isHasErrMsg() {
		return hasErrMsg;
	}

	public void setHasErrMsg(boolean hasErrMsg) {
		this.hasErrMsg = hasErrMsg;
	}

	public void setOnResponseEndListening(
			OnResponseEndListening<DO> onResponseEndListening) {
		this.onResponseEndListening = onResponseEndListening;
	}

	public int getRequestType() {
		return requestType;
	}

	public void setRequestType(int requestType) {
		this.requestType = requestType;
	}

	public boolean isShowDialog() {
		return isShowDialog;
	}

	public void setShowDialog(boolean isShowDialog) {
		this.isShowDialog = isShowDialog;
	}

}
