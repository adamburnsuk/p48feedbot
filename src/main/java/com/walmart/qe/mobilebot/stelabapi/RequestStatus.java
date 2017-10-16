package com.walmart.qe.mobilebot.stelabapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestStatus {

@SerializedName("status-id")
@Expose
private String statusId;
@SerializedName("status-description")
@Expose
private String statusDescription;
@SerializedName("status-details")
@Expose
private String statusDetails;

public String getStatusId() {
return statusId;
}

public void setStatusId(String statusId) {
this.statusId = statusId;
}

public String getStatusDescription() {
return statusDescription;
}

public void setStatusDescription(String statusDescription) {
this.statusDescription = statusDescription;
}

public String getStatusDetails() {
return statusDetails;
}

public void setStatusDetails(String statusDetails) {
this.statusDetails = statusDetails;
}

}
