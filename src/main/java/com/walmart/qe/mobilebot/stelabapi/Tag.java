package com.walmart.qe.mobilebot.stelabapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tag {

@SerializedName("name")
@Expose
private String name;
@SerializedName("ip")
@Expose
private String ip;
@SerializedName("port")
@Expose
private String port;
@SerializedName("password")
@Expose
private String password;
@SerializedName("manufacturer")
@Expose
private String manufacturer;
@SerializedName("model")
@Expose
private String model;
@SerializedName("os")
@Expose
private String os;
@SerializedName("firmware")
@Expose
private String firmware;

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getIp() {
return ip;
}

public void setIp(String ip) {
this.ip = ip;
}

public String getPort() {
return port;
}

public void setPort(String port) {
this.port = port;
}

public String getPassword() {
return password;
}

public void setPassword(String password) {
this.password = password;
}

public String getManufacturer() {
return manufacturer;
}

public void setManufacturer(String manufacturer) {
this.manufacturer = manufacturer;
}

public String getModel() {
return model;
}

public void setModel(String model) {
this.model = model;
}

public String getOs() {
return os;
}

public void setOs(String os) {
this.os = os;
}

public String getFirmware() {
return firmware;
}

public void setFirmware(String firmware) {
this.firmware = firmware;
}

}
