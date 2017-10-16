package com.walmart.qe.mobilebot.stelabapi;

import java.util.Map;
import java.util.HashMap;

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
"name",
"ip",
"port",
"manufacturer",
"model",
"os",
"firmware"
})
public class MetaTag {

@JsonProperty("name")
@SerializedName("name")
@Expose
private String name;
@JsonProperty("ip")
@SerializedName("ip")
@Expose
private String ip;
@JsonProperty("port")
@SerializedName("port")
@Expose
private String port;
@JsonProperty("password")
@SerializedName("password")
@Expose
private String password;
@JsonProperty("manufacturer")
@SerializedName("manufacturer")
@Expose
private String manufacturer;
@JsonProperty("model")
@SerializedName("model")
@Expose
private String model;
@JsonProperty("os")
@SerializedName("os")
@Expose
private String os;
@JsonProperty("firmware")
@SerializedName("firmware")
@Expose
private String firmware;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("name")
public String getName() {
return name;
}

@JsonProperty("name")
public void setName(String name) {
this.name = name;
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
public String getPort() {
return port;
}

@JsonProperty("port")
public void setPort(String port) {
this.port = port;
}

@JsonProperty("password")
public String getPassword() {
return password;
}

@JsonProperty("password")
public void setPassword(String password) {
this.password = password;
}

@JsonProperty("manufacturer")
public String getManufacturer() {
return manufacturer;
}

@JsonProperty("manufacturer")
public void setManufacturer(String manufacturer) {
this.manufacturer = manufacturer;
}

@JsonProperty("model")
public String getModel() {
return model;
}

@JsonProperty("model")
public void setModel(String model) {
this.model = model;
}

@JsonProperty("os")
public String getOs() {
return os;
}

@JsonProperty("os")
public void setOs(String os) {
this.os = os;
}

@JsonProperty("firmware")
public String getFirmware() {
return firmware;
}

@JsonProperty("firmware")
public void setFirmware(String firmware) {
this.firmware = firmware;
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
