package com.walmart.qe.mobilebot.stelabapi;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AvailableDeviceResponse {

	@SerializedName("available-devices")
	@Expose
	private List<EggDevice> Devices = null;
	@SerializedName("user-id")
	@Expose
	private Integer userId;
	@SerializedName("action")
	@Expose
	private String action;
	@SerializedName("session-id")
	@Expose
	private String sessionId;
	@SerializedName("request-status")
	@Expose
	private RequestStatus requestStatus;

	public List<EggDevice> getDevices() {
	return Devices;
	}

	public void setDevices(List<EggDevice> Devices) {
	this.Devices = Devices;
	}

	public Integer getUserId() {
	return userId;
	}

	public void setUserId(Integer userId) {
	this.userId = userId;
	}

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

	public RequestStatus getRequestStatus() {
	return requestStatus;
	}

	public void setRequestStatus(RequestStatus requestStatus) {
	this.requestStatus = requestStatus;
	}

	}