package com.walmart.qe.mobilebot.stelabapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SessionInfo {

@SerializedName("action")
@Expose
private String action;
@SerializedName("session-id")
@Expose
private String sessionId;
@SerializedName("app-version")
@Expose
private String appVersion;
@SerializedName("app-platform")
@Expose
private String appPlatform;
@SerializedName("user-id")
@Expose
private Integer userId;
@SerializedName("request-status")
@Expose
private RequestStatus requestStatus;

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

public String getAppVersion() {
return appVersion;
}

public void setAppVersion(String appVersion) {
this.appVersion = appVersion;
}

public String getAppPlatform() {
return appPlatform;
}

public void setAppPlatform(String appPlatform) {
this.appPlatform = appPlatform;
}

public Integer getUserId() {
return userId;
}

public void setUserId(Integer userId) {
this.userId = userId;
}

public RequestStatus getRequestStatus() {
return requestStatus;
}

public void setRequestStatus(RequestStatus requestStatus) {
this.requestStatus = requestStatus;
}

}
