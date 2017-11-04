package com.ideal.zsyy.entity;

public class WCBUserEntity {

	private int UserID;
	private String UserNo;
	private int BId;
	private String NoteNo;
	private String UserFName;
	private String Phone;
	private String Address;
	private double LastMonthValue;
	private double LastMonthWater;
	private double LastMonthFee;
	private double CurrentMonthValue;
	private double CurrMonthWNum;
	private double CurrMonthFee;
	private double ShouFei;
	private String ShouFeiDate;
	private String PriceType;
	private String PriceTypeName;
	private String StealNo;
	private int ChaoBiaoTag;
	private String ChaoBiaoDate;
	private int OrChaoBiaoTag; // 是否有状态改变
	private int OrMemoTag;// 是否备注和电话修改
	private int IsPrint;// 是否打印
	private int OrPhoneTag;
	private int OrGpsTag;
	private int IsChaoBiao;
	private double PreMoney;
	private double OweMoney;
	private double OrOweFee;
	private double OrPreMoney;
	private double Latitude;
	private double Longitude;
	private int alreadyUpload;
	private int IsReverse;
	private String StepPrice;
	private String ExtraPrice;
	private int IsSummaryMeter;
	private String WaterMeterParentID;
	private int OrderNumber;
	private double WaterFixValue;
	private String NFCTag;
	private String LastChaoBiaoDate;
	private String StealID;
	private String PianNo;
	private String AreaNo;
	private String DuanNo;
	private String readMeterRecordId;
	private double avePrice;
	private double extraChargePrice1;
	private double extraCharge1;
	private double extraChargePrice2;
	private double extraCharge2;
	private double extraTotalCharge;
	private double TotalCharge;
	private double OVERDUEMONEY;
	private String checkState;
	private String checkDateTime;
	private String checker;
	private int ReadMeterRecordYear;
	private int ReadMeterRecordMonth;
	private String WaterMeterPositionName;
	private String chargeID;
	private String waterUserchargeType;
	private String waterUserTypeId;
	private String Memo1;

	public int getUserID() {
		return UserID;
	}

	public void setUserID(int userID) {
		UserID = userID;
	}

	public String getUserNo() {
		return UserNo;
	}

	public void setUserNo(String userNo) {
		UserNo = userNo;
	}

	public String getNoteNo() {
		return NoteNo;
	}

	public void setNoteNo(String noteNo) {
		NoteNo = noteNo;
	}

	public String getUserFName() {
		if (UserFName == null || UserFName.length() <= 0) {
			return " ";
		} else {
			return UserFName;
		}

	}

