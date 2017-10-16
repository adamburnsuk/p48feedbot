package com.walmart.qe.mobilebot.stelabapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReservationResponse {

@SerializedName("action")
@Expose
private String action;
@SerializedName("session-id")
@Expose
private String sessionId;
@SerializedName("reservation-id")
@Expose
private Integer reservationId;
@SerializedName("reservation-details")
@Expose
private ActiveReservation reservationDetails;
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

public Integer getReservationId() {
return reservationId;
}

public void setReservationId(Integer reservationId) {
this.reservationId = reservationId;
}

public ActiveReservation getActiveReservation() {
return reservationDetails;
}

public void setActiveReservation(ActiveReservation reservationDetails) {
	this.reservationDetails = reservationDetails;
}

public RequestStatus getRequestStatus() {
return requestStatus;
}

public void setRequestStatus(RequestStatus requestStatus) {
this.requestStatus = requestStatus;
}

}
