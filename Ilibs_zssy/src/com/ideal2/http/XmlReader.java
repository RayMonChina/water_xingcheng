package com.ideal2.http;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;

import org.xmlpull.v1.XmlSerializer;

import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;

public class XmlReader {

	private static final String TAG = "XmlReader";
	private XmlNode root;

	public void setForeachNodeListener(OnForeachNodeListener listener) {
		this.listener = listener;
	}

	private OnForeachNodeListener listener;

	public XmlReader(XmlNode arg0) {
		if (arg0 != null) {
			root = arg0;
		}
	}

	public XmlReader() {

	}

	/**
	 * 
	 * @param xPath
	 *            书写格式类似于root/elements[2]/elements[3]
	 * @return 返回搜索到的节点
	 */
	public XmlNode getNode(String xPath) {
		return getNode(xPath, root);
	}

	/**
	 * 搜索节点
	 * 
	 * @param xPath
	 *            书写格式类似于root/elements[2]/elements[3]
	 * @param root
	 *            搜索的根节点。
	 * @return 返回搜索到的节点
	 */
	public XmlNode getNode(String xPath, XmlNode root) {
		if (TextUtils.isEmpty(xPath)) {
			return null;
		}
		String[] elements = xPath.split("/");
		Log.d("xPath", "xPath: " + elements.length);
		Log.d("xPath", "Root: " + root.getElementName());
		if (!root.getElementName().equals(elements[0])) {
			return null;
		}
		XmlNode pointer = root;
		for (int i = 1; i < elements.length; i++) {
			if (pointer == null) {
				return null;
			}
			pointer = findNode(elements[i], pointer);
		}
		return pointer;
	}

	private XmlNode findNode(String element, XmlNode node) {
		if (node.hasChild()) {
			int index = 0;
			if (element.endsWith("]")) {
				index = Integer
						.parseInt(element.substring(
								element.lastIndexOf("[") + 1,
								element.lastIndexOf("]")));
				element = element.substring(0, element.indexOf("["));
			}
			for (XmlNode iNode : node.getChildNodes()) {
				if (element.equals(iNode.getElementName())) {
					index--;
					Log.d("xPath", "index: " + index);
					if (index < 0) {
						return iNode;
					}
				}

			}
		}
		return null;
	}

	public void foreachNode() {
		if (root != null) {
			foreachNode(root);
		}
	}

	public void foreachNode(XmlNode xNode) {
		if (xNode.isNotForeach()) {
			return;
		} else {
			if (listener != null) {
				listener.onNodeCall(xNode.getElementName(),xNode.getElementName(),
						xNode.getElementValue(),
						OnForeachNodeListener.TYPE_ELEMENT);
			}
			Log.d(TAG, "Element: " + xNode.getElementName() + " Value: "
					+ xNode.getElementValue());
			HashMap<String, String> attrMap = xNode.getMap();
			for (String i : attrMap.keySet()) {
				if (listener != null) {
					listener.onNodeCall(xNode.getElementName(),i, attrMap.get(i),
							OnForeachNodeListener.TYPE_ATTR);
				}
				Log.d(TAG, "Attr：" + i + " : " + attrMap.get(i));
			}
			if (xNode.hasChild()) {
				for (XmlNode node : xNode.getChildNodes()) {
					foreachNode(node);
				}
			}
		}
	}

	public interface OnForeachNodeListener {
		public int TYPE_ELEMENT = 0;
		public int TYPE_ATTR = 1;

		public void onNodeCall(String elementName,String Key, String val, int type);
	}

	public String toXml() {
		if (root != null)
			return toXml(root);
		return null;
	}

	public String toXml(XmlNode xNode) {
		StringWriter xmlWriter = new StringWriter();

		XmlSerializer serializer = Xml.newSerializer();
		try {
			serializer.setOutput(xmlWriter);
			
			serializer.startDocument("utf-8", false);
			readNode(xNode, serializer);
			serializer.endDocument();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String str = xmlWriter.toString();
		int index = str.indexOf("?>");
		str = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+str.substring(index+2);
		return str;

	}

	private void readNode(XmlNode xNode, XmlSerializer serializer)
			throws IllegalArgumentException, IllegalStateException, IOException {
		// TODO Auto-generated method stub
		if (xNode.isNotForeach()) {
			return;
		} else {
			serializer.startTag("", xNode.getElementName());
			HashMap<String, String> attrMap = xNode.getMap();
			if (!attrMap.isEmpty()) {
				for (String i : attrMap.keySet()) {
					
					serializer.attribute("", i, attrMap.get(i)==null?"":attrMap.get(i));
				}
			}
			if (xNode.hasChild()) {
				for (XmlNode node : xNode.getChildNodes()) {
					readNode(node, serializer);
				}
			} else {
				serializer.text(xNode.getElementValue());
			}
			serializer.endTag("", xNode.getElementName());
		}
	}
}
