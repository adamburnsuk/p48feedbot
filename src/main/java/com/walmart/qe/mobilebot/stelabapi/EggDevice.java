package com.walmart.qe.mobilebot.stelabapi;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EggDevice {

@SerializedName("device-id")
@Expose
private Integer deviceId;
@SerializedName("meta-tags")
@Expose
private List<MetaTag> metaTags = null;
@SerializedName("active-reservation")
@Expose
private ActiveReservation activeReservation;

public Integer getDeviceId() {
return deviceId;
}

public void setDeviceId(Integer deviceId) {
this.deviceId = deviceId;
}

public List<MetaTag> getMetaTags() {
return metaTags;
}

public void setMetaTags(List<MetaTag> metaTags) {
this.metaTags = metaTags;
}

public ActiveReservation getActiveReservation() {
	return activeReservation;
}

public void setActiveReservation(ActiveReservation activeReservation) {
	this.activeReservation = activeReservation;
}

public String getDeviceName(){
	return this.getMetaTags().get(0).getName();
}

public String getDeviceIP() {
	return this.getMetaTags().get(1).getIp();
}

public String getDevicePort(){
	return this.getMetaTags().get(2).getPort();
}

public String getDeviceManufacturer(){
	return this.getMetaTags().get(3).getManufacturer();
}

public String getDeviceModel(){
	return this.getMetaTags().get(4).getModel();
}

public String getDeviceOS(){
	return this.getMetaTags().get(5).getOs();
}

public String getDeviceFirmware(){
	return this.getMetaTags().get(6).getFirmware();
}

}
