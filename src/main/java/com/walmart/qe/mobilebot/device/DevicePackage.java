package com.walmart.qe.mobilebot.device;

public class DevicePackage {

	private final String packageName;

	public DevicePackage(String packageName){
		this.packageName = packageName;
	}
	
	public String getPackageName() {
		return packageName;
	}
}
