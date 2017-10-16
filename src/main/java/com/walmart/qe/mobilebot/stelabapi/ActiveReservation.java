package com.walmart.qe.mobilebot.stelabapi;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"start-time",
"end-time",
"ip",
"port",
"user-id",
"device-id",
"reservation-id",
"connection-options",
"meta-tags"
})
public class ActiveReservation {

@JsonProperty("start-time")
@SerializedName("start-time")
@Expose
private String startTime;
@JsonProperty("end-time")
@SerializedName("end-time")
@Expose
private String endTime;
@JsonProperty("ip")
@SerializedName("ip")
@Expose
private String ip;
@JsonProperty("port")
@SerializedName("port")
@Expose
private Integer port;
@JsonProperty("user-id")
@SerializedName("user-id")
@Expose
private Integer userId;
@JsonProperty("device-id")
@SerializedName("device-id")
@Expose
private Integer deviceId;
@JsonProperty("reservation-id")
@SerializedName("reservation-id")
@Expose
private Integer reservationId;
@JsonProperty("connection-options")
@SerializedName("connection-options")
@Expose
private ConnectionOptions connectionOptions;
@JsonProperty("meta-tags")
@SerializedName("meta-tags")
@Expose
private List<MetaTag> metaTags = null;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

public String getStartTime() {
return startTime;
}


@JsonProperty("start-time")
public void setStartTime(String startTime) {
this.startTime = startTime;
}


@JsonProperty("end-time")
public String getEndTime() {
return endTime;
}


@JsonProperty("end-time")
public void setEndTime(String endTime) {
this.endTime = endTime;
}


@JsonProperty("ip")
public String getIp() {
return ip;
}


@JsonProperty("ip")
public void setIp(String ip) {
this.ip = ip;
}


@JsonProperty("port")
public Integer getPort() {
return port;
}


@JsonProperty("port")
public void setPort(Integer port) {
this.port = port;
}


@JsonProperty("user-id")
public Integer getUserId() {
return userId;
}


@JsonProperty("user-id")
public void setUserId(Integer userId) {
this.userId = userId;
}


@JsonProperty("device-id")
public Integer getDeviceId() {
return deviceId;
}


@JsonProperty("device-id")
public void setDeviceId(Integer deviceId) {
this.deviceId = deviceId;
}


@JsonProperty("reservation-id")
public Integer getReservationId() {
return reservationId;
}


@JsonProperty("reservation-id")
public void setReservationId(Integer reservationId) {
this.reservationId = reservationId;
}


@JsonProperty("connection-options")
public ConnectionOptions getConnectionOptions() {
return connectionOptions;
}


@JsonProperty("connection-options")
public void setConnectionOptions(ConnectionOptions connectionOptions) {
this.connectionOptions = connectionOptions;
}


@JsonProperty("meta-tags")
public List<MetaTag> getMetaTags() {
return metaTags;
}


@JsonProperty("meta-tags")
public void setMetaTags(List<MetaTag> metaTags) {
this.metaTags = metaTags;
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

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}
