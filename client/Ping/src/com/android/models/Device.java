package com.android.models;

import android.net.NetworkInfo.DetailedState;
import android.net.NetworkInfo.State;

public class Device {
	
	String phoneDetail;
	String networkDetail;


	String phoneType;
	String deviceId;
	String softwareVersion;
	String phoneNumber;
	String networkCountry;
	String networkOperatorId;
	String networkName;
	String networkType;
	String simNetworkCountry;
	String simState;
	String simOperatorName;
	String simOperatorCode;
	String simSerialNumber;
	String connectionType;
	String mobileNetworkState;
	String mobileNetworkDetailedState;
	String wifiState;
	GPS gps;
	
	
	

	public GPS getGps() {
		return gps;
	}

	public void setGps(GPS gps) {
		this.gps = gps;
	}

	public Device() {
		
	}
	
	public Device(String phoneDetail, String networkDetail) {
		super();
		this.phoneDetail = phoneDetail;
		this.networkDetail = networkDetail;
	}

	
	public String getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}

	public String getPhoneDetail() {
		return phoneDetail;
	}

	public void setPhoneDetail(String phoneDetail) {
		this.phoneDetail = phoneDetail;
	}

	public String getNetworkDetail() {
		return networkDetail;
	}

	public void setNetworkDetail(String networkDetail) {
		this.networkDetail = networkDetail;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getSoftwareVersion() {
		return softwareVersion;
	}

	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getNetworkCountryISO() {
		return networkCountry;
	}

	public void setNetworkCountryISO(String networkCountry) {
		this.networkCountry = networkCountry;
	}

	public String getNetworkOperatorId() {
		return networkOperatorId;
	}

	public void setNetworkOperatorId(String networkOperatorId) {
		this.networkOperatorId = networkOperatorId;
	}

	public String getNetworkName() {
		return networkName;
	}

	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}

	public String getNetworkType() {
		return networkType;
	}

	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}

	public String getSimState() {
		return simState;
	}

	public void setSimState(String simState) {
		this.simState = simState;
	}

	public String getNetworkCountry() {
		return networkCountry;
	}

	public void setNetworkCountry(String networkCountry) {
		this.networkCountry = networkCountry;
	}

	public String getSimNetworkCountry() {
		return simNetworkCountry;
	}

	public void setSimNetworkCountry(String simNetworkCountry) {
		this.simNetworkCountry = simNetworkCountry;
	}
	
	public String getSimOperatorCode() {
		return simOperatorCode;
	}

	public void setSimOperatorCode(String simOperatorCode) {
		this.simOperatorCode = simOperatorCode;
	}

	public String getSimOperatorName() {
		return simOperatorName;
	}

	public void setSimOperatorName(String simOperatorName) {
		this.simOperatorName = simOperatorName;
	}

	public String getSimSerialNumber() {
		return simSerialNumber;
	}

	public void setSimSerialNumber(String simSerialNumber) {
		this.simSerialNumber = simSerialNumber;
	}

	public String getConnectionType() {
		return connectionType;
	}

	public void setConnectionType(String connectionType) {
		this.connectionType = connectionType;
	}

	public String getMobileNetworkState() {
		return mobileNetworkState;
	}

	public void setMobileNetworkState(String mobileNetworkState) {
		this.mobileNetworkState = mobileNetworkState;
	}

	public String getMobileNetworkDetailedState() {
		return mobileNetworkDetailedState;
	}

	public void setMobileNetworkDetailedState(String mobileNetworkDetailedState) {
		this.mobileNetworkDetailedState = mobileNetworkDetailedState;
	}

	public String getWifiState() {
		return wifiState;
	}

	public void setWifiState(String wifiState) {
		this.wifiState = wifiState;
	}

	

	
	

}
