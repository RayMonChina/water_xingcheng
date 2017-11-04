package com.ideal.zsyy.response;

import java.util.List;

import com.ideal.zsyy.entity.EquipmentItem;
import com.ideal2.base.gson.CommonRes;

public class DeviceRes extends CommonRes {
	private List<EquipmentItem> equipmentItems;

	public List<EquipmentItem> getEquipmentItems() {
		return equipmentItems;
	}

	public void setEquipmentItems(List<EquipmentItem> equipmentItems) {
		this.equipmentItems = equipmentItems;
	}
	
}
