package com.ideal.zsyy.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ideal.zsyy.entity.WCBUserEntity;
import com.ideal.zsyy.service.PreferencesService;

import android.annotation.SuppressLint;
import android.content.Context;

public class PrintTemplate {

	// 收费小票打印版式
	@SuppressLint("SimpleDateFormat")
	public static String[] GetPrintData(WCBUserEntity userItem,Context context) {
		Date currDate = new Date();
		PreferencesService preferencesService = new PreferencesService(context);
		List<String> arrList = new ArrayList<String>();
		String strDate = new SimpleDateFormat("yyyy年MM月dd日").format(currDate);
		if (userItem == null) {
			return null;
		}
		arrList.add("\n喀左县自来水公司");
		arrList.add("水费收费凭条\n");
		arrList.add("\n\r");
		arrList.add("\n\r");
		arrList.add("用户号： " + userItem.getUserNo() + "\n");
		arrList.add("户  名： " + userItem.getUserFName() + "\n");
		arrList.add("电  话：" + (userItem.getPhone() == null ? "" : userItem.getPhone()) + "\n");
		arrList.add("地  址：" + userItem.getAddress() + "\n");
		arrList.add("-----------------------\n");
		arrList.add("上期读数：" + (int) userItem.getLastMonthValue() + "\n");
		arrList.add("本期读数：" + (int) userItem.getCurrentMonthValue() + "\n");
		arrList.add("本期水量：" + (int) userItem.getCurrMonthWNum() + "\n");
		arrList.add("水费单价：" + userItem.getAvePrice() + "元/吨\n");
		arrList.add("污水处理费单价：" + userItem.getExtraChargePrice1() + "元/吨\n");
		arrList.add("水费：" + userItem.getCurrMonthFee() + "元\n");
		arrList.add("附加费：" + userItem.getExtraCharge2() + "元\n");
		arrList.add("污水处理费：" + userItem.getExtraCharge1() + "元\n");
		arrList.add("金额合计：" + userItem.getTotalCharge() + "元\n");
		//arrList.add("账户金额：" + userItem.getOweMoney() + "元\n");
		arrList.add("交费方式：" + userItem.getPreMoney() + "元\n");//======================
		arrList.add("-----------------------\n");
		arrList.add("收费员：" + preferencesService.getLoginInfo().get("userName").toString() + "\n");
		arrList.add("打票时间：" + strDate + "\n");
		arrList.add("维修电话：4822974\n");
		arrList.add("备注：非报销凭证，请持此收费凭条30日内到自来水公司换取正式发票\n");
		arrList.add("\n----------------------\n");
		arrList.add("\n\r");

		return (String[]) arrList.toArray(new String[arrList.size()]);
	}
}