	public void setUserFName(String userFName) {
		UserFName = userFName;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public double getLastMonthValue() {
		return LastMonthValue;
	}

	public void setLastMonthValue(double lastMonthValue) {
		LastMonthValue = lastMonthValue;
	}

	public double getLastMonthWater() {
		return LastMonthWater;
	}

	public void setLastMonthWater(double lastMonthWater) {
		LastMonthWater = lastMonthWater;
	}

	public double getLastMonthFee() {
		return LastMonthFee;
	}

	public void setLastMonthFee(double lastMonthFee) {
		LastMonthFee = lastMonthFee;
	}

	public double getCurrentMonthValue() {
		return CurrentMonthValue;
	}

	public void setCurrentMonthValue(double currentMonthValue) {
		CurrentMonthValue = currentMonthValue;
	}

	public double getCurrMonthWNum() {
		return CurrMonthWNum;
	}

	public void setCurrMonthWNum(double currMonthWNum) {
		CurrMonthWNum = currMonthWNum;
	}

	public double getCurrMonthFee() {
		return CurrMonthFee;
	}

	public void setCurrMonthFee(double currMonthFee) {
		CurrMonthFee = currMonthFee;
	}

	public double getShouFei() {
		return ShouFei;
	}

	public void setShouFei(double shouFei) {
		ShouFei = shouFei;
	}

	public String getShouFeiDate() {
		return ShouFeiDate;
	}

	public void setShouFeiDate(String shouFeiDate) {
		ShouFeiDate = shouFeiDate;
	}

	public String getPriceType() {
		return PriceType;
	}

	public void setPriceType(String priceType) {
		PriceType = priceType;
	}

	public String getPriceTypeName() {
		return PriceTypeName;
	}

	public void setPriceTypeName(String priceTypeName) {
		PriceTypeName = priceTypeName;
	}

	public String getStealNo() {
		return StealNo;
	}

	public void setStealNo(String stealNo) {
		StealNo = stealNo;
	}

	public int getChaoBiaoTag() {
		return ChaoBiaoTag;
	}

	public void setChaoBiaoTag(int chaoBiaoTag) {
		ChaoBiaoTag = chaoBiaoTag;
	}

	public String getChaoBiaoDate() {
		return ChaoBiaoDate;
	}

	public void setChaoBiaoDate(String chaoBiaoDate) {
		ChaoBiaoDate = chaoBiaoDate;
	}

	public int getOrChaoBiaoTag() {
		return OrChaoBiaoTag;
	}

	public void setOrChaoBiaoTag(int orChaoBiaoTag) {
		OrChaoBiaoTag = orChaoBiaoTag;
	}

	public double getPreMoney() {
		return PreMoney;
	}

	public void setPreMoney(double preMoney) {
		PreMoney = preMoney;
	}

	public double getOweMoney() {
		return OweMoney;
	}

	public void setOweMoney(double oweMoney) {
		OweMoney = oweMoney;
	}

	public double getOrOweFee() {
		return OrOweFee;
	}

	public void setOrOweFee(double orOweFee) {
		OrOweFee = orOweFee;
	}

	public double getOrPreMoney() {
		return OrPreMoney;
	}

	public void setOrPreMoney(double orPreMoney) {
		OrPreMoney = orPreMoney;
	}

	public double getLatitude() {
		return Latitude;
	}

	public void setLatitude(double latitude) {
		Latitude = latitude;
	}

	public double getLongitude() {
		return Longitude;
	}

	public void setLongitude(double longitude) {
		Longitude = longitude;
	}

	public int getAlreadyUpload() {
		return alreadyUpload;
	}

	public void setAlreadyUpload(int AlreadyUpload) {
		this.alreadyUpload = AlreadyUpload;
	}

	public int getIsReverse() {
		return IsReverse;
	}

	public void setIsReverse(int isReverse) {
		IsReverse = isReverse;
	}

	public String getStepPrice() {
		return StepPrice;
	}

	public void setStepPrice(String stepPrice) {
		StepPrice = stepPrice;
	}

	public String getExtraPrice() {
		return ExtraPrice;
	}

	public void setExtraPrice(String extraPrice) {
		ExtraPrice = extraPrice;
	}

	public int getIsSummaryMeter() {
		return IsSummaryMeter;
	}

	public void setIsSummaryMeter(int isSummaryMeter) {
		IsSummaryMeter = isSummaryMeter;
	}

	public String getWaterMeterParentID() {
		return WaterMeterParentID;
	}

	public void setWaterMeterParentID(String waterMeterParentID) {
		WaterMeterParentID = waterMeterParentID;
	}

	public int getOrderNumber() {
		return OrderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		OrderNumber = orderNumber;
	}

	public double getWaterFixValue() {
		return WaterFixValue;
	}

	public void setWaterFixValue(double waterFixValue) {
		WaterFixValue = waterFixValue;
	}

	public String getNFCTag() {
		return NFCTag;
	}

	public void setNFCTag(String nFCTag) {
		NFCTag = nFCTag;
	}

	public String getLastChaoBiaoDate() {
		return LastChaoBiaoDate;
	}

	public void setLastChaoBiaoDate(String lastChaoBiaoDate) {
		LastChaoBiaoDate = lastChaoBiaoDate;
	}

	public String getStealID() {
		return StealID;
	}

	public void setStealID(String stealID) {
		StealID = stealID;
	}

	public String getPianNo() {
		return PianNo;
	}

	public void setPianNo(String pianNo) {
		PianNo = pianNo;
	}

	public String getAreaNo() {
		return AreaNo;
	}

	public void setAreaNo(String areaNo) {
		AreaNo = areaNo;
	}

	public String getDuanNo() {
		return DuanNo;
	}

	public void setDuanNo(String duanNo) {
		DuanNo = duanNo;
	}

	public int getBId() {
		return BId;
	}

	public void setBId(int bId) {
		BId = bId;
	}

	public String getReadMeterRecordId() {
		return readMeterRecordId;
	}

	public void setReadMeterRecordId(String readMeterRecordId) {
		this.readMeterRecordId = readMeterRecordId;
	}

	public double getAvePrice() {
		return avePrice;
	}

	public void setAvePrice(double avePrice) {
		this.avePrice = avePrice;
	}

	public double getExtraChargePrice1() {
		return extraChargePrice1;
	}

	public void setExtraChargePrice1(double extraChargePrice1) {
		this.extraChargePrice1 = extraChargePrice1;
	}

	public double getExtraCharge1() {
		return extraCharge1;
	}

	public void setExtraCharge1(double extraCharge1) {
		this.extraCharge1 = extraCharge1;
	}

	public double getExtraChargePrice2() {
		return extraChargePrice2;
	}

	public void setExtraChargePrice2(double extraChargePrice2) {
		this.extraChargePrice2 = extraChargePrice2;
	}

	public double getExtraCharge2() {
		return extraCharge2;
	}

	public void setExtraCharge2(double extraCharge2) {
		this.extraCharge2 = extraCharge2;
	}

	public double getExtraTotalCharge() {
		return extraTotalCharge;
	}

	public void setExtraTotalCharge(double extraTotalCharge) {
		this.extraTotalCharge = extraTotalCharge;
	}

	public double getTotalCharge() {
		return TotalCharge;
	}

	public void setTotalCharge(double totalCharge) {
		TotalCharge = totalCharge;
	}

	public double getOVERDUEMONEY() {
		return OVERDUEMONEY;
	}

	public void setOVERDUEMONEY(double oVERDUEMONEY) {
		OVERDUEMONEY = oVERDUEMONEY;
	}

	public int getReadMeterRecordYear() {
		return ReadMeterRecordYear;
	}

	public void setReadMeterRecordYear(int readMeterRecordYear) {
		ReadMeterRecordYear = readMeterRecordYear;
	}

	public int getReadMeterRecordMonth() {
		return ReadMeterRecordMonth;
	}

	public void setReadMeterRecordMonth(int readMeterRecordMonth) {
		ReadMeterRecordMonth = readMeterRecordMonth;
	}

	public String getWaterMeterPositionName() {
		return WaterMeterPositionName;
	}

	public void setWaterMeterPositionName(String waterMeterPositionName) {
		WaterMeterPositionName = waterMeterPositionName;
	}

	public String getCheckState() {
		return checkState;
	}

	public void setCheckState(String checkState) {
		this.checkState = checkState;
	}

	public String getCheckDateTime() {
		return checkDateTime;
	}

	public void setCheckDateTime(String checkDateTime) {
		this.checkDateTime = checkDateTime;
	}

	public String getChecker() {
		return checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	public String getChargeID() {
		return chargeID;
	}

	public void setChargeID(String chargeID) {
		this.chargeID = chargeID;
	}

	public String getWaterUserchargeType() {
		return waterUserchargeType;
	}

	public void setWaterUserchargeType(String waterUserchargeType) {
		this.waterUserchargeType = waterUserchargeType;
	}

	public String getWaterUserTypeId() {
		return waterUserTypeId;
	}

	public void setWaterUserTypeId(String waterUserTypeId) {
		this.waterUserTypeId = waterUserTypeId;
	}

	public String getMemo1() {
		return Memo1;
	}

	public void setMemo1(String Memo1) {
		this.Memo1 = Memo1;
	}

	public int getOrMemoTag() {
		return OrMemoTag;
	}

	public void setOrMemoTag(int orMemoTag) {
		OrMemoTag = orMemoTag;
	}

	public int getIsPrint() {
		return IsPrint;
	}

	public void setIsPrint(int isPrint) {
		IsPrint = isPrint;
	}

	public int getOrPhoneTag() {
		return OrPhoneTag;
	}

	public void setOrPhoneTag(int orPhoneTag) {
		OrPhoneTag = orPhoneTag;
	}

	public int getOrGpsTag() {
		return OrGpsTag;
	}

	public void setOrGpsTag(int orGpsTag) {
		OrGpsTag = orGpsTag;
	}

	public int getIsChaoBiao() {
		return IsChaoBiao;
	}

	public void setIsChaoBiao(int isChaoBiao) {
		IsChaoBiao = isChaoBiao;
	}

}
