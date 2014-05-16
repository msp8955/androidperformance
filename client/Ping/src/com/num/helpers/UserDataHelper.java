package com.num.helpers;

import android.content.Context;
import com.num.utils.PreferencesUtil;

public class UserDataHelper {
	
	
	Context context;
	public final static int NONE = -1;
	public final static int PREPAID = -2;
	
	public UserDataHelper(Context context){
		this.context = context;
	}
	
	public int getDataCap() {
		return PreferencesUtil.getDataInt("dataLimit", context);
	}
	public void setDataCap(int dataCap) {
		PreferencesUtil.setDataInt("dataLimit", dataCap, context);
	}
	
	public int getBillingCycle() {
		return PreferencesUtil.getDataInt("billingCycle", context);
	}
	public void setBillingCycle(int billingCycle) {
		PreferencesUtil.setDataInt("billingCycle", billingCycle, context);
	}
	
	public int getBillingCost() {
		return PreferencesUtil.getDataInt("billingCost", context);
	}
	public void setBillingCost(int cost) {
		PreferencesUtil.setDataInt("billingCost", cost, context);
	}
	
	public String getCurrency(){
		return PreferencesUtil.getDataString("currency", context);
	}
	public void setCurrency(String c){
		PreferencesUtil.setDataString("currency", c, context);
	}
	
	public int getDataEnable() {
		return PreferencesUtil.getDataInt("dataenable", context);
	}
	public void setDataEnable(int val) {
		PreferencesUtil.setDataInt("billingCost", val, context);
	}
	public int getPrepaidData() {
		return PreferencesUtil.getDataInt("prepaidData", context);
	}
	public void setPrepaidData(int val) {
		PreferencesUtil.setDataInt("prepaidData", val, context);
	}
	
	public boolean isFilled(){		
		return PreferencesUtil.contains("dataLimit", context) && 
				PreferencesUtil.contains("billingCycle", context) && 
				PreferencesUtil.contains("billingCost", context) &&
				PreferencesUtil.contains("currency", context);
	}

}
