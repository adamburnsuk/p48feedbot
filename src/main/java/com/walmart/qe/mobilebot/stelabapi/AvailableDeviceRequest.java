package com.walmart.qe.mobilebot.stelabapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AvailableDeviceRequest {

@SerializedName("action")
@Expose
private String action;
@SerializedName("session-id")
@Expose
private String sessionId;
@SerializedName("start-time")
@Expose
private String startTime;
@SerializedName("end-time")
@Expose
private String endTime;

public String getAction() {
return action;
}

public void setAction(String action) {
this.action = action;
}

public String getSessionId() {
return sessionId;
}

public void setSessionId(String sessionId) {
this.sessionId = sessionId;
}

public String getStartTime() {
return startTime;
}

public void setStartTime(String startTime) {
this.startTime = startTime;
}

public String getEndTime() {
return endTime;
}

public void setEndTime(String endTime) {
this.endTime = endTime;
}

}
