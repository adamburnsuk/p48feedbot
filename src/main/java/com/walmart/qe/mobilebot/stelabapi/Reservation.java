package com.walmart.qe.mobilebot.stelabapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Reservation {

@SerializedName("action")
@Expose
private String action;
@SerializedName("session-id")
@Expose
private String sessionId;
@SerializedName("reservation-details")
@Expose
private ActiveReservation reservationDetails;
@SerializedName("request-status")
@Expose
private RequestStatus requestStatus;
@SerializedName("device-id")
@Expose
private int deviceId;
@SerializedName("start-in")
@Expose
private int startIn;
@SerializedName("duration")
@Expose
private int duration;
@SerializedName("reservation-id")
@Expose
private Integer reservationId;

public int getDuration() {
	return duration;
}

public void setDuration(int duration) {
	this.duration = duration;
}

public int getDeviceId() {
	return deviceId;
}

public void setDeviceId(Integer deviceId) {
	this.deviceId = deviceId;
}

public int getStartIn() {
	return startIn;
}

public void setStartIn(int startIn) {
	this.startIn = startIn;
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

public ActiveReservation getReservationDetails() {
return reservationDetails;
}

public void setReservationDetails(ActiveReservation reservationDetails) {
this.reservationDetails = reservationDetails;
}

public RequestStatus getRequestStatus() {
return requestStatus;
}

public void setRequestStatus(RequestStatus requestStatus) {
this.requestStatus = requestStatus;
}

public int getReservationId() {
	return reservationId;
}

public void setReservationId(Integer reservationId) {
	this.reservationId = reservationId;
}

}
