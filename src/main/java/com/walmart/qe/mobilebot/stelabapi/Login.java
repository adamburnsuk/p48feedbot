package com.walmart.qe.mobilebot.stelabapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login {

@SerializedName("action")
@Expose
private String action;
@SerializedName("username")
@Expose
private String username;
@SerializedName("password")
@Expose
private String password;

public String getAction() {
return action;
}

public void setAction(String action) {
this.action = action;
}

public String getUsername() {
return username;
}

public void setUsername(String username) {
this.username = username;
}

public String getPassword() {
return password;
}

public void setPassword(String password) {
this.password = password;
}

}
