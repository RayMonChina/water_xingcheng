package com.ideal.zsyy.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

import android.R.integer;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.util.Base64;


public class ImageItem implements Serializable {
	public String imageId;
	public String thumbnailPath;
	public String imagePath;
	private Bitmap bitmap;
	public boolean isSelected = false;
	
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	public String getThumbnailPath() {
		return thumbnailPath;
	}
	public void setThumbnailPath(String thumbnailPath) {
		this.thumbnailPath = thumbnailPath;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public boolean isSelected() {
		return isSelected;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	public Bitmap getBitmap() {		
		if(bitmap == null){
			try {
				bitmap = Bimp.revitionImageSize(imagePath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	
	@SuppressLint("NewApi")
	public String imgToBase64() {
		ByteArrayOutputStream out = null;
		if(getBitmap()==null)
		{
			return "";
		}
		try {
			out = new ByteArrayOutputStream();
			int compress=100;
			int bcount=bitmap.getByteCount();
			if(bcount>1024*500&&bcount<1024*1024)
			{
				compress=80;
			}
			else if(bcount>1024*1024) {
				compress=50;
			}
			bitmap.compress(Bitmap.CompressFormat.JPEG, compress, out);
			out.flush();
			out.close();
			byte[] imgBytes = out.toByteArray();
			return Base64.encodeToString(imgBytes, Base64.DEFAULT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		} finally {
			try {
				out.flush();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
