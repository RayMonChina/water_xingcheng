package com.ideal.zsyy.response;

import com.ideal2.base.gson.CommonRes;

public class VersionValidateRes extends CommonRes {

	private String fileUrl;
	private String updateInfo;
	private int versionCode;

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getUpdateInfo() {
		return updateInfo;
	}

	public void setUpdateInfo(String updateInfo) {
		this.updateInfo = updateInfo;
	}

	public int getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}
	
}
