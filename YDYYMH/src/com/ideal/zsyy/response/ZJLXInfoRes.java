package  com.ideal.zsyy.response;

import java.util.List;

import com.ideal.zsyy.entity.ZJLXInfo;
import com.ideal2.base.gson.CommonRes;


public class ZJLXInfoRes extends CommonRes {

	private List<ZJLXInfo> zjlxInfos;

	public List<ZJLXInfo> getZjlxInfos() {
		return zjlxInfos;
	}
	public void setZjlxInfos(List<ZJLXInfo> zjlxInfos) {
		this.zjlxInfos = zjlxInfos;
	}
}
