package com.ideal2.base;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.ideal2.components.MyToast;

public class MessageFacotry {

	private Context context;
	private Map<Integer,String> messageMap = null;
	
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}	
	
	public Map<Integer,String> getMessageMap(){
		if(null==messageMap){
			messageMap = new HashMap<Integer, String>();
			messageMap.put(400, "服务器不理解请求的语法。");//400   （错误请求） 服务器不理解请求的语法。
			messageMap.put(401, "请求要求身份验证。");//401   （未授权） 请求要求身份验证。 对于需要登录的网页，服务器可能返回此响应。
			messageMap.put(403, "服务器拒绝请求。");//403   （禁止） 服务器拒绝请求。
			messageMap.put(404, "找不到请求的服务器。");//404   （未找到） 服务器找不到请求的网页。
			messageMap.put(405, "禁用请求中指定的方法。");//405   （方法禁用） 禁用请求中指定的方法。
			messageMap.put(406, "无法使用请求的内容特性响应请求的网页。");//406   （不接受） 无法使用请求的内容特性响应请求的网页。
			messageMap.put(407, "需要代理授权。");//407   （需要代理授权） 此状态代码与 401（未授权）类似，但指定请求者应当授权使用代理。
			messageMap.put(408, "服务器等候请求时发生超时。");//408   （请求超时）  服务器等候请求时发生超时。
			messageMap.put(409, "服务器在完成请求时发生冲突。");//409   （冲突）  服务器在完成请求时发生冲突。 服务器必须在响应中包含有关冲突的信息。
			messageMap.put(410, "如果请求的资源已永久删除，服务器就会返回此响应。");//410   （已删除）  如果请求的资源已永久删除，服务器就会返回此响应。
			messageMap.put(411, "服务器不接受不含有效内容长度标头字段的请求。");//411   （需要有效长度） 服务器不接受不含有效内容长度标头字段的请求。
			messageMap.put(412, "服务器未满足请求者在请求中设置的其中一个前提条件。");//412   （未满足前提条件） 服务器未满足请求者在请求中设置的其中一个前提条件。
			messageMap.put(413, "服务器无法处理请求，因为请求实体过大，超出服务器的处理能力。");//413   （请求实体过大） 服务器无法处理请求，因为请求实体过大，超出服务器的处理能力。
			messageMap.put(414, "请求的 URI（通常为网址）过长，服务器无法处理。");//414   （请求的 URI 过长） 请求的 URI（通常为网址）过长，服务器无法处理。
			messageMap.put(415, "请求的格式不受请求页面的支持。");//415   （不支持的媒体类型） 请求的格式不受请求页面的支持。
			messageMap.put(416, "如果页面无法提供请求的范围，则服务器会返回此状态代码。");//416   （请求范围不符合要求） 如果页面无法提供请求的范围，则服务器会返回此状态代码。
			messageMap.put(417, "服务器未满足'期望'请求标头字段的要求。");//417   （未满足期望值） 服务器未满足"期望"请求标头字段的要求。
			messageMap.put(500,"500.服务器遇到错误，无法完成请求。");//500   （服务器内部错误）  服务器遇到错误，无法完成请求。
			messageMap.put(501,"501.服务器不具备完成请求的功能。");//501   （尚未实施） 服务器不具备完成请求的功能。 例如，服务器无法识别请求方法时可能会返回此代码。
			messageMap.put(502,"502.服务器作为网关或代理，从上游服务器收到无效响应。");//502   （错误网关） 服务器作为网关或代理，从上游服务器收到无效响应。
			messageMap.put(503,"503.服务器目前无法使用（由于超载或停机维护）。");//503   （服务不可用） 服务器目前无法使用（由于超载或停机维护）。 通常，这只是暂时状态。
			messageMap.put(504,"504.服务器作为网关或代理，但是没有及时从上游服务器收到请求。");//504   （网关超时）  服务器作为网关或代理，但是没有及时从上游服务器收到请求。
			messageMap.put(505,"505.服务器不支持请求中所用的 HTTP 协议版本。");//505   （HTTP 版本不受支持） 服务器不支持请求中所用的 HTTP 协议版本。
			
			messageMap.put(800, "");
			messageMap.put(800, "数据响应异常。");
			messageMap.put(801, "响应为null。");//客户端接收到的响应为空。
			messageMap.put(802, "HTTP主机连接异常。");//HTTP主机连接异常(HttpHostConnectException)
			messageMap.put(803, "socket的异常。");//socket的异常(SocketException)
			messageMap.put(804, "与服务器连接失败，请检查网络和相关配置。");//与服务器连接失败。
			messageMap.put(805, "不支持的编码异常。");//不支持的编码异常(UnsupportedEncodingException)
			messageMap.put(806, "客户端协议异常。");//客户端协议异常(ClientProtocolException)
			messageMap.put(807, "非法状态异常。");//非法状态异常(IllegalStateException)
			messageMap.put(808, "IO异常。");//IO异常(IOException)
			messageMap.put(809, "数据访问过程中发生未知异常。");
		}
		return messageMap;
	}
	
	private MessageFacotry() {
	};

	public MessageFacotry(Context context, int responseCode) {
		this.context = context;

	};

	public static void responseErrMessage(Context context, int responseCode) {
		MessageFacotry messageFacotry = new MessageFacotry();
		messageFacotry.setContext(context);
		MyToast.makeText(context, messageFacotry.errMsg(responseCode), Toast.LENGTH_SHORT).show();
	}
	
	public static String responseErrMessage(int responseCode){
		MessageFacotry messageFacotry = new MessageFacotry();
		return messageFacotry.errMsg(responseCode);
	}
	
	private Handler responseHandler = new Handler() {
	
	};
	
	public String errMsg(int responseCode){
		String errMsg="";
		errMsg = getMessageMap().get(responseCode);
		if(null==errMsg){
			errMsg = "响应码"+responseCode+".未知错误。";
		}
		return errMsg;
	}
	

	
}
