package com.ideal2.base;

import android.content.Context;

import com.ideal2.http.XmlNode;
import com.ideal2.http.XmlReader;

/**
 * 
 * @author xufeng
 * @version 1.0 公司数据传输基类
 * 
 */
public class IdealBaseDao extends BaseDao implements
		BaseDao.OnResponseEndListening {
	public final static int REQUESTTYPE_NO = 0;
	public final static int REQUESTTYPE_INSERT = 1;
	public final static int REQUESTTYPE_DELETE = 2;
	public final static int REQUESTTYPE_UPDATE = 3;
	public final static int REQUESTTYPE_SELECT = 4;

	private IDomainObject domainObject;
	private XmlReader mReader;
	private OnResponseEndListening onResponseEndListening;
	private OnRequestErrListening onRequestErrListening;
	private int requestType;

	public IdealBaseDao(Context context) {
		super(context, ConfigBase.create(context).getIdealText(
				ConfigBase.IDEAL_URL), ConfigBase.create(context).getIdealText(
				ConfigBase.IDEAL_XMLNAME));
		super.setOnResponseEndListening(this);
		mReader = new XmlReader();
	}

	public boolean request(IDomainObject domainObject) {
		if (null == domainObject) {
			return false;
		} else {
			this.domainObject = domainObject;
		}
		if (null == this.domainObject.requestXml()) {
			return false;
		} else {
			return request(domainObject,
					mReader.toXml(domainObject.requestXml()));
		}

	}

	public boolean request(IDomainObject domainObject, XmlNode xNode) {
		if (null == xNode) {
			return false;
		}
		return request(domainObject, mReader.toXml(xNode));
	}

	public boolean request(IDomainObject domainObject, String xmlStr) {

		if (null == xmlStr) {
			return false;
		}
		return super.conn(xmlStr);
	}

	@Override
	public void onResponseEnd(XmlNode xNode, boolean result, int responseCode,
			String requestXml) {
		if (result) {
			BuildDomainObject BuildDomainObject = new BuildDomainObject(
					domainObject);
			BuildDomainObject.foreachNode(xNode, null);

			if (null != onResponseEndListening) {
				onResponseEndListening.onResponseEnd(domainObject, xNode,
						responseCode);
			}
		} else {
			if (null != onRequestErrListening) {
				onRequestErrListening.onRequestErr(domainObject, xNode,
						responseCode);
			}
		}

	}

	public interface OnResponseEndListening {
		public void onResponseEnd(IDomainObject domainObject, XmlNode xNode,
				int responseCode);
	}

	public interface OnRequestErrListening {
		public void onRequestErr(IDomainObject domainObject, XmlNode xNode,
				int responseCode);
	}

	public void setOnResponseEndListening(
			OnResponseEndListening onResponseEndListening) {
		this.onResponseEndListening = onResponseEndListening;
	}

	public void setOnRequestErrListening(
			OnRequestErrListening onRequestErrListening) {
		this.onRequestErrListening = onRequestErrListening;
	}

	public int getRequestType() {
		return requestType;
	}

	public void setRequestType(int requestType) {
		this.requestType = requestType;
	}

}
