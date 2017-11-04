package com.ideal.zsyy.base;

import android.graphics.drawable.Drawable;

public class AppListBase {

	private Drawable drawable;
	private String appName;
	private String className;
	private String packageName;
	private String parameters;

	public AppListBase(String packageName, String className, String parameters,
			Drawable drawable, String appName) {

		this.packageName = packageName;
		this.className = className;
		this.drawable = drawable;
		this.appName = appName;
		this.parameters = parameters;

	}

	public Drawable getDrawable() {
		return drawable;
	}

	public void setDrawable(Drawable drawable) {
		this.drawable = drawable;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

}
