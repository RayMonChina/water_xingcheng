package com.ideal2.base.gson;

import java.io.InputStreamReader;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.ideal.wdm.tools.DataCache;
import com.ideal.wdm.tools.PreferencesService;
import com.ideal2.base.BaseDao;
import com.ideal2.base.BuildDomainObject;
import com.ideal2.base.ClientBaseDao;
import com.ideal2.base.ConfigBase;
import com.ideal2.base.IDomainObject;
import com.ideal2.base.MessageFacotry;
import com.ideal2.base.ClientBaseDao.OnResponseEndListening;
import com.ideal2.components.ScreenParameter;
import com.ideal2.demo.R;
import com.ideal2.http.XmlNode;
import com.ideal2.http.XmlReader;
import com.ideal2.log.ILog;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class GsonServlet<REQ extends CommonReq, RES extends CommonRes> extends
		GsonBaseDao implements GsonBaseDao.ConnStateListening,
		GsonBaseDao.OnResponseEndListening {

	private static Dialog dialog;
	private volatile boolean isRequest = true;
	private int connstate;
	private OnResponseEndListening<REQ, RES> onResponseEndListening;

	private GsonBuilder gsonBuilder;
	private Gson gson;
	private Context context;
	private REQ req;
	private RES res;
	private Class resClass;
	private PreferencesService preferencesService;
	private boolean isToastErrMsg = false;

	private void showDialog(Dialog d) {
		if (dialog!=null && dialog.isShowing()) {
			dialog.dismiss();
		}
		dialog = d;
		
		if(dialog!= null){
			dialog.show();
		}
		
	}

	public GsonServlet(Context context) {
		super();
		this.context = context;
		DataCache dataCache=DataCache.getCache(context);
		super.setUrl(dataCache.getUrl() + "");

		gsonBuilder = new GsonBuilder();
		gson = new Gson();

		ScreenParameter screenParameter = ScreenParameter.getInstance(context);
		int width = screenParameter.getWidth();
		int height = screenParameter.getHeight();

		super.setConnStateListening(this);
		super.setOnResponseEndListening(new GsonBaseDao.OnResponseEndListening() {

			@Override
			public void onResponseEnd(InputStreamReader isr, boolean result,
					int responseCode, String requestGson) {
				if (null != isr) {
					res = (RES) gson.fromJson(isr, resClass);
					ILog.d(TAG, "锟斤拷应锟斤拷" + gson.toJson(res));
				}
				Message msg = new Message();
				Bundle data = new Bundle();
				data.putInt("responseCode", responseCode);
				data.putBoolean("result", result);
				msg.setData(data);
				responseHandler.sendMessage(msg);
			}
		});

	}

	public void request(REQ req, Class resClass) {
		this.resClass = resClass;
		this.req = req;
		if (!this.isRequest) {
			return;
		}
		if (null == this.req) {
			return;
		}
		super.getmParams().add(
				new BasicNameValuePair("type", req.getOperType()));
		String gsonStr = gson.toJson(req, req.getClass());

		doService(gsonStr);
	}

	@Override
	public boolean connState(int connstate) {
		this.connstate = connstate;
		switch (connstate) {
		case CONNSTATE_CREATEREQUEST:
			Log.d("showDialogHandler", "CONNSTATE_CREATEREQUEST");
			this.isRequest = false;

			showDialogHandler.sendEmptyMessage(0);

			break;
		case CONNSTATE_RESPONSEING:
			// if (!dialog.isShowing()) {
			// dialog.show();
			// }
			break;
		case CONNSTATE_RESPONSEEND:
			Log.d("showDialogHandler", "CONNSTATE_RESPONSEEND");
			this.isRequest = true;
			showDialogHandler.sendEmptyMessage(1);

			break;
		}
		return true;
	}

	public boolean isShowDialog = true;

	public boolean isShowDialog() {
		return isShowDialog;
	}

	public void setShowDialog(boolean isShowDialog) {
		this.isShowDialog = isShowDialog;
	}

	private Handler showDialogHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:

				if (isShowDialog) {
					Log.d("showDialogHandler", "showDialogHandler");
					Dialog dialog = new Dialog(context, R.style.pro2Dialog);
					dialog.setContentView(R.layout.progressbar_layout);
					//璁剧疆杩涘害鏉″璇濇鍦ㄥ姞杞芥椂鍏朵粬鍦版柟閮戒笉鑳界偣,鍙湁杩斿洖閿墠鏈夌敤
					dialog.setCanceledOnTouchOutside(false);
					dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
						// 锟斤拷dialog锟斤拷锟绞憋拷乇盏锟角帮拷锟斤拷锟�锟斤拷锟斤拷isRequest 为true
						@Override
						public void onDismiss(DialogInterface dialog) {
							GsonServlet.this.destroy();
							isRequest = true;
						}
					});
					showDialog(dialog);
				}

				break;
			case 1:
				if (dialog!=null&&dialog.isShowing()) {
					dialog.dismiss();
				}
				break;
			default:
				break;
			}

		}
	};

	@Override
	public void onResponseEnd(InputStreamReader isr, boolean result,
			int responseCode, String requestGson) {
		// this.res = (RES) gson.fromJson(isr, CommonRes.class);
		// ILog.d(TAG, "锟斤拷应锟斤拷"+gson.toJson(this.res));
		// Message msg = new Message();
		// Bundle data = new Bundle();
		// data.putInt("responseCode", responseCode);
		// data.putBoolean("result", result);
		// msg.setData(data);
		// responseHandler.sendMessage(msg);

	}

	private Handler responseHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Bundle data = msg.getData();
			int responseCode = data.getInt("responseCode");
			boolean result = data.getBoolean("result");
			if(!(context instanceof Application)&&((Activity)context).isFinishing()){
				Log.d(TAG, "Activity has Destoryed. Stop callback!");
				return;
			}
			if (result) {
				if (res == null) {
					responseCode = 801;
					if (!isToastErrMsg) {
						if (null != onResponseEndListening) {
							onResponseEndListening.onResponseEnd(req, null,
									false, MessageFacotry
											.responseErrMessage(responseCode),
									responseCode);
							onResponseEndListening.onResponseEndErr(req, null,
									MessageFacotry
											.responseErrMessage(responseCode),
									responseCode);
						}
					} else {
						MessageFacotry.responseErrMessage(getContext(),
								responseCode);
					}
				} else {
					if (null != onResponseEndListening) {
						if (res.isErrMsg()) {
							onResponseEndListening.onResponseEnd(req, res,
									false, res.getErrMsg(), responseCode);
							onResponseEndListening.onResponseEndErr(req, res,
									res.getErrMsg(), responseCode);
						} else {
							onResponseEndListening.onResponseEnd(req, res,
									true, res.getErrMsg(), responseCode);
							onResponseEndListening.onResponseEndSuccess(req,
									res, res.getErrMsg(), responseCode);
						}
					}
				}
			} else {
				if (!isToastErrMsg) {
					if (null != onResponseEndListening) {
						onResponseEndListening
								.onResponseEnd(req, null, false, MessageFacotry
										.responseErrMessage(responseCode),
										responseCode);
						onResponseEndListening
								.onResponseEndErr(req, null, MessageFacotry
										.responseErrMessage(responseCode),
										responseCode);
					}
				} else {
					MessageFacotry.responseErrMessage(getContext(),
							responseCode);
				}
			}
		}
	};

	public interface OnResponseEndListening<REQ extends CommonReq, RES extends CommonRes> {
		public void onResponseEnd(REQ commonReq, RES commonRes, boolean result,
				String errmsg, int responseCode);

		public void onResponseEndSuccess(REQ commonReq, RES commonRes,
				String errmsg, int responseCode);

		public void onResponseEndErr(REQ commonReq, RES commonRes,
				String errmsg, int responseCode);
	}

	public Dialog getDialog() {
		return dialog;
	}

	public void setDialog(Dialog dialog) {
		this.dialog = dialog;
	}

	public boolean isRequest() {
		return isRequest;
	}

	public void setRequest(boolean isRequest) {
		this.isRequest = isRequest;
	}

	public int getConnstate() {
		return connstate;
	}

	public void setConnstate(int connstate) {
		this.connstate = connstate;
	}

	public OnResponseEndListening<REQ, RES> getOnResponseEndListening() {
		return onResponseEndListening;
	}

	public void setOnResponseEndListening(
			OnResponseEndListening<REQ, RES> onResponseEndListening) {
		this.onResponseEndListening = onResponseEndListening;
	}

	public GsonBuilder getGsonBuilder() {
		return gsonBuilder;
	}

	public void setGsonBuilder(GsonBuilder gsonBuilder) {
		this.gsonBuilder = gsonBuilder;
	}

	public Gson getGson() {
		return gson;
	}

	public void setGson(Gson gson) {
		this.gson = gson;
	}

	public Context getContext() {
		return this.context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public Handler getResponseHandler() {
		return responseHandler;
	}

	public void setResponseHandler(Handler responseHandler) {
		this.responseHandler = responseHandler;
	}

}
