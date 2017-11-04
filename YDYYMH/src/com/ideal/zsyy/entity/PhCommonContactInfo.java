package com.ideal.zsyy.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class PhCommonContactInfo implements Parcelable {

	private String id;
	private String user_id;
	private String contact_name;
	private String contact_sex;
	private String contact_brithday;
	private String contact_phone;
	private String zj_type;
	private String zj_number;
	private String isvisit;
	private String contact_add;
	private String contact_person_name;
	private String contact_person_phone;
	private String medical_cardnum;

	public static final Parcelable.Creator<PhCommonContactInfo> CREATOR = new Creator<PhCommonContactInfo>() {
		public PhCommonContactInfo createFromParcel(Parcel source) {
			PhCommonContactInfo info = new PhCommonContactInfo();
			info.id = source.readString();
			info.user_id = source.readString();
			info.contact_name = source.readString();
			info.contact_sex = source.readString();
			info.contact_brithday = source.readString();
			info.contact_phone= source.readString();
			info.zj_type = source.readString();
			info.zj_number = source.readString();
			info.isvisit = source.readString();
			info.contact_add = source.readString();
			info.contact_person_name = source.readString();
			info.contact_person_phone = source.readString();
			info.medical_cardnum = source.readString();
			return info;
		}

		public PhCommonContactInfo[] newArray(int size) {
			return new PhCommonContactInfo[size];
		}
	};

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(user_id);
		dest.writeString(contact_name);
		dest.writeString(contact_sex);
		dest.writeString(contact_brithday);
		dest.writeString(contact_phone);
		dest.writeString(zj_type);
		dest.writeString(zj_number);
		dest.writeString(isvisit);
		dest.writeString(contact_add);
		dest.writeString(contact_person_name);
		dest.writeString(contact_person_phone);
		dest.writeString(medical_cardnum);
	}

	public String getId() {
		return id == null ? "" : id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser_id() {
		return user_id == null ? "" : user_id;
	}

	public void setUser_id(String userId) {
		user_id = userId;
	}

	public String getContact_name() {
		return contact_name == null ? "" : contact_name;
	}

	public void setContact_name(String contactName) {
		contact_name = contactName;
	}

	public String getContact_sex() {
		return contact_sex == null ? "" : contact_sex;
	}

	public void setContact_sex(String contactSex) {
		contact_sex = contactSex;
	}

	public String getContact_brithday() {
		return contact_brithday == null ? "" : contact_brithday;
	}

	public void setContact_brithday(String contactBrithday) {
		contact_brithday = contactBrithday;
	}

	public String getContact_phone() {
		return contact_phone == null ? "" : contact_phone;
	}

	public void setContact_phone(String contactPhone) {
		contact_phone = contactPhone;
	}

	public String getZj_type() {
		return zj_type == null ? "" : zj_type;
	}

	public void setZj_type(String zjType) {
		zj_type = zjType;
	}

	public String getZj_number() {
		return zj_number == null ? "" : zj_number;
	}

	public void setZj_number(String zjNumber) {
		zj_number = zjNumber;
	}

	public String getIsvisit() {
		return isvisit == null ? "" : isvisit;
	}

	public void setIsvisit(String isvisit) {
		this.isvisit = isvisit;
	}

	public String getContact_person_name() {
		return contact_person_name == null ? "" : contact_person_name;
	}

	public void setContact_person_name(String contactPersonName) {
		contact_person_name = contactPersonName;
	}

	public String getContact_person_phone() {
		return contact_person_phone == null ? "" : contact_person_phone;
	}

	public void setContact_person_phone(String contactPersonPhone) {
		contact_person_phone = contactPersonPhone;
	}

	public String getMedical_cardnum() {
		return medical_cardnum == null ? "" : medical_cardnum;
	}

	public void setMedical_cardnum(String medicalCardnum) {
		medical_cardnum = medicalCardnum;
	}

	public String getContact_add() {
		return contact_add == null ? "" : contact_add;
	}

	public void setContact_add(String contactAdd) {
		contact_add = contactAdd;
	}
}
